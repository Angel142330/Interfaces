package com.example.fragmentos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class AdaptadorHome extends RecyclerView.Adapter<AdaptadorHome.OfertaViewHolder> {

    private List<Ofertas_Home> list_oferta;
    ArrayList<Ofertas_Home> arrayLista_Oferta;


    public AdaptadorHome(List<Ofertas_Home> list_oferta) {
        this.list_oferta = list_oferta;
        arrayLista_Oferta=new ArrayList<>();
        arrayLista_Oferta.addAll(list_oferta);

    }

    @NonNull
    @Override
    public OfertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oferta, parent, false);

        return new OfertaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull OfertaViewHolder holder, int position) {
        Ofertas_Home ofer = list_oferta.get(position);
        holder.nombre.setText(ofer.getNombre());
        holder.precio.setText(ofer.getPrecio());
        Glide.with(holder.itemView.getContext()).load(ofer.getImagen()).into(holder.imagen);

    }

    public  void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            list_oferta.clear();
            list_oferta.addAll(arrayLista_Oferta);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Ofertas_Home> collecion = list_oferta.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                list_oferta.clear();
                list_oferta.addAll(collecion);
            } else {
                for (Ofertas_Home c : arrayLista_Oferta) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        list_oferta.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list_oferta.size();
    }

    public void setResults(List<Ofertas_Home> ofertas) {
        this.list_oferta.addAll(ofertas);
        notifyDataSetChanged();
    }


    class OfertaViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView precio;
        private ImageView imagen;

        public OfertaViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombre);
            precio = itemView.findViewById(R.id.precio);
            imagen = itemView.findViewById(R.id.imagen);
        }
    }
}
