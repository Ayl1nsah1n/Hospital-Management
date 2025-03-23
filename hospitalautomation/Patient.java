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
import javax.swing.JOptionPane;

/**
 *The Patient class extends the User class and provides additional functionalities
 * specific to patients, such as registration, appointment creation, and updating
 * working hour statuses.
 * @author sayli
 */
public class Patient extends User{
    
    // SQL utility objects for database operations
    Statement st = null;
    ResultSet rs = null;
    Connection con = conn.connDb(); // Establishes a connection to the database
    PreparedStatement ps = null;
    
    public Patient() {
		super();
	}

	public Patient(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
	}

     /**
     * Registers a new patient in the system.
     *
     * @param tcno     The ID number of the patient.
     * @param password The password of the patient.
     * @param name     The name of the patient.
     * @return True if registration is successful, false otherwise.
     */
        public boolean register(String tcno, String password, String name) {
		int key = 0;  // Tracks the success of the operation
		boolean duplicate = false;  // Tracks whether the ID number is already in use
		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + " (?,?,?,?)";
		try {
			st = con.createStatement();
                        // Check if the TC number already exists in the database
			rs = st.executeQuery("SELECT * FROM user WHERE tcno='" + tcno + "'");
			while (rs.next()) {
				duplicate = true;
				JOptionPane.showMessageDialog(null, "There is a user for this id number.", null, JOptionPane.INFORMATION_MESSAGE);
				break;
			}
			if (!duplicate) {

                            // If no duplicate is found, insert the new patient into the database
				ps = con.prepareStatement(query);
				ps.setString(1, tcno);
				ps.setString(2, password);
				ps.setString(3, name);
				ps.setString(4, "hasta");

				ps.executeUpdate();
				key = 1;  // Registration successful

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}
                
        }       
        
        /**
     * Adds a new appointment for the patient.
     *
     * @param doctor_id   The ID of the doctor.
     * @param hasta_id    The ID of the patient.
     * @param doctor_name The name of the doctor.
     * @param hasta_name  The name of the patient.
     * @param appDate     The date of the appointment.
     * @return True if the appointment is successfully added, false otherwise.
     */
    public boolean addAppointment(int doctor_id, int hasta_id, String doctor_name, String hasta_name, String appDate) {
		int key = 0;  // Tracks the success of the operation
		String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES"
				+ " (?,?,?,?,?)";
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, doctor_id);
			ps.setString(2, doctor_name);
			ps.setInt(3, hasta_id);
			ps.setString(4, hasta_name);
			ps.setString(5, appDate);

			ps.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
                
                
    /**
     * Updates the status of a working hour for a doctor.
     *
     * @param doctor_id The ID of the doctor.
     * @param wdate     The date of the working hour to update.
     * @return True if the status is successfully updated, false otherwise.
     */
	public boolean updateWhourStatus(int doctor_id, String wdate) {
		int key = 0;
		String query = "UPDATE whour SET status = ? WHERE doctor_id= ? AND wdate= ?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, "p"); // Set status to "p" ("passive")
			ps.setInt(2, doctor_id);
			ps.setString(3, wdate);
			ps.executeUpdate();
			key = 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (key == 1) {
			return true;
		} else {
			return false;
		}
	}
        
        
        
}
