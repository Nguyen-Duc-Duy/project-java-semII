/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IOrders;
import Emtitys.Orders;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class OrdersDAO implements IOrders {

    Connection connect;

    public OrdersDAO(Connection c) {
        this.connect = c;
    }

    @Override
    public List<Orders> getAll() {
        List<Orders> listOr = new ArrayList<>();
        try {

            String SQLselectAll = "{ call selectAllO }";
            PreparedStatement ps = connect.prepareStatement(SQLselectAll);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orders o = new Orders(rs.getInt("id"), rs.getInt("id_employee"), rs.getInt("status"), rs.getInt("code"), rs.getString("date_created"), rs.getString("date_updated"));
                listOr.add(o);
            }
            return listOr;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống xin thử lại sau !");
            System.out.println("Lỗi sql select orders!" + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Orders> getAllByEm(int id_em) {
        List<Orders> listO = new ArrayList<>();
        try {

            String SQLselectAll = "{ call selectOByEm(?) }";
            PreparedStatement ps = connect.prepareStatement(SQLselectAll);
            ps.setInt(1, id_em);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Orders o = new Orders(rs.getInt("id"), rs.getInt("id_employee"), rs.getInt("status"), rs.getInt("code"), rs.getString("date_created"), rs.getString("date_updated"));
                listO.add(o);
            }
            return listO;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống xin thử lại sau !");
            System.out.println("Lỗi sql select orders!" + ex.getMessage());
            return null;
        }
    }

    @Override
    public void createOrder(Orders o, int id_em) {
        String SQLcreateOrder = "{ call createOrder(?,?) }";
        try {
            PreparedStatement ps = connect.prepareStatement(SQLcreateOrder);
            ps.setInt(1, id_em);
            ps.setInt(2, o.getCode());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " Phiếu mua hàng đã được thêm !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống xin thử lại sau !");
            System.out.println("Lỗi sql create orders!" + ex.getMessage());
        }

    }

    @Override
    public void updateOrder(Orders o) {

    }

    @Override
    public void deleteOrder(int id) {
        String SQLcreateOrder = "{ call deleteOrder(?) }";
        try {
            PreparedStatement ps = connect.prepareStatement(SQLcreateOrder);
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null," Xóa thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống xin thử lại sau !");
            System.out.println("Lỗi sql delete orders!" + ex.getMessage());
        }
    }

    @Override
    public Orders selectById(int id) {
        try {

            String SQLselectAll = "{ call selectOrderById(?) }";
            PreparedStatement ps = connect.prepareStatement(SQLselectAll);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
             Orders o = new Orders();
            while (rs.next()) {
                o = new Orders(rs.getInt("id"), rs.getInt("id_employee"), rs.getInt("status"), rs.getInt("code"), rs.getString("date_created"), rs.getString("date_updated"));
            }
            return o;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống xin thử lại sau !");
            System.out.println("Lỗi sql select orders!" + ex.getMessage());
            return null;
        }
    }

}
