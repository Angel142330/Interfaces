package com.example.fragmentos;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fragmentos.fragment.Comida;

import java.util.ArrayList;

public class SharedViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Comida>> listaCarrito = new MutableLiveData<>();

    public SharedViewModel() {
        listaCarrito.setValue(new ArrayList<>());
    }
}
