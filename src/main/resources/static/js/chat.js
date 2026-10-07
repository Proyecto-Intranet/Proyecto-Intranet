(function () {
  'use strict';

  const toggle   = document.getElementById('chat-toggle');
  const panel    = document.getElementById('chat-panel');
  const cerrar   = document.getElementById('chat-cerrar');
  const mensajes = document.getElementById('chat-mensajes');
  const entrada  = document.getElementById('chat-texto');
  const enviar   = document.getElementById('chat-enviar');

  // Si la página no tiene el widget, no hacemos nada
  if (!toggle || !panel) {
    return;
  }

  // Token CSRF que Thymeleaf dejó en las etiquetas <meta> de la cabecera
  const csrfToken  = document.querySelector('meta[name="_csrf"]')?.content;
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

  let saludado = false;
  let enviando = false;

  function abrir() {
    panel.hidden = false;
    toggle.hidden = true;
    entrada.focus();
    if (!saludado) {
      anadir('¡Hola! Pregúntame lo que necesites.', 'bot');
      saludado = true;
    }
  }

  function cerrarPanel() {
    panel.hidden = true;
    toggle.hidden = false;
    toggle.focus();
  }

  // textContent (y no innerHTML) evita que un texto con HTML se ejecute en la página
  function anadir(texto, clase) {
    const burbuja = document.createElement('div');
    burbuja.className = 'msg ' + clase;
    burbuja.textContent = texto;
    mensajes.appendChild(burbuja);
    mensajes.scrollTop = mensajes.scrollHeight;
    return burbuja;
  }

  async function enviarPregunta() {
    const texto = entrada.value.trim();
    if (!texto || enviando) {
      return;
    }

    anadir(texto, 'usuario');
    entrada.value = '';
    enviando = true;
    enviar.disabled = true;
    const pensando = anadir('Escribiendo…', 'bot pensando');

    try {
      const respuesta = await fetch('/api/chat', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ texto })
      });

      if (respuesta.redirected || respuesta.status === 401 || respuesta.status === 403) {
        throw new Error('sesion');
      }
      if (!respuesta.ok) {
        throw new Error('http ' + respuesta.status);
      }

      const datos = await respuesta.json();
      pensando.className = 'msg bot' + (datos.encontrada ? '' : ' sin-respuesta');
      pensando.textContent = datos.texto;
    } catch (error) {
      pensando.className = 'msg bot error';
      pensando.textContent = error.message === 'sesion'
        ? 'Tu sesión ha caducado. Recarga la página e inicia sesión de nuevo.'
        : 'Ha ocurrido un error. Inténtalo de nuevo en unos minutos.';
    } finally {
      enviando = false;
      enviar.disabled = false;
      mensajes.scrollTop = mensajes.scrollHeight;
      entrada.focus();
    }
  }

  toggle.addEventListener('click', abrir);
  cerrar.addEventListener('click', cerrarPanel);
  enviar.addEventListener('click', enviarPregunta);
  entrada.addEventListener('keydown', function (evento) {
    if (evento.key === 'Enter') {
      evento.preventDefault();
      enviarPregunta();
    }
  });
  document.addEventListener('keydown', function (evento) {
    if (evento.key === 'Escape' && !panel.hidden) {
      cerrarPanel();
    }
  });
})();