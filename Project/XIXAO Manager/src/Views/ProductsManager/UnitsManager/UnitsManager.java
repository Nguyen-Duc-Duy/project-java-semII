/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.ProductsManager.UnitsManager;

import Controllers.DAO.GroupsPerDAO;
import Controllers.DAO.UnitDAO;
import Emtitys.Employers;
import Emtitys.Units;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class UnitsManager extends javax.swing.JPanel {

    /**
     * Creates new form Units
     */
    Connection con;
    UnitDAO UD;
    GroupsPerDAO GPD;
    Employers em;

    public UnitsManager(Connection c, Employers em) {
        this.con = c;
        this.em = em;

        UD = new UnitDAO(c);
        GPD = new GroupsPerDAO(c);
        initComponents();
        defaultInterface();
        showPopupUnit();
        RolesActions();
    }

    //  phân quyền
    private void RolesActions() {
        List<String> listActionsByEm = GPD.listActByEm(em.getId());
        jAddUnit.setVisible(false);
        jUpdateUnit.setVisible(false);
        jAddUnit.setVisible(false);
        jBoxUnit.setVisible(false);
        jPUCreateUnit.setVisible(false);
        jPUUpdateUnit.setVisible(false);
        if (em.getStatus() == 2) {
            jAddUnit.setVisible(true);
            jUpdateUnit.setVisible(true);
            jAddUnit.setVisible(true);
            jBoxUnit.setVisible(true);
            jPUCreateUnit.setVisible(true);
            jPUUpdateUnit.setVisible(true);
        } else {
            for (String ActionsByEm : listActionsByEm) {
                switch (ActionsByEm) {
                    case "U-1":
                        jAddUnit.setVisible(true);
                        jPUCreateUnit.setVisible(true);
                        jBoxUnit.setVisible(true);
                        break;
                    case "U-2":
                        jUpdateUnit.setVisible(true);
                        jPUUpdateUnit.setVisible(true);
                        jBoxUnit.setVisible(true);
                        break;
                    case "U-4":
                        jBoxUnit.setVisible(false);
                        break;
                }
            }
        }
        
    }
//    giao diện mặc định

    private void defaultInterface() {
        addTableListU(UD.getAll());
        jAddUnit.setVisible(true);
        jUpdateUnit.setVisible(false);
        jNameU.setText("");
    }
//

    private void addTableListU(List<Units> listUnit) {
        DefaultTableModel dtm = new DefaultTableModel();
        List<Units> listU = listUnit;
        dtm.addColumn("#");
        dtm.addColumn("Đơn Vị");
        dtm.addColumn("Ngày Tạo");
        dtm.addColumn("Ngày Cập Nhật");

        int i = 0;
        for (Units u : listU) {
            Vector v = new Vector();
            i++;
            v.add(i);
            v.add(u.getName());
            v.add(u.getDate_created());
            v.add(u.getDate_updated());
            dtm.addRow(v);
        }
        jListUnits.setModel(dtm);
        jListUnits.getColumnModel().getColumn(0).setMinWidth(30);
        jListUnits.getColumnModel().getColumn(0).setMaxWidth(60);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInteractUnit = new javax.swing.JPopupMenu();
        jPUCreateUnit = new javax.swing.JMenuItem();
        jPUUpdateUnit = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListUnits = new javax.swing.JTable();
        jBoxUnit = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jNameU = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jAddUnit = new javax.swing.JButton();
        jUpdateUnit = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSearchUnits = new javax.swing.JTextField();

        jPUCreateUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-icon16.png"))); // NOI18N
        jPUCreateUnit.setText("Thêm");
        jPUCreateUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPUCreateUnitActionPerformed(evt);
            }
        });
        jInteractUnit.add(jPUCreateUnit);

        jPUUpdateUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon-16.png"))); // NOI18N
        jPUUpdateUnit.setText("Sửa");
        jPUUpdateUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPUUpdateUnitActionPerformed(evt);
            }
        });
        jInteractUnit.add(jPUUpdateUnit);

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đơn Vị", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        setPreferredSize(new java.awt.Dimension(725, 312));

        jListUnits.setModel(new javax.swing.table.DefaultTableModel(
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
        jListUnits.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListUnitsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jListUnits);

        jBoxUnit.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin Đơn Vị của Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel2.setText("Tên đơn vị");

        jAddUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-1-icon.png"))); // NOI18N
        jAddUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAddUnitActionPerformed(evt);
            }
        });
        jPanel3.add(jAddUnit);

        jUpdateUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon.png"))); // NOI18N
        jUpdateUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateUnitActionPerformed(evt);
            }
        });
        jPanel3.add(jUpdateUnit);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jNameU)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNameU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jBoxUnitLayout = new javax.swing.GroupLayout(jBoxUnit);
        jBoxUnit.setLayout(jBoxUnitLayout);
        jBoxUnitLayout.setHorizontalGroup(
            jBoxUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jBoxUnitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxUnitLayout.setVerticalGroup(
            jBoxUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxUnitLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBoxUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
            .addComponent(jBoxUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Tìm kiếm");

        jSearchUnits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSearchUnitsActionPerformed(evt);
            }
        });
        jSearchUnits.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSearchUnitsKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jSearchUnitsKeyReleased(evt);
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
                .addComponent(jSearchUnits, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(355, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSearchUnits, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jSearchUnitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSearchUnitsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSearchUnitsActionPerformed

    private void jAddUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAddUnitActionPerformed
        String nameUnit = jNameU.getText();
        UD.createUnit(new Units(nameUnit, ""));
        defaultInterface();

    }//GEN-LAST:event_jAddUnitActionPerformed
    int idUnitSelected;
//    hiển thị popup tương tác

    private void showPopupUnit() {
        jListUnits.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractUnit.show(e.getComponent(), e.getX(), e.getY());
                        idUnitSelected = UD.getAll().get(jListUnits.getSelectedRow()).getId();

                    }
                }
            }

        });
    }
    private void jListUnitsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListUnitsMouseClicked

    }//GEN-LAST:event_jListUnitsMouseClicked

    private void jPUCreateUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPUCreateUnitActionPerformed
        jAddUnit.setVisible(true);
        jUpdateUnit.setVisible(false);
        jBoxUnit.setVisible(true);
        jNameU.setText("");
        jNameU.requestFocus(true);
    }//GEN-LAST:event_jPUCreateUnitActionPerformed

    private void jPUUpdateUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPUUpdateUnitActionPerformed
        jBoxUnit.setVisible(true);
        jAddUnit.setVisible(false);
        jUpdateUnit.setVisible(true);
        Units u = new Units();
        try {
            u = UD.getAll().get(idUnitSelected);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        jNameU.setText(u.getName());
        jNameU.requestFocus(true);
    }//GEN-LAST:event_jPUUpdateUnitActionPerformed

    private void jUpdateUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateUnitActionPerformed
        String nameUnitUpdate = jNameU.getText();
        UD.updateUnit(new Units(idUnitSelected, nameUnitUpdate, ""));
        defaultInterface();
    }//GEN-LAST:event_jUpdateUnitActionPerformed

    private void jSearchUnitsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSearchUnitsKeyPressed


    }//GEN-LAST:event_jSearchUnitsKeyPressed

    private void jSearchUnitsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSearchUnitsKeyReleased
        String search = jSearchUnits.getText();
        if (search.equalsIgnoreCase("")) {
            addTableListU(UD.getAll());
        } else {
            addTableListU(UD.searchUnit(search));
        }
    }//GEN-LAST:event_jSearchUnitsKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAddUnit;
    private javax.swing.JPanel jBoxUnit;
    private javax.swing.JPopupMenu jInteractUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTable jListUnits;
    private javax.swing.JTextField jNameU;
    private javax.swing.JMenuItem jPUCreateUnit;
    private javax.swing.JMenuItem jPUUpdateUnit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jSearchUnits;
    private javax.swing.JButton jUpdateUnit;
    // End of variables declaration//GEN-END:variables
}