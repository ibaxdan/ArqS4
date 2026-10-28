package com.example.arqs4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArqS4Application {

    public static void main(String[] args) {
        SpringApplication.run(ArqS4Application.class, args);

        // Pruebas de Círculo
        Circulo c = new Circulo(5.0f);
        System.out.println("Círculo Área: " + c.calcularArea());
        System.out.println("Círculo Perímetro: " + c.calcularPerimetro());

        // Pruebas de Cuadrado
        Cuadrado cuad = new Cuadrado(4.0f);
        System.out.println("Cuadrado Área: " + cuad.calcularArea());
        System.out.println("Cuadrado Perímetro: " + cuad.calcularPerimetro());

         Triangulo tri = new Triangulo(5.0f, 4.0f, 5.0f, 5.0f);
        System.out.println("Triángulo Área: " + tri.calcularArea());
        System.out.println("Triángulo Perímetro: " + tri.calcularPerimetro());
    }
}