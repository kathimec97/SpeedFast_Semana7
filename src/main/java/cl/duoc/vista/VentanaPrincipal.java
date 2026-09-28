package cl.duoc.vista;


import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * Clase que modela la interfaz gráfica de la Ventana Principal del Sistema:
 * <p>
 * Contiene tres botones que permiten gestionar cada parte del sistema Speedfast:
 * Pedidos: abre la 'VentanaRegistroPedidos.java'
 * Repartidores: abre la 'VentanaRegistrarRepartidores.java'
 * Entregas: abre la 'VentanaEntrega.java'
 */
public class VentanaPrincipal extends JFrame {

    private JButton botonPedidos;
    private JButton botonRepartidores;
    private JButton botonEntregas;

    public VentanaPrincipal() {
        setTitle("Ventana Principal-SpeedFast");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        botonPedidos = new JButton("Pedidos");
        botonRepartidores = new JButton("Repartidores");
        botonEntregas = new JButton("Entregas");

        panelBotones.add(botonPedidos);
        panelBotones.add(botonRepartidores);
        panelBotones.add(botonEntregas);
        setVisible(true);

        add(panelBotones, BorderLayout.CENTER);

        botonPedidos.addActionListener(e -> {
            VentanaRegistroPedido registroP = new VentanaRegistroPedido();
            registroP.setVisible(true);
        });

        botonRepartidores.addActionListener(e -> {
            VentanaRegistrarRepartidor RegistrarR = new VentanaRegistrarRepartidor();
            RegistrarR.setVisible(true);
        });


        botonEntregas.addActionListener(e -> {
            VentanaEntrega entrega = null;
            try {
                entrega = new VentanaEntrega();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            entrega.setVisible(true);
        });


    }

}
