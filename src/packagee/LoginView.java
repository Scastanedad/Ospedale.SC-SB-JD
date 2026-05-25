
package packagee;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import packagee.controller.LoginController;
import packagee.response.ServiceResponse;

public class LoginView extends javax.swing.JFrame {

    private int x, y;
    private final LoginController loginController;

    public LoginView() {
        initComponents();
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);
        this.loginController = LoginController.getInstance();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new packagee.PanelRound();
        panelRound2 = new packagee.PanelRound();
        btnClose = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelRound3 = new packagee.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        txtLoginUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtLoginPassword = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtRegFirstname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtRegLastname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRegId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtRegPhone = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtRegEmail = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtRegUsername = new javax.swing.JTextField();
        txtRegPassword = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtRegConfirm = new javax.swing.JTextField();
        cmbRegGender = new javax.swing.JComboBox<>();
        btnRegister = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtRegAddress = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtRegBirthdate = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelRound1.setRadius(50);

        panelRound2.setRadius(50);
        panelRound2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound2MouseDragged(evt);
            }
        });
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound2MousePressed(evt);
            }
        });

        btnClose.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnClose.setText("X");
        btnClose.setBorderPainted(false);
        btnClose.setContentAreaFilled(false);
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClose.setFocusable(false);
        btnClose.setRequestFocusEnabled(false);
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addGap(19, 19, 19))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnClose, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel1.setText("LOGIN");

        txtLoginUsername.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtLoginUsername.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel2.setText("USERNAME");

        txtLoginPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtLoginPassword.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel3.setText("PASSWORD");

        btnLogin.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnLogin.setText("ENTER");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addContainerGap(475, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(481, 481, 481))
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound3Layout.createSequentialGroup()
                        .addGap(431, 431, 431)
                        .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtLoginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtLoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelRound3Layout.createSequentialGroup()
                        .addGap(471, 471, 471)
                        .addComponent(btnLogin)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1)
                .addGap(74, 74, 74)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtLoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtLoginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnLogin)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Login", panelRound3);

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel4.setText("Firstname");

        txtRegFirstname.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel5.setText("Lastname");

        txtRegLastname.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel6.setText("ID");

        txtRegId.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel7.setText("Gender");

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel8.setText("Phone");

        txtRegPhone.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel9.setText("Email");

        txtRegEmail.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel10.setText("User");

        txtRegUsername.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        txtRegPassword.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel11.setText("Password");

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel12.setText("Password confirmation");

        txtRegConfirm.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        txtRegConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });

        cmbRegGender.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        cmbRegGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Female", "Male" }));

        btnRegister.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        btnRegister.setText("Save");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel13.setText("Address");

        txtRegAddress.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel14.setText("Birthdate");

        txtRegBirthdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(450, 450, 450)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(434, 434, 434)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRegUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRegPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(473, 473, 473)
                        .addComponent(jLabel10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(432, 432, 432)
                        .addComponent(txtRegConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(456, 456, 456)
                        .addComponent(btnRegister))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(396, 396, 396)
                        .addComponent(jLabel12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtRegBirthdate, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(34, 34, 34)
                                .addComponent(txtRegFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtRegLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(30, 30, 30)
                                .addComponent(txtRegId, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addGap(26, 26, 26)
                                .addComponent(cmbRegGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtRegAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtRegPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtRegEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtRegFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtRegLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtRegId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(cmbRegGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtRegBirthdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(txtRegAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtRegPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtRegEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(txtRegUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(txtRegPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(txtRegConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnRegister)
                .addGap(42, 42, 42))
        );

        jTabbedPane1.addTab("Patient register", jPanel3);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelRound2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_panelRound2MousePressed

    private void panelRound2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_panelRound2MouseDragged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String username = txtLoginUsername.getText().trim();
        String password = txtLoginPassword.getText().trim();

        ServiceResponse response = loginController.login(username, password);

        if (!response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Login", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.util.HashMap<String, Object> userData = (java.util.HashMap<String, Object>) response.getData();
        String userType = (String) userData.get("type");

        this.setVisible(false);
        if ("Administrator".equals(userType)) {
            AdminDashboardView admin = new AdminDashboardView(userData);
            admin.setVisible(true);
        } else if ("Doctor".equals(userType)) {
            DoctorDashboardView doctor = new DoctorDashboardView(userData);
            doctor.setVisible(true);
        } else {
            PatientDashboardView patient = new PatientDashboardView(userData);
            patient.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String genderStr = cmbRegGender.getItemAt(cmbRegGender.getSelectedIndex());
        ServiceResponse response = loginController.registerPatient(
                txtRegId.getText().trim(),
                txtRegUsername.getText().trim(),
                txtRegFirstname.getText().trim(),
                txtRegLastname.getText().trim(),
                txtRegPassword.getText().trim(),
                txtRegConfirm.getText().trim(),
                txtRegEmail.getText().trim(),
                txtRegBirthdate.getText().trim(),
                genderStr,
                txtRegPhone.getText().trim(),
                txtRegAddress.getText().trim()
        );
        if (response.isSuccess()) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Registro", JOptionPane.INFORMATION_MESSAGE);
            txtRegId.setText("");
            txtRegUsername.setText("");
            txtRegFirstname.setText("");
            txtRegLastname.setText("");
            txtRegPassword.setText("");
            txtRegConfirm.setText("");
            txtRegEmail.setText("");
            txtRegBirthdate.setText("");
            txtRegPhone.setText("");
            txtRegAddress.setText("");
            cmbRegGender.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
    }//GEN-LAST:event_jTextField10ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRegister;
    private javax.swing.JComboBox<String> cmbRegGender;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtLoginUsername;
    private javax.swing.JTextField txtRegConfirm;
    private javax.swing.JTextField txtRegAddress;
    private javax.swing.JTextField txtRegBirthdate;
    private javax.swing.JTextField txtLoginPassword;
    private javax.swing.JTextField txtRegFirstname;
    private javax.swing.JTextField txtRegLastname;
    private javax.swing.JTextField txtRegId;
    private javax.swing.JTextField txtRegPhone;
    private javax.swing.JTextField txtRegEmail;
    private javax.swing.JTextField txtRegUsername;
    private javax.swing.JTextField txtRegPassword;
    private packagee.PanelRound panelRound1;
    private packagee.PanelRound panelRound2;
    private packagee.PanelRound panelRound3;
    // End of variables declaration//GEN-END:variables
}
