package com.example.nutripatas.Classes;


import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class Alimentador
{
    private String nome, IP;
    private int porçãoRação, intervaloRefeição, quantidadeReservatorio;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    String esp32IP;
    private String Status;

    public Alimentador(String nome, int porçãoRação, int intervaloRefeição)
    {
        this.nome = nome;
        this.IP = IP;
        this.intervaloRefeição = intervaloRefeição;
        this.porçãoRação = porçãoRação;
    }



    public void setQuantidadeReservatorio(String res)
    {
        quantidadeReservatorio = Integer.parseInt(res);
    }
    public int getIntervalo()
    {
        return intervaloRefeição;
    }

    public String getStatus()
    {
        return Status;
    }

    public int getPorcao()
    {
        return porçãoRação;
    }
    public int getQuantidadeReservatorio()
    {
        return 1750;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome) {
        nome = nome;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPorçãoRação() {
        return porçãoRação;
    }

    public void setQuantidadeRação(int quantidadeRação) {
        this.porçãoRação = porçãoRação;
    }

    public int getIntervaloRefeição() {
        return intervaloRefeição;
    }

    public void setIntervaloRefeição(int intervaloRefeição)
    {
        this.intervaloRefeição = intervaloRefeição;
    }

    public int CalcularIntervalo(int intervaloRefeição)
    {
        return intervaloRefeição * 60000;
    }
}