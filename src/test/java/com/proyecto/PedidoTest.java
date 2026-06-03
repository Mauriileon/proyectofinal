package com.proyecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios de {@link Pedido}.
 * Cubre cálculo de totales, gestión de productos, validaciones y regresión.
 */
@DisplayName("Tests de Pedido")
class PedidoTest {

    private Cliente clienteValido;

    /** Inicializa un cliente de prueba reutilizable en todos los tests. */
    @BeforeEach
    void setUp() {
        clienteValido = new Cliente("Ana García", "ana@email.com", "Calle Mayor 1");
    }

    /** Verifica que el total de un pedido mixto (digital + físico) es la suma de precios finales. */
    @Test
    @DisplayName("TC-PED-01: Total con productos mixtos es correcto")
    void testTotalConProductosMixtos() {
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("Ebook", 100.0, 5.0));
        pedido.agregarProducto(new ProductoFisico("Teclado", 50.0, 0.8, 5.0));

        double resultado = pedido.calcularTotal();

        assertEquals(176.0, resultado, 0.001);
    }

    /** Verifica que la lista de productos no está vacía tras añadir un producto. */
    @Test
    @DisplayName("TC-PED-02: Lista no vacía tras agregar un producto")
    void testListaNoVaciaTrasAgregar() {
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("App", 10.0, 1.0));
        assertFalse(pedido.getProductos().isEmpty());
    }

    /** Verifica que calcularTotal sobre un pedido vacío lanza {@link IllegalStateException}. */
    @Test
    @DisplayName("TC-PED-03: Pedido vacío lanza IllegalStateException")
    void testPedidoVacioLanzaExcepcion() {
        Pedido pedido = new Pedido(clienteValido);
        assertThrows(IllegalStateException.class, () -> pedido.calcularTotal());
    }

    /** Verifica que crear un pedido con cliente nulo lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-PED-04: Cliente nulo lanza IllegalArgumentException")
    void testClienteNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new Pedido(null));
    }

    /** Verifica que el total mixto es distinto al total de solo el producto digital. */
    @Test
    @DisplayName("TC-PED-05: Total mixto no es igual al total solo digital")
    void testTotalMixtoNoIgualSoloDigital() {
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("Ebook", 100.0, 5.0));
        pedido.agregarProducto(new ProductoFisico("Teclado", 50.0, 0.8, 5.0));
        assertNotEquals(90.0, pedido.calcularTotal());
    }

    /**
     * Verifica calcularTotal con distintos valores de precio y envío de {@link ProductoFisico}.
     * @param precio         precio base del producto
     * @param envio          coste de envío
     * @param totalEsperado  suma esperada
     */
    @ParameterizedTest(name = "precio={0}, envío={1} → total={2}")
    @CsvSource({
        "50.0,  5.0,  55.0",
        "100.0, 10.0, 110.0",
        "25.0,  0.0,  25.0",
        "80.0,  7.5,  87.5"
    })
    @DisplayName("TC-PED-06: Parametrizado - calcularTotal con distintos ProductoFisico")
    void testTotalParametrizadoFisico(double precio, double envio, double totalEsperado) {
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoFisico("Producto", precio, 1.0, envio));
        assertEquals(totalEsperado, pedido.calcularTotal(), 0.001);
    }

    /** Verifica que {@code toString()} de {@link Pedido} devuelve una cadena no nula. */
    @Test
    @DisplayName("TC-PED-07: toString de Pedido no es nulo")
    void testToStringPedido() {
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("App", 10.0, 1.0));
        assertNotNull(pedido.toString());
    }

    /** Verifica que {@code toString()} de {@link ProductoDigital} devuelve una cadena no nula. */
    @Test
    @DisplayName("TC-PED-08: toString de ProductoDigital no es nulo")
    void testToStringProductoDigital() {
        ProductoDigital producto = new ProductoDigital("App", 10.0, 1.0);
        assertNotNull(producto.toString());
    }

    /** Verifica que {@code toString()} de {@link ProductoFisico} devuelve una cadena no nula. */
    @Test
    @DisplayName("TC-PED-09: toString de ProductoFisico no es nulo")
    void testToStringProductoFisico() {
        ProductoFisico producto = new ProductoFisico("Teclado", 50.0, 1.0, 5.0);
        assertNotNull(producto.toString());
    }

    /** Verifica que {@code resumenPedido()} no lanza ninguna excepción. */
    @Test
    @DisplayName("TC-PED-10: resumenPedido no lanza excepción")
    void testResumenPedido() {
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("App", 10.0, 1.0));
        assertDoesNotThrow(() -> pedido.resumenPedido());
    }

    /** Verifica que los setters de {@link Cliente} actualizan los valores correctamente. */
    @Test
    @DisplayName("TC-PED-11: Getters y setters de Cliente funcionan")
    void testGettersSettersCliente() {
        Cliente cliente = clienteValido;
        cliente.setNombre("Pedro");
        cliente.setCorreo("pedro@mail.com");
        cliente.setDireccion("Calle Nueva 5");
        assertEquals("Pedro", cliente.getNombre());
        assertEquals("pedro@mail.com", cliente.getCorreo());
        assertEquals("Calle Nueva 5", cliente.getDireccion());
    }

    /** Verifica que los setters de {@link Pedido} actualizan los valores correctamente. */
    @Test
    @DisplayName("TC-PED-12: Getters y setters de Pedido funcionan")
    void testGettersSettersPedido() {
        ProductoDigital producto = new ProductoDigital("App", 10.0, 1.0);
        Pedido pedido = new Pedido(clienteValido, producto, 2);
        pedido.setCantidad(5);
        pedido.setCliente(new Cliente("Pedro", "p@mail.com", "Calle 2"));
        assertEquals(producto, pedido.getProducto());
        assertEquals(5, pedido.getCantidad());
        assertEquals("Pedro", pedido.getCliente().getNombre());
    }
}
