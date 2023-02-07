package com.example.fragmentos;

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

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {
    private List<Comida> shoppingCartList;


    public CarritoAdapter(List<Comida> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comida, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comida food = shoppingCartList.get(position);
        holder.nombre.setText(food.getNombre());
        Glide.with(holder.itemView.getContext()).load(food.getImagen()).into(holder.imagen);


    }

    @Override
    public int getItemCount() {
        return shoppingCartList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            imagen = itemView.findViewById(R.id.image_producto);
        }
    }
}
