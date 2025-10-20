package controller;

import view.Vista;
import model.TiendaOnline;

public class Controlador {
    private Vista vista;
    private TiendaOnline modelo;

    //Constructor
    public Controlador(Vista vista, TiendaOnline modelo){
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