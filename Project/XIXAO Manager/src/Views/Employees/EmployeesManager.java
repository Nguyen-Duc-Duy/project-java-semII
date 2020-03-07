/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.Employees;

import Controllers.DAO.AccountDAO;
import Controllers.DAO.ActionsDAO;
import Controllers.DAO.CoutersDAO;
import Controllers.DAO.EmployeesDAO;
import Controllers.DAO.GroupsPerDAO;
import Controllers.DAO.PerAccDAO;
import Controllers.DAO.ViewsDAO;
import Emtitys.Actions;
import Emtitys.Couters;
import Emtitys.Employers;
import Emtitys.GroupsPers;
import Emtitys.PersActions;
import Emtitys.Products;
import Emtitys.Views;
import Views.MethodCommon;
import Views.ProductsManager.ListPro;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.SpreadsheetInfo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class EmployeesManager extends javax.swing.JFrame {

    /**
     * Creates new form EmployeesManager
     */
    Connection connect;
    GroupsPerDAO GPD;
    ViewsDAO VD;
    ActionsDAO AD;
    EmployeesDAO ED;
    AccountDAO ACCD;
    PerAccDAO PAD;
    Employers em;
    CoutersDAO CD;

    public EmployeesManager(Connection c, Employers em) {
        this.connect = c;
        this.em = em;

        GPD = new GroupsPerDAO(c);
        VD = new ViewsDAO(c);
        AD = new ActionsDAO(c);
        ED = new EmployeesDAO(c);
        ACCD = new AccountDAO(c);
        PAD = new PerAccDAO(c);
        CD = new CoutersDAO(c);

        initComponents();
        setLocationRelativeTo(null);
        new MethodCommon(getClass(), this, "icon-logo-X-green16.png");

        showPerssion();
        defaultBoxInforEm();
        addComboBoxFillEm();
        interfaceDefaultEmployee();

        interfaceDefaultGroupPer();

        setDefaultCloseOperation(EmployeesManager.DISPOSE_ON_CLOSE);

        roleViews();
        roleActions();

    }
//  phân quyền
//      giao diện giữa nhân viên và quyền

    private void roleViews() {
        jBoxTabPanel.remove(jEm);
        jBoxTabPanel.remove(jPer);

        if (em.getStatus() == 2) {
            jBoxTabPanel.addTab("Nhân viên", jEm);
            jBoxTabPanel.addTab("Nhóm quyền", jPer);
        } else {
            List<String> listPAByEm = GPD.listPerActByEm(em.getId());
            for (String PAByEm : listPAByEm) {
                switch (PAByEm) {
                    case "Em":
                        jBoxTabPanel.addTab("Nhân viên", jEm);
                    case "Per":
                        jBoxTabPanel.addTab("Nhóm quyền", jPer);
                }
            }
        }

    }
//    hành động

    private void roleActions() {
//      chức năng của nhân viên
        jCreateEm.setVisible(false);
        jUpdatePopupEm.setVisible(false);
        jCreatePopupEm.setVisible(false);
        jDeletePopupEm.setVisible(false);
        jPrintListEm.setVisible(false);
        jChangeSTTpopupEm.setVisible(true);
//      chức năng của nhóm quyền
        jBoxActions.setVisible(false);
        jCreateGPPopup.setVisible(false);
        jUpdateGPPopup.setVisible(false);
        if (em.getStatus() == 2) {
//          nhân viên
            jCreateEm.setVisible(true);
            jUpdatePopupEm.setVisible(true);
            jCreatePopupEm.setVisible(true);
            jDeletePopupEm.setVisible(true);
            jPrintListEm.setVisible(true);
            jChangeSTTpopupEm.setVisible(true);
//          nhóm quyền
            jBoxActions.setVisible(true);
            jCreateGPPopup.setVisible(true);
            jUpdateGPPopup.setVisible(true);
        } else {
            List<String> listActByEm = GPD.listPerActByEm(em.getId());
            for (String ActByEm1 : listActByEm) {
                switch (ActByEm1) {
//                    nhân viên
                    case "E-1":
                        jCreatePopupEm.setVisible(true);
                    case "E-2":
                        jUpdatePopupEm.setVisible(true);
                        break;
                    case "E-3":
                        jDeletePopupEm.setVisible(true);
                        break;
                    case "E-4":
                        jBoxListEm.setVisible(true);
                        break;
                    case "E-6":
                        jChangeSTTpopupEm.setVisible(true);
                        break;
//                        nhóm quền
                    case "PER-1":
                        jCreateGPPopup.setVisible(true);
                        jBoxActions.setVisible(true);
                        break;
                    case "E-5":
                        jBoxActions.setVisible(true);
                        jUpdateGPPopup.setVisible(true);
                        break;
                }
            }
        }

    }

//  hiển thị danh sách quyền
    List<JCheckBox> listJCheckBox = new ArrayList<>();

    private void showPerssion() {
        List<Views> listViews = VD.getAll();
        jTestCheckBox.setLayout(new GridLayout(listViews.size(), 1, WIDTH, WIDTH));

        for (Views listView : listViews) {
            List<Actions> listAct = AD.listActById_view(listView.getId());

            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());

            p.setPreferredSize(new Dimension(640, 100));
            p.setBounds(100, 100, 100, 100);
            p.setBackground(Color.gray);
            p.setBorder(new EmptyBorder(5, 5, 5, 5));
            p.setVisible(true);

            JLabel lab1 = new JLabel("User Name", JLabel.LEFT);
            p.add(lab1 = new JLabel(listView.getName()));

            for (Actions listAct1 : listAct) {
                JCheckBox jc = new JCheckBox(listAct1.getName());
                jc.setName(String.valueOf(listAct1.getId()));

                listJCheckBox.add(jc);
                p.add(jc);
            }

            jTestCheckBox.add(p);
        }
    }

//  giao diện mặc định
    private void defaultBoxInforEm() {
        jCreateEm.setVisible(true);
        jCouter.setVisible(false);
        jSaveEm.setVisible(false);
        jBoxCouter.setVisible(false);
        jErrorAcc.setText("");
        jNameEm1.setText("");
        jEmailEm.setText("");
        jPhoneEm.setText("");
        jPassEm.setText("");
        jPermission.setSelectedIndex(0);

    }

    private void addComboBoxFillEm() {
        jComboBoxFillEm.addItem("");
        jComboBoxFillEm.addItem("Nhân viên vừa thêm");
        List<GroupsPers> listGP = GPD.getAll();
        for (GroupsPers GP : listGP) {
            jComboBoxFillEm.addItem("Nhân viên " + GP.getName());
        }
    }

    private void interfaceDefaultEmployee() {
        addTableEm(ED.getEm());
        addComboboxGroupPers();
        addComboboxCouters();

        showPopupEm();
        jComboBoxFillEm.setSelectedIndex(0);
    }

    private void interfaceDefaultGroupPer() {
//      interface employee
        addTableGroupsPers();
        showPopupInteractGP();

        jCouter.setVisible(false);
        jBoxCouter.setVisible(false);
        jNameGP.setText(null);
        jStatusGP.setVisible(false);
        jUpdateGP.setVisible(false);
        jCreatePer.setVisible(true);
        for (JCheckBox listJCheckBox1 : listJCheckBox) {
            listJCheckBox1.setSelected(false);
        }

    }

//  hiển thị dữ liệu lên bảng nhóm quyền
    private void addTableGroupsPers() {
        DefaultTableModel dtm = new DefaultTableModel();
        List<GroupsPers> listGP = GPD.getAll();

        dtm.addColumn("#");
        dtm.addColumn("Tên");
        dtm.addColumn("Trạng Thái");
        dtm.addColumn("Ngày Tạo");
        dtm.addColumn("Ngày Cập Nhật");

        for (int i = 0; i < listGP.size(); i++) {
            GroupsPers gp = listGP.get(i);
            String status = "";
            if (gp.getStatus() == 1) {
                status = "Đang thực thi";
            } else {
                status = "Không hoạt động";
            }
            Vector v = new Vector();
            v.add(i + 1);
            v.add(gp.getName());
            v.add(status);
            v.add(gp.getDate_created());
            v.add(gp.getDate_updated());
            dtm.addRow(v);
        }
        jTableGP.setModel(dtm);
    }
//  quản lý nhân viên
//  hiển thị nhân viên lên bảng

    private void addTableEm(List<Employers> listEm) {
        DefaultTableModel dtm = new DefaultTableModel();

        List<GroupsPers> listGP = GPD.getAll();
        dtm.addColumn("#");
        dtm.addColumn("Tên");
        dtm.addColumn("Email");
        dtm.addColumn("SĐT");
        dtm.addColumn("Trạng Thái");
        dtm.addColumn("Ngày Tạo");
        dtm.addColumn("Quyền");
        dtm.addColumn("Quầy");
        int stt = 0;

        for (int i = 0; i < listEm.size(); i++) {
            Employers e = listEm.get(i);
            stt++;
            String namePer = "";
            for (GroupsPers listGP1 : listGP) {
                if (listGP1.getId() == e.getId_per()) {
                    namePer = listGP1.getName();
                }
            }
            String nameCouter = "";
            if (e.getId_couter() == 0) {
                nameCouter = "not";
            } else {
                for (int j = 0; j < CD.getAll().size(); j++) {
                    Couters couter = CD.getAll().get(j);
                    if (e.getId_couter() == couter.getId()) {
                        nameCouter = couter.getName();
                    }
                }
            }
            Vector v = new Vector();
            v.add(stt);
            v.add(e.getName());
            v.add(e.getEmail());
            v.add(e.getPhone());
            v.add(e.getStatus() == 1 ? "Đang làm" : "Đã nghỉ");
            v.add(e.getDate_created());
            v.add(namePer);
            v.add(nameCouter);
            dtm.addRow(v);
        }
        jTableEm.setModel(dtm);
    }
//  thêm dứ liệu lên combobox
//    combobox of couters

    private void addComboboxCouters() {
        List<Couters> listCouter = CD.getAll();
        DefaultComboBoxModel dcmCouters = new DefaultComboBoxModel();
        for (Couters Couter : listCouter) {
            if (Couter.getStatus() == 1) {
                dcmCouters.addElement(Couter.getName());
            }
            
        }
        jCouter.setModel(dcmCouters);
    }
//    combobox of group perActions

    private void addComboboxGroupPers() {

        DefaultComboBoxModel dcmGroupPer = new DefaultComboBoxModel();
        List<GroupsPers> listGP = GPD.getAll();
        for (GroupsPers gp : listGP) {
            if (gp.getStatus() == 1) {
                dcmGroupPer.addElement(gp.getName());
            }
        }
        jPermission.setModel(dcmGroupPer);
//        add event
        jPermission.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GroupsPers gp = GPD.getAll().get(jPermission.getSelectedIndex());
                if (GPD.checkPerPAY(gp.getId())) {
                    jCouter.setVisible(true);
                    jBoxCouter.setVisible(true);
                } else {
                    jCouter.setVisible(false);
                    jBoxCouter.setVisible(false);
                }
            }
        });
    }

    //  
    private void setPopupInteract(int status) {
        if (status == 1) {
            jChangeSTTpopupEm.setIcon(new ImageIcon(ListPro.class.getResource("/Commons/img/Eye-Invisible-icon.png")));
            jChangeSTTpopupEm.setText("Nghỉ làm");
        } else {
            jChangeSTTpopupEm.setIcon(new ImageIcon(ListPro.class.getResource("/Commons/img/Eye-Visible-icon.png")));
            jChangeSTTpopupEm.setText("Đi làm");
        }

    }
//  hiển thị popup menu EM
    int row_select_Em = 0;

    private void showPopupEm() {
        jTableEm.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = jTableEm.rowAtPoint(e.getPoint());
                if (r >= 0 && r < jTableEm.getRowCount()) {
                    jTableEm.setRowSelectionInterval(r, r);
                } else {
                    jTableEm.clearSelection();
                }

                row_select_Em = jTableEm.getSelectedRow();
                if (row_select_Em < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jInteractEm.show(e.getComponent(), e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractEm.show(e.getComponent(), e.getX(), e.getY());
                    }
                    Employers ee = ED.getEm().get(jTableEm.getSelectedRow());
                    setPopupInteract(ee.getStatus());

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

        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jInteractGP = new javax.swing.JPopupMenu();
        jCreateGPPopup = new javax.swing.JMenuItem();
        jUpdateGPPopup = new javax.swing.JMenuItem();
        jInteractEm = new javax.swing.JPopupMenu();
        jCreatePopupEm = new javax.swing.JMenuItem();
        jUpdatePopupEm = new javax.swing.JMenuItem();
        jDeletePopupEm = new javax.swing.JMenuItem();
        jChangeSTTpopupEm = new javax.swing.JMenuItem();
        jBoxTabPanel = new javax.swing.JTabbedPane();
        jEm = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        CreateEm = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jComboBoxFillEm = new javax.swing.JComboBox();
        jPrintListEm = new javax.swing.JButton();
        jBoxListEm = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableEm = new javax.swing.JTable();
        jInforEm = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jNameEm1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jEmailEm = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPhoneEm = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPassEm = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPermission = new javax.swing.JComboBox();
        jPanel14 = new javax.swing.JPanel();
        jBoxCouter = new javax.swing.JLabel();
        jCouter = new javax.swing.JComboBox();
        jPanel15 = new javax.swing.JPanel();
        jCreateEm = new javax.swing.JButton();
        jSaveEm = new javax.swing.JButton();
        jErrorAcc = new javax.swing.JLabel();
        jPer = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableGP = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jBoxActions = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jNameGP = new javax.swing.JTextField();
        jCreatePer = new javax.swing.JButton();
        jStatusGP = new javax.swing.JCheckBox();
        jUpdateGP = new javax.swing.JButton();
        jTestCheckBox = new javax.swing.JPanel();

        jButton4.setText("jButton4");

        jLabel2.setText("jLabel2");

        jCreateGPPopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-icon16.png"))); // NOI18N
        jCreateGPPopup.setText("Thêm mới");
        jCreateGPPopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateGPPopupActionPerformed(evt);
            }
        });
        jInteractGP.add(jCreateGPPopup);

        jUpdateGPPopup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon-16.png"))); // NOI18N
        jUpdateGPPopup.setText("Cập nhật");
        jUpdateGPPopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateGPPopupActionPerformed(evt);
            }
        });
        jInteractGP.add(jUpdateGPPopup);

        jCreatePopupEm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/add-icon16.png"))); // NOI18N
        jCreatePopupEm.setText("Thêm mới");
        jCreatePopupEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreatePopupEmActionPerformed(evt);
            }
        });
        jInteractEm.add(jCreatePopupEm);

        jUpdatePopupEm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/edit-file-icon-16.png"))); // NOI18N
        jUpdatePopupEm.setText("Cập nhật");
        jUpdatePopupEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdatePopupEmActionPerformed(evt);
            }
        });
        jInteractEm.add(jUpdatePopupEm);

        jDeletePopupEm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/Close-icon16.png"))); // NOI18N
        jDeletePopupEm.setText("Xóa");
        jDeletePopupEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeletePopupEmActionPerformed(evt);
            }
        });
        jInteractEm.add(jDeletePopupEm);

        jChangeSTTpopupEm.setText("abc");
        jChangeSTTpopupEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChangeSTTpopupEmActionPerformed(evt);
            }
        });
        jInteractEm.add(jChangeSTTpopupEm);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Danh mục quản lý nhân viên và nhóm quyền");
        setPreferredSize(new java.awt.Dimension(789, 696));

        jBoxTabPanel.setBackground(new java.awt.Color(255, 0, 0));
        jBoxTabPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBoxTabPanelMouseClicked(evt);
            }
        });

        jEm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jEmMouseClicked(evt);
            }
        });

        jLayeredPane1.setLayout(new java.awt.CardLayout());

        CreateEm.setAutoscrolls(true);
        CreateEm.setPreferredSize(new java.awt.Dimension(777, 662));

        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Lọc");

        jComboBoxFillEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFillEmActionPerformed(evt);
            }
        });

        jPrintListEm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Commons/img/print-icon.png"))); // NOI18N
        jPrintListEm.setText("In Danh Sách");
        jPrintListEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrintListEmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jComboBoxFillEm, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 371, Short.MAX_VALUE)
                .addComponent(jPrintListEm)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxFillEm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPrintListEm, javax.swing.GroupLayout.PREFERRED_SIZE, 26, Short.MAX_VALUE))
                .addGap(7, 7, 7))
        );

        jBoxListEm.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jTableEm.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(jTableEm);

        javax.swing.GroupLayout jBoxListEmLayout = new javax.swing.GroupLayout(jBoxListEm);
        jBoxListEm.setLayout(jBoxListEmLayout);
        jBoxListEmLayout.setHorizontalGroup(
            jBoxListEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxListEmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jBoxListEmLayout.setVerticalGroup(
            jBoxListEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxListEmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );

        jInforEm.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin nhân viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Tên");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNameEm1))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jNameEm1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Email");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jEmailEm))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jEmailEm, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Liên hệ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPhoneEm, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jPhoneEm, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 152, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Mật khẩu");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPassEm))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jPassEm, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Quyền");

        jPermission.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPermission.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPermissionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPermission, 0, 192, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPermission, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jBoxCouter.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jBoxCouter.setText("Quầy than toán");

        jCouter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jBoxCouter, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCouter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jBoxCouter)
                .addComponent(jCouter, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jCreateEm.setText("Thêm");
        jCreateEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreateEmActionPerformed(evt);
            }
        });

        jSaveEm.setText("Lưu");
        jSaveEm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSaveEmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCreateEm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSaveEm)
                .addContainerGap(224, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCreateEm)
                    .addComponent(jSaveEm))
                .addContainerGap())
        );

        jErrorAcc.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jInforEmLayout = new javax.swing.GroupLayout(jInforEm);
        jInforEm.setLayout(jInforEmLayout);
        jInforEmLayout.setHorizontalGroup(
            jInforEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jInforEmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jInforEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jInforEmLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jInforEmLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jErrorAcc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jInforEmLayout.setVerticalGroup(
            jInforEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInforEmLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jInforEmLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jErrorAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout CreateEmLayout = new javax.swing.GroupLayout(CreateEm);
        CreateEm.setLayout(CreateEmLayout);
        CreateEmLayout.setHorizontalGroup(
            CreateEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateEmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CreateEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBoxListEm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jInforEm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        CreateEmLayout.setVerticalGroup(
            CreateEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CreateEmLayout.createSequentialGroup()
                .addComponent(jInforEm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBoxListEm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLayeredPane1.add(CreateEm, "card2");

        javax.swing.GroupLayout jEmLayout = new javax.swing.GroupLayout(jEm);
        jEm.setLayout(jEmLayout);
        jEmLayout.setHorizontalGroup(
            jEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jEmLayout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jEmLayout.setVerticalGroup(
            jEmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jEmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
        );

        jBoxTabPanel.addTab("Nhân viên", jEm);

        jTableGP.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableGP);

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin quyền", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel16.setText("- Một nhóm quyền có thể vào nhiều giao diện");

        jLabel17.setText("- Một giao diện luôn chứa nhiều hành động ");

        jLabel21.setText("- Phân quyền theo giao diện và hành động");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel21))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel21)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jBoxActions.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thêm nhóm quyền cùng chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setText("Tên");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jNameGP, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jNameGP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jCreatePer.setText("Tạo Quyền");
        jCreatePer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCreatePerActionPerformed(evt);
            }
        });

        jStatusGP.setText("Hoạt động");

        jUpdateGP.setText("Lưu");
        jUpdateGP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateGPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jStatusGP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
                .addComponent(jUpdateGP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCreatePer)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCreatePer)
                            .addComponent(jStatusGP)
                            .addComponent(jUpdateGP))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTestCheckBox.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quyền", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jTestCheckBox.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTestCheckBox.setLayout(new java.awt.GridLayout(2, 0, 1, 0));

        javax.swing.GroupLayout jBoxActionsLayout = new javax.swing.GroupLayout(jBoxActions);
        jBoxActions.setLayout(jBoxActionsLayout);
        jBoxActionsLayout.setHorizontalGroup(
            jBoxActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxActionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTestCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jBoxActionsLayout.setVerticalGroup(
            jBoxActionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxActionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTestCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPerLayout = new javax.swing.GroupLayout(jPer);
        jPer.setLayout(jPerLayout);
        jPerLayout.setHorizontalGroup(
            jPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxActions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPerLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPerLayout.setVerticalGroup(
            jPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jBoxActions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBoxTabPanel.addTab("Nhóm Quyền", jPer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBoxTabPanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBoxTabPanel, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//hiển thị dữ liệu để cập nhật nhóm quyền
    private void jUpdateGPPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateGPPopupActionPerformed
        interfaceDefaultGroupPer();

        GroupsPers gp = GPD.getAll().get(row_GP_selected);
        jUpdateGP.setVisible(true);
        jCreatePer.setVisible(false);
        jStatusGP.setVisible(true);
        jNameGP.setText(gp.getName());

        List<PersActions> listPA = PAD.listPerAccById(gp.getId());

        for (JCheckBox listJCheckBox1 : listJCheckBox) {

            for (PersActions listPA1 : listPA) {
                if (Integer.parseInt(listJCheckBox1.getName()) == listPA1.getId_act()) {
                    listJCheckBox1.setSelected(true);
                }
            }
        }
        if (gp.getStatus() == 1) {
            jStatusGP.setSelected(true);
        } else {
            jStatusGP.setSelected(false);
        }
    }//GEN-LAST:event_jUpdateGPPopupActionPerformed

//hiển thị from mặc định
    private void jCreateGPPopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateGPPopupActionPerformed
        interfaceDefaultGroupPer();
    }//GEN-LAST:event_jCreateGPPopupActionPerformed

//
    private Employers selectInput() {

        String name = jNameEm1.getText();
        String email = jEmailEm.getText();
        String phone = jPhoneEm.getText();
        String pass = jPassEm.getText();
        int id_pers = GPD.getAll().get(jPermission.getSelectedIndex()).getId();
        int id_Couter = 0;
        if (jCouter.isVisible()) {
            id_Couter = CD.getAll().get(jCouter.getSelectedIndex()).getId();
        } else {
            id_Couter = 0;
        }
        if (!ACCD.rgEmail(email)) {
            jErrorAcc.setText("Email không đúng định dạng !");
            return null;
        } else if (ACCD.checkEmail(email).size() == 0) {
            Employers e = new Employers(name, email, pass, "", phone, id_Couter, id_pers, "");
            return e;
        } else {
            jErrorAcc.setText("Email đã tồn tại Xin nhập lại !");
            return null;
        }

    }
    private void jUpdatePopupEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdatePopupEmActionPerformed
        jSaveEm.setVisible(true);
        jCreateEm.setVisible(false);
        Employers e = ED.getEm().get(row_select_Em);
        jNameEm1.setText(e.getName());
        jEmailEm.setText(e.getEmail());
        jPhoneEm.setText(e.getPhone());
        List<GroupsPers> ListGroupPer = GPD.getAll();
        int row = 0;
        int i;
        for (i = 0; i < ListGroupPer.size(); i++) {
            GroupsPers c = ListGroupPer.get(i);

            if (c.getId() == e.getId_per()) {
                row = i;
            }
        }
        for (int j = 0; j < CD.getAll().size(); j++) {
            Couters c = CD.getAll().get(j);
            if (c.getId() == e.getId_couter()) {
                jCouter.setSelectedIndex(j);
            }
        }
        jPermission.setSelectedIndex(row);
    }//GEN-LAST:event_jUpdatePopupEmActionPerformed

    private void jDeletePopupEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeletePopupEmActionPerformed
        Employers e = ED.getEm().get(row_select_Em);
        int check = JOptionPane.showConfirmDialog(rootPane, "Bạn có chác muốn xóa Nhân viên này ?", "Xác nhận hành động", JOptionPane.YES_OPTION, JOptionPane.CANCEL_OPTION);
        if (check == 0) {
            if (ED.deleteEm(e.getId())) {
                interfaceDefaultEmployee();
                defaultBoxInforEm();
            }
        }
    }//GEN-LAST:event_jDeletePopupEmActionPerformed

    private void jCreatePopupEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreatePopupEmActionPerformed
        defaultBoxInforEm();
    }//GEN-LAST:event_jCreatePopupEmActionPerformed

    private void jChangeSTTpopupEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChangeSTTpopupEmActionPerformed
        Employers e = ED.getEm().get(row_select_Em);
        int confim = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn thay đổi trạng thái nhân viên này ?", "Xác nhận hành động", JOptionPane.YES_OPTION, JOptionPane.CANCEL_OPTION);
        if (confim == 0) {
            if (ED.changeSTT(e.getId(), e.getStatus())) {
                interfaceDefaultEmployee();
                defaultBoxInforEm();
            }
        }
    }//GEN-LAST:event_jChangeSTTpopupEmActionPerformed

//cập nhật nhóm quyền
    private void jUpdateGPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateGPActionPerformed
        //        cập nhật thông tin quyền
        GroupsPers selectedGP = GPD.getAll().get(row_GP_selected);
        String name = jNameGP.getText();
        int status;
        if (jStatusGP.isSelected()) {
            status = 1;
        } else {
            status = 0;
        }
        GroupsPers gp = new GroupsPers(selectedGP.getId(), name, "", status);
        GPD.updateGroupsPer(gp);

        //      danh sách nhóm quyền theo id quyền
        List<PersActions> listPerAction = PAD.listPerAccById(selectedGP.getId());
        //      Danh sách các phần tử đã chọn giống với csdl
        List<Integer> lisSimilar = new ArrayList<>();
        //      danh sách các ô đã chọn khác nhau
        List<JCheckBox> listDifferentCheckBox = new ArrayList<>();
        //      danh sách các phần tử khác nhau trên csdl
        List<PersActions> listDifferentPA = PAD.listPerAccById(selectedGP.getId());
        //      danh sách các phần từ khác nhau(ô checkbox và scsdl)
        List<JCheckBox> listDifferent = new ArrayList<>();

        //      lấy các id act giống nhau
        for (JCheckBox listCB : listJCheckBox) {
            if (listCB.isSelected()) {
                for (PersActions listPerAction1 : listPerAction) {
                    if (Integer.parseInt(listCB.getName()) == listPerAction1.getId_act()) {
                        lisSimilar.add(listPerAction1.getId_act());
                        System.out.println("các phần tử giống nhau !" + listPerAction1.getId_act());
                    }
                }
            }
        }
        // lấy các ô đã chọn và lấy các đối tượng khác nhau
        for (JCheckBox listCB : listJCheckBox) {
            if (listCB.isSelected()) {
                listDifferentCheckBox.add(listCB);
                listDifferent.add(listCB);
            } else {
                for (int i = 0; i < listPerAction.size(); i++) {
                    PersActions per = listPerAction.get(i);
                    if (per.getId_act() == Integer.parseInt(listCB.getName())) {
                        listDifferent.add(listCB);
                    }
                }
            }
        }
        //      lọc các phần tử giống nhau(với csdl) trong ô
        for (int i = 0; i < listDifferentCheckBox.size(); i++) {
            for (int j = 0; j < lisSimilar.size(); j++) {
                if (lisSimilar.get(j) == Integer.parseInt(listDifferentCheckBox.get(i).getName())) {
                    listDifferentCheckBox.remove(i);
                }
            }

        }
        for (int i = 0; i < listDifferent.size(); i++) {
            for (int j = 0; j < lisSimilar.size(); j++) {
                if (lisSimilar.get(j) == Integer.parseInt(listDifferent.get(i).getName())) {
                    listDifferent.remove(i);
                }
            }

        }
        for (int i = 0; i < listDifferent.size(); i++) {
            JCheckBox get = listDifferent.get(i);
            System.out.println("Các phẩn tử khác nhau " + get.getName());

        }
        for (JCheckBox checked : listDifferentCheckBox) {
            System.out.println("phần tử của ô khác nhau : " + checked.getName());
        }

        int cout = 0;
        //      lấy các phần tử khác nhau trên csdl
        for (int i = 0; i < listPerAction.size(); i++) {
            PersActions PA = listPerAction.get(i);
            for (Integer id_act : lisSimilar) {
                if (PA.getId_act() == id_act) {
                    listDifferentPA.remove(i - cout);
                    cout++;
                }
            }
        }

        for (int i = 0; i < listDifferentPA.size(); i++) {
            PersActions per = listDifferentPA.get(i);
            System.out.println("các phần tử trên csdl khác nhau : " + per.getId_act());
        }
        //      Thục hiện thay đổi csdl
        for (int i = 0; i < listDifferent.size(); i++) {
            try {
                JCheckBox checkFiltered = listDifferentCheckBox.get(i);
                try {
                    //                  thực hiện cập nhật
                    PAD.updatePerAcc(selectedGP.getId(), listDifferentPA.get(i).getId_act(), Integer.parseInt(checkFiltered.getName()));

                } catch (Exception e) {
                    //                  thực hiện thêm mới
                    PAD.createPerAcc(new PersActions(selectedGP.getId(), Integer.parseInt(checkFiltered.getName())));
                }
            } catch (Exception e) {

                try {
                    //                 thực hiện xóa khi bị tràn mảng listDifferentCheckBox
                    PAD.deletePerAcc(selectedGP.getId(), listDifferentPA.get(i).getId_act());
                } catch (Exception ex) {
                    //                 trương trình thưc hiện cập nhật xong
                }
            }
        }
        //      quay về giao diện mặc định
        interfaceDefaultGroupPer();
    }//GEN-LAST:event_jUpdateGPActionPerformed

// tạo nhóm quyền
    private void jCreatePerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreatePerActionPerformed
        String name = jNameGP.getText();
        GroupsPers gp = new GroupsPers(name, 1);
        if (GPD.createGroupsPer(gp)) {
            int id_per = GPD.getAll().get(GPD.getAll().size() - 1).getId();
            //        thêm quyền theo hành động
            for (JCheckBox listJCheckBox1 : listJCheckBox) {
                if (listJCheckBox1.isSelected()) {
                    PAD.createPerAcc(new PersActions(id_per, Integer.valueOf(listJCheckBox1.getName())));
                }
            }
            interfaceDefaultGroupPer();
        }
    }//GEN-LAST:event_jCreatePerActionPerformed

    private void jSaveEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSaveEmActionPerformed

    }//GEN-LAST:event_jSaveEmActionPerformed

    private void jCreateEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCreateEmActionPerformed
        if (selectInput() != null) {
            if (ED.createEm(selectInput())) {
                interfaceDefaultEmployee();
                defaultBoxInforEm();
            }
        }
    }//GEN-LAST:event_jCreateEmActionPerformed

    private void jPermissionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPermissionActionPerformed

    }//GEN-LAST:event_jPermissionActionPerformed

    private void jPrintListEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPrintListEmActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        File file = new File("E:/");
        fileChooser.setSelectedFile(file);
        fileChooser.showSaveDialog(jPrintListEm);

        String pathNew = String.valueOf(fileChooser.getSelectedFile());
        try {
            SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
            ExcelFile excel = new ExcelFile();
            ExcelWorksheet workSheet = excel.addWorksheet("Tables");
            if (file.exists()) {
                file.delete();
                workSheet.getCell(0, 0).setValue("#");
                workSheet.getCell(0, 1).setValue("Tên");
                workSheet.getCell(0, 2).setValue("Email");
                workSheet.getCell(0, 3).setValue("SĐT");
                workSheet.getCell(0, 4).setValue("Trạng Thái");
                workSheet.getCell(0, 5).setValue("Ngày Tạo");
                workSheet.getCell(0, 6).setValue("Quyền");
                workSheet.getCell(0, 7).setValue("Quầy");

                List<Employers> lisEm = ED.getEm();
                excel.save(pathNew);

                int count = 0;
                for (int i = 1; i <= lisEm.size(); i++) {
                    Employers e = lisEm.get(i - 1);
                    count++;
                    workSheet.getCell(i, 0).setValue(count);
                    for (int j = 0; j < 6; j++) {
                        workSheet.getCell(i, 1).setValue(e.getName());
                        workSheet.getCell(i, 2).setValue(e.getEmail());
                        workSheet.getCell(i, 3).setValue(e.getPhone());
                        workSheet.getCell(i, 4).setValue(e.getStatus());
                        workSheet.getCell(i, 5).setValue(e.getDate_created());

//                      lấy tên nhóm quyền
                        String namePer = "";
                        for (GroupsPers listGP1 : GPD.getAll()) {
                            if (listGP1.getId() == e.getId_per()) {
                                namePer = listGP1.getName();
                            }
                        }
//                      lấy tên quầy nếu có
                        String nameCouter = "";
                        if (e.getId_couter() == 0) {
                            nameCouter = "not";
                        } else {
                            for (int z = 0; z < CD.getAll().size(); z++) {
                                Couters couter = CD.getAll().get(z);
                                if (e.getId_couter() == couter.getId()) {
                                    nameCouter = couter.getName();
                                }
                            }
                        }
                        workSheet.getCell(i, 6).setValue(namePer);
                        workSheet.getCell(i, 7).setValue(nameCouter);

                    }
                }
                excel.save(pathNew);
            }
            JOptionPane.showMessageDialog(rootPane, "Lưu file thành công !");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "File không tồn tại ! Xin thử lại !");
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_jPrintListEmActionPerformed

    private void jComboBoxFillEmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFillEmActionPerformed
        int rowFillListEm = jComboBoxFillEm.getSelectedIndex();
        if (rowFillListEm == 1) {
            addTableEm(ED.listEmInNewDate());
        } else if (rowFillListEm > 1) {
            GroupsPers GP = GPD.getAll().get(rowFillListEm - 2);
            addTableEm(ED.listEmByGP(GP.getId()));
        }
    }//GEN-LAST:event_jComboBoxFillEmActionPerformed

    private void jEmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jEmMouseClicked

    }//GEN-LAST:event_jEmMouseClicked

    private void jBoxTabPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBoxTabPanelMouseClicked
//        if (jPer.isShowing()) {
//            System.out.println("ok1");
//        } else {
//            System.out.println("false1");
//        }
//        if (jEm.isShowing()) {
//            System.out.println("ok2");
//        } else {
//            System.out.println("false2");
//        }
    }//GEN-LAST:event_jBoxTabPanelMouseClicked
//
    int row_GP_selected = 0;

    private void showPopupInteractGP() {
        //      bắt sự kiện
        jTableGP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = jTableGP.rowAtPoint(e.getPoint());
                if (r >= 0 && r < jTableGP.getRowCount()) {
                    jTableGP.setRowSelectionInterval(r, r);
                } else {
                    jTableGP.clearSelection();
                }

                row_GP_selected = jTableGP.getSelectedRow();
                if (row_GP_selected < 0) {
                    return;
                }
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    jInteractGP.show(e.getComponent(), e.getX(), e.getY());
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jInteractGP.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
    }
//   

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
            java.util.logging.Logger.getLogger(EmployeesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeesManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CreateEm;
    private javax.swing.JPanel jBoxActions;
    private javax.swing.JLabel jBoxCouter;
    private javax.swing.JPanel jBoxListEm;
    private javax.swing.JTabbedPane jBoxTabPanel;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenuItem jChangeSTTpopupEm;
    private javax.swing.JComboBox jComboBoxFillEm;
    private javax.swing.JComboBox jCouter;
    private javax.swing.JButton jCreateEm;
    private javax.swing.JMenuItem jCreateGPPopup;
    private javax.swing.JButton jCreatePer;
    private javax.swing.JMenuItem jCreatePopupEm;
    private javax.swing.JMenuItem jDeletePopupEm;
    private javax.swing.JPanel jEm;
    private javax.swing.JTextField jEmailEm;
    private javax.swing.JLabel jErrorAcc;
    private javax.swing.JPanel jInforEm;
    private javax.swing.JPopupMenu jInteractEm;
    private javax.swing.JPopupMenu jInteractGP;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JTextField jNameEm1;
    private javax.swing.JTextField jNameGP;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jPassEm;
    private javax.swing.JPanel jPer;
    private javax.swing.JComboBox jPermission;
    private javax.swing.JTextField jPhoneEm;
    private javax.swing.JButton jPrintListEm;
    private javax.swing.JButton jSaveEm;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBox jStatusGP;
    private javax.swing.JTable jTableEm;
    private javax.swing.JTable jTableGP;
    private javax.swing.JPanel jTestCheckBox;
    private javax.swing.JButton jUpdateGP;
    private javax.swing.JMenuItem jUpdateGPPopup;
    private javax.swing.JMenuItem jUpdatePopupEm;
    // End of variables declaration//GEN-END:variables
}
