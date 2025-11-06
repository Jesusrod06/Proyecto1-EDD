/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionArchivos;

import EDD.Grafo;

public class Cargar {

    private Grafo grafo;
    private String txt;

    public Cargar(Grafo grafo) {
        this.grafo = grafo;
        this.txt = null;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    public void cargarTxt() {
        // Dividimos el texto principal por la palabra "relaciones"
        String[] separar = txt.split("relaciones");

        // La primera parte (antes de 'relaciones') contiene los usuarios
        String usuariosStr = separar[0];

        // Dividimos por saltos de línea (admite \r\n, \n o \r)
        String[] lineas = usuariosStr.split("\\r?\\n");

        String[] usuariosArray = new String[lineas.length - 1];
        int index = 0;
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            if (!linea.isEmpty() && linea.startsWith("@")) {
                usuariosArray[index] = linea;
                index++;
            }
        }
        
        for (int i = 0; i < usuariosArray.length; i++) {
            grafo.insertarUsuario(usuariosArray[i]);
        }
        
        String[] relacionesArray = separar[1].split("\\r?\\n");
        for (int i = 1; i < relacionesArray.length; i++) {
            String linea = relacionesArray[i].trim();
            if (!linea.isEmpty()) {
                String[] usuariosR = linea.split(", ");
                String usuario1 = usuariosR[0].trim();
                String usuario2 = usuariosR[1].trim();
                
                grafo.seguirUsuarioCarga(usuario1, usuario2);
            }
        }
        
        //System.out.println(grafo.toString());

    }

}
