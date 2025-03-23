/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalautomation;

import javax.swing.JOptionPane;

/**
 *
 * @author sayli
 */
public class Helper {
    
    public static boolean confirm(String str){
        String msg;
        switch(str){
            case "Sure":
            msg = "Are you sure?";
            break;
            default:
                msg = str;
                break;
        }
        int res = JOptionPane.showInternalConfirmDialog(null, msg, "Attention", JOptionPane.YES_NO_OPTION);
        if(res == 0){
            return true;
        }else{
            return false;
        }
    }
}
