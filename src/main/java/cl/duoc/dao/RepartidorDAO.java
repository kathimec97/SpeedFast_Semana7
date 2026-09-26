package cl.duoc.dao;

import cl.duoc.model.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RepartidorDAO {

    public List<Repartidor> listarTodos() {

        ArrayList<Repartidor> listaRepartidores = new ArrayList<>();

        String sql = "SELECT * FROM repartidor";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Repartidor rep = new Repartidor();

                rep.setId(rs.getInt("id"));
                rep.setNombre(rs.getString("nombre"));

                listaRepartidores.add(rep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaRepartidores;
    }

}


