package com.example.nutripatas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutripatas.Classes.Alimentador;
import com.example.nutripatas.databinding.FragmentDadosAlimentadorBinding;

import java.util.ArrayList;

public class DadosAdapter extends RecyclerView.Adapter<DadosAdapter.DadosViewHolder>
{
    private final ArrayList<Alimentador> DadosAlimentadorList;
    private final Context context;

    public DadosAdapter(ArrayList<Alimentador> dadosAlimentadorList, Context context) {
        DadosAlimentadorList = dadosAlimentadorList;
        this.context = context;
    }

    public static class DadosViewHolder extends RecyclerView.ViewHolder
    {
        FragmentDadosAlimentadorBinding binding;
        public DadosViewHolder(FragmentDadosAlimentadorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public DadosAdapter.DadosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        FragmentDadosAlimentadorBinding listItem;
        listItem = FragmentDadosAlimentadorBinding.inflate(LayoutInflater.from(context),parent,false);
        return new DadosViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull DadosAdapter.DadosViewHolder holder, int position)
    {
        holder.binding.nomeAlimentador.setText(DadosAlimentadorList.get(position).getNome());
        holder.binding.status.setText("Conectado");
        holder.binding.txtIntervalorefeicao.setText(DadosAlimentadorList.get(position).getIntervaloRefeição());
        holder.binding.txtPorcao.setText(DadosAlimentadorList.get(position).getPorçãoRação());
        holder.binding.txtQuantidadeReservatorio.setText(DadosAlimentadorList.get(position).getQuantidadeReservatorio());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
