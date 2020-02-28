/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.ProductsManager.CategorysManager;

import Controllers.DAO.CategoryDAO;
import Controllers.DAO.GroupsPerDAO;
import Emtitys.Categorys;
import Emtitys.Employers;
import Views.ProductsManager.ListPro;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class CategorysManager extends javax.swing.JPanel {

    Connection con;
    CategoryDAO CD;
    GroupsPerDAO GPA;
    Employers em;

    public CategorysManager(Connection c, Employers em) {
        this.con = c;
        this.em = em;

        CD = new CategoryDAO(con);
        GPA = new GroupsPerDAO(con);
        initComponents();
        showPopupInteract();
        defaultInterface();
        RolesActions();
    }
//  phân quyền

    private void RolesActions() {
        List<String> listActionsByEm = GPA.listActByEm(em.getId());
        jCreateCat.setVisible(false);
        jUpdateCat.setVisible(false);
        jPUChangeSTT.setVisible(false);
        jPUUpdateCat.setVisible(false);
        jPUCreasteCat.setVisible(false);
        if (em.getStatus() == 2) {
            jCreateCat.setVisible(true);
            jUpdateCat.setVisible(true);
            jPUChangeSTT.setVisible(true);
            jPUUpdateCat.setVisible(true);
            jPUCreasteCat.setVisible(true);
        } else {
            for (String ActionsByEm : listActionsByEm) {
                switch (ActionsByEm) {
                    case "CT-1":
                        jCreateCat.setVisible(true);
                        jPUCreasteCat.setVisible(true);
                        break;
                    case "CT-2":
                        jUpdateCat.setVisible(true);
                        jPUUpdateCat.setVisible(true);
                        break;
                    case "CT-3":
                        jBoxInforCat.setVisible(false);
                        break;
                    case "CT-5":
                        jBoxInforCat.setVisible(false);
                        jPUChangeSTT.setVisible(true);
                        break;
                }
            }
        }
        
    }
// 

    private void defaultInterface() {
        addTableListCat(CD.getAll());
        jStatusCat.setVisible(false);
        jUpdateCat.setVisible(false);
        jDeleteCat.setVisible(false);
        jCreateCat.setVisible(true);
        jPUDeleteCat.setVisible(false);
        jNameCat.setText("");
    }
//    

    private void addTableListCat(List<Categorys> list) {
        DefaultTableModel dtm = new DefaultTableModel();
        List<Categorys> listCat = list;
        dtm.addColumn("#");
        dtm.addColumn("Danh Mục");
        dtm.addColumn("Trạng Thái");
        dtm.addColumn("Ngày Tạo");
        dtm.addColumn("Ngày Cập Nhật");

        int i = 0;
        for (Categorys l : listCat) {
            Vector v = new Vector<>();
            i++;
            String status = "";
            if (l.getStatus() == 1) {
                status = "HIện";
            } else {
                status = "Ẩn";
            }
            v.add(i);
            v.add(l.getName());
            v.add(status);
            v.add(l.getDate_created());
            v.add(l.getDate_updated());
            dtm.addRow(v);
        }
        jListCat.setModel(dtm);
        jListCat.getColumnModel().getColumn(0).setMinWidth(30);
        jListCat.getColumnModel().getColumn(0).setMaxWidth(60);
    }

//  
    private void setPopupInteract(int status) {
        if (status == 1) {
            jPUChangeSTT.setIcon(new ImageIcon(ListPro.class.getResource("/Commons/img/Eye-Invisible-icon.png")));
            jPUChangeSTT.setText("Ẩn");
        } else {
            jPUChangeSTT.setIcon(new ImageIcon(ListPro.class.getResource("/Commons/img/Eye-Visible-icon.png")));
            jPUChangeSTT.setText("Hiện");
        }
    }
//
    int CatPopup = 0;
    int statusPopup = 0;

    private void showPopupInteract() {
        jListCat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = jListCat.rowAtPoint(e.getPoint());
                if (r >= 0 && r < jListCat.getRowCount()) {
                    jListCat.setRowSelectionInterval(r, r);
                } else {
                    jListCat.clearSelection();
                }
                int rowindex = jListCat.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jInteractCat.show(e.getComponent(), e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractCat.show(e.getComponent(), e.getX(), e.getY());
                    }
                    Categorys c = CD.getAll().get(jListCat.getSelectedRow());
                    CatPopup = c.getId();
                    statusPopup = c.getStatus();
                    if (c.getStatus() == 1) {
                        setPopupInteract(1);
                    } else {
                        setPopupInteract(0);
                    }

                }
            }
        });

    }
//    

    private void showInforCat() {

        int row = jListCat.getSelectedRow();
        Categorys c = CD.getAll().get(row);
        CatPopup = c.getId();
        jUpdateCat.setVisible(true);
//        jDeleteCat.setVisible(true);
        jCreateCat.setVisible(false);
        jStatusCat.setVisible(true);
        if (c.getStatus() == 1) {
            jStatusCat.setSelected(true);
        } else {
            jStatusCat.setSelected(false);
        }

        jNameCat.setText(c.getName());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInteractCat = new javax.swing.JPopupMenu();
        jPUCreasteCat = new javax.swing.JMenuItem();
        jPUUpdateCat = new javax.swing.JMenuItem();
        jPUDeleteCat = new javax.swing.JMenuItem();
        jPUChangeSTT = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListCat = new javax.swing.JTable();
        jBoxInforCat = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jCreateCat = new javax.swing.JButton();
        jUpdateCat = new javax.swing.JButton();
        jDeleteCat = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jNameCat = new javax.swing.JTextField();
        jStatusCat = new javax.swing.JCheckBox();
        jErrorCat = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSearch = new javax.swing.JTextField();

        jPUCreasteCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-icon16.png"))); // NOI18N
        jPUCreasteCat.setText("Thêm mới");
        jPUCreasteCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPUCreasteCatActionPerformed(evt);
            }
        });
        jInteractCat.add(jPUCreasteCat);

        jPUUpdateCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon-16.png"))); // NOI18N
        jPUUpdateCat.setText("Sửa");
        jPUUpdateCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPUUpdateCatActionPerformed(evt);
            }
        });
        jInteractCat.add(jPUUpdateCat);

        jPUDeleteCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/Close-icon16.png"))); // NOI18N
        jPUDeleteCat.setText("Xóa");
        jPUDeleteCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPUDeleteCatActionPerformed(evt);
            }
        });
        jInteractCat.add(jPUDeleteCat);

        jPUChangeSTT.setText("abc");
        jPUChangeSTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPUChangeSTTActionPerformed(evt);
            }
        });
        jInteractCat.add(jPUChangeSTT);

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(725, 312));

        jListCat.setModel(new javax.swing.table.DefaultTableModel(
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
        jListCat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListCatMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jListCatMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jListCatMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jListCat);

        jBoxInforCat.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jCreateCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-1-icon.png"))); // NOI18N
        jCreateCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateCatActionPerformed(evt);
            }
        });
        jPanel3.add(jCreateCat);

        jUpdateCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon.png"))); // NOI18N
        jUpdateCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateCatActionPerformed(evt);
            }
        });
        jPanel3.add(jUpdateCat);

        jDeleteCat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/Close-icon.png"))); // NOI18N
        jDeleteCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteCatActionPerformed(evt);
            }
        });
        jPanel3.add(jDeleteCat);

        jLabel2.setText("Tên danh mục");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jNameCat))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNameCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jStatusCat.setText("HIển thị");
        jStatusCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStatusCatActionPerformed(evt);
            }
        });

        jErrorCat.setForeground(new java.awt.Color(255, 102, 102));

        javax.swing.GroupLayout jBoxInforCatLayout = new javax.swing.GroupLayout(jBoxInforCat);
        jBoxInforCat.setLayout(jBoxInforCatLayout);
        jBoxInforCatLayout.setHorizontalGroup(
            jBoxInforCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jBoxInforCatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jStatusCat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jErrorCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jBoxInforCatLayout.setVerticalGroup(
            jBoxInforCatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxInforCatLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jStatusCat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jErrorCat, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBoxInforCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBoxInforCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Tìm kiếm");

        jSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchActionPerformed(evt);
            }
        });
        jSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(355, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jSearch))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchActionPerformed

    }//GEN-LAST:event_jSearchActionPerformed

    private void jCreateCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateCatActionPerformed
        String name = jNameCat.getText();
        List<Categorys> listCat = CD.getAll();
        Pattern pt = Pattern.compile("^(\\\\)+$");
        Matcher matcher = pt.matcher(name);
        boolean validate = matcher.find();
        int check = 0;
        for (Categorys listCat1 : listCat) {
            if (jNameCat.getText().equalsIgnoreCase(listCat1.getName())) {
                jErrorCat.setText("Danh mục đã tồn tại !");
                break;
            } else {
                check++;
            }
        }
        if (check == listCat.size()) {
            if (validate) {
                jErrorCat.setText("Giá trị nhập không hợp lệ ! Xin nhập lại !");
            } else {
                name = jNameCat.getText();
                Categorys c = new Categorys(name, 1, "");
                CD.createCat(c);
                defaultInterface();
            }

        }


    }//GEN-LAST:event_jCreateCatActionPerformed

    private void jPUCreasteCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPUCreasteCatActionPerformed
        defaultInterface();
    }//GEN-LAST:event_jPUCreasteCatActionPerformed

    private void jListCatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListCatMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {
            jInteractCat.show(evt.getComponent(), evt.getX(), evt.getY());
        }
        Categorys cat = CD.getAll().get(jListCat.getSelectedRow());
        setPopupInteract(cat.getId());
    }//GEN-LAST:event_jListCatMouseClicked

    private void jListCatMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListCatMouseReleased

    }//GEN-LAST:event_jListCatMouseReleased

    private void jListCatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListCatMousePressed
        List<String> listActionsByEm = GPA.listActByEm(em.getId());

        for (String ActionsByEm : listActionsByEm) {
            switch (ActionsByEm) {
                case "CT-2":
                    showInforCat();
                    break;
            }
        }
    }//GEN-LAST:event_jListCatMousePressed

    private void jStatusCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jStatusCatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jStatusCatActionPerformed

    private void jPUUpdateCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPUUpdateCatActionPerformed

        List<String> listActionsByEm = GPA.listActByEm(em.getId());
        if (em.getStatus() == 2) {
            showInforCat();
        } else {
            for (String ActionsByEm : listActionsByEm) {
                switch (ActionsByEm) {
                    case "CT-2":
                        showInforCat();
                        break;
                }
            }
        }
        
    }//GEN-LAST:event_jPUUpdateCatActionPerformed
//

    private void changeSTTCat() {
        int check = 0;
        if (statusPopup == 1) {
            check = JOptionPane.showConfirmDialog(jCreateCat, "Bạn có chắc muốn ẩn danh mục này ? Hành động này sẽ ẩn mọi sản phâm thuộc danh mục này !", "Xác nhân ", JOptionPane.YES_NO_OPTION);

        } else {
            check = JOptionPane.showConfirmDialog(jCreateCat, "Bạn có chắc muốn hiển thị danh mục này ?  Hành động này sẽ Hiển thị mọi sản phâm thuộc danh mục này !", "Xác nhân ", JOptionPane.YES_NO_OPTION);

        }
        if (check == 0) {
            CD.changeSTT(CatPopup, statusPopup);
            defaultInterface();
        }

    }
    private void jPUDeleteCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPUDeleteCatActionPerformed
        changeSTTCat();
    }//GEN-LAST:event_jPUDeleteCatActionPerformed

    private void jUpdateCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateCatActionPerformed
        String name = jNameCat.getText();
        int status;
        if (jStatusCat.isSelected()) {
            status = 1;
        } else {
            status = 0;
        };
        Categorys c = new Categorys(CatPopup, name, status, "");
        CD.updateCat(c);
        defaultInterface();
    }//GEN-LAST:event_jUpdateCatActionPerformed

    private void jPUChangeSTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPUChangeSTTActionPerformed
        changeSTTCat();
    }//GEN-LAST:event_jPUChangeSTTActionPerformed

    private void jDeleteCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteCatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jDeleteCatActionPerformed

    private void jSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSearchKeyReleased
        String searchKey = jSearch.getText();

        if (searchKey.equalsIgnoreCase("")) {
            addTableListCat(CD.getAll());
        } else {
            addTableListCat(CD.searchCat(searchKey));

        }

    }//GEN-LAST:event_jSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jBoxInforCat;
    private javax.swing.JButton jCreateCat;
    private javax.swing.JButton jDeleteCat;
    private javax.swing.JLabel jErrorCat;
    private javax.swing.JPopupMenu jInteractCat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTable jListCat;
    private javax.swing.JTextField jNameCat;
    private javax.swing.JMenuItem jPUChangeSTT;
    private javax.swing.JMenuItem jPUCreasteCat;
    private javax.swing.JMenuItem jPUDeleteCat;
    private javax.swing.JMenuItem jPUUpdateCat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jSearch;
    private javax.swing.JCheckBox jStatusCat;
    private javax.swing.JButton jUpdateCat;
    // End of variables declaration//GEN-END:variables
}
