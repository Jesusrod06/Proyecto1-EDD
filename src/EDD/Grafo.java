/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import javax.swing.JOptionPane;

public class Grafo {

    private Lista usuarios;

    public Grafo() {
        this.usuarios = new Lista();
    }

    public Lista getUsuarios() {
        return usuarios;
    }

    public void setEstaciones(Lista usuarios) {
        this.usuarios = usuarios;
    }

    public boolean isEmpty() {
        return this.usuarios.isEmpty();
    }

    public Vertice buscar(String nombreUsuario) {
        if (!this.isEmpty()) {
            Nodo aux = this.usuarios.getpFirst();
            while (aux != null) {
                Vertice verticeActual = (Vertice) aux.getDato();
                if (verticeActual.getNombre().equalsIgnoreCase(nombreUsuario)) {
                    return verticeActual;
                }
                aux = aux.getPnext();
            }
            return null;
        }
        return null;
    }

    public void insertarUsuario(String nombreUsuario) {
        if (this.buscar(nombreUsuario) == null) {
            Vertice vertice = new Vertice(nombreUsuario);
            vertice.setNumVertice(this.usuarios.getSize());
            this.usuarios.insertarFinal(vertice);
        } else {
            JOptionPane.showMessageDialog(null, "La estacion ya existe.");
        }
    }

    public void insertarUsuario2(Vertice usuario) {
        if (this.buscar(usuario.getNombre()) == null) {
            usuario.setNumVertice(this.usuarios.getSize());
            this.usuarios.insertarFinal(usuario);
        } else {
            JOptionPane.showMessageDialog(null, "La estacion ya existe.");
        }
    }

    //Tiene un pequeño bug
    public void seguirUsuario(String nombreUsuario1, String nombreUsuario2) {
        if (this.buscar(nombreUsuario1) != null && this.buscar(nombreUsuario2) != null) {
            Vertice usuarioInicial = buscar(nombreUsuario1);
            Vertice usuarioFinal = buscar(nombreUsuario2);

            usuarioInicial.getAdyacentes().insertarFinal(usuarioFinal);
            JOptionPane.showMessageDialog(null, "Usuario " + nombreUsuario1 + " siguiendo a " + nombreUsuario2);
        } else {
            if (this.buscar(nombreUsuario1) == null && this.buscar(nombreUsuario2) != null) {
                JOptionPane.showMessageDialog(null, "El usuario " + nombreUsuario1 + " no existe");
            } else if (this.buscar(nombreUsuario1) != null && this.buscar(nombreUsuario2) == null) {
                JOptionPane.showMessageDialog(null, "El usuario " + nombreUsuario2 + " no existe");
            } else {
                JOptionPane.showMessageDialog(null, "Ninguna de los usuarios existe.");
            }
        }
    }

    public void dejarDeSeguir(String nombreUsuario1, String nombreUsuario2) {
        if (this.buscar(nombreUsuario1) != null && this.buscar(nombreUsuario2) != null) {
            Vertice usuarioInicial = buscar(nombreUsuario1);
            Vertice usuarioFinal = buscar(nombreUsuario2);

            if (usuarioInicial.getAdyacentes().buscar(usuarioFinal)) {
                usuarioInicial.getAdyacentes().eliminarPorReferencia(usuarioFinal);
                JOptionPane.showMessageDialog(null, "El usuario " + nombreUsuario1 + " ha dejado de seguir a " + nombreUsuario2);
            } else {
                JOptionPane.showMessageDialog(null, "No son adyacentes.");
            }

        } else {
            if (this.buscar(nombreUsuario1) == null && this.buscar(nombreUsuario2) != null) {
                JOptionPane.showMessageDialog(null, "El usuario " + nombreUsuario1 + " no existe");
            } else if (this.buscar(nombreUsuario1) != null && this.buscar(nombreUsuario2) == null) {
                JOptionPane.showMessageDialog(null, "El usuario " + nombreUsuario2 + " no existe");
            } else {
                JOptionPane.showMessageDialog(null, "Ninguna de los usuarios existe.");
            }
        }
    }

    public void getAdjacent(String nombreUsuario) {
        if (this.buscar(nombreUsuario) != null) {
            Vertice usuario = this.buscar(nombreUsuario);
            JOptionPane.showMessageDialog(null, usuario.printAdy());
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no existe.");
        }
    }

    public void destruir() {
        this.usuarios = new Lista();
    }

    @Override
    public String toString() {
        if (!this.isEmpty()) {
            String usuariosStr = "";
            Nodo aux = this.usuarios.getpFirst();
            while (aux.getPnext()!= null) {
                Vertice verticeActual = (Vertice) aux.getDato();
                usuariosStr += verticeActual.getNombre() + " ---> " + verticeActual.printAdy() + "\n";
                aux = aux.getPnext();
            }
            Vertice verticeActual = (Vertice) aux.getDato();
            usuariosStr += verticeActual.getNombre() + " ---> " + verticeActual.printAdy();
            return usuariosStr;
        } else {
            return "Grafo vacion";
        }
    }
}
