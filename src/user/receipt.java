/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import system.Session;
import system.login;

/**
 *
 * @author k
 */
public class receipt extends javax.swing.JFrame {

    /** Characters wide for rules in printed/plain receipt (approx. full ticket). */
    private static final int RECEIPT_LINE_WIDTH = 56;

    /**
     * Plain-text / print columns (monospace).
     */
    private static final int COL_ITEM = 18;
    private static final int COL_QTY = 12;
    private static final int COL_AMT = 11;

    private static final Font RECEIPT_MONO_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    /** White ticket layout (must match receipt.form AbsoluteLayout). */
    private static final int PANEL_ITEM_X = 20;
    private static final int PANEL_QTY_COLUMN_CENTER = 175;
    /**
     * Left edge of the visible “Total” header ({@code jLabel11} on receipt.form). Center is computed with
     * the same font so line amounts sit under the word.
     */
    private static final int PANEL_TOTAL_HEADER_LEFT_X = 290;
    private static final Font PANEL_TOTAL_HEADER_FONT = new Font("Tahoma", Font.PLAIN, 14);
    /** Right edge of ticket content (white panel width). */
    private static final int PANEL_AMOUNT_RIGHT = 370;
    /** Summary amount column width; placed so its center matches the Total header center. */
    private static final int PANEL_SUMMARY_VALUE_W = 90;

    public int userId;
    private String plainReceiptForPrint = "";

    /**
     * Creates new form receipt (designer / preview only).
     */
    public receipt() {
        initComponents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        styleSeparatorLabels(370);
        if (Session.isLoggedIn()) {
            this.userId = Session.getUserId();
        }
    }

    /**
     * Receipt after checkout — fills labels from the order and cart.
     */
    public receipt(java.awt.Window owner, int userId, int orderId, String customerName,
            DefaultTableModel cartModel, double orderTotal, double cashPaid, double changeAmt) {
        initComponents();
        if (!Session.isLoggedIn()) {
            JOptionPane.showMessageDialog(owner, "Your session has expired. Please log in again.",
                    "Login Required", JOptionPane.WARNING_MESSAGE);
            dispose();
            new login().setVisible(true);
            return;
        }
        this.userId = Session.getUserId();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBackToDashboard();
            }
        });
        plainReceiptForPrint = buildPlainReceipt(orderId, customerName, cartModel, orderTotal, cashPaid, changeAmt);
        applyOrderDataToForm(orderId, customerName, cartModel, orderTotal, cashPaid, changeAmt);
        pack();
        if (owner != null) {
            setSize(owner.getSize());
            setLocation(owner.getLocation());
        }
    }

    /**
     * Keeps the NetBeans receipt layout (pink frame, white ticket, separators, column headers)
     * and fills order fields; line items use monospace columns between the dashed rules.
     */
    private void applyOrderDataToForm(int orderId, String customerName, DefaultTableModel cartModel,
            double orderTotal, double cashPaid, double changeAmt) {
        final int ticketW = 370;
        styleSeparatorLabels(ticketW);

        Font titleFont = name.getFont();
        name.setFont(titleFont != null ? titleFont : new Font("Tahoma", Font.PLAIN, 24));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel3.remove(name);
        jPanel3.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, ticketW, 40));

        Font valueFont = new Font("Tahoma", Font.PLAIN, 14);
        orderno.setFont(valueFont);
        orderno.setText(String.format("%05d", orderId));
        jPanel3.remove(orderno);
        jPanel3.add(orderno, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 86, 255, 24));

        customer.setFont(valueFont);
        customer.setText(customerName != null ? customerName : "");
        jPanel3.remove(customer);
        jPanel3.add(customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 116, 255, 24));

        item.setVisible(false);
        quantity.setVisible(false);
        total.setVisible(false);

        JTextArea lines = new JTextArea();
        lines.setEditable(false);
        lines.setOpaque(false);
        lines.setFont(RECEIPT_MONO_FONT);
        lines.setBorder(null);
        lines.setLineWrap(false);
        FontMetrics fmMono = fontMetricsFor(RECEIPT_MONO_FONT);
        FontMetrics fmTotalHeader = fontMetricsFor(PANEL_TOTAL_HEADER_FONT);
        final int lineX = PANEL_ITEM_X;
        final int qtyCenterInText = PANEL_QTY_COLUMN_CENTER - lineX;
        final int totalHeaderCenterX = PANEL_TOTAL_HEADER_LEFT_X + fmTotalHeader.stringWidth("Total") / 2;
        final int amtCenterInText = totalHeaderCenterX - lineX;
        final int summaryValueX = totalHeaderCenterX - PANEL_SUMMARY_VALUE_W / 2;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            Object nameObj = cartModel.getValueAt(i, 1);
            Object qtyObj = cartModel.getValueAt(i, 2);
            Object subObj = cartModel.getValueAt(i, 4);
            String n = nameObj != null ? nameObj.toString() : "";
            int q = qtyObj instanceof Number ? ((Number) qtyObj).intValue() : 0;
            double lineTot = subObj instanceof Number ? ((Number) subObj).doubleValue() : 0;
            sb.append(formatLineItemRowPixels(fmMono, n, q, lineTot, qtyCenterInText, amtCenterInText));
            if (i < cartModel.getRowCount() - 1) {
                sb.append('\n');
            }
        }
        lines.setText(sb.toString());

        JScrollPane lineScroll = new JScrollPane(lines);
        lineScroll.setBorder(null);
        lineScroll.setOpaque(false);
        lineScroll.getViewport().setOpaque(false);
        lineScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        lineScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        int rowCount = Math.max(1, cartModel.getRowCount());
        int lineAreaH = Math.min(46, 16 + rowCount * 15);
        final int lineW = PANEL_AMOUNT_RIGHT - lineX + 6;
        jPanel3.add(lineScroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(lineX, 188, lineW, lineAreaH));

        Font amtFont = RECEIPT_MONO_FONT;
        total1.setFont(amtFont);
        total1.setHorizontalAlignment(SwingConstants.CENTER);
        total1.setText(String.format(Locale.US, "%.2f", orderTotal));
        jPanel3.remove(total1);
        jPanel3.add(total1, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                summaryValueX, 250, PANEL_SUMMARY_VALUE_W, 20));

        cash.setFont(amtFont);
        cash.setHorizontalAlignment(SwingConstants.CENTER);
        cash.setText(String.format(Locale.US, "%.2f", cashPaid));
        jPanel3.remove(cash);
        jPanel3.add(cash, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                summaryValueX, 270, PANEL_SUMMARY_VALUE_W, 20));

        change.setFont(amtFont);
        change.setHorizontalAlignment(SwingConstants.CENTER);
        change.setText(String.format(Locale.US, "%.2f", changeAmt));
        jPanel3.remove(change);
        jPanel3.add(change, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                summaryValueX, 290, PANEL_SUMMARY_VALUE_W, 20));

        javax.swing.JLabel thanks = new javax.swing.JLabel("Thank you for buying!", SwingConstants.CENTER);
        thanks.setFont(new Font("Tahoma", Font.PLAIN, 12));
        jPanel3.add(thanks, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 314, ticketW, 22));
    }

    private static FontMetrics fontMetricsFor(Font f) {
        BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = bi.createGraphics();
        try {
            return g.getFontMetrics(f);
        } finally {
            g.dispose();
        }
    }

    /** Full-width {@code =} / {@code -} rules using this label’s font metrics. */
    private void styleSeparatorLabels(int panelWidth) {
        Font eqFont = jLabel5.getFont();
        if (eqFont == null) {
            eqFont = new Font("Tahoma", Font.PLAIN, 11);
        }
        FontMetrics fm = fontMetricsFor(eqFont);
        int eqW = Math.max(1, fm.charWidth('='));
        int dashW = Math.max(1, fm.charWidth('-'));
        int eqCount = Math.min(46, Math.max(38, (panelWidth - 16) / eqW));
        int dashCountFull = Math.min(48, Math.max(36, (panelWidth - 20) / dashW));
        int dashCountShort = Math.min(36, Math.max(26, (panelWidth * 5 / 12) / dashW));

        String eqLine = repeatChar('=', eqCount);
        String dashFull = repeatChar('-', dashCountFull);
        String dashShort = repeatChar('-', dashCountShort);

        javax.swing.JLabel[] eqLabels = { jLabel5, jLabel6, jLabel7 };
        for (javax.swing.JLabel jl : eqLabels) {
            jl.setFont(eqFont);
            jl.setText(eqLine);
            jl.setHorizontalAlignment(SwingConstants.CENTER);
            jl.setVerticalAlignment(SwingConstants.CENTER);
        }
        jPanel3.remove(jLabel5);
        jPanel3.remove(jLabel6);
        jPanel3.remove(jLabel7);
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, panelWidth, 16));
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, panelWidth, 16));
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, panelWidth, 16));

        jLabel8.setFont(eqFont);
        jLabel8.setText(dashShort);
        jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel3.remove(jLabel8);
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, panelWidth, 14));

        jLabel9.setFont(eqFont);
        jLabel9.setText(dashFull);
        jLabel9.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel3.remove(jLabel9);
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 176, panelWidth, 14));
    }

    /**
     * Places quantity centered on {@code qtyCenterPx} and line total centered on {@code amtCenterPx}
     * (coordinates relative to the line’s JTextArea, left edge = {@link #PANEL_ITEM_X} on ticket).
     */
    private static String formatLineItemRowPixels(FontMetrics fm, String rawItem, int qty, double lineTotal,
            int qtyCenterPx, int amtCenterPx) {
        String item = rawItem == null ? "" : rawItem;
        String qtyStr = String.valueOf(qty);
        String amt = String.format(Locale.US, "%.2f", lineTotal);
        int qtyW = fm.stringWidth(qtyStr);
        int amtW = fm.stringWidth(amt);
        int xQtyStart = qtyCenterPx - qtyW / 2;
        int xAmtStart = amtCenterPx - amtW / 2;
        int gapBeforeQty = 8;
        int maxItemPx = xQtyStart - gapBeforeQty;
        if (maxItemPx < 12) {
            maxItemPx = 12;
        }
        while (item.length() > 1 && fm.stringWidth(item + "..") > maxItemPx) {
            item = item.substring(0, item.length() - 1);
        }
        if (fm.stringWidth(item) > maxItemPx && item.length() > 2) {
            item = item.substring(0, Math.max(1, item.length() - 2)) + "..";
        }
        StringBuilder line = new StringBuilder(item);
        while (fm.stringWidth(line.toString()) < xQtyStart) {
            line.append(' ');
        }
        line.append(qtyStr);
        while (fm.stringWidth(line.toString()) < xAmtStart) {
            line.append(' ');
        }
        line.append(amt);
        return line.toString();
    }

    /**
     * Printed / plain-text row: fixed {@code width} chars; amount is centered in the last {@link #COL_AMT}
     * columns (under “Total”), matching the on-screen ticket.
     */
    private static String formatLineItemRow(String itemLeft, int qty, double lineTotal, int width) {
        String left = String.format(Locale.US, "%-" + COL_ITEM + "s  %s",
                itemLeft, centerInField(String.valueOf(qty), COL_QTY));
        String amt = centerInField(String.format(Locale.US, "%.2f", lineTotal), COL_AMT);
        int gap = width - left.length() - COL_AMT;
        if (gap < 1) {
            left = String.format(Locale.US, "%-" + COL_ITEM + "s  %s",
                    truncPlain(itemLeft, Math.max(4, COL_ITEM - 3)),
                    centerInField(String.valueOf(qty), COL_QTY));
            gap = width - left.length() - COL_AMT;
        }
        if (gap < 1) {
            gap = 1;
        }
        return left + repeatChar(' ', gap) + amt + '\n';
    }

    private static String formatReceiptHeaderLine(int width) {
        String left = String.format(Locale.US, "%-" + COL_ITEM + "s  %s",
                "Item", centerInField("Quantity", COL_QTY));
        String colTotal = centerInField("Total", COL_AMT);
        int gap = width - left.length() - COL_AMT;
        if (gap < 1) {
            gap = 1;
        }
        return left + repeatChar(' ', gap) + colTotal + '\n';
    }

    /** Center {@code text} in a fixed monospace width (pads with spaces). */
    private static String centerInField(String text, int fieldWidth) {
        if (text == null) {
            text = "";
        }
        if (text.length() > fieldWidth) {
            return text.substring(0, fieldWidth);
        }
        int pad = fieldWidth - text.length();
        int left = pad / 2;
        int right = pad - left;
        return repeatChar(' ', left) + text + repeatChar(' ', right);
    }

    private static String formatSummaryLine(String label, double amount, int width) {
        String amtCol = centerInField(String.format(Locale.US, "%.2f", amount), COL_AMT);
        int labelMax = Math.max(1, width - COL_AMT - 1);
        String lab = label.length() > labelMax ? label.substring(0, labelMax) : label;
        int gap = width - lab.length() - COL_AMT;
        if (gap < 1) {
            gap = 1;
        }
        return lab + repeatChar(' ', gap) + amtCol + '\n';
    }

    private void goBackToDashboard() {
        int id = Session.isLoggedIn() ? Session.getUserId() : userId;
        new usersdashboard(id).setVisible(true);
        dispose();
    }

    private void printReceipt() {
        if (plainReceiptForPrint == null || plainReceiptForPrint.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to print on this receipt.");
            return;
        }
        JTextArea ta = new JTextArea(plainReceiptForPrint);
        ta.setFont(RECEIPT_MONO_FONT);
        ta.setMargin(new java.awt.Insets(16, 16, 16, 16));
        try {
            boolean ok = ta.print(null, null, true, null, null, true);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Printing was cancelled.");
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Could not print: " + ex.getMessage());
        }
    }

    private static String truncPlain(String s, int max) {
        if (s == null) {
            return "";
        }
        if (s.length() <= max) {
            return s;
        }
        return s.substring(0, max - 2) + "..";
    }

    private static String repeatChar(char c, int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static String centerLine(String s, int width) {
        if (s.length() >= width) {
            return s;
        }
        int pad = Math.max(0, (width - s.length()) / 2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pad; i++) {
            sb.append(' ');
        }
        sb.append(s);
        return sb.toString();
    }

    private static String buildPlainReceipt(int orderId, String customer, DefaultTableModel cartModel,
            double orderTotal, double cashPaid, double changeAmt) {
        int W = RECEIPT_LINE_WIDTH;
        String eq = repeatChar('=', W);
        String dashRow = repeatChar('-', W);
        String dashTitle = repeatChar('-', Math.max(28, W * 2 / 3));
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append(centerLine("FRUITBASKET", W)).append('\n');
        sb.append(centerLine(dashTitle, W)).append('\n');
        sb.append(eq).append('\n');
        sb.append(String.format(Locale.US, "%-14s%s%n", "ORDER NO :", String.format("%05d", orderId)));
        String cust = customer != null ? customer : "";
        if (cust.length() > W - 14) {
            cust = cust.substring(0, W - 16) + "..";
        }
        sb.append(String.format(Locale.US, "%-14s%s%n", "CUSTOMER :", cust));
        sb.append(eq).append('\n');
        sb.append(formatReceiptHeaderLine(W));
        sb.append(dashRow).append('\n');
        for (int i = 0; i < cartModel.getRowCount(); i++) {
            Object nameObj = cartModel.getValueAt(i, 1);
            Object qtyObj = cartModel.getValueAt(i, 2);
            Object subObj = cartModel.getValueAt(i, 4);
            String name = nameObj != null ? nameObj.toString() : "";
            int qty = qtyObj instanceof Number ? ((Number) qtyObj).intValue() : 0;
            double sub = subObj instanceof Number ? ((Number) subObj).doubleValue() : 0;
            sb.append(formatLineItemRow(truncPlain(name, COL_ITEM), qty, sub, W));
        }
        sb.append(eq).append('\n');
        sb.append(formatSummaryLine("TOTAL:", orderTotal, W));
        sb.append(formatSummaryLine("CASH:", cashPaid, W));
        sb.append(formatSummaryLine("CHANGE:", changeAmt, W));
        sb.append(eq).append('\n');
        sb.append(centerLine("Thank you for buying!", W)).append('\n');
        return sb.toString();
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
        jPanel3 = new javax.swing.JPanel();
        name = new javax.swing.JLabel();
        orderno = new javax.swing.JLabel();
        customer = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        item = new javax.swing.JLabel();
        quantity = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        total1 = new javax.swing.JLabel();
        cash = new javax.swing.JLabel();
        change = new javax.swing.JLabel();
        back = new javax.swing.JButton();
        print = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RECEIPT");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 250, 50));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        name.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        name.setText("FRUITBASKET");
        jPanel3.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 150, 46));
        jPanel3.add(orderno, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 90, 30));
        jPanel3.add(customer, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 90, 30));

        jLabel2.setText("ORDER NO :");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel3.setText("CUSTOMER :");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Item");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, 20));

        jLabel5.setText("==============================================");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 370, -1));

        jLabel6.setText("==============================================");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 370, -1));

        jLabel7.setText("==============================================");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 370, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel8.setText("---------------------------------------------------");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel9.setText("--------------------------------------------------------------------------------");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 320, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Quantity");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, -1, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Total");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, -1, 20));
        jPanel3.add(item, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 90, 20));

        quantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 90, 20));

        total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 200, 90, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("TOTAL");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("CASH");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 20));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("CHANGE");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, 20));

        total1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(total1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, 90, 20));

        cash.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(cash, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, 90, 20));

        change.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(change, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, 90, 20));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 370, 380));

        back.setBackground(new java.awt.Color(255, 255, 255));
        back.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        back.setText("BACK");
        back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backMouseClicked(evt);
            }
        });
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });
        jPanel2.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 450, 150, 30));

        print.setBackground(new java.awt.Color(255, 255, 255));
        print.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        print.setText("PRINT");
        print.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printMouseClicked(evt);
            }
        });
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });
        jPanel2.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 450, 150, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 500));

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

    private void backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backMouseClicked
        goBackToDashboard();
    }//GEN-LAST:event_backMouseClicked

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        goBackToDashboard();
    }//GEN-LAST:event_backActionPerformed

    private void printMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printMouseClicked
        printReceipt();
    }//GEN-LAST:event_printMouseClicked

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        printReceipt();
    }//GEN-LAST:event_printActionPerformed

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
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new receipt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JLabel cash;
    private javax.swing.JLabel change;
    private javax.swing.JLabel customer;
    private javax.swing.JLabel item;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel name;
    private javax.swing.JLabel orderno;
    private javax.swing.JButton print;
    private javax.swing.JLabel quantity;
    private javax.swing.JLabel total;
    private javax.swing.JLabel total1;
    // End of variables declaration//GEN-END:variables
}
