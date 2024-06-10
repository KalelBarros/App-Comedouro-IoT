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
import com.example.nutripatas.R;
import com.example.nutripatas.databinding.FragmentDadosAlimentadorBinding;
import com.example.nutripatas.databinding.FragmentMonitoramentoBinding;
import com.example.nutripatas.fragments.DispositivoFragment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonitoramentoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonitoramentoFragment extends Fragment
{
    protected FragmentMonitoramentoBinding binding;
    private DadosAdapter dadosAdapter;
    static ArrayList<Alimentador> dadosList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MonitoramentoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonitoramentoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonitoramentoFragment newInstance(String param1, String param2) {
        MonitoramentoFragment fragment = new MonitoramentoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMonitoramentoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.RecyclerViewDados;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        dadosAdapter = new DadosAdapter(dadosList, getActivity());
        recyclerView.setAdapter(dadosAdapter);

        return inflater.inflate(R.layout.fragment_monitoramento, container, false);
    }

    public static void getDados()
    {
        for(int i = 0; i< DispositivoFragment.alimentadorList.size(); i++)
        {
            dadosList.add(DispositivoFragment.alimentadorList.get(i));
        }
    }

}