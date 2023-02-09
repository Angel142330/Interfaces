package com.example.fragmentos;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fragmentos.fragment.Comida;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Comida>> listaCarrito = new MutableLiveData<>();

    public MutableLiveData<Double> precioTotal = new MutableLiveData<>();

    ArrayList<Comida> lista;

    public SharedViewModel() {
        listaCarrito.setValue(new ArrayList<>());
        precioTotal.setValue(0.0);
    }
    public void calculatePrecioTotal() {
        ArrayList<Comida> lista = listaCarrito.getValue();
        Double total = 0.0;
        for (Comida c : lista) {
            total += Double.valueOf(c.getPrecio());
        }
        DecimalFormat df = new DecimalFormat("#.##");
        String precioFormateado = df.format(total);
        precioTotal.setValue(Double.parseDouble(precioFormateado));
    }


    public void addComida(Comida comida) {
        lista.add(comida);
        precioTotal.setValue(Double.valueOf(precioTotal.getValue() + comida.getPrecio()));
    }

    public void deleteComida(Comida comida) {
         lista = listaCarrito.getValue();
        lista.remove(comida);
        listaCarrito.setValue(lista);
    }
}
