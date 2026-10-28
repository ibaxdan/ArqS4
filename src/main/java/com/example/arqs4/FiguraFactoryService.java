package com.example.arqs4;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FiguraFactoryService {

    private final Map<String, Class<? extends Figura>> figurasDinamicas = new ConcurrentHashMap<>();

    // Configuración explícita del GroovyClassLoader con el ClassLoader de Figura
    private final GroovyClassLoader groovyClassLoader = new GroovyClassLoader(Figura.class.getClassLoader());

    public void registrarFiguraGroovy(String codigoGroovy) {
        try {
            Class<?> clazz = groovyClassLoader.parseClass(codigoGroovy);
            if (Figura.class.isAssignableFrom(clazz)) {
                figurasDinamicas.put(clazz.getSimpleName().toLowerCase(), clazz.asSubclass(Figura.class));
            } else {
                throw new IllegalArgumentException("La clase debe heredar de Figura");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("Fallo al compilar script Groovy: " + e.getMessage(), e);
        }
    }

    public Figura instanciarFigura(String nombreFigura, Object... parametros) throws Exception {
        String clave = nombreFigura.toLowerCase();
        Class<? extends Figura> claseFigura = figurasDinamicas.get(clave);

        if (claseFigura == null) {
            try {
                String paqueteBase = "com.example.arqs4.";
                String nombreFormato = nombreFigura.substring(0, 1).toUpperCase() + nombreFigura.substring(1).toLowerCase();
                Class<?> nativa = Class.forName(paqueteBase + nombreFormato);
                if (Figura.class.isAssignableFrom(nativa)) {
                    claseFigura = nativa.asSubclass(Figura.class);
                }
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("No se encontró la figura: " + nombreFigura);
            }
        }

        for (Constructor<?> ctor : claseFigura.getConstructors()) {
            if (ctor.getParameterCount() == parametros.length) {
                return (Figura) ctor.newInstance(parametros);
            }
        }

        throw new NoSuchMethodException("No se encontró constructor para " + nombreFigura + " con " + parametros.length + " parámetros.");
    }
}