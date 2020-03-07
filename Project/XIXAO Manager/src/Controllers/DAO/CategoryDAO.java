/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.ICategory;
import Emtitys.Categorys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class CategoryDAO implements ICategory {

    Connection con;

    public CategoryDAO(Connection c) {
        this.con = c;
    }

    Date date = new Date();
    String getTimeNow = new SimpleDateFormat("yyyy:MM:dd HH::mm").format(date);

    @Override
    public List<Categorys> getAll() {
        List<Categorys> listCat = new ArrayList<>();
        try {
            String SQLselectCat = "{ call selectAllCat }";
            PreparedStatement ps = con.prepareStatement(SQLselectCat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categorys c = new Categorys(rs.getInt("id"), rs.getString("name"), rs.getInt("status"), rs.getString("date_created"), rs.getString("date_updated"));
                listCat.add(c);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi hệ thống xin thử lại !");
            System.out.println("lỗi csdl cat" + ex.getMessage());
        }
        return listCat;

    }

    @Override
    public void createCat(Categorys c) {

        try {
            String SQLcreateCat = "{ call createCat(?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateCat);
            ps.setString(1, c.getName());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm Mới thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql !" + ex.getMessage());
        }
    }

    @Override
    public void updateCat(Categorys c) {
        try {
            String SQLcreateCat = "{ call updateCat(?,?,?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateCat);
            ps.setInt(1, c.getId());
            ps.setString(2, c.getName());
            ps.setInt(3, c.getStatus());

            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " mục đã được Cập Nhật thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql !" + ex.getMessage());
        }
    }

    @Override
    public void deleteCat(int id) {
        try {
            String SQLcreateCat = "{ call deleteCat(?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateCat);
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " mục đã được Xóa thành công !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql !" + ex.getMessage());
        }
    }

    @Override
    public void changeSTT(int id, int status) {
        try {
            String SQLcreateCat = "{ call changeSTTCat(?,?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateCat);
            int change;
            String message = "";
            if (status == 1) {
                change = 0;
                message = " Danh mục đã bị ẩn!";
            } else {
                change = 1;
                message = " Danh mục đã được hiển thị thành công !";
            }
            ps.setInt(1, change);
            ps.setInt(2, id);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + message);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql !" + ex.getMessage());
        }
    }

    @Override
    public List<Categorys> searchCat(String key) {
        List<Categorys> listSearch = new ArrayList<>();

        try {
            String SQLsearchCat = "{ call searchCat(?)}";
            PreparedStatement ps = con.prepareStatement(SQLsearchCat);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Categorys c = new Categorys(rs.getInt("id"), rs.getString("name"), rs.getInt("status"), rs.getString("date_created"), rs.getString("date_updated"));
                    listSearch.add(c);
                }
            return listSearch;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

}
