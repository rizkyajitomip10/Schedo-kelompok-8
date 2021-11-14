package com.Schedo.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = AppDb.TABLE_SCHEDULE)
public class Scheduler {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "schedule_id")
    private int schedule_id;
    @ColumnInfo(name = "mapel")
    private String mapel;
    @ColumnInfo(name = "pengajar")
    private String pengajar;
    @ColumnInfo(name = "waktu")
    private String waktu;
    @ColumnInfo(name = "hari")
    private String hari;

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getPengajar() {
        return pengajar;
    }

    public void setPengajar(String pengajar) {
        this.pengajar = pengajar;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
}
