/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalautomation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * The WorkingHour class represents the working hours of doctors.
 * It includes functionalities to retrieve the list of available working hours
 * and manage the attributes associated with each working hour.
 */
public class WorkingHour {
    // Attributes of the working hour
    private int id, doctor_id;
    private String doctorName,wdate, status;
    HospitalDBConnection conn = new HospitalDBConnection();
    Statement st = null;
    ResultSet rs = null;
    
    
    /**
     * Retrieves the list of available working hours for a specific doctor.
     *
     * @param doctor_id The ID of the doctor whose working hours are to be fetched.
     * @return An ArrayList of WorkingHour objects representing the available working hours.
     * @throws SQLException If a database error occurs.
     */
    public ArrayList<WorkingHour> getWhourList(int doctor_id) throws SQLException {
		ArrayList<WorkingHour> list = new ArrayList<>();

		WorkingHour obj;
		try {
			Connection con = conn.connDb();
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM whour WHERE status ='a' AND doctor_id=" + doctor_id);

			while (rs.next()) {
                            // Iterate through the result set and populate the working hour list
				obj = new WorkingHour();
				obj.setId(rs.getInt("id"));
				obj.setDoctor_id(rs.getInt("doctor_id"));
				obj.setDoctorName(rs.getString("doctor_name"));
				obj.setStatus(rs.getString("status"));
				obj.setWdate(rs.getString("wdate"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

    

	public WorkingHour() {

	}

    public WorkingHour(int id,int doctor_id, String doctorName, String wdate, String status) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.doctorName = doctorName;
        this.wdate = wdate;
        this.status = status;
    }

    
    public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
    
    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    

    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

    public String getWdate() {
        return wdate;
        
    }
    
    public void setWdate(String wdate){
        this.wdate = wdate;
    }
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

