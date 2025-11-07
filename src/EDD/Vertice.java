/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;


/**
 * Representa un vértice dentro de un grafo.
 * Cada vértice contiene un nombre, un número identificador
 * y una lista de adyacencias que corresponde a las conexiones
 * con otros vértices del grafo.
 */
public class Vertice {

    /**
     * Identificador numérico del vértice dentro del grafo.
     */
    int numVertice;

    /**
     * Nombre del vértice, utilizado como identificador textual.
     */
    String nombre;

    /**
     * Lista de vértices adyacentes (conexiones o relaciones).
     */
    Lista adyacentes;

    /**
     * Crea un nuevo vértice con un nombre determinado.
     * Inicializa la lista de adyacencias vacía y el número de vértice en -1.
     *
     * @param nombre nombre del vértice
     */
    public Vertice(String nombre) {
        this.numVertice = -1;
        this.nombre = nombre;
        this.adyacentes = new Lista();
    }

    /**
     * Obtiene el número del vértice.
     *
     * @return número de vértice
     */
    public int getNumVertice() {
        return numVertice;
    }

    /**
     * Asigna un nuevo número al vértice.
     *
     * @param numVertice nuevo número de vértice
     */
    public void setNumVertice(int numVertice) {
        this.numVertice = numVertice;
    }

    /**
     * Obtiene el nombre del vértice.
     *
     * @return nombre del vértice
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nuevo nombre al vértice.
     *
     * @param nombre nuevo nombre del vértice
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de vértices adyacentes a este vértice.
     *
     * @return lista de adyacencias
     */
    public Lista getAdyacentes() {
        return adyacentes;
    }

    /**
     * Asigna una lista de vértices adyacentes a este vértice.
     *
     * @param adyacentes lista de adyacencias a establecer
     */
    public void setAdyacentes(Lista adyacentes) {
        this.adyacentes = adyacentes;
    }

    /**
     * Genera una representación textual de los vértices adyacentes a este vértice.
     * Si no tiene adyacentes, devuelve el texto "No tiene adyacentes".
     *
     * @return cadena de texto con los nombres de los vértices adyacentes
     */
    public String printAdy() {
        if (!this.adyacentes.isEmpty()) {
            Nodo aux = this.adyacentes.getpFirst();
            String adyacentesStr = "";
            while (aux.getPnext()!= null) {
                Vertice estacionActual = (Vertice) aux.getDato();
                adyacentesStr += estacionActual.getNombre() + " ---> ";

                aux = aux.getPnext();
            }
            Vertice estacionActual = (Vertice) aux.getDato();
            adyacentesStr += estacionActual.getNombre();

            return adyacentesStr;
        }

        return "No tiene adyacentes";
    }

    /**
     * Devuelve una representación textual del vértice,
     * incluyendo su nombre, número y sus adyacentes.
     *
     * @return cadena con la información del vértice
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nNumero de Vertice: " + numVertice + "\nAdyacentes: " + this.printAdy() + "\n";
    }
}