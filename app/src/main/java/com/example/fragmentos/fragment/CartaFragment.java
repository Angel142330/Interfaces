package com.example.fragmentos.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragmentos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartaFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdaptadorComidas adaptador;
    private List<Comida> comidas;
    FirebaseFirestore db;
    private SearchView searchView;
    private ProgressDialog progressDialog;

    public static Fragment newInstance() {
        CartaFragment fragment = new CartaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carta, container, false);

        //Search
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


        recyclerView = view.findViewById(R.id.recyclerView_carta);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

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

                    comidas.add(comida);
                }
                adaptador = new AdaptadorComidas(comidas);
                recyclerView.setAdapter(adaptador);
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception error) {
                if (error != null) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
        return view;
    }
}