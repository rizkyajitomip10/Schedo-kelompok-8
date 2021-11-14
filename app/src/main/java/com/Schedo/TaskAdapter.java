package com.Schedo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Schedo.db.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    Context c;
    List<Task> data;
    TaskClick taskClick;

    public TaskAdapter(Context c, TaskClick taskClick) {
        this.c = c;
        this.taskClick = taskClick;
    }

    public void Update(List<Task> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskHolder(LayoutInflater.from(c).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.desc.setText(data.get(position).getDescription());
        holder.due_date.setText(data.get(position).getDue_date());

        holder.title.setChecked(data.get(position).isStatus());
        holder.title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                taskClick.onTaskChecked(data.get(position).getTask_id(), isChecked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskClick.onTaskClick(data.get(position).getTask_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data!=null ? data.size() : 0;
    }

    public class TaskHolder extends RecyclerView.ViewHolder{
        CheckBox title;
        TextView due_date, desc;
        public TaskHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.task_title);
            due_date = v.findViewById(R.id.task_due_date);
            desc = v.findViewById(R.id.task_description);
        }
    }

    public interface TaskClick{
        void onTaskChecked(int id, boolean checked);
        void onTaskClick(int id);
    }
}
