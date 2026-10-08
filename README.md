# Intranet STEMDO

![Java](https://img.shields.io/badge/Java-17-informational)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-brightgreen)
![Build](https://img.shields.io/badge/build-Maven-blue)
![Milestone](https://img.shields.io/badge/Milestone%201-MVP%20en%20curso-orange)

Intranet corporativa de STEMDO: punto de acceso único a la información y los servicios internos de la
empresa. Este repositorio contiene el **Milestone 1 – MVP Intranet** (fecha límite: **22 de octubre de 2026**),
cuyo objetivo es entregar una primera versión funcional y navegable que sirva de base para el resto del proyecto.

## Índice

1. [Estado del proyecto](#1-estado-del-proyecto)
2. [Alcance de esta versión](#2-alcance-de-esta-versión)
3. [Stack tecnológico](#3-stack-tecnológico)
4. [Puesta en marcha](#4-puesta-en-marcha)
5. [Arquitectura](#5-arquitectura)
6. [Estructura del proyecto](#6-estructura-del-proyecto)
7. [Seguridad: autenticación, autorización y roles](#7-seguridad-autenticación-autorización-y-roles)
8. [Modelo de datos](#8-modelo-de-datos)
9. [Rutas de la aplicación](#9-rutas-de-la-aplicación)
10. [Configuración](#10-configuración)
11. [Pruebas](#11-pruebas)
12. [Decisiones de diseño y limitaciones conocidas](#12-decisiones-de-diseño-y-limitaciones-conocidas)
13. [Hoja de ruta](#13-hoja-de-ruta)
14. [Guía de desarrollo](#14-guía-de-desarrollo)
15. [Solución de problemas](#15-solución-de-problemas)

---

## 1. Estado del proyecto

| Indicador            | Valor |
|----------------------|-------|
| Milestone            | [Milestone 1 – MVP Intranet](https://github.com/Proyecto-Intranet/Proyecto-Intranet/milestone/1) |
| Fecha límite         | 22 de octubre de 2026 |
| Issues completados   | 4 de 9 (#1, #3, #4, #6) |
| Issues en curso      | 1 (#5 Gestión de roles) |
| Issues pendientes    | 4 (#7, #8, #9, #10) |

El detalle por issue, con lo hecho y lo que falta, está en
[`docs/ESTADO-MILESTONE-1.md`](docs/ESTADO-MILESTONE-1.md).

## 2. Alcance de esta versión

### Incluido

- Aplicación base Spring Boot arrancable con un solo comando.
- **Autenticación** por formulario (login y logout) con contraseñas cifradas (BCrypt).
- **Control de acceso**: todas las páginas requieren sesión; los usuarios no autenticados son
  redirigidos a `/login`.
- **Roles** `ADMIN` y `USER` almacenados en base de datos y cargados en Spring Security.
- **Diseño corporativo común**: hoja de estilos, logotipos y plantillas reutilizables (cabecera, pie).
- **Página de inicio** con saludo al usuario autenticado y las cinco áreas de la intranet.
- **Página de error** común (404, 403 y error genérico).

### Fuera de alcance (planificado en el milestone)

- Secciones Personas y Cultura, Mi Carrera y Formación (#7, #8, #9).
- Directorio de empleados y oficinas (#10).
- Restricción de rutas por rol (parte pendiente de #5).
- Despliegue en un entorno compartido y base de datos persistente.

## 3. Stack tecnológico

| Capa              | Tecnología |
|-------------------|------------|
| Lenguaje          | Java 17 |
| Framework         | Spring Boot 4.1.1 |
| Web               | Spring MVC + Thymeleaf (renderizado en servidor) |
| Seguridad         | Spring Security + `thymeleaf-extras-springsecurity6` |
| Persistencia      | Spring Data JPA (Hibernate) |
| Base de datos     | H2 en memoria |
| Construcción      | Maven (wrapper incluido, no hace falta instalarlo) |
| Pruebas           | JUnit 5, Spring Boot Test, Spring Security Test |

## 4. Puesta en marcha

### Requisitos

- JDK 17 o superior (`java -version`).
- Acceso a Maven Central la primera vez, para descargar dependencias.

### Arrancar la aplicación

```bash
./mvnw spring-boot:run        # Linux / macOS
mvnw.cmd spring-boot:run      # Windows
```

La aplicación queda disponible en <http://localhost:8080>.

### Usuarios de prueba

Se crean automáticamente en cada arranque.

| Usuario   | Contraseña    | Rol   |
|-----------|---------------|-------|
| `admin`   | `admin1234`   | ADMIN |
| `usuario` | `usuario1234` | USER  |

> Son credenciales **solo para desarrollo local**. Deben eliminarse antes de cualquier despliegue
> (ver [Decisiones y limitaciones](#12-decisiones-de-diseño-y-limitaciones-conocidas)).

### Generar el ejecutable

```bash
./mvnw clean package
java -jar target/intranet-0.0.1-SNAPSHOT.jar
```

## 5. Arquitectura

Aplicación **monolítica** con renderizado en servidor: no hay API REST ni frontend independiente.

```
Navegador ──HTTP──▶ Filtros de Spring Security (sesión, CSRF, login)
                            │
                            ▼
                    Controlador MVC ──▶ Plantilla Thymeleaf ──▶ HTML
                            │
                            ▼
              UsuarioDetailsService ──▶ UsuarioRepository (JPA) ──▶ H2
```

Flujo de inicio de sesión:

1. El usuario solicita una ruta protegida sin sesión → redirección a `/login`.
2. El formulario envía `POST /login` (con token CSRF).
3. Spring Security llama a `UsuarioDetailsService`, que busca el usuario en base de datos.
4. Se compara la contraseña con el hash BCrypt almacenado.
5. Si es correcta, se crea la sesión y se redirige a `/`; si no, a `/login?error`.

## 6. Estructura del proyecto

```
Proyecto-Intranet/
├── pom.xml                          # Dependencias y build
├── mvnw, mvnw.cmd, .mvn/            # Maven Wrapper
├── README.md
├── docs/
│   └── ESTADO-MILESTONE-1.md        # Seguimiento de issues del milestone
└── src/
    ├── main/
    │   ├── java/com/intranetstemdo/intranet/
    │   │   ├── IntranetApplication.java        # Punto de entrada
    │   │   ├── config/
    │   │   │   ├── SecurityConfig.java         # Reglas de acceso, login/logout, BCrypt
    │   │   │   └── DatosDev.java               # Usuarios de prueba al arrancar
    │   │   ├── usuarios/
    │   │   │   ├── Usuario.java                # Entidad JPA
    │   │   │   ├── UsuarioRepository.java      # Acceso a datos
    │   │   │   └── UsuarioDetailsService.java  # Integración con Spring Security
    │   │   └── web/
    │   │       └── PaginasController.java      # Rutas "/" y "/login"
    │   └── resources/
    │       ├── application.yml                 # Configuración
    │       ├── static/css/app.css              # Estilos corporativos
    │       ├── static/images/                  # Logotipos y favicon
    │       └── templates/
    │           ├── index.html                  # Página de inicio
    │           ├── login.html                  # Formulario de acceso
    │           ├── error.html                  # Página de error
    │           └── fragments/layout.html       # <head>, cabecera y pie reutilizables
    └── test/java/com/intranetstemdo/intranet/
        └── IntranetApplicationTests.java       # Prueba de arranque del contexto
```

### Responsabilidad de cada clase

| Clase | Responsabilidad |
|-------|-----------------|
| `IntranetApplication` | Arranque de Spring Boot. |
| `SecurityConfig` | Define qué rutas son públicas, configura el formulario de login/logout y declara el codificador de contraseñas BCrypt. |
| `DatosDev` | `CommandLineRunner` que inserta los usuarios de prueba al iniciar. |
| `Usuario` | Entidad JPA que representa a un usuario de la intranet. |
| `UsuarioRepository` | Repositorio Spring Data con la búsqueda `findByUsername`. |
| `UsuarioDetailsService` | Convierte un `Usuario` en un `UserDetails` de Spring Security (rol → `ROLE_<rol>`; cuenta deshabilitada si `activo = false`). |
| `PaginasController` | Controlador MVC de las páginas de inicio y login. |

### Plantillas Thymeleaf

`fragments/layout.html` contiene los fragmentos compartidos por todas las páginas:

| Fragmento | Uso | Contenido |
|-----------|-----|-----------|
| `head(titulo)` | `<head th:replace="~{fragments/layout :: head('Título')}">` | Metadatos, título, favicon y hoja de estilos |
| `cabecera` | `<header th:replace="~{fragments/layout :: cabecera}">` | Logotipo, usuario autenticado y botón **Salir** |
| `pie` | `<footer th:replace="~{fragments/layout :: pie}">` | Pie de página |

## 7. Seguridad: autenticación, autorización y roles

### Autenticación

- Inicio de sesión por **formulario** en `/login`; cierre de sesión mediante `POST /logout`.
- Contraseñas almacenadas únicamente como hash **BCrypt**; nunca en texto plano.
- Un usuario con `activo = false` no puede iniciar sesión.
- Mensajes de retroalimentación: `/login?error` (credenciales incorrectas) y `/login?logout` (sesión cerrada).

### Autorización

| Ruta | Acceso |
|------|--------|
| `/login`, `/error` | Público |
| `/css/**`, `/images/**` | Público |
| Cualquier otra ruta | Requiere sesión iniciada |

### Protección CSRF

Activada por defecto. Los formularios Thymeleaf (`th:action`) incluyen el token automáticamente.

### Roles

Cada usuario tiene un rol en base de datos (`ADMIN` o `USER`) que Spring Security expone como
`ROLE_ADMIN` / `ROLE_USER`. Pueden usarse:

- En la configuración de seguridad: `.requestMatchers("/admin/**").hasRole("ADMIN")`
- En plantillas: `<a sec:authorize="hasRole('ADMIN')">…</a>`

> **Estado:** el modelo de roles está implementado, pero **todavía no hay rutas restringidas por rol**.
> Es la parte pendiente del issue #5.

## 8. Modelo de datos

Una única tabla, generada por Hibernate a partir de la entidad `Usuario`.

**Tabla `usuario`**

| Columna    | Tipo         | Restricciones | Descripción |
|------------|--------------|---------------|-------------|
| `id`       | BIGINT       | PK, autogenerada | Identificador |
| `username` | VARCHAR(100) | Único, no nulo | Nombre de usuario |
| `password` | VARCHAR(100) | No nulo | Hash BCrypt de la contraseña |
| `rol`      | VARCHAR(30)  | No nulo | `ADMIN` o `USER` |
| `activo`   | BOOLEAN      | No nulo, por defecto `true` | Si es `false`, no puede iniciar sesión |

## 9. Rutas de la aplicación

| Método | Ruta | Acceso | Descripción |
|--------|------|--------|-------------|
| GET  | `/`       | Autenticado | Página de inicio |
| GET  | `/login`  | Público     | Formulario de acceso |
| POST | `/login`  | Público     | Procesa el inicio de sesión (Spring Security) |
| POST | `/logout` | Autenticado | Cierra la sesión (Spring Security) |
| GET  | `/error`  | Público     | Página de error |

## 10. Configuración

Fichero `src/main/resources/application.yml`:

| Propiedad | Valor | Descripción |
|-----------|-------|-------------|
| `server.port` | `8080` | Puerto HTTP |
| `spring.datasource.url` | `jdbc:h2:mem:intranet` | Base de datos H2 en memoria |
| `spring.jpa.hibernate.ddl-auto` | `create-drop` | Hibernate crea el esquema al arrancar y lo elimina al parar |
| `spring.jpa.open-in-view` | `false` | Evita consultas perezosas desde la capa web |
| `spring.thymeleaf.cache` | `false` | Recarga las plantillas sin reiniciar (desarrollo) |

## 11. Pruebas

```bash
./mvnw test
```

Actualmente existe una prueba de humo (`IntranetApplicationTests.contextLoads`) que verifica que el
contexto de Spring arranca correctamente. Pruebas previstas: login correcto e incorrecto,
redirección de rutas protegidas y control de acceso por rol.

## 12. Decisiones de diseño y limitaciones conocidas

| Decisión / limitación | Motivo | Cuándo revisarla |
|-----------------------|--------|------------------|
| H2 en memoria con `create-drop` | Cero configuración para el MVP | Al necesitar datos persistentes (p. ej. directorio de empleados): H2 en fichero o PostgreSQL con migraciones Flyway |
| Usuarios de prueba fijos en `DatosDev` | Permitir el acceso sin pantalla de alta | Antes de desplegar: eliminarlos y crear el administrador inicial mediante variable de entorno |
| Tarjetas de inicio sin enlaces | Las secciones aún no existen | Al implementar cada sección |
| Sin restricciones por rol | Pendiente de #5 | Antes de cerrar el milestone |
| Cookie de sesión sin atributo `Secure` | Desarrollo local por HTTP | Al desplegar bajo HTTPS: `server.servlet.session.cookie.secure=true` |
| Cobertura de pruebas mínima | Alcance de MVP | Añadir pruebas de seguridad antes del despliegue |

## 13. Hoja de ruta

Seguimiento completo en el [milestone](https://github.com/Proyecto-Intranet/Proyecto-Intranet/milestone/1).

| # | Issue | Prioridad | Estado |
|---|-------|-----------|--------|
| 1  | Configuración inicial del proyecto | Urgent | ✅ Completado |
| 3  | Diseño corporativo común | High | ✅ Completado |
| 4  | Sistema de autenticación | High | ✅ Completado |
| 5  | Gestión de roles | High | 🔄 En curso |
| 6  | Página de Inicio | High | ✅ Completado |
| 7  | Sección Personas y Cultura | Medium | ⏳ Pendiente |
| 8  | Sección Mi Carrera | Medium | ⏳ Pendiente |
| 9  | Sección Formación | Medium | ⏳ Pendiente |
| 10 | Directorio de empleados y oficinas | High | ⏳ Pendiente |

## 14. Guía de desarrollo

### Añadir una nueva sección

1. Crear la plantilla `src/main/resources/templates/secciones/<seccion>.html` usando los fragmentos
   `head`, `cabecera` y `pie`.
2. Añadir el método en el controlador:
   ```java
   @GetMapping("/<seccion>")
   public String seccion() { return "secciones/<seccion>"; }
   ```
3. Enlazar la tarjeta correspondiente en `index.html`.
4. Si la sección es solo para un rol, añadir la regla en `SecurityConfig`:
   `.requestMatchers("/<seccion>/**").hasRole("ADMIN")`.

### Flujo de trabajo con Git

- Una rama por issue: `feature/<n>-descripcion-corta` (por ejemplo, `feature/10-directorio`).
- Commits siguiendo [Conventional Commits](https://www.conventionalcommits.org/): `feat:`, `fix:`, `docs:`, `chore:`, `test:`.
- Referenciar el issue en el commit o pull request: `Closes #N` para cerrarlo automáticamente,
  `Refs #N` para enlazarlo sin cerrarlo.
- Actualizar `docs/ESTADO-MILESTONE-1.md` al cerrar cada issue.

## 15. Solución de problemas

| Síntoma | Causa probable | Solución |
|---------|----------------|----------|
| `Port 8080 was already in use` | Otro proceso usa el puerto | Cambiar `server.port` o liberar el puerto |
| Error de versión de Java al compilar | JDK anterior a 17 | Instalar JDK 17+ y comprobar `java -version` |
| No descarga dependencias | Sin acceso a Maven Central (proxy, red corporativa) | Configurar el proxy en `~/.m2/settings.xml` |
| No puedo iniciar sesión | La BD es en memoria y se recrea al reiniciar | Usar `admin / admin1234` o `usuario / usuario1234` |
| `403` tras enviar un formulario | Falta el token CSRF | Usar `th:action` en el formulario para que se incluya automáticamente |
