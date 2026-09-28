package cl.duoc.vista;

import cl.duoc.dao.EntregaDAO;
import cl.duoc.dao.PedidoDAO;
import cl.duoc.dao.RepartidorDAO;
import cl.duoc.model.Entrega;
import cl.duoc.model.Pedido;
import cl.duoc.model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VentanaEntrega extends JFrame {
    private JLabel lblPedido;
    private JLabel lblRepartidor;
    private JComboBox<String> comboPedidos;
    private JComboBox<String> comboRepartidores;
    private JButton botonAsignar;

    private JTable tablaEntregas;
    private DefaultTableModel modeloEntregas;
    private JButton botonActualizar;
    private JButton botonCerrar;

    public VentanaEntrega() throws SQLException {
        this.setTitle("Entrega de Pedido e Historial");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());


        JPanel panelEntrega = new JPanel(new GridLayout(3, 2, 10, 10));
        panelEntrega.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        lblPedido = new JLabel("Pedido:");
        comboPedidos = new JComboBox<>();

        lblRepartidor = new JLabel("Repartidor:");
        comboRepartidores = new JComboBox<>();

        botonAsignar = new JButton("Simular Envío");

        cargarPedidosPendientes();
        cargarRepartidores();

        panelEntrega.add(lblPedido);
        panelEntrega.add(comboPedidos);
        panelEntrega.add(lblRepartidor);
        panelEntrega.add(comboRepartidores);
        panelEntrega.add(new JLabel(""));
        panelEntrega.add(botonAsignar);

        botonAsignar.addActionListener(e -> {
            asignarEntrega();

        });

        add(panelEntrega, BorderLayout.NORTH);


        String[] columnas = {"ID Entrega", "ID Pedido", "ID Repartidor", "Fecha", "Hora"};
        modeloEntregas = new DefaultTableModel(columnas, 0);
        tablaEntregas = new JTable(modeloEntregas);

        JScrollPane scroll = new JScrollPane(tablaEntregas);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial de Entregas"));

        add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        botonActualizar = new JButton("Actualizar Tabla");
        botonCerrar = new JButton("Cerrar");

        panelBotones.add(botonActualizar);
        panelBotones.add(botonCerrar);

        botonActualizar.addActionListener(e -> {
            actualizarTabla();
        });

        botonCerrar.addActionListener(e -> {
            this.dispose();
        });

        add(panelBotones, BorderLayout.SOUTH);
        actualizarTabla();

        setVisible(true);

    }

    private void cargarPedidosPendientes() throws SQLException {
        comboPedidos.removeAllItems();
        PedidoDAO dao = new PedidoDAO();
        List<Pedido> pendientes = dao.listarPendientes();
        for (Pedido p : pendientes) {
            comboPedidos.addItem(p.getId() + " - " + p.getDireccionEntrega());
        }

    }

    private void cargarRepartidores() {
        comboRepartidores.removeAllItems();
        RepartidorDAO dao = new RepartidorDAO();
        List<Repartidor> repartidores = dao.listarTodos();
        for (Repartidor r : repartidores) {
            comboRepartidores.addItem(r.getId() + " - " + r.getNombre());
        }
    }

    private void asignarEntrega() {
        if (comboPedidos.getSelectedItem() == null || comboRepartidores.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, " No hay Pedidos o repartidores disponibles.");
            return;
        }

        try {
            String textoPedido = comboPedidos.getSelectedItem().toString();
            String textoRepartidor = comboRepartidores.getSelectedItem().toString();

            int idPedido = Integer.parseInt(textoPedido.split(" ")[0]);
            int idRepartidor = Integer.parseInt(textoRepartidor.split(" ")[0]);

            Pedido p = new Pedido();
            p.setId(idPedido);

            Repartidor r = new Repartidor();
            r.setId(idRepartidor);

            Entrega nuevaEntrega = new Entrega();
            nuevaEntrega.setPedido(p);
            nuevaEntrega.setRepartidor(r);
            nuevaEntrega.setFechaEntrega(LocalDate.now());
            nuevaEntrega.setHoraEntrega(LocalTime.now());

            EntregaDAO dao = new EntregaDAO();
            dao.guardar(nuevaEntrega);

            JOptionPane.showMessageDialog(this, "Entrega asignada correctamente.");

            actualizarTabla();
            cargarPedidosPendientes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, " Error al asignar la entrega: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modeloEntregas.setRowCount(0);

        try {
            EntregaDAO dao = new EntregaDAO();
            List<Entrega> lista = dao.listarTodas();

            for (Entrega e : lista) {

                modeloEntregas.addRow(new Object[]{
                        e.getIdEntrega(),
                        e.getPedido().getId(),
                        e.getRepartidor().getId(),
                        e.getFechaEntrega(),
                        e.getHoraEntrega()

                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la entrega: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    }

