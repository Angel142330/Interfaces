package com.example.fragmentos.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fragmentos.Ofertas_Home;
import com.example.fragmentos.R;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class
AdaptadorComidas extends RecyclerView.Adapter<AdaptadorComidas.ComidaViewHolder> {
    private List<Comida> comidas;
    private List<Comida> comidas2;

    public AdaptadorComidas(List<Comida> comidas) {
        this.comidas = comidas;
        comidas2=new ArrayList<>();
        comidas2.addAll(comidas);
    }

    @Override
    public ComidaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comida, parent, false);
        return new ComidaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ComidaViewHolder holder, int position) {
        Comida comida = comidas.get(position);
        holder.textNombre.setText(comida.getNombre());
        Glide.with(holder.itemView.getContext()).load(comida.getImagen()).into(holder.imagenComida);
    }

    @Override
    public int getItemCount() {
        return comidas.size();
    }

    public void setResults(List<Comida> comidas) {
        this.comidas.addAll(comidas);
        notifyDataSetChanged();
    }

    public  void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            comidas.clear();
            comidas.addAll(comidas2);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Comida> collecion = comidas.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                comidas.clear();
                comidas.addAll(collecion);
            } else {
                for (Comida c : comidas2) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        comidas.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ComidaViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre;
        ImageView imagenComida;

        public ComidaViewHolder(View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.nombre);

            imagenComida = itemView.findViewById(R.id.image_producto);
        }
    }
}
