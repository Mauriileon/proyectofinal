package com.proyecto;



public class Main {

    public static void main(String[] args) {

        //  1. Crear clientes 
        Cliente cliente1 = new Cliente(
                "Mauricio León",
                "mauricio@email.com",
                "Calle Mayor 10, Madrid"
        );

        Cliente cliente2 = new Cliente(
                "Ana García",
                "ana.garcia@email.com",
                "Av. Diagonal 45, Barcelona"
        );

        //  2. Crear productos físicos 
        // ProductoFisico(nombre, precioBase, pesoKg, costeEnvio)
        ProductoFisico teclado  = new ProductoFisico("Teclado mecánico",   75.00, 1.2,  5.99);
        ProductoFisico monitor  = new ProductoFisico("Monitor 27\"",      350.00, 6.5, 15.00);
        ProductoFisico telefono = new ProductoFisico("Teléfono Samsung",  599.00, 0.2,  0.00);

        //  3. Crear productos digitales 
        // ProductoDigital(nombre, precioBase, tamañoMB)
        ProductoDigital software  = new ProductoDigital("Adobe Photoshop",  29.99, 2048.0);
        ProductoDigital licencia  = new ProductoDigital("Windows 11 Home",  119.00,  512.0);
        ProductoDigital ebook     = new ProductoDigital("Clean Code (ebook)", 14.99,   5.2);
        
     

        //  4. Mostrar precios individuales 
        System.out.println("=== PRECIOS DE PRODUCTOS ===");
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                teclado.getNombre(),  teclado.getPrecio(),  teclado.calcularPrecioFinal());
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                monitor.getNombre(),  monitor.getPrecio(),  monitor.calcularPrecioFinal());
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                software.getNombre(), software.getPrecio(), software.calcularPrecioFinal());
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                licencia.getNombre(), licencia.getPrecio(), licencia.calcularPrecioFinal());
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                ebook.getNombre(),    ebook.getPrecio(),    ebook.calcularPrecioFinal());
        System.out.println();

        //  5. Crear pedido mixto para cliente1 
        Pedido pedido1 = new Pedido(cliente1);
        pedido1.agregarProducto(teclado);
        pedido1.agregarProducto(monitor);
        pedido1.agregarProducto(software);
        pedido1.agregarProducto(ebook);

        //  6. Crear pedido solo digital para cliente2 
        Pedido pedido2 = new Pedido(cliente2);
        pedido2.agregarProducto(licencia);
        pedido2.agregarProducto(ebook);
        pedido2.agregarProducto(software);

        //  7. Mostrar resúmenes 
        System.out.println();
        pedido1.resumenPedido();

        System.out.println();
        pedido2.resumenPedido();

        //  8. Demostración de excepción: pedido sin productos 
        System.out.println();
        System.out.println("=== DEMO EXCEPCIÓN: pedido sin productos ===");
        try {
            Pedido pedidoVacio = new Pedido(cliente1);
            pedidoVacio.calcularTotal(); // debe lanzar IllegalStateException
        } catch (IllegalStateException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        }

        // ── 9. Demostración de excepción: precio negativo ─────────────────
        System.out.println();
        System.out.println("=== DEMO EXCEPCIÓN: precio negativo ===");
        try {
            ProductoDigital invalido = new ProductoDigital("Producto inválido", -10.0, 1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Excepción capturada correctamente: " + e.getMessage());
        }
    }
}
