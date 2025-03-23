/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalautomation;


import java.sql.*;
import java.util.ArrayList;

/**
 * The Clinic class represents a clinic in the hospital automation system.
 * It provides functionalities to manage clinic information and associated doctors.
 */
public class Clinic {
    HospitalDBConnection conn = new HospitalDBConnection();
    private int id;
    private String name;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        
    
        public Clinic(){}
        
    public Clinic(int id, String name){
        super();
        this.id = id;
        this.name = name;
    }
    
    
    /**
     * Retrieves the list of all clinics from the database.
     *
     * @return An ArrayList of Clinic objects.
     * @throws SQLException If a database error occurs.
     */
    public ArrayList<Clinic> getList() throws SQLException{
        ArrayList<Clinic> list = new ArrayList<>();
        Clinic obj ;
       Connection con = conn.connDb();
      
      try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM clinic");
            while(rs.next()){
                obj = new Clinic();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                list.add(obj);
            }
      }catch(SQLException e){
          e.printStackTrace();
      }finally{
         // Ensure resources are closed to avoid memory leaks
                st.close();
                rs.close();
                con.close();
            }
        return list;
    }
    
    
    /**
     * Adds a new clinic to the database.
     *
     * @param name The name of the clinic to add.
     * @return True if the clinic is successfully added, false otherwise.
     * @throws SQLException If a database error occurs.
     */
   public boolean addClinic(String name) throws SQLException{
        String query = "INSERT INTO clinic" + "(name) VALUES " + "(?)";
        boolean key = false;
        Connection con = conn.connDb();
        try { 
        st = con.createStatement();
        ps = con.prepareStatement(query);
        ps.setString(1, name);
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
     * Deletes a clinic from the database.
     *
     * @param id The ID of the clinic to delete.
     * @return True if the clinic is successfully deleted, false otherwise.
     * @throws SQLException If a database error occurs.
     */
   public boolean deleteClinic(int id) throws SQLException{
        String query = " DELETE FROM clinic WHERE id = ? ";
        boolean key = false;
        Connection con = conn.connDb();
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
   
   
   public Clinic getFech(int id) {
		Connection con = conn.connDb();
		Clinic c = new Clinic();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM clinic WHERE id=" + id);
			while (rs.next()) {
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
   
   public boolean updateClinic(int id, String name) throws SQLException{
        String query = "UPDATE clinic SET name = ? WHERE id = ?";
        boolean key = false;
        Connection con = conn.connDb();
        try { 
        st = con.createStatement();
        ps = con.prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, id);
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
     * Retrieves the list of doctors assigned to a specific clinic.
     *
     * @param clinic_id The ID of the clinic.
     * @return An ArrayList of User objects representing doctors in the clinic.
     * @throws SQLException If a database error occurs.
     */
   public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
	            Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.iduser,u.tcno,u.type,u.name,u.password FROM worker w LEFT JOIN user u ON w.user_id=u.iduser WHERE clinic_id= "+clinic_id);

			while (rs.next()) {
				obj = new User(rs.getInt("u.iduser"), rs.getString("u.tcno"), rs.getString("u.name"), rs.getString("u.password"),
						rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
    
    
    public int getId(){
        return id;
        
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
