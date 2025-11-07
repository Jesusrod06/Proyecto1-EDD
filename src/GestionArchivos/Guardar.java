/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestionArchivos;

import EDD.Grafo;
import EDD.Lista;
import EDD.Nodo;
import EDD.Vertice;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;


public class Guardar {

    private Grafo grafo;

    public Guardar(Grafo grafo) {
        this.grafo = grafo;
    }

    public Grafo getGrafo() {
        return grafo;
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Guarda el grafo en un archivo de texto dentro del Test package,
     * con el formato:
     *
     * usuarios
     * @usuario1
     * @usuario2
     * ...
     * relaciones
     * @usuario1, @usuario2
     * ...
     *
     * @param nombreArchivo nombre del archivo, por ejemplo "grafo.txt"
     */
    public void guardarEnTest(String nombreArchivo) {
        if (grafo == null || grafo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay datos en el grafo para guardar.");
            return;
        }

        //Construir el contenido
        StringBuilder sb = new StringBuilder();

        // Sección de usuarios
        sb.append("usuarios\n");

        Lista listaUsuarios = grafo.getUsuarios();
        Nodo aux = listaUsuarios.getpFirst();
        while (aux != null) {
            Vertice v = (Vertice) aux.getDato();
            sb.append(v.getNombre()).append("\n");
            aux = aux.getPnext();
        }
        // Sección de relaciones
        sb.append("relaciones\n");

        aux = listaUsuarios.getpFirst();
        while (aux != null) {
            Vertice origen = (Vertice) aux.getDato();
            Lista adyacentes = origen.getAdyacentes();

            Nodo auxAdy = adyacentes.getpFirst();
            while (auxAdy != null) {
                Vertice destino = (Vertice) auxAdy.getDato();

                sb.append(origen.getNombre())
                  .append(", ")
                  .append(destino.getNombre())
                  .append("\n");

                auxAdy = auxAdy.getPnext();
            }

            aux = aux.getPnext();
        }

        // Ruta del archivo dentro del "Test package"
        // Ajusta la ruta si tu carpeta de tests se llama diferente
        File file = new File("src/test/" + nombreArchivo);

        // Asegurar que el directorio exista
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        //Escribir en el archivo
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(sb.toString());
            bw.flush();

            JOptionPane.showMessageDialog(
                null,
                "Grafo guardado correctamente en:\n" + file.getAbsolutePath()
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al guardar el grafo: " + e.getMessage()
            );
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // Ignorar
                }
            }
        }
    }
}
