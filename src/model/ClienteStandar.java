package model;

public class ClienteStandar extends Cliente {
    private double descuentoEnvio = 0.0;


    public ClienteStandar(String email, String nombre, String domicilio, String NIF, double descuentoEnvio) {
        super(email, nombre, domicilio, NIF);
        this.descuentoEnvio = descuentoEnvio;
    }

    public double getDescuentoEnvio() {
        return descuentoEnvio;
    }

    public void setDescuentoEnvio(double descuentoEnvio) {
        this.descuentoEnvio = descuentoEnvio;
    }

}