/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalautomation;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author sayli
 */
public class HospitalDBConnection {
    Connection c = null;
    public HospitalDBConnection(){}
    
    public Connection connDb(){
        try {
            this.c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospital?user=root&password=1234");
            
        return c;
        
        } catch (SQLException ex) {
            Logger.getLogger(HospitalDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    
    
}
