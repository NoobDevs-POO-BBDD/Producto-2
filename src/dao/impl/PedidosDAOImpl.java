package dao.impl;

import dao.factory.ArticuloDAOFactory;
import dao.factory.ClienteDAOFactory;
import dao.interfaces.PedidoDAO;
import model.Articulo;
import model.Cliente;
import model.Pedido;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidosDAOImpl implements PedidoDAO {

    @Override
    public Pedido getPedidoPorNumero(String numeroPedido) throws Exception {
        String sql = "SELECT * FROM pedidos WHERE numero_pedido = ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignamos el parámetro del PreparedStatement
            ps.setString(1, numeroPedido);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String numeroPedidoDB = rs.getString("numero_pedido"); // número de pedido desde BD
                    String emailCliente = rs.getString("id_cliente");      // email del cliente
                    String codigoArticulo = rs.getString("id_articulo");   // código del artículo
                    int cantidad = rs.getInt("cantidad");
                    LocalDateTime fechaHora = rs.getObject("fecha_hora", LocalDateTime.class);
                    boolean estado = rs.getBoolean("estado");

                    // Buscamos el cliente y el artículo usando sus DAOs mediante Factory
                    Cliente cliente = ClienteDAOFactory.getClienteDAO().getClientePorEmail(emailCliente);
                    Articulo articulo = ArticuloDAOFactory.getArticuloDAO().getArticuloPorCodigo(codigoArticulo);

                    return new Pedido(numeroPedidoDB, cliente, articulo, cantidad, fechaHora, estado);
                } else {
                    System.out.println("No hay ningún pedido con este número");
                    return null;
                }
            }
        }
    }



    @Override
    public List<Pedido> getTodosLosPedidos() throws Exception {
        List<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String numeroPedidoDB = rs.getString("numero_pedido");
                String emailCliente = rs.getString("id_cliente");
                String codigoArticulo = rs.getString("id_articulo");
                int cantidad = rs.getInt("cantidad");
                LocalDateTime fechaHora = rs.getObject("fecha_hora", LocalDateTime.class);
                boolean estado = rs.getBoolean("estado");

                Cliente cliente = ClienteDAOFactory.getClienteDAO().getClientePorEmail(emailCliente);
                Articulo articulo = ArticuloDAOFactory.getArticuloDAO().getArticuloPorCodigo(codigoArticulo);

                listaPedidos.add(new Pedido(numeroPedidoDB, cliente, articulo, cantidad, fechaHora, estado));
            }
        }

        return listaPedidos;
    }

    @Override
    public List<Pedido> getPedidosPorCliente(String emailCliente) throws Exception {
        List<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE id_cliente = ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, emailCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String numeroPedidoDB = rs.getString("numero_pedido");
                    String emailClienteBD = rs.getString("id_cliente");
                    String codigoArticulo = rs.getString("id_articulo");
                    int cantidad = rs.getInt("cantidad");
                    LocalDateTime fechaHora = rs.getObject("fecha_hora", LocalDateTime.class);
                    boolean estado = rs.getBoolean("estado");

                    Cliente cliente = ClienteDAOFactory.getClienteDAO().getClientePorEmail(emailClienteBD);
                    Articulo articulo = ArticuloDAOFactory.getArticuloDAO().getArticuloPorCodigo(codigoArticulo);

                    listaPedidos.add(new Pedido(numeroPedidoDB, cliente, articulo, cantidad, fechaHora, estado));
                }
            }
        }

        return listaPedidos;
    }

    @Override
    public List<Pedido> getPedidosPendientes() throws Exception {
        List<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE estado = 0";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String numeroPedidoDB = rs.getString("numero_pedido");
                    String emailClienteBD = rs.getString("id_cliente");
                    String codigoArticulo = rs.getString("id_articulo");
                    int cantidad = rs.getInt("cantidad");
                    LocalDateTime fechaHora = rs.getObject("fecha_hora", LocalDateTime.class);
                    boolean estado = rs.getBoolean("estado");

                    Cliente cliente = ClienteDAOFactory.getClienteDAO().getClientePorEmail(emailClienteBD);
                    Articulo articulo = ArticuloDAOFactory.getArticuloDAO().getArticuloPorCodigo(codigoArticulo);

                    listaPedidos.add(new Pedido(numeroPedidoDB, cliente, articulo, cantidad, fechaHora, estado));
                }
            }
        }

        return listaPedidos;
    }

    @Override
    public List<Pedido> getPedidosEnviados() throws Exception {
        List<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE estado = 1";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String numeroPedidoDB = rs.getString("numero_pedido");
                    String emailClienteBD = rs.getString("id_cliente");
                    String codigoArticulo = rs.getString("id_articulo");
                    int cantidad = rs.getInt("cantidad");
                    LocalDateTime fechaHora = rs.getObject("fecha_hora", LocalDateTime.class);
                    boolean estado = rs.getBoolean("estado");

                    Cliente cliente = ClienteDAOFactory.getClienteDAO().getClientePorEmail(emailClienteBD);
                    Articulo articulo = ArticuloDAOFactory.getArticuloDAO().getArticuloPorCodigo(codigoArticulo);

                    listaPedidos.add(new Pedido(numeroPedidoDB, cliente, articulo, cantidad, fechaHora, estado));
                }
            }
        }

        return listaPedidos;
    }

    @Override
    public void anadirPedido(Pedido pedido) throws Exception {
        // Lógica JDBC para anadir pedido
    }

    @Override
    public boolean eliminarPedido(String numeroPedido) throws Exception {
        // Lógica JDBC para eliminar pedido
        return false;
    }

    @Override
    public void marcarPedidoEnviado(String numeroPedido) throws Exception {
        // Lógica JDBC para actualizar estado
    }
}
