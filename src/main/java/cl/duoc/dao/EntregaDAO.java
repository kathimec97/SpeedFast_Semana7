package cl.duoc.dao;

import cl.duoc.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EntregaDAO {

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
               int fila =  stmtUpdate.executeUpdate();

               if(fila == 0){
                   throw new Exception("El pedido ID " + entrega.getPedido().getId() + " no existe en la tabla o la columna de ID es incorrecta.");
               }
            }

        }

        }



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