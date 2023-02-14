package com.example.fragmentos.fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.fragmentos.AdaptadorValorar;
import com.example.fragmentos.R;
import com.example.fragmentos.SharedViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class ValorarFragment extends Fragment implements OnMapReadyCallback{

        public ValorarFragment() {}
        private RecyclerView recyclerView;
        private AdaptadorValorar valorarAdapter;
        private ArrayList<Comida> listaCarrito;
        private ProgressDialog progressDialog;
        private  FirebaseFirestore db;
        private GoogleMap mGoogleMap;
        private MapView mapaView;

    public static ValorarFragment newInstance() {
            ValorarFragment fragment = new ValorarFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            setStatusBarColor();
            View view = inflater.inflate(R.layout.activity_valorar_fragment, container, false);
            listaCarrito = new ArrayList<>();
            recyclerView = view.findViewById(R.id.recyclerView_valorar);
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
                        Comida oferta = new Comida();
                        oferta.setNombre(document.get("Plato").toString());
                        oferta.setImagen(document.get("Imagen").toString());
                        oferta.setPrecio(document.get("Precio").toString());
                        listaCarrito.add(oferta);
                    }
                    valorarAdapter = new AdaptadorValorar(listaCarrito, getContext());
                    recyclerView.setAdapter(valorarAdapter);
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

            mapaView = view.findViewById(R.id.mapa);
            mapaView.onCreate(savedInstanceState);
            mapaView.getMapAsync(this);

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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        // Configura el mapa según tus necesidades
        // Por ejemplo:
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Coloca marcadores en el mapa
        // Por ejemplo:
        LatLng sydney = new LatLng(40.3807095, -3.7536673);
        mGoogleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapaView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapaView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapaView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapaView.onLowMemory();
    }
}


