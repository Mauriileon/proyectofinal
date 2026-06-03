package com.proyecto;

public class ProductoFisico extends Producto {

    private static final double ENVIO_ESPANIA = 0.0;
    private static final double ENVIO_EUROPA  = 5.0;
    private static final double ENVIO_RESTO   = 10.0;

    private double peso;
    private double costeEnvio;

    public ProductoFisico(String nombre, double precio, double peso, String paisDestino) {
        super(nombre, precio);
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo.");
        }
        this.peso       = peso;
        this.costeEnvio = calcularCosteEnvio(paisDestino);
    }

    public ProductoFisico(String nombre, double precio, double peso, double costeEnvio) {
        super(nombre, precio);
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo.");
        }
        if (costeEnvio < 0) {
            throw new IllegalArgumentException("El coste de envío no puede ser negativo.");
        }
        this.peso       = peso;
        this.costeEnvio = costeEnvio;
    }

    public static double calcularCosteEnvio(String pais) {
        if (pais == null) {
            return ENVIO_RESTO;
        }
        switch (pais.trim().toLowerCase()) {
            case "españa":
            case "espana":
            case "spain":
                return ENVIO_ESPANIA;
            case "francia":
            case "france":
            case "italia":
            case "italy":
            case "portugal":
                return ENVIO_EUROPA;
            default:
                return ENVIO_RESTO;
        }
    }

    @Override
    public double calcularPrecioFinal() {
        return precio + costeEnvio;
    }

    public double getPeso() { return peso; }

    public void setPeso(double peso) { this.peso = peso; }

    public double getCosteEnvio() { return costeEnvio; }

    public void setCosteEnvio(double costeEnvio) {
        if (costeEnvio < 0) {
            throw new IllegalArgumentException("El coste de envío no puede ser negativo.");
        }
        this.costeEnvio = costeEnvio;
    }

    @Override
    public String toString() {
        return "ProductoFisico{"
                + "nombre='" + nombre + '\''
                + ", precio=" + precio
                + ", peso=" + peso + "kg"
                + ", costeEnvio=" + costeEnvio
                + ", precioFinal=" + String.format("%.2f", calcularPrecioFinal())
                + '}';
    }
}
