package com.example.nutripatas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutripatas.Classes.Alimentador;
import com.example.nutripatas.R;

import java.util.ArrayList;

public class DadosAdapter extends RecyclerView.Adapter<DadosAdapter.DadosViewHolder> {
    private ArrayList<Alimentador> dadosList;
    private Context context;

    public DadosAdapter(ArrayList<Alimentador> dadosList, Context context) {
        this.dadosList = dadosList;
        this.context = context;
    }

    @NonNull
    @Override
    public DadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_dados_alimentador, parent, false);
        return new DadosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DadosViewHolder holder, int position) {
        Alimentador alimentador = dadosList.get(position);
        holder.nomeAlimentador.setText(alimentador.getNome());
        holder.status.setText(alimentador.getStatus());  // Assumindo que você tem um método getStatus()
        holder.intervaloRefeicao.setText(String.valueOf(alimentador.getIntervalo()));
        holder.porcao.setText(String.valueOf(alimentador.getPorcao()));
        holder.quantidadeReservatorio.setText(String.valueOf(alimentador.getQuantidadeReservatorio()));
    }

    @Override
    public int getItemCount() {
        return dadosList.size();
    }

    static class DadosViewHolder extends RecyclerView.ViewHolder {
        TextView nomeAlimentador;
        TextView status;
        TextView intervaloRefeicao;
        TextView porcao;
        TextView quantidadeReservatorio;

        public DadosViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeAlimentador = itemView.findViewById(R.id.nome_alimentador);
            status = itemView.findViewById(R.id.status);
            intervaloRefeicao = itemView.findViewById(R.id.txt_intervalorefeicao);
            porcao = itemView.findViewById(R.id.txt_porcao);
            quantidadeReservatorio = itemView.findViewById(R.id.txt_quantidadeReservatorio);
        }
    }
}