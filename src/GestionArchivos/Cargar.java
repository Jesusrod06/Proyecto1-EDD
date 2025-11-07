/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionArchivos;

import EDD.Grafo;
import EDD.Lista;
import EDD.Nodo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

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

    //busca un String dentro de una Lista usando equals()
    private boolean listaContieneString(Lista lista, String valor) {
        Nodo aux = lista.getpFirst();
        while (aux != null) {
            Object dato = aux.getDato();
            if (dato instanceof String) {
                if (valor.equals((String) dato)) {
                    return true;
                }
            }
            aux = aux.getPnext();
        }
        return false;
    }

    public void cargarTxt() {
        try {
            // 0) Validación básica del texto
            if (txt == null || txt.trim().isEmpty()) {
                grafo.destruir();
                JOptionPane.showMessageDialog(null, "Error: el texto está vacío. No se pudo cargar el grafo.");
                return;
            }

            // 1) Dividimos el texto por la palabra "relaciones"
            String[] separar = txt.split("relaciones");

            if (separar.length != 2) {
                grafo.destruir();
                JOptionPane.showMessageDialog(null, "Error de formato: falta la sección 'relaciones' o está repetida.");
                return;
            }

            String usuariosStr = separar[0].trim();
            String relacionesStr = separar[1].trim();

            // 2) Procesar usuarios en una Lista propia
            Lista usuariosLista = new Lista();
            String[] lineasUsuarios = usuariosStr.split("\\r?\\n");

            for (int i = 0; i < lineasUsuarios.length; i++) {
                String linea = lineasUsuarios[i].trim();
                if (linea.isEmpty()) {
                    continue;
                }

                // Saltar la cabecera "usuarios"
                if (linea.equalsIgnoreCase("usuarios")) {
                    continue;
                }

                // Cada usuario debe empezar con '@'
                if (!linea.startsWith("@")) {
                    grafo.destruir();
                    JOptionPane.showMessageDialog(
                            null,
                            "Error de formato en usuarios: la línea '" + linea + "' no comienza con '@'."
                    );
                    return;
                }

                usuariosLista.insertarFinal(linea);
            }

            if (usuariosLista.getSize() == 0) {
                grafo.destruir();
                JOptionPane.showMessageDialog(null, "Error: no se encontró ningún usuario válido.");
                return;
            }

            // 3) Procesar relaciones en otra Lista propia
            Lista relacionesLista = new Lista();

            if (!relacionesStr.isEmpty()) {
                String[] lineasRelaciones = relacionesStr.split("\\r?\\n");

                for (int i = 0; i < lineasRelaciones.length; i++) {
                    String linea = lineasRelaciones[i].trim();
                    if (linea.isEmpty()) {
                        continue;
                    }

                    // Por si acaso vuelve a aparecer la palabra 'relaciones'
                    if (linea.equalsIgnoreCase("relaciones")) {
                        continue;
                    }

                    // Formato esperado: "@user1, @user2"
                    String[] partes = linea.split(",");
                    if (partes.length != 2) {
                        grafo.destruir();
                        JOptionPane.showMessageDialog(
                                null,
                                "Error de formato en relaciones: la línea '" + linea
                                + "' no tiene el formato '@usuario1, @usuario2'."
                        );
                        return;
                    }

                    String usuario1 = partes[0].trim();
                    String usuario2 = partes[1].trim();

                    // Validar que empiecen con '@'
                    if (!usuario1.startsWith("@") || !usuario2.startsWith("@")) {
                        grafo.destruir();
                        JOptionPane.showMessageDialog(
                                null,
                                "Error de formato en relaciones: la línea '" + linea
                                + "' contiene usuarios sin '@'."
                        );
                        return;
                    }

                    // Validar que existan en la lista de usuarios
                    if (!listaContieneString(usuariosLista, usuario1)
                            || !listaContieneString(usuariosLista, usuario2)) {

                        grafo.destruir();
                        JOptionPane.showMessageDialog(
                                null,
                                "Error: en la relación '" + linea
                                + "' alguno de los usuarios no está en la lista de usuarios."
                        );
                        return;
                    }

                    // Guardamos la relación como un arreglo de 2 Strings dentro de tu Lista
                    String[] par = new String[2];
                    par[0] = usuario1;
                    par[1] = usuario2;
                    relacionesLista.insertarFinal(par);
                }
            }

            // 4) Si todo es válido, reconstruimos el grafo
            grafo.destruir(); // Limpiar grafo anterior

            // Cargar usuarios
            for (int i = 0; i < usuariosLista.getSize(); i++) {
                String usuario = (String) usuariosLista.getValor(i);
                grafo.insertarUsuario(usuario);
            }

            // Cargar relaciones
            for (int i = 0; i < relacionesLista.getSize(); i++) {
                String[] par = (String[]) relacionesLista.getValor(i);
                String usuario1 = par[0];
                String usuario2 = par[1];

                grafo.seguirUsuarioCarga(usuario1, usuario2);
            }

            JOptionPane.showMessageDialog(null, "Grafo cargado correctamente.");

        } catch (Exception e) {
            grafo.destruir();
            JOptionPane.showMessageDialog(
                    null,
                    "Error inesperado al cargar el grafo: " + e.getMessage()
            );
        }
    }

    public void cargarDesdeRecursoTest(String nombreRecurso) {
        String ruta = "/test/" + nombreRecurso;  

        try (InputStream is = getClass().getResourceAsStream(ruta)) {
            if (is == null) {
                grafo.destruir();
                JOptionPane.showMessageDialog(
                        null,
                        "No se encontró el recurso: " + ruta
                );
                return;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }

            // Guardamos el contenido en txt y llamamos a tu método de parseo
            this.txt = sb.toString();
            this.cargarTxt();

        } catch (IOException e) {
            grafo.destruir();
            JOptionPane.showMessageDialog(
                    null,
                    "Error al leer el archivo de test: " + e.getMessage()
            );
        }
    }
}
