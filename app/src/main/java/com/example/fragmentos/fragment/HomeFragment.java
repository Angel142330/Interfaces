package com.example.fragmentos.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

import com.example.fragmentos.AdaptadorHome;
import com.example.fragmentos.Ofertas_Home;
import com.example.fragmentos.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdaptadorHome adaptador;
    private List<Ofertas_Home> list_oferta;
    ArrayList<Ofertas_Home> arrayLista_oferta;
    FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private SearchView searchView;


    public static Fragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        searchView = view.findViewById(R.id.search_view);
        arrayLista_oferta = new ArrayList<>();
        list_oferta = new ArrayList<>();

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


        recyclerView = view.findViewById(R.id.recyclerView_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        CollectionReference comidasRef = db.collection("carta");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando datos...");
        progressDialog.show();
        comidasRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    Ofertas_Home oferta = new Ofertas_Home();
                    oferta.setNombre(document.get("Plato").toString());
                    oferta.setImagen(document.get("Imagen").toString());

                    list_oferta.add(oferta);
                }
                adaptador = new AdaptadorHome(list_oferta);
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