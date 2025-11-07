/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import EDD.Grafo;
import EDD.Lista;
import EDD.Nodo;
import EDD.Vertice;

public class AlgoritmoKosaraju {

    private final Grafo grafo;

    public AlgoritmoKosaraju(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Devuelve una Lista donde cada elemento es otra Lista.- Lista componentes
   - componentes.getValor(i) -> Lista con los nombres (String) de los vértices del componente i
     * @return
     */
    public Lista obtenerComponentesFuertementeConectados() {
        Lista usuarios = grafo.getUsuarios();
        int n = usuarios.getSize();

        if (n == 0) {
            return new Lista();
        }

        // 1) Primer DFS para obtener el orden de finalización (pila)
        boolean[] visitado = new boolean[n];
        Lista pila = new Lista();  // usaremos insertarInicio como "push" (LIFO)

        for (int i = 0; i < n; i++) {
            Vertice v = (Vertice) usuarios.getValor(i);
            if (!visitado[v.getNumVertice()]) {
                dfs1(v, visitado, pila);
            }
        }

        // 2) Construir grafo transpuesto
        Grafo grafoT = construirGrafoTranspuesto();

        // 3) Segundo DFS en grafo transpuesto según la pila
        Lista componentes = new Lista();
        boolean[] visitadoT = new boolean[n];

        // Recorremos la "pila" desde el primero (que es el último en haber terminado)
        Nodo nodoPila = pila.getpFirst();
        while (nodoPila != null) {
            Vertice vOriginal = (Vertice) nodoPila.getDato();
            // en el grafo transpuesto buscamos por nombre
            Vertice vT = grafoT.buscar(vOriginal.getNombre());

            if (vT != null && !visitadoT[vT.getNumVertice()]) {
                Lista componente = new Lista();
                dfs2(grafoT, vT, visitadoT, componente);
                componentes.insertarFinal(componente);
            }

            nodoPila = nodoPila.getPnext();
        }

        return componentes;
    }

    // DFS del primer paso: llena la "pila" en orden de finalización
    private void dfs1(Vertice v, boolean[] visitado, Lista pila) {
        int idx = v.getNumVertice();
        visitado[idx] = true;

        Lista ady = v.getAdyacentes();
        for (int i = 0; i < ady.getSize(); i++) {
            Vertice w = (Vertice) ady.getValor(i);
            int j = w.getNumVertice();
            if (!visitado[j]) {
                dfs1(w, visitado, pila);
            }
        }

        // "push" en la pila (LIFO) usando insertarInicio
        pila.insertarInicio(v);
    }

    // Construye un nuevo grafo con todas las aristas invertidas
    private Grafo construirGrafoTranspuesto() {
        Grafo gT = new Grafo();
        Lista usuarios = grafo.getUsuarios();

        // 1) Crear mismos vértices
        for (int i = 0; i < usuarios.getSize(); i++) {
            Vertice v = (Vertice) usuarios.getValor(i);
            gT.insertarUsuario(v.getNombre());
        }

        // 2) Invertir aristas: si en original hay v -> w, en transpuesto habrá w -> v
        for (int i = 0; i < usuarios.getSize(); i++) {
            Vertice v = (Vertice) usuarios.getValor(i);
            Lista ady = v.getAdyacentes();

            for (int j = 0; j < ady.getSize(); j++) {
                Vertice w = (Vertice) ady.getValor(j);

                // En el transpuesto, arista w -> v
                Vertice vT = gT.buscar(v.getNombre());
                Vertice wT = gT.buscar(w.getNombre());

                if (wT != null && vT != null) {
                    wT.getAdyacentes().insertarFinal(vT);
                }
            }
        }

        // Reenumerar los numVertice del grafo transpuesto
        Lista usuariosT = gT.getUsuarios();
        for (int i = 0; i < usuariosT.getSize(); i++) {
            Vertice vT = (Vertice) usuariosT.getValor(i);
            vT.setNumVertice(i);
        }

        return gT;
    }

    // DFS sobre el grafo transpuesto: arma un componente (lista de nombres String)
    private void dfs2(Grafo gT, Vertice v, boolean[] visitadoT, Lista componente) {
        int idx = v.getNumVertice();
        visitadoT[idx] = true;

        // Guardamos el NOMBRE del vértice en el componente
        componente.insertarFinal(v.getNombre());

        Lista ady = v.getAdyacentes();
        for (int i = 0; i < ady.getSize(); i++) {
            Vertice w = (Vertice) ady.getValor(i);
            int j = w.getNumVertice();
            if (!visitadoT[j]) {
                dfs2(gT, w, visitadoT, componente);
            }
        }
    }
}
