/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalautomation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *The Doctor class extends the User class and provides additional 
 * functionalities specific to doctors, such as managing working hours.
 * @author sayli
 */
public class Doctor extends User{
    
    // SQL utility objects for database operations
    Connection con = conn.connDb();  // Establishes a connection to the database
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        
    public Doctor(int id, String tcno, String name, String password, String type){
        super(id, tcno, name, password ,type);
    }
    
    public Doctor(){
        super();
    }
    
    /**
     * Deletes a working hour from the database.
     *
     * @param id The ID of the working hour to delete.
     * @return True if the working hour is successfully deleted, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public boolean deleteWhour(int id) throws SQLException {
		String query = "DELETE FROM whour WHERE id=?";
		boolean key = false;
		try {
			st = con.createStatement();
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (key)
			return true;
		else
			return false;
	}

    
    /**
     * Retrieves a list of available working hours for the specified doctor.
     *
     * @param doctor_id The ID of the doctor.
     * @return An ArrayList of WorkingHour objects.
     * @throws SQLException If a database error occurs.
     */
    public ArrayList<WorkingHour> getWhourList(int doctor_id) throws SQLException {
		ArrayList<WorkingHour> list = new ArrayList<>();

		WorkingHour obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM whour WHERE status ='a' AND doctor_id=" + doctor_id);

			while (rs.next()) {
				obj = new WorkingHour();
				obj.setId(rs.getInt("id"));
				//obj.setDoctor_id(rs.getInt("doctor_id"));
				//obj.setDoctor_name(rs.getString("doctor_name"));
				obj.setStatus(rs.getString("status"));
				obj.setWdate(rs.getString("wdate"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
    
    
    /**
     * Adds a new working hour for the specified doctor.
     *
     * @param doctor_id   The ID of the doctor.
     * @param doctor_name The name of the doctor.
     * @param wdate       The date of the working hour.
     * @return True if the working hour is successfully added, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public boolean addWhour(int doctor_id, String doctor_name, String wdate)throws SQLException{
        int key = 0;
        int count = 0;
        String query ="INSERT INTO whour" + "(doctor_id, doctor_name, wdate) VALUES " + "(?, ?, ?)";
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM whour WHERE status='a' AND doctor_id = " + doctor_id + " AND wdate = '"+wdate+"'" );
            
            while (rs.next()){
                count++;  // Increment count if the working hour already exists
                break;
            }
            if(count == 0){ // If no duplicate exists, insert the new working hour
            ps = con.prepareStatement(query);
            ps.setInt(1, doctor_id);
            ps.setString(2, doctor_name);
            ps.setString(3, wdate);
            ps.executeUpdate();
            }
            key = 1;
        } catch (SQLException ex) {
            Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(key == 1)
            return true;
        else
        return false;
    }
}
