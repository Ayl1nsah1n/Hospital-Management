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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *PatientPage represents the GUI page for patients to view doctors, working hours, and make appointments.
 * @author Aylin Sahin
 */
public class PatientPage extends javax.swing.JFrame {
 
     // Instances of patient, clinic, and appointment-related objects
    private static Patient patient = new Patient();
    private Clinic clinic = new Clinic();
    private DefaultTableModel doctorModel;
    private Object[] doctorData = null;
    private WorkingHour whour = new WorkingHour();
    private DefaultTableModel whourModel;
    private Object[] whourData = null;
    private int selectDoctorID = 0;
    private String selectDoctorName = null;
    private DefaultTableModel appointModel;
    private Object[] appointData = null;
    private Appointment appoint = new Appointment();
    /**
     * 
     * Creates new form PatientPage
     */
    public PatientPage(Patient patient) throws SQLException {
        initComponents();
        this.patient = patient;
        JMenuItem deleteMenu = new JMenuItem("Delete");
        
         // Initialize doctor table model
         doctorModel = new DefaultTableModel();
		Object[] colDoktor = new Object[2];
		colDoktor[0] = "ID";
		colDoktor[1] = "NAME";
		doctorModel.setColumnIdentifiers(colDoktor);
		doctorData = new Object[2];
                
                 // Initialize working hour table model
                whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "DATE";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
                
                // Initialize appointment table model
                appointModel = new DefaultTableModel();
		Object[] colAppoint = new Object[3];
		colAppoint[0] = "ID";
		colAppoint[1] = "DOCTOR";
		colAppoint[2] = "DATE";
		appointModel.setColumnIdentifiers(colAppoint);
		appointData = new Object[3];
                
                 // Populate the appointment table with patient data
                 for (int i = 0; i < appoint.getHastaList(patient.getId()).size(); i++) {
			appointData[0] = appoint.getHastaList(patient.getId()).get(i).getId();
			appointData[1] = appoint.getHastaList(patient.getId()).get(i).getDoctorName();
			appointData[2] = appoint.getHastaList(patient.getId()).get(i).getAppDate();
			appointModel.addRow(appointData);
		}
                table_appoint.setModel(appointModel);
          
                  // Populate the clinic selection combo box
         select_clinic.addItem(new Item(-1, "Choose Clinic"));

        for (int i = 0; i < clinic.getList().size(); i++){
            select_clinic.addItem(new Item(clinic.getList().get(i).getId(), clinic.getList().get(i).getName()));
        }
        
        // Add an action listener for clinic selection
        select_clinic.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               if(select_clinic.getSelectedIndex() != 0){ // If a valid clinic is selected
                   JComboBox c = (JComboBox) e.getSource();
                   Item item = (Item) c.getSelectedItem();
                   DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
                   clearModel.setRowCount(0);  // Clear the doctor table
                   try {
                        // Populate the doctor table based on the selected clinic
                       for(int i = 0; i < clinic.getClinicDoctorList(item.getKey()).size(); i++){
                         doctorData[0] = clinic.getClinicDoctorList(item.getKey()).get(i).getId();
			 doctorData[1] = clinic.getClinicDoctorList(item.getKey()).get(i).getName();
			 doctorModel.addRow(doctorData);  
                       }
                       table_doctor.setModel(doctorModel);
                   } catch (SQLException ex) {
                       Logger.getLogger(PatientPage.class.getName()).log(Level.SEVERE, null, ex);
                   }
               }else{
                   DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		   clearModel.setRowCount(0);
               }
                  
           }
        });
        
        table_appoint.setComponentPopupMenu(jPopupMenu1);
        jPopupMenu1.add(deleteMenu);

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String selDate = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 2);
					String selDoctorName = (String) table_appoint.getValueAt(table_appoint.getSelectedRow(), 1);
					appoint.deleteAppoint(selDate, selDoctorName);
					updateAppointModel(patient.getId());

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
                
                table_appoint.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				try {
					int selectedRow = table_appoint.rowAtPoint(point);
					table_appoint.setRowSelectionInterval(selectedRow, selectedRow);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
        
    }
    
    public PatientPage() throws SQLException {
        this(new Patient()); 
    }
    
    
    /**
     * Updates the working hour table based on the selected doctor.
     *
     * @param doctor_id The ID of the selected doctor.
     */
    public void updateWhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < whour.getWhourList(doctor_id).size(); i++) {
			whourData[0] = whour.getWhourList(doctor_id).get(i).getId();
			whourData[1] = whour.getWhourList(doctor_id).get(i).getWdate();
			whourModel.addRow(whourData);
		}
	}
    
    
    /**
     * Updates the appointment table based on the patient's ID.
     *
     * @param hasta_id The ID of the patient.
     */
    public void updateAppointModel(int hasta_id) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_appoint.getModel();
		clearModel.setRowCount(0);
		try {
			for (int i = 0; i < appoint.getHastaList(hasta_id).size(); i++) {
				appointData[0] = appoint.getHastaList(hasta_id).get(i).getId();
				appointData[1] = appoint.getHastaList(hasta_id).get(i).getDoctorName();
				appointData[2] = appoint.getHastaList(hasta_id).get(i).getAppDate();
				appointModel.addRow(appointData);
			}
                       
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_doctor = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        select_clinic = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btn_selDoctor = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_whour = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        addApp = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_appoint = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("WELCOME");

        jButton1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jButton1.setText("LOGOUT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        table_doctor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "NAME"
            }
        ));
        jScrollPane1.setViewportView(table_doctor);

        jLabel2.setText("Doctor List");

        jLabel3.setText("Clinic Name");

        jLabel4.setText("Choose Doctor");

        btn_selDoctor.setText("CHOOSE");
        btn_selDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_selDoctorActionPerformed(evt);
            }
        });

        table_whour.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "DATE"
            }
        ));
        jScrollPane2.setViewportView(table_whour);

        jLabel5.setText("Add Appointment");

        addApp.setText("ADD");
        addApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAppActionPerformed(evt);
            }
        });

        jLabel6.setText("Avaliable Hours");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_selDoctor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(select_clinic, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .addComponent(addApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 113, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(select_clinic, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_selDoctor, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addApp, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Appointment System", jPanel1);

        table_appoint.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "DOCTOR", "DATE"
            }
        ));
        jScrollPane3.setViewportView(table_appoint);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("My Appointments", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Action for selecting a doctor and displaying their working hours.
     */
    private void btn_selDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_selDoctorActionPerformed
        // TODO add your handling code here:
        int row = table_doctor.getSelectedRow();
	    if (row >= 0) {
		String value = table_doctor.getModel().getValueAt(row, 0).toString();
		int id = Integer.parseInt(value);
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
			try {
                            // Populate the working hour table for the selected doctor
				for (int i = 0; i < whour.getWhourList(id).size(); i++) {
					whourData[0] = whour.getWhourList(id).get(i).getId();
					whourData[1] = whour.getWhourList(id).get(i).getWdate();
					whourModel.addRow(whourData);

				}
	         		} catch (SQLException e1) {
				e1.printStackTrace();
			}
			table_whour.setModel(whourModel);
			selectDoctorID = id;  // Store the selected doctor's ID
			selectDoctorName = table_doctor.getModel().getValueAt(row, 1).toString();
	        	} else {
			JOptionPane.showMessageDialog(null, "Please choose a doctor", null, JOptionPane.ERROR_MESSAGE);
				}
        
    }//GEN-LAST:event_btn_selDoctorActionPerformed

    /**
     * Action for adding a new appointment.
     */
    private void addAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAppActionPerformed
        // TODO add your handling code here:
        
        int selRow = table_whour.getSelectedRow();
	if (selRow >= 0) {
	    String date = table_whour.getModel().getValueAt(selRow, 1).toString();
	    try {
                // Add a new appointment
		boolean control = patient.addAppointment(selectDoctorID, patient.getId(), selectDoctorName,patient.getName(), date);
		if (control) {
		JOptionPane.showMessageDialog(null, "Mission Successful!", null, JOptionPane.INFORMATION_MESSAGE);
		patient.updateWhourStatus(selectDoctorID, date);
		updateWhourModel(selectDoctorID);
		updateAppointModel(patient.getId());

		} else {
		JOptionPane.showMessageDialog(null, "ERROR", null, JOptionPane.ERROR_MESSAGE);
	    }

	} catch (SQLException e1) {
	    e1.printStackTrace();
	}
	} else {
	    JOptionPane.showMessageDialog(null, "Please enter a valid date!");
     }
    }//GEN-LAST:event_addAppActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        LoginPage login = new LoginPage();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PatientPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PatientPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PatientPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PatientPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
	public void run() {
	try {
	PatientPage frame = new PatientPage(patient);
	frame.setVisible(true);
	} catch (Exception e) {
	e.printStackTrace();
				}
			}
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addApp;
    private javax.swing.JButton btn_selDoctor;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<Item> select_clinic;
    private javax.swing.JTable table_appoint;
    private javax.swing.JTable table_doctor;
    private javax.swing.JTable table_whour;
    // End of variables declaration//GEN-END:variables
}
