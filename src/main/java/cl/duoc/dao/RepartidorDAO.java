package cl.duoc.dao;

import cl.duoc.model.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de manejar la base de datos para los Repartidores.
 *
 * Permite listar todos los repartidores y registrar nuevos
 * @author Katherine
 */

public class RepartidorDAO {

    /**
     * Lista todos los repartidores en la base de datos
     * @return una lista general de repartidores
     */
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

    /**
     * Guarda los repartidores nuevos en la base de datos
     * @param repartidor el objeto repartidor con ID y su nombre.
     */
    public void guardarRepartidor(Repartidor repartidor) {
        String sql = "INSERT INTO repartidor (nombre) VALUES (?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, repartidor.getNombre());


            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
