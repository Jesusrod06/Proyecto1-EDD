/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EDD;

import javax.swing.JOptionPane;

/**
 * Representa un grafo de usuarios utilizando una lista enlazada de vértices.
 * Cada vértice corresponde a un usuario y mantiene una lista de adyacencias que
 * modela las relaciones de seguimiento entre ellos.
 */
public class Grafo {

    /**
     * Lista de usuarios (vértices) que conforman el grafo.
     */
    private Lista usuarios;

    /**
     * Crea un nuevo grafo vacío inicializando la lista de usuarios.
     */
    public Grafo() {
        this.usuarios = new Lista();
    }

    /**
     * Obtiene la lista de usuarios del grafo.
     *
     * @return la lista de usuarios representada por {@link Lista}
     */
    public Lista getUsuarios() {
        return usuarios;
    }

    /**
     * Establece la lista de usuarios del grafo.
     *
     * @param usuarios nueva lista de usuarios que reemplazará a la actual
     */
    public void setUsuarios(Lista usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Verifica si el grafo no contiene usuarios.
     *
     * @return {@code true} si la lista de usuarios está vacía, {@code false} en
     * caso contrario
     */
    public boolean isEmpty() {
        return this.usuarios.isEmpty();
    }

    /**
     * Busca un usuario en el grafo por su nombre.
     *
     * @param nombreUsuario nombre del usuario a buscar
     * @return el vértice correspondiente al usuario si existe, o {@code null}
     * si no se encuentra
     */
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

    /**
     * Inserta un nuevo usuario en el grafo usando su nombre, si no existe ya.
     * En caso de existir, muestra un mensaje de advertencia.
     *
     * @param nombreUsuario nombre del nuevo usuario a insertar
     */
    public void insertarUsuario(String nombreUsuario) {
        if (this.buscar(nombreUsuario) == null) {
            Vertice vertice = new Vertice(nombreUsuario);
            vertice.setNumVertice(this.usuarios.getSize());
            this.usuarios.insertarFinal(vertice);
        } else {
            JOptionPane.showMessageDialog(null, "La estacion ya existe.");
        }
    }

    /**
     * Inserta un usuario ya creado (vértice) en el grafo si no existe uno con
     * el mismo nombre. En caso de existir, muestra un mensaje de advertencia.
     *
     * @param usuario vértice que representa al usuario a insertar
     */
    public void insertarUsuario2(Vertice usuario) {
        if (this.buscar(usuario.getNombre()) == null) {
            usuario.setNumVertice(this.usuarios.getSize());
            this.usuarios.insertarFinal(usuario);
        } else {
            JOptionPane.showMessageDialog(null, "La estacion ya existe.");
        }
    }

    /**
     * Crea una relación de seguimiento entre dos usuarios. El usuario
     * {@code nombreUsuario1} comenzará a seguir a {@code nombreUsuario2}.
     * Muestra mensajes dependiendo de si los usuarios existen o no.
     *
     * @param nombreUsuario1 nombre del usuario que seguirá a otro
     * @param nombreUsuario2 nombre del usuario que será seguido
     */
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

    /**
     * Crea una relación de seguimiento entre dos usuarios, pensada para uso
     * durante la carga inicial de datos. No muestra el mensaje de confirmación
     * de seguimiento exitoso, pero sí muestra mensajes de error si algún
     * usuario no existe.
     *
     * @param nombreUsuario1 nombre del usuario que seguirá a otro
     * @param nombreUsuario2 nombre del usuario que será seguido
     */
    public void seguirUsuarioCarga(String nombreUsuario1, String nombreUsuario2) {
        if (this.buscar(nombreUsuario1) != null && this.buscar(nombreUsuario2) != null) {
            Vertice usuarioInicial = buscar(nombreUsuario1);
            Vertice usuarioFinal = buscar(nombreUsuario2);

            usuarioInicial.getAdyacentes().insertarFinal(usuarioFinal);
            //JOptionPane.showMessageDialog(null, "Usuario " + nombreUsuario1 + " siguiendo a " + nombreUsuario2);
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

    /**
     * Elimina la relación de seguimiento entre dos usuarios si existe. El
     * usuario {@code nombreUsuario1} dejará de seguir a {@code nombreUsuario2}.
     * Muestra mensajes informativos o de error según corresponda.
     *
     * @param nombreUsuario1 nombre del usuario que deja de seguir
     * @param nombreUsuario2 nombre del usuario que era seguido
     */
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

    /**
     * Muestra mediante un cuadro de diálogo la lista de usuarios adyacentes
     * (aquellos a los que sigue) un usuario dado.
     *
     * @param nombreUsuario nombre del usuario del que se desea ver sus
     * adyacentes
     */
    public void getAdjacent(String nombreUsuario) {
        if (this.buscar(nombreUsuario) != null) {
            Vertice usuario = this.buscar(nombreUsuario);
            JOptionPane.showMessageDialog(null, usuario.printAdy());
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no existe.");
        }
    }

    /**
     * Elimina completamente un usuario del grafo. Esto incluye:
     * 
     * Eliminarlo de la lista principal de usuarios.
     * Eliminar todas las referencias a él en las listas de adyacencia de
     * los demás usuarios.
     * Re-enumerar los índices {@code numVertice} de los vértices
     * restantes.
     * 
     * Muestra mensajes informativos si el grafo está vacío o si el usuario no
     * existe.
     *
     * @param nombreUsuario nombre del usuario que se desea eliminar
     */
    public void eliminarUsuario(String nombreUsuario) {
        if (this.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El grafo está vacío.");
            return;
        }

        //Buscar el vértice a eliminar
        Vertice usuarioAEliminar = this.buscar(nombreUsuario);
        if (usuarioAEliminar == null) {
            JOptionPane.showMessageDialog(null, "El usuario " + nombreUsuario + " no existe.");
            return;
        }

        //Desligar al usuario de TODAS las listas de adyacencia
        Nodo aux = this.usuarios.getpFirst();
        while (aux != null) {
            Vertice v = (Vertice) aux.getDato();

            if (v != usuarioAEliminar) {
                Lista adys = v.getAdyacentes();

                // Como tu Lista.buscar() y eliminarPorReferencia() trabajan por ==,
                // podemos eliminar por referencia al objeto usuarioAEliminar.
                while (adys.buscar(usuarioAEliminar)) {
                    adys.eliminarPorReferencia(usuarioAEliminar);
                }
            }

            aux = aux.getPnext();
        }

        //Eliminar el vértice de la lista principal de usuarios
        this.usuarios.eliminarPorReferencia(usuarioAEliminar);

        //Re-enumerar numVertice para mantenerlos coherentes
        aux = this.usuarios.getpFirst();
        int idx = 0;
        while (aux != null) {
            Vertice v = (Vertice) aux.getDato();
            v.setNumVertice(idx);
            idx++;
            aux = aux.getPnext();
        }

        JOptionPane.showMessageDialog(null, "Usuario " + nombreUsuario + " eliminado correctamente.");
    }

    /**
     * Destruye el contenido lógico del grafo reinicializando la lista de
     * usuarios a una nueva instancia vacía.
     */
    public void destruir() {
        this.usuarios = new Lista();
    }

    /**
     * Devuelve una representación textual del grafo, donde cada línea contiene
     * el nombre de un usuario seguido de sus adyacentes. Si el grafo está
     * vacío, devuelve el texto {@code "Grafo vacio"}.
     *
     * @return una cadena con la representación de los usuarios y sus
     * adyacencias, o {@code "Grafo vacio"} si no hay usuarios
     */
    @Override
    public String toString() {
        if (!this.isEmpty()) {
            String usuariosStr = "";
            Nodo aux = this.usuarios.getpFirst();
            while (aux.getPnext() != null) {
                Vertice verticeActual = (Vertice) aux.getDato();
                usuariosStr += verticeActual.getNombre() + " ---> " + verticeActual.printAdy() + "\n";
                aux = aux.getPnext();
            }
            Vertice verticeActual = (Vertice) aux.getDato();
            usuariosStr += verticeActual.getNombre() + " ---> " + verticeActual.printAdy();
            return usuariosStr;
        } else {
            return "Grafo vacio";
        }
    }
}
