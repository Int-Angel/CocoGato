
package cocogatoclient;

/**
 *
 * @author Lenovo
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
