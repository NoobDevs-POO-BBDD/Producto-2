package model;

import java.util.Objects;
import java.util.regex.Pattern;

public abstract class Cliente {
    //  INMUTABILIDAD: Atributos final para datos únicos
    private final String email;
    private final String NIF;
    
    private String nombre;
    private String domicilio;
    
    //  VALIDACIÓN: Patrones para formato
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern NIF_PATTERN = 
        Pattern.compile("^[0-9]{8}[A-Za-z]$");

    // VALIDACIÓN EN CONSTRUCTOR
    public Cliente(String email, String nombre, String domicilio, String NIF) {
        validarEmail(email);
        validarNIF(NIF);
        validarNombre(nombre);
        validarDomicilio(domicilio);
        
        this.email = email.toLowerCase().trim(); // Normalizar email
        this.nombre = nombre.trim();
        this.domicilio = domicilio.trim();
        this.NIF = NIF.toUpperCase().trim(); // Normalizar NIF
    }

    //  MÉTODOS DE VALIDACIÓN PRIVADOS
    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Formato de email inválido: " + email);
        }
    }

    private void validarNIF(String nif) {
        if (nif == null || nif.trim().isEmpty()) {
            throw new IllegalArgumentException("El NIF no puede ser nulo o vacío");
        }
        if (!NIF_PATTERN.matcher(nif.trim()).matches()) {
            throw new IllegalArgumentException("Formato de NIF inválido: " + nif);
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
    }

    private void validarDomicilio(String domicilio) {
        if (domicilio == null || domicilio.trim().isEmpty()) {
            throw new IllegalArgumentException("El domicilio no puede ser nulo o vacío");
        }
    }

    //  GETTERS
    public String getEmail() {
        return email;
    }

    //  ELIMINADO: Setters para datos inmutables
    // public void setEmail(String email) {
    //     this.email = email;
    // }

    public String getNombre() {
        return nombre;
    }

    //  VALIDACIÓN EN SETTERS
    public void setNombre(String nombre) {
        validarNombre(nombre);
        this.nombre = nombre.trim();
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        validarDomicilio(domicilio);
        this.domicilio = domicilio.trim();
    }

    public String getNIF() {
        return NIF;
    }

    //  ELIMINADO: Setter para NIF inmutable
    // public void setNIF(String NIF) {
    //     this.NIF = NIF;
    // }

    //  MÉTODOS EQUALS Y HASHCODE (basados en email como identificador único)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(email, cliente.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    // MÉTODO TOSTRING
    @Override
    public String toString() {
        return String.format("Cliente[email=%s, nombre=%s, NIF=%s, domicilio=%s]", 
                           email, nombre, NIF, domicilio);
    }

    //  MÉTODO ABSTRACTO para comportamiento específico de cada tipo
    public abstract double getDescuentoEnvio();
    
    public abstract String getTipoCliente();

    //  MÉTODO PARA CALCULAR COSTE ENVÍO (usando el descuento específico)
    public double calcularCosteEnvio(double costeBaseEnvio) {
        return costeBaseEnvio * (1 - getDescuentoEnvio());
    }
}