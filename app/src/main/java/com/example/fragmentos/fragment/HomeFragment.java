package com.example.fragmentos.fragment;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.fragmentos.AdaptadorHome;
import com.example.fragmentos.AdaptadorPager;
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
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdaptadorHome adaptador;
    private List<Ofertas_Home> list_oferta;
    ArrayList<Ofertas_Home> arrayLista_oferta;
    FirebaseFirestore db;
    private ProgressDialog progressDialog;
    ViewPager viewPager;
    private int dotscount;
    private ImageView[] dots;
    private int NUM_PAGES = 0;
    private int currentPage = 0;
    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};


    public static Fragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setStatusBarColor();

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.view_pager);

        AdaptadorPager viewPagerAdapter = new AdaptadorPager(getContext());
        viewPager.setAdapter(viewPagerAdapter);
        NUM_PAGES = viewPagerAdapter.getCount();

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
//            sliderDots.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        // Create a timer to automatically slide through the images
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);


        arrayLista_oferta = new ArrayList<>();
        list_oferta = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView_home);
        recyclerView.setLayoutManager(new

                LinearLayoutManager(getContext()));

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

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == NUM_PAGES - 1) {
                            currentPage = 0;
                        } else {
                            currentPage++;
                        }
                        viewPager.setCurrentItem(currentPage);
                    }
                });
            }
        }
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