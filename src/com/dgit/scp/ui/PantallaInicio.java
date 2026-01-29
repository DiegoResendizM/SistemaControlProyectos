package com.dgit.scp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PantallaInicio extends JFrame {

    private JButton btnIngresar;
    private JProgressBar progressBar;
    private Timer timer;
    private int progreso = 0;

    public PantallaInicio() {
        initComponents();
        configurarVentana();
        procesarDatosConStreams();
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(41, 128, 185),
                        0, getHeight(), new Color(109, 213, 250)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel lblIcono = new JLabel();
        try {

            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/resources/images/locoscp.png"));

            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

            lblIcono.setIcon(iconoEscalado);
        } catch (Exception e) {

            lblIcono.setText("SCP");
            lblIcono.setFont(new Font("Arial", Font.BOLD, 60));
            lblIcono.setForeground(Color.WHITE);
            System.err.println("No se pudo cargar la imagen: " + e.getMessage());
        }
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitulo = new JLabel("Sistema de Control de Proyectos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Dirección General de Innovación y Tecnología");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setForeground(new Color(236, 240, 241));

        JLabel lblCiudad = new JLabel("Ciudad Futura");
        lblCiudad.setFont(new Font("Arial", Font.BOLD, 14));
        lblCiudad.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCiudad.setForeground(new Color(236, 240, 241));

        Component rigidArea1 = Box.createRigidArea(new Dimension(0, 40));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setPreferredSize(new Dimension(350, 25));
        progressBar.setMaximumSize(new Dimension(350, 25));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setForeground(new Color(46, 204, 113));

        JLabel lblCargando = new JLabel("Procesando datos con Streams...");
        lblCargando.setFont(new Font("Arial", Font.ITALIC, 12));
        lblCargando.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCargando.setForeground(Color.WHITE);

        btnIngresar = new JButton("INGRESAR AL SISTEMA");
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));
        btnIngresar.setBackground(new Color(46, 204, 113));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setPreferredSize(new Dimension(250, 45));
        btnIngresar.setMaximumSize(new Dimension(250, 45));
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIngresar.setVisible(false);

        JLabel lblMatricula = new JLabel("Matrícula: ES231110272");
        lblMatricula.setFont(new Font("Courier New", Font.BOLD, 11));
        lblMatricula.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMatricula.setForeground(new Color(236, 240, 241));

        panelPrincipal.add(lblIcono);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(lblSubtitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        panelPrincipal.add(lblCiudad);
        panelPrincipal.add(rigidArea1);
        panelPrincipal.add(progressBar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(lblCargando);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        panelPrincipal.add(btnIngresar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPrincipal.add(lblMatricula);

        add(panelPrincipal);

        btnIngresar.addActionListener(e -> abrirMenuPrincipal());

        btnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIngresar.setBackground(new Color(39, 174, 96));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIngresar.setBackground(new Color(46, 204, 113));
            }
        });
    }

    private void procesarDatosConStreams() {

        java.util.List<String> modulos = java.util.Arrays.asList(
                "GESTIÓN DE REPORTES DE PROYECTO",
                "GESTIÓN DE DOCUMENTOS PDF",
                "SISTEMA DE ARCHIVOS",
                "CONTROL DE ACCESOS",
                "BASE DE DATOS"
        );

        String resultado = modulos.stream()
                .map(String::toLowerCase)
                .map(s -> s.replace("ó", "o").replace("í", "i"))
                .reduce("", (a, b) -> a + b + " | ");

        System.out.println("Datos procesados con Streams: " + resultado);

        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progreso += 2;
                progressBar.setValue(progreso);

                if (progreso >= 100) {
                    timer.stop();
                    btnIngresar.setVisible(true);
                    progressBar.setString("Sistema listo ✓");
                }
            }
        });
        timer.start();
    }

    private void abrirMenuPrincipal() {
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(true);
        this.dispose();
    }

    private void configurarVentana() {
        setTitle("SCP - Bienvenida");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
