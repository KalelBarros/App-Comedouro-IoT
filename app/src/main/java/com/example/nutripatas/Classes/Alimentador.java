package com.example.nutripatas.Classes;

public class Alimentador
{
    private String Nome, IP;
    private int quantidadeRação, intervaloRefeição;

    public Alimentador(String nome, String IP, int intervaloRefeição)
    {
        Nome = nome;
        this.IP = IP;
        this.intervaloRefeição = intervaloRefeição;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getQuantidadeRação() {
        return quantidadeRação;
    }

    public void setQuantidadeRação(int quantidadeRação) {
        this.quantidadeRação = quantidadeRação;
    }

    public int getIntervaloRefeição() {
        return intervaloRefeição;
    }

    public void setIntervaloRefeição(int intervaloRefeição) {
        this.intervaloRefeição = intervaloRefeição;
    }
}
