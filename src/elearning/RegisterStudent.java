package elearning;

import elearningproject.Pain;
import elearningproject.Server;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterStudent extends javax.swing.JFrame {


        private InetAddress address ;
        private DatagramPacket packet;
        String name;


        public RegisterStudent() throws UnknownHostException {
            initComponents();
            this.setSize(400, 300);
            address=InetAddress.getByName("localhost");
        }


        private void initComponents() {

            jPanel1 = new javax.swing.JPanel();

            jPanel2 = new javax.swing.JPanel();
            jTextField1 = new javax.swing.JTextField();
            jLabel1 = new javax.swing.JLabel();
            jLabel2 = new javax.swing.JLabel();
            jPasswordField1 = new javax.swing.JPasswordField();

//            jSeparator2 = new javax.swing.JSeparator();
            jLabel3 = new javax.swing.JLabel();
            jButton1 = new javax.swing.JButton();
//            jLabel4 = new javax.swing.JLabel();
//            jLabel5 = new javax.swing.JLabel();
//            jTextField2 = new javax.swing.JTextField();
//            jPasswordField2 = new javax.swing.JPasswordField();
//            jButton2 = new javax.swing.JButton();
//            jLabel6 = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            jPanel2.setBackground(new java.awt.Color(204, 255, 255));

            jTextField1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jTextField1ActionPerformed(evt);
                }
            });

            jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            jLabel1.setText("Name");

            jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            jLabel2.setText("Password");

            jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jPasswordField1ActionPerformed(evt);
                }
            });



//            jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
//            jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
            jLabel3.setText("              Connexion Student");

            jButton1.setBackground(new java.awt.Color(204, 204, 204));
            jButton1.setText("Connexion");
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });

//            jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
//            jLabel4.setText("Name");

//            jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
//            jLabel5.setText("Password");
//
//            jButton2.setBackground(new java.awt.Color(204, 204, 204));
//            jButton2.setText("Connexion");
//            jButton2.addActionListener(new java.awt.event.ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent evt) {
//                    jButton2ActionPerformed(evt);
//                }
//            });

//            jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
//            jLabel6.setText("      Connexion Etudiant");

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGap(42, 42, 42)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                            .addComponent(jTextField1,javax.swing.GroupLayout.DEFAULT_SIZE, 184, 500)
                                                                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, 500)))
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                    .addGap(11, 11, 11)
                                                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGap(153, 153, 153)
                                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, 500)))
                                    .addGap(67, 67, 67)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                            .addGap(63, 63, 63)
                                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addGroup(jPanel2Layout.createSequentialGroup()
//                                                                    .addComponent(jLabel5)
                                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                                                                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
//                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
//                                                                    .addComponent(jLabel4)
                                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                            .addContainerGap(53, Short.MAX_VALUE))
                                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
//                                                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                            .addGap(96, 96, 96))
                                                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
//                                                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                            .addGap(87, 87, 87))))))
                                    )));
            jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(10, 10, 10)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
//                                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                                            .addComponent(jLabel4))
                                                                    .addGap(18, 18, 18)
                                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE))
//                                                            .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
//                                                            .addComponent(jLabel5))
//                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
//                                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
//                                    .addContainerGap(52, Short.MAX_VALUE))
                                                    )))));
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            ));
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            pack();
        }

        private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

//        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
//            // TODO add your handling code here:
//            try {
//                String name = jTextField2.getText();
//                String pass=new String(jPasswordField2.getPassword());
//                DatabaseConnection db = new DatabaseConnection();
//                String query="SELECT * FROM student";
//                ResultSet rs=db.getStatement().executeQuery(query);
//                int inscrit=0;
//                while(rs.next()){
//                    if(name.equals(rs.getString(2)) && pass.equals(rs.getString(3))){
//                        inscrit=1;
//                        break;
//                    }
//                }
//
//                if(inscrit==1){
//                    JOptionPane.showMessageDialog(null,"connexion reussie");
//
//                    String url="rmi://localhost/paint";
//                    try {
//                        Pain od=(Pain) Naming.lookup(url);
//                        od.principale(name);
//
//                    }catch (Exception e){
//                        System.out.println("Serveur Introuvable");
//                        e.printStackTrace();
//                    }
//
//
//                    this.dispose();
//                }else {
//                    JOptionPane.showMessageDialog(null,"connexion echouee");
//                }
//            }catch (SQLException e){
//                  e.printStackTrace();
//            }
//
//        }

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
            try {
                    this.name = jTextField1.getText();
                    System.out.println("name : "+name);
                    String pass = new String(jPasswordField1.getPassword());
                    DatabaseConnection db = new DatabaseConnection();
                    String query = "SELECT * FROM student";
                    ResultSet rs = db.getStatement().executeQuery(query);
                    int inscrit = 0;
                    while (rs.next()) {
                        if (name.equals(rs.getString(2)) && pass.equals(rs.getString(3))) {
                            inscrit = 1;
                            break;
                        }
                    }
                    query="SELECT * FROM cours";
                     rs = db.getStatement().executeQuery(query);
                    if (inscrit == 1) {
                        JOptionPane.showMessageDialog(null, "connexion réussie");
                        //  new Elearning(name,socket).setVisible(true);
//                    String url="rmi://localhost/paint";
//                    try {
//                        Pain od=(Pain) Naming.lookup(url);
//                        od.principale(name);
//                    }catch (Exception e){
//                        System.out.println("Serveur Introuvable");
//                        e.printStackTrace();
//                    }

                        new CourseCardList(name,rs);
                    this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "connexion échouée");
                    }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


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
                java.util.logging.Logger.getLogger(elearning.Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(elearning.Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(elearning.Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(elearning.Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new RegisterStudent().setVisible(true);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        // Variables declaration - do not modify

        private javax.swing.JButton jButton1;
        //        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        //        private javax.swing.JLabel jLabel4;
//        private javax.swing.JLabel jLabel5;
//        private javax.swing.JLabel jLabel6;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPasswordField jPasswordField1;
//        private javax.swing.JPasswordField jPasswordField2;

        //        private javax.swing.JSeparator jSeparator2;
        private javax.swing.JTextField jTextField1;
//        private javax.swing.JTextField jTextField2;
        // End of variables declaration



}
