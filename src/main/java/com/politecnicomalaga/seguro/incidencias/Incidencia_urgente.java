package com.politecnicomalaga.seguro.incidencias;

/**
 *
 * @author noelia
 */
public class Incidencia_urgente extends Incidencia {

    private String diasMax;

    public Incidencia_urgente(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion, String diasMax, String codIncidencia) {
        super(fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencia);
        this.diasMax = diasMax;
    }

    public Incidencia_urgente(String sCsv) {
        super(sCsv);
        String[] columnas = sCsv.split(";");

        if (columnas[0].equals("Incidencia")) {
            this.diasMax = columnas[8];
        }
    }

    @Override
    public String toString() {
        return super.toString()+ "Días Máximos: " + diasMax;
    }

    @Override
    public String toCSV() {
        String cadena = String.format("Incidencia;%s;%s;%s;%s;%s;%b;%s;%s\n", fechaSuceso, hora, matPropia, matAjena, descripcion, abierta, codIncidencia, diasMax);
        return cadena;
    }
}
