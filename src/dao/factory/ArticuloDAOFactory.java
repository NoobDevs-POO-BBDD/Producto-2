package dao.factory;

import dao.impl.ArticuloDAOImpl;
import dao.interfaces.ArticuloDAO;

public class ArticuloDAOFactory {

    // Metodo para obtener DAO de Artículo
    public static ArticuloDAO getArticuloDAO() {
        return new ArticuloDAOImpl();
    }

}
