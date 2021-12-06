package com.example.evaln3;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder>{

    private ArrayList<Siniestro> siniestros;
    public adapter(ArrayList<Siniestro> siniestros) {
        this.siniestros = siniestros;
    }

    @NonNull
    @Override
    public adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.ViewHolder holder, int position) {
        holder.Name.setText(siniestros.get(position).getName());
        holder.Desc.setText(siniestros.get(position).getDesc());
        holder.LAT.setText(siniestros.get(position).getLAT());
        holder.LON.setText(siniestros.get(position).getLON());
        holder.Photo.setImageBitmap(siniestros.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return siniestros.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Name;
        private TextView Desc;
        private TextView LAT;
        private TextView LON;
        private ImageView Photo;

        public ViewHolder(View itemView){
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            Desc = itemView.findViewById(R.id.Desc);
            LAT = itemView.findViewById(R.id.LAT);
            LON = itemView.findViewById(R.id.LON);
            Photo = itemView.findViewById(R.id.Photo);
        }
    }
}
