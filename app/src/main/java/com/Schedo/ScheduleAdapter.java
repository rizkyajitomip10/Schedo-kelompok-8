package com.Schedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.Schedo.db.AppDb;
import com.Schedo.db.Scheduler;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder>{
    Context c;
    String[] data;
    ScheduleClick scheduleClick;
    AppDb appDb;

    public ScheduleAdapter(Context c, String[] data, ScheduleClick scheduleClick) {
        this.c = c;
        this.data = data;
        this.scheduleClick = scheduleClick;
        appDb = Room.databaseBuilder(c, AppDb.class, AppDb.DB).allowMainThreadQueries().build();
    }

    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleHolder(LayoutInflater.from(c).inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.hari.setText(data[position].toUpperCase());

        holder.rv.setLayoutManager(new LinearLayoutManager(c));
        holder.rv.setHasFixedSize(true);
        holder.rv.setAdapter(new HariAdapter(c, appDb.taskDao().getAllSchedule(data[position]), new HariAdapter.HariClick() {
            @Override
            public void onHari(Scheduler datas) {
                scheduleClick.onHari(datas);
            }
        }));

        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleClick.onAdd(data[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ScheduleHolder extends RecyclerView.ViewHolder {
        LinearLayout ln;
        TextView hari;
        RecyclerView rv;
        public ScheduleHolder(@NonNull View v) {
            super(v);
            ln = v.findViewById(R.id.lnHari);
            hari = v.findViewById(R.id.hari);
            rv = v.findViewById(R.id.rvHari);
        }
    }

    public interface ScheduleClick{
        void onAdd(String hari);
        void onHari(Scheduler datas);
    }
}
