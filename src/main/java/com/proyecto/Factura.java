package com.proyecto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public class Factura {

    // ── Atributos ──────────────────────────────────────────────────────────
    private final String    codigoFactura;
    private final LocalDate fechaEmision;
    private final Cliente   cliente;
    private final Pedido    pedido;
    private final double    totalNeto;
    private final double    totalIva;
    private final double    totalEnvio;
    private final double    descuentoAplicado;
    private final double    totalFinal;

    
    public Factura(Cliente cliente, Pedido pedido, LocalDate fechaEmision,
                   double totalNeto, double totalIva, double totalEnvio,
                   double descuentoAplicado, double totalFinal) {
        this.codigoFactura     = generarCodigo();
        this.cliente           = cliente;
        this.pedido            = pedido;
        this.fechaEmision      = fechaEmision;
        this.totalNeto         = totalNeto;
        this.totalIva          = totalIva;
        this.totalEnvio        = totalEnvio;
        this.descuentoAplicado = descuentoAplicado;
        this.totalFinal        = totalFinal;
    }

   
    private String generarCodigo() {
        return "FAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }


    public void imprimirFactura() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.println("│                    FACTURA                      │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf( "│  Código  : %-38s│%n", codigoFactura);
        System.out.printf( "│  Fecha   : %-38s│%n", fechaEmision.format(fmt));
        System.out.printf( "│  Cliente : %-38s│%n", cliente.getNombre());
        System.out.printf( "│  Pedido  : #%-37d│%n", pedido.getIdPedido());
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf( "│  Base neta          : %25.2f €│%n", totalNeto);
        System.out.printf( "│  IVA                : %25.2f €│%n", totalIva);
        System.out.printf( "│  Gastos de envío    : %25.2f €│%n", totalEnvio);
        System.out.printf( "│  Descuento fidelidad: %25.2f €│%n", descuentoAplicado);
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf( "│  TOTAL FINAL        : %25.2f €│%n", totalFinal);
        System.out.println("└─────────────────────────────────────────────────┘");
    }

    public String getCodigoFactura() { return codigoFactura; }

    public LocalDate getFechaEmision() { return fechaEmision; }

   
    public Cliente getCliente() { return cliente; }

   
    public Pedido getPedido() { return pedido; }

    
    public double getTotalNeto() { return totalNeto; }

    public double getTotalIva() { return totalIva; }

  
    public double getTotalEnvio() { return totalEnvio; }

    public double getDescuentoAplicado() { return descuentoAplicado; }

  

    public double getTotalFinal() { return totalFinal; }

    // ── toString ───────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "Factura{"
                + "codigo='" + codigoFactura + '\''
                + ", cliente='" + cliente.getNombre() + '\''
                + ", totalFinal=" + String.format("%.2f", totalFinal)
                + '}';
    }
}
