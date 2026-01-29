package com.dgit.scp;

import com.dgit.scp.ui.PantallaInicio;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainApp {
    
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.err.println("No se pudo establecer el Look and Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            PantallaInicio pantallaInicio = new PantallaInicio();
            pantallaInicio.setVisible(true);
        });
    }
}
