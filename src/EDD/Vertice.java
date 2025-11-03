/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;


public class Vertice {
    int numVertice;
    String nombre;
    Lista adyacentes;

    public Vertice(String nombre) {
        this.numVertice = -1;
        this.nombre = nombre;
        this.adyacentes = new Lista();
    }

    public int getNumVertice() {
        return numVertice;
    }

    public void setNumVertice(int numVertice) {
        this.numVertice = numVertice;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Lista getAdyacentes() {
        return adyacentes;
    }

    public void setAdyacentes(Lista adyacentes) {
        this.adyacentes = adyacentes;
    }

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

    @Override
    public String toString() {
        return "Nombre: " + nombre + "\nNumero de Vertice: " + numVertice + "\nAdyacentes: " + this.printAdy() + "\n";
    }
}
