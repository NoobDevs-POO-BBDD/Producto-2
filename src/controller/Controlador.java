
package controller;

import model.Articulo;
import model.Cliente;
import model.Pedido;
import model.TiendaOnline;
import view.Vista;
import java.util.List;

public class Controlador {
    private Vista vista;
    private TiendaOnline modelo;

    //Constructor
    public Controlador(Vista vista, TiendaOnline modelo){
        this.vista = vista;
        this.modelo = modelo;
    }

    // Gestión de clientes.
    public void solicitarAnadirCliente(String email, String nombre, String domicilio, String nif, Boolean premium){
        try {
            modelo.anadirCliente(email, nombre, domicilio, nif,premium);
            vista.clienteAnadido();
        } catch (IllegalArgumentException e) {
            vista.mostrarError(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void solicitarMostrarClientes() throws Exception {
        List<Cliente> listaDeClientes = modelo.mostrarClientes();
        vista.mostrarListaClientes(listaDeClientes);
    }

    public void solicitarMostrarClientesEstandar() throws Exception {
        List<Cliente> listaDeClientesEstandar = modelo.mostrarClientesEstandar();
        vista.mostarListaClientesEstandar(listaDeClientesEstandar);
    }

    public void solicitarMostrarClientesPremium() throws Exception {
        List<Cliente> listaDeClientesPremium = modelo.mostrarClientesPremium();
        vista.mostrarListaClientesPremium(listaDeClientesPremium);
    }

    //Gestión de artículos

    public void solicitarAnadirArticulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion){
        try {
            modelo.anadirArticulo(codigo,descripcion,precioVenta,gastosEnvio,tiempoPreparacion);
            vista.articuloAnadido();
        } catch (Exception e) {
            vista.mostrarError(e.getMessage());
        }
    }

    public void solicitarMostrarArticulos()throws Exception{
        List<Articulo> listaArticulo = modelo.mostrarArticulos();
        vista.mostrarListaArticulos(listaArticulo);
    }

    public void solicitarBuscarArticulo(String codigoBuscar) throws Exception{
        Articulo articuloBuscado = modelo.buscarArticulo(codigoBuscar);
        vista.articuloBuscado(articuloBuscado);
    }

    //Gestión de pedidos.

    public void solicitarAnadirPedido(String numeroPedido,String cliente,String articulo,int cantidad) throws Exception {
        try {
            modelo.anadirPedido(numeroPedido,cliente,articulo,cantidad);
            vista.pedidoAnadido();
        } catch (IllegalArgumentException e) {
            String error = e.getMessage();
            if (error != null && error.startsWith("No existe el cliente con email")){
                vista.mostrarError(error);
                vista.solicitarDatosNuevoCliente(cliente);
                Cliente c = modelo.buscarClientePorEmail(cliente);

                if (c !=null ){
                    solicitarAnadirPedido(numeroPedido, cliente, articulo, cantidad);
                }else {
                    vista.mostrarError("No se pudo registrar el cliente. Pedido cancelado.");
                }
            }else{
                vista.mostrarError(e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void solicitarEliminarPedido(String numeroPedidoBorrar) throws Exception {
        boolean verdadero = modelo.eliminarPedido(numeroPedidoBorrar);
        if(verdadero){
            List<Pedido> listaActualizada = modelo.mostrarPedidosPendientes();
            vista.mostrarListaPedidosPendientes(listaActualizada);
            vista.pedidoEliminado();
        } else {
            String message = "No se pudo eliminar el pedido " + numeroPedidoBorrar + ". Es posible que ya esté enviado o se ha eliminado con anterioridad.";
            vista.mostrarError(message);
        }
    }

    public void solicitarMostrarPedidosPendientes() throws Exception {
        List<Pedido> pendientes = modelo.mostrarPedidosPendientes();
        vista.mostrarListaPedidosPendientes(pendientes);
    }

    public void solicitarMostrarPedidosPendientesEmail(String emailCliente) throws Exception {
        List<Pedido> pendientesEmail = modelo.mostrarPedidosPendientes(emailCliente);
        vista.mostrarListaPedidosPendientes(pendientesEmail);
    }

    public void solicitarMostrarPedidosEnviados() throws Exception {
        List<Pedido> enviados = modelo.mostrarPedidosEnviados();
        vista.mostrarListaPedidosEnviados(enviados);
    }
    public void solicitarMostrarPedidosEnviadosEmail(String emailCliente) throws Exception {
        List<Pedido> enviadosEmail = modelo.mostrarPedidosEnviados(emailCliente);
        vista.mostrarListaPedidosEnviados(enviadosEmail);
    }

    /**
     * Se inicia la aplicación llamando a la función principal de vista
     */
    public void iniciar() throws Exception {
        vista.menu();
    }

}
