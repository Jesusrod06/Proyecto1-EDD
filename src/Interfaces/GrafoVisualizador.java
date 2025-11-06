/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import EDD.Grafo;
import EDD.Lista;
import EDD.Vertice;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

public class GrafoVisualizador extends JFrame {

    private Grafo grafo;        // Tu grafo de usuarios (EDD.Grafo)
    private Viewer viewer;
    private ViewPanel viewPanel;

    public GrafoVisualizador(Grafo grafo) {
        this.grafo = grafo;

        setTitle("Red social - Usuarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el grafo de GraphStream
        Graph graph = new SingleGraph("Usuarios");
        inicializarGrafo(graph);

        // === Crear el viewer solo una vez (igual que en tu ejemplo de Estaciones) ===
        if (viewer == null) {
            viewer = graph.display(false);   // NO new Viewer(...), usamos display()
            viewer.enableAutoLayout();
        }

        // Obtener el panel de vista y agregarlo al JFrame
        if (viewPanel == null) {
            viewPanel = (ViewPanel) viewer.getDefaultView();
            add(viewPanel, BorderLayout.CENTER);
        }

        // Botón para cerrar / volver
        JButton volverBtn = new JButton("Cerrar");
        volverBtn.addActionListener(e -> {
            cerrarViewer();
            this.dispose();
        });
        add(volverBtn, BorderLayout.SOUTH);
    }

    private void inicializarGrafo(Graph graph) {
        Lista usuariosLista = grafo.getUsuarios();

        // 1) Agregar nodos (uno por usuario)
        for (int i = 0; i < usuariosLista.getSize(); i++) {
            Vertice usuario = (Vertice) usuariosLista.getValor(i);

            Node node = graph.addNode(usuario.getNombre());
            node.setAttribute("ui.label", usuario.getNombre());
            node.setAttribute(
                "ui.style",
                "fill-color: #A8E6A1; size: 25px; text-size: 16px; " +
                "text-alignment: center; text-color: black;"
            );
        }

        // 2) Agregar aristas (relaciones seguirUsuario)
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

        // Estilos generales del grafo
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
        
        Menu menu = new Menu();
    }
}
