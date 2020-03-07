/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IOrderDetail;
import Emtitys.OrderDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class OrderDetailDAO implements IOrderDetail {

    Connection connect;

    public OrderDetailDAO(Connection c) {
        this.connect = c;
    }

    @Override
    public List<OrderDetails> listOD(int id) {
        List<OrderDetails> listOD = new ArrayList<>();
        try {

            String SQLselect = "{ call selectAll(?) }";
            PreparedStatement ps = connect.prepareStatement(SQLselect);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetails od = new OrderDetails(rs.getInt("id_order"), rs.getInt("id_pro"), rs.getInt("quantity"));
                listOD.add(od);
            }
            return listOD;
        } catch (SQLException ex) {
            System.out.println("lối order detail" + ex.getMessage());
            return null;
        }
    }

    @Override
    public void createOD(OrderDetails od) {
        try {
            String SQLcreate = "{ call createOD(?,?,?) }";
            PreparedStatement ps = connect.prepareStatement(SQLcreate);
            ps.setInt(1, od.getId_order());
            ps.setInt(2, od.getId_pro());
            ps.setInt(3, od.getQuantity());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " Sản phẩm được thêm vào danh sách !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"thêm  Sản phẩm thất bại !");
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void updateOD(OrderDetails od) {
        try {
            String SQLupdate = "{ call updateOD(?,?,?)}";
            PreparedStatement ps = connect.prepareStatement(SQLupdate);
            ps.setInt(1, od.getId_order());
            ps.setInt(2, od.getId_pro());
            ps.setInt(3, od.getQuantity());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "cập nhật thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "cập nhật thất bại !");
            System.out.println(ex.getMessage()+"update OD");
        }
    }

    @Override
    public void deleteOD(OrderDetails od) {
        try {
            String SQLdelete = "{ call delete(?,?)}";
            PreparedStatement ps= connect.prepareStatement(SQLdelete);
            ps.setInt(1, od.getId_order());
            ps.setInt(2, od.getId_pro());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Xóa thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Xóa thất bại !");
            System.out.println(ex.getMessage()+"delete OD");
        }
    }

}
