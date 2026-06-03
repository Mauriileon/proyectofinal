package com.proyecto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios de {@link ProductoFisico}.
 * Cubre cálculo de precio final, zonas de envío y validaciones de entrada.
 */
@DisplayName("Tests de ProductoFisico")
class ProductoFisicoTest {

    /** Verifica que el coste de envío explícito se suma al precio base. */
    @Test
    @DisplayName("TC-PF-01: Coste de envío se suma al precio base")
    void testCosteEnvioSumado() {
        ProductoFisico producto = new ProductoFisico("Teclado", 50.0, 1.2, 5.0);
        assertEquals(55.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que un coste de envío 0 no modifica el precio base. */
    @Test
    @DisplayName("TC-PF-02: Envío 0 no modifica el precio")
    void testSinCosteEnvio() {
        ProductoFisico producto = new ProductoFisico("Libro", 20.0, 0.5, 0.0);
        assertEquals(20.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que un precio negativo lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-PF-03: Precio negativo lanza excepción")
    void testPrecioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProductoFisico("Inválido", -5.0, 1.0, 3.0));
    }

    /** Verifica que un coste de envío negativo lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-PF-04: Coste de envío negativo lanza excepción")
    void testCosteEnvioNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProductoFisico("Ratón", 25.0, 0.3, -3.0));
    }

    /** Verifica que el precio final nunca es inferior al precio base. */
    @Test
    @DisplayName("TC-PF-05: El precio final nunca es menor al precio base")
    void testPrecioFinalNuncaMenorQueBase() {
        ProductoFisico producto = new ProductoFisico("Auriculares", 80.0, 0.4, 7.0);
        assertFalse(producto.calcularPrecioFinal() < producto.getPrecio());
    }

    /** Verifica que el envío a España es 0€ usando el constructor por país. */
    @Test
    @DisplayName("TC-PF-06: Envío España = 0€ con constructor por país")
    void testEnvioEspana() {
        ProductoFisico producto = new ProductoFisico("Camiseta", 50.0, 0.5, "España");
        assertEquals(50.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que el envío a Francia es 5€ usando el constructor por país. */
    @Test
    @DisplayName("TC-PF-07: Envío Francia = 5€ con constructor por país")
    void testEnvioFrancia() {
        ProductoFisico producto = new ProductoFisico("Monitor", 100.0, 2.0, "Francia");
        assertEquals(105.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que el envío al resto del mundo es 10€ usando el constructor por país. */
    @Test
    @DisplayName("TC-PF-09: Envío resto del mundo = 10€ con constructor por país")
    void testEnvioRestoMundo() {
        ProductoFisico producto = new ProductoFisico("Cámara", 200.0, 1.5, "Japón");
        assertEquals(210.0, producto.calcularPrecioFinal(), 0.001);
    }

    /** Verifica que un peso negativo lanza {@link IllegalArgumentException}. */
    @Test
    @DisplayName("TC-PF-10: Peso negativo lanza IllegalArgumentException")
    void testPesoNegativoLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new ProductoFisico("Roto", 30.0, -1.0, 3.0));
    }

    /** Verifica que los setters de {@link ProductoFisico} actualizan los valores correctamente. */
    @Test
    @DisplayName("Setters de ProductoFisico funcionan")
    void testSetters() {
        ProductoFisico p = new ProductoFisico("Teclado", 50.0, 1.0, 5.0);
        p.setNombre("Nuevo");
        p.setPrecio(90.0);
        p.setPeso(3.0);
        p.setCosteEnvio(8.0);
        assertEquals("Nuevo", p.getNombre());
        assertEquals(90.0, p.getPrecio(), 0.001);
        assertEquals(3.0, p.getPeso(), 0.001);
        assertEquals(8.0, p.getCosteEnvio(), 0.001);
    }
}
