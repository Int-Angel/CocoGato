
package cocogatoclient;

/**
 * Esta clase contiene las propiedades para almacenar el registro de la tabla
 * jugador de la DB correspondiente al usuario que inicio sesion
 * @author Miguel
 */
public class Jugador {

   /* public Jugador(int id, String usuario) {
        this.id = id;
        this.usuario = usuario;
    }*/
    
    static int id;
    static String usuario;
    static String contraseña; 
    static boolean conectado;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
