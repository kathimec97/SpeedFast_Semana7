package cl.duoc.vista;

import cl.duoc.dao.RepartidorDAO;
import cl.duoc.model.Repartidor;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistrarRepartidor extends JFrame {

    private JLabel lblNombreRepartidor;
    private JTextField txtNombreRepartidor;
    private JButton btnRegistrarRepartidor;

    public VentanaRegistrarRepartidor() {

        this.setTitle("Registro de Repartidores");
        setSize(350, 120);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        JPanel panelRegistroRepartidor = new JPanel(new GridLayout(2, 2, 10, 10));
        panelRegistroRepartidor.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        JLabel lblNombreRepartidor = new JLabel("Nombre Repartidor:");
        txtNombreRepartidor = new JTextField();
        btnRegistrarRepartidor = new JButton("Registrar");

        panelRegistroRepartidor.add(lblNombreRepartidor);
        panelRegistroRepartidor.add(txtNombreRepartidor);
        panelRegistroRepartidor.add(new JLabel(""));
        panelRegistroRepartidor.add(btnRegistrarRepartidor);

        btnRegistrarRepartidor.addActionListener(e -> {
                guardarRepartidor();
        });

        setContentPane(panelRegistroRepartidor);

        setVisible(true);
    }

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

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaRegistrarRepartidor().setVisible(true);
        });
    }
}
