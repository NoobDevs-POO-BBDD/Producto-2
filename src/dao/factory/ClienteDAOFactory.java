package dao.factory;

import dao.impl.ClienteDAOImpl;
import dao.interfaces.ClienteDAO;

public class ClienteDAOFactory {

    public static ClienteDAOImpl getClienteDAO() {
        return new ClienteDAOImpl();
    }
}
