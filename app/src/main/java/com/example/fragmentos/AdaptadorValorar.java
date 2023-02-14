package com.example.fragmentos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fragmentos.fragment.Comida;

import java.util.List;

public class AdaptadorValorar extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {
        private final List<Comida> comidas;
        private final Context context;

        public interface OnItemClickListener {
            void onItemClick(Comida comida);
        }

        private AdaptadorCarrito.OnItemClickListener listener;
        public void setOnItemClickListener(AdaptadorCarrito.OnItemClickListener listener) {
            this.listener = listener;
        }

        public AdaptadorValorar(List<Comida> comidas, Context context) {
            this.comidas = comidas;
            this.context = context;
        }

        @NonNull
        @Override
        public AdaptadorCarrito.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_valorar, parent, false);
            return new AdaptadorCarrito.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.fragmentos.AdaptadorCarrito.ViewHolder holder, int position) {
            Comida comida = comidas.get(position);

            holder.nombre.setText(comida.getNombre());

            Glide.with(holder.itemView.getContext()).load(comida.getImagen()).into(holder.imagen);

        }

        @Override
        public int getItemCount() {
            return comidas.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            TextView nombre;
            ImageView imagen;
            TextView textViewPrecio;

            ViewHolder(View itemView) {
                super(itemView);
                textViewPrecio = itemView.findViewById(R.id.textView_productPrice);
                nombre = itemView.findViewById(R.id.textView_productName);
                imagen = itemView.findViewById(R.id.imageView_productImage);
            }
        }
}
