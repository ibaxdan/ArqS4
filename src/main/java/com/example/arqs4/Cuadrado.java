package com.example.arqs4;

public class Cuadrado extends Figura {
    private float lado;

    public Cuadrado(float lado) {
        this.lado = lado;
    }

    @Override
    public double calcularArea() {
        return lado * lado;
    }

    @Override
    public double calcularPerimetro() {
        return 4 * lado;
    }
}