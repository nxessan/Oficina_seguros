package com.politecnicomalaga.seguro.incidencias;

/**
 *
 * @author noelia
 */
public class Incidencia {

    public enum AtributosIncidencias {
        FECHASUCESO, HORA, MATPROPIA, MATAJENA, DESCRIPCION, ABIERTA, CODINCIDENCIA
    };

    protected String fechaSuceso;
    protected String hora;
    protected String matPropia;
    protected String matAjena;
    protected String descripcion;
    protected boolean abierta = true;
    protected String codIncidencia;

    public Incidencia(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion, String codIncidencia) {
        this.fechaSuceso = fechaSuceso;
        this.hora = hora;
        this.matPropia = matPropia;
        this.matAjena = matAjena;
        this.descripcion = descripcion;
        this.abierta = true;
        this.codIncidencia = codIncidencia;
    }

    public Incidencia(String sCSV) {
        String[] columnas = sCSV.split(";");

        if (columnas[0].equals("Tratamiento")) {
            this.fechaSuceso = columnas[1];
            this.hora = columnas[2];
            this.matPropia = columnas[3];
            this.matAjena = columnas[4];
            this.descripcion = columnas[5];
            this.descripcion = columnas[6];
            this.abierta = Boolean.parseBoolean(columnas[7]);

        } else {
            this.fechaSuceso = "";
            this.hora = "";
            this.matPropia = "";
            this.matAjena = "";
            this.descripcion = "";
            this.abierta = false;
        }
    }

    public boolean compara(String campo, AtributosIncidencias at) {
        char comparador;
        String dato;
        switch (at) {
            case FECHASUCESO:
                return this.fechaSuceso.contains(campo);
            case HORA:
                return this.hora.contains(campo);
            case MATPROPIA:
                return this.matPropia.contains(campo);
            case MATAJENA:
                return this.matAjena.contains(campo);
            case DESCRIPCION:
                return this.descripcion.contains(campo);
            case ABIERTA:
                if (campo.equals("true")) {
                    return abierta;
                } else {
                    return !abierta;
                }
        }
        return false;
    }

    public String getFechaSuceso() {
        return fechaSuceso;
    }

    public void setFechaSuceso(String fechaSuceso) {
        this.fechaSuceso = fechaSuceso;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMatPropia() {
        return matPropia;
    }

    public void setMatPropia(String matPropia) {
        this.matPropia = matPropia;
    }

    public String getMatAjena() {
        return matAjena;
    }

    public void setMatAjena(String matAjena) {
        this.matAjena = matAjena;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public String getCodIncidencia() {
        return codIncidencia;
    }
    
    @Override
    public String toString() {
        return String.format("%10s#%10s#%10s#%10s#%10s#%10b#%10s", fechaSuceso, hora, matPropia, matAjena, descripcion, abierta, codIncidencia);
    }

    public String toCSV() {
        return String.format("Incidencia;%s;%s;%s;%s;%s;%b;%s\n", fechaSuceso, hora, matPropia, matAjena, descripcion, abierta, codIncidencia);
    }
}
