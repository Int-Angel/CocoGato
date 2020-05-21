/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogatoserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class DB {
    private Connection con;
            
    public DB() 
    { 
         try  
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
            con = DriverManager.getConnection("jdbc:mysql://localhost/cocogato","root",""); 
        }  
        catch (ClassNotFoundException ex)  
        { 
            System.out.println("Error al cargar el driver " + ex.getMessage()); 
        } 
        catch (SQLException ex)  
        { 
            System.out.println("Error al cargar el driver " + ex.getMessage()); 
        } 
    } 
        
    public boolean insertPlayer(Jugador jugador) 
    { 
        try { 
            String sql = "INSERT INTO jugador(JugadorID, Usuario, Contraseña) VALUES ( ?, ?, ? );";
            PreparedStatement stmt = con.prepareStatement(sql); 
            stmt.setInt(1, jugador.id);
            stmt.setString(2, jugador.usuario);
            stmt.setString(3, jugador.contraseña);
            stmt.executeUpdate(); 
        } catch (SQLException ex) { 
            return false; 
        } 
        return true; 
    }
    
    public boolean insertAutoincrementPlayer(Jugador jugador) 
    { 
        try { 
            String sql = "INSERT INTO jugador(JugadorID, Usuario, Contraseña) VALUES (NULL, ?, ? );";
            PreparedStatement stmt = con.prepareStatement(sql); 
            stmt.setString(1, jugador.usuario);
            stmt.setString(2, jugador.contraseña);
            stmt.executeUpdate(); 
        } catch (SQLException ex) { 
            return false; 
        } 
        return true; 
    }
        
    public boolean insertGame(Partida partida) 
    { 
        try { 
            String sql = "INSERT INTO partido(IDPartida, Jugador1ID, Jugador2ID, IDGanador, Status) VALUES ( ?, ?, ?, ?, ? );";
            PreparedStatement stmt = con.prepareStatement(sql); 
            stmt.setInt(1, partida.id);
            stmt.setInt(2, partida.idJugador1);
            stmt.setInt(3, partida.idJugador2);
            stmt.setInt(4, partida.idGanador);
            stmt.setString(5, partida.status);
            stmt.executeUpdate(); 
        } catch (SQLException ex) { 
            return false; 
        } 
        return true; 
    }
    
    public boolean deletePlayer(Jugador jugador) 
    { 
        try { 
            String sql = "DELETE FROM jugador WHERE JugadorID = ?;";
            PreparedStatement stmt = con.prepareStatement(sql); 
            stmt.setInt(1, jugador.id); 
            stmt.executeUpdate(); 
        } catch (SQLException ex) { 
            return false; 
        }
        return true; 
    }
    
    public boolean deleteGame(Partida partida) 
    { 
        try { 
            String sql = "DELETE FROM partida WHERE id = ?;";
            PreparedStatement stmt = con.prepareStatement(sql); 
            stmt.setInt(1, partida.id); 
            stmt.executeUpdate(); 
        } catch (SQLException ex) { 
            return false; 
        } 
        return true; 
    }
    /*
    public boolean updatePlayer(Jugador jugador) 
    { 
        try { 
            String sql = "UPDATE alumno SET XXX = ? WHERE XXX = ?;";
            PreparedStatement stmt = con.prepareStatement(sql); 
            stmt.setInt(1, jugador.XXX);
            stmt.setInt(2, jugador.XXX);
            stmt.executeUpdate(); 
        } catch (SQLException ex) { 
            return false; 
        } 
        return true; 
    }
    
    public boolean updateGame(Partida partida) 
    { 
        try { 
            String sql = "UPDATE partida SET XXX = ? WHERE XXX = ?;";
            PreparedStatement stmt = con.prepareStatement(sql); 
            stmt.setInt(1, partida.XXX);
            stmt.setInt(2, partida.XXX);
            stmt.executeUpdate(); 
        } catch (SQLException ex) { 
            return false; 
        } 
        return true; 
    }
    */
    
    public ArrayList<Jugador> selectPlayers()
    { 
        ArrayList<Jugador> resultados = new ArrayList<Jugador>();
        ResultSet result;
        try
        {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM jugador");
            result = stmt.executeQuery();
            while (result.next())
            { 
                Jugador r = new Jugador();
                r.id = result.getInt("JugadorID");
                r.usuario = result.getString("Usuario");
                r.contraseña = result.getString("Contraseña");
                resultados.add(r);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error en la consulta");
            return null;
        }
        return resultados; 
    }
    
    public ArrayList<Partida> selectGames()
    { 
        ArrayList<Partida> resultados = new ArrayList<Partida>();
        ResultSet result;
        try
        {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM partida");
            result = stmt.executeQuery();
            while (result.next())
            { 
                Partida r = new Partida();
                r.id = result.getInt("IDPartida");
                r.idJugador1 = result.getInt("Jugador1ID");
                r.idJugador2 = result.getInt("Jugador2ID");
                r.idGanador = result.getInt("IDGanador");
                r.status = result.getString("Status");
                
                resultados.add(r);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error en la consulta");
            return null;
        }
        return resultados;
    }
    
    public Jugador selectPlayer(int id)
    { 
        Jugador resultado = new Jugador();
        try { 
            ResultSet result;
            PreparedStatement stmt = con.prepareStatement("SELECT (JugadorID, Usuario, Contraseña) FROM jugador WHERE JugadorID = ?;");
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            resultado.id = result.getInt("JugadorID");
            resultado.usuario = result.getString("Usuario");
            resultado.contraseña = result.getString("Contraseña");
        } catch (SQLException ex) { 
            return resultado; 
        }
        return resultado; 
    }
    
    public Jugador selectPlayer(String usuario)
    { 
        Jugador resultado = new Jugador();
        try { 
            ResultSet result;
            PreparedStatement stmt = con.prepareStatement("SELECT (JugadorID, Usuario, Contraseña) FROM jugador WHERE Usuario = ?;");
            stmt.setString(1, usuario);
            result = stmt.executeQuery();
            resultado.id = result.getInt("JugadorID");
            resultado.usuario = result.getString("Usuario");
            resultado.contraseña = result.getString("Contraseña");
        } catch (SQLException ex) { 
            return resultado; 
        }
        return resultado; 
    }
    
    public Partida selectGame(int id)
    { 
        Partida resultado = new Partida();
        try { 
            ResultSet result;
            PreparedStatement stmt = con.prepareStatement("SELECT (IDPartida, Jugador1ID, Jugador2ID, IDGanador, Status) FROM partida WHERE IDPartida = ?;");
            stmt.setInt(1, id);
            result = stmt.executeQuery();
            resultado.id = result.getInt("JugadorID");
            resultado.idJugador1 = result.getInt("Jugador1ID");
            resultado.idJugador2 = result.getInt("Jugador2ID");
            resultado.idGanador = result.getInt("IDGanador");
            resultado.status = result.getString("Status");
            
        } catch (SQLException ex) { 
            return resultado; 
        }
        return resultado; 
    }
}
