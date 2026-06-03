package com.proyecto;

/**
 * Clase base abstracta que representa un producto del sistema.
 */
public abstract class Producto {

    protected int    id;
    protected String nombre;
    protected double precio;

    /**
     * @param nombre nombre del producto
     * @param precio precio base; debe ser >= 0
     * @throws IllegalArgumentException si el precio es negativo
     */
    public Producto(String nombre, double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Calcula el precio final del producto según su tipo (IVA o envío).
     * @return precio final calculado
     */
    abstract double calcularPrecioFinal();

    /** @return identificador del producto */
    public int getId() { return id; }

    /** @return nombre del producto */
    public String getNombre() { return nombre; }

    /** @param nombre nuevo nombre */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return precio base del producto */
    public double getPrecio() { return precio; }

    /**
     * @param precio nuevo precio base; debe ser >= 0
     * @throws IllegalArgumentException si el precio es negativo
     */
    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{nombre='" + nombre + "', precio=" + precio + "}";
    }
}
