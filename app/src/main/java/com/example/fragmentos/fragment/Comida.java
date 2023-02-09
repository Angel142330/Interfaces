package com.example.fragmentos.fragment;



import java.io.Serializable;
import java.util.ArrayList;

public class Comida implements Serializable {
    private String nombre;

    private String imagen;
    private String precio;

    public Comida() {
    }

    public Comida(String nombre, String precio, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return nombre + "," + precio + "," + imagen;
    }

    public static Comida fromString(String comidaString) {
        String[] parts = comidaString.split(",");
        String nombre = parts[0];
        String precio = parts[1];
        String imagen = parts[2];
        return new Comida(nombre, precio, imagen);
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public void setPrecio(String precio){
        this.precio=precio;
    }
    public String getPrecio() {
        return precio;
    }
}
