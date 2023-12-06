package com.example.project_rentalps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project_rentalps.data.HistoryData;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryData> {

    public HistoryAdapter(Context context, List<HistoryData> historyList) {
        super(context, 0, historyList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_item, parent, false);
        }

        HistoryData history = getItem(position);

        TextView usernameCusTextView = convertView.findViewById(R.id.usernameCusTextView);
        TextView idPemesananTextView = convertView.findViewById(R.id.idPemesananTextView);
        TextView waktuTextView = convertView.findViewById(R.id.waktuTextView);
        TextView totalHargaTextView = convertView.findViewById(R.id.totalHargaTextView);
        TextView statusTextView = convertView.findViewById(R.id.statusTextView);

        if (history != null) {
            usernameCusTextView.setText("Username: " + history.getUsernameCus());
            idPemesananTextView.setText("ID Pemesanan: " + history.getIdPemesanan());
            waktuTextView.setText("Waktu: " + history.getWaktuAwal() + " - " + history.getWaktuAkhir());
            totalHargaTextView.setText("Total Harga: " + history.getTotalHarga());
            statusTextView.setText("Status: " + history.getStatus());
        }

        return convertView;
    }
}
