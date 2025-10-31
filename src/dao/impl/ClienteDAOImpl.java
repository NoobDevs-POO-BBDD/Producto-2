package dao.impl;

import dao.interfaces.ClienteDAO;
import model.Cliente;
import model.ClientePremium;
import model.ClienteStandar;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public Cliente getClientePorEmail(String email) throws Exception {
        String sql = "SELECT * FROM clientes WHERE email = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Leemos el tipo (ENUM: 'ESTANDAR' o 'PREMIUM')
                String tipo = rs.getString("tipo");
                if ("PREMIUM".equalsIgnoreCase(tipo)) {
                    return new ClientePremium(
                            rs.getString("email"),
                            rs.getString("nombre"),
                            rs.getString("domicilio"),
                            rs.getString("nif"),
                            ClientePremium.DESCUENTO_ENVIO_PREMIUM,
                            ClientePremium.CUOTA_ANUAL_PREMIUM
                    );
                } else {
                    return new ClienteStandar(
                            rs.getString("email"),
                            rs.getString("nombre"),
                            rs.getString("domicilio"),
                            rs.getString("nif"),
                            ClienteStandar.DESCUENTO_ENVIO_STANDAR
                    );
                }
            }
            // 7. Si no se encontró nada
            System.out.println("No hay ningún cliente registrado con este email");
            return null;
        } catch (Exception e) {
            // 8. Capturamos cualquier excepción
            throw e;
        }
    }

    @Override
    public List<Cliente> getTodosLosClientes() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                boolean premium = rs.getBoolean("premium");
                if (premium) {
                    lista.add(new ClientePremium(
                            rs.getString("email"),
                            rs.getString("nombre"),
                            rs.getString("domicilio"),
                            rs.getString("nif"),
                            rs.getDouble("descuento_envio"),
                            rs.getInt("cuota_anual")
                    ));
                } else {
                    lista.add(new ClienteStandar(
                            rs.getString("email"),
                            rs.getString("nombre"),
                            rs.getString("domicilio"),
                            rs.getString("nif"),
                            rs.getDouble("descuento_envio")
                    ));
                }
            }

        }

        return lista;
    }


    @Override
    public List<Cliente> getClientesEstandar() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE tipo = 'ESTANDAR'";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ClienteStandar(
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("domicilio"),
                        rs.getString("nif"),
                        rs.getDouble("descuento_envio")
                ));
            }
        }
        return lista;
    }

    @Override
    public List<Cliente> getClientesPremium() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE tipo = 'PREMIUM'";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new ClientePremium(
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("domicilio"),
                        rs.getString("nif"),
                        rs.getDouble("descuento_envio"),
                        rs.getInt("cuota_anual")
                ));
            }
        }
        return lista;
    }

    @Override
    public void anadirCliente(Cliente cliente) throws Exception {
        // Lógica JDBC para anadir clientes
    }
}
