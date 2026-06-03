package com.proyecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración, sistema E2E y regresión para {@link Tienda} y {@link Factura}.
 * Valida el flujo completo desde la realización de una venta hasta la generación de la factura.
 */
@DisplayName("Tests de integración — Tienda y Factura")
class TiendaIntegracionTest {

    private Tienda  tienda;
    private Cliente clienteBasico;
    private Cliente clienteVip;

    /** Inicializa la tienda y los clientes de prueba reutilizados en todos los tests. */
    @BeforeEach
    void setUp() {
        tienda        = new Tienda("TechShop Online");
        clienteBasico = new Cliente(1, "Ana García", "ana@email.com",
                "Calle Mayor 1", 0, false, "España");
        clienteVip    = new Cliente(2, "Mauricio León", "mauricio@email.com",
                "Gran Vía 10", 5, true, "España");
    }

    /** Verifica que {@code realizarVenta} devuelve una factura no nula. */
    @Test
    @DisplayName("TC-INT-01: realizarVenta devuelve Factura no nula")
    void testRealizarVentaDevuelveFactura() {
        Pedido pedido = new Pedido(clienteBasico);
        pedido.agregarProducto(new ProductoDigital("Ebook", 50.0, 1.0, "GENERAL"));

        Factura factura = tienda.realizarVenta(clienteBasico, pedido);

        assertNotNull(factura, "La factura generada no debe ser nula");
    }

    /** Verifica que el código de factura se genera automáticamente con prefijo FAC-. */
    @Test
    @DisplayName("TC-INT-02: Código de factura generado automáticamente con prefijo FAC-")
    void testCodigoFacturaAutomatico() {
        Pedido pedido = new Pedido(clienteBasico);
        pedido.agregarProducto(new ProductoFisico("Teclado", 75.0, 1.0, "España"));

        Factura factura = tienda.realizarVenta(clienteBasico, pedido);

        assertNotNull(factura.getCodigoFactura());
        assertTrue(factura.getCodigoFactura().startsWith("FAC-"),
                "El código de factura debe empezar por 'FAC-'");
    }

    /** Verifica que el total final coincide con el precio del pedido cuando no hay descuento. */
    @Test
    @DisplayName("TC-INT-03: totalFinal de Factura coincide con total del Pedido (sin descuento)")
    void testTotalFacturaSinDescuento() {
        ProductoDigital ebook = new ProductoDigital("Ebook", 100.0, 1.0, "GENERAL");
        Pedido pedido = new Pedido(clienteBasico);
        pedido.agregarProducto(ebook);

        Factura factura = tienda.realizarVenta(clienteBasico, pedido);

        assertEquals(121.0, factura.getTotalFinal(), 0.001,
                "Sin descuento, el total de la factura debe ser el precio final del producto");
    }

    /** Verifica que el descuento VIP del 10% (5 años + VIP) se aplica correctamente. */
    @Test
    @DisplayName("TC-INT-04: Descuento VIP (10%) se aplica sobre el total del pedido")
    void testDescuentoVipAplicado() {
        Pedido pedido = new Pedido(clienteVip);
        pedido.agregarProducto(new ProductoDigital("Software", 100.0, 1.0, "GENERAL"));

        Factura factura = tienda.realizarVenta(clienteVip, pedido);

        assertEquals(108.9, factura.getTotalFinal(), 0.001,
                "Con 10% de descuento VIP, 121€ deben quedar en 108.9€");
        assertEquals(12.1, factura.getDescuentoAplicado(), 0.001,
                "El descuento aplicado debe ser 12.1€ (10% de 121€)");
    }

    /** Verifica que los gastos de envío de productos físicos se reflejan correctamente en la factura. */
    @Test
    @DisplayName("TC-INT-05: Gastos de envío reflejados en la Factura")
    void testEnvioDesglosadoEnFactura() {
        Pedido pedido = new Pedido(clienteBasico);
        pedido.agregarProducto(new ProductoFisico("Teclado", 50.0, 1.0, "España"));
        pedido.agregarProducto(new ProductoFisico("Monitor", 80.0, 3.0, "Francia"));

        Factura factura = tienda.realizarVenta(clienteBasico, pedido);

        assertEquals(5.0, factura.getTotalEnvio(), 0.001,
                "El total de envío debe ser 5€ (0€ España + 5€ Francia)");
    }

    /** Verifica que la fecha de emisión de la factura no es nula. */
    @Test
    @DisplayName("TC-INT-06: Fecha de emisión de la Factura no es nula")
    void testFechaEmisionNoNula() {
        Pedido pedido = new Pedido(clienteBasico);
        pedido.agregarProducto(new ProductoDigital("App", 10.0, 1.0));

        Factura factura = tienda.realizarVenta(clienteBasico, pedido);

        assertNotNull(factura.getFechaEmision(), "La fecha de emisión no puede ser nula");
    }

    /** Verifica que el cliente almacenado en la factura coincide con el del pedido. */
    @Test
    @DisplayName("TC-INT-07: Cliente en Factura es el mismo que el del pedido")
    void testClienteFactura() {
        Pedido pedido = new Pedido(clienteVip);
        pedido.agregarProducto(new ProductoDigital("Licencia", 30.0, 1.0));

        Factura factura = tienda.realizarVenta(clienteVip, pedido);

        assertEquals(clienteVip.getNombre(), factura.getCliente().getNombre(),
                "El nombre del cliente en la factura debe coincidir");
    }

    /** Flujo E2E completo: crea cliente VIP, dos productos, pedido y verifica la factura generada. */
    @Test
    @DisplayName("TC-INT-08: Flujo E2E completo — compra → factura")
    void testFlujoEndToEnd() {
        Cliente cliente = new Cliente(10, "Carlos Ruiz", "carlos@test.com",
                "Plaza Mayor 1", 3, true, "España");
        ProductoDigital software    = new ProductoDigital("Adobe CC", 50.0, 2048.0, "GENERAL");
        ProductoFisico  auriculares = new ProductoFisico("Sony WH-1000XM5", 300.0, 0.25, "España");

        Pedido pedido = new Pedido(cliente);
        pedido.agregarProducto(software);
        pedido.agregarProducto(auriculares);

        Factura factura = tienda.realizarVenta(cliente, pedido);

        assertNotNull(factura.getCodigoFactura());
        assertTrue(factura.getTotalFinal() > 0, "El total final debe ser positivo");
        assertTrue(factura.getDescuentoAplicado() > 0,
                "El descuento VIP + 3 años debe generar un descuento positivo");
        assertEquals(cliente.getNombre(), factura.getCliente().getNombre());
        assertEquals(0.0, factura.getTotalEnvio(), 0.001, "El envío a España es 0€");
        assertDoesNotThrow(factura::imprimirFactura);
    }

    /** Verifica que con pedido mixto y cliente VIP el total final + descuento equals el total bruto. */
    @Test
    @DisplayName("TC-INT-09: Factura con pedido mixto y descuento aplicado")
    void testFacturaPedidoMixtoConDescuento() {
        Pedido pedido = new Pedido(clienteVip);
        pedido.agregarProducto(new ProductoDigital("Ebook", 20.0, 1.0, "SUPER"));
        pedido.agregarProducto(new ProductoFisico("USB Hub", 15.0, 0.1, "España"));

        Factura factura = tienda.realizarVenta(clienteVip, pedido);

        assertEquals(35.8, factura.getTotalFinal() + factura.getDescuentoAplicado(), 0.001,
                "El total final + descuento debe coincidir con el total bruto del pedido");
        assertTrue(factura.getDescuentoAplicado() > 0, "El descuento debe ser positivo para cliente VIP");
    }

    /** Verifica que cliente nulo en {@code realizarVenta} lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-INT-10 (Robustez): Cliente nulo en realizarVenta lanza excepción")
    void testRealizarVentaClienteNulo() {
        Pedido pedido = new Pedido(clienteBasico);
        pedido.agregarProducto(new ProductoDigital("App", 10.0, 1.0));

        assertThrows(IllegalArgumentException.class,
                () -> tienda.realizarVenta(null, pedido),
                "Cliente nulo debe lanzar IllegalArgumentException");
    }

    /** Verifica que pedido nulo en {@code realizarVenta} lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-INT-11 (Robustez): Pedido nulo en realizarVenta lanza excepción")
    void testRealizarVentaPedidoNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> tienda.realizarVenta(clienteBasico, null),
                "Pedido nulo debe lanzar IllegalArgumentException");
    }

    /** Verifica que un pedido vacío en {@code realizarVenta} lanza {@link IllegalStateException}. */
    @Test
    @DisplayName("TC-INT-12 (Robustez): Pedido vacío en realizarVenta lanza excepción")
    void testRealizarVentaPedidoVacio() {
        Pedido pedidoVacio = new Pedido(clienteBasico);

        assertThrows(IllegalStateException.class,
                () -> tienda.realizarVenta(clienteBasico, pedidoVacio),
                "Pedido vacío debe lanzar IllegalStateException");
    }

    /** Verifica que nombre de tienda vacío en el constructor lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-INT-13 (Robustez): Tienda con nombre vacío lanza excepción")
    void testTiendaNombreVacio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Tienda(""),
                "Nombre de tienda vacío debe lanzar IllegalArgumentException");
    }

    /** Verifica que dos ventas consecutivas generan códigos de factura únicos. */
    @Test
    @DisplayName("TC-INT-14: Dos ventas generan códigos de factura distintos")
    void testCodigosFacturaUnicos() {
        Pedido p1 = new Pedido(clienteBasico);
        p1.agregarProducto(new ProductoDigital("App1", 10.0, 1.0));

        Pedido p2 = new Pedido(clienteBasico);
        p2.agregarProducto(new ProductoDigital("App2", 20.0, 1.0));

        Factura f1 = tienda.realizarVenta(clienteBasico, p1);
        Factura f2 = tienda.realizarVenta(clienteBasico, p2);

        assertNotEquals(f1.getCodigoFactura(), f2.getCodigoFactura(),
                "Dos facturas distintas deben tener códigos únicos");
    }

    /** Regresión: verifica que calcularTotal sigue siendo precio + envío como en la Parte 3. */
    @Test
    @DisplayName("TC-REG-01 (Regresión): calcularTotal de Pedido funciona como en Parte 3")
    void testRegresionCalcularTotal() {
        Pedido pedido = new Pedido(clienteBasico);
        pedido.agregarProducto(new ProductoFisico("Teclado", 50.0, 1.0, 5.0));
        assertEquals(55.0, pedido.calcularTotal(), 0.001,
                "REGRESIÓN: calcularTotal debe seguir siendo precio + envío");
    }

    /** Regresión: verifica que un pedido vacío sigue lanzando {@link IllegalStateException}. */
    @Test
    @DisplayName("TC-REG-02 (Regresión): Pedido vacío sigue lanzando excepción")
    void testRegresionPedidoVacio() {
        Pedido pedido = new Pedido(clienteBasico);
        assertThrows(IllegalStateException.class, pedido::calcularTotal,
                "REGRESIÓN: Pedido vacío debe seguir lanzando IllegalStateException");
    }

    /** Regresión: verifica que un precio negativo sigue lanzando {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-REG-03 (Regresión): Precio negativo sigue lanzando excepción")
    void testRegresionPrecioNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProductoDigital("Test", -1.0, 1.0),
                "REGRESIÓN: Precio negativo debe seguir lanzando IllegalArgumentException");
    }
}
