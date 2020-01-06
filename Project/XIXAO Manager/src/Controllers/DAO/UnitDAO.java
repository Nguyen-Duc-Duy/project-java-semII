/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IUnits;
import Emtitys.Categorys;
import Emtitys.Units;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class UnitDAO implements IUnits{
    Connection con;

    public UnitDAO(Connection c) {
        this.con = c;
    }
    
    Date date = new Date();
    String getTimeNow = new SimpleDateFormat("yyyy:MM:dd HH::mm").format(date);
    
    @Override
    public List<Units> getAll() {
        List<Units> listUnit = new ArrayList<>();
        try {
            String SQLselectUnit = "{ call selectAllUnit }";
            PreparedStatement ps = con.prepareStatement(SQLselectUnit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Units u =new Units(rs.getInt("id"), rs.getString("name"), rs.getString("date_created"), rs.getString("date_updated"));
                listUnit.add(u);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi hệ thống xin thử lại !");
            System.out.println("lỗi csdl cat" + ex.getMessage());
        }
        return listUnit;
    }

    @Override
    public void createUnit(Units u) {
        try {
            String SQLcreateUnit = "{ call createUnit(?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateUnit);
            ps.setString(1, u.getName());
            ps.executeQuery();
            JOptionPane.showMessageDialog(null, "Thêm Mới thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql !" + ex.getMessage());
        }
    }

    @Override
    public void updateUnit(Units u) {
        try {
            String SQLcreateUnit = "{ call updateUnit(?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateUnit);
            ps.setString(1, u.getName());

            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " mục đã được Cập Nhật thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql !" + ex.getMessage());
        }
    }

    @Override
    public void deleteUnit(int id) {
        try {
            String SQLcreateUnit = "{ call deleteUnit(?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateUnit);
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " mục đã được Xóa thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql !" + ex.getMessage());
        }
    }

    @Override
    public void printUnit() {
        List<Units> listUnit = getAll();
    }

    
}
