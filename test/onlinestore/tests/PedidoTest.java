package onlinestore.tests;

import model.Articulo;
import model.ClienteStandar;
import model.Pedido;
import  org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class PedidoTest {
@Test
void testConstructorYGetters() {
    Articulo articulo = new Articulo("A001", "Libro", 25.0, 5.0, 2);
    ClienteStandar cliente = new ClienteStandar("ana@mail.com", "Ana", "Barcelona, 123", "11111111A", 0.0);
    LocalDateTime fecha = LocalDateTime.of(2025, 10, 23, 15, 30);

    Pedido pedido = new Pedido("P001", cliente, articulo, 3, fecha, true);

    assertEquals("P001", pedido.getNumeroPedido());
    assertEquals(cliente, pedido.getCliente());
    assertEquals(3, pedido.getCantidad());
    assertTrue(pedido.isEstado());
    assertEquals(articulo, pedido.getArticulo());
    assertEquals(fecha, pedido.getFechaHora());
}

    @Test
    void testSetters() {
        Articulo articulo = new Articulo("A001", "Libro", 25.0, 5.0, 2);
        ClienteStandar cliente = new ClienteStandar("ana@mail.com", "Ana", "Barcelona, 123", "11111111A", 0.0);
        LocalDateTime fecha = LocalDateTime.of(2025, 10, 23, 15, 30);

        Pedido pedido = new Pedido("P001", cliente, articulo, 3, fecha, true);


        Articulo nuevoArticulo = new Articulo("A002", "Cuaderno", 30.0, 7.5, 1);
        ClienteStandar nuevoCliente = new ClienteStandar("maria@mail.com", "María", "Calle Madrid", "22222222B", 5.0);
        LocalDateTime nuevaFecha = LocalDateTime.of(2025, 10, 24, 10, 0);


        pedido.setNumeroPedido("P002");
        pedido.setArticulo(nuevoArticulo);
        pedido.setCliente(nuevoCliente);
        pedido.setCantidad(5);
        pedido.setFechaHora(nuevaFecha);
        pedido.setEstado(false);

        // Comprobar cambios
        assertEquals("P002", pedido.getNumeroPedido());
        assertEquals(nuevoArticulo, pedido.getArticulo());
        assertEquals(nuevoCliente, pedido.getCliente());
        assertEquals(5, pedido.getCantidad());
        assertEquals(nuevaFecha, pedido.getFechaHora());
        assertFalse(pedido.isEstado());
    }
    @Test
    void testToString() {
        Articulo articulo = new Articulo("A001", "Libro", 25.0, 5.0, 2);
        ClienteStandar cliente = new ClienteStandar("ana@mail.com", "Ana", "Barcelona, 123", "11111111A", 0.0);
        LocalDateTime fecha = LocalDateTime.of(2025, 10, 23, 15, 30);

        Pedido pedido = new Pedido("P001", cliente, articulo, 3, fecha, true);

        String esperado = "Pedidos{" +
                "numeroPedido='P001'" +
                ", cliente=" + cliente +
                ", articulo=" + articulo +
                ", cantidad=3" +
                ", fechaHora=" + fecha +
                ", estado=true" +
                '}';

        assertEquals(esperado, pedido.toString());
    }
}