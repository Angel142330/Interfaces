package com.example.fragmentos.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentos.AdaptadorCarrito;
import com.example.fragmentos.MainActivity;
import com.example.fragmentos.R;
import com.example.fragmentos.SharedViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

import java.util.List;
public class CartaFragment extends Fragment {
    private ArrayList<Comida> comidas;
    private RecyclerView recyclerView;
    private AdaptadorComidas adaptador;

    private AdaptadorCarrito adaptador_carrito;
    private SearchView searchView;
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;

    private SharedViewModel viewModel;

    public CartaFragment() {
        // Required empty public constructor
    }

    public static CartaFragment newInstance() {
        return new CartaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setStatusBarColor();

        View view = inflater.inflate(R.layout.fragment_carta, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerView_carta);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txtBuscar) {
                adaptador.filtrado(txtBuscar);
                return false;
            }
        });

        db = FirebaseFirestore.getInstance();
        comidas = new ArrayList<>();

        CollectionReference comidasRef = db.collection("carta");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();

        comidasRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Comida comida = new Comida();
                    comida.setNombre(document.get("Plato").toString());
                    comida.setImagen(document.get("Imagen").toString());
                    comida.setPrecio(document.get("Precio").toString());
                    comidas.add(comida);
                }





                adaptador = new AdaptadorComidas(comidas, getContext());
                adaptador.setOnItemClickListener(new AdaptadorComidas.OnItemClickListener() {
                    @Override
                    public void onItemClick(Comida comida) {
                        ArrayList<Comida> listaCarrito = viewModel.listaCarrito.getValue();
                        listaCarrito.add(comida);
                        viewModel.listaCarrito.setValue(listaCarrito);
                        Toast.makeText(getContext(), comida.getNombre()+" se ha añadido al carrito", Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerView.setAdapter(adaptador);
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        return view;


    }
    private void setStatusBarColor(){
        if(getActivity()!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.carta, this.getActivity().getTheme()));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.carta, this.getActivity().getTheme()));
            }
        }
    }

}