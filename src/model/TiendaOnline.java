import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TiendaOnline {
    private List<Articulo> articulos;
    private List<Cliente> clientes;
    private List<Pedidos> pedidos;

    public TiendaOnline() {
        this.articulos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    // === GESTIÓN DE ARTÍCULOS ===
    
    public void añadirArticulo(Articulo articulo) {
        // Verificar que no existe un artículo con el mismo código
        if (buscarArticulo(articulo.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un artículo con el código: " + articulo.getCodigo());
        }
        articulos.add(articulo);
    }

    public List<Articulo> mostrarArticulos() {
        return new ArrayList<>(articulos);
    }

    public Articulo buscarArticulo(String codigo) {
        return articulos.stream()
                .filter(articulo -> articulo.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    // === GESTIÓN DE CLIENTES ===
    
    public void añadirCliente(Cliente cliente) {
        // Verificar que no existe un cliente con el mismo email (identificador)
        if (buscarClientePorEmail(cliente.getEmail()) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + cliente.getEmail());
        }
        clientes.add(cliente);
    }

    public List<Cliente> mostrarClientes() {
        return new ArrayList<>(clientes);
    }

    public List<Cliente> mostrarClientesEstandar() {
        return clientes.stream()
                .filter(cliente -> cliente instanceof ClienteStandar)
                .toList();
    }

    public List<Cliente> mostrarClientesPremium() {
        return clientes.stream()
                .filter(cliente -> cliente instanceof ClientePremium)
                .toList();
    }

    public Cliente buscarClientePorEmail(String email) {
        return clientes.stream()
                .filter(cliente -> cliente.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public Cliente buscarClientePorNIF(String nif) {
        return clientes.stream()
                .filter(cliente -> cliente.getNIF().equals(nif))
                .findFirst()
                .orElse(null);
    }

    // === GESTIÓN DE PEDIDOS ===
    
    public void añadirPedido(String numeroPedido, String emailCliente, String codigoArticulo, int cantidad) {
        // Verificar que el artículo existe
        Articulo articulo = buscarArticulo(codigoArticulo);
        if (articulo == null) {
            throw new IllegalArgumentException("No existe el artículo con código: " + codigoArticulo);
        }

        // Buscar cliente por email
        Cliente cliente = buscarClientePorEmail(emailCliente);
        
        // Si el cliente no existe, lanzamos excepción (según requisitos, se deberían pedir los datos)
        if (cliente == null) {
            throw new IllegalArgumentException("No existe el cliente con email: " + emailCliente + 
                                             ". Se deben pedir los datos del nuevo cliente primero.");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }

        Pedidos pedido = new Pedidos(
            numeroPedido,
            cliente,
            articulo,
            cantidad,
            LocalDate.now(),
            false // estado inicial: pendiente (no enviado)
        );
        pedidos.add(pedido);
    }

    public boolean eliminarPedido(String numeroPedido) {
        Pedidos pedido = buscarPedido(numeroPedido);
        if (pedido != null && !estaEnviado(pedido) && puedeSerCancelado(pedido)) {
            return pedidos.remove(pedido);
        }
        return false;
    }

    public List<Pedidos> mostrarPedidosPendientes() {
        return pedidos.stream()
                .filter(pedido -> !pedido.estado()) // estado false = pendiente
                .toList();
    }

    public List<Pedidos> mostrarPedidosPendientes(String emailCliente) {
        return pedidos.stream()
                .filter(pedido -> !pedido.estado() && 
                         pedido.cliente().getEmail().equals(emailCliente))
                .toList();
    }

    public List<Pedidos> mostrarPedidosEnviados() {
        return pedidos.stream()
                .filter(Pedidos::estado) // estado true = enviado
                .toList();
    }

    public List<Pedidos> mostrarPedidosEnviados(String emailCliente) {
        return pedidos.stream()
                .filter(pedido -> pedido.estado() && 
                         pedido.cliente().getEmail().equals(emailCliente))
                .toList();
    }

    public void marcarPedidoComoEnviado(String numeroPedido) {
        Pedidos pedido = buscarPedido(numeroPedido);
        if (pedido != null) {
            pedido.setEstado(true);
        }
    }

    // === MÉTODOS AUXILIARES ===
    
    public Pedidos buscarPedido(String numeroPedido) {
        return pedidos.stream()
                .filter(pedido -> pedido.numeroPedido().equals(numeroPedido))
                .findFirst()
                .orElse(null);
    }

    private boolean estaEnviado(Pedidos pedido) {
        return pedido.estado();
    }

    private boolean puedeSerCancelado(Pedidos pedido) {
        LocalDateTime fechaPedido = pedido.fechaHora().atStartOfDay();
        LocalDateTime ahora = LocalDateTime.now();
        long minutosTranscurridos = ChronoUnit.MINUTES.between(fechaPedido, ahora);
        
        return minutosTranscurridos <= pedido.articulo().getTiempoPreparacion();
    }

    public double calcularPrecioPedido(String numeroPedido) {
        Pedidos pedido = buscarPedido(numeroPedido);
        if (pedido == null) {
            return 0.0;
        }

        Articulo articulo = pedido.articulo();
        int cantidad = pedido.cantidad();
        Cliente cliente = pedido.cliente();

        double precioBase = articulo.getPrecioVenta() * cantidad;
        double gastosEnvio = articulo.getGastosEnvio();

        // Aplicar descuento en envío para clientes premium
        if (cliente instanceof ClientePremium premium) {
            gastosEnvio *= (1 - premium.getDescuentoEnvio());
        }

        return precioBase + gastosEnvio;
    }

    // === ESTADÍSTICAS ===
    
    public int getTotalArticulos() {
        return articulos.size();
    }

    public int getTotalClientes() {
        return clientes.size();
    }

    public int getTotalClientesEstandar() {
        return mostrarClientesEstandar().size();
    }

    public int getTotalClientesPremium() {
        return mostrarClientesPremium().size();
    }

    public int getTotalPedidos() {
        return pedidos.size();
    }

    public int getTotalPedidosPendientes() {
        return mostrarPedidosPendientes().size();
    }

    public int getTotalPedidosEnviados() {
        return mostrarPedidosEnviados().size();
    }

    @Override
    public String toString() {
        return "TiendaOnline{" +
                "articulos=" + getTotalArticulos() +
                ", clientes=" + getTotalClientes() +
                " (Estandar: " + getTotalClientesEstandar() + 
                ", Premium: " + getTotalClientesPremium() + ")" +
                ", pedidos=" + getTotalPedidos() +
                " (Pendientes: " + getTotalPedidosPendientes() + 
                ", Enviados: " + getTotalPedidosEnviados() + ")" +
                '}';
    }
}