package dao.factory;

import dao.impl.PedidosDAOImpl;
import dao.interfaces.PedidoDAO;

public class PedidoDAOFactory {

    public static PedidoDAO getPedidoDAO() {
        return new PedidosDAOImpl();
    }
}
