package model;

public class ClientePremium extends Cliente {
    private double descuentoEnvio = 0.2;
    private int cuotaAnual = 30;


    public ClientePremium(String email, String nombre, String domicilio, String NIF, double descuentoEnvio, int cuotaAnual) {
        super(email, nombre, domicilio, NIF);
        this.descuentoEnvio = descuentoEnvio;
        this.cuotaAnual = cuotaAnual;
    }


    public double getDescuentoEnvio() { return descuentoEnvio; }
    public void setDescuentoEnvio(double descuentoEnvio) { this.descuentoEnvio = descuentoEnvio; }

    public int getCuotaAnual() { return cuotaAnual; }
    public void setCuotaAnual(int cuotaAnual) { this.cuotaAnual = cuotaAnual; }
}