package com.Schedo;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputLayout;
import com.Schedo.db.AppDb;
import com.Schedo.db.Task;

import java.util.Calendar;

public class FormtaskActivity extends AppCompatActivity implements View.OnClickListener, Toolbars.OnToolbarClick {
    AppDb appDb;
    TextInputLayout title, desc, due_date;
    //    CheckBox reminder;
    RadioGroup reminder;
    Button btn_act, btn_del;
    String id = "";
    String date_time = "";
    int h, m;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_task);
        appDb = Room.databaseBuilder(this, AppDb.class, AppDb.DB).allowMainThreadQueries().build();

        Toolbars toolbars = findViewById(R.id.toolbars);
        toolbars.setOnToolbarBackClick(this);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        due_date = findViewById(R.id.due_date);
        due_date.getEditText().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DateDialog();
            }
        });
//        reminder = findViewById(R.id.reminder);
        reminder = findViewById(R.id.radio_button);
        btn_act = findViewById(R.id.btn_act);
        btn_act.setOnClickListener(this);
        btn_del = findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        btn_del.setVisibility(View.GONE);

        if(getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
            btn_act.setText("Update");
            btn_del.setVisibility(View.VISIBLE);

            Task task = appDb.taskDao().getById(id);
            title.getEditText().setText(task.getTitle());
            desc.getEditText().setText(task.getDescription());
            due_date.getEditText().setText(task.getDue_date());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_act:
                if(TextUtils.isEmpty(title.getEditText().getText().toString())||
                        TextUtils.isEmpty(desc.getEditText().getText().toString())||
                        TextUtils.isEmpty(due_date.getEditText().getText().toString())){
                    Toast.makeText(getApplicationContext(), "Lengkapi Semua Formulir", Toast.LENGTH_SHORT).show();
                }else{
                    if(getIntent().hasExtra("id")){
                        Task task = new Task();
                        task.setTask_id(Integer.parseInt(getIntent().getStringExtra("id")));
                        task.setTitle(title.getEditText().getText().toString());
                        task.setDescription(desc.getEditText().getText().toString());
                        task.setDue_date(due_date.getEditText().getText().toString());
                        if(appDb.taskDao().up(task) > 0){
                            if(reminder.getCheckedRadioButtonId() != -1){
                                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                                intent.putExtra(AlarmClock.EXTRA_MESSAGE, title.getEditText().getText().toString());
                                intent.putExtra(AlarmClock.EXTRA_HOUR, h);
                                intent.putExtra(AlarmClock.EXTRA_MINUTES, m);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }else{
                        Task task = new Task();
                        task.setTitle(title.getEditText().getText().toString());
                        task.setDescription(desc.getEditText().getText().toString());
                        task.setDue_date(due_date.getEditText().getText().toString());
                        task.setStatus(false);
                        if(appDb.taskDao().add(task)>0){
                            if(reminder.getCheckedRadioButtonId() != -1){
                                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                                intent.putExtra(AlarmClock.EXTRA_MESSAGE, title.getEditText().getText().toString());
                                intent.putExtra(AlarmClock.EXTRA_HOUR, h);
                                intent.putExtra(AlarmClock.EXTRA_MINUTES, m);
                                startActivity(intent);
                            }
                            finish();
                        }
                    }
                }
                break;
            case R.id.btn_del:
                AlertDialog.Builder alertDel = new AlertDialog.Builder(this);
                alertDel.setMessage("Apakah anda yakin ingin menghapus data ini?");
                alertDel.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appDb.taskDao().del(appDb.taskDao().getById(id));
                        dialog.cancel();
                        finish();
                    }
                });
                alertDel.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDel.show();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DateDialog(){
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_time = String.format("%04d-%02d-%02d",
                        year,
                        month+1,
                        dayOfMonth);
                TimeDialog();
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void TimeDialog(){
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                h = hourOfDay;
                m = minute;
                date_time += " " + String.format("%02d:%02d:%02d",
                        h,
                        m,
                        0);
                due_date.getEditText().setText(date_time);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onBackClick() {
        finish();
    }
}
