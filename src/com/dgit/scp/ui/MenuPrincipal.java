package com.dgit.scp.ui;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    
    private JButton btnGestionReportes;
    private JButton btnGestionDocumentos;
    private JButton btnSalir;
    
    public MenuPrincipal() {
        initComponents();
        configurarVentana();
    }
    
    private void initComponents() {

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panelPrincipal.setBackground(new Color(240, 240, 245));

        JLabel lblTitulo = new JLabel("Sistema de Control de Proyectos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setForeground(new Color(51, 51, 51));

        JLabel lblSubtitulo = new JLabel("DGIT - Ciudad Futura");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setForeground(new Color(102, 102, 102));

        btnGestionReportes = crearBoton("Gestión de Reportes de Proyecto", new Color(52, 152, 219));
        btnGestionDocumentos = crearBoton("Gestión de Documentos PDF", new Color(46, 204, 113));
        btnSalir = crearBoton("Salir", new Color(231, 76, 60));

        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(lblSubtitulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 40)));
        panelPrincipal.add(btnGestionReportes);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(btnGestionDocumentos);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(btnSalir);

        add(panelPrincipal);

        configurarAcciones();
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(350, 50));
        boton.setPreferredSize(new Dimension(350, 50));
        boton.setBorderPainted(false);
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

        btnGestionReportes.addActionListener(e -> {
            GestionReportes ventanaReportes = new GestionReportes(this);
            ventanaReportes.setVisible(true);
            this.setVisible(false);
        });

        btnGestionDocumentos.addActionListener(e -> {
            GestionDocumentos ventanaDocumentos = new GestionDocumentos(this);
            ventanaDocumentos.setVisible(true);
            this.setVisible(false);
        });

        btnSalir.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (respuesta == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
    
    private void configurarVentana() {
        setTitle("SCP - Sistema de Control de Proyectos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}