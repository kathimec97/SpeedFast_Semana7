package cl.duoc.vista;

import cl.duoc.dao.RepartidorDAO;
import cl.duoc.model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Clase que modela la interfaz gráfica de los repartidores:
 * <p>
 * Entrega una ventana con un campo para escribir el nombre del repartidor y un botón registrar,
 * adicionalmente muestra una tabla con la lista de los repartidores registrados,
 * cuenta con un botón actualizar para refrescar la tabla.
 */
public class VentanaRegistrarRepartidor extends JFrame {

    private JLabel lblNombreRepartidor;
    private JTextField txtNombreRepartidor;
    private JButton btnRegistrarRepartidor;
    private JButton btnActualizar;

    private JTable tblRepartidores;
    private DefaultTableModel modeloRepartidores;

    public VentanaRegistrarRepartidor() {

        this.setTitle("Registro de Repartidores");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel panelRegistroRepartidor = new JPanel(new GridLayout(2, 2, 10, 10));
        panelRegistroRepartidor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JPanel panelActualizar = new JPanel();


        lblNombreRepartidor = new JLabel("Nombre Repartidor:");
        txtNombreRepartidor = new JTextField();
        btnRegistrarRepartidor = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");

        panelRegistroRepartidor.add(lblNombreRepartidor);
        panelRegistroRepartidor.add(txtNombreRepartidor);
        panelRegistroRepartidor.add(new JLabel(""));
        panelRegistroRepartidor.add(btnRegistrarRepartidor);
        panelActualizar.add(btnActualizar);

        btnActualizar.addActionListener(
                actionEvent -> {
                    actualizarTabla();
                }
        );

        btnRegistrarRepartidor.addActionListener(e -> {
            guardarRepartidor();
        });

        add(panelRegistroRepartidor, BorderLayout.NORTH);
        add(panelActualizar, BorderLayout.SOUTH);

        String[] columnas = {"Id", "Nombre"};
        modeloRepartidores = new DefaultTableModel(columnas, 0);
        tblRepartidores = new JTable(modeloRepartidores);

        JScrollPane scrollPane = new JScrollPane(tblRepartidores);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Repartidores Registrados"));

        add(scrollPane, BorderLayout.CENTER);

        actualizarTabla();

        setVisible(true);
    }

    /**
     * Obtiene el nombre del repartidor a registrar y crea un objeto repartidor para guardar en la base de datos.
     *
     */
    public void guardarRepartidor() {
        String nombreR = txtNombreRepartidor.getText().trim();


        if (nombreR.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete el campo Nombre Repartidor");
            return;

        }


        Repartidor nuevo = new Repartidor();
        nuevo.setNombre(nombreR);

        try {
            RepartidorDAO dao = new RepartidorDAO();
            dao.guardarRepartidor(nuevo);


            JOptionPane.showMessageDialog(this, "Repartidor agregado correctamente");

            txtNombreRepartidor.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    /**
     * Actualiza la vista de la tabla de repartidores en la ventana.
     */
    private void actualizarTabla() {
        modeloRepartidores.setRowCount(0);

        try {
            RepartidorDAO dao = new RepartidorDAO();
            List<Repartidor> lista = dao.listarTodos();

            for (Repartidor r : lista) {

                modeloRepartidores.addRow(new Object[]{r.getId(), r.getNombre()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la tabla: " + e.getMessage());
        }
    }

}
