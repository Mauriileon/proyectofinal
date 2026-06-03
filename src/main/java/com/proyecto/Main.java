package com.proyecto;


public class Main {

    public static void main(String[] args) {

        System.out.println("=== SISTEMA DE GESTIÓN DE PEDIDOS - PARTE 4 ===\n");

        // 1. Crear tienda
        Tienda tienda = new Tienda("TechShop Online");

        // 2. Crear clientes (con nuevos atributos Parte 4)
        Cliente clienteVip = new Cliente(
                1, "Mauricio León", "mauricio@email.com",
                "Calle Mayor 10, Madrid", 5, true, "España"
        );

        Cliente clienteNormal = new Cliente(
                2, "Ana García", "ana@email.com",
                "Av. Diagonal 45, Barcelona", 2, false, "Francia"
        );

        // 3. Crear productos con IVA (ProductoDigital Parte 4)
        ProductoDigital software   = new ProductoDigital("Adobe Photoshop", 100.0, 2048.0, "GENERAL");
        ProductoDigital ebook      = new ProductoDigital("Clean Code (ebook)", 20.0, 5.0, "SUPER");
        ProductoFisico  teclado    = new ProductoFisico("Teclado mecánico", 75.0, 1.2, "España");
        ProductoFisico  auriculares = new ProductoFisico("Auriculares BT", 50.0, 0.3, "Francia");

        // 4. Mostrar precios individuales
        System.out.println("=== PRECIOS DE PRODUCTOS ===");
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                software.getNombre(), software.getPrecio(), software.calcularPrecioFinal());
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                ebook.getNombre(), ebook.getPrecio(), ebook.calcularPrecioFinal());
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                teclado.getNombre(), teclado.getPrecio(), teclado.calcularPrecioFinal());
        System.out.printf("%-30s base: %7.2f €  →  final: %7.2f €%n",
                auriculares.getNombre(), auriculares.getPrecio(), auriculares.calcularPrecioFinal());
        System.out.println();

        // 5. Pedido cliente VIP
        System.out.println("=== PEDIDO CLIENTE VIP (5 años + VIP = 10% descuento) ===");
        Pedido pedido1 = new Pedido(clienteVip);
        pedido1.agregarProducto(software);
        pedido1.agregarProducto(teclado);
        pedido1.agregarProducto(ebook);
        pedido1.resumenPedido();

        Factura factura1 = tienda.realizarVenta(clienteVip, pedido1);
        factura1.imprimirFactura();
        System.out.println();

        // 6. Pedido cliente normal
        System.out.println("=== PEDIDO CLIENTE NORMAL (2 años, sin VIP = 2% descuento) ===");
        Pedido pedido2 = new Pedido(clienteNormal);
        pedido2.agregarProducto(auriculares);
        pedido2.agregarProducto(ebook);
        pedido2.resumenPedido();

        Factura factura2 = tienda.realizarVenta(clienteNormal, pedido2);
        factura2.imprimirFactura();
        System.out.println();

        // 7. Demostración de excepciones
        System.out.println("=== DEMO EXCEPCIONES ===");

        try {
            Pedido pedidoVacio = new Pedido(clienteVip);
            tienda.realizarVenta(clienteVip, pedidoVacio);
        } catch (IllegalStateException e) {
            System.out.println("✓ Pedido vacío: " + e.getMessage());
        }

        try {
            new ProductoDigital("Inválido", -10.0, 1.0);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Precio negativo: " + e.getMessage());
        }

        try {
            new ProductoDigital("Inválido", 10.0, 1.0, "INVALIDO");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ IVA inválido: " + e.getMessage());
        }
    }
}
