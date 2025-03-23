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
/**
 *The Appointment class handles appointment-related operations, such as 
 * retrieving, deleting, and managing appointment records in the database.
 * @author Aylin Sahin
 */
public class Appointment {
    // Attributes of the Appointment class
    private int id, doctorID, hastaID;
	private String doctorName, hastaName, appDate;
        // Database connection object
	HospitalDBConnection conn = new HospitalDBConnection();
        // SQL utility objects
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	public Appointment(int id, int doctorID, int hastaID, String doctorName, String hastaName, String appDate) {
		super();
		this.id = id;
		this.doctorID = doctorID;
		this.hastaID = hastaID;
		this.doctorName = doctorName;
		this.hastaName = hastaName;
		this.appDate = appDate;
	}

	public Appointment() {
	}

        
        /**
     * Deletes an appointment from the database based on the given date and 
     * updates the corresponding working hour status for the doctor.
     *
     * @param date The appointment date to delete.
     * @param name The doctor's name whose appointment needs to be deleted.
     */
	public void deleteAppoint(String date, String name) {
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			String query1 = "DELETE FROM appointment WHERE app_date='" + date + "'";
			String query2 = "UPDATE whour SET status='a' WHERE doctor_name='" + name + "' AND wdate='" + date + "' ";

			preparedStatement = con.prepareStatement(query1);
			preparedStatement.executeUpdate();
                        
                        // Update the working hour status
			preparedStatement = con.prepareStatement(query2);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

        /**
     * Retrieves a list of appointments for a specific patient.
     *
     * @param hasta_id The ID of the patient.
     * @return A list of Appointment objects for the given patient.
     * @throws SQLException If a database error occurs.
     */
	public ArrayList<Appointment> getHastaList(int hasta_id) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;
		Connection con = conn.connDb();

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE hasta_id =" + hasta_id);
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setAppDate(rs.getString("app_date"));
				list.add(obj);  // Add the appointment to the list
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}

		return list;
	}

        /**
     * Retrieves a list of appointments for a specific doctor.
     *
     * @param doctor_id The ID of the doctor.
     * @return A list of Appointment objects for the given doctor.
     * @throws SQLException If a database error occurs.
     */
	public ArrayList<Appointment> getRandevuList(int doctor_id) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;
		Connection con = conn.connDb();

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_id =" + doctor_id);
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setAppDate(rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
                    // Close database resources
			st.close();
			rs.close();
			con.close();
		}

		return list;
	}
        
        /**
     * Retrieves a list of appointments for a specific patient (misnamed method).
     * 
     * @param doctor_id The ID of the patient (should be patient ID instead).
     * @return A list of Appointment objects.
     * @throws SQLException If a database error occurs.
     */
	public ArrayList<Appointment> getDoctorList(int doctor_id) throws SQLException {
		ArrayList<Appointment> list = new ArrayList<>();
		Appointment obj;
		Connection con = conn.connDb();

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM appointment WHERE hasta_id =" + doctor_id);
			while (rs.next()) {
				obj = new Appointment();
				obj.setId(rs.getInt("id"));
				obj.setDoctorID(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setHastaID(rs.getInt("hasta_id"));
				obj.setHastaName(rs.getString("hasta_name"));
				obj.setAppDate(rs.getString("app_date"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}

		return list;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public int getHastaID() {
		return hastaID;
	}

	public void setHastaID(int hastaID) {
		this.hastaID = hastaID;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getHastaName() {
		return hastaName;
	}

	public void setHastaName(String hastaName) {
		this.hastaName = hastaName;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
}
