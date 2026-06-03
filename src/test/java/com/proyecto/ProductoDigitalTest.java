package com.proyecto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios y parametrizados de {@link ProductoDigital}.
 * Cubre aplicación de IVA, validaciones de precio y tipos de IVA.
 */
@DisplayName("Tests de ProductoDigital")
class ProductoDigitalTest {

    /** Verifica que IVA GENERAL del 21% se aplica correctamente sobre precio 100. */
    @Test
    @DisplayName("TC-PD-01: Precio final aplica IVA GENERAL del 21%")
    void testDescuento10PorCiento() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, 5.0);
        assertEquals(121.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que precio base 0 produce precio final 0 con cualquier tipo de IVA. */
    @Test
    @DisplayName("TC-PD-02: Precio base 0 produce precio final 0")
    void testPrecioCero() {
        ProductoDigital producto = new ProductoDigital("Gratis", 0.0, 1.0);
        assertEquals(0.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que un precio negativo lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-PD-03: Precio negativo lanza IllegalArgumentException")
    void testPrecioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProductoDigital("Inválido", -10.0, 5.0));
    }

    /** Verifica que el precio final con IVA es distinto al precio base. */
    @Test
    @DisplayName("TC-PD-04: El precio final no es igual al precio base")
    void testPrecioFinalNoIgualAlBase() {
        ProductoDigital producto = new ProductoDigital("Juego", 60.0, 15.0);
        assertNotEquals(60.0, producto.calcularPrecioFinal());
    }

    /** Verifica mediante {@code assertFalse} que el precio final supera al precio base. */
    @Test
    @DisplayName("TC-PD-05: AssertFalse - precio final no es el precio base")
    void testPrecioFinalNoesPrecioBase() {
        ProductoDigital producto = new ProductoDigital("Música", 100.0, 3.0);
        assertFalse(producto.calcularPrecioFinal() == 100.0);
    }

    /**
     * Verifica IVA GENERAL del 21% con cuatro precios distintos.
     * @param precioBase    precio base de entrada
     * @param precioEsperado precio final esperado tras aplicar el 21%
     */
    @ParameterizedTest(name = "precio={0} → esperado={1}")
    @CsvSource({
        "10.0,   12.1",
        "50.0,   60.5",
        "100.0, 121.0",
        "200.0, 242.0"
    })
    @DisplayName("TC-PD-06: Parametrizado - IVA GENERAL 21% con diferentes precios")
    void testDescuentoParametrizado(double precioBase, double precioEsperado) {
        ProductoDigital producto = new ProductoDigital("Producto", precioBase, 1.0);
        assertEquals(precioEsperado, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que IVA REDUCIDO del 10% se aplica correctamente sobre precio 100. */
    @Test
    @DisplayName("TC-PD-02: IVA REDUCIDO (10%) aplicado correctamente")
    void testIvaReducido() {
        ProductoDigital producto = new ProductoDigital("Software", 100.0, 1.0, "REDUCIDO");
        assertEquals(110.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que IVA SUPER del 4% se aplica correctamente sobre precio 100. */
    @Test
    @DisplayName("TC-PD-07: IVA SUPER (4%) aplicado correctamente")
    void testIvaSuper() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, 1.0, "SUPER");
        assertEquals(104.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que un tipo de IVA inválido lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-PD-10: Tipo IVA inválido lanza IllegalArgumentException")
    void testIvaInvalidoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProductoDigital("App", 50.0, 1.0, "INVALIDO"),
                "Tipo de IVA desconocido debe lanzar excepción");
    }

    /** Verifica que pasar {@code null} como tipo de IVA lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-PD-11: IVA nulo lanza IllegalArgumentException")
    void testIvaNuloLanzaExcepcion() {
        ProductoDigital producto = new ProductoDigital("App", 50.0, 1.0);
        assertThrows(IllegalArgumentException.class,
                () -> producto.aplicarIVA(null),
                "IVA nulo debe lanzar excepción");
    }

    /** Verifica que los setters de {@link ProductoDigital} actualizan los valores correctamente. */
    @Test
    @DisplayName("Setters de ProductoDigital funcionan")
    void testSetters() {
        ProductoDigital p = new ProductoDigital("App", 50.0, 10.0);
        p.setNombre("Nuevo");
        p.setPrecio(80.0);
        p.setTamañoMB(200.0);
        assertEquals("Nuevo", p.getNombre());
        assertEquals(80.0, p.getPrecio(), 0.001);
        assertEquals(200.0, p.getTamañoMB(), 0.001);
    }
}
