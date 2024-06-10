package com.example.nutripatas.fragments;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static android.provider.Contacts.SettingsColumns.KEY;

import static java.sql.Types.INTEGER;
import static java.text.Collator.PRIMARY;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.SupplicantState;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nutripatas.Adapter.AlimentadorAdapter;
import com.example.nutripatas.Classes.Alimentador;
import com.example.nutripatas.databinding.FragmentAlimentadorBinding;
import com.example.nutripatas.databinding.FragmentPopUpBinding;
import com.example.nutripatas.fragments.MonitoramentoFragment;

import java.util.ArrayList;

public class DispositivoFragment extends Fragment {

    private SQLiteDatabase bancoDados;
    private FragmentAlimentadorBinding binding;
    private AlimentadorAdapter alimentadorAdapter;
    public static ArrayList<Alimentador> alimentadorList = new ArrayList<>();
    private AlertDialog dialog;
    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar e solicitar permissão de localização
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION);
        }
        criarBancoDados();
    }

    public void criarBancoDados()
    {
        try
        {
            bancoDados = openOrCreateDatabase("Alimentador",null,null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS Alimentador(" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , nome VARCHAR)");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlimentadorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView recyclerViewAlimentador = binding.RecyclerViewAlimentador;
        recyclerViewAlimentador.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAlimentador.setHasFixedSize(true);
        alimentadorAdapter = new AlimentadorAdapter(alimentadorList, getActivity());
        recyclerViewAlimentador.setAdapter(alimentadorAdapter);

        // Adicionar código para exibir o nome da rede Wi-Fi
        TextView networkNameTextView = binding.networkNameTextView; // Certifique-se de que o ID está correto no layout XML
        String networkName = getNetworkName();
        networkNameTextView.setText("Conectado a: " + networkName);

        binding.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(inflater, container);
            }
        });

        return view;
    }

    private String getNetworkName() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager.isWifiEnabled()) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null && wifiInfo.getSupplicantState() == SupplicantState.COMPLETED) {
                    String ssid = wifiInfo.getSSID();
                    if (ssid != null && !ssid.equals("<unknown ssid>") && !ssid.equals("0x")) {
                        return ssid;
                    }
                }
            }
            return "Sem conexão Wi-Fi";
        } else {
            return "Permissão de localização não concedida";
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, atualize o TextView com o nome da rede
                TextView networkNameTextView = binding.networkNameTextView;
                String networkName = getNetworkName();
                networkNameTextView.setText("Conectado a: " + networkName);
            } else {
                // Permissão negada, informe o usuário
                TextView networkNameTextView = binding.networkNameTextView;
                networkNameTextView.setText("Permissão de localização necessária para obter o nome da rede");
            }
        }
    }

    private void showDialog(LayoutInflater inflater, ViewGroup container) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        FragmentPopUpBinding dialogBinding = FragmentPopUpBinding.inflate(inflater, container, false);
        View dialogView = dialogBinding.getRoot();

        EditText nomeAlimentador = dialogBinding.nome;
        EditText porçãoRação = dialogBinding.porO;
        EditText intervaloRefeicaoAlimentador = dialogBinding.intervalo;
        Button confirmar = dialogBinding.btConfirmar;
        Button cancelar = dialogBinding.btCancelar;

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nomeAlimentador.getText().toString().isEmpty() ||
                        porçãoRação.getText().toString().isEmpty() ||
                        intervaloRefeicaoAlimentador.getText().toString().isEmpty()) {
                    dialog.dismiss();
                } else {
                    Alimentador a = new Alimentador(
                            nomeAlimentador.getText().toString(),
                            Integer.parseInt(porçãoRação.getText().toString()),
                            Integer.parseInt(intervaloRefeicaoAlimentador.getText().toString())
                    );
                    alimentadorList.add(a);
                    alimentadorAdapter.notifyDataSetChanged();
                    MonitoramentoFragment.getDados();
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
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
