/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.ProductsManager;

import Controllers.DAO.GroupsPerDAO;
import Controllers.DAO.PerAccDAO;
import Controllers.DAO.ProductDAO;
import Emtitys.Employers;
import Emtitys.Products;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class ListPro extends javax.swing.JPanel {

    /**
     * Creates new form ListPro
     */
    Connection con;
    ProductDAO PD;
    interactToolbar itf;
    DefaultTableModel dtm;
    GroupsPerDAO GPD;
    Employers em;

    public ListPro(Connection c, ProductsManager PM, Employers em) {
        this.con = c;
        this.em = em;
        PD = new ProductDAO(con);
        GPD = new GroupsPerDAO(c);

        initComponents();
        RolesActions();

        showPopupInteract();
        addComboBox();
        addTableList(PD.getAll());
        setAutoscrolls(true);
        itf = PM;
    }
//  phân quyền 

    private void RolesActions() {
        List<String> listActionsByEm = GPD.listActByEm(em.getId());

        jUpdateProPU.setVisible(false);
        jDeleteProPU.setVisible(false);
        jChangeSTTPU.setVisible(false);
        if (em.getStatus() == 2) {
            jUpdateProPU.setVisible(true);
            jDeleteProPU.setVisible(true);
            jChangeSTTPU.setVisible(true);
        }else{
            for (String ActionsByEm : listActionsByEm) {
                switch (ActionsByEm) {
                    case "P-2":
                        jUpdateProPU.setVisible(true);
                        break;
                    case "P-3":
                        jDeleteProPU.setVisible(true);
                        break;
                    case "P-6":
                        jChangeSTTPU.setVisible(true);
                        break;
                }
            }
        }
    }
//  

    private void addComboBox() {
        jFillterPro.addItem("");
        jFillterPro.addItem("Theo A-Z");
        jFillterPro.addItem("Sản phẩm mới trong ngày");
        jFillterPro.addItem("Sản phẩm mới trong tuần");

    }

//  
    private void addTableList(List<Products> ListPro) {
        dtm = new DefaultTableModel();
        List<Products> listPro = ListPro;
        dtm.addColumn("#");
        dtm.addColumn("Tên");
        dtm.addColumn("Code");
        dtm.addColumn("Danh Mục");
        dtm.addColumn("Giá");
        dtm.addColumn("Khuyến Mãi");
        dtm.addColumn("Mô Tả");
        dtm.addColumn("Số Lượng");
        dtm.addColumn("Ảnh");
        dtm.addColumn("Đơn Vị");
        dtm.addColumn("Trạng Thái");
        dtm.addColumn("Ngày Tạo");
        dtm.addColumn("Ngày Sửa");
        Locale locale = new Locale("vi", "VN");      
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

        
        int i = 0;
        String status = "";
        for (Products l : listPro) {
            Vector v = new Vector<>();
            if (l.getStatus() == 1) {
                status = "Hiện";
            } else {
                status = "Ẩn";
            }
            i++;
            v.add(i);
            v.add(l.getName());
            v.add(l.getCode());
            v.add(l.getId_cat());
            v.add(currencyFormatter.format(l.getPrice()));
            v.add(currencyFormatter.format(l.getSale()));
            v.add(l.getDescript());
            v.add(l.getQuantity());
            v.add(l.getImg());
            v.add(l.getId_unit());
            v.add(status);
            v.add(l.getDate_crated());
            v.add(l.getDate_updated());
            dtm.addRow(v);

        }
        jTableListPro.setModel(dtm);
    }

//  
    private void setPopupInteract(int status) {
        if (status == 1) {
            jChangeSTTPU.setIcon(new ImageIcon(ListPro.class.getResource("/Commons/img/Eye-Invisible-icon.png")));
            jChangeSTTPU.setText("Ẩn");
        } else {
            jChangeSTTPU.setIcon(new ImageIcon(ListPro.class.getResource("/Commons/img/Eye-Visible-icon.png")));
            jChangeSTTPU.setText("Hiện");
        }

    }
//  

    interface interactToolbar {

        public void changeToolbar(int id);

        public void changeInforPopup(int id);

        public void deletePro(int id);

        public void changeSTT(int id, int status);
    }
//
    int idPopup = 0;
    int statusPopup = 0;

    private void showPopupInteract() {
        jTableListPro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = jTableListPro.rowAtPoint(e.getPoint());
                if (r >= 0 && r < jTableListPro.getRowCount()) {
                    jTableListPro.setRowSelectionInterval(r, r);
                } else {
                    jTableListPro.clearSelection();
                }

                int rowindex = jTableListPro.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jInteractPro.show(e.getComponent(), e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractPro.show(e.getComponent(), e.getX(), e.getY());
                    }
                    Products p = PD.getAll().get(jTableListPro.getSelectedRow());
                    idPopup = p.getId();
                    statusPopup = p.getStatus();
                    if (p.getStatus() == 1) {
                        setPopupInteract(1);
                    } else {
                        setPopupInteract(0);
                    }

                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInteractPro = new javax.swing.JPopupMenu();
        jUpdateProPU = new javax.swing.JMenuItem();
        jDeleteProPU = new javax.swing.JMenuItem();
        jChangeSTTPU = new javax.swing.JMenuItem();
        jListPro = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jFillterPro = new javax.swing.JComboBox();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableListPro = new javax.swing.JTable();

        jUpdateProPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon-16.png"))); // NOI18N
        jUpdateProPU.setText("Sửa");
        jUpdateProPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateProPUActionPerformed(evt);
            }
        });
        jInteractPro.add(jUpdateProPU);

        jDeleteProPU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/Close-icon16.png"))); // NOI18N
        jDeleteProPU.setText("Xóa");
        jDeleteProPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteProPUActionPerformed(evt);
            }
        });
        jInteractPro.add(jDeleteProPU);

        jChangeSTTPU.setText("abc");
        jChangeSTTPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChangeSTTPUActionPerformed(evt);
            }
        });
        jInteractPro.add(jChangeSTTPU);

        setBackground(new java.awt.Color(255, 0, 0));
        setAutoscrolls(true);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Lọc");

        jFillterPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFillterProActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFillterPro, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jFillterPro)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách sản phẩm"));

        jTableListPro.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableListPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListProMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTableListProMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableListProMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTableListPro);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jListProLayout = new javax.swing.GroupLayout(jListPro);
        jListPro.setLayout(jListProLayout);
        jListProLayout.setHorizontalGroup(
            jListProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jListProLayout.setVerticalGroup(
            jListProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jListProLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jListPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jListPro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jFillterProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFillterProActionPerformed
        int rowSelectSort = jFillterPro.getSelectedIndex();
        if (rowSelectSort == 1) {
            addTableList(PD.sortToAToZ());
        } else if (rowSelectSort == 2) {
            DateFormat dateSQL = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            addTableList(PD.listProInDay(dateSQL.format(calendar.getTime())));
        }else if(rowSelectSort == 3){
            addTableList(PD.listProOfWeek());
        } else {
            addTableList(PD.getAll());
        }
    }//GEN-LAST:event_jFillterProActionPerformed

    private void jTableListProMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListProMousePressed
        try {
            itf.changeToolbar(PD.getAll().get(jTableListPro.getSelectedRow()).getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_jTableListProMousePressed

    private void jTableListProMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListProMouseClicked


    }//GEN-LAST:event_jTableListProMouseClicked

    private void jTableListProMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListProMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableListProMouseReleased

    private void jUpdateProPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateProPUActionPerformed
        itf.changeToolbar(idPopup);
        itf.changeInforPopup(idPopup);
    }//GEN-LAST:event_jUpdateProPUActionPerformed

    private void jDeleteProPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteProPUActionPerformed
        itf.changeToolbar(idPopup);
        itf.deletePro(idPopup);
    }//GEN-LAST:event_jDeleteProPUActionPerformed

    private void jChangeSTTPUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChangeSTTPUActionPerformed
        itf.changeSTT(idPopup, statusPopup);
    }//GEN-LAST:event_jChangeSTTPUActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jChangeSTTPU;
    private javax.swing.JMenuItem jDeleteProPU;
    private javax.swing.JComboBox jFillterPro;
    private javax.swing.JPopupMenu jInteractPro;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jListPro;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jTableListPro;
    private javax.swing.JMenuItem jUpdateProPU;
    // End of variables declaration//GEN-END:variables

}
