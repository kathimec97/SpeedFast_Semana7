package cl.duoc.dao;

import cl.duoc.model.EstadoPedido;
import cl.duoc.model.Pedido;
import cl.duoc.model.TipoPedido;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para la entidad Pedido.
 * Encargada de guardar los nuevos pedidos en la base de datos, listar todos los pedidos
 * o listar solo los pedidos pendientes para entregar.
 *
 * @author Katherine
 *
 */
public class PedidoDAO {

    /**
     * Guarda un nuevo pedido en la base de datos
     *
     * @param pedido El objeto con los datos del pedido y el repartidor.
     */
    public void guardar(Pedido pedido) {

        String sql = "INSERT INTO pedido(direccion, tipo, estado) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccionEntrega());
            stmt.setString(2, pedido.getTipoPedido().name());
            stmt.setString(3, pedido.getEstadoPedido().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }


    /**
     * Lista los pedidos con estado PENDIENTE de envío
     *
     * @return una lista Pedido pendiente para su selección y envío
     * @throws SQLException si ocurre un error con la base de datos
     */
    public List<Pedido> listarPendientes() throws SQLException {
        List<Pedido> pendientes = new ArrayList<>();

        String sql = "SELECT id, direccion FROM pedido where estado = 'PENDIENTE'";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("Id"));
                p.setDireccionEntrega(rs.getString("direccion"));

                pendientes.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendientes;
    }

    /**
     * Lista todos los pedidos en la base de datos
     *
     * @return una lista general de pedidos registrados
     * @throws SQLException
     */
    public List<Pedido> listarTodos() throws SQLException {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT * FROM pedido";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("Id"));
                p.setDireccionEntrega(rs.getString("direccion"));
                p.setTipoPedido(TipoPedido.valueOf(rs.getString("tipo")));
                p.setEstadoPedido(EstadoPedido.valueOf(rs.getString("estado")));
                pedidos.add(p);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }


}


