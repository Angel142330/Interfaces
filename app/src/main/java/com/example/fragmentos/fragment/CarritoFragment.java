package com.example.fragmentos.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmentos.AdaptadorCarrito;
import com.example.fragmentos.R;
import com.example.fragmentos.SharedViewModel;


import java.util.ArrayList;

public class CarritoFragment extends Fragment {

    private SharedViewModel viewModel;
    private RecyclerView recyclerView;
    private AdaptadorCarrito carritoAdapter;
    private ArrayList<Comida> listaCarrito;
    private TextView textViewTotal;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compra, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_carrito);
        listaCarrito = new ArrayList<>();
        carritoAdapter = new AdaptadorCarrito(listaCarrito, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(carritoAdapter);
        setStatusBarColor();
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        carritoAdapter.setOnItemClickListener(new AdaptadorCarrito.OnItemClickListener() {
            @Override
            public void onItemClick(Comida comida) {
                viewModel.deleteComida(comida);
            }
        });

        viewModel.listaCarrito.observe(getViewLifecycleOwner(), lista -> {
            listaCarrito.clear();
            listaCarrito.addAll(lista);
            carritoAdapter.notifyDataSetChanged();
            viewModel.calculatePrecioTotal();
        });

        textViewTotal = view.findViewById(R.id.textView_total);
        viewModel.precioTotal.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                textViewTotal.setText("Total: $" + aDouble);
            }
        });

        return view;
    }
    private void setStatusBarColor(){
        if(getActivity()!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.principal, this.getActivity().getTheme()));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.principal, this.getActivity().getTheme()));
            }
        }
    }
}
