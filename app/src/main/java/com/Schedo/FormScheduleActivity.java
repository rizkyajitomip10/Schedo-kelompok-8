package com.Schedo;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputLayout;
import com.Schedo.db.AppDb;
import com.Schedo.db.Scheduler;

import java.util.Calendar;

public class FormScheduleActivity extends AppCompatActivity implements View.OnClickListener, Toolbars.OnToolbarClick {
    AppDb appDb;
    TextInputLayout mapel, nama_pengajar, jam_awal, jam_akhir;
    Button btn_act, btn_del;
    String id = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_schedule);
        Toolbars toolbars = findViewById(R.id.toolbars);
        toolbars.setOnToolbarBackClick(this);
        appDb = Room.databaseBuilder(this, AppDb.class, AppDb.DB).allowMainThreadQueries().build();
        mapel = findViewById(R.id.mapel);
        nama_pengajar = findViewById(R.id.nama_pengajar);
        jam_awal = findViewById(R.id.jam_awal);
        jam_awal.getEditText().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                TimeDialog(jam_awal);
            }
        });
        jam_akhir = findViewById(R.id.jam_akhir);
        jam_akhir.getEditText().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                TimeDialog(jam_akhir);
            }
        });

        btn_act = findViewById(R.id.btn_act);
        btn_act.setOnClickListener(this);
        btn_del = findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        btn_del.setVisibility(View.GONE);

        if(getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
            btn_act.setText("Update");
            btn_del.setVisibility(View.VISIBLE);

            Scheduler scheduler = appDb.taskDao().getByIdSchedule(id);
            mapel.getEditText().setText(scheduler.getMapel());
            nama_pengajar.getEditText().setText(scheduler.getPengajar());
            String[] jams = scheduler.getWaktu().split("-");
            jam_awal.getEditText().setText(jams[0]);
            jam_akhir.getEditText().setText(jams[1]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_act:
                if(TextUtils.isEmpty(mapel.getEditText().getText().toString())||
                        TextUtils.isEmpty(nama_pengajar.getEditText().getText().toString())||
                        TextUtils.isEmpty(jam_awal.getEditText().getText().toString())||
                        TextUtils.isEmpty(jam_akhir.getEditText().getText().toString())){
                    Toast.makeText(getApplicationContext(), "Lengkapi Semua Formulir", Toast.LENGTH_SHORT).show();
                }else{
                    if(getIntent().hasExtra("id")){
                        Scheduler scheduler = new Scheduler();
                        scheduler.setSchedule_id(Integer.parseInt(getIntent().getStringExtra("id")));
                        scheduler.setMapel(mapel.getEditText().getText().toString());
                        scheduler.setPengajar(nama_pengajar.getEditText().getText().toString());
                        scheduler.setWaktu(jam_awal.getEditText().getText().toString() + "-" + jam_akhir.getEditText().getText().toString());
                        scheduler.setHari(getIntent().getStringExtra("hari"));
                        appDb.taskDao().upSchedule(scheduler);
                        finish();
                    }else{
                        Scheduler scheduler = new Scheduler();
                        scheduler.setMapel(mapel.getEditText().getText().toString());
                        scheduler.setPengajar(nama_pengajar.getEditText().getText().toString());
                        scheduler.setWaktu(jam_awal.getEditText().getText().toString() + "-" + jam_akhir.getEditText().getText().toString());
                        scheduler.setHari(getIntent().getStringExtra("hari"));
                        appDb.taskDao().addSchedule(scheduler);
                        finish();
                    }
                }
                break;
            case R.id.btn_del:
                AlertDialog.Builder alertDel = new AlertDialog.Builder(this);
                alertDel.setMessage("Apakah anda yakin ingin menghapus data ini?");
                alertDel.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        appDb.taskDao().delSchedule(appDb.taskDao().getByIdSchedule(id));
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


    private void TimeDialog(TextInputLayout input){
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                input.getEditText().setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onBackClick() {
        finish();
    }
}
