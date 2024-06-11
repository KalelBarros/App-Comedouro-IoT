package com.example.nutripatas.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nutripatas.Adapter.DadosAdapter;
import com.example.nutripatas.Classes.Alimentador;
import com.example.nutripatas.databinding.FragmentMonitoramentoBinding;
import java.util.ArrayList;

public class MonitoramentoFragment extends Fragment {
    protected FragmentMonitoramentoBinding binding;
    private DadosAdapter dadosAdapter;
    public static ArrayList<Alimentador> dadosList = new ArrayList<>(); // Mudei para public static

    public MonitoramentoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMonitoramentoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.RecyclerViewDados;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        // Inicializar o adaptador com a lista de dados e vinculá-lo ao RecyclerView
        dadosAdapter = new DadosAdapter(dadosList, getActivity());
        recyclerView.setAdapter(dadosAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}