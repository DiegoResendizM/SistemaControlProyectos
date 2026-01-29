package com.dgit.scp.ui;

import com.dgit.scp.util.FileManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class GestionDocumentos extends JFrame {
    
    private MenuPrincipal menuPrincipal;
    
    private JTable tablaDocumentos;
    private DefaultTableModel modeloTabla;
    
    private JButton btnCargarPDF;
    private JButton btnAbrirPDF;
    private JButton btnEliminarPDF;
    private JButton btnActualizar;
    private JButton btnSalir;
    
    public GestionDocumentos(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        initComponents();
        configurarVentana();
        cargarListaDocumentos();
    }
    
    private void initComponents() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(46, 204, 113));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitulo = new JLabel("Gestión de Documentos PDF");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        JPanel panelTabla = new JPanel(new BorderLayout(5, 5));
        panelTabla.setBackground(Color.WHITE);
        
        JLabel lblInstrucciones = new JLabel("Documentos almacenados:");
        lblInstrucciones.setFont(new Font("Arial", Font.BOLD, 13));
        panelTabla.add(lblInstrucciones, BorderLayout.NORTH);

        String[] columnas = {"Nombre del Archivo", "Tamaño", "Fecha de Carga"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaDocumentos = new JTable(modeloTabla);
        tablaDocumentos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaDocumentos.setRowHeight(25);
        tablaDocumentos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaDocumentos.getTableHeader().setBackground(new Color(52, 152, 219));
        tablaDocumentos.getTableHeader().setForeground(Color.WHITE);
        tablaDocumentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollTabla = new JScrollPane(tablaDocumentos);
        scrollTabla.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnCargarPDF = crearBoton("Cargar PDF", new Color(52, 152, 219));
        btnAbrirPDF = crearBoton("Abrir PDF", new Color(46, 204, 113));
        btnEliminarPDF = crearBoton("Eliminar PDF", new Color(231, 76, 60));
        btnActualizar = crearBoton("Actualizar Lista", new Color(241, 196, 15));
        btnSalir = crearBoton("Salir", new Color(149, 165, 166));
        
        panelBotones.add(btnCargarPDF);
        panelBotones.add(btnAbrirPDF);
        panelBotones.add(btnEliminarPDF);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);

        configurarAcciones();
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setPreferredSize(new Dimension(140, 35));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            Color colorOriginal = color;
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorOriginal.brighter());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorOriginal);
            }
        });
        
        return boton;
    }
    
    private void configurarAcciones() {

        btnCargarPDF.addActionListener(e -> cargarPDF());

        btnAbrirPDF.addActionListener(e -> abrirPDF());

        btnEliminarPDF.addActionListener(e -> eliminarPDF());

        btnActualizar.addActionListener(e -> cargarListaDocumentos());

        btnSalir.addActionListener(e -> {
            menuPrincipal.setVisible(true);
            this.dispose();
        });
    }
    
    private void cargarPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar documento PDF");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos PDF", "pdf"));
        
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();

            if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
                JOptionPane.showMessageDialog(
                    this,
                    "Por favor, seleccione un archivo PDF válido.",
                    "Formato inválido",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            boolean copiado = FileManager.copiarDocumento(archivo);
            
            if (copiado) {
                JOptionPane.showMessageDialog(
                    this,
                    "Documento cargado exitosamente:\n" + archivo.getName(),
                    "Documento cargado",
                    JOptionPane.INFORMATION_MESSAGE
                );
                cargarListaDocumentos();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar el documento. Verifique que el archivo no esté en uso.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void abrirPDF() {
        int filaSeleccionada = tablaDocumentos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor, seleccione un documento de la lista.",
                "Sin selección",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String nombreArchivo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        boolean abierto = FileManager.abrirDocumento(nombreArchivo);
        
        if (!abierto) {
            JOptionPane.showMessageDialog(
                this,
                "No se pudo abrir el documento.\nVerifique que tenga un lector de PDF instalado.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void eliminarPDF() {
        int filaSeleccionada = tablaDocumentos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor, seleccione un documento de la lista.",
                "Sin selección",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String nombreArchivo = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro que desea eliminar el documento?\n" + nombreArchivo,
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = FileManager.eliminarDocumento(nombreArchivo);
            
            if (eliminado) {
                JOptionPane.showMessageDialog(
                    this,
                    "Documento eliminado exitosamente.",
                    "Eliminación exitosa",
                    JOptionPane.INFORMATION_MESSAGE
                );
                cargarListaDocumentos();
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el documento.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private void cargarListaDocumentos() {

        modeloTabla.setRowCount(0);

        List<String[]> documentos = FileManager.listarDocumentos();

        for (String[] doc : documentos) {
            modeloTabla.addRow(doc);
        }

        if (documentos.isEmpty()) {
            modeloTabla.addRow(new String[]{"No hay documentos cargados", "", ""});
        }
    }
    
    private void configurarVentana() {
        setTitle("SCP - Gestión de Documentos PDF");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                menuPrincipal.setVisible(true);
                dispose();
            }
        });
    }
}