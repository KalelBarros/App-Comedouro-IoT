package com.example.nutripatas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutripatas.Adapter.AlimentadorAdapter;
import com.example.nutripatas.Classes.Alimentador;
import com.example.nutripatas.databinding.ActivityMainBinding;
import com.example.nutripatas.databinding.FragmentPopUpBinding;
import com.example.nutripatas.fragments.MonitoramentoFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class MainActivity extends AppCompatActivity {
    private CardView btnAdicionar;
    private AlimentadorAdapter alimentadorAdapter;
    public static ArrayList<Alimentador> alimentadorList = new ArrayList<>();
    private AlertDialog dialog;
    private ActivityMainBinding binding;
    private TextView networkNameTextView;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        RecyclerView recyclerViewAlimentador = findViewById(R.id.RecyclerViewAlimentador);
        recyclerViewAlimentador.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAlimentador.setHasFixedSize(true);
        alimentadorAdapter = new AlimentadorAdapter(alimentadorList, this);
        recyclerViewAlimentador.setAdapter(alimentadorAdapter);

        btnAdicionar = findViewById(R.id.btn_adicionar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                ViewGroup container = findViewById(android.R.id.content);
                showDialog(inflater, container);
            }
        });

        // Exibir a rede Wi-Fi
        networkNameTextView = findViewById(R.id.networkNameTextView);
        ExibirRede();
    }

    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            final android.net.wifi.WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !connectionInfo.getSSID().isEmpty()) {
                ssid = connectionInfo.getSSID();
                // Remove quotes from SSID
                if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                }
            }
        }
        return ssid;
    }

    public void ExibirRede() {
        String ssid = getCurrentSsid(this);
        if (ssid != null) {
            networkNameTextView.setText("Conectado a: " + ssid);
        } else {
            networkNameTextView.setText("Não conectado a nenhuma rede Wi-Fi");
        }
    }

    private void showDialog(LayoutInflater inflater, ViewGroup container) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        FragmentPopUpBinding dialogBinding = FragmentPopUpBinding.inflate(inflater, container, false);
        View dialogView = dialogBinding.getRoot();
        builder.setView(dialogView);

        EditText nomeAlimentador = dialogBinding.nome;
        EditText porçãoRação = dialogBinding.porO;
        EditText intervaloRefeicaoAlimentador = dialogBinding.intervalo;
        Button confirmar = dialogBinding.btConfirmar;
        Button cancelar = dialogBinding.btCancelar;

        dialog = builder.create();

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nomeAlimentador.getText().toString().isEmpty() &&
                        !porçãoRação.getText().toString().isEmpty() &&
                        !intervaloRefeicaoAlimentador.getText().toString().isEmpty()) {
                    Alimentador alimentador = new Alimentador(
                            nomeAlimentador.getText().toString(),
                            Integer.parseInt(porçãoRação.getText().toString()),
                            Integer.parseInt(intervaloRefeicaoAlimentador.getText().toString())
                    );
                    alimentadorList.add(alimentador);
                    if (alimentadorAdapter != null) {
                        alimentadorAdapter.notifyDataSetChanged();
                    }
                        discoverESP32IP();
                        if (alimentador.getIP() != null) {
                            String racaoAmount = porçãoRação.getText().toString();
                            String interval = intervaloRefeicaoAlimentador.getText().toString();
                            new SendDataTask().execute(racaoAmount, interval);
                        } else {
                            Toast.makeText(MainActivity.this, "ESP32 IP não encontrado", Toast.LENGTH_SHORT).show();
                        }
                    MonitoramentoFragment.dadosList.add(alimentador);
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

        dialog.show();
    }

    private class SendDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String racaoAmount = params[0];
                String interval = params[1];
                URL url = new URL("http://" + alimentadorList.get(0).getIP() + "/setData");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String postData = "racaoAmount=" + racaoAmount + "&interval=" + interval;
                OutputStream os = connection.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                return result.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Toast.makeText(MainActivity.this, "Dados enviados com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Erro ao enviar dados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetStockInfoTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://" + alimentadorList.get(0).getIP());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Valor do sensor de peso:")) {
                        int startIndex = line.indexOf(":") + 2; // Index after ": "
                        int endIndex = line.indexOf(" kg</p>"); // Index before " kg</p>"
                        return line.substring(startIndex, endIndex);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                alimentadorList.get(0).setQuantidadeReservatorio(result);
            } else {
                alimentadorList.get(0).setQuantidadeReservatorio("3770");
            }
        }
    }

    public void discoverESP32IP() {
        executorService.execute(() -> {
            try {
                JmDNS jmdns = JmDNS.create(InetAddress.getByName("0.0.0.0"));
                ServiceInfo[] services = jmdns.list("_http._tcp.local.");
                for (ServiceInfo serviceInfo : services) {
                    if (serviceInfo.getName().equals("Comedouro")) {
                        alimentadorList.get(0).setIP(serviceInfo.getInetAddresses()[0].getHostAddress());
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "ESP32 encontrado em: " + alimentadorList.get(0).getIP(), Toast.LENGTH_SHORT).show());
                        new GetStockInfoTask().execute();
                        break;
                    }
                }
                jmdns.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
