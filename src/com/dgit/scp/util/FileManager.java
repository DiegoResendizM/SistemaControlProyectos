package com.dgit.scp.util;

import java.awt.Desktop;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileManager {
    
    private static final String CARPETA_REPORTES = "reportes";
    private static final String CARPETA_DOCUMENTOS = "documentos";
    
    static {
        // Crear carpetas si no existen
        crearCarpeta(CARPETA_REPORTES);
        crearCarpeta(CARPETA_DOCUMENTOS);
    }
    
    /**
     * Crea una carpeta si no existe
     */
    private static void crearCarpeta(String nombreCarpeta) {
        File carpeta = new File(nombreCarpeta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
    
    /**
     * Guarda un reporte en archivo de texto
     * @param nombreArchivo Nombre del archivo (ej: proyecto_ES231110272_original.txt)
     * @param contenido Contenido del reporte
     * @return true si se guardó exitosamente, false en caso contrario
     */
    public static boolean guardarReporte(String nombreArchivo, String contenido) {
        String rutaCompleta = CARPETA_REPORTES + File.separator + nombreArchivo;
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaCompleta, true))) {  // Cambiado a true para agregar (append)
            writer.print(contenido);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar reporte: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Copia un archivo PDF a la carpeta de documentos
     * @param archivoOrigen Archivo PDF a copiar
     * @return true si se copió exitosamente, false en caso contrario
     */
    public static boolean copiarDocumento(File archivoOrigen) {
        if (!archivoOrigen.exists()) {
            return false;
        }
        
        String nombreArchivo = archivoOrigen.getName();
        Path origen = archivoOrigen.toPath();
        Path destino = Paths.get(CARPETA_DOCUMENTOS, nombreArchivo);
        
        try {
            // Si el archivo ya existe, agregar un número
            if (Files.exists(destino)) {
                String nombreSinExtension = nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
                String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf('.'));
                int contador = 1;
                
                do {
                    nombreArchivo = nombreSinExtension + "_" + contador + extension;
                    destino = Paths.get(CARPETA_DOCUMENTOS, nombreArchivo);
                    contador++;
                } while (Files.exists(destino));
            }
            
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            System.err.println("Error al copiar documento: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Lista todos los documentos PDF en la carpeta de documentos
     * @return Lista con información de cada documento [nombre, tamaño, fecha]
     */
    public static List<String[]> listarDocumentos() {
        List<String[]> documentos = new ArrayList<>();
        File carpeta = new File(CARPETA_DOCUMENTOS);
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
        
        if (archivos != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            
            for (File archivo : archivos) {
                String nombre = archivo.getName();
                String tamanio = formatearTamanio(archivo.length());
                String fecha = sdf.format(new Date(archivo.lastModified()));
                
                documentos.add(new String[]{nombre, tamanio, fecha});
            }
        }
        
        return documentos;
    }
    
    /**
     * Abre un documento PDF con la aplicación predeterminada del sistema
     * @param nombreArchivo Nombre del archivo PDF
     * @return true si se abrió exitosamente, false en caso contrario
     */
    public static boolean abrirDocumento(String nombreArchivo) {
        File archivo = new File(CARPETA_DOCUMENTOS, nombreArchivo);
        
        if (!archivo.exists()) {
            return false;
        }
        
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(archivo);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            System.err.println("Error al abrir documento: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Elimina un documento PDF de la carpeta de documentos
     * @param nombreArchivo Nombre del archivo PDF a eliminar
     * @return true si se eliminó exitosamente, false en caso contrario
     */
    public static boolean eliminarDocumento(String nombreArchivo) {
        File archivo = new File(CARPETA_DOCUMENTOS, nombreArchivo);
        
        if (!archivo.exists()) {
            return false;
        }
        
        return archivo.delete();
    }
    
    /**
     * Lee el contenido de un archivo de texto
     * @param nombreArchivo Nombre del archivo
     * @return Contenido del archivo o null si hay error
     */
    public static String leerReporte(String nombreArchivo) {
        String rutaCompleta = CARPETA_REPORTES + File.separator + nombreArchivo;
        StringBuilder contenido = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaCompleta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
            return contenido.toString();
        } catch (IOException e) {
            System.err.println("Error al leer reporte: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Formatea el tamaño de un archivo en bytes a formato legible
     * @param bytes Tamaño en bytes
     * @return Tamaño formateado (ej: "1.5 MB")
     */
    private static String formatearTamanio(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024.0));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0));
        }
    }
    
    /**
     * Verifica si existe un reporte específico
     * @param nombreArchivo Nombre del archivo
     * @return true si existe, false en caso contrario
     */
    public static boolean existeReporte(String nombreArchivo) {
        File archivo = new File(CARPETA_REPORTES, nombreArchivo);
        return archivo.exists();
    }
    
    /**
     * Lista todos los reportes en la carpeta de reportes
     * @return Array con nombres de archivos de reporte
     */
    public static String[] listarReportes() {
        File carpeta = new File(CARPETA_REPORTES);
        File[] archivos = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        
        if (archivos != null) {
            String[] nombres = new String[archivos.length];
            for (int i = 0; i < archivos.length; i++) {
                nombres[i] = archivos[i].getName();
            }
            return nombres;
        }
        
        return new String[0];
    }
    
    /**
     * Procesa un archivo de texto usando flujos de caracteres (Streams)
     * Convierte el contenido a minúsculas y guarda un nuevo archivo
     * @param nombreArchivoOriginal Nombre del archivo original
     * @param nombreArchivoProcesado Nombre del archivo procesado
     * @return true si se procesó exitosamente, false en caso contrario
     */
    public static boolean procesarArchivoConStreams(String nombreArchivoOriginal, String nombreArchivoProcesado) {
        String rutaOriginal = CARPETA_REPORTES + File.separator + nombreArchivoOriginal;
        String rutaProcesada = CARPETA_REPORTES + File.separator + nombreArchivoProcesado;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaOriginal));
             BufferedWriter writer = new BufferedWriter(new FileWriter(rutaProcesada))) {
            
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Procesar con flujos de caracteres: convertir a minúsculas
                String lineaProcesada = linea.toLowerCase();
                writer.write(lineaProcesada);
                writer.newLine();
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error al procesar archivo con streams: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Procesa archivo aplicando múltiples transformaciones con Streams
     * @param nombreArchivoOriginal Archivo fuente
     * @param nombreArchivoProcesado Archivo destino
     * @param transformaciones Array de transformaciones a aplicar
     * @return true si se procesó exitosamente
     */
    public static boolean procesarArchivoAvanzado(String nombreArchivoOriginal, 
                                                   String nombreArchivoProcesado,
                                                   String tipoTransformacion) {
        String rutaOriginal = CARPETA_REPORTES + File.separator + nombreArchivoOriginal;
        String rutaProcesada = CARPETA_REPORTES + File.separator + nombreArchivoProcesado;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaOriginal));
             PrintWriter writer = new PrintWriter(new FileWriter(rutaProcesada))) {
            
            // Usar Streams de Java 8+ para procesamiento
            reader.lines()
                  .map(linea -> {
                      switch (tipoTransformacion) {
                          case "minusculas":
                              return linea.toLowerCase();
                          case "mayusculas":
                              return linea.toUpperCase();
                          case "sin_espacios":
                              return linea.replaceAll("\\s+", "");
                          case "invertir":
                              return new StringBuilder(linea).reverse().toString();
                          default:
                              return linea.toLowerCase();
                      }
                  })
                  .forEach(writer::println);
            
            return true;
        } catch (IOException e) {
            System.err.println("Error al procesar archivo avanzado: " + e.getMessage());
            return false;
        }
    }
}