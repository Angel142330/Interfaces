package com.example.fragmentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fragmentos.fragment.HomeFragment;
import com.example.fragmentos.fragment.CartaFragment;
import com.example.fragmentos.fragment.CarritoFragment;
import com.example.fragmentos.fragment.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bnvMenu;
    private Fragment fragment;
    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initValues();
        initListener();

    }

    private void initView(){
        bnvMenu=findViewById(R.id.bnvMenu);
    }

    private void initValues(){
        manager=getSupportFragmentManager();
        cargarPrimerFragment();
    }

    private void initListener(){
        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idMenu= item.getItemId();

                switch (idMenu){
                    case R.id.menu_home:
                        //getSupportActionBar().setTitle("Home");
                        fragment= CartaFragment.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.menu_dashboard:
                        //getSupportActionBar().setTitle("Dashboard");
                        fragment= HomeFragment.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.carrito:
                        //getSupportActionBar().setTitle("Carrito");
                        fragment= CarritoFragment.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.person:
                        //getSupportActionBar().setTitle("perfil");
                        fragment= PerfilFragment.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.settings:
                        //getSupportActionBar().setTitle("ajustes");
                       // fragment= CarritoFragment.newInstance();
                       // openFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    private void openFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.frameContainer,fragment);
        transaction.commit();
    }

    private void cargarPrimerFragment(){
       // getSupportActionBar().setTitle("Home");
        fragment= HomeFragment.newInstance();
        openFragment(fragment);
    }

}