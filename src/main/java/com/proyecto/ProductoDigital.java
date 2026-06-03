package com.proyecto;

/**
 * Producto descargable con aplicación de IVA según su tipo (GENERAL, REDUCIDO o SUPER).
 */
public class ProductoDigital extends Producto {

    private static final double IVA_GENERAL  = 0.21;
    private static final double IVA_REDUCIDO = 0.10;
    private static final double IVA_SUPER    = 0.04;

    private double tamañoMB;
    private String tipoIva;

    /**
     * @param nombre    nombre del producto
     * @param precio    precio base
     * @param tamañoMB  tamaño del archivo en megabytes
     * @param tipoIva   tipo de IVA: GENERAL, REDUCIDO o SUPER
     * @throws IllegalArgumentException si el tipo de IVA es inválido o nulo
     */
    public ProductoDigital(String nombre, double precio, double tamañoMB, String tipoIva) {
        super(nombre, precio);
        this.tamañoMB = tamañoMB;
        this.tipoIva  = validarTipoIva(tipoIva);
    }

    /**
     * Constructor con IVA GENERAL por defecto.
     * @param nombre   nombre del producto
     * @param precio   precio base
     * @param tamañoMB tamaño del archivo en megabytes
     */
    public ProductoDigital(String nombre, double precio, double tamañoMB) {
        this(nombre, precio, tamañoMB, "GENERAL");
    }

    /**
     * Calcula el precio aplicando el tipo de IVA indicado.
     * @param tipoIva tipo de IVA a aplicar (GENERAL, REDUCIDO o SUPER)
     * @return precio con IVA incluido
     * @throws IllegalArgumentException si el tipo de IVA es inválido o nulo
     */
    public double aplicarIVA(String tipoIva) {
        double tasaIva = obtenerTasa(validarTipoIva(tipoIva));
        return precio + (precio * tasaIva);
    }

    /**
     * Devuelve el precio base con el IVA del constructor aplicado.
     * @return precio final con IVA
     */
    @Override
    public double calcularPrecioFinal() {
        return aplicarIVA(this.tipoIva);
    }

    private String validarTipoIva(String tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de IVA no puede ser nulo.");
        }
        String tipoUpper = tipo.toUpperCase();
        if (!tipoUpper.equals("GENERAL") && !tipoUpper.equals("REDUCIDO") && !tipoUpper.equals("SUPER")) {
            throw new IllegalArgumentException(
                    "Tipo de IVA inválido: '" + tipo + "'. Use GENERAL, REDUCIDO o SUPER.");
        }
        return tipoUpper;
    }

    private double obtenerTasa(String tipo) {
        switch (tipo) {
            case "GENERAL":  return IVA_GENERAL;
            case "REDUCIDO": return IVA_REDUCIDO;
            case "SUPER":    return IVA_SUPER;
            default:         return IVA_GENERAL;
        }
    }

    public double getTamañoMB() { return tamañoMB; }

    public void setTamañoMB(double tamañoMB) { this.tamañoMB = tamañoMB; }

    public String getTipoIva() { return tipoIva; }

    @Override
    public String toString() {
        return "ProductoDigital{"
                + "nombre='" + nombre + '\''
                + ", precio=" + precio
                + ", tipoIva='" + tipoIva + '\''
                + ", tamañoMB=" + tamañoMB
                + ", precioFinal=" + String.format("%.2f", calcularPrecioFinal())
                + '}';
    }
}
