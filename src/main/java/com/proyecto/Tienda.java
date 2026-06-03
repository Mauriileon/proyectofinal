package com.proyecto;

import java.time.LocalDate;


/**
 * Orquesta el flujo de venta: aplica descuentos, calcula envíos y genera la factura.
 */
public class Tienda {

    private final String nombre;

    /**
     * @param nombre nombre de la tienda; no puede ser nulo ni vacío
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    public Tienda(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la tienda no puede ser nulo o vacío.");
        }
        this.nombre = nombre;
    }

    /**
     * Procesa la venta y devuelve la factura generada.
     * @param cliente cliente que realiza la compra
     * @param pedido  pedido con los productos
     * @return factura con el desglose completo
     * @throws IllegalArgumentException si el cliente o pedido son nulos
     * @throws IllegalStateException    si el pedido está vacío
     */
    public Factura realizarVenta(Cliente cliente, Pedido pedido) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo para realizar una venta.");
        }
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo para realizar una venta.");
        }

        double totalBruto = pedido.calcularTotal();

        double porcentajeDescuento = cliente.calcularDescuentoFidelidad();
        double importeDescuento    = totalBruto * porcentajeDescuento;
        double totalConDescuento   = totalBruto - importeDescuento;

        double totalEnvio  = calcularTotalEnvio(pedido);
        double baseNeta    = totalConDescuento - totalEnvio;
        double totalIva    = totalBruto - baseNeta - totalEnvio - importeDescuento;

        // 4. Generar y devolver la Factura
        return new Factura(
                cliente,
                pedido,
                LocalDate.now(),
                baseNeta,
                totalIva,
                totalEnvio,
                importeDescuento,
                totalConDescuento
        );
    }

    
    private double calcularTotalEnvio(Pedido pedido) {
        double totalEnvio = 0.0;
        for (Producto p : pedido.getProductos()) {
            if (p instanceof ProductoFisico pFisico) {
                totalEnvio += pFisico.getCosteEnvio();
            }
        }
        return totalEnvio;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Tienda{nombre='" + nombre + "'}";
    }
}
