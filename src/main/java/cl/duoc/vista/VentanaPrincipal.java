package cl.duoc.vista;


import cl.duoc.model.Repartidor;
import cl.duoc.model.ZonaDeCarga;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JButton botonRegistrarPedido;
    private JButton botonListaPedidos;
    private JButton botonAsignarRepartidor;
    private final ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

    public VentanaPrincipal() {
        setTitle("Ventana Principal-SpeedFast");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        botonRegistrarPedido = new JButton("Registrar pedido");
        botonListaPedidos = new JButton("Lista de pedidos");
        botonAsignarRepartidor = new JButton("Asignar repartidor/Iniciar Reparto");

        panelBotones.add(botonRegistrarPedido);
        panelBotones.add(botonListaPedidos);
        panelBotones.add(botonAsignarRepartidor);
        setVisible(true);

        add(panelBotones, BorderLayout.CENTER);

        botonRegistrarPedido.addActionListener(e -> {
            VentanaRegistroPedido registro = new VentanaRegistroPedido(this.zonaDeCarga);
            registro.setVisible(true);
        });

        botonListaPedidos.addActionListener(e -> {
          VentanaListaPedidos listado = new VentanaListaPedidos(this.zonaDeCarga);
         listado.setVisible(true);
        });

        botonAsignarRepartidor.addActionListener(e -> {
            if(zonaDeCarga.getPedidosPendientes().isEmpty()){
                JOptionPane.showMessageDialog(this, "No hay Pedidos Pendientes para despachar",
                        "Sin Pedidos",
                        JOptionPane.WARNING_MESSAGE);
                        return;
            }
            Thread repartidor1 = new Thread(new Repartidor("PatrickJ", zonaDeCarga), "PatrickJ");
            Thread repartidor2 = new Thread(new Repartidor("JeanL", zonaDeCarga), "JeanL");
            Thread repartidor3 = new Thread(new Repartidor("ChandlerB", zonaDeCarga), "ChandlerB");
            Thread repartidor4 = new Thread(new Repartidor("AtticusF", zonaDeCarga), "AtticusF");

            repartidor1.start();
            repartidor2.start();
            repartidor3.start();
            repartidor4.start();

            JOptionPane.showMessageDialog(this, "¡Reparto iniciado con exito!" + "\n" + "Reparto en curso...");

        });


    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }



}
