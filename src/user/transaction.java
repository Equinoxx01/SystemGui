/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import config.config;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import system.Session;
import system.login;

/**
 *
 * @author USER19
 */
public class transaction extends javax.swing.JFrame {

    public int userId;
    Color defaultColor = new Color(236, 240, 241);
    Color activeColor = new Color(255, 204, 204);

    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JLabel lblCash;
    private javax.swing.JTextField txtCash;
    private javax.swing.JLabel lblChange;
    private javax.swing.JTextField txtChange;

    private static final String[] CART_COLUMNS = { "Product ID", "Product Name", "Quantity", "Price", "Subtotal" };

    /**
     * Creates new form transaction
     */
    public transaction() {
        this(0);
    }

    public transaction(int userId) {
        initComponents();
        this.userId = userId;
        setupCartModel();
        ensureTables();
        displayAvailableProducts();
        setupCheckoutFields();
        JButton[] buttons = { dashboard, products, orders, cart, logout };
        for (JButton btn : buttons) {
            btn.setOpaque(true);
            btn.setContentAreaFilled(true);
            btn.setBorderPainted(false);
            btn.setBackground(defaultColor);
        }
    }

    private void resetMenuColors() {
        JButton[] buttons = { dashboard, products, orders, cart, logout };
        for (JButton btn : buttons) {
            btn.setBackground(defaultColor);
        }
    }

    private void setupCartModel() {
        carts.setModel(new DefaultTableModel(new Object[][] {}, CART_COLUMNS));
    }

    private void setupCheckoutFields() {
        lblTotal = new JLabel("Total:");
        txtTotal = new JTextField(12);
        txtTotal.setEditable(false);
        lblCash = new JLabel("Cash:");
        txtCash = new JTextField(12);
        lblChange = new JLabel("Change:");
        txtChange = new JTextField(12);
        txtChange.setEditable(false);
        jPanel3.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, 50, 25));
        jPanel3.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, 120, 30));
        jPanel3.add(lblCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 335, 50, 25));
        jPanel3.add(txtCash, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 335, 120, 30));
        jPanel3.add(lblChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 60, 25));
        jPanel3.add(txtChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 370, 120, 30));
        updateTotalDisplay();
    }

    private void ensureTables() {
        config.seedDefaultProducts();
        try (Connection conn = config.connectDB(); Statement st = conn != null ? conn.createStatement() : null) {
            if (st != null) {
                st.executeUpdate("CREATE TABLE IF NOT EXISTS tbl_orders (order_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, total REAL, cash REAL, change_amt REAL, order_date TEXT)");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS tbl_order_items (id INTEGER PRIMARY KEY AUTOINCREMENT, order_id INTEGER, product_id INTEGER, product_name TEXT, qty INTEGER, price REAL, subtotal REAL)");
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    private void displayAvailableProducts() {
        String sql = "SELECT p_id AS 'ID', product_name AS 'Product', price AS 'Price', quantity AS 'Qty', status AS 'Status' FROM tbl_products WHERE (quantity IS NULL OR quantity > 0) ORDER BY p_id";
        config con = new config();
        con.displayData(sql, product);
    }

    private double getCartTotal() {
        DefaultTableModel m = (DefaultTableModel) carts.getModel();
        double total = 0;
        for (int i = 0; i < m.getRowCount(); i++) {
            Object v = m.getValueAt(i, 4);
            if (v != null) {
                try {
                    total += Double.parseDouble(v.toString());
                } catch (NumberFormatException ignored) {}
            }
        }
        return total;
    }

    private void updateTotalDisplay() {
        double t = getCartTotal();
        txtTotal.setText(String.format("%.2f", t));
    }

    private int getAvailableQuantity(int productId) {
        try (Connection conn = config.connectDB();
             PreparedStatement ps = conn != null ? conn.prepareStatement("SELECT quantity FROM tbl_products WHERE p_id = ?") : null) {
            if (ps == null) return 0;
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("quantity") : 0;
            }
        } catch (SQLException e) {
            return 0;
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dashboard = new javax.swing.JButton();
        products = new javax.swing.JButton();
        orders = new javax.swing.JButton();
        cart = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        userprofile = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        Quantity = new javax.swing.JLabel();
        txtquantity = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        product = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        carts = new javax.swing.JTable();
        add = new javax.swing.JButton();
        checkout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TRANSACTION/ORDERS");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 300, 50));

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

        orders.setBackground(new java.awt.Color(255, 255, 255));
        orders.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        orders.setText("Orders");
        orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ordersMouseClicked(evt);
            }
        });
        orders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordersActionPerformed(evt);
            }
        });
        jPanel2.add(orders, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 150, 30));

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
        jPanel2.add(cart, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 150, 30));

        logout.setBackground(new java.awt.Color(255, 51, 51));
        logout.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 0, 0));
        logout.setText("LOG OUT");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jPanel2.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 440, 150, 30));

        userprofile.setBackground(new java.awt.Color(255, 255, 255));
        userprofile.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        userprofile.setText("Profile");
        userprofile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userprofileMouseClicked(evt);
            }
        });
        userprofile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userprofileActionPerformed(evt);
            }
        });
        jPanel2.add(userprofile, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 150, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 500));

        jPanel3.setBackground(new java.awt.Color(255, 153, 153));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Quantity.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        Quantity.setText("Quantity:");
        jPanel3.add(Quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        txtquantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtquantityActionPerformed(evt);
            }
        });
        jPanel3.add(txtquantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 120, 30));

        product.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(product);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 90));

        carts.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(carts);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 630, 90));

        add.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        add.setText("ADD TO CART");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        jPanel3.add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 140, 33));

        checkout.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        checkout.setText("CHECKOUT");
        checkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutActionPerformed(evt);
            }
        });
        jPanel3.add(checkout, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 140, 33));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 630, 500));

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
        new usersdashboard(this.userId).setVisible(true);
        this.dispose();
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
        new products(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_productsActionPerformed

    private void ordersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ordersMouseClicked
        resetMenuColors();
        orders.setBackground(activeColor);
    }//GEN-LAST:event_ordersMouseClicked

    private void ordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordersActionPerformed
        // Stay on transaction/orders screen
    }//GEN-LAST:event_ordersActionPerformed

    private void cartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cartMouseClicked
        new transaction(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_cartMouseClicked

    private void cartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cartActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to log out?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            Session.clearSession();
            login lg = new login();
            lg.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_logoutActionPerformed

    private void userprofileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userprofileMouseClicked
        new userprofile(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userprofileMouseClicked

    private void userprofileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userprofileActionPerformed
        new userprofile(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_userprofileActionPerformed

    private void txtquantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtquantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtquantityActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        int row = product.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product from the table.");
            return;
        }
        Object idObj = product.getValueAt(row, 0);
        Object nameObj = product.getValueAt(row, 1);
        Object priceObj = product.getValueAt(row, 2);
        Object qtyAvailObj = product.getValueAt(row, 3);
        if (idObj == null || nameObj == null || priceObj == null) {
            JOptionPane.showMessageDialog(this, "Invalid product selection.");
            return;
        }
        int productId;
        double price;
        int availableQty = 999;
        try {
            productId = idObj instanceof Number ? ((Number) idObj).intValue() : Integer.parseInt(idObj.toString());
            price = priceObj instanceof Number ? ((Number) priceObj).doubleValue() : Double.parseDouble(priceObj.toString());
            if (qtyAvailObj != null && !qtyAvailObj.toString().isEmpty())
                availableQty = qtyAvailObj instanceof Number ? ((Number) qtyAvailObj).intValue() : Integer.parseInt(qtyAvailObj.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid product data.");
            return;
        }
        int orderQty;
        String qtyStr = txtquantity.getText().trim();
        if (qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter quantity.");
            return;
        }
        try {
            orderQty = Integer.parseInt(qtyStr);
            if (orderQty <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a number.");
            return;
        }
        int stock = getAvailableQuantity(productId);
        if (orderQty > stock) {
            JOptionPane.showMessageDialog(this, "Insufficient stock. Available: " + stock);
            return;
        }
        String name = nameObj.toString();
        double subtotal = price * orderQty;
        DefaultTableModel cartModel = (DefaultTableModel) carts.getModel();
        cartModel.addRow(new Object[] { productId, name, orderQty, price, subtotal });
        txtquantity.setText("");
        updateTotalDisplay();
    }//GEN-LAST:event_addActionPerformed

    private void checkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkoutActionPerformed
        double total = getCartTotal();
        if (total <= 0) {
            JOptionPane.showMessageDialog(this, "Cart is empty. Add products first.");
            return;
        }
        String cashStr = txtCash.getText().trim();
        if (cashStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter cash amount.");
            return;
        }
        double cash;
        try {
            cash = Double.parseDouble(cashStr);
            if (cash < 0) {
                JOptionPane.showMessageDialog(this, "Cash must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid cash amount.");
            return;
        }
        if (cash < total) {
            JOptionPane.showMessageDialog(this, "Insufficient cash. Total: " + String.format("%.2f", total) + " | Cash given: " + String.format("%.2f", cash));
            return;
        }
        double change = cash - total;
        txtChange.setText(String.format("%.2f", change));
        int choice = JOptionPane.showConfirmDialog(this, "Total: " + String.format("%.2f", total) + "\nCash: " + String.format("%.2f", cash) + "\nChange: " + String.format("%.2f", change) + "\n\nConfirm and complete order?", "Confirm Checkout", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) return;
        try (Connection conn = config.connectDB()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed.");
                return;
            }
            String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            try (PreparedStatement psOrder = conn.prepareStatement("INSERT INTO tbl_orders (user_id, total, cash, change_amt, order_date) VALUES (?,?,?,?,?)")) {
                psOrder.setInt(1, userId);
                psOrder.setDouble(2, total);
                psOrder.setDouble(3, cash);
                psOrder.setDouble(4, change);
                psOrder.setString(5, date);
                psOrder.executeUpdate();
            }
            try (PreparedStatement getOrderId = conn.prepareStatement("SELECT last_insert_rowid()");
                 ResultSet rs = getOrderId.executeQuery()) {
                int orderId = rs.next() ? rs.getInt(1) : 0;
                if (orderId > 0) {
                    DefaultTableModel cartModel = (DefaultTableModel) carts.getModel();
                    try (PreparedStatement psItem = conn.prepareStatement("INSERT INTO tbl_order_items (order_id, product_id, product_name, qty, price, subtotal) VALUES (?,?,?,?,?,?)")) {
                        for (int i = 0; i < cartModel.getRowCount(); i++) {
                            int pid = ((Number) cartModel.getValueAt(i, 0)).intValue();
                            String pname = cartModel.getValueAt(i, 1).toString();
                            int qty = ((Number) cartModel.getValueAt(i, 2)).intValue();
                            double pr = ((Number) cartModel.getValueAt(i, 3)).doubleValue();
                            double sub = ((Number) cartModel.getValueAt(i, 4)).doubleValue();
                            psItem.setInt(1, orderId);
                            psItem.setInt(2, pid);
                            psItem.setString(3, pname);
                            psItem.setInt(4, qty);
                            psItem.setDouble(5, pr);
                            psItem.setDouble(6, sub);
                            psItem.executeUpdate();
                            try (PreparedStatement upd = conn.prepareStatement("UPDATE tbl_products SET quantity = COALESCE(quantity,0) - ? WHERE p_id = ?")) {
                                upd.setInt(1, qty);
                                upd.setInt(2, pid);
                                upd.executeUpdate();
                            }
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Order completed. Change: " + String.format("%.2f", change));
            setupCartModel();
            updateTotalDisplay();
            txtCash.setText("");
            txtChange.setText("");
            displayAvailableProducts();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving order: " + e.getMessage());
        }
    }//GEN-LAST:event_checkoutActionPerformed

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
            java.util.logging.Logger.getLogger(transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new transaction(Session.getUserId()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Quantity;
    private javax.swing.JButton add;
    private javax.swing.JButton cart;
    private javax.swing.JTable carts;
    private javax.swing.JButton checkout;
    private javax.swing.JButton dashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logout;
    private javax.swing.JButton orders;
    private javax.swing.JTable product;
    private javax.swing.JButton products;
    private javax.swing.JTextField txtquantity;
    private javax.swing.JButton userprofile;
    // End of variables declaration//GEN-END:variables
}
