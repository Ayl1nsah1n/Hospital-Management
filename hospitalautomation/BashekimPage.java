/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.hospitalautomation;


import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


/**
 * BashekimPage represents the main GUI page for managing doctors and clinics.
 * @author Aylin Sahin
 */
public class BashekimPage extends javax.swing.JFrame {
    
    // Static instances of Bashekim and Clinic classes
    static Bashekim bashekim = new Bashekim();
    static Clinic clinic = new Clinic();
    
    // Models for tables and data arrays
    private DefaultTableModel doctorModel = null;
    private Object[] doctorData = null;
    private DefaultTableModel clinicModel = null;
    private Object[] clinicData = null;
    Object[] workerData = new Object[2];
    DefaultTableModel workerModel = new DefaultTableModel();
    
    
    //Constructor to initialize BashekimPage with a Bashekim instance.
    public BashekimPage(Bashekim bashekim)throws SQLException {
        initComponents();
        
        
        selectDoctor.addItem(new Item(-1, "Choose Doctor"));
        // Create a Delete menu for the popup menu
        JMenuItem deleteMenu = new JMenuItem("Delete");
        JMenuItem updateMenu = new JMenuItem ("Update");
        doctorModel = new DefaultTableModel();
        Object[] colDoctorName = new Object[4];
        colDoctorName[0] = "ID";
        colDoctorName[1] = "Name Surname";
        colDoctorName[2] = "ID Number";
        colDoctorName[3] = "Password";
        doctorModel.setColumnIdentifiers(colDoctorName);
        doctorData = new Object[4];
        // Populate the doctor table with data from Bashekim
        try {
            for(int i = 0; i < bashekim.getDoctorList().size(); i++){
                doctorData[0] = bashekim.getDoctorList().get(i).getId();
                doctorData[1] = bashekim.getDoctorList().get(i).getName();
                doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
                doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
                doctorModel.addRow(doctorData);
            }
            jTable2.setModel(doctorModel);
            
            jTable2.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                 // Add a selection listener to the doctor table
                try{
                    txt_userId.setText(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                }catch(Exception ex){
                    // Ignore exception for invalid selection
                }
            }
        });
         } catch (SQLException ex) {
            Logger.getLogger(BashekimPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Add a listener to update doctor details when edited in the table
        jTable2.getModel().addTableModelListener(new TableModelListener(){
            @Override
            public void tableChanged(TableModelEvent e){
                if(e.getType() == TableModelEvent.UPDATE){
                    int selectID = Integer.parseInt(jTable2.getValueAt(jTable2.getSelectedRow(), 0).toString());
                    String selectName = jTable2.getValueAt(jTable2.getSelectedRow(), 1).toString();
                    String selectTcno = jTable2.getValueAt(jTable2.getSelectedRow(), 2).toString();
                    String selectPass = jTable2.getValueAt(jTable2.getSelectedRow(), 3).toString();
                    try {
                        bashekim.updateDoctor(selectID, selectTcno, selectPass, selectName);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(BashekimPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        // Initialize clinic table model and populate it
        clinicModel = new DefaultTableModel();
        Object[] colClinic = new Object[2];
        colClinic[0] = "ID";
        colClinic[1] = "Name";
        clinicModel.setColumnIdentifiers(colClinic);
        clinicData = new Object[2];
    
    for (int i = 0; i < clinic.getList().size(); i++) {
        clinicData[0] = clinic.getList().get(i).getId();
        clinicData[1] = clinic.getList().get(i).getName();
        clinicModel.addRow(clinicData);
    }
      clinic_table.setModel(clinicModel); 
      
      
      // Initialize worker table model
      Object[] colWorker = new Object[2];
      colWorker[0] ="ID";
      colWorker[1] ="NAME SURNAME";
      workerModel.setColumnIdentifiers(colWorker);
     
      
      
      updateMenu.addActionListener(new ActionListener(){
          @Override
	    public void actionPerformed(ActionEvent e) {
	    int selID = Integer.parseInt(clinic_table.getValueAt(clinic_table.getSelectedRow(), 0).toString());
	        Clinic selectClinic = clinic.getFech(selID);
		UpdateClinicPage updateGUI = new UpdateClinicPage(selectClinic);
		updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		updateGUI.setVisible(true);
		updateGUI.addWindowListener(new WindowAdapter() {
		public void windowClosed(WindowEvent e) {
		    try {
			updateClinicModel();
			} catch (SQLException e1) {
			    e1.printStackTrace();
			}
		}
	    });
	}
      });

      
      // Add delete action to popup menu
      deleteMenu.addActionListener(new ActionListener(){
          @Override
          public void actionPerformed(ActionEvent arg0){
              if(Helper.confirm("Sure")){
                  int selID = Integer.parseInt(clinic_table.getValueAt(clinic_table.getSelectedRow(), 0).toString());
                  try {
                      if(clinic.deleteClinic(selID)){
                          updateClinicModel();
                   JOptionPane.showMessageDialog(null, "Transaction Successful!", null, JOptionPane.INFORMATION_MESSAGE);
                          return;
                      }else{
                          JOptionPane.showMessageDialog(null, "ERROR", null, JOptionPane.ERROR);
                      }
                  } catch (SQLException ex) {
                      Logger.getLogger(BashekimPage.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
          }
      });
      
      // Attach the popup menu to the clinic table
      clinic_table.setComponentPopupMenu(jPopupMenu1);
      jPopupMenu1.add(deleteMenu);
      jPopupMenu1.add(updateMenu);
      
      // Add mouse listener for row selection in clinic table
      clinic_table.addMouseListener(new MouseAdapter(){
          @Override
          public void mousePressed(MouseEvent e){
              Point point =e.getPoint();
              int selectedRow = clinic_table.rowAtPoint(point);
              clinic_table.setRowSelectionInterval(selectedRow, selectedRow);
          }
      });
      
      // Populate selectDoctor combo box with doctor data
      for(int i = 0; i < bashekim.getDoctorList().size(); i++){
          selectDoctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
      }
      // Listener for selecting a doctor from the combo box
      selectDoctor.addActionListener(e -> {
          JComboBox c = (JComboBox) e.getSource();
          Item item = (Item) c.getSelectedItem();
          System.out.println(item.getKey() + " : " + item.getValue());
                  
      });
    }
        
        
    
        
    

    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        jPopupMenu4 = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_name = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        add = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txt_userId = new javax.swing.JTextField();
        delete = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt_clinic = new javax.swing.JTextField();
        selectDoctor = new javax.swing.JComboBox<>();
        addWorker = new javax.swing.JButton();
        addClinic1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        clinic_table = new javax.swing.JTable();

        jLabel6.setText("jLabel6");

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 204, 204));

        jLabel1.setText("WELCOME");

        logout.setText("LOGOUT");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        jLabel2.setText("NAME");

        jLabel3.setText("ID NUMBER");

        jLabel4.setText("PASSWORD");

        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        jLabel5.setText("USER ID");

        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME SURNAME", "ID NUMBER", "PASSWORD"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                        .addComponent(txt_password)
                        .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_userId, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_userId, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Doctor Management", jPanel1);

        jLabel8.setText("CLINIC NAME");

        selectDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDoctorActionPerformed(evt);
            }
        });

        addWorker.setText("ADD");
        addWorker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWorkerActionPerformed(evt);
            }
        });

        addClinic1.setText("ADD");
        addClinic1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addClinic1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME SURNAME"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );

        clinic_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "CLINIC NAME"
            }
        ));
        jScrollPane4.setViewportView(clinic_table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(addClinic1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                        .addComponent(selectDoctor, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addWorker, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txt_clinic, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_clinic, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addClinic1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(selectDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addWorker, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clinic Management", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

     /**
     * Default constructor that initializes with a default Bashekim instance.
     */
    public BashekimPage() throws SQLException {
        this(new Bashekim()); 
    }
    
    //Update clinic table model
    public void updateClinicModel() throws SQLException{
       DefaultTableModel clearModel = (DefaultTableModel) clinic_table.getModel();
       clearModel.setRowCount(0);
       for(int i = 0; i < clinic.getList().size(); i++){
           clinicData[0] = clinic.getList().get(i).getId();
           clinicData[1] = clinic.getList().get(i).getName();
           clinicModel.addRow(clinicData);
       }

    }
    
    // Update doctor table model
    public void updateDoctorModel() throws SQLException{
        DefaultTableModel clearModel = (DefaultTableModel) jTable2.getModel();
        clearModel.setRowCount(0);
        for(int i = 0; i < bashekim.getDoctorList().size(); i++){
                doctorData[0] = bashekim.getDoctorList().get(i).getId();
                doctorData[1] = bashekim.getDoctorList().get(i).getName();
                doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
                doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
                doctorModel.addRow(doctorData);
            }
    }
    
    // Action for logging out
    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        LoginPage login = new LoginPage();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_logoutActionPerformed

    // Action for adding a new doctor
    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
       
       String id = txt_id.getText();
       String name =txt_name.getText();
       String pass = new String(txt_password.getPassword());
       // Validation for fields
       if(id.isEmpty() || name.isEmpty() || pass.isEmpty()){
           JOptionPane.showMessageDialog(null, "Please fill in all fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
       }
        // Validation for ID, Name, and Password
       Pattern idPattern = Pattern.compile("^\\d{7}$"); // ID yalnızca 11 haneli rakam olmalı
       Pattern namePattern = Pattern.compile("^[A-Za-z]+ [A-Za-z]+$"); // İsim yalnızca harflerden oluşmalı
       Pattern passPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).+$"); // Şifre en az bir harf ve bir rakam içermeli

    // ID doğrulama
    Matcher idMatcher = idPattern.matcher(id);
    if (!idMatcher.matches()) {
        JOptionPane.showMessageDialog(null, "ID must contain exactly 7 digits.", "ERROR", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // İsim doğrulama
    Matcher nameMatcher = namePattern.matcher(name);
    if (!nameMatcher.matches()) {
        JOptionPane.showMessageDialog(null, "Name can only contain Name Surname and only english characters!", "ERROR", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Şifre doğrulama
    Matcher passMatcher = passPattern.matcher(pass);
    if (!passMatcher.matches()) {
        JOptionPane.showMessageDialog(null, "Password must contain at least one letter and one number.", "ERROR", JOptionPane.ERROR_MESSAGE);
        return;
    }
           // Add the doctor to the system
           try {
               boolean control = bashekim.addDoctor(id, pass, name);
               if(control){
                   JOptionPane.showMessageDialog(null, "Transaction Successful!", null, JOptionPane.INFORMATION_MESSAGE);
                   txt_name.setText(null);
                   txt_id.setText(null);
                   txt_password.setText(null);
                   updateDoctorModel();
                   
               }
           } catch (SQLException ex) {
               Logger.getLogger(BashekimPage.class.getName()).log(Level.SEVERE, null, ex);
           }
       
    }//GEN-LAST:event_addActionPerformed
    
     // Action for deleting a doctor
    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
       
        
        if(txt_userId.getText().length() == 0){
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            if(Helper.confirm("Sure")){
            int selectID = Integer.parseInt(txt_userId.getText());
            try {
                boolean control = bashekim.deleteDoctor(selectID);
                if(control){
                    JOptionPane.showMessageDialog(null, "Transaction Successful!", null, JOptionPane.INFORMATION_MESSAGE);
                    txt_userId.setText(null);
                    updateDoctorModel();
                }
            } catch (SQLException ex) {
                Logger.getLogger(BashekimPage.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }
    }//GEN-LAST:event_deleteActionPerformed
    
    // Action for adding a new clinic
    private void addClinic1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClinic1ActionPerformed
       
        if(txt_clinic.getText().length() == 0){
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "ERROR", JOptionPane.ERROR_MESSAGE);
          
        }else{
            try{
                if(clinic.addClinic(txt_clinic.getText())){
                   JOptionPane.showMessageDialog(null, "Transaction Successful!", null, JOptionPane.INFORMATION_MESSAGE);
                   txt_clinic.setText(null);
                   updateClinicModel();
                }
            }catch(SQLException e1){
                e1.printStackTrace();
            }
        }
    }//GEN-LAST:event_addClinic1ActionPerformed

    // Action for adding a worker to a clinic
    private void addWorkerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWorkerActionPerformed
        // TODO add your handling code here:
        int selRow = clinic_table.getSelectedRow();
        if(selRow >= 0){
            try {
                String selClinic = clinic_table.getModel().getValueAt(selRow, 0).toString();
                int selClinicId = Integer.parseInt(selClinic);
                Item doctorItem = (Item) selectDoctor.getSelectedItem();
                boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicId);
                if(control){
                    JOptionPane.showMessageDialog(null, "Transaction Successful!", null, JOptionPane.INFORMATION_MESSAGE);
                    DefaultTableModel clearModel = (DefaultTableModel) jTable1.getModel();
                    clearModel.setRowCount(0);
                    for(int i = 0; i < bashekim.getClinicDoctorList(selClinicId).size(); i++){
                        workerData[0] = bashekim.getClinicDoctorList(selClinicId).get(i).getId();
                        workerData[1] = bashekim.getClinicDoctorList(selClinicId).get(i).getName();
                        workerModel.addRow(workerData);
                    }
                    jTable1.setModel(workerModel);
                }else{
                    JOptionPane.showMessageDialog(null, "ERROR", null, JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(BashekimPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please choose a clinic!");
        }
    }//GEN-LAST:event_addWorkerActionPerformed

    private void selectDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectDoctorActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_selectDoctorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BashekimPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BashekimPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BashekimPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BashekimPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                try{
                    
                    BashekimPage frame = new BashekimPage(bashekim);
                    frame.setVisible(true);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton addClinic1;
    private javax.swing.JButton addWorker;
    private javax.swing.JTable clinic_table;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JPopupMenu jPopupMenu4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable4;
    private javax.swing.JButton logout;
    private javax.swing.JComboBox<Item> selectDoctor;
    private javax.swing.JTextField txt_clinic;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_name;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_userId;
    // End of variables declaration//GEN-END:variables
}
