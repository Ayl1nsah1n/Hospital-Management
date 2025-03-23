/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitalautomation;
import java.sql.*;
/**
 *
 * @author sayli
 */
public class HospitalAutomation {

    public static void main(String[] args) {
        try {
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospital?user=root&password=1234");
        if (con != null) {
            System.out.println("Database connection successful!");
        } else {
            System.out.println("Database connection failed!");
        }
    } catch (SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }
    }
}
