package com.proyecto;

// Importaciones de JUnit 5 para testing
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Importa los métodos de aserción (assertEquals, assertThrows, etc.)
import static org.junit.jupiter.api.Assertions.*;

// Nombre descriptivo del conjunto de tests

@DisplayName("Tests de Pedido")
class PedidoTest {

    private Cliente clienteValido;

    @BeforeEach
    void setUp() {
        clienteValido = new Cliente("Ana García", "ana@email.com", "Calle Mayor 1");
    }

    @Test
    @DisplayName("TC-PED-01: Total con productos mixtos es correcto")
    void testTotalConProductosMixtos() {

        // Arrange
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("Ebook", 100.0, 5.0));
        pedido.agregarProducto(new ProductoFisico("Teclado", 50.0, 0.8, 5.0));

        // Act
        double resultado = pedido.calcularTotal();

        // Assert
        assertEquals(176.0, resultado, 0.001);
    }

    @Test
    @DisplayName("TC-PED-02: Lista no vacía tras agregar un producto")
    void testListaNoVaciaTrasAgregar() {

        // Arrange
        Pedido pedido = new Pedido(clienteValido);

        // Act
        pedido.agregarProducto(new ProductoDigital("App", 10.0, 1.0));

        // Assert
        assertFalse(pedido.getProductos().isEmpty());
    }

    @Test
    @DisplayName("TC-PED-03: Pedido vacío lanza IllegalStateException")
    void testPedidoVacioLanzaExcepcion() {

        // Arrange
        Pedido pedido = new Pedido(clienteValido);

        // Act + Assert
        assertThrows(IllegalStateException.class, () -> pedido.calcularTotal());
    }

    @Test
    @DisplayName("TC-PED-04: Cliente nulo lanza IllegalArgumentException")
    void testClienteNuloLanzaExcepcion() {

        // Arrange + Act + Assert
        assertThrows(IllegalArgumentException.class, () -> new Pedido(null));
    }

    @Test
    @DisplayName("TC-PED-05: Total mixto no es igual al total solo digital")
    void testTotalMixtoNoIgualSoloDigital() {

        // Arrange
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("Ebook", 100.0, 5.0));
        pedido.agregarProducto(new ProductoFisico("Teclado", 50.0, 0.8, 5.0));

        // Act
        double resultado = pedido.calcularTotal();

        // Assert
        assertNotEquals(90.0, resultado);
    }

    @ParameterizedTest(name = "precio={0}, envío={1} → total={2}")
    @CsvSource({
        "50.0,  5.0,  55.0",
        "100.0, 10.0, 110.0",
        "25.0,  0.0,  25.0",
        "80.0,  7.5,  87.5"
    })
    @DisplayName("TC-PED-06: Parametrizado - calcularTotal con distintos ProductoFisico")
    void testTotalParametrizadoFisico(double precio, double envio, double totalEsperado) {

        // Arrange
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoFisico("Producto", precio, 1.0, envio));

        // Act
        double resultado = pedido.calcularTotal();

        // Assert
        assertEquals(totalEsperado, resultado, 0.001);
    }

    @Test
    @DisplayName("TC-PED-07: toString de Pedido no es nulo")
    void testToStringPedido() {

        // Arrange
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("App", 10.0, 1.0));

        // Act
        String resultado = pedido.toString();

        // Assert
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("TC-PED-08: toString de ProductoDigital no es nulo")
    void testToStringProductoDigital() {

        // Arrange
        ProductoDigital producto = new ProductoDigital("App", 10.0, 1.0);

        // Act
        String resultado = producto.toString();

        // Assert
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("TC-PED-09: toString de ProductoFisico no es nulo")
    void testToStringProductoFisico() {

        // Arrange
        ProductoFisico producto = new ProductoFisico("Teclado", 50.0, 1.0, 5.0);

        // Act
        String resultado = producto.toString();

        // Assert
        assertNotNull(resultado);
    }

    @Test
    @DisplayName("TC-PED-10: resumenPedido no lanza excepción")
    void testResumenPedido() {

        // Arrange
        Pedido pedido = new Pedido(clienteValido);
        pedido.agregarProducto(new ProductoDigital("App", 10.0, 1.0));

        // Act + Assert
        assertDoesNotThrow(() -> pedido.resumenPedido());
    }

    @Test
    @DisplayName("TC-PED-11: Getters y setters de Cliente funcionan")
    void testGettersSettersCliente() {

        // Arrange
        Cliente cliente = clienteValido;

        // Act
        cliente.setNombre("Pedro");
        cliente.setCorreo("pedro@mail.com");
        cliente.setDireccion("Calle Nueva 5");

        // Assert
        assertEquals("Pedro", cliente.getNombre());
        assertEquals("pedro@mail.com", cliente.getCorreo());
        assertEquals("Calle Nueva 5", cliente.getDireccion());
    }

    @Test
    @DisplayName("TC-PED-12: Getters y setters de Pedido funcionan")
    void testGettersSettersPedido() {

        // Arrange
        ProductoDigital producto = new ProductoDigital("App", 10.0, 1.0);
        Pedido pedido = new Pedido(clienteValido, producto, 2);

        // Act
        pedido.setCantidad(5);
        pedido.setCliente(new Cliente("Pedro", "p@mail.com", "Calle 2"));

        // Assert
        assertEquals(producto, pedido.getProducto());
        assertEquals(5, pedido.getCantidad());
        assertEquals("Pedro", pedido.getCliente().getNombre());
    }
}