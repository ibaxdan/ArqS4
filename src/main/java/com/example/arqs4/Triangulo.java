package com.example.arqs4;

public class Triangulo extends Figura {
    private float base;
    private float altura;
    private float lado1;
    private float lado2;
    private float lado3;

    public Triangulo(float base, float altura, float lado2, float lado3) {
        this.base = base;
        this.altura = altura;
        this.lado1 = base; // la base cuenta como lado1
        this.lado2 = lado2;
        this.lado3 = lado3;
    }

    @Override
    public double calcularArea() {
        return (base * altura) / 2.0f;
    }

    @Override
    public double calcularPerimetro() {
        return lado1 + lado2 + lado3;
    }
}