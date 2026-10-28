package com.example.arqs4;

public class Circulo extends Figura {
    private float r;

    public Circulo(float r) {
        this.r = r;
    }

    @Override
    public double calcularArea() {
        float pi = (float) Math.PI;
        return pi * (r * r);
    }

    @Override
    public double calcularPerimetro() {
        float pi = (float) Math.PI;
        return pi * r * 2;
    }
}