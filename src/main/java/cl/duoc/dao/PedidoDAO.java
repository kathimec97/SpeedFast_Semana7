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
            }catch(SQLException e) {
            e.printStackTrace();
            }
            return pendientes;
        }

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
            }catch(SQLException e) {
                e.printStackTrace();
            }
            return pedidos;
        }


}


