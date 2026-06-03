package com.proyecto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de ProductoDigital")
class ProductoDigitalTest {

    @Test
    @DisplayName("TC-PD-01: Precio final aplica descuento del 10%")
    void testDescuento10PorCiento() {
        ProductoDigital producto = new ProductoDigital("Ebook", 100.0, 5.0);
        assertEquals(90.0, producto.calcularPrecioFinal(), 0.001);
    }

    @Test
    @DisplayName("TC-PD-02: Precio base 0 produce precio final 0")
    void testPrecioCero() {
        ProductoDigital producto = new ProductoDigital("Gratis", 0.0, 1.0);
        assertEquals(0.0, producto.calcularPrecioFinal(), 0.001);
    }

    @Test
    @DisplayName("TC-PD-03: Precio negativo lanza IllegalArgumentException")
    void testPrecioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProductoDigital("Inválido", -10.0, 5.0));
    }

    @Test
    @DisplayName("TC-PD-04: El precio final no es igual al precio base")
    void testPrecioFinalNoIgualAlBase() {
        ProductoDigital producto = new ProductoDigital("Juego", 60.0, 15.0);
        assertNotEquals(60.0, producto.calcularPrecioFinal());
    }

    @Test
    @DisplayName("TC-PD-05: AssertFalse - precio final no es el precio base")
    void testPrecioFinalNoesPrecioBase() {
        ProductoDigital producto = new ProductoDigital("Música", 100.0, 3.0);
        assertFalse(producto.calcularPrecioFinal() == 100.0);
    }

    @ParameterizedTest(name = "precio={0} → esperado={1}")
    @CsvSource({
        "10.0,   9.0",
        "50.0,  45.0",
        "100.0, 90.0",
        "200.0, 180.0"
    })
    @DisplayName("TC-PD-06: Parametrizado - descuento 10% con diferentes precios")
    void testDescuentoParametrizado(double precioBase, double precioEsperado) {
        ProductoDigital producto = new ProductoDigital("Producto", precioBase, 1.0);
        assertEquals(precioEsperado, producto.calcularPrecioFinal(), 0.001);
    }

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
