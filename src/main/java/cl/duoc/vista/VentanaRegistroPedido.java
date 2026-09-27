package cl.duoc.vista;

import cl.duoc.dao.PedidoDAO;
import cl.duoc.model.EstadoPedido;
import cl.duoc.model.Pedido;
import cl.duoc.model.TipoPedido;
import cl.duoc.model.ZonaDeCarga;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaRegistroPedido extends JFrame {
    private Pedido pedido;
    private JLabel lblDireccion;
    private JLabel lblTipo;
    private JTextField txtDireccion;
    private JComboBox comboTipo;
    private JButton btnGuardar;
    private JTable tblPedido;
    private DefaultTableModel modeloPedido;
    private JButton btnActualizar;

    public VentanaRegistroPedido() {

        this.setTitle("Registro de pedidos");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel panelRegistro = new JPanel(new GridLayout(3, 2, 10, 10));
        panelRegistro.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        JLabel lblDireccion = new JLabel("Direccion");
        JLabel lblTipo = new JLabel("Tipo");


        txtDireccion = new JTextField();
        comboTipo = new JComboBox<>(TipoPedido.values());
        btnGuardar = new JButton("Guardar");


        panelRegistro.add(lblDireccion);
        panelRegistro.add(txtDireccion);
        panelRegistro.add(lblTipo);
        panelRegistro.add(comboTipo);
        panelRegistro.add(new JLabel(""));
        panelRegistro.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            guardarPedido();
        });

        add(panelRegistro, BorderLayout.NORTH);

        String[] columnas = {"Id", "Dirección", "Tipo", "Estado"};
        modeloPedido = new DefaultTableModel(columnas, 0);
        tblPedido = new JTable(modeloPedido);

        JScrollPane scroll = new JScrollPane(tblPedido);
        scroll.setBorder(BorderFactory.createTitledBorder("Pedidos Registrados"));


        add(scroll, BorderLayout.CENTER);

        btnActualizar = new JButton("Actualizar");
        JPanel panelActualizar = new JPanel();
        panelActualizar.add(btnActualizar);

        btnActualizar.addActionListener(e -> {
            actualizarTabla();
        });
        add(panelActualizar, BorderLayout.SOUTH);

        actualizarTabla();

        setVisible(true);

    }

    public void guardarPedido() {
        String direccion = txtDireccion.getText().trim();
        TipoPedido tipo = (TipoPedido) comboTipo.getSelectedItem();

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos");
            return;

        }


        Pedido nuevo = new Pedido(0, direccion, EstadoPedido.PENDIENTE, tipo);


        try {
            PedidoDAO dao = new PedidoDAO();
            dao.guardar(nuevo);


            JOptionPane.showMessageDialog(this, "Pedido agregado correctamente");

            txtDireccion.setText("");
            comboTipo.setSelectedIndex(0);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        modeloPedido.setRowCount(0);

        try{
            PedidoDAO dao = new PedidoDAO();

            List<Pedido> lista = dao.listarTodos();

            for (Pedido pedido : lista) {

                modeloPedido.addRow(new Object[]{pedido.getId(), pedido.getDireccionEntrega(), pedido.getTipoPedido(), pedido.getEstadoPedido()});

            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la tabla: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaRegistroPedido().setVisible(true);
        });
    }




}