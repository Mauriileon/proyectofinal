package com.proyecto;

import java.time.LocalDate;


public class Tienda {

    private final String nombre;


    public Tienda(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la tienda no puede ser nulo o vacío.");
        }
        this.nombre = nombre;
    }

   
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
