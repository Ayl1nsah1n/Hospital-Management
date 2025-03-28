/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitalautomation;

/**
 *
 * @author sayli
 */
public class User {
    private int id;
    String tcno, name, password, type;
    HospitalDBConnection conn = new HospitalDBConnection();
    
    public User(int id, String tcno, String name, String password, String type){
        super();
        this.id = id;
        this.tcno = tcno;
        this.password = password;
        this.name = name;
        this.type = type;
    }
    
    public User(){}

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTcno(){
        return tcno;
    }
    public void setTcno(String tcno){
        this.tcno = tcno;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    
    
    
    
}
