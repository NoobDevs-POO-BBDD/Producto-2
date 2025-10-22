import model.TiendaOnline;
import view.Vista;
import controller.Controlador;

/**
 * Clase principal dónde se une el modelo la vista y el controlador.
 */
public class App {
    public static void main(String[] args){
        //Se crea el modelo
        TiendaOnline modelo = new TiendaOnline();

        //Añadir datos de prueba
        modelo.cargarDatosDePrueba();

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
 * Se añade / 100 para crear porcentaje en TiendaOnline CalcularPrecioPedido
 * Si no existe el cliente no podemos añadirlo y juraría que nos lo piden en el caso de uso salta illegalArgumentException en TiendaOnline anadirPEdido si el cliente es null
 * Modificar la vista para que pregunte filtrar por cliente  en mostrar pedidos enviados y pedidos pendientes (lo pide el enunciado)
 * He quitdado system.println de controlador subsituyendolo por vista. su metodo
 * Errores de copia y pega en vista tipo en pedidos pendientes mostraba mensaje lista pedidos enviado
 * Corregido el problema de introducción de datos en la vista
 */

