package com.example.nutripatas.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nutripatas.Adapter.AlimentadorAdapter;
import com.example.nutripatas.Classes.Alimentador;
import com.example.nutripatas.databinding.FragmentDispositivoBinding;
import com.example.nutripatas.databinding.FragmentPopUpBinding;

import java.util.ArrayList;

public class DispositivoFragment extends Fragment {

    private FragmentDispositivoBinding binding;
    private AlimentadorAdapter alimentadorAdapter;
    private ArrayList<Alimentador> alimentadorList = new ArrayList<>();
    private AlertDialog dialog; // Adicionando a variável dialog

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDispositivoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView recyclerViewAlimentador = binding.RecyclerViewAlimentador;
        recyclerViewAlimentador.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAlimentador.setHasFixedSize(true);
        alimentadorAdapter = new AlimentadorAdapter(alimentadorList, getActivity());
        recyclerViewAlimentador.setAdapter(alimentadorAdapter);

        binding.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(inflater, container);
            }
        });

        return view;
    }

    private void showDialog(LayoutInflater inflater, ViewGroup container) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        FragmentPopUpBinding dialogBinding = FragmentPopUpBinding.inflate(inflater, container, false);
        View dialogView = dialogBinding.getRoot();

        EditText nomeDispositivo = dialogBinding.nome;
        EditText ipDispositivo = dialogBinding.ip;
        EditText intervaloRefeicaoDispositivo = dialogBinding.intervalo;
        Button confirmar = dialogBinding.btConfirmar;
        Button cancelar = dialogBinding.btCancelar;

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nomeDispositivo.getText().toString().isEmpty() ||
                        ipDispositivo.getText().toString().isEmpty() ||
                        intervaloRefeicaoDispositivo.getText().toString().isEmpty()) {
                    dialog.dismiss();
                } else {
                    Alimentador a = new Alimentador(
                            nomeDispositivo.getText().toString(),
                            ipDispositivo.getText().toString(),
                            Integer.parseInt(intervaloRefeicaoDispositivo.getText().toString())
                    );
                    alimentadorList.add(a);
                    alimentadorAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        builder.setView(dialogView);
        dialog = builder.create(); // Inicializando a variável dialog
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
