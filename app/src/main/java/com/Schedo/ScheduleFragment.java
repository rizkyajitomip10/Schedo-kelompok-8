package com.Schedo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Schedo.db.Scheduler;

public class ScheduleFragment extends Fragment implements ScheduleAdapter.ScheduleClick {
    RecyclerView rv;
    ScheduleAdapter scheduleAdapter;
    String[] data = {"SABTU", "AHAD", "SENIN", "SELASA", "RABU", "KAMIS", "JUMAT"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        rv = v.findViewById(R.id.rvSchedule);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        scheduleAdapter = new ScheduleAdapter(getActivity(), data, ScheduleFragment.this);
        rv.setAdapter(scheduleAdapter);
    }

    @Override
    public void onAdd(String hari) {
//        Toast.makeText(getActivity(), hari, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(), FormScheduleActivity.class);
        i.putExtra("hari", hari);
        startActivity(i);
    }

    @Override
    public void onHari(Scheduler datas) {
        Intent i = new Intent(getActivity(), FormScheduleActivity.class);
        i.putExtra("id", datas.getSchedule_id()+"");
        i.putExtra("hari", datas.getHari()+"");
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(scheduleAdapter != null){
            scheduleAdapter.notifyDataSetChanged();
        }
    }
}
