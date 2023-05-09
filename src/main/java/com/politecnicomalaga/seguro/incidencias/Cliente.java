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
public class Cliente {

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
        //Me vendrá una línea mínimo para paciente
        String[] columnas = lineas[0].split(";");
        if (columnas[0].equals("Cliente")) {
            this.dni = columnas[1];
            this.codPoliza = columnas[2];
            this.nombre = columnas[3];
            this.apellidos = columnas[4];
            this.direccion = columnas[5];
            this.email = columnas[6];
            this.telefono = columnas[7];
        } else {
            return;
        }

        //De 0 a n incidencias..
        misIncidencias = new ArrayList<>();

        //Si las líneas son más de 1... Hay incidencias
        for (int i = 1; i < lineas.length; i++) {
         //   String[] columnasIncidencia = sCSV.split(";");

            //Saber si es incidencia urgente lo sabré si el ultimo atributo tiene 1 o 2 numeros representando los dias
            if (columnas[0].equals("Incidencia")) {
                if (columnas.length > 8 && columnas[8].length() > 0 && columnas[8].length() <= 2) {
                    //trabajo la incidencia urgente
                    Incidencia in = new Incidencia_urgente(lineas[i]);
                    this.misIncidencias.add(in);
                    //Saber si es incidencia ajena lo sabré si el ultimo atributo tiene entre 8 o 9 numeros representando el dni con letra o sin letra
                } else if (columnas.length > 8 && columnas[8].length() >= 8 && columnas[8].length() <= 9) {
                    //trabajo la incidencia urgente
                    Incidencia in = new Incidencia_ajena(lineas[i]);
                    this.misIncidencias.add(in);

                } else {
                    //trabajo la incidencia normal
                    Incidencia in = new Incidencia(lineas[i]);
                    //lo pongo en la lista
                    this.misIncidencias.add(in);
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
            misIncidencias.add(new Incidencia(fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencias));
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
            misIncidencias.add(new Incidencia(fechaSuceso, hora, matPropia, matAjena, descripcion, codIncidencias));
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
