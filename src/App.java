import model.Data;
import view.Vista;
import controller.Controlador;

/**
 * Clase principal dónde se une el modelo la vista y el controlador.
 */
public class Main {
    public static void main(String[] args){
        //Se crea el modelo
        Data modelo = new Data();

        //Se crea la vista
        Vista vista = new Vista();

        //Se crea el controlador pasandole la vista y el modelo
        Controlador controlador = new Controlador(vista, modelo);

        //Se manda a vista el controlador para que puedan interactuar
        vista.setControlador(controlador);

        //Se inicia la aplicación meidiante el controlador
        controlador.iniciar();
    }

}


/**
 * CAMBIOS:
 * -> Clase ClienteStandar se ha eliminado el atributo descuento
 * -> Clase ClientePremium se han añadido las constantes CUOTA_ANUAL_PREMIUM y DESCUENTO_ENVIO_PREMIUM
 * -> Clase cliente, cliente premium y clientes estandar se añade el tostring
 * -> clase Data se ha modificado los métodos añadirCliente y añadirArticulo
 * -> Clase PedidoS se le quita la S
 */
