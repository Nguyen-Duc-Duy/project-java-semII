/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.ICouters;
import Emtitys.Couters;
import Emtitys.Employers;
import Emtitys.OrderDetails;
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
public class CoutersDAO implements ICouters{
    
    Connection connect;

    public CoutersDAO(Connection c) {
        this.connect = c;
    }
    
    @Override
    public List<Couters> getAll() {
        List<Couters> listCouter = new ArrayList<>();
        try {

            String SQLselect = "{ call selectAllCouter }";
            PreparedStatement ps = connect.prepareStatement(SQLselect);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Couters c = new Couters(rs.getInt("id"),rs.getInt("status"), rs.getString("name"), rs.getString("date_created"), rs.getString("date_updated"));
                listCouter.add(c);
            }
            return listCouter;
        } catch (SQLException ex) {
            System.out.println("lối couter" + ex.getMessage());
            return null;
        }
    }

    @Override
    public void createCouter(Couters c) {
        try {
            String SQLcreate = "{ call createCouter(?) }";
            PreparedStatement ps = connect.prepareStatement(SQLcreate);
            ps.setString(1, c.getName());

            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " Quầy thanh toán được thêm !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Thêm Quầy thất bại !");
            System.out.println("create couter"+ex.getMessage());

        }
    }

    @Override
    public void updateCouter(Couters c) {
        try {
            String SQLcreate = "{ call updateCouter(?,?,?) }";
            PreparedStatement ps = connect.prepareStatement(SQLcreate);
            ps.setInt(1, c.getId());
            ps.setString(2, c.getName());
            ps.setInt(3, c.getStatus());

            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " Quầy thanh toán đã được cập nhật !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Cập nhật thất bại !");
            System.out.println("update couter"+ex.getMessage());

        }
    }

    @Override
    public void changeSTTCouter(int id, int status) {
        try {
            String SQLcreate = "{ call changeSTTCouter(?,?) }";
            PreparedStatement ps = connect.prepareStatement(SQLcreate);
            int change;
            String message;
            if (status == 0) {
                message = " Quầy thanh toán đã bị ẩn!";
            } else {
                message = " Quầy thanh toán đã được hiển thị thành công !";
            }
            ps.setInt(1, id);
            ps.setInt(2, status);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + message);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Thực thi thấy bại !");
            System.out.println("changeStt couter"+ex.getMessage());

        }
    }

    @Override
    public List<Employers> showEmByCouter(int id_couter) {
       List<Employers> listEm = new ArrayList<>();
        try {
            String SQLcreate = "{ call selectEmByCouter(?) }";
            PreparedStatement ps = connect.prepareStatement(SQLcreate);
            ps.setInt(1, id_couter);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Employers e = new Employers(rs.getInt("id"), rs.getString("name"));
                listEm.add(e);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Cập nhật thất bại !");
            System.out.println("update couter"+ex.getMessage());

        }
        return listEm;
    }
    
}
