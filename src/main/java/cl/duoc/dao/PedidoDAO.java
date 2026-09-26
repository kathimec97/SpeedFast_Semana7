package cl.duoc.dao;

import cl.duoc.model.Pedido;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Data Access Object para la entidad Pedido.
 * Encargada de guardar los nuevos pedidos en la base de datos.
 *
 */
public class PedidoDAO {

    public void guardar(Pedido pedido) {

        String sql = "INSERT INTO pedido(direccion, tipo, estado) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipoPedido().name());
            stmt.setString(3, pedido.getEstadoPedido().name());

            stmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();

        }
    }

}
