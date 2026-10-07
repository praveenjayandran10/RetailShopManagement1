/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.retailshop.view;

import com.retailshop.dao.SalesLookupDAO;
import com.retailshop.model.ComboItem;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import com.retailshop.model.CartItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import com.retailshop.service.SaleService;
import com.retailshop.exception.ValidationException;

/**
 *
 * @author HP
 */
public class SalesFrame extends javax.swing.JFrame {
    private final List<CartItem> cart = new ArrayList<>();

private final DefaultTableModel cartModel =
        new DefaultTableModel(
            new String[]{
                "Product", "Unit Price", "Quantity", "Line Total"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };

    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SalesFrame.class.getName());

    /**
     * Creates new form SalesFrame
     */
    
    public SalesFrame() {
        initComponents();
        setTitle("Retail Shop - Sales and Billing");
    setLocationRelativeTo(null);
    loadDropdowns();
    tblCart.setModel(cartModel);

tblCart.setSelectionMode(
    javax.swing.ListSelectionModel.SINGLE_SELECTION
        
);
setDefaultCloseOperation(
    javax.swing.WindowConstants.DISPOSE_ON_CLOSE
);
txtCashReceived.getDocument().addDocumentListener(
    new javax.swing.event.DocumentListener() {

        @Override
        public void insertUpdate(
                javax.swing.event.DocumentEvent event) {
            calculateChange();
        }

        @Override
        public void removeUpdate(
                javax.swing.event.DocumentEvent event) {
            calculateChange();
        }

        @Override
        public void changedUpdate(
                javax.swing.event.DocumentEvent event) {
            calculateChange();
        }
    }
);

btnRemoveItem.setEnabled(false);

txtQuantity.setText("1");
lblTotal.setText("0.00");
lblChange.setText("0.00");
    }
    
    private void loadDropdowns() {

    cmbCustomer.setEnabled(false);
    cmbEmployee.setEnabled(false);
    cmbProduct.setEnabled(false);
    btnAddToCart.setEnabled(false);
    btnCompleteSale.setEnabled(false);

    new SwingWorker<Void, Void>() {

        private List<ComboItem> customers;
        private List<ComboItem> employees;
        private List<ComboItem> products;

        @Override
        protected Void doInBackground() throws Exception {
            SalesLookupDAO dao = new SalesLookupDAO();

            customers = dao.getCustomers();
            employees = dao.getEmployees();
            products = dao.getProducts();

            return null;
        }

        @Override
        protected void done() {
            try {
                get();

                cmbCustomer.setModel(new DefaultComboBoxModel<>(
                    customers.toArray(new ComboItem[0])
                ));

                cmbEmployee.setModel(new DefaultComboBoxModel<>(
                    employees.toArray(new ComboItem[0])
                ));

                cmbProduct.setModel(new DefaultComboBoxModel<>(
                    products.toArray(new ComboItem[0])
                ));

                cmbCustomer.setEnabled(!customers.isEmpty());
                cmbEmployee.setEnabled(!employees.isEmpty());
                cmbProduct.setEnabled(!products.isEmpty());
                btnAddToCart.setEnabled(!products.isEmpty());

            } catch (Exception ex) {
                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                    SalesFrame.this,
                    "Could not load billing lists. Check the Output panel.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }

    }.execute();
}
    private void refreshCart() {

    cartModel.setRowCount(0);

    BigDecimal total = BigDecimal.ZERO;

    for (CartItem item : cart) {

        cartModel.addRow(new Object[]{
            item.getProductName(),
            item.getUnitPrice().setScale(2).toPlainString(),
            item.getQuantity(),
            item.getLineTotal().setScale(2).toPlainString()
        });

        total = total.add(item.getLineTotal());
    }

    lblTotal.setText(total.setScale(2).toPlainString());

    // Reset change when the cart total changes.
    lblChange.setText("0.00");

    btnCompleteSale.setEnabled(
        !cart.isEmpty()
        && cmbCustomer.getSelectedItem() != null
        && cmbEmployee.getSelectedItem() != null
    );
}
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbCustomer = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbEmployee = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbProduct = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        btnAddToCart = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        btnRemoveItem = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCashReceived = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblChange = new javax.swing.JLabel();
        btnCompleteSale = new javax.swing.JButton();
        btnClearSale = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Sales and Billing");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Customer");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Cashier");

        cmbEmployee.addActionListener(this::cmbEmployeeActionPerformed);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Product");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Quantity");

        txtQuantity.addActionListener(this::txtQuantityActionPerformed);

        btnAddToCart.setText("Add to cart");
        btnAddToCart.addActionListener(this::btnAddToCartActionPerformed);

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Product", "Unit Price", "Quantity", "Line Total"
            }
        ));
        jScrollPane1.setViewportView(tblCart);

        btnRemoveItem.setText("Remove Selected Item");
        btnRemoveItem.addActionListener(this::btnRemoveItemActionPerformed);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Total:");

        lblTotal.setText("0.00");

        jLabel7.setText("Cash received");

        jLabel8.setText("Change:");

        lblChange.setText("0.00");

        btnCompleteSale.setText("Complete Sale");
        btnCompleteSale.addActionListener(this::btnCompleteSaleActionPerformed);

        btnClearSale.setText("Clear Sale");
        btnClearSale.addActionListener(this::btnClearSaleActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(cmbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel3))
                                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnAddToCart, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cmbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(btnRemoveItem))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addGap(35, 35, 35)
                        .addComponent(lblTotal))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtCashReceived, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(lblChange, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(btnCompleteSale)
                        .addGap(34, 34, 34)
                        .addComponent(btnClearSale, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(jLabel1)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddToCart))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemoveItem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCashReceived, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblChange))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCompleteSale)
                    .addComponent(btnClearSale))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEmployeeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEmployeeActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void btnAddToCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToCartActionPerformed
ComboItem selected =
        (ComboItem) cmbProduct.getSelectedItem();

if (selected == null) {
    JOptionPane.showMessageDialog(
        this, "Select a product."
    );
    return;
}

final int quantityToAdd;

try {
    quantityToAdd = Integer.parseInt(
        txtQuantity.getText().trim()
    );

    if (quantityToAdd <= 0) {
        JOptionPane.showMessageDialog(
            this, "Quantity must be greater than zero."
        );
        return;
    }

} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(
        this, "Enter a whole number for quantity."
    );
    return;
}

final int productId = selected.getId();

int existingIndex = -1;
int existingQuantity = 0;

for (int i = 0; i < cart.size(); i++) {
    if (cart.get(i).getProductId() == productId) {
        existingIndex = i;
        existingQuantity = cart.get(i).getQuantity();
        break;
    }
}

long combined = (long) existingQuantity + quantityToAdd;

if (combined > Integer.MAX_VALUE) {
    JOptionPane.showMessageDialog(
        this, "Quantity is too large."
    );
    return;
}

final int totalQuantity = (int) combined;
final int index = existingIndex;

btnAddToCart.setEnabled(false);
btnRemoveItem.setEnabled(false);
btnClearSale.setEnabled(false);
btnCompleteSale.setEnabled(false);

new SwingWorker<CartItem, Void>() {

    @Override
    protected CartItem doInBackground() throws Exception {
        return new SalesLookupDAO().getCartItem(
            productId, totalQuantity
        );
    }

    @Override
    protected void done() {
        try {
            CartItem item = get();

            if (index == -1) {
                cart.add(item);
            } else {
                cart.set(index, item);
            }

            txtQuantity.setText("1");

        } catch (Exception ex) {
            ex.printStackTrace();

            Throwable cause = ex.getCause();
            String message = "Could not add the product.";

            if (cause instanceof java.sql.SQLException) {
                message = cause.getMessage();
            }

            JOptionPane.showMessageDialog(
                SalesFrame.this,
                message,
                "Cart Error",
                JOptionPane.ERROR_MESSAGE
            );

        } finally {
            refreshCart();

            btnAddToCart.setEnabled(
                cmbProduct.getSelectedItem() != null
                    
            );

            btnRemoveItem.setEnabled(!cart.isEmpty());
            btnClearSale.setEnabled(true);
        }
        // Reset change when the cart total changes.
lblChange.setText("0.00");
calculateChange();
    }

}.execute();        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddToCartActionPerformed

    private void btnRemoveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveItemActionPerformed
int selectedRow = tblCart.getSelectedRow();

if (selectedRow == -1) {
    JOptionPane.showMessageDialog(
        this, "Select an item in the cart to remove."
    );
    return;
}

int row = tblCart.convertRowIndexToModel(selectedRow);

cart.remove(row);
refreshCart();

btnRemoveItem.setEnabled(!cart.isEmpty());        // TODO add your handling code here:
    }//GEN-LAST:event_btnRemoveItemActionPerformed

    private void btnClearSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearSaleActionPerformed
if (!cart.isEmpty()) {
    int answer = JOptionPane.showConfirmDialog(
        this,
        "Clear all items from this sale?",
        "Clear Sale",
        JOptionPane.YES_NO_OPTION
    );

    if (answer != JOptionPane.YES_OPTION) {
        return;
    }
}

cart.clear();
txtQuantity.setText("1");
txtCashReceived.setText("");

refreshCart();
btnRemoveItem.setEnabled(false);        // TODO add your handling code here:
    }//GEN-LAST:event_btnClearSaleActionPerformed

    private void btnCompleteSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompleteSaleActionPerformed
ComboItem customer =
        (ComboItem) cmbCustomer.getSelectedItem();

ComboItem employee =
        (ComboItem) cmbEmployee.getSelectedItem();

if (customer == null || employee == null || cart.isEmpty()) {
    JOptionPane.showMessageDialog(
        this, "Select a customer, cashier, and add products."
    );
    return;
}

final BigDecimal cashReceived;

try {
    cashReceived = new BigDecimal(
        txtCashReceived.getText().trim()
    );

    if (cashReceived.signum() < 0 || cashReceived.scale() > 2) {
        JOptionPane.showMessageDialog(
            this, "Enter a valid cash amount with up to 2 decimals."
        );
        return;
    }

} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(
        this, "Enter a valid cash amount."
    );
    return;
}

// Take a snapshot so this sale uses the current cart.
final List<CartItem> saleCart = new ArrayList<>(cart);

BigDecimal total = BigDecimal.ZERO;

for (CartItem item : saleCart) {
    total = total.add(item.getLineTotal());
}

if (cashReceived.compareTo(total) < 0) {
    JOptionPane.showMessageDialog(
        this, "Cash received is less than the total."
    );
    return;
}

final BigDecimal saleTotal = total;
final BigDecimal change = cashReceived.subtract(saleTotal);

int answer = JOptionPane.showConfirmDialog(
    this,
    "Complete sale?\n"
        + "Total: " + saleTotal.setScale(2).toPlainString()
        + "\nChange: " + change.setScale(2).toPlainString(),
    "Confirm Sale",
    JOptionPane.YES_NO_OPTION
);

if (answer != JOptionPane.YES_OPTION) {
    return;
}

btnCompleteSale.setEnabled(false);
btnAddToCart.setEnabled(false);
btnRemoveItem.setEnabled(false);
btnClearSale.setEnabled(false);

cmbCustomer.setEnabled(false);
cmbEmployee.setEnabled(false);
cmbProduct.setEnabled(false);

txtQuantity.setEnabled(false);
txtCashReceived.setEnabled(false);
tblCart.setEnabled(false);

new SwingWorker<Integer, Void>() {

    @Override
    protected Integer doInBackground() throws Exception {
        return new SaleService().completeSale(
            customer.getId(),
            employee.getId(),
            saleCart,
            cashReceived
        );
    }

    @Override
    protected void done() {
        boolean saved = false;

        try {
            int saleId = get();
            saved = true;

            cart.clear();
            txtCashReceived.setText("");
            txtQuantity.setText("1");
            refreshCart();

            JOptionPane.showMessageDialog(
                SalesFrame.this,
                "Sale saved successfully!\n"
                    + "Sale ID: " + saleId
                    + "\nTotal: "
                    + saleTotal.setScale(2).toPlainString()
                    + "\nChange: "
                    + change.setScale(2).toPlainString()
            );

        } catch (Exception ex) {
            ex.printStackTrace();

            Throwable cause = ex.getCause();

            String message =
                "Could not complete the sale. Check the Output panel.";

            if (cause instanceof ValidationException) {
                message = cause.getMessage();
            }

            JOptionPane.showMessageDialog(
                SalesFrame.this,
                message,
                "Sale Error",
                JOptionPane.ERROR_MESSAGE
            );

        } finally {
            txtQuantity.setEnabled(true);
            txtCashReceived.setEnabled(true);
            tblCart.setEnabled(true);

            btnClearSale.setEnabled(true);
            btnRemoveItem.setEnabled(!cart.isEmpty());

            if (saved) {
                // Reload products because available stock changed.
                loadDropdowns();
            } else {
                cmbCustomer.setEnabled(
                    cmbCustomer.getItemCount() > 0
                );
                cmbEmployee.setEnabled(
                    cmbEmployee.getItemCount() > 0
                );
                cmbProduct.setEnabled(
                    cmbProduct.getItemCount() > 0
                );

                btnAddToCart.setEnabled(
                    cmbProduct.getSelectedItem() != null
                );

                refreshCart();
            }
        }
    }

}.execute();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCompleteSaleActionPerformed
      private void calculateChange() {

    BigDecimal total = BigDecimal.ZERO;

    for (CartItem item : cart) {
        total = total.add(item.getLineTotal());
    }

    String text = txtCashReceived.getText().trim();

    if (text.isEmpty()) {
        lblChange.setText("0.00");
        return;
    }

    try {
        BigDecimal cash = new BigDecimal(text);

        if (cash.signum() < 0 || cash.scale() > 2) {
            lblChange.setText("Invalid cash");
            return;
        }

        if (cash.compareTo(total) < 0) {
            lblChange.setText("Insufficient cash");
            return;
        }

        lblChange.setText(
            cash.subtract(total).setScale(2).toPlainString()
        );

    } catch (NumberFormatException ex) {
        lblChange.setText("Invalid cash");
    }
}
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new SalesFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddToCart;
    private javax.swing.JButton btnClearSale;
    private javax.swing.JButton btnCompleteSale;
    private javax.swing.JButton btnRemoveItem;
    private javax.swing.JComboBox<com.retailshop.model.ComboItem> cmbCustomer;
    private javax.swing.JComboBox<com.retailshop.model.ComboItem> cmbEmployee;
    private javax.swing.JComboBox<com.retailshop.model.ComboItem> cmbProduct;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChange;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblCart;
    private javax.swing.JTextField txtCashReceived;
    private javax.swing.JTextField txtQuantity;
    // End of variables declaration//GEN-END:variables
}


