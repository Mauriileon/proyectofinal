package com.proyecto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de Cliente")
class ClienteTest {

    @Test
    @DisplayName("Constructor asigna todos los campos")
    void testConstructor() {
        Cliente c = new Cliente("Ana", "ana@mail.com", "Calle 1");
        assertEquals("Ana", c.getNombre());
        assertEquals("ana@mail.com", c.getCorreo());
        assertEquals("Calle 1", c.getDireccion());
    }

    @Test
    @DisplayName("toString no es nulo")
    void testToString() {
        Cliente c = new Cliente("Ana", "ana@mail.com", "Calle 1");
        assertNotNull(c.toString());
    }
}