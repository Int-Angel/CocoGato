/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cocogato;

/**
 *
 * @author Lenovo
 */
public class DB {
    public DB() 
    { 
        try  
        { 
            Class.forName("com.mysql.jdbc.Driver"); 
        }  
        catch (ClassNotFoundException ex)  
        { 
            System.out.println("Error al cargar el driver " + ex.getMessage()); 
        } 
    } 
}
