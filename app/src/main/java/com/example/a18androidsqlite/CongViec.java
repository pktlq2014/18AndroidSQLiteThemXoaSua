package com.example.a18androidsqlite;

public class CongViec
{
    // bước 6
    private int idCV;
    private String tenCV;

    public CongViec() {
    }

    public CongViec(int idCV, String tenCV) {
        this.idCV = idCV;
        this.tenCV = tenCV;
    }

    public int getIdCV() {
        return idCV;
    }

    public void setIdCV(int idCV) {
        this.idCV = idCV;
    }

    public String getTenCV() {
        return tenCV;
    }

    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }
}
