package com.dgit.scp.ui;

import com.dgit.scp.util.FileManager;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class GestionReportes extends JFrame {

    private MenuPrincipal menuPrincipal;

    private JTextField txtNombreProyecto;
    private JTextField txtResponsable;
    private JTextArea txtDescripcionAvance;

    private JButton btnGuardarReporte;
    private JButton btnProcesarDocumento;  // Renombrado para coincidir con "procesar documento"
    private JButton btnSalir;

    private static final String MATRICULA = "ES231110272";

    public GestionReportes(MenuPrincipal menuPrincipal) {
        this.menuPrincipal = menuPrincipal;
        initComponents();
        configurarVentana();
    }

    private void initComponents() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);

        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 152, 219));
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel lblTitulo = new JLabel("Gestión de Reportes de Proyecto");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel lblNombre = new JLabel("Nombre del Proyecto:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        panelFormulario.add(lblNombre, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtNombreProyecto = new JTextField(30);
        txtNombreProyecto.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtNombreProyecto, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel lblResponsable = new JLabel("Responsable del Proyecto:");
        lblResponsable.setFont(new Font("Arial", Font.BOLD, 13));
        panelFormulario.add(lblResponsable, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        txtResponsable = new JTextField(30);
        txtResponsable.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtResponsable, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel lblDescripcion = new JLabel("Descripción del Avance Semanal:");
        lblDescripcion.setFont(new Font("Arial", Font.BOLD, 13));
        panelFormulario.add(lblDescripcion, gbc);

        gbc.gridx = 1; gbc.weightx = 0.7; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        txtDescripcionAvance = new JTextArea(8, 30);
        txtDescripcionAvance.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDescripcionAvance.setLineWrap(true);
        txtDescripcionAvance.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcionAvance);
        scrollDescripcion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelFormulario.add(scrollDescripcion, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);

        btnGuardarReporte = crearBoton("Guardar Reporte", new Color(46, 204, 113));
        btnProcesarDocumento = crearBoton("Procesar documento", new Color(155, 89, 182));  // Renombrado, y removido el otro botón
        btnSalir = crearBoton("Salir", new Color(231, 76, 60));

        panelBotones.add(btnGuardarReporte);
        panelBotones.add(btnProcesarDocumento);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
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
        boton.setPreferredSize(new Dimension(160, 40));
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

        btnGuardarReporte.addActionListener(e -> guardarReporte());

        btnProcesarDocumento.addActionListener(e -> procesarArchivo());  // Ahora es este el que procesa con streams

        btnSalir.addActionListener(e -> {
            menuPrincipal.setVisible(true);
            this.dispose();
        });
    }

    private void guardarReporte() {
        String nombre = txtNombreProyecto.getText().trim();
        String responsable = txtResponsable.getText().trim();
        String descripcion = txtDescripcionAvance.getText().trim();

        if (nombre.isEmpty() || responsable.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, complete todos los campos antes de guardar.",
                "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder contenido = new StringBuilder();
        contenido.append("========================================\n");
        contenido.append("  REPORTE DE AVANCE DE PROYECTO\n");
        contenido.append("  DGIT - Ciudad Futura\n");
        contenido.append("========================================\n\n");
        contenido.append("Nombre del Proyecto: ").append(nombre).append("\n");
        contenido.append("Responsable: ").append(responsable).append("\n");
        contenido.append("Fecha de registro: ").append(LocalDateTime.now()).append("\n\n");
        contenido.append("Descripción del Avance Semanal:\n");
        contenido.append(descripcion).append("\n\n");
        contenido.append("========================================\n");
        contenido.append("Matrícula: ").append(MATRICULA).append("\n");
        contenido.append("========================================\n\n");  // Agregado un salto extra para separar reportes

        String nombreArchivo = "proyecto_" + MATRICULA + "_original.txt";
        boolean guardado = FileManager.guardarReporte(nombreArchivo, contenido.toString());

        if (guardado) {
            JOptionPane.showMessageDialog(this,
                "Reporte agregado exitosamente en:\nreportes/" + nombreArchivo,
                "Reporte guardado", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al guardar el reporte. Verifique los permisos de escritura.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarArchivo() {
        String archivoOriginal = "proyecto_" + MATRICULA + "_original.txt";
        String archivoProcesado = "proyecto_" + MATRICULA + "_procesado.txt";

        if (!FileManager.existeReporte(archivoOriginal)) {
            JOptionPane.showMessageDialog(this,
                "No existe el archivo original.\nPrimero debe guardar un reporte.",
                "Archivo no encontrado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean procesado = FileManager.procesarArchivoAvanzado(
            archivoOriginal,
            archivoProcesado,
            "minusculas"
        );

        if (procesado) {
            JOptionPane.showMessageDialog(this,
                "Archivo procesado exitosamente con Streams.\n\n" +
                "Transformación aplicada: Conversión a minúsculas\n" +
                "Archivo original: " + archivoOriginal + "\n" +
                "Archivo procesado: " + archivoProcesado + "\n\n" +
                "Ubicación: reportes/",
                "Procesamiento completado", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Error al procesar el archivo con Streams.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombreProyecto.setText("");
        txtResponsable.setText("");
        txtDescripcionAvance.setText("");
        txtNombreProyecto.requestFocus();
    }

    private void configurarVentana() {
        setTitle("SCP - Gestión de Reportes");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(750, 550);
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