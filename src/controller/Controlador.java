package NoobDevs.controlador;

import NoobDevs.vista.Vista;
import NoobDevs.modelo.Data;

public class Controlador {
    private Vista vista;
    private Data modelo;

    //Constructor
    public Controlador(Vista vista, Data modelo){
        this.vista = vista;
        this.modelo = modelo;
    }

    // CÓDIGO


    /**
     * Se inicia la aplicación llamando a la función principal de vista
     */
    public void iniciar(){
        vista.menu();
    }
}