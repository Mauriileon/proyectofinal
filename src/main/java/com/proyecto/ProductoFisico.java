package com.proyecto;

/**
 * Producto físico con coste de envío determinado por zona geográfica.
 */
public class ProductoFisico extends Producto {

    private static final double ENVIO_ESPANIA = 0.0;
    private static final double ENVIO_EUROPA  = 5.0;
    private static final double ENVIO_RESTO   = 10.0;

    private double peso;
    private double costeEnvio;

    /**
     * Constructor que calcula el coste de envío según el país de destino.
     * @param nombre       nombre del producto
     * @param precio       precio base
     * @param peso         peso en kg; debe ser >= 0
     * @param paisDestino  país de destino para calcular el envío
     * @throws IllegalArgumentException si el peso es negativo
     */
    public ProductoFisico(String nombre, double precio, double peso, String paisDestino) {
        super(nombre, precio);
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo.");
        }
        this.peso       = peso;
        this.costeEnvio = calcularCosteEnvio(paisDestino);
    }

    /**
     * Constructor con coste de envío explícito.
     * @param nombre     nombre del producto
     * @param precio     precio base
     * @param peso       peso en kg; debe ser >= 0
     * @param costeEnvio coste de envío; debe ser >= 0
     * @throws IllegalArgumentException si el peso o coste de envío son negativos
     */
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

    /**
     * Determina el coste de envío según el país: España=0€, Europa=5€, resto=10€.
     * @param pais país de destino
     * @return coste de envío en euros
     */
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

    /**
     * @return precio base más coste de envío
     */
    @Override
    public double calcularPrecioFinal() {
        return precio + costeEnvio;
    }

    /** @return peso del producto en kg */
    public double getPeso() { return peso; }

    /** @param peso nuevo peso en kg */
    public void setPeso(double peso) { this.peso = peso; }

    /** @return coste de envío en euros */
    public double getCosteEnvio() { return costeEnvio; }

    /**
     * @param costeEnvio nuevo coste de envío; debe ser >= 0
     * @throws IllegalArgumentException si el coste es negativo
     */
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
