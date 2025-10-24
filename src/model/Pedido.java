package model;

import java.time.LocalDateTime;

public class Pedido {
    private String numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHora;
    private boolean estado;
    private double precioTotal;

    //constructor
    public Pedido(String numeroPedido, Cliente cliente, Articulo articulo, int cantidad, LocalDateTime fechaHora, boolean estado) {
        this.numeroPedido = numeroPedido;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.precioTotal = calcularPrecioTotal();
    }

    //getters and setters
    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }



    private double calcularPrecioTotal() {
        double precioBase = articulo.getPrecioVenta() * cantidad;
        double gastosEnvio = articulo.getGastosEnvio();

        if (cliente instanceof ClientePremium premium) {
            gastosEnvio *= (1 - premium.getDescuentoEnvio() / 100.0);
        }

        return precioBase + gastosEnvio;
    }


    @Override
    public String toString() {
        return "Pedidos{" +
                "numeroPedido='" + numeroPedido + '\'' +
                ", cliente=" + cliente +
                ", articulo=" + articulo +
                ", cantidad=" + cantidad +
                ", fechaHora=" + fechaHora +
                ", estado=" + estado +
                ", precioTotal=" + String.format("%.2f", precioTotal) +
                '}';
    }
}