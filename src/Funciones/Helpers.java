/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;


public class Helpers {
    /**
     * 
     * Valida si un nombre de usuario es válido.Debe comenzar con '@' y luego tener al menos un carácter.Ejemplo válido: @usuario, @pepe123, @_nombre
        Ejemplo inválido: usuario, @@pepe, @
     * @param nombre
     * @return 
     */
    public boolean esUsuarioValido(String nombre) {
        if (nombre == null) return false;

        // ^@: empieza con @
        // [^@\\s]+: uno o más caracteres que no sean @ ni espacio
        return nombre.matches("^@[^@\\s]+$");
    }
}
