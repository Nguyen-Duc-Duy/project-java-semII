/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.ProductsManager;

import Commons.ConnectData;
import Controllers.DAO.ProductDAO;
import Emtitys.Products;
import Views.ProductsManager.CategorysManager.CategorysManager;
import Views.ProductsManager.UnitsManager.UnitsManager;
import java.sql.Connection;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ADMIN
 */
public class ProductsManager extends javax.swing.JFrame implements ListPro.interactToolbar{

    /**
     * Creates new form Employer
     */
    ConnectData c = new ConnectData();
    Connection connect = c.Connecting();
    ProductDAO PD = new ProductDAO(connect);
//    PropertysCommon PPCM = new PropertysCommon(this, , null)

    public ProductsManager() {
        initComponents();
//        set layer mặc định
        ListPro listPro = new ListPro(connect, this);
        setLayerPro(listPro);
        CategorysManager catForm = new CategorysManager(connect);
        setlayerOther(catForm);
        setDefaultToolbar();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void setDefaultToolbar() {
        jUpdatePro.setVisible(false);
        jDeletePro.setVisible(false);
        jStatusPro.setVisible(false);
    }

    private void setLayerPro(JPanel panel) {
        jLayeredPane1.removeAll();
        jLayeredPane1.add(panel);
        jLayeredPane1.repaint();
        jLayeredPane1.revalidate();
    }

    private void setlayerOther(JPanel panel) {
        jLayeredPane4.removeAll();
        jLayeredPane4.add(panel);
        jLayeredPane4.repaint();
        jLayeredPane4.revalidate();
    }
    
//
    int idProSelected=10;
    @Override
    public void changeToolbar(int id) {
        jUpdatePro.setVisible(true);
        jDeletePro.setVisible(true);
        jStatusPro.setVisible(true);
        idProSelected = id;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jCreatePro = new javax.swing.JButton();
        jListPro = new javax.swing.JButton();
        jUpdatePro = new javax.swing.JButton();
        jDeletePro = new javax.swing.JButton();
        jStatusPro = new javax.swing.JButton();
        jPrintPro = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel7 = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        jCategorysTool = new javax.swing.JButton();
        jUnitsTool = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Sản Phẩm");

        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        jCreatePro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-1-icon.png"))); // NOI18N
        jCreatePro.setText("Thêm mới");
        jCreatePro.setFocusable(false);
        jCreatePro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCreatePro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCreatePro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateProActionPerformed(evt);
            }
        });
        jToolBar2.add(jCreatePro);

        jListPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/list-icon.png"))); // NOI18N
        jListPro.setText("Danh sách");
        jListPro.setFocusable(false);
        jListPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jListPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jListPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jListProActionPerformed(evt);
            }
        });
        jToolBar2.add(jListPro);

        jUpdatePro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon.png"))); // NOI18N
        jUpdatePro.setText("Cập nhật");
        jUpdatePro.setFocusable(false);
        jUpdatePro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jUpdatePro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jUpdatePro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateProActionPerformed(evt);
            }
        });
        jToolBar2.add(jUpdatePro);

        jDeletePro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/Close-icon.png"))); // NOI18N
        jDeletePro.setText("Xóa");
        jDeletePro.setFocusable(false);
        jDeletePro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jDeletePro.setMaximumSize(new java.awt.Dimension(50, 49));
        jDeletePro.setMinimumSize(new java.awt.Dimension(50, 49));
        jDeletePro.setPreferredSize(new java.awt.Dimension(50, 49));
        jDeletePro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jDeletePro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteProActionPerformed(evt);
            }
        });
        jToolBar2.add(jDeletePro);

        jStatusPro.setText("Trạng thái");
        jStatusPro.setFocusable(false);
        jStatusPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jStatusPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jStatusPro);

        jPrintPro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/print-icon.png"))); // NOI18N
        jPrintPro.setText("In");
        jPrintPro.setFocusable(false);
        jPrintPro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPrintPro.setMaximumSize(new java.awt.Dimension(50, 49));
        jPrintPro.setMinimumSize(new java.awt.Dimension(50, 49));
        jPrintPro.setPreferredSize(new java.awt.Dimension(50, 49));
        jPrintPro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPrintPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrintProActionPerformed(evt);
            }
        });
        jToolBar2.add(jPrintPro);

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sản Phẩm", jPanel1);

        jToolBar4.setRollover(true);
        jToolBar4.setAutoscrolls(true);
        jToolBar4.setBorderPainted(false);

        jCategorysTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/category-icon.png"))); // NOI18N
        jCategorysTool.setText("Danh Mục");
        jCategorysTool.setFocusable(false);
        jCategorysTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCategorysTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCategorysTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCategorysToolActionPerformed(evt);
            }
        });
        jToolBar4.add(jCategorysTool);

        jUnitsTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/scale-icon.png"))); // NOI18N
        jUnitsTool.setText("Đơn vị");
        jUnitsTool.setFocusable(false);
        jUnitsTool.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jUnitsTool.setMaximumSize(new java.awt.Dimension(50, 49));
        jUnitsTool.setMinimumSize(new java.awt.Dimension(50, 49));
        jUnitsTool.setPreferredSize(new java.awt.Dimension(50, 49));
        jUnitsTool.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jUnitsTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUnitsToolActionPerformed(evt);
            }
        });
        jToolBar4.add(jUnitsTool);

        jLayeredPane4.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );

        jLayeredPane4.add(jPanel2, "card2");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane4)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông tin chung", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jListProActionPerformed
        ListPro listPro = new ListPro(connect,this);
        setLayerPro(listPro);
    }//GEN-LAST:event_jListProActionPerformed

    private void jCreateProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateProActionPerformed
        InforPro createPro = new InforPro(connect);
        setLayerPro(createPro);
        
    }//GEN-LAST:event_jCreateProActionPerformed

    private void jDeleteProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteProActionPerformed
        int check = JOptionPane.showConfirmDialog(rootPane, "Bạn có chác muốn xóa Sản Phẩm", "Xác nhận hành động", JOptionPane.CANCEL_OPTION, JOptionPane.OK_OPTION);
        if(check == 0){
            PD.deletePro(idProSelected);
        }
    }//GEN-LAST:event_jDeleteProActionPerformed

    private void jPrintProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPrintProActionPerformed
        JFileChooser chooser = new JFileChooser();
        String choosertitle = "Chọn đường dẫn";
//        chooser.showOpenDialog()
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
        } else {
            JOptionPane.showMessageDialog(rootPane, "Lỗi đường đãn xin thử lại !");
        }

    }//GEN-LAST:event_jPrintProActionPerformed

    private void jCategorysToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCategorysToolActionPerformed
        CategorysManager catForm = new CategorysManager(connect);
        setlayerOther(catForm);
    }//GEN-LAST:event_jCategorysToolActionPerformed

    private void jUnitsToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUnitsToolActionPerformed
        UnitsManager unitForm = new UnitsManager(connect);
        setlayerOther(unitForm);
    }//GEN-LAST:event_jUnitsToolActionPerformed

    private void jUpdateProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateProActionPerformed
        InforPro inforPro = new InforPro(connect,idProSelected);
        setLayerPro(inforPro);
        
    }//GEN-LAST:event_jUpdateProActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProductsManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductsManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductsManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductsManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductsManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jCategorysTool;
    private javax.swing.JButton jCreatePro;
    private javax.swing.JButton jDeletePro;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JButton jListPro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JButton jPrintPro;
    private javax.swing.JButton jStatusPro;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JButton jUnitsTool;
    private javax.swing.JButton jUpdatePro;
    // End of variables declaration//GEN-END:variables

    
}
