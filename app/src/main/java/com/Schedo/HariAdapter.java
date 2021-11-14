package com.Schedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Schedo.db.Scheduler;

import java.util.List;

public class HariAdapter extends RecyclerView.Adapter<HariAdapter.HariHolder> {
    Context c;
    List<Scheduler> data;
    HariClick hariClick;

    public HariAdapter(Context c, List<Scheduler> data, HariClick hariClick) {
        this.c = c;
        this.data = data;
        this.hariClick = hariClick;
    }


    @NonNull
    @Override
    public HariHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HariHolder(LayoutInflater.from(c).inflate(R.layout.item_hari, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HariHolder holder, int position) {
        String haris = "Mata Pelajaran   : " + data.get(position).getMapel() + "\n";
        haris       += "Nama Pengajar  : " + data.get(position).getPengajar() + "\n";
        haris       += "Jam                      : " + data.get(position).getWaktu();
        holder.hari.setText(haris);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hariClick.onHari(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HariHolder extends RecyclerView.ViewHolder {
        TextView hari;
        public HariHolder(@NonNull View v) {
            super(v);
            hari = v.findViewById(R.id.txtHari);
        }
    }

    public interface HariClick{
        void onHari(Scheduler datas);
    }
}
