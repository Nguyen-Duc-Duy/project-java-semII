/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.ProductsManager;

import Controllers.DAO.GroupsPerDAO;
import Controllers.DAO.ProductDAO;
import Emtitys.Employers;
import Emtitys.Products;
import Views.Employees.EmployeesManager;
import Views.MethodCommon;
import Views.ProductsManager.CategorysManager.CategorysManager;
import Views.ProductsManager.UnitsManager.UnitsManager;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.SpreadsheetInfo;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ADMIN
 */
public class ProductsManager extends javax.swing.JFrame implements ListPro.interactToolbar {

    /**
     * Creates new form Employer
     */
    Connection connect;
    ProductDAO PD;
    ListPro listPro;
    GroupsPerDAO GPD;
    Employers em;
//    PropertysCommon PPCM = new PropertysCommon(this, , null)

    public ProductsManager(Connection c, Employers em) {
        this.connect = c;
        this.em = em;
        GPD = new GroupsPerDAO(c);
        PD = new ProductDAO(connect);

        initComponents();
        setLocationRelativeTo(null);

        new MethodCommon(getClass(), this, "icon-logo-X-green16.png");

        RolesView();
        RolesActions();
//        set layer mặc định
        listPro = new ListPro(connect, this, em);
        setLayerPro(listPro);
        CategorysManager catForm = new CategorysManager(connect, em);
        setlayerOther(catForm);
        setDefaultToolbar();
        setDefaultCloseOperation(ProductsManager.DISPOSE_ON_CLOSE);

    }
//  phân quyền

    private void RolesView() {
        jBoxPro.remove(jPro);
        jBoxPro.remove(jCatUnit);
        jCategorysTool.setVisible(false);
        jUnitsTool.setVisible(false);
        if (em.getStatus() == 2) {
            jBoxPro.addTab("Sản phẩm", jPro);
            jBoxPro.addTab("Thông tin chung", jCatUnit);
            jCategorysTool.setVisible(true);
            jUnitsTool.setVisible(true);
        } else {
            List<String> listPAByEm = GPD.listPerActByEm(em.getId());

            for (String PAByEm : listPAByEm) {

                switch (PAByEm) {
                    case "Pro":
                        jBoxPro.addTab("Sản phẩm", jPro);
                        break;
                    case "Cat":
                        jBoxPro.addTab("Thông tin chung", jCatUnit);
                        jCategorysTool.setVisible(true);
                        break;
                    case "Unit":
                        jBoxPro.addTab("Thông tin chung", jCatUnit);
                        jUnitsTool.setVisible(true);
                        break;
                }
            }
        }
    }

    private void RolesActions() {
        jCreatePro.setVisible(false);
        jListPro.setVisible(false);
        jUpdatePro.setVisible(false);
        jDeletePro.setVisible(false);
        jPrintPro.setVisible(false);
        if (em.getStatus() == 2) {
            jCreatePro.setVisible(true);
            jListPro.setVisible(true);
            jUpdatePro.setVisible(true);
            jDeletePro.setVisible(true);
            jPrintPro.setVisible(true);
        } else {
            List<String> ListActionsByEm = GPD.listActByEm(em.getId());
            for (String ActionsByEm : ListActionsByEm) {
                switch (ActionsByEm) {
                    case "P-1":
                        jCreatePro.setVisible(true);
                        break;
                    case "P-2":
                        jUpdatePro.setVisible(true);
                        break;
                    case "P-3":
                        jDeletePro.setVisible(true);
                        break;
                    case "P-4":
                        jListPro.setVisible(true);
                        break;
                    case "P-5":
                        jPrintPro.setVisible(true);
                        break;
                }
            }
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void setDefaultToolbar() {
        jUpdatePro.setVisible(false);
        jDeletePro.setVisible(false);
//        jStatusPro.setVisible(false);
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
    int idProSelected = 10;

    @Override
    public void changeToolbar(int id) {
        jUpdatePro.setVisible(true);
        jDeletePro.setVisible(true);
//        jStatusPro.setVisible(true);
        idProSelected = id;
        RolesActions();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBoxPro = new javax.swing.JTabbedPane();
        jPro = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jCreatePro = new javax.swing.JButton();
        jListPro = new javax.swing.JButton();
        jUpdatePro = new javax.swing.JButton();
        jDeletePro = new javax.swing.JButton();
        jPrintPro = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jCatUnit = new javax.swing.JPanel();
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

        javax.swing.GroupLayout jProLayout = new javax.swing.GroupLayout(jPro);
        jPro.setLayout(jProLayout);
        jProLayout.setHorizontalGroup(
            jProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jProLayout.setVerticalGroup(
            jProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jProLayout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBoxPro.addTab("Sản Phẩm", jPro);

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

        javax.swing.GroupLayout jCatUnitLayout = new javax.swing.GroupLayout(jCatUnit);
        jCatUnit.setLayout(jCatUnitLayout);
        jCatUnitLayout.setHorizontalGroup(
            jCatUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jCatUnitLayout.setVerticalGroup(
            jCatUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jCatUnitLayout.createSequentialGroup()
                .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBoxPro.addTab("Thông tin chung", jCatUnit);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBoxPro)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBoxPro)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jListProActionPerformed
        ListPro listPro = new ListPro(connect, this, em);
        setLayerPro(listPro);
        setDefaultToolbar();
    }//GEN-LAST:event_jListProActionPerformed

    private void jCreateProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateProActionPerformed
        InforPro createPro = new InforPro(connect);
        setLayerPro(createPro);
        setDefaultToolbar();
    }//GEN-LAST:event_jCreateProActionPerformed
    @Override
    public void deletePro(int id) {
        int check = JOptionPane.showConfirmDialog(rootPane, "Bạn có chác muốn xóa Sản Phẩm", "Xác nhận hành động", JOptionPane.CANCEL_OPTION, JOptionPane.OK_OPTION);
        if (check == 0) {
            PD.deletePro(id);
        };
        setLayerPro(new ListPro(connect, this, em));
    }
    private void jDeleteProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteProActionPerformed
        deletePro(idProSelected);
    }//GEN-LAST:event_jDeleteProActionPerformed
    public void saveExcel() {

        JFileChooser fileChooser = new JFileChooser();
        File file = new File("E:/");
        fileChooser.setSelectedFile(file);
        fileChooser.showSaveDialog(jPrintPro);
        String pathNew = String.valueOf(fileChooser.getSelectedFile());
        try {
            SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
            ExcelFile excel = new ExcelFile();
            ExcelWorksheet workSheet = excel.addWorksheet("Tables");
            if (file.exists()) {
                file.delete();
                workSheet.getCell(0, 0).setValue("#");
                workSheet.getCell(0, 1).setValue("Tên");
                workSheet.getCell(0, 2).setValue("Mã");
                workSheet.getCell(0, 3).setValue("Danh Mục");
                workSheet.getCell(0, 4).setValue("Giá");
                workSheet.getCell(0, 5).setValue("Khuyến Mãi");
                workSheet.getCell(0, 6).setValue("Mô Tả");
                workSheet.getCell(0, 7).setValue("Số Lượng");
                workSheet.getCell(0, 8).setValue("Ảnh");
                workSheet.getCell(0, 9).setValue("Đơn Vị");
                workSheet.getCell(0, 10).setValue("Trạng Thái");
                workSheet.getCell(0, 11).setValue("Ngày Tạo");
                workSheet.getCell(0, 12).setValue("Ngày Sửa");
                List<Products> listPros = PD.getAll();
                excel.save(pathNew);
                int count = 0;
                Locale locale = new Locale("vi", "VN");      
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                

                for (int i = 1; i <= listPros.size(); i++) {
                    count++;
                    workSheet.getCell(i, 0).setValue(count);
                    for (int j = 0; j < 6; j++) {
                        workSheet.getCell(i, 1).setValue(listPros.get(i - 1).getName());
                        workSheet.getCell(i, 2).setValue(listPros.get(i - 1).getId_cat());
                        workSheet.getCell(i, 3).setValue(currencyFormatter.format(listPros.get(i - 1).getPrice()));
                        workSheet.getCell(i, 4).setValue(currencyFormatter.format(listPros.get(i - 1).getSale()));
                        workSheet.getCell(i, 5).setValue(listPros.get(i - 1).getDescript());
                        workSheet.getCell(i, 6).setValue(listPros.get(i - 1).getQuantity());
                        workSheet.getCell(i, 7).setValue(listPros.get(i - 1).getImg());
                        workSheet.getCell(i, 8).setValue(listPros.get(i - 1).getId_unit());
                        workSheet.getCell(i, 9).setValue(listPros.get(i - 1).getStatus());
                        workSheet.getCell(i, 10).setValue(listPros.get(i - 1).getDate_crated());
                        workSheet.getCell(i, 11).setValue(listPros.get(i - 1).getDate_updated());

                    }
                }
                excel.save(pathNew);
            }
            JOptionPane.showMessageDialog(rootPane, "Lưu file thành công !");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "File không tồn tại ! Xin thử lại !");
            System.out.println(ex.toString());
        }
    }
    private void jPrintProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPrintProActionPerformed
        setDefaultToolbar();

        saveExcel();
    }//GEN-LAST:event_jPrintProActionPerformed

    private void jCategorysToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCategorysToolActionPerformed
        CategorysManager catForm = new CategorysManager(connect, em);
        setlayerOther(catForm);
    }//GEN-LAST:event_jCategorysToolActionPerformed

    private void jUnitsToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUnitsToolActionPerformed
        UnitsManager unitForm = new UnitsManager(connect, em);
        setlayerOther(unitForm);
    }//GEN-LAST:event_jUnitsToolActionPerformed
    @Override
    public void changeInforPopup(int id) {
        InforPro inforPro = new InforPro(connect, idProSelected);
        setLayerPro(inforPro);
    }
    private void jUpdateProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateProActionPerformed
        InforPro inforPro = new InforPro(connect, idProSelected);
        setLayerPro(inforPro);

    }//GEN-LAST:event_jUpdateProActionPerformed

    @Override
    public void changeSTT(int id, int status) {
        PD.changeSTT(id, status);
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
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jBoxPro;
    private javax.swing.JPanel jCatUnit;
    private javax.swing.JButton jCategorysTool;
    private javax.swing.JButton jCreatePro;
    private javax.swing.JButton jDeletePro;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JButton jListPro;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JButton jPrintPro;
    private javax.swing.JPanel jPro;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JButton jUnitsTool;
    private javax.swing.JButton jUpdatePro;
    // End of variables declaration//GEN-END:variables

}
