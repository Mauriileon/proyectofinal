package com.proyecto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Pedido {

    private static final AtomicInteger CONTADOR = new AtomicInteger(1);

    private final int                  idPedido;
    private Cliente                    cliente;
    private final List<Producto>       productos;
    private final Map<Producto, Integer> cantidades;

    private Producto producto;
    private int      cantidad;

    public Pedido(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        this.idPedido   = CONTADOR.getAndIncrement();
        this.cliente    = cliente;
        this.productos  = new ArrayList<>();
        this.cantidades = new HashMap<>();
        this.cantidad   = 0;
    }

    public Pedido(Cliente cliente, Producto producto, int cantidad) {
        this(cliente);
        if (producto != null) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.productos.add(producto);
            this.cantidades.put(producto, cantidad);
        }
    }

    public void agregarProducto(Producto p) {
        agregarProducto(p, 1);
    }

    public void agregarProducto(Producto p, int cantidad) {
        if (p == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (cantidad < 1) {
            throw new IllegalArgumentException("La cantidad debe ser al menos 1.");
        }
        for (int i = 0; i < cantidad; i++) {
            productos.add(p);
        }
        cantidades.merge(p, cantidad, Integer::sum);
    }

    public void eliminarProducto(Producto p) {
        productos.remove(p);
        if (cantidades.containsKey(p)) {
            int nuevaCantidad = cantidades.get(p) - 1;
            if (nuevaCantidad <= 0) {
                cantidades.remove(p);
            } else {
                cantidades.put(p, nuevaCantidad);
            }
        }
    }

    public double calcularTotal() {
        if (productos.isEmpty()) {
            throw new IllegalStateException(
                    "No se puede calcular el total: el pedido no contiene productos.");
        }
        double total = 0.0;
        for (Producto p : productos) {
            total += p.calcularPrecioFinal();
        }
        return total;
    }

    public void resumenPedido() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         RESUMEN DEL PEDIDO               ║");
        System.out.printf( "║  ID Pedido: %-30d ║%n", idPedido);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ Cliente   : " + cliente.getNombre());
        System.out.println("║ Correo    : " + cliente.getCorreo());
        System.out.println("║ Dirección : " + cliente.getDireccion());
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ Productos:");
        for (Producto p : productos) {
            System.out.printf("║   %-25s  %6.2f €%n",
                    p.getNombre(), p.calcularPrecioFinal());
        }
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║ TOTAL                          %8.2f €%n", calcularTotal());
        System.out.println("╚══════════════════════════════════════════╝");
    }

    public int getIdPedido() { return idPedido; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<Producto> getProductos() { return productos; }

    public Map<Producto, Integer> getCantidades() { return cantidades; }

    public Producto getProducto() { return producto; }

    public void setProducto(Producto p) { this.producto = p; }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return "Pedido{"
                + "idPedido=" + idPedido
                + ", cliente=" + cliente.getNombre()
                + ", numeroDeProdutos=" + productos.size()
                + '}';
    }
}
