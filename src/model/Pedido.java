import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Pedido {
    private String numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDate fechaPedido;
    private boolean enviado;           // Nombre más descriptivo

    //constructor
    public Pedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDate fechaHora, boolean estado) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaPedido = fechaPedido;
        this.enviado = enviado;
    }

    //  CONSTRUCTOR PARA PEDIDOS NUEVOS (no enviados)
    public Pedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad) {
        this(numeroPedido, cliente, articulo, cantidad, LocalDate.now(), false);
    }

    // MÉTODOS DE VALIDACIÓN PRIVADOS
    private void validarNumeroPedido(String numeroPedido) {
        if (numeroPedido == null || numeroPedido.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de pedido no puede ser nulo o vacío");
        }
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
    }

    private void validarArticulo(Articulo articulo) {
        if (articulo == null) {
            throw new IllegalArgumentException("El artículo no puede ser nulo");
        }
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
    }

    private void validarFechaPedido(LocalDate fechaPedido) {
        if (fechaPedido == null) {
            throw new IllegalArgumentException("La fecha del pedido no puede ser nula");
        }
        if (fechaPedido.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del pedido no puede ser futura");
        }
    }

    //  GETTERS CON CONVENCIÓN JAVA
    public String getNumeroPedido() {
        return numeroPedido;
    }

    //  ELIMINADO: Setter para número pedido (inmutable)
    // public void setNumeroPedido(String numeroPedido) { ... }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        validarCliente(cliente);
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        validarArticulo(articulo);
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        validarCantidad(cantidad);
        this.cantidad = cantidad;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        validarFechaPedido(fechaPedido);
        this.fechaPedido = fechaPedido;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    //  MÉTODOS DE NEGOCIO
    public double calcularTotal() {
        double precioBase = articulo.getPrecioVenta() * cantidad;
        double gastosEnvio = articulo.getGastosEnvio();
        
        // Aplicar descuento de envío para clientes premium
        if (cliente instanceof ClientePremium premium) {
            gastosEnvio *= (1 - premium.getDescuentoEnvio());
        }
        
        return precioBase + gastosEnvio;
    }

    public boolean estaPendiente() {
        return !enviado;
    }

    public boolean puedeSerCancelado() {
        if (enviado) {
            return false; // No se puede cancelar si ya fue enviado
        }
        
        LocalDateTime fechaPedido = this.fechaPedido.atStartOfDay();
        LocalDateTime ahora = LocalDateTime.now();
        long minutosTranscurridos = ChronoUnit.MINUTES.between(fechaPedido, ahora);
        
        return minutosTranscurridos <= articulo.getTiempoPreparacion();
    }

    public int getTiempoRestanteCancelacion() {
        if (enviado) {
            return 0; // Ya no se puede cancelar
        }
        
        LocalDateTime fechaPedido = this.fechaPedido.atStartOfDay();
        LocalDateTime ahora = LocalDateTime.now();
        long minutosTranscurridos = ChronoUnit.MINUTES.between(fechaPedido, ahora);
        int tiempoRestante = articulo.getTiempoPreparacion() - (int)minutosTranscurridos;
        
        return Math.max(0, tiempoRestante);
    }

    // MÉTODOS EQUALS Y HASHCODE (basados en númeroPedido)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(numeroPedido, pedido.numeroPedido);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido='" + numeroPedido + '\'' +
                ", cliente=" + cliente +
                ", articulo=" + articulo +
                ", cantidad=" + cantidad +
                ", fechaHora=" + fechaHora +
                ", estado=" + estado +
                '}';
    }

    // TOSTRING MEJORADO
    @Override
    public String toString() {
        return String.format(
            "Pedido[#%s, cliente=%s, artículo=%s, cantidad=%d, total=%.2f€, %s]",
            numeroPedido,
            cliente.getNombre(),
            articulo.getDescripcion(),
            cantidad,
            calcularTotal(),
            enviado ? "ENVIADO" : "PENDIENTE"
        );
    }

    // MÉTODO DE FÁBRICA
    public static Pedido crearPedido(String numeroPedido, Cliente cliente, 
                                    Articulo articulo, int cantidad) {
        return new Pedido(numeroPedido, cliente, articulo, cantidad);
    }
}