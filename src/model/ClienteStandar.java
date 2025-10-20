package model;

import java.util.Objects;

public class ClienteStandar extends Cliente {
    private double descuentoEnvio;

    // CONSTRUCTOR CON VALIDACIÓN
    public ClienteStandar(String email, String nombre, String domicilio, String NIF, 
                         double descuentoEnvio) {
        super(email, nombre, domicilio, NIF);
        setDescuentoEnvio(descuentoEnvio); // Usar setter para validación
    }

    // CONSTRUCTOR CON VALOR POR DEFECTO 
    public ClienteStandar(String email, String nombre, String domicilio, String NIF) {
        this(email, nombre, domicilio, NIF, 0.0); // Descuento 0% por defecto
    }

    // IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS
    @Override
    public double getDescuentoEnvio() { 
        return descuentoEnvio; 
    }

    @Override
    public String getTipoCliente() {
        return "ESTÁNDAR";
    }

    //  GETTERS Y SETTERS CON VALIDACIÓN
    public void setDescuentoEnvio(double descuentoEnvio) { 
        if (descuentoEnvio != 0.0) {
            throw new IllegalArgumentException(
                "Los clientes estándar no pueden tener descuento de envío. " +
                "Solo clientes premium pueden tener descuento. Valor: " + descuentoEnvio
            );
        }
        this.descuentoEnvio = descuentoEnvio; 
    }

    //  MÉTODOS ESPECÍFICOS DE CLIENTE ESTÁNDAR
    public boolean deberiaSerPremium(int pedidosMensuales, double costeEnvioPromedio) {
        if (pedidosMensuales < 0 || costeEnvioPromedio < 0) {
            throw new IllegalArgumentException("Los valores no pueden ser negativos");
        }
        
        double costeEnvioMensual = pedidosMensuales * costeEnvioPromedio;
        double costeEnvioAnual = costeEnvioMensual * 12;
        
        // Suponiendo que premium cuesta 30€/año y da 20% descuento
        double ahorroPotencial = costeEnvioAnual * 0.2;
        return ahorroPotencial > 30; 
    }

    //  SOBRESCRITURA DE EQUALS Y HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClienteStandar that = (ClienteStandar) o;
        return Double.compare(that.descuentoEnvio, descuentoEnvio) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), descuentoEnvio);
    }

    // SOBRESCRITURA DE TOSTRING
    @Override
    public String toString() {
        return String.format(
            "ClienteStandar[%s, descuento=%.0f%%]",
            super.toString().replace("Cliente[", "").replace("]", ""),
            descuentoEnvio * 100
        );
    }

    //  MÉTODO DE FÁBRICA (OPCIONAL)
    public static ClienteStandar crearEstandar(String email, String nombre, 
                                              String domicilio, String NIF) {
        return new ClienteStandar(email, nombre, domicilio, NIF);
    }

    //  MÉTODO PARA CONVERSIÓN A PREMIUM
    public ClientePremium convertirAPremium(double descuentoEnvio, int cuotaAnual) {
        return new ClientePremium(
            this.getEmail(),
            this.getNombre(),
            this.getDomicilio(),
            this.getNIF(),
            descuentoEnvio,
            cuotaAnual
        );
    }
}