
package system;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class landing extends javax.swing.JFrame {

    Color defaultColor = new Color(236, 240, 241);   
    Color activeColor  = new Color(255,204,204); 
    
    public landing() {
         initComponents();

    JButton[] buttons = { aboutus, fruits, contacts };

    for (JButton btn : buttons) {
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setBackground(defaultColor);
    }
    }

    private void resetMenuColors() {
    JButton[] buttons = {
        aboutus, 
        fruits,
        contacts, 
                  
       
    };

    for (JButton btn : buttons) {
        btn.setBackground(defaultColor);
    }
}
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        orange = new javax.swing.JLabel();
        white = new javax.swing.JLabel();
        basketwhite = new javax.swing.JLabel();
        basketorange = new javax.swing.JLabel();
        aboutus = new javax.swing.JButton();
        contacts = new javax.swing.JButton();
        fruits = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orange.setBackground(new java.awt.Color(255, 153, 0));
        orange.setFont(new java.awt.Font("Segoe UI", 1, 70)); // NOI18N
        orange.setForeground(new java.awt.Color(255, 153, 0));
        orange.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orange.setText("FRUIT ");
        jPanel2.add(orange, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 230, 70));

        white.setFont(new java.awt.Font("Segoe UI", 1, 70)); // NOI18N
        white.setForeground(new java.awt.Color(255, 255, 255));
        white.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        white.setText("FRUIT ");
        jPanel2.add(white, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 230, 70));

        basketwhite.setFont(new java.awt.Font("Segoe UI", 1, 70)); // NOI18N
        basketwhite.setForeground(new java.awt.Color(255, 255, 255));
        basketwhite.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        basketwhite.setText("BASKET");
        jPanel2.add(basketwhite, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 270, 70));

        basketorange.setBackground(new java.awt.Color(255, 153, 0));
        basketorange.setFont(new java.awt.Font("Segoe UI", 1, 70)); // NOI18N
        basketorange.setForeground(new java.awt.Color(255, 153, 0));
        basketorange.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        basketorange.setText("BASKET");
        jPanel2.add(basketorange, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 270, 70));

        aboutus.setBackground(new java.awt.Color(255, 255, 255));
        aboutus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        aboutus.setText("About us");
        aboutus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aboutusMouseClicked(evt);
            }
        });
        aboutus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutusActionPerformed(evt);
            }
        });
        jPanel2.add(aboutus, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 120, 30));

        contacts.setBackground(new java.awt.Color(255, 255, 255));
        contacts.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        contacts.setText("Contacts");
        contacts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                contactsMouseClicked(evt);
            }
        });
        contacts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactsActionPerformed(evt);
            }
        });
        jPanel2.add(contacts, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 30, 120, 30));

        fruits.setBackground(new java.awt.Color(255, 255, 255));
        fruits.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fruits.setText("Fruits");
        fruits.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fruitsMouseClicked(evt);
            }
        });
        fruits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fruitsActionPerformed(evt);
            }
        });
        jPanel2.add(fruits, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 30, 120, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1050, 90));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 153, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton1.setText("EXPLORE NOW");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 220, 130, 50));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("“Nature paints with fruits—soft reds, warm yellows,");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 160, 440, 40));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("and calming greens—creating beauty we can taste.”");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, 480, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/strawberry.png"))); // NOI18N
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 410));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1050, 410));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        login lf = new login();
        lf.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void aboutusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutusMouseClicked
        resetMenuColors();
        aboutus.setBackground(activeColor);
    }//GEN-LAST:event_aboutusMouseClicked

    private void aboutusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aboutusActionPerformed

    private void fruitsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fruitsMouseClicked
       resetMenuColors();
       fruits.setBackground(activeColor);
    }//GEN-LAST:event_fruitsMouseClicked

    private void fruitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fruitsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fruitsActionPerformed

    private void contactsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contactsMouseClicked
        resetMenuColors();
        contacts.setBackground(activeColor);
    }//GEN-LAST:event_contactsMouseClicked

    private void contactsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactsActionPerformed

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
            java.util.logging.Logger.getLogger(landing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(landing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(landing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(landing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new landing().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutus;
    private javax.swing.JLabel basketorange;
    private javax.swing.JLabel basketwhite;
    private javax.swing.JButton contacts;
    private javax.swing.JButton fruits;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel orange;
    private javax.swing.JLabel white;
    // End of variables declaration//GEN-END:variables
}
