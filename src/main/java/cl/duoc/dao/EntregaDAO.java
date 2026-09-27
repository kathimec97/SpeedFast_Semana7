package cl.duoc.dao;

import cl.duoc.model.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class EntregaDAO {

public void guardar(Entrega entrega){
    String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?,?,?,?)";

    try(Connection conn = ConexionDB.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)) {

        statement.setInt(1, entrega.getPedido().getId());
        statement.setInt(2, entrega.getRepartidor().getId());

        statement.setDate(3, java.sql.Date.valueOf(entrega.getFechaEntrega()));
        statement.setTime(4, java.sql.Time.valueOf(entrega.getHoraEntrega()));

        statement.executeUpdate();
    }catch (SQLException e) {
        e.printStackTrace();
    }


}
}
