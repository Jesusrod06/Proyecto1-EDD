/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import EDD.Grafo;
import EDD.Lista;
import EDD.Nodo;
import EDD.Vertice;
import Funciones.AlgoritmoKosaraju;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

public class GrafoVisualizadorSCC extends JFrame {

    private Grafo grafo;
    private Viewer viewer;
    private ViewPanel viewPanel;

    public GrafoVisualizadorSCC(Grafo grafo) {
        this.grafo = grafo;

        setTitle("Componentes fuertemente conectados");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el grafo de GraphStream
        Graph graph = new SingleGraph("SCC");
        inicializarGrafo(graph);

        if (viewer == null) {
            viewer = graph.display(false);
            viewer.enableAutoLayout();
        }

        if (viewPanel == null) {
            viewPanel = (ViewPanel) viewer.getDefaultView();
            add(viewPanel, BorderLayout.CENTER);
        }

        JButton volverBtn = new JButton("Cerrar");
        volverBtn.addActionListener(e -> {
            cerrarViewer();
            this.dispose();
        });
        add(volverBtn, BorderLayout.SOUTH);
    }

    private void inicializarGrafo(Graph graph) {
        // 1) Obtener componentes fuertemente conectados
        AlgoritmoKosaraju kosaraju = new AlgoritmoKosaraju(grafo);
        Lista componentes = kosaraju.obtenerComponentesFuertementeConectados();

        // Paleta de colores (puedes añadir más)
        String[] colores = {
            "#FF6B6B", // rojo
            "#4ECDC4", // turquesa
            "#FFD93D", // amarillo
            "#6A4C93", // violeta
            "#1A535C", // azul
            "#FF9F1C", // naranja
            "#2EC4B6", // verde-azulado
            "#E71D36"  // rojo fuerte
        };

        Lista usuariosLista = grafo.getUsuarios();

        // 2) Crear nodos con color según componente
        for (int i = 0; i < usuariosLista.getSize(); i++) {
            Vertice usuario = (Vertice) usuariosLista.getValor(i);
            String nombre = usuario.getNombre();

            int compIndex = obtenerIndiceComponente(nombre, componentes);
            if (compIndex < 0) {
                compIndex = 0;
            }
            String color = colores[compIndex % colores.length];

            Node node = graph.addNode(nombre);
            node.setAttribute("ui.label", nombre);
            node.setAttribute(
                "ui.style",
                "fill-color: " + color + "; size: 25px; text-size: 16px;" +
                "text-alignment: center; text-color: black;"
            );
        }

        // 3) Agregar aristas (direccionales) como antes
        for (int i = 0; i < usuariosLista.getSize(); i++) {
            Vertice usuario = (Vertice) usuariosLista.getValor(i);
            Lista adyacentes = usuario.getAdyacentes();

            for (int j = 0; j < adyacentes.getSize(); j++) {
                Vertice seguido = (Vertice) adyacentes.getValor(j);

                String edgeId = usuario.getNombre() + "->" + seguido.getNombre();
                if (graph.getEdge(edgeId) == null) {
                    Edge edge = graph.addEdge(edgeId, usuario.getNombre(), seguido.getNombre(), true);
                    edge.setAttribute("ui.style",
                        "arrow-shape: arrow; arrow-size: 12px, 4px; size: 2px;");
                }
            }
        }

        // 4) Estilos generales
        graph.setAttribute("ui.stylesheet",
            "node {" +
                "text-size: 16px;" +
                "size: 40px;" +
                "text-alignment: center;" +
                "text-color: black;" +
                "stroke-mode: plain;" +
                "stroke-color: black;" +
                "stroke-width: 1px;" +
            "}" +
            "edge {" +
                "size: 2px;" +
            "}"
        );
    }

    /**
     * Devuelve el índice del componente al que pertenece un usuario
     * componentes: Lista donde cada elemento es una Lista de nombres (String)
     */
    private int obtenerIndiceComponente(String nombre, Lista componentes) {
        Nodo compNodo = componentes.getpFirst();
        int indice = 0;

        while (compNodo != null) {
            Lista componente = (Lista) compNodo.getDato();
            Nodo usuarioNodo = componente.getpFirst();

            while (usuarioNodo != null) {
                String nom = (String) usuarioNodo.getDato();
                if (nombre.equals(nom)) {
                    return indice;
                }
                usuarioNodo = usuarioNodo.getPnext();
            }

            indice++;
            compNodo = compNodo.getPnext();
        }

        return -1; // no encontrado
    }

    private void cerrarViewer() {
        if (viewer != null) {
            viewer.disableAutoLayout();
            viewer.close();
            viewer = null;
        }
        if (viewPanel != null) {
            remove(viewPanel);
            viewPanel = null;
        }

        new Menu();
    }
}
