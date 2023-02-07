package com.example.fragmentos.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentos.R;
import com.example.fragmentos.CarritoAdapter;

import java.util.ArrayList;
import java.util.List;


public class CarritoFragment extends Fragment {

    private RecyclerView recyclerView;
    private CarritoAdapter carritoAdapter;
    private List<Comida> listaCarrito;

    public CarritoFragment() {
        // Required empty public constructor
    }
    public static CarritoFragment newInstance() {
        CarritoFragment fragment = new CarritoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        listaCarrito = new ArrayList<>();
        carritoAdapter = new CarritoAdapter(listaCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(carritoAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Comida selectedFood = bundle.getParcelable("seleccion");
            listaCarrito.add(selectedFood);
            carritoAdapter.notifyDataSetChanged();
        }



      return view;
    }
}