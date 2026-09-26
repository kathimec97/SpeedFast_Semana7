package cl.duoc.vista;

import cl.duoc.model.Pedido;
import cl.duoc.model.ZonaDeCarga;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class VentanaListaPedidos extends JFrame {
    private ZonaDeCarga zonaDeCarga;
    private JTable tablaPedidos;
    private JScrollPane jspTabla;
    private DefaultTableModel modelo;
    private JButton btnActualizar;

    public VentanaListaPedidos(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;
        setTitle("Listado de pedidos");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panelListado = new JPanel(new BorderLayout(0,15));
        panelListado.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inicializarTabla();
        cargarPedidos();

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarPedidos());


        panelListado.add(jspTabla, BorderLayout.CENTER);
        panelListado.add(btnActualizar, BorderLayout.SOUTH);

        setContentPane(panelListado);

        setVisible(true);


    }

    private void inicializarTabla() {

        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modelo = new DefaultTableModel(columnas, 0);

        tablaPedidos = new JTable(modelo);

        tablaPedidos.setModel(modelo);
        tablaPedidos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        jspTabla = new JScrollPane(tablaPedidos);


    }

    public void cargarPedidos(){
        if (zonaDeCarga == null || zonaDeCarga.getPedidosPendientes() == null) {
            return;
        }

        modelo.setRowCount(0);

        for(Pedido p : zonaDeCarga.getPedidosPendientes()){
            Object[] fila = {
                    p.getId(),
                    p.getDireccionEntrega(),
                    p.getTipoPedido(),
                    p.getEstadoPedido()
            };
            modelo.addRow(fila);
        }
    }


    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaListaPedidos(null).setVisible(true);
        });
    }

}