package com.example.nutripatas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutripatas.Classes.Alimentador;
import com.example.nutripatas.databinding.AlimentadorItemBinding;

import java.util.ArrayList;

public class AlimentadorAdapter extends RecyclerView.Adapter<AlimentadorAdapter.AlimentadorViewHolder> {

    private final ArrayList<Alimentador> alimentadorList;
    private final LayoutInflater inflater;

    public AlimentadorAdapter(ArrayList<Alimentador> alimentadorList, Context context) {
        this.alimentadorList = alimentadorList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AlimentadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AlimentadorItemBinding binding = AlimentadorItemBinding.inflate(inflater, parent, false);
        return new AlimentadorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentadorViewHolder holder, int position) {
        Alimentador alimentador = alimentadorList.get(position);
        holder.binding.Nome.setText(alimentador.getNome());
        holder.binding.porO.setText(String.valueOf(alimentador.getPorçãoRação()));
        holder.binding.Intervalo.setText(String.valueOf(alimentador.getIntervaloRefeição()));
    }

    @Override
    public int getItemCount() {
        return alimentadorList.size();
    }

    public static class AlimentadorViewHolder extends RecyclerView.ViewHolder {

        private final AlimentadorItemBinding binding;

        public AlimentadorViewHolder(@NonNull AlimentadorItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
