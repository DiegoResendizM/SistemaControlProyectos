# Sistema de Control de Proyectos (SCP)

Este es un proyecto de Java que implementa un sistema de gestión de reportes de proyectos y documentos PDF con interfaz gráfica de usuario (GUI) utilizando Swing. Permite guardar reportes, procesar archivos con streams, cargar y gestionar documentos PDF, y realizar operaciones básicas de archivo.

## Descripción

El sistema consta de:
- **Pantalla de Inicio**: Muestra un progreso de carga y procesa datos iniciales con streams de Java.
- **Menú Principal**: Acceso a las secciones de gestión de reportes y documentos.
- **Gestión de Reportes**: Permite ingresar datos de proyectos, guardar reportes en archivos de texto y procesarlos (ej. convertir a minúsculas) usando streams.
- **Gestión de Documentos**: Permite cargar PDFs, listarlos en una tabla, abrirlos, eliminarlos y actualizar la lista.
- **Manejo de Archivos**: Utiliza una clase `FileManager` para operaciones de archivos, incluyendo creación de carpetas (`reportes` y `documentos`), copia, eliminación y procesamiento.

Incluye funcionalidades como validaciones, confirmaciones y manejo de errores. El proyecto incorpora procesamiento avanzado con streams de Java 8+ para transformar archivos.

## Características

- Interfaz gráfica amigable con Swing y diseños responsivos.
- Guardado y procesamiento de reportes de proyectos en archivos TXT.
- Carga, visualización, apertura y eliminación de documentos PDF.
- Procesamiento de archivos usando streams (map, reduce, etc.).
- Barra de progreso en la pantalla de inicio con simulación de carga.
- Validaciones y mensajes de error/confirmación con JOptionPane.
- Soporte para matrícula de estudiante (ES231110272) en reportes.
- Creación automática de carpetas para reportes y documentos.

## Tecnologías Utilizadas

- **Lenguaje**: Java (JDK 8 o superior, con streams de Java 8+).
- **Bibliotecas**: Swing para GUI, java.awt y java.io para manejo de archivos e interfaces.
- **Entorno de Desarrollo**: Basado en los archivos proporcionados, parece desarrollado en un IDE como NetBeans o IntelliJ.
- **Almacenamiento**: Archivos de texto plano (TXT) para reportes y PDFs en carpetas locales.

## Requisitos

- Java Runtime Environment (JRE) 8 o superior.
- Compilador Java (para construir el proyecto).
- Opcional: IDE como NetBeans, IntelliJ o Eclipse para editar y ejecutar.
- Lector de PDF instalado en el sistema (ej. Adobe Reader) para abrir documentos.

## Instalación

1. Clona o descarga el repositorio:
   ```
   git clone https://github.com/tu-usuario/scp-proyecto.git
   ```

2. Abre el proyecto en tu IDE.
   - Asegúrate de que los paquetes estén configurados correctamente:
     - `com.dgit.scp`: MainApp.java
     - `com.dgit.scp.util`: FileManager.java
     - `com.dgit.scp.ui`: PantallaInicio.java, MenuPrincipal.java, GestionReportes.java, GestionDocumentos.java

3. Compila el proyecto.

4. Las carpetas `reportes` y `documentos` se crearán automáticamente al ejecutar.

## Uso

### Iniciar la Aplicación
1. Ejecuta `MainApp.java` (o desde el IDE).
2. Se mostrará la **Pantalla de Inicio** con una barra de progreso que simula el procesamiento de datos con streams.
3. Una vez completado, accede al **Menú Principal**.

### Gestión de Reportes
1. Desde el Menú Principal, selecciona "Gestión de Reportes de Proyecto".
2. Ingresa el nombre del proyecto, responsable y descripción del avance.
3. Haz clic en "Guardar Reporte" para almacenar en `reportes/proyecto_ES231110272_original.txt` (modo append para agregar múltiples reportes).
4. Haz clic en "Procesar Documento" para generar una versión procesada (ej. en minúsculas) en `reportes/proyecto_ES231110272_procesado.txt`.

### Gestión de Documentos
1. Desde el Menú Principal, selecciona "Gestión de Documentos PDF".
2. Haz clic en "Cargar PDF" para seleccionar y copiar un PDF a la carpeta `documentos`.
3. La tabla muestra los PDFs cargados con nombre, tamaño y fecha.
4. Selecciona un PDF y usa:
   - "Abrir PDF" para visualizarlo.
   - "Eliminar PDF" para borrarlo (con confirmación).
   - "Actualizar Lista" para refrescar la tabla.

### Notas
- Los reportes incluyen timestamps y la matrícula del estudiante.
- El procesamiento usa streams para transformaciones como minúsculas, mayúsculas, etc. (configurable en el código).
- Archivos se almacenan en carpetas relativas al directorio de ejecución.

## Estructura del Proyecto

```
src/
├── com/
│   ├── dgit/
│   │   ├── scp/
│   │   │   └── MainApp.java
│   │   ├── scp/
│   │   │   └── util/
│   │   │       └── FileManager.java
│   │   └── scp/
│   │       └── ui/
│   │           ├── PantallaInicio.java
│   │           ├── MenuPrincipal.java
│   │           ├── GestionReportes.java
│   │           └── GestionDocumentos.java
reportes/     (generada en runtime)
documentos/   (generada en runtime)
```

## Contribuciones

Si deseas contribuir:
1. Haz un fork del repositorio.
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`).
3. Commitea tus cambios (`git commit -m 'Agrega nueva funcionalidad'`).
4. Pushea la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.

## Licencia

Este proyecto está bajo la licencia MIT. Ver [LICENSE](LICENSE) para más detalles.

## Autor

- Diego Resendiz
- Contacto: dirm_1104@hotmail.com

Si tienes problemas o sugerencias, abre un issue en el repositorio. ¡Gracias por usar este proyecto!
