package com.proyecto;

public abstract class Producto {

    protected int    id;
    protected String nombre;
    protected double precio;

    public Producto(String nombre, double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    abstract double calcularPrecioFinal();

    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }

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
