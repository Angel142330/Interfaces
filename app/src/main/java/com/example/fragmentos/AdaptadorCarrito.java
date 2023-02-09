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
import com.example.fragmentos.fragment.AdaptadorComidas;
import com.example.fragmentos.fragment.Comida;

import java.util.List;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {
    private SharedViewModel viewModel;
    private final List<Comida> comidas;
    private final Context context;

    public interface OnItemClickListener {
        void onItemClick(Comida comida);
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(AdaptadorCarrito.OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdaptadorCarrito(List<Comida> comidas, Context context) {
        this.comidas = comidas;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comida comida = comidas.get(position);

        holder.nombre.setText(comida.getNombre());

        Glide.with(holder.itemView.getContext()).load(comida.getImagen()).into(holder.imagen);
        holder.textViewPrecio.setText("$" + comida.getPrecio());

        final int pos=position;
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(comida);

                    }
                }
            }
        });

    }

    private void removeItem(int position) {
        comidas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, comidas.size());
    }


    @Override
    public int getItemCount() {
        return comidas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        ImageView imagen;
        TextView textViewPrecio;
        ImageView imageViewDelete;

        ViewHolder(View itemView) {
            super(itemView);
            textViewPrecio = itemView.findViewById(R.id.textView_productPrice);
            nombre = itemView.findViewById(R.id.textView_productName);
            imagen = itemView.findViewById(R.id.imageView_productImage);
            imageViewDelete = itemView.findViewById(R.id.imageView_remove);
        }
    }
}

