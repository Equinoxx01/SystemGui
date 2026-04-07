/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import config.config;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import system.Session;
import system.login;

/**
 * Lists the logged-in user's orders and line items; print opens the same receipt as Reports.
 */
public class orders extends javax.swing.JFrame {

    public int userId;
    Color defaultColor = new Color(236, 240, 241);
    Color activeColor = new Color(255, 204, 204);

    private JScrollPane orderLinesScroll;
    private JTable orderLinesTable;

    /**
     * Creates new form orders
     */
    public orders() {
        this(0);
    }

    public orders(int userId) {
        initComponents();
        this.userId = userId;
        setTitle("My Orders");
        setupExtendedUi();
        ensureOrderTables();
        refreshOrdersTable();

        JButton[] buttons = { dashboard, products, cart, orders1, profile, logout };
        for (JButton btn : buttons) {
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(false);
            btn.setBackground(defaultColor);
        }
        orders1.setBackground(activeColor);

        orders.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    loadLineItemsForSelection();
                }
            }
        });
    }

    private void setupExtendedUi() {
        JLabel hdr = new JLabel("My orders");
        hdr.setFont(new Font("Tahoma", Font.BOLD, 18));
        jPanel1.add(hdr, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 55, 220, 26));
        JLabel itemsLbl = new JLabel("Fruits in selected order");
        itemsLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
        jPanel1.add(itemsLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 222, 400, 22));

        jPanel1.remove(jScrollPane1);
        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 85, 630, 125));
        orderLinesTable = new JTable();
        orderLinesScroll = new JScrollPane(orderLinesTable);
        jPanel1.add(orderLinesScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 248, 630, 195));
        jPanel1.remove(print);
        jPanel1.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 455, 110, 33));
    }

    private void ensureOrderTables() {
        try (Connection conn = config.connectDB(); Statement st = conn != null ? conn.createStatement() : null) {
            if (st != null) {
                st.executeUpdate("CREATE TABLE IF NOT EXISTS tbl_orders (order_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, total REAL, cash REAL, change_amt REAL, order_date TEXT)");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS tbl_order_items (id INTEGER PRIMARY KEY AUTOINCREMENT, order_id INTEGER, product_id INTEGER, product_name TEXT, qty INTEGER, price REAL, subtotal REAL)");
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    private void refreshOrdersTable() {
        if (userId <= 0) {
            orders.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Order ID", "Total", "Cash", "Change", "Date" }));
            return;
        }
        String sql = "SELECT o.order_id AS 'Order ID', o.total AS 'Total', o.cash AS 'Cash', o.change_amt AS 'Change', o.order_date AS 'Date' "
                + "FROM tbl_orders o WHERE o.user_id = " + userId + " ORDER BY o.order_id DESC";
        config con = new config();
        try {
            con.displayData(sql, orders);
        } catch (Exception e) {
            orders.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Order ID", "Total", "Cash", "Change", "Date" }));
        }
    }

    private void loadLineItemsForSelection() {
        int row = orders.getSelectedRow();
        if (row < 0) {
            orderLinesTable.setModel(new DefaultTableModel(new String[] { "Product", "Qty", "Price", "Subtotal" }, 0));
            return;
        }
        Object idObj = orders.getValueAt(row, 0);
        int orderId;
        if (idObj instanceof Number) {
            orderId = ((Number) idObj).intValue();
        } else {
            try {
                orderId = Integer.parseInt(String.valueOf(idObj).trim());
            } catch (NumberFormatException ex) {
                return;
            }
        }
        DefaultTableModel m = new DefaultTableModel(new String[] { "Product", "Qty", "Price", "Subtotal" }, 0);
        try (Connection conn = config.connectDB()) {
            if (conn == null) {
                return;
            }
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT product_name, qty, price, subtotal FROM tbl_order_items WHERE order_id = ? ORDER BY id")) {
                ps.setInt(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        m.addRow(new Object[] {
                                rs.getString("product_name"),
                                rs.getInt("qty"),
                                rs.getDouble("price"),
                                rs.getDouble("subtotal")
                        });
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Could not load items: " + ex.getMessage());
        }
        orderLinesTable.setModel(m);
    }

    private void resetMenuColors() {
        JButton[] buttons = { dashboard, products, cart, orders1, profile, logout };
        for (JButton btn : buttons) {
            btn.setBackground(defaultColor);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this method. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dashboard = new javax.swing.JButton();
        products = new javax.swing.JButton();
        profile = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        cart = new javax.swing.JButton();
        orders1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        orders = new javax.swing.JTable();
        print = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ORDERS DASHBOARD");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 260, 50));

        dashboard.setBackground(new java.awt.Color(255, 255, 255));
        dashboard.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        dashboard.setText("Dashboard");
        dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardMouseClicked(evt);
            }
        });
        dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardActionPerformed(evt);
            }
        });
        jPanel2.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 150, 30));

        products.setBackground(new java.awt.Color(255, 255, 255));
        products.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        products.setText("Products");
        products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productsMouseClicked(evt);
            }
        });
        products.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productsActionPerformed(evt);
            }
        });
        jPanel2.add(products, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 150, 30));

        profile.setBackground(new java.awt.Color(255, 255, 255));
        profile.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        profile.setText("Profile");
        profile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileMouseClicked(evt);
            }
        });
        profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileActionPerformed(evt);
            }
        });
        jPanel2.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 150, 30));

        logout.setBackground(new java.awt.Color(255, 51, 51));
        logout.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 0, 0));
        logout.setText("LOG OUT");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jPanel2.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 150, 30));

        cart.setBackground(new java.awt.Color(255, 255, 255));
        cart.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cart.setText("Cart");
        cart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cartMouseClicked(evt);
            }
        });
        cart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cartActionPerformed(evt);
            }
        });
        jPanel2.add(cart, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 150, 30));

        orders1.setBackground(new java.awt.Color(255, 255, 255));
        orders1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        orders1.setText("Orders");
        orders1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orders1MouseClicked(evt);
            }
        });
        orders1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orders1ActionPerformed(evt);
            }
        });
        jPanel2.add(orders1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 150, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 500));

        orders.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(orders);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, 630, 90));

        print.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        print.setText("PRINT");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jPanel1.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 250, 110, 33));

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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMouseClicked
        resetMenuColors();
        dashboard.setBackground(activeColor);
    }//GEN-LAST:event_dashboardMouseClicked

    private void dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardActionPerformed
        new usersdashboard(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_dashboardActionPerformed

    private void productsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productsMouseClicked
        new products(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_productsMouseClicked

    private void productsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productsActionPerformed

    private void profileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileMouseClicked
        new userprofile(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_profileMouseClicked

    private void profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileActionPerformed
        new userprofile(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_profileActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            system.Session.clearSession();
            login lg = new login();
            lg.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_logoutActionPerformed

    private void cartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartMouseClicked
        new transaction(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cartMouseClicked

    private void cartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cartActionPerformed

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        int row = orders.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an order in the table first.", "Print receipt",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Object idObj = orders.getValueAt(row, 0);
        int orderId;
        if (idObj instanceof Number) {
            orderId = ((Number) idObj).intValue();
        } else {
            try {
                orderId = Integer.parseInt(String.valueOf(idObj).trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Could not read the order ID for this row.");
                return;
            }
        }
        if (orderId <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid order.");
            return;
        }
        new receipt(this, orderId, true).setVisible(true);
    }//GEN-LAST:event_printActionPerformed

    private void orders1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orders1MouseClicked
        resetMenuColors();
        orders1.setBackground(activeColor);
    }//GEN-LAST:event_orders1MouseClicked

    private void orders1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orders1ActionPerformed
        // already on orders
    }//GEN-LAST:event_orders1ActionPerformed

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
            java.util.logging.Logger.getLogger(orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (!Session.isLoggedIn()) {
                    JOptionPane.showMessageDialog(null, "You must log in first.", "Login Required", JOptionPane.WARNING_MESSAGE);
                    new login().setVisible(true);
                    return;
                }
                new orders(Session.getUserId()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cart;
    private javax.swing.JButton dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logout;
    private javax.swing.JTable orders;
    private javax.swing.JButton orders1;
    private javax.swing.JButton print;
    private javax.swing.JButton products;
    private javax.swing.JButton profile;
    // End of variables declaration//GEN-END:variables
}
