/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalautomation;

import java.sql.*;
import java.util.ArrayList;



/**
 *The Bashekim class extends the User class and provides additional 
 * functionalities for managing doctors and clinic-related operations.
 * @author sayli
 */
public class Bashekim extends User{
        Connection con = conn.connDb();
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
    
    public Bashekim(int id, String tcno, String name, String password, String type){
        super(id, tcno, name, password ,type);
    }
    
    public Bashekim(){}
    
    /**
     * Retrieves the list of all doctors from the database.
     *
     * @return An ArrayList of User objects representing doctors.
     * @throws SQLException If a database error occurs.
     */
    public ArrayList<User> getDoctorList() throws SQLException{
        ArrayList<User> list = new ArrayList<>();
        
        User obj ;
            try {
                st =con.createStatement();
                rs = st.executeQuery("SELECT * FROM user WHERE type = 'doktor' ");
        while(rs.next()){
            obj = new User(rs.getInt("iduser"), rs.getString("tcno"), rs.getString("name"), rs.getString("password"), rs.getString("type"));
            list.add(obj);
        }
            } catch (SQLException ex) {
              ex.printStackTrace();
            }
            return list;
    }
    
    
    /**
     * Retrieves the list of doctors assigned to a specific clinic.
     *
     * @param clinic_id The ID of the clinic.
     * @return An ArrayList of User objects representing clinic doctors.
     * @throws SQLException If a database error occurs.
     */
    public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException{
        ArrayList<User> list = new ArrayList<>();
        
        User obj ;
            try {
                st =con.createStatement();
                rs = st.executeQuery("SELECT u.iduser, u.tcno, u.type, u.name, u.password FROM worker e LEFT JOIN user u ON e.user_id = u.iduser WHERE clinic_id = " + clinic_id);
        while(rs.next()){
            obj = new User(rs.getInt("u.iduser"), rs.getString("u.tcno"), rs.getString("u.name"), rs.getString("u.password"), rs.getString("u.type"));
            list.add(obj);
        }
            } catch (SQLException ex) {
              ex.printStackTrace();
            }
            return list;
    }
    
    /**
     * Adds a new doctor to the database.
     *
     * @param tcno     The TC number of the doctor.
     * @param password The password of the doctor.
     * @param name     The name of the doctor.
     * @return True if the doctor is successfully added, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public boolean addDoctor(String tcno, String password, String name) throws SQLException{
        String query = "INSERT INTO user" + "(tcno, password, name, type) VALUES" + "(?, ?, ?, ?)";
        boolean key = false;
        try { 
        st = con.createStatement();
        ps = con.prepareStatement(query);
        ps.setString(1, tcno);
        ps.setString(2, password);
        ps.setString(3, name);
        ps.setString(4, "doktor");
        ps.executeUpdate();
        key = true;
       }catch(Exception e){
          e.printStackTrace();
       }
        
        if (key)
             return true;  
        else
            return false;
    }
    
    
    /**
     * Deletes a doctor from the database.
     *
     * @param id The ID of the doctor to delete.
     * @return True if the doctor is successfully deleted, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public boolean deleteDoctor(int id) throws SQLException{
        String query = "DELETE FROM user WHERE iduser = ?";
        boolean key = false;
        try { 
        st = con.createStatement();
        ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        key = true;
       }catch(Exception e){
          e.printStackTrace();
       }
        
        if (key)
             return true;  
        else
            return false;
    }
    
    /**
     * Updates the details of a doctor in the database.
     *
     * @param id       The ID of the doctor to update.
     * @param tcno     The new TC number.
     * @param password The new password.
     * @param name     The new name.
     * @return True if the doctor is successfully updated, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public boolean updateDoctor(int id, String tcno, String password, String name) throws SQLException{
        String query = "UPDATE user SET name = ?, tcno = ?, password = ? WHERE iduser = ?";
        boolean key = false;
        try { 
        st = con.createStatement();
        ps = con.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, tcno);
        ps.setString(3, password);
        ps.setInt(4, id);
        ps.executeUpdate();
        key = true;
       }catch(Exception e){
          e.printStackTrace();
       }
        
        if (key)
             return true;  
        else
            return false;
    }
    
    
    
    /**
     * Assigns a doctor to a specific clinic.
     *
     * @param user_id   The ID of the doctor.
     * @param clinic_id The ID of the clinic.
     * @return True if the doctor is successfully assigned, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public boolean addWorker(int user_id, int clinic_id) throws SQLException{
        String query = "INSERT INTO worker" + "(clinic_id, user_id) VALUES" + "(?, ?)";
        boolean key = false;
        int count = 0;
        try { 
        rs = st.executeQuery("SELECT * FROM worker WHERE clinic_id = " + clinic_id + " AND user_id = " + user_id);
        st = con.createStatement();
        while(rs.next()){
            count++;
        }
        if(count == 0){
        ps = con.prepareStatement(query);
        ps.setInt(1, clinic_id);
        ps.setInt(2, user_id);
        ps.executeUpdate();
        
        }
        key = true;
        
       }catch(Exception e){
          e.printStackTrace();
       }
        
        if (key)
             return true;  
        else
            return false;
    }
    
}
