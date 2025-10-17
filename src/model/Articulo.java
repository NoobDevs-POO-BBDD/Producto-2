import java.util.Objects;

public class Articulo {
    //  INMUTABILIDAD: Atributos final para código (no debería cambiar)
    private final String codigo;
    private String descripcion;
    private double precioVenta;
    private double gastosEnvio;
    private int tiempoPreparacion;

    //  VALIDACIÓN EN CONSTRUCTOR
    public Articulo(String codigo, String descripcion, double precioVenta, 
                   double gastosEnvio, int tiempoPreparacion) {
        
        // Validaciones de parámetros
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        if (precioVenta < 0) {
            throw new IllegalArgumentException("El precio de venta no puede ser negativo");
        }
        if (gastosEnvio < 0) {
            throw new IllegalArgumentException("Los gastos de envío no pueden ser negativos");
        }
        if (tiempoPreparacion < 0) {
            throw new IllegalArgumentException("El tiempo de preparación no puede ser negativo");
        }
        
        this.codigo = codigo.trim();
        this.descripcion = descripcion.trim();
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    // GETTERS
    public String getCodigo() {
        return codigo;
    }

    // ELIMINADO: Setter para código (inmutable)
    // public void setCodigo(String codigo) {
    //     this.codigo = codigo;
    // }

    public String getDescripcion() {
        return descripcion;
    }

    //  VALIDACIÓN EN SETTERS
    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        this.descripcion = descripcion.trim();
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        if (gastosEnvio < 0) {
            throw new IllegalArgumentException("Los gastos de envío no pueden ser negativos");
        }
        this.gastosEnvio = gastosEnvio;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        if (precioVenta < 0) {
            throw new IllegalArgumentException("El precio de venta no puede ser negativo");
        }
        this.precioVenta = precioVenta;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        if (tiempoPreparacion < 0) {
            throw new IllegalArgumentException("El tiempo de preparación no puede ser negativo");
        }
        this.tiempoPreparacion = tiempoPreparacion;
    }

    // MÉTODOS EQUALS Y HASHCODE (Importante para colecciones)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Articulo articulo = (Articulo) o;
        return Objects.equals(codigo, articulo.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    // MEJORA: toString más legible
    @Override
    public String toString() {
        return String.format(
            "Artículo[código=%s, descripción=%s, precio=%.2f€, envío=%.2f€, preparación=%d min]",
            codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion
        );
    }

    //  MÉTODO DE FÁBRICA (OPCIONAL - Patrón Factory)
    public static Articulo crearArticulo(String codigo, String descripcion, 
                                        double precioVenta, double gastosEnvio, 
                                        int tiempoPreparacion) {
        return new Articulo(codigo, descripcion, precioVenta, gastosEnvio, tiempoPreparacion);
    }
}