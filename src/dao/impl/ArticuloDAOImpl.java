package dao.impl;
import dao.interfaces.ArticuloDAO;
import model.Articulo;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAOImpl implements ArticuloDAO {

    @Override
    public void anadirArticulo(Articulo articulo) throws Exception {
        // Lógica JDBC para añadir articulo
    }

    @Override
    public Articulo getArticuloPorCodigo(String codigo) throws Exception {
        String sql = "SELECT * FROM articulos WHERE codigo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion")
                );
            }
        }
        return null; // si no encuentra el artículo
    }

    @Override
    public List<Articulo> getTodosLosArticulos() throws Exception {
        List<Articulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM articulos";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Articulo articulo = new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion")
                );
                lista.add(articulo);
            }
        }
        return lista;
    }


}
