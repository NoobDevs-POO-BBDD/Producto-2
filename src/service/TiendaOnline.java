import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class TiendaOnline {
    //  COLECCIONES MEJORADAS: HashMap para búsquedas eficientes
    private Map<String, Articulo> articulosMap;
    private Map<String, Cliente> clientesMap;
    private Map<String, Pedidos> pedidosMap;
    
    //  GENERICS: Uso de tipos parametrizados en colecciones
    private List<Pedidos> pedidosList; // Para mantener orden

    public TiendaOnline() {
        this.articulosMap = new HashMap<>();
        this.clientesMap = new HashMap<>();
        this.pedidosMap = new HashMap<>();
        this.pedidosList = new ArrayList<>();
    }

    // === GESTIÓN DE ARTÍCULOS ===
    
    public void añadirArticulo(Articulo articulo) {
        //  EXCEPCIÓN PERSONALIZADA (debes crear esta clase)
        if (articulosMap.containsKey(articulo.getCodigo())) {
            throw new ArticuloExistenteException("Ya existe un artículo con el código: " + articulo.getCodigo());
        }
        articulosMap.put(articulo.getCodigo(), articulo);
    }

    public List<Articulo> mostrarArticulos() {
        //  COLECCIÓN INMUTABLE para evitar modificaciones externas
        return List.copyOf(articulosMap.values());
    }

    public Optional<Articulo> buscarArticulo(String codigo) {
        //  OPTIONAL: Mejora el manejo de valores nulos
        return Optional.ofNullable(articulosMap.get(codigo));
    }

    // === GESTIÓN DE CLIENTES ===
    
    public void añadirCliente(Cliente cliente) {
        //  EXCEPCIÓN PERSONALIZADA
        if (clientesMap.containsKey(cliente.getEmail())) {
            throw new ClienteExistenteException("Ya existe un cliente con el email: " + cliente.getEmail());
        }
        clientesMap.put(cliente.getEmail(), cliente);
    }

    public List<Cliente> mostrarClientes() {
        return List.copyOf(clientesMap.values());
    }

    //  GENERICS: Uso de wildcards para flexibilidad
    public List<? extends Cliente> mostrarClientesEstandar() {
        return clientesMap.values().stream()
                .filter(cliente -> cliente instanceof ClienteStandar)
                .collect(Collectors.toList());
    }

    public List<? extends Cliente> mostrarClientesPremium() {
        return clientesMap.values().stream()
                .filter(cliente -> cliente instanceof ClientePremium)
                .collect(Collectors.toList());
    }

    public Optional<Cliente> buscarClientePorEmail(String email) {
        return Optional.ofNullable(clientesMap.get(email));
    }

    public Optional<Cliente> buscarClientePorNIF(String nif) {
        return clientesMap.values().stream()
                .filter(cliente -> cliente.getNIF().equals(nif))
                .findFirst();
    }

    // === GESTIÓN DE PEDIDOS ===
    
    public void añadirPedido(String numeroPedido, String emailCliente, String codigoArticulo, int cantidad) {
        //  EXCEPCIONES ESPECÍFICAS
        if (pedidosMap.containsKey(numeroPedido)) {
            throw new PedidoExistenteException("Ya existe un pedido con número: " + numeroPedido);
        }

        Articulo articulo = articulosMap.get(codigoArticulo);
        if (articulo == null) {
            throw new ArticuloNoEncontradoException("No existe el artículo con código: " + codigoArticulo);
        }

        Cliente cliente = clientesMap.get(emailCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("No existe el cliente con email: " + emailCliente);
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
            false
        );
        
        pedidosMap.put(numeroPedido, pedido);
        pedidosList.add(pedido);
    }

    public boolean eliminarPedido(String numeroPedido) {
        Pedidos pedido = pedidosMap.get(numeroPedido);
        if (pedido != null && !pedido.estado() && puedeSerCancelado(pedido)) {
            pedidosMap.remove(numeroPedido);
            pedidosList.remove(pedido);
            return true;
        }
        return false;
    }

    //  COLECCIONES: Streams inmutables para datos de solo lectura
    public List<Pedidos> mostrarPedidosPendientes() {
        return pedidosList.stream()
                .filter(pedido -> !pedido.estado())
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Pedidos> mostrarPedidosPendientes(String emailCliente) {
        return pedidosList.stream()
                .filter(pedido -> !pedido.estado() && 
                         pedido.cliente().getEmail().equals(emailCliente))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Pedidos> mostrarPedidosEnviados() {
        return pedidosList.stream()
                .filter(Pedidos::estado)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Pedidos> mostrarPedidosEnviados(String emailCliente) {
        return pedidosList.stream()
                .filter(pedido -> pedido.estado() && 
                         pedido.cliente().getEmail().equals(emailCliente))
                .collect(Collectors.toUnmodifiableList());
    }

    public void marcarPedidoComoEnviado(String numeroPedido) {
        Pedidos pedido = pedidosMap.get(numeroPedido);
        if (pedido != null) {
            pedido.setEstado(true);
        }
    }

    // === MÉTODOS AUXILIARES ===
    
    public Optional<Pedidos> buscarPedido(String numeroPedido) {
        return Optional.ofNullable(pedidosMap.get(numeroPedido));
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
        Pedidos pedido = pedidosMap.get(numeroPedido);
        if (pedido == null) {
            throw new PedidoNoEncontradoException("No existe el pedido: " + numeroPedido);
        }

        Articulo articulo = pedido.articulo();
        int cantidad = pedido.cantidad();
        Cliente cliente = pedido.cliente();

        double precioBase = articulo.getPrecioVenta() * cantidad;
        double gastosEnvio = articulo.getGastosEnvio();

        if (cliente instanceof ClientePremium premium) {
            gastosEnvio *= (1 - premium.getDescuentoEnvio());
        }

        return precioBase + gastosEnvio;
    }

    // === ESTADÍSTICAS ===
    
    public int getTotalArticulos() {
        return articulosMap.size();
    }

    public int getTotalClientes() {
        return clientesMap.size();
    }

    public int getTotalClientesEstandar() {
        return (int) clientesMap.values().stream()
                .filter(cliente -> cliente instanceof ClienteStandar)
                .count();
    }

    public int getTotalClientesPremium() {
        return (int) clientesMap.values().stream()
                .filter(cliente -> cliente instanceof ClientePremium)
                .count();
    }

    public int getTotalPedidos() {
        return pedidosMap.size();
    }

    public int getTotalPedidosPendientes() {
        return (int) pedidosList.stream()
                .filter(pedido -> !pedido.estado())
                .count();
    }

    public int getTotalPedidosEnviados() {
        return (int) pedidosList.stream()
                .filter(Pedidos::estado)
                .count();
    }
}