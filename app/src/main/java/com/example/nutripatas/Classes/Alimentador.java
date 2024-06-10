package com.example.nutripatas.Classes;

public class Alimentador
{
    private String nome, IP;
    private int porçãoRação, intervaloRefeição, quantidadeReservatorio;

    public Alimentador(String nome, int porçãoRação, int intervaloRefeição)
    {
        this.nome = nome;
        this.IP = IP;
        this.intervaloRefeição = intervaloRefeição;
        this.porçãoRação = porçãoRação;
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
