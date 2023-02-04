package com.example.fragmentos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class AdaptadorHome extends RecyclerView.Adapter<AdaptadorHome.OfertaViewHolder> {

    private List<Ofertas_Home> oferta;

    public AdaptadorHome(List<Ofertas_Home> oferta) {
        this.oferta = oferta;
    }

    @NonNull
    @Override
    public OfertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_principal, parent, false);

        return new OfertaViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull OfertaViewHolder holder, int position) {
        Ofertas_Home ofer = oferta.get(position);
        holder.nombre.setText(ofer.getNombre());
        holder.precio.setText(ofer.getPrecio());
        Glide.with(holder.itemView.getContext()).load(ofer.getImagen()).into(holder.imagen);

    }


    @Override
    public int getItemCount() {
        return oferta.size();
    }

    public void setResults(List<Ofertas_Home> ofertas) {
        this.oferta.addAll(ofertas);
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
