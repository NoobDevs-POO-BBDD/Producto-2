package dao.interfaces;

import model.Pedido;
import java.util.List;

public interface PedidoDAO {

    // Obtener un pedido por su número (identificador único)
    Pedido getPedidoPorNumero(String numeroPedido) throws Exception;

    // Obtener todos los pedidos
    List<Pedido> getTodosLosPedidos() throws Exception;

    // Obtener pedidos de un cliente específico
    List<Pedido> getPedidosPorCliente(String emailCliente) throws Exception;

    // Obtener solo pedidos pendientes
    List<Pedido> getPedidosPendientes() throws Exception;

    // Obtener solo pedidos enviados
    List<Pedido> getPedidosEnviados() throws Exception;

    // Agregar un nuevo pedido
    void anadirPedido(Pedido pedido) throws Exception;

    // Eliminar un pedido por su número
    boolean eliminarPedido(String numeroPedido) throws Exception;

    // Marcar un pedido como enviado
    void marcarPedidoEnviado(String numeroPedido) throws Exception;
}
