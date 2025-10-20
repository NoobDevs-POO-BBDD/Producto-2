import java.util.Objects;

public class ClientePremium extends Cliente {
    private double descuentoEnvio;
    private int cuotaAnual;

    //  CONSTRUCTOR CON VALIDACIÓN
    public ClientePremium(String email, String nombre, String domicilio, String NIF, 
                         double descuentoEnvio, int cuotaAnual) {
        super(email, nombre, domicilio, NIF);
        setDescuentoEnvio(descuentoEnvio); // Usar setter para validación
        setCuotaAnual(cuotaAnual);         // Usar setter para validación
    }

    //  CONSTRUCTOR CON VALORES POR DEFECTO
    public ClientePremium(String email, String nombre, String domicilio, String NIF) {
        this(email, nombre, domicilio, NIF, 0.2, 30); // Valores por defecto
    }

    //  IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS
    @Override
    public double getDescuentoEnvio() { 
        return descuentoEnvio; 
    }

    @Override
    public String getTipoCliente() {
        return "PREMIUM";
    }

    //  GETTERS Y SETTERS CON VALIDACIÓN
    public void setDescuentoEnvio(double descuentoEnvio) { 
        if (descuentoEnvio < 0 || descuentoEnvio > 1) {
            throw new IllegalArgumentException(
                "El descuento de envío debe estar entre 0 y 1 (0%-100%). Valor: " + descuentoEnvio
            );
        }
        this.descuentoEnvio = descuentoEnvio; 
    }

    public int getCuotaAnual() { 
        return cuotaAnual; 
    }

    public void setCuotaAnual(int cuotaAnual) { 
        if (cuotaAnual < 0) {
            throw new IllegalArgumentException(
                "La cuota anual no puede ser negativa. Valor: " + cuotaAnual
            );
        }
        this.cuotaAnual = cuotaAnual; 
    }

    //  MÉTODOS ESPECÍFICOS DE CLIENTE PREMIUM
    public double calcularAhorroAnual(double enviosAnuales) {
        if (enviosAnuales < 0) {
            throw new IllegalArgumentException("Los envíos anuales no pueden ser negativos");
        }
        return enviosAnuales * descuentoEnvio;
    }

    public boolean esCuotaJustificada(double enviosAnuales, double costeEnvioPromedio) {
        double ahorro = calcularAhorroAnual(enviosAnuales * costeEnvioPromedio);
        return ahorro > cuotaAnual;
    }

    //  SOBRESCRITURA DE EQUALS Y HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientePremium that = (ClientePremium) o;
        return Double.compare(that.descuentoEnvio, descuentoEnvio) == 0 &&
               cuotaAnual == that.cuotaAnual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), descuentoEnvio, cuotaAnual);
    }

    //  SOBRESCRITURA DE TOSTRING
    @Override
    public String toString() {
        return String.format(
            "ClientePremium[%s, descuento=%.0f%%, cuota=%d€/año]",
            super.toString().replace("Cliente[", "").replace("]", ""),
            descuentoEnvio * 100,
            cuotaAnual
        );
    }

    // MÉTODO DE FÁBRICA (OPCIONAL)
    public static ClientePremium crearPremium(String email, String nombre, 
                                             String domicilio, String NIF) {
        return new ClientePremium(email, nombre, domicilio, NIF);
    }
}