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

/**
 * Clase que modela la interfaz gráfica para las entregas:
 * <p>
 * Entrega una ventana con la opción de seleccionar un pedido de una lista desplegable de Pendientes y
 * un repartidor que tiene su propia lista desplegable. Al seleccionar el boton 'simular Envío' se registra
 * la entrega en la base de datos.
 * Adicionalmente, cuenta con un boton para actualizar la tabla
 * que estamos viendo en la interfaz y un boton para cerrar la ventana.
 *
 * @author Katherine
 */
public class VentanaEntrega extends JFrame {
    private  JLabel lblPedido;
    private  JLabel lblRepartidor;
    private  JComboBox<String> comboPedidos;
    private  JComboBox<String> comboRepartidores;
    private  JButton botonAsignar;

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

        botonAsignar = new JButton("simular Envío");

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

    /**
     * Permite visualizar y seleccionar los pedidos pendientes mediante una lista desplegable.
     *
     * @throws SQLException
     */
    private void cargarPedidosPendientes() throws SQLException {
        comboPedidos.removeAllItems();
        PedidoDAO dao = new PedidoDAO();
        List<Pedido> pendientes = dao.listarPendientes();
        for (Pedido p : pendientes) {
            comboPedidos.addItem(p.getId() + " - " + p.getDireccionEntrega());
        }

    }

    /**
     * Permite visualizar y seleccionar los repartidores guardados mediante una lista desplegable.
     */
    private void cargarRepartidores() {
        comboRepartidores.removeAllItems();
        RepartidorDAO dao = new RepartidorDAO();
        List<Repartidor> repartidores = dao.listarTodos();
        for (Repartidor r : repartidores) {
            comboRepartidores.addItem(r.getId() + " - " + r.getNombre());
        }
    }

    /**
     * Verifica que se seleccione un pedido y un repartidor y genera un objeto entrega para guardar.
     */
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

    /**
     * Actualiza visualmente la tabla con el historial de entregas.
     */
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

