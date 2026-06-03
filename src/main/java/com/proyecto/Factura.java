package com.proyecto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


/**
 * Documento de venta generado por {@link Tienda} con código único y desglose de importes.
 */
public class Factura {

    private final String    codigoFactura;
    private final LocalDate fechaEmision;
    private final Cliente   cliente;
    private final Pedido    pedido;
    private final double    totalNeto;
    private final double    totalIva;
    private final double    totalEnvio;
    private final double    descuentoAplicado;
    private final double    totalFinal;

    /**
     * Crea una factura y genera automáticamente su código único con prefijo FAC-.
     * @param cliente           cliente de la venta
     * @param pedido            pedido asociado
     * @param fechaEmision      fecha de emisión
     * @param totalNeto         base neta tras descuento y envío
     * @param totalIva          importe de IVA
     * @param totalEnvio        gastos de envío totales
     * @param descuentoAplicado importe de descuento por fidelidad
     * @param totalFinal        importe total a pagar
     */
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

    /** Imprime por consola la factura formateada con todos los importes. */
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

    /** @return código único de la factura con prefijo FAC- */
    public String getCodigoFactura() { return codigoFactura; }

    /** @return fecha de emisión de la factura */
    public LocalDate getFechaEmision() { return fechaEmision; }

    /** @return cliente asociado a la factura */
    public Cliente getCliente() { return cliente; }

    /** @return pedido asociado a la factura */
    public Pedido getPedido() { return pedido; }

    /** @return base neta tras aplicar descuento y descontar envío */
    public double getTotalNeto() { return totalNeto; }

    /** @return importe de IVA */
    public double getTotalIva() { return totalIva; }

    /** @return gastos de envío totales */
    public double getTotalEnvio() { return totalEnvio; }

    /** @return importe de descuento por fidelidad aplicado */
    public double getDescuentoAplicado() { return descuentoAplicado; }

    /** @return importe total final a pagar */
    public double getTotalFinal() { return totalFinal; }


    @Override
    public String toString() {
        return "Factura{"
                + "codigo='" + codigoFactura + '\''
                + ", cliente='" + cliente.getNombre() + '\''
                + ", totalFinal=" + String.format("%.2f", totalFinal)
                + '}';
    }
}
