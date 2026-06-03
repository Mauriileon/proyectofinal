package com.proyecto;

public class Cliente {

    private static final double DESCUENTO_POR_ANIO = 0.01;
    private static final double DESCUENTO_VIP = 0.05;
    private static final double DESCUENTO_MAXIMO = 0.20;

    private int    id;
    private String nombre;
    private String correo;
    private String direccion;
    private int    añosAntiguedad;
    private boolean esVip;
    private String pais;

    public Cliente(int id, String nombre, String correo, String direccion,
                   int añosAntiguedad, boolean esVip, String pais) {
        if (añosAntiguedad < 0) {
            throw new IllegalArgumentException("Los años de antigüedad no pueden ser negativos.");
        }
        this.id             = id;
        this.nombre         = nombre;
        this.correo         = correo;
        this.direccion      = direccion;
        this.añosAntiguedad = añosAntiguedad;
        this.esVip          = esVip;
        this.pais           = pais;
    }

    public Cliente(String nombre, String correo, String direccion) {
        this(0, nombre, correo, direccion, 0, false, "España");
    }

    public double calcularDescuentoFidelidad() {
        double descuento = añosAntiguedad * DESCUENTO_POR_ANIO;
        if (esVip) {
            descuento += DESCUENTO_VIP;
        }
        return Math.min(descuento, DESCUENTO_MAXIMO);
    }

    public int getId() {
         return id; }

    public String getNombre() {
         return nombre; }

    public void setNombre(String nombre) { 
        this.nombre = nombre; }

    public String getCorreo() { 
        return correo; }

    public void setCorreo(String correo) { 
        this.correo = correo; }

    public String getDireccion() {
         return direccion; }

    public void setDireccion(String direccion) {
         this.direccion = direccion; }

    public int getAñosAntiguedad() {
         return añosAntiguedad; }

    public boolean isEsVip() {
         return esVip; }

    public String getPais() {
         return pais; }

    @Override
    public String toString() {
        return "Cliente{"
                + "id=" + id
                + ", nombre='" + nombre + '\''
                + ", correo='" + correo + '\''
                + ", pais='" + pais + '\''
                + ", añosAntiguedad=" + añosAntiguedad
                + ", esVip=" + esVip
                + '}';
    }
}
