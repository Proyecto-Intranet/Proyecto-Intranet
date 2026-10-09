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

