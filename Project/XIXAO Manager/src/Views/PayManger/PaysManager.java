/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.PayManger;

import Controllers.DAO.CoutersDAO;
import Controllers.DAO.EmployeesDAO;
import Controllers.DAO.GroupsPerDAO;
import Controllers.DAO.OrderDetailDAO;
import Controllers.DAO.OrdersDAO;
import Controllers.DAO.ProductDAO;
import Controllers.DAO.UnitDAO;
import Emtitys.Couters;
import Emtitys.Employers;
import Emtitys.OrderDetails;
import Emtitys.Orders;
import Emtitys.Products;
import Emtitys.Units;
import Views.MethodCommon;
import Views.ProductsManager.ListPro;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.SpreadsheetInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class PaysManager extends javax.swing.JFrame {

    /**
     * Creates new form Pays
     */
    Connection con;
    ProductDAO PD;
    UnitDAO UD;
    OrdersDAO O;
    OrderDetailDAO OD;
    CoutersDAO CD;
    EmployeesDAO ED;
    Employers em;
    GroupsPerDAO GPD;

    public PaysManager(Connection c, Employers em) {
        this.con = c;
        this.em = em;
        PD = new ProductDAO(c);
        UD = new UnitDAO(c);
        O = new OrdersDAO(c);
        OD = new OrderDetailDAO(c);
        CD = new CoutersDAO(c);
        ED = new EmployeesDAO(c);
        GPD = new GroupsPerDAO(c);

        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(PaysManager.DISPOSE_ON_CLOSE);
        new MethodCommon(getClass(), this, "icon-logo-X-green16.png");

        addComoboBoxFillOrders();
        setDefaultInterface();
        setlayerOrder(jListOrder);
        addTableOrderByEm();
        showInterractOrderDetail();
        addTableCouter();
        showPopupOrders();

        RolesViews();
        RolesActions();
    }
    Locale locale = new Locale("vi", "VN");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
//    phân quyền
//    Giao diện

    private void RolesViews() {
        jBoxPays.remove(jTabPays);
        jBoxPays.remove(jTabCouters);
        jBoxPays.remove(jTabAllOrders);
        if (em.getStatus() == 2) {
            jBoxPays.addTab("Thanh Toán Đơn", jTabPays);
            jBoxPays.addTab("Quầy Thanh Toán", jTabCouters);
            jBoxPays.addTab("Tất Cả Đơn Hàng", jTabAllOrders);
        } else if (em.getStatus() == 0 || em.getStatus() == 1) {
            List<String> listViewsOfEm = GPD.listPerActByEm(em.getId());

            for (String ViewsOfEm : listViewsOfEm) {
                switch (ViewsOfEm) {
                    case "Pay":
                        jBoxPays.addTab("Thanh Toán Đơn", jTabPays);
                        break;
                    case "Couter":
                        jBoxPays.addTab("Quầy Thanh Toán", jTabCouters);
                        break;
                    case "Order":
                        jBoxPays.addTab("Tất Cả Đơn Hàng", jTabAllOrders);
                        break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống xin thử lại sau !");
        }
    }
//   Chức năng

    private void RolesActions() {
//        thanh toán đơn 
        jCreatePays.setVisible(false);
        jListPays.setVisible(false);
        jDetailOrderByEm.setVisible(false);
//      Quầy thanh toán
        jCreateCouterPopup.setVisible(false);
        jUpdateCouter.setVisible(false);
        jEmOfCouter.setVisible(false);
        jChangeSTTCouter.setVisible(false);
//      tất cả đơn hàng
        jPrintListAllOrder.setVisible(false);
        if (em.getStatus() == 2) {
//          thanh toán đơn 
            jCreatePays.setVisible(true);
            jListPays.setVisible(true);
            jDetailOrderByEm.setVisible(true);
//          Quầy thanh toán
            jCreateCouterPopup.setVisible(true);
            jUpdateCouter.setVisible(true);
            jEmOfCouter.setVisible(true);
            jChangeSTTCouter.setVisible(true);
//          tất cả đơn hàng
            jPrintListAllOrder.setVisible(true);
        } else {
            List<String> listPAByEm = GPD.listPerActByEm(em.getId());
            for (String PAByEm : listPAByEm) {
                switch (PAByEm) {
                    case "PAY-1":
                        jCreatePays.setVisible(true);
                        break;
                    case "PAY-2":
                        jListPays.setVisible(true);
                        break;
                    case "C-1":
                        jCreateCouterPopup.setVisible(true);
                        break;
                    case "C-2":
                        jUpdateCouter.setVisible(true);
                        break;
                    case "C-4":
                        jEmOfCouter.setVisible(true);
                        break;
                    case "C-5":
                        jChangeSTTCouter.setVisible(true);
                        break;
                    case "O-2":
                        jPrintListAllOrder.setVisible(true);
                        break;

                }

            }
        }

    }
//    

    private void setDefaultInterface() {
        jBoxInforProSearched.setVisible(false);
        addTableOrderPro();
        jBoxTableEmOfCouter.setVisible(false);
        jSaveCouter.setVisible(false);
        jUpdateOD.setVisible(false);
        jCreateOD.setVisible(true);
        addTableOrders();
        showOrderByEm();
    }
//

    private void setlayerOrder(JPanel panel) {
        jLayerOrders.removeAll();
        jLayerOrders.add(panel);
        jLayerOrders.repaint();
        jLayerOrders.revalidate();

    }
//

    private void addComoboBoxFillOrders() {
        jComboBoxFillOrder.removeAllItems();
        jComboBoxFillOrder.addItem("Tất cả");
        jComboBoxFillOrder.addItem("Đơn hàng trong ngày");
        jComboBoxFillOrder.addItem("Đơn hàng trong 1 tuần");
        jComboBoxFillOrder.addItem("Đơn hàng trong 1 tháng");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton5 = new javax.swing.JButton();
        jInteractOD = new javax.swing.JPopupMenu();
        jChangeProOrder = new javax.swing.JMenuItem();
        jDeleteProOrder = new javax.swing.JMenuItem();
        jInteractCouter = new javax.swing.JPopupMenu();
        jCreateCouterPopup = new javax.swing.JMenuItem();
        jUpdateCouter = new javax.swing.JMenuItem();
        jChangeSTTCouter = new javax.swing.JMenuItem();
        jEmOfCouter = new javax.swing.JMenuItem();
        jInteractOrders = new javax.swing.JPopupMenu();
        jShowDetailOrder = new javax.swing.JMenuItem();
        jInteractOrdersByEm = new javax.swing.JPopupMenu();
        jDetailOrderByEm = new javax.swing.JMenuItem();
        jBoxPays = new javax.swing.JTabbedPane();
        jTabPays = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jCreatePays = new javax.swing.JButton();
        jListPays = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLayerOrders = new javax.swing.JLayeredPane();
        jListOrder = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableOrderOfEm = new javax.swing.JTable();
        jPanel29 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jComboBoxFillOrder = new javax.swing.JComboBox();
        jDashboardOrders = new javax.swing.JPanel();
        jInforOrder = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jMoneyInput = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTotalMoney = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jDiscount = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jNumberTicket = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jDateOutput = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSearchPro = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jErrorSearch = new javax.swing.JLabel();
        jBoxInforProSearched = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jNamePro = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jCodePro = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jUnitPro = new javax.swing.JLabel();
        jNumberUnit = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPricePro = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSalePro = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jCreateOD = new javax.swing.JButton();
        jUpdateOD = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTabCouters = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCouters = new javax.swing.JTable();
        jBoxTableEmOfCouter = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableEmOfCouter = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jNameCouter = new javax.swing.JTextField();
        jStatusCouter = new javax.swing.JCheckBox();
        jPanel35 = new javax.swing.JPanel();
        jCreateCouter = new javax.swing.JButton();
        jSaveCouter = new javax.swing.JButton();
        jTabAllOrders = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTableOrders = new javax.swing.JTable();
        jPanel47 = new javax.swing.JPanel();
        jPrintListAllOrder = new javax.swing.JButton();

        jButton5.setText("jButton5");

        jChangeProOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon-16.png"))); // NOI18N
        jChangeProOrder.setText("Sửa");
        jChangeProOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChangeProOrderActionPerformed(evt);
            }
        });
        jInteractOD.add(jChangeProOrder);

        jDeleteProOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/Close-icon16.png"))); // NOI18N
        jDeleteProOrder.setText("Xóa");
        jDeleteProOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteProOrderActionPerformed(evt);
            }
        });
        jInteractOD.add(jDeleteProOrder);

        jCreateCouterPopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-icon16.png"))); // NOI18N
        jCreateCouterPopup.setText("Thêm Mới");
        jCreateCouterPopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateCouterPopupActionPerformed(evt);
            }
        });
        jInteractCouter.add(jCreateCouterPopup);

        jUpdateCouter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon-16.png"))); // NOI18N
        jUpdateCouter.setText("Cập nhật");
        jUpdateCouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateCouterActionPerformed(evt);
            }
        });
        jInteractCouter.add(jUpdateCouter);

        jChangeSTTCouter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon-16.png"))); // NOI18N
        jChangeSTTCouter.setText("abc");
        jChangeSTTCouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChangeSTTCouterActionPerformed(evt);
            }
        });
        jInteractCouter.add(jChangeSTTCouter);

        jEmOfCouter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/users-icon.png"))); // NOI18N
        jEmOfCouter.setText("Xem nhân viên");
        jEmOfCouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEmOfCouterActionPerformed(evt);
            }
        });
        jInteractCouter.add(jEmOfCouter);

        jShowDetailOrder.setText("Xem chi tiết");
        jShowDetailOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jShowDetailOrderActionPerformed(evt);
            }
        });
        jInteractOrders.add(jShowDetailOrder);

        jDetailOrderByEm.setText("Xem chi tiết");
        jDetailOrderByEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDetailOrderByEmActionPerformed(evt);
            }
        });
        jInteractOrdersByEm.add(jDetailOrderByEm);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Danh mục quản lý Bán Hàng");

        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);

        jCreatePays.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-1-icon.png"))); // NOI18N
        jCreatePays.setText("Thanh toán");
        jCreatePays.setFocusable(false);
        jCreatePays.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCreatePays.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCreatePays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreatePaysActionPerformed(evt);
            }
        });
        jToolBar1.add(jCreatePays);

        jListPays.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/view-list-pay-icon.png"))); // NOI18N
        jListPays.setText("Danh sách");
        jListPays.setFocusable(false);
        jListPays.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jListPays.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jListPays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jListPaysActionPerformed(evt);
            }
        });
        jToolBar1.add(jListPays);

        jLayerOrders.setLayout(new java.awt.CardLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dánh sách đơn hàng"));

        jTableOrderOfEm.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableOrderOfEm);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel16.setText("Lọc");

        jComboBoxFillOrder.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBoxFillOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(535, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jComboBoxFillOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jListOrderLayout = new javax.swing.GroupLayout(jListOrder);
        jListOrder.setLayout(jListOrderLayout);
        jListOrderLayout.setHorizontalGroup(
            jListOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jListOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jListOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jListOrderLayout.setVerticalGroup(
            jListOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jListOrderLayout.createSequentialGroup()
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );

        jLayerOrders.add(jListOrder, "card3");

        javax.swing.GroupLayout jDashboardOrdersLayout = new javax.swing.GroupLayout(jDashboardOrders);
        jDashboardOrders.setLayout(jDashboardOrdersLayout);
        jDashboardOrdersLayout.setHorizontalGroup(
            jDashboardOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jDashboardOrdersLayout.setVerticalGroup(
            jDashboardOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLayerOrders.add(jDashboardOrders, "card4");

        jInforOrder.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin đơn hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel8.setText("Tiền nhận");

        jMoneyInput.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMoneyInput.setForeground(new java.awt.Color(0, 102, 102));

        jLabel9.setText("VND");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jMoneyInput, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jMoneyInput)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel12.setText("Tổng hóa đơn");

        jLabel13.setText("VND");

        jTotalMoney.setBackground(new java.awt.Color(204, 102, 255));
        jTotalMoney.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTotalMoney.setForeground(new java.awt.Color(0, 153, 0));

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTotalMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel14.setText("Tổng chiết khấu");

        jLabel15.setText("VND");

        jDiscount.setBackground(new java.awt.Color(204, 102, 255));
        jDiscount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jDiscount.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jButton8.setText("Hủy");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/print-icon.png"))); // NOI18N
        jButton9.setText("Thanh toán");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setText("Số  Phiếu");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNumberTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jNumberTicket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jLabel2.setText("Ngày xuất");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDateOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );

        jLabel4.setText("Nhập mã hàng");

        jButtonSearch.setText("Tìm");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jErrorSearch.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSearchPro, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonSearch)
                .addGap(18, 18, 18)
                .addComponent(jErrorSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addComponent(jSearchPro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButtonSearch)
                .addComponent(jErrorSearch))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBoxInforProSearched.setBorder(javax.swing.BorderFactory.createTitledBorder("Sản phẩm"));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setText("Tên");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNamePro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jNamePro, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.setText("Mã");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCodePro, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCodePro, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jNumberUnit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jUnitPro, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jUnitPro, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jNumberUnit)
        );

        jLabel6.setText("Giá");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPricePro, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPricePro, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel7.setText("Khuyến mại");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSalePro, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jSalePro, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCreateOD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-icon16.png"))); // NOI18N
        jCreateOD.setText("Thêm vào rỏ");
        jCreateOD.setMaximumSize(new java.awt.Dimension(150, 23));
        jCreateOD.setMinimumSize(new java.awt.Dimension(150, 23));
        jCreateOD.setPreferredSize(new java.awt.Dimension(150, 23));
        jCreateOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateODActionPerformed(evt);
            }
        });
        jPanel22.add(jCreateOD);

        jUpdateOD.setText("Lưu");
        jUpdateOD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateODActionPerformed(evt);
            }
        });
        jPanel22.add(jUpdateOD);

        javax.swing.GroupLayout jBoxInforProSearchedLayout = new javax.swing.GroupLayout(jBoxInforProSearched);
        jBoxInforProSearched.setLayout(jBoxInforProSearchedLayout);
        jBoxInforProSearchedLayout.setHorizontalGroup(
            jBoxInforProSearchedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxInforProSearchedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxInforProSearchedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jBoxInforProSearchedLayout.createSequentialGroup()
                        .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(79, 79, 79)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxInforProSearchedLayout.setVerticalGroup(
            jBoxInforProSearchedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxInforProSearchedLayout.createSequentialGroup()
                .addGroup(jBoxInforProSearchedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jBoxInforProSearchedLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)))
                .addGap(30, 30, 30))
        );

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable2);

        javax.swing.GroupLayout jInforOrderLayout = new javax.swing.GroupLayout(jInforOrder);
        jInforOrder.setLayout(jInforOrderLayout);
        jInforOrderLayout.setHorizontalGroup(
            jInforOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBoxInforProSearched, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jInforOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInforOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jInforOrderLayout.setVerticalGroup(
            jInforOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInforOrderLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBoxInforProSearched, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLayerOrders.add(jInforOrder, "card2");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayerOrders)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayerOrders)
        );

        javax.swing.GroupLayout jTabPaysLayout = new javax.swing.GroupLayout(jTabPays);
        jTabPays.setLayout(jTabPaysLayout);
        jTabPaysLayout.setHorizontalGroup(
            jTabPaysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jTabPaysLayout.setVerticalGroup(
            jTabPaysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jTabPaysLayout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBoxPays.addTab("Thanh toán đơn", jTabPays);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dánh sách quầy thu ngân", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jTableCouters.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCouters.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCoutersMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableCouters);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBoxTableEmOfCouter.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách nhân viên"));

        jTableEmOfCouter.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTableEmOfCouter);

        javax.swing.GroupLayout jBoxTableEmOfCouterLayout = new javax.swing.GroupLayout(jBoxTableEmOfCouter);
        jBoxTableEmOfCouter.setLayout(jBoxTableEmOfCouterLayout);
        jBoxTableEmOfCouterLayout.setHorizontalGroup(
            jBoxTableEmOfCouterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxTableEmOfCouterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jBoxTableEmOfCouterLayout.setVerticalGroup(
            jBoxTableEmOfCouterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxTableEmOfCouterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin Quầy"));

        jLabel27.setText("Tên Quầy");

        jStatusCouter.setText("Hoạt động");

        jCreateCouter.setText("Thêm");
        jCreateCouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateCouterActionPerformed(evt);
            }
        });
        jPanel35.add(jCreateCouter);

        jSaveCouter.setText("Lưu");
        jSaveCouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaveCouterActionPerformed(evt);
            }
        });
        jPanel35.add(jSaveCouter);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jNameCouter)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jStatusCouter))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel35, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNameCouter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jStatusCouter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBoxTableEmOfCouter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBoxTableEmOfCouter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jTabCoutersLayout = new javax.swing.GroupLayout(jTabCouters);
        jTabCouters.setLayout(jTabCoutersLayout);
        jTabCoutersLayout.setHorizontalGroup(
            jTabCoutersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jTabCoutersLayout.setVerticalGroup(
            jTabCoutersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jBoxPays.addTab("Quầy thanh toán", jTabCouters);

        jTableOrders.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTableOrders);

        jPrintListAllOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/print-icon.png"))); // NOI18N
        jPrintListAllOrder.setText("In");
        jPrintListAllOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrintListAllOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPrintListAllOrder)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPrintListAllOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jTabAllOrdersLayout = new javax.swing.GroupLayout(jTabAllOrders);
        jTabAllOrders.setLayout(jTabAllOrdersLayout);
        jTabAllOrdersLayout.setHorizontalGroup(
            jTabAllOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jTabAllOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jTabAllOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE)
                    .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jTabAllOrdersLayout.setVerticalGroup(
            jTabAllOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jTabAllOrdersLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                .addContainerGap())
        );

        jBoxPays.addTab("Danh sách đơn hàng", jTabAllOrders);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBoxPays)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jBoxPays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//  Hiển thị sản phẩm 
    private void showProSearched(int row, int numberunit) {
        jNumberUnit.setText(String.valueOf(numberunit));
        Products p = PD.getAll().get(row);
        List<Units> listUnit = UD.getAll();
        for (Units listUnit1 : listUnit) {
            if (listUnit1.getId() == p.getId_unit()) {
                jUnitPro.setText(listUnit1.getName());
            }
        }
        jNamePro.setText(p.getName());
        jCodePro.setText(String.valueOf(p.getCode()));
        jPricePro.setText(String.valueOf(currencyFormatter.format(p.getPrice())));
        jSalePro.setText(String.valueOf(currencyFormatter.format(p.getSale())));

    }

//  phương thức tính tổng tiền của sản phẩm
    private float calculateTotalPrice(int id_order) {
        List<OrderDetails> listOD = OD.listOD(id_order);
        float total = 0;
        for (OrderDetails l1 : listOD) {
            Products ProOfOrder = PD.selectProById(l1.getId_pro());
            total = ProOfOrder.getPrice() - (ProOfOrder.getSale() * l1.getQuantity());
        }
        return total;
    }
//  phương thức tính tổng chiết khấu của sản phẩm

    private float calculateTotalSale(int id_order) {
        List<OrderDetails> listOD = OD.listOD(id_order);
        float total = 0;
        for (OrderDetails l1 : listOD) {
            Products ProOfOrder = PD.selectProById(l1.getId_pro());
            total = ProOfOrder.getSale() * l1.getQuantity();
        }
        return total;
    }

//  hiển thị danh sách đơn hàng theo nhân viên
    private void addTableOrderByEm() {
        List<Orders> listOrderOfEm = O.getAllByEm(em.getId());
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("#");
        dtm.addColumn("Mã Phiếu");
        dtm.addColumn("Thời Gian Xuất");
        dtm.addColumn("Chiết Khấu");
        dtm.addColumn("Tổng Tiền");

        for (int i = 0; i < listOrderOfEm.size(); i++) {
            Orders get = listOrderOfEm.get(i);
            List<OrderDetails> listOD = OD.listOD(get.getId());

            Vector v = new Vector();
            v.add(i + 1);
            v.add(get.getCode());
            v.add(get.getDate_created());
            v.add(currencyFormatter.format(calculateTotalSale(get.getId())));
            v.add(currencyFormatter.format(calculateTotalPrice(get.getId())));
            dtm.addRow(v);
        }
        jTableOrderOfEm.setModel(dtm);
    }
//  tìm sản phẩm 
    int row_pro_search = 0;
    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        List<Products> listPro = PD.getAll();
        String codeSearch = jSearchPro.getText();

        int count = 0;
        for (int i = 0; i < listPro.size(); i++) {
            Products listPro1 = listPro.get(i);
            if (codeSearch.equalsIgnoreCase(String.valueOf(listPro1.getCode()))) {
                jBoxInforProSearched.setVisible(true);
                showProSearched(i, 0);
                row_pro_search = i;
                break;
            } else {
                count++;
            }
        }
        if (count == listPro.size()) {
            jErrorSearch.setText("Mã sản phẩm không tồn tại xin nhập lại !");
        }
    }//GEN-LAST:event_jButtonSearchActionPerformed
//  tạo mới order
    int numberTicket;
    int id_order = 0;
    private void jCreatePaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreatePaysActionPerformed
        jCreateOD.setVisible(true);
        setlayerOrder(jInforOrder);
        numberTicket = (int) (Math.random() * (999999999 - 111111111));

        Orders o = new Orders(em.getId(), 1, numberTicket);
        Date date = new Date();
        jNumberTicket.setText(String.valueOf(numberTicket));
        jDateOutput.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        O.createOrder(o, em.getId());
//        lấy id order vừa tạo và dòng list
        List<Orders> ListO = O.getAll();
        for (int i = 0; i < ListO.size(); i++) {
            Orders ListO1 = ListO.get(i);
            if (numberTicket == ListO1.getCode()) {
                id_order = ListO1.getId();
            }
        }
    }//GEN-LAST:event_jCreatePaysActionPerformed

    private void jListPaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jListPaysActionPerformed
        setlayerOrder(jListOrder);
        addTableOrderByEm();
    }//GEN-LAST:event_jListPaysActionPerformed

    int quantity = 0;
//  thêm sp vào orderDetail
    private void jCreateODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateODActionPerformed
        String rg = "^([0-9])+$";
        Pattern pt = Pattern.compile(rg);
        Matcher matcher = pt.matcher(jNumberUnit.getText());
//        kiểm tra định dạng
        if (matcher.find()) {
            quantity = Integer.parseInt(jNumberUnit.getText());
//        số lượng > 0
            if (quantity == 0) {
                JOptionPane.showMessageDialog(rootPane, "Giá trị đơn vị phải lớn hơn 0 !");
            } else {
                Products pro = PD.getAll().get(row_pro_search);
//          kiểm tra số lượng trong kho
                if (pro.getQuantity() < quantity) {
                    JOptionPane.showMessageDialog(rootPane, "Giá trị vượt mức số lượng có trong kho ! xin nhập lại !");
                } else {
//              
                    //thêm mới
                    OrderDetails od = new OrderDetails(id_order, pro.getId(), quantity);
                    OD.createOD(od);
                    addTableOrderPro();
//        
//              xét tổng giá trị hóa đơn
                    jTotalMoney.setText(String.valueOf(currencyFormatter.format(totalOrder)));
                    jDiscount.setText(String.valueOf(currencyFormatter.format(discount)));
//              Giản số lượng sản phẩm trong kho
                    PD.changeQuantityPro(pro.getId(), (pro.getQuantity() - quantity));
                }

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Giá trị đơn vị không đúng định dạng !");
        }


    }//GEN-LAST:event_jCreateODActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {
            jInteractOD.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable2MouseClicked
// xóa phiếu
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        int conflim = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn xóa phiếu mua hàng này ?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (conflim == 0) {
            O.deleteOrder(id_order);
        }
        setlayerOrder(jListOrder);
    }//GEN-LAST:event_jButton8ActionPerformed
// xóa sp đã chọn  trong đơn hàng
    private void jDeleteProOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteProOrderActionPerformed
        OrderDetails o = new OrderDetails(id_order, id_pro_selected, 1);
        OD.deleteOD(o);
    }//GEN-LAST:event_jDeleteProOrderActionPerformed
//thay đổi thông tin sp đã chọn trong đơn hàng
    int numberUnitOld = 0;
    private void jChangeProOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChangeProOrderActionPerformed
        jUpdateOD.setVisible(true);
        jCreateOD.setVisible(false);
        OrderDetails o = new OrderDetails(id_order, id_pro_selected, 1);
        numberUnitOld = (int) (jTable2.getValueAt(jTable2.getSelectedRow(), 4));
        showProSearched(row_pro_selected, numberUnitOld);

    }//GEN-LAST:event_jChangeProOrderActionPerformed
// cập nhật sản phẩn trong danh sách đơn hàng
    private void jUpdateODActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateODActionPerformed
        String rg = "^([0-9])+$";
        Pattern pt = Pattern.compile(rg);
        Matcher matcher = pt.matcher(jNumberUnit.getText());
//      kiếm tra định dạng số lượng đơn vị
        if (matcher.find()) {
            quantity = Integer.parseInt(jNumberUnit.getText());

//      số lượng > 0
            if (quantity == 0) {
                JOptionPane.showMessageDialog(rootPane, "Giá trị đơn vị phải lớn hơn 0 !");
            } else {
                Products pro = PD.getAll().get(row_pro_selected);
//          kiểm tra số lượng trong kho
                if ((pro.getQuantity() + numberUnitOld) < quantity) {
                    JOptionPane.showMessageDialog(rootPane, "Giá trị vượt mức số lượng có trong kho ! xin nhập lại !");
                } else {
//              cập nhật đơn hàng
                    OrderDetails od = new OrderDetails(id_order, id_pro_selected, quantity);
                    System.out.println(id_pro_selected + "/" + id_order);
                    OD.updateOD(od);
                    addTableOrderPro();
//        
//              xét giá trị hóa đơn
                    jTotalMoney.setText(String.valueOf(totalOrder));
                    jDiscount.setText(String.valueOf(discount));
//              Giản số lượng sản phẩm trong kho
                    PD.changeQuantityPro(id_pro_selected, (pro.getQuantity() + numberUnitOld) - quantity);
                }

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Giá trị đơn vị không đúng định dạng !");
        }
    }//GEN-LAST:event_jUpdateODActionPerformed
//  Hiển thị popup interact couter
    int idCouterPopup = 0;
    int rowSelectedCouter = 0;
    int statusCouterPopup = 0;
    private void jTableCoutersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCoutersMouseClicked

        jTableCouters.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = jTableCouters.rowAtPoint(e.getPoint());
                if (r >= 0 && r < jTableCouters.getRowCount()) {
                    jTableCouters.setRowSelectionInterval(r, r);
                } else {
                    jTableCouters.clearSelection();
                }

                int rowindex = jTableCouters.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jInteractCouter.show(e.getComponent(), e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractCouter.show(e.getComponent(), e.getX(), e.getY());
                    }
                    rowSelectedCouter = jTableCouters.getSelectedRow();
                    Couters c = CD.getAll().get(jTableCouters.getSelectedRow());
                    idCouterPopup = c.getId();
                    statusCouterPopup = c.getStatus();
                    if (c.getStatus() == 1) {
                        setPopupInteract(1);
                    } else {
                        setPopupInteract(0);
                    }

                }
            }
        });
    }//GEN-LAST:event_jTableCoutersMouseClicked
//thêm mới quầy
    private void jCreateCouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateCouterActionPerformed
        String nameCouter = jNameCouter.getText();
        Couters c = new Couters(1, nameCouter, "");
        CD.createCouter(c);
        addTableCouter();

    }//GEN-LAST:event_jCreateCouterActionPerformed
//cập nhật quầy
    private void jUpdateCouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateCouterActionPerformed
        Couters c = CD.getAll().get(rowSelectedCouter);
        jStatusCouter.setVisible(true);
        jSaveCouter.setVisible(true);
        jCreateCouter.setVisible(false);
        jNameCouter.setText(c.getName());
        if (c.getStatus() == 1) {
            jStatusCouter.setSelected(true);
        } else {
            jStatusCouter.setSelected(false);
        }
        addTableCouter();

    }//GEN-LAST:event_jUpdateCouterActionPerformed
//cập nhật quầy
    private void jSaveCouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveCouterActionPerformed
        int status = 0;
        if (jStatusCouter.isSelected()) {
            status = 1;
        } else {
            status = 0;
        }
        Couters c = new Couters(CD.getAll().get(rowSelectedCouter).getId(), status, jNameCouter.getText(), "");
        CD.updateCouter(c);
        addTableCouter();
    }//GEN-LAST:event_jSaveCouterActionPerformed
// thay đổi trạng thái của quầy
    private void jChangeSTTCouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChangeSTTCouterActionPerformed

        int status = 0;
        String message = "";
        if (statusCouterPopup == 0) {
            status = 1;
            message = "Bạn có chắc muốn Hiện Quầy này !";
        } else {
            status = 0;
            message = "Bạn có chắc muốn Ẩn Quầy này !";
        }
        int check = JOptionPane.showConfirmDialog(rootPane, message, "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (check == 0) {
            CD.changeSTTCouter(idCouterPopup, status);
        }
        addTableCouter();

    }//GEN-LAST:event_jChangeSTTCouterActionPerformed
// hiển thị nhân viên thuộc quầy thanh toán
    private void jEmOfCouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEmOfCouterActionPerformed
        List<Employers> listEmOfCouter = CD.showEmByCouter(idCouterPopup);
        jBoxTableEmOfCouter.setVisible(true);
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("#");
        dtm.addColumn("Nhân viên");
        jTableEmOfCouter.getColumnModel().getColumn(0).setWidth(1);
        jTableEmOfCouter.getColumnModel().getColumn(0).setPreferredWidth(1);
        jTableEmOfCouter.getColumnModel().getColumn(0).setMaxWidth(3);

        for (int i = 0; i < listEmOfCouter.size(); i++) {
            Employers em = listEmOfCouter.get(i);
            Vector v = new Vector();
            v.add(i + 1);
            v.add(em.getName());
            dtm.addRow(v);
        }
        jTableEmOfCouter.setModel(dtm);
    }//GEN-LAST:event_jEmOfCouterActionPerformed
    int idOrderOfEm = 0;
// lấy đơn hàng trong danh sách của nhân viên

    private void showOrderByEm() {
        //      bắt sự kiện
        jTableOrderOfEm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = jTableOrderOfEm.rowAtPoint(e.getPoint());
                if (r >= 0 && r < jTableOrderOfEm.getRowCount()) {
                    jTableOrderOfEm.setRowSelectionInterval(r, r);
                } else {
                    jTableOrderOfEm.clearSelection();
                }

                idOrderOfEm = O.getAll().get(jTableOrderOfEm.getSelectedRow()).getId();

                if (row_pro_selected < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jInteractOrdersByEm.show(e.getComponent(), e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractOrdersByEm.show(e.getComponent(), e.getX(), e.getY());
                    }

                }
            }
        });
    }
//hiển thị chi tiêt đơn hàng
    private void jShowDetailOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jShowDetailOrderActionPerformed

        OrderDtail oView = new OrderDtail(id_Orders, con);
        oView.setVisible(true);
    }//GEN-LAST:event_jShowDetailOrderActionPerformed

    private void jDetailOrderByEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDetailOrderByEmActionPerformed
        OrderDtail oView = new OrderDtail(idOrderOfEm, con);
        oView.setVisible(true);
    }//GEN-LAST:event_jDetailOrderByEmActionPerformed

    private void jCreateCouterPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateCouterPopupActionPerformed
        jNameCouter.setText("");
        jStatusCouter.setSelected(false);
        jCreateCouter.setVisible(true);
        jSaveCouter.setVisible(false);
    }//GEN-LAST:event_jCreateCouterPopupActionPerformed

    private void jPrintListAllOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPrintListAllOrderActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        File file = new File("E:/");
        fileChooser.setSelectedFile(file);
        fileChooser.showSaveDialog(jPrintListAllOrder);
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

                List<Products> listPros = PD.getAll();
                excel.save(pathNew);
                int count = 0;
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
    }//GEN-LAST:event_jPrintListAllOrderActionPerformed
    private void setPopupInteract(int status) {
        if (status == 1) {
            jChangeSTTCouter.setIcon(new ImageIcon(ListPro.class.getResource("/Commons/img/Eye-Invisible-icon.png")));
            jChangeSTTCouter.setText("Ẩn");
        } else {
            jChangeSTTCouter.setIcon(new ImageIcon(ListPro.class.getResource("/Commons/img/Eye-Visible-icon.png")));
            jChangeSTTCouter.setText("Hiện");
        }

    }
//
    float discount = 0;
    float totalOrder = 0;
// hiển thị dữ liệu lên bảng chi tiết đơn hàng theo order

    private void addTableOrderPro() {
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.addColumn("#");
        dtm.addColumn("Mã Hàng");
        dtm.addColumn("Tên Hàng");
        dtm.addColumn("Đơn Vị");
        dtm.addColumn("Số Lượng");
        dtm.addColumn("Giá");
        dtm.addColumn("Khuyến Mại");
        dtm.addColumn("Chiết Khấu");
        dtm.addColumn("Thành Tiền");
        jTable2.setModel(dtm);

//      lấy danh sách orderDetail theo id_order
        List<OrderDetails> listOD = OD.listOD(id_order);
        int i = 0;
        for (OrderDetails listOD1 : listOD) {
            i++;
//            lấy sản phẩm theo id của orderDteail
            Products p = PD.selectProById(listOD1.getId_pro());
            float moneyPro = (p.getPrice() - p.getSale()) * listOD1.getQuantity();
            float discountPro = p.getSale() * listOD1.getQuantity();
            Vector v = new Vector<>();
            v.add(i);
            v.add(p.getCode());
            v.add(p.getName());
            v.add(p.getDate_crated());
            v.add(listOD1.getQuantity());
            v.add(currencyFormatter.format(p.getPrice()));
            v.add(currencyFormatter.format(p.getSale()));
            v.add(currencyFormatter.format(discountPro));
            v.add(currencyFormatter.format(moneyPro));
            dtm.addRow(v);
//          Tính tổng tiền và tổng chiết khấu
            discount += discountPro;
            totalOrder += moneyPro;
        }
        jTable2.setModel(dtm);
    }

//  show interact - hiển thị thao tác với các sản phẩm trong đơn hàng chi tiết
    int id_pro_selected = 0;
    int row_pro_selected = 0;

    private void showInterractOrderDetail() {
//      bắt sự kiện
        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = jTable2.rowAtPoint(e.getPoint());
                if (r >= 0 && r < jTable2.getRowCount()) {
                    jTable2.setRowSelectionInterval(r, r);
                } else {
                    jTable2.clearSelection();
                }

                row_pro_selected = jTable2.getSelectedRow();
                if (row_pro_selected < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jInteractOD.show(e.getComponent(), e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractOD.show(e.getComponent(), e.getX(), e.getY());
                    }
//                  lấy id_pro từ dòng đc chọn
                    id_pro_selected = OD.listOD(id_order).get(row_pro_selected).getId_pro();

                }
            }
        });
    }
//  quản lý Quầy Thanh toán

    private void addTableCouter() {
        DefaultTableModel dtm = new DefaultTableModel();
        List<Couters> listCouter = CD.getAll();

        dtm.addColumn("#");
        dtm.addColumn("Tên");
        dtm.addColumn("Trạng Thái");
        dtm.addColumn("Ngày Tạo");
        dtm.addColumn("Ngày Cập nhật");

        for (int i = 0; i < listCouter.size(); i++) {
            Couters c = listCouter.get(i);
            String status = "";
            if (c.getStatus() == 1) {
                status = "Hiển thị";
            } else {
                status = "Ẩn";
            }

            Vector v = new Vector<>();
            v.add(i + 1);
            v.add(c.getName());
            v.add(status);
            v.add(c.getDate_created());
            v.add(c.getDate_updated());
            dtm.addRow(v);
        }
        jTableCouters.setModel(dtm);
    }
// hiển thị dữ liệu của tất cả đơn hàng

    private void addTableOrders() {
        DefaultTableModel dtm = new DefaultTableModel();
        List<Orders> listOrders = O.getAll();

        dtm.addColumn("#");
        dtm.addColumn("Mã");
        dtm.addColumn("Nhân viên");
        dtm.addColumn("Chiết khấu");
        dtm.addColumn("Tổng giá");
        dtm.addColumn("Ngày tạo");

        for (int i = 0; i < listOrders.size(); i++) {
            Orders o = listOrders.get(i);
            Employers e = ED.getEmById(o.getId_employee());
            List<OrderDetails> od = OD.listOD(o.getId());
            float totalOrders = 0;
            float discounts = 0;
            for (OrderDetails od1 : od) {
                Products p = PD.selectProById(od1.getId_pro());
                discounts += od1.getQuantity() * p.getSale();
                totalOrders += (p.getPrice() - p.getSale()) * od1.getQuantity();
            }
            Vector v = new Vector();
            v.add(i + 1);
            v.add(o.getCode());
            v.add(e.getName());
            v.add(discounts);
            v.add(totalOrders);
            v.add(o.getDate_created());
            dtm.addRow(v);
        }
        jTableOrders.setModel(dtm);
    }
// hiển thị popupmenu của orders
    int id_Orders = 0;

    private void showPopupOrders() {
//      bắt sự kiện
        jTableOrders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = jTableOrders.rowAtPoint(e.getPoint());
                if (r >= 0 && r < jTableOrders.getRowCount()) {
                    jTableOrders.setRowSelectionInterval(r, r);
                } else {
                    jTableOrders.clearSelection();
                }

                row_pro_selected = jTableOrders.getSelectedRow();
                if (row_pro_selected < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jInteractOrders.show(e.getComponent(), e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractOrders.show(e.getComponent(), e.getX(), e.getY());
                    }
//                  lấy id_order từ dòng đc chọn
                    id_Orders = O.getAll().get(jTableOrders.getSelectedRow()).getId();

                }
            }
        });
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
            java.util.logging.Logger.getLogger(PaysManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaysManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaysManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaysManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JPanel jBoxInforProSearched;
    private javax.swing.JTabbedPane jBoxPays;
    private javax.swing.JPanel jBoxTableEmOfCouter;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JMenuItem jChangeProOrder;
    private javax.swing.JMenuItem jChangeSTTCouter;
    private javax.swing.JLabel jCodePro;
    private javax.swing.JComboBox jComboBoxFillOrder;
    private javax.swing.JButton jCreateCouter;
    private javax.swing.JMenuItem jCreateCouterPopup;
    private javax.swing.JButton jCreateOD;
    private javax.swing.JButton jCreatePays;
    private javax.swing.JPanel jDashboardOrders;
    private javax.swing.JLabel jDateOutput;
    private javax.swing.JMenuItem jDeleteProOrder;
    private javax.swing.JMenuItem jDetailOrderByEm;
    private javax.swing.JLabel jDiscount;
    private javax.swing.JMenuItem jEmOfCouter;
    private javax.swing.JLabel jErrorSearch;
    private javax.swing.JPanel jInforOrder;
    private javax.swing.JPopupMenu jInteractCouter;
    private javax.swing.JPopupMenu jInteractOD;
    private javax.swing.JPopupMenu jInteractOrders;
    private javax.swing.JPopupMenu jInteractOrdersByEm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayerOrders;
    private javax.swing.JPanel jListOrder;
    private javax.swing.JButton jListPays;
    private javax.swing.JTextField jMoneyInput;
    private javax.swing.JTextField jNameCouter;
    private javax.swing.JLabel jNamePro;
    private javax.swing.JLabel jNumberTicket;
    private javax.swing.JTextField jNumberUnit;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel jPricePro;
    private javax.swing.JButton jPrintListAllOrder;
    private javax.swing.JLabel jSalePro;
    private javax.swing.JButton jSaveCouter;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jSearchPro;
    private javax.swing.JMenuItem jShowDetailOrder;
    private javax.swing.JCheckBox jStatusCouter;
    private javax.swing.JPanel jTabAllOrders;
    private javax.swing.JPanel jTabCouters;
    private javax.swing.JPanel jTabPays;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableCouters;
    private javax.swing.JTable jTableEmOfCouter;
    private javax.swing.JTable jTableOrderOfEm;
    private javax.swing.JTable jTableOrders;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel jTotalMoney;
    private javax.swing.JLabel jUnitPro;
    private javax.swing.JMenuItem jUpdateCouter;
    private javax.swing.JButton jUpdateOD;
    // End of variables declaration//GEN-END:variables
}
