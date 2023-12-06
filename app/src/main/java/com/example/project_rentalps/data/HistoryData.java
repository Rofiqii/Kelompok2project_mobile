package com.example.project_rentalps.data;

public class HistoryData {
    private String usernameCus;
    private String idPemesanan;
    private String idPs;
    private String waktuAwal;
    private String waktuAkhir;
    private String totalHarga;
    private String tanggal;
    private String status;

    public HistoryData(String usernameCus, String idPemesanan, String idPs, String waktuAwal, String waktuAkhir, String totalHarga, String tanggal, String status) {
        this.usernameCus = usernameCus;
        this.idPemesanan = idPemesanan;
        this.idPs = idPs;
        this.waktuAwal = waktuAwal;
        this.waktuAkhir = waktuAkhir;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
        this.status = status;
    }

    public String getUsernameCus() {
        return usernameCus;
    }

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public String getIdPs() {
        return idPs;
    }

    public String getWaktuAwal() {
        return waktuAwal;
    }

    public String getWaktuAkhir() {
        return waktuAkhir;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }
}
