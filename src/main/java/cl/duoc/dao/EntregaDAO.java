package cl.duoc.dao;

import cl.duoc.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de manejar la base de datos para las entregas
 *
 * Permite guardar nuevas entregas con su repartidor, fecha y hora
 * @author Katherine
 */
public class EntregaDAO {

    /**
     * Guarda nuevas entregas, específicamente el ID del pedido, el ID del repartidor,
     * la fecha y la hora de entrega.
     * Actualiza la tabla Pedido para que al guardar una entrega el pedido "entregado" pase de estado PENDIENTE
     * a ENTREGADO.
     *
     * @param entrega el objeto que se crea a partir de los datos de laa diferentes tablas.
     * @throws Exception en caso de un error con la base de datos
     */
    public void guardar(Entrega entrega) throws Exception {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?,?,?,?)";

        String sqlUpdate = " UPDATE pedido SET estado = 'ENTREGADO' WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection()) {

            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, entrega.getPedido().getId());
                statement.setInt(2, entrega.getRepartidor().getId());

                statement.setDate(3, java.sql.Date.valueOf(entrega.getFechaEntrega()));
                statement.setTime(4, java.sql.Time.valueOf(entrega.getHoraEntrega()));

                statement.executeUpdate();

            }

            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setInt(1, entrega.getPedido().getId());
                int fila = stmtUpdate.executeUpdate();

                if (fila == 0) {
                    throw new Exception("El pedido ID " + entrega.getPedido().getId() + " no existe en la tabla o la columna de ID es incorrecta.");
                }
            }

        }

    }


    /**
     * Lista todas las entregas de la base de datos
     *
     * @return una lista general de entregas
     */

    public List<Entrega> listarTodas() {
        ArrayList<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entrega";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id_pedido"));

                Repartidor repartidor = new Repartidor();
                repartidor.setId(rs.getInt("id_repartidor"));

                Entrega entrega = new Entrega();
                entrega.setIdEntrega(rs.getInt("id"));
                entrega.setPedido(pedido);
                entrega.setRepartidor(repartidor);

                entrega.setFechaEntrega(rs.getDate("fecha").toLocalDate());
                entrega.setHoraEntrega(rs.getTime("hora").toLocalTime());

                lista.add(entrega);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}