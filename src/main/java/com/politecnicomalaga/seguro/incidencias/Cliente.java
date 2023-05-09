/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.politecnicomalaga.seguro.incidencias;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author noelia
 */
public class Cliente implements Comparable<Cliente> {

    @Override
    public int compareTo(Cliente p) {
        int resultado = 0;

        //Como es ese paciente que me pasan con respcto al this?
        resultado = (this.apellidos + "," + this.nombre).compareTo(p.getApellidos() + "," + p.getNombre());

        return resultado;
    }

    public enum AtributosCliente {
        DNI, CODPOLIZA, NOMBRE, APELLIDOS, DIRECCION, EMAIL, TELEFONO
    };

    private String dni;
    private String codPoliza;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private int numIncidencias = 1;

    List<Incidencia> misIncidencias = new ArrayList<Incidencia>();

    public Cliente(String dni, String codPoliza, String nombre, String apellidos, String direccion, String email, String telefono) {
        this.dni = dni;
        this.codPoliza = codPoliza;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        numIncidencias = 1;
    }

    //Constructor para pasar de csv a string 
    public Cliente(String sCSV) {
        String[] lineas = sCSV.split("\n");
        //Me vendrá una línea mínimo para cliente
        String[] columnasCliente = lineas[0].split(";");
        if (columnasCliente[0].equals("Cliente")) {
            this.dni = columnasCliente[1];
            this.codPoliza = columnasCliente[2];
            this.nombre = columnasCliente[3];
            this.apellidos = columnasCliente[4];
            this.direccion = columnasCliente[5];
            this.email = columnasCliente[6];
            this.telefono = columnasCliente[7];
        } else {
            return;
        }

        //Después de 0 a n Incidencias
        misIncidencias = new ArrayList<>();

        //Si las líneas son más de 1... Hay Incidencias
        for (int i = 1; i < lineas.length; i++) {

            String[] columnasIncidencia = lineas[i].split(";");

            if (columnasIncidencia[0].equals("Incidencia")) {

                if (columnasIncidencia.length > 8 && columnasIncidencia[8].length() >= 1 && columnasIncidencia[8].length() <= 2) {
                    Incidencia in = new Incidencia_urgente(lineas[i]);
                    misIncidencias.add(in);
                } else if (columnasIncidencia.length > 8 && columnasIncidencia[8].length() > 2) {
                    Incidencia in = new Incidencia_ajena(lineas[i]);
                    misIncidencias.add(in);
                } else {
                    Incidencia in = new Incidencia(lineas[i]);
                    misIncidencias.add(in);
                }
            }
        }
    }

    //número de póliza del cliente, un guión y un id autonumérico generado por la aplicación    
    public String generarCodIncidencia() {
        String cadena = codPoliza + "-" + numIncidencias;
        numIncidencias++;
        return cadena;
    }

    public boolean nuevaIncidencia(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion) {

        if (fechaSuceso.equals("") || fechaSuceso.isEmpty()
                || hora.equals("") || hora.isEmpty()
                || matPropia.equals("") || matPropia.isEmpty()
                || matAjena.equals("") || matAjena.isEmpty()
                || descripcion.equals("") || descripcion.isEmpty()) {
            return false;
        } else {
            String codIncidencias = generarCodIncidencia();
            misIncidencias.add(new Incidencia(fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencias));
            return true;
        }
    }

    public boolean nuevaIncidenciaAjena(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion, String dniAjeno) {

        if (fechaSuceso.equals("") || fechaSuceso.isEmpty()
                || hora.equals("") || hora.isEmpty()
                || matPropia.equals("") || matPropia.isEmpty()
                || matAjena.equals("") || matAjena.isEmpty()
                || descripcion.equals("") || descripcion.isEmpty()) {
            return false;
        } else {
            String codIncidencias = generarCodIncidencia();
            misIncidencias.add(new Incidencia_ajena(fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencias, dniAjeno));
            return true;
        }
    }

    public boolean nuevaIncidenciaUrgente(String fechaSuceso, String hora, String matPropia, String matAjena, String descripcion, String diasMax) {

        if (fechaSuceso.equals("") || fechaSuceso.isEmpty()
                || hora.equals("") || hora.isEmpty()
                || matPropia.equals("") || matPropia.isEmpty()
                || matAjena.equals("") || matAjena.isEmpty()
                || descripcion.equals("") || descripcion.isEmpty()) {
            return false;
        } else {
            String codIncidencias = generarCodIncidencia();
            misIncidencias.add(new Incidencia_urgente(fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencias, diasMax));
            return true;
        }
    }

    public boolean cerrarIncidencia(String codIncidencia) {
        for (Incidencia incidencia : misIncidencias) {
            if (incidencia.getCodIncidencia().equals(codIncidencia)) {
                incidencia.setAbierta(false);
                return true;
            }
        }
        return false;
    }

    public Incidencia mostrarIncidenciaPorCodigo(String codIncidencia) {
        for (Incidencia incidencia : misIncidencias) {
            if (incidencia.getCodIncidencia().equals(codIncidencia)) {
                return incidencia;
            }
        }
        return null;
    }

    public Incidencia[] buscaIncidencias(String campoBusqueda, Incidencia.AtributosIncidencias atributoBusqueda) {
        ArrayList<Incidencia> resultado = new ArrayList<>();

        for (Incidencia t : misIncidencias) {
            if (t.compara(campoBusqueda, atributoBusqueda)) {
                resultado.add(t);
            }
        }

        if (resultado.size() > 0) {
            Incidencia[] listaT = new Incidencia[resultado.size()];
            return resultado.toArray(listaT);
        }
        return null;
    }

    public Incidencia[] todasIncidencias() {
        if (misIncidencias.size() == 0) {
            return null;
        }
        Incidencia[] listaT = new Incidencia[misIncidencias.size()];
        return misIncidencias.toArray(listaT);
    }

    public List<Incidencia> listarIncidencias() {
        return misIncidencias;
    }

    public Incidencia_urgente[] listarIncidenciasUrgentes() {
        ArrayList<Incidencia_urgente> resultado = new ArrayList<>();
        for (Incidencia i : misIncidencias) {
            if (i instanceof Incidencia_urgente) {
                Incidencia_urgente[] listaI = new Incidencia_urgente[resultado.size()];
                return resultado.toArray(listaI);
            }
        }
        return null;
    }

    public boolean compara(String campo, AtributosCliente at) {
        switch (at) {
            case DNI:
                return this.dni.contains(campo);
            case CODPOLIZA:
                return this.codPoliza.contains(campo);
            case NOMBRE:
                return this.nombre.contains(campo);
            case APELLIDOS:
                return this.apellidos.contains(campo);
            case DIRECCION:
                return this.direccion.contains(campo);
            case EMAIL:
                return this.email.contains(campo);
            case TELEFONO:
                return this.telefono.contains(campo);
        }

        return false;
    }

    public void setValor(String campo, AtributosCliente at) {
        switch (at) {
            case DNI:
                this.setDni(campo);
                break;
            case NOMBRE:
                this.setNombre(campo);
                break;
            case APELLIDOS:
                this.setApellidos(campo);
                break;
            case DIRECCION:
                this.setDireccion(campo);
                break;
            case EMAIL:
                this.setEmail(campo);
                break;
            case TELEFONO:
                this.setTelefono(campo);
                break;
        }
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCodPoliza() {
        return codPoliza;
    }

    public void setCodPoliza(String codPoliza) {
        this.codPoliza = codPoliza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Incidencia> getListaIncidencias() {
        return misIncidencias;
    }

    @Override
    public String toString() {
        return String.format("%10s#%10s#%10s#%10s#%20s#%10s", dni, codPoliza, nombre, apellidos, direccion, email, telefono);
    }

    public String toCSV() {
        String cadena = String.format("Cliente;%s;%s;%s;%s;%s;%s;%s\n", dni, codPoliza, nombre, apellidos, direccion, email, telefono);
        for (Incidencia i : this.misIncidencias) {
            cadena += i.toCSV();
        }
        return cadena;
    }

}
