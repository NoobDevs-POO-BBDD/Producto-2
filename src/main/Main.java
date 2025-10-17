package NoobDevs.main;

import NoobDevs.modelo.Data;
import NoobDevs.vista.Vista;
import NoobDevs.controlador.Controlador;

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

//según he visto en tutoriales, el main debería quedar así porque tiene que pilla rel codigo de view, pero tampoco estoy segura -annahico

import view.TiendaView;

public class Main {
    public static void main(String[] args) {
        TiendaView vista = new TiendaView();
        vista.mostrarMenuPrincipal();
    }
}