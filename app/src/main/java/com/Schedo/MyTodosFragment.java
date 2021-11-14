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
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.Schedo.db.AppDb;

public class MyTodosFragment extends Fragment implements View.OnClickListener, TaskAdapter.TaskClick{
    AppDb appDb;
    RecyclerView rv;
    TaskAdapter taskAdapter;
    FloatingActionButton fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mytodos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        appDb = Room.databaseBuilder(getActivity(), AppDb.class, AppDb.DB).allowMainThreadQueries().build();
        rv = v.findViewById(R.id.rvTask);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        taskAdapter = new TaskAdapter(getActivity(), this);
        rv.setAdapter(taskAdapter);
        fab = v.findViewById(R.id.add);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add:
                Intent i = new Intent(getActivity(), FormtaskActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onTaskChecked(int id, boolean checked) {

    }

    @Override
    public void onTaskClick(int id) {
        Intent i = new Intent(getActivity(), FormtaskActivity.class);
        i.putExtra("id", id+"");
        startActivity(i);
    }


    @Override
    public void onStart() {
        super.onStart();
        if(taskAdapter != null){
            taskAdapter.Update(appDb.taskDao().getAll());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(taskAdapter != null){
            taskAdapter.Update(appDb.taskDao().getAll());
        }
    }
}
