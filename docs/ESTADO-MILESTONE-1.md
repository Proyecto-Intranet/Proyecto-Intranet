# Estado del Milestone 1 – MVP Intranet

**Vence:** 22 de octubre de 2026 · **Última actualización:** 8 de octubre de 2026

## Resumen

La versión actual cubre la **base técnica, el login y la página de inicio**. Las cuatro secciones
de contenido y el directorio de empleados siguen pendientes.

| # | Issue | Prioridad | Estado | Qué hay hecho | Qué falta |
|---|-------|-----------|--------|---------------|-----------|
| 1 | Configuración inicial del proyecto | Urgent | **Hecho → cerrar** | Proyecto Spring Boot/Maven, estructura de paquetes, H2, Thymeleaf, README, tests de arranque | — |
| 3 | Diseño corporativo común | High | **Hecho → cerrar** | CSS corporativo, paleta por área, logos, fragmentos de cabecera/pie reutilizables | Menú de navegación entre secciones (va con cada sección) |
| 4 | Sistema de autenticación | High | **Hecho → cerrar** | Login/logout, BCrypt, rutas protegidas, usuarios en BD, página de error | — |
| 5 | Gestión de roles | High | **En curso** | Rol `ADMIN`/`USER` guardado y cargado en Spring Security | Restringir al menos una ruta por rol y comprobarlo con un test |
| 6 | Página de Inicio | High | **Hecho → cerrar** | Bienvenida con el nombre del usuario y las 5 tarjetas de áreas | Enlazar las tarjetas cuando existan las secciones |
| 7 | Sección Personas y Cultura | Medium | Pendiente | — | Todo |
| 8 | Sección Mi Carrera | Medium | Pendiente | — | Todo |
| 9 | Sección Formación | Medium | Pendiente | — | Todo |
| 10 | Directorio de empleados y oficinas | High | Pendiente (**siguiente a empezar**) | — | Entidades, datos, listado y búsqueda |

> Nota: no existe el issue #2 en el milestone (la numeración salta del 1 al 3).

**Progreso:** 4 de 9 issues completados (~44 %), 1 en curso, 4 pendientes.

## Cómo reflejarlo en GitHub

- **Cerrar** #1, #3, #4 y #6 (usar `Closes #N` en el commit/PR o cerrarlos a mano con el comentario de abajo).
- **Mover a "In progress"** #5 (roles) y #10 (directorio, el siguiente por prioridad).
- **Dejar en "To do"** #7, #8 y #9.

## Comentarios sugeridos para cada issue

**#1 Configuración inicial** – cerrar
> Proyecto base creado con Spring Boot 4.1.1 + Maven (Java 17). Estructura de paquetes `config`, `usuarios`, `web`; plantillas Thymeleaf; H2 en memoria. Instrucciones de arranque en el README. Documentación técnica completa en el README.

**#3 Diseño corporativo común** – cerrar
> Hoja de estilos corporativa (`app.css`), logos y favicon, y fragmentos Thymeleaf reutilizables (`head`, `cabecera`, `pie`) en `fragments/layout.html`. La navegación entre secciones se añadirá junto a cada sección.

**#4 Sistema de autenticación** – cerrar
> Login por formulario con Spring Security, contraseñas con BCrypt y usuarios en base de datos. Todas las rutas exigen sesión salvo `/login`, recursos estáticos y `/error`. Logout operativo. Usuarios de prueba: `admin` y `usuario` (ver README).

**#5 Gestión de roles** – en curso
> El rol (`ADMIN`/`USER`) se guarda en BD y se carga como `ROLE_*` en Spring Security. Pendiente: proteger al menos una ruta por rol y añadir un test que compruebe que `USER` recibe 403.

**#6 Página de Inicio** – cerrar
> Página de inicio con cabecera, saludo al usuario autenticado y las 5 áreas de la intranet. Las tarjetas se enlazarán cuando se implementen las secciones (#7, #8, #9, #10).

**#10 Directorio de empleados y oficinas** – en curso (al empezar)
> Se inicia el trabajo: entidades `Empleado` y `Oficina`, datos de ejemplo, listado y búsqueda.

## Mensajes de commit sugeridos

    chore: estructura inicial del proyecto Spring Boot            (Closes #1)
    feat: estilos corporativos y fragmentos de layout             (Closes #3)
    feat: login y logout con Spring Security y BCrypt             (Closes #4)
    feat: página de inicio con las áreas de la intranet           (Closes #6)
    feat: restringir rutas por rol                                (Refs #5)

## Próximos pasos recomendados (hasta el 22 de octubre)

1. Verificar que la app arranca en tu máquina y cerrar #1, #3, #4, #6.
2. Terminar #5 (una ruta solo-ADMIN + test).
3. Empezar #10 (directorio): es High y las demás secciones pueden ser páginas estáticas.
4. Hacer #7, #8 y #9 como páginas de contenido simples enlazadas desde el inicio.
5. Actualizar este documento al cerrar cada issue.
