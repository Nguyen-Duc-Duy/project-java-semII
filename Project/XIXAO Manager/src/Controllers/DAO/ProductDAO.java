/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Commons.ConnectData;
import Controllers.Interfaces.IProduct;
import Emtitys.Products;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class ProductDAO implements IProduct {

    ConnectData c = new ConnectData();
    Connection connect = c.Connecting();

    public ProductDAO(Connection c) {

    }

    @Override
    public List<Products> getAll() {
        List<Products> listPro = new ArrayList<>();
        try {
            String SQLselectAll = "{ CALL  selectAllPro }";
            PreparedStatement ps = connect.prepareStatement(SQLselectAll);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products p = new Products(rs.getInt("id"), rs.getString("name"), rs.getInt("id_cat"), rs.getInt("code"), rs.getFloat("price"), rs.getFloat("sale"), rs.getString("descript"), rs.getInt("quantity"), rs.getString("img"), rs.getInt("id_unit"), rs.getInt("status"), rs.getString("date_crated"), rs.getString("date_updated"));
                listPro.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listPro;
    }

    @Override
    public void createPro(Products p) {
        try {
            String SQLcreatePro = "{ call createPro(?,?,?,?,?,?,?,?,?)}";
            PreparedStatement ps = connect.prepareStatement(SQLcreatePro);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getId_cat());
            ps.setFloat(3, p.getPrice());
            ps.setFloat(4, p.getSale());
            ps.setString(5, p.getDescript());
            ps.setInt(6, p.getQuantity());
            ps.setString(7, p.getImg());
            ps.setInt(8, p.getId_unit());
            ps.setInt(9, p.getCode());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " sản phẩm đã được thêm !");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại ! Xin thử lại sau");
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void updatePro(Products p) {
        try {
            String SQLcreatePro = "{ call updatePro(?,?,?,?,?,?,?,?,?)}";
            PreparedStatement ps = connect.prepareStatement(SQLcreatePro);
            ps.setString(1, p.getName());
            ps.setInt(2, p.getId_cat());
            ps.setFloat(3, p.getPrice());
            ps.setFloat(4, p.getSale());
            ps.setString(5, p.getDescript());
            ps.setInt(6, p.getQuantity());
            ps.setString(7, p.getImg());
            ps.setInt(8, p.getId_unit());
            ps.setInt(9, p.getId());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " sản phẩm đã được cập nhật !");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "cập nhật thất bại ! Xin thử lại sau");
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deletePro(int id) {
        try {
            String SQLdeletePro = "{ call deletePro(?)}";
            PreparedStatement ps = connect.prepareStatement(SQLdeletePro);

            ps.setInt(1, id);
            System.out.println(id);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Xóa thành công !");
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "Xóa thất bại Xin thử lại sau !");
            System.out.println("Lỗi csdl !" + ex.getMessage());
        }

    }

    @Override
    public void printPro() {

    }

    @Override
    public void changeSTT(int id, int status) {
        try {
            String SQLdeletePro = "{ call changeSTTPro(?,?)}";
            PreparedStatement ps = connect.prepareStatement(SQLdeletePro);
            int change;
            String message = "";
            if (status == 1) {
                change = 0;
                message = " Sản Phẩm đã bị ẩn!";
            } else {
                change = 1;
                message = " Sản Phẩm đã được hiển thị thành công !";
            }
            ps.setInt(1, id);
            ps.setInt(2, change);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + message);
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "Thay đổi thất bại Xin thử lại sau !");
            System.out.println("Lỗi csdl !" + ex.getMessage());
        }
    }

    @Override
    public Products selectProById(int id) {
        Products p= new Products();
        try {
            String SQLdeletePro = "{ call selectProById(?)}";
            PreparedStatement ps = connect.prepareStatement(SQLdeletePro);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p = new Products(rs.getInt("id"), rs.getString("name"), rs.getInt("id_cat"), rs.getInt("code"), rs.getFloat("price"), rs.getFloat("sale"), rs.getString("descript"), rs.getInt("quantity"), rs.getString("img"), rs.getInt("id_unit"), rs.getInt("status"), rs.getString("unit"), rs.getString("date_updated"));
            }
            return p;
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "Thay đổi thất bại Xin thử lại sau !");
            System.out.println("Lỗi csdl !" + ex.getMessage());
            return null;
        }
        
    }

    @Override
    public void changeQuantityPro(int id,int quantity) {
        Products p= new Products();
        try {
            String SQLdeletePro = "{ call changeQuantityPro(?,?)}";
            PreparedStatement ps = connect.prepareStatement(SQLdeletePro);

            ps.setInt(1, id);
            ps.setInt(2, quantity);
            int row = ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Lỗi csdl !" + ex.getMessage());
        }
    }

    @Override
    public List<Products>  sortToAToZ() {
        List<Products> listPro = this.getAll();
        Collections.sort(listPro);
        return listPro;
    }

    @Override
    public List<Products> listProInDay(String day) {
        List<Products> ListPro = new ArrayList<>();
        try {
            String SQLselectListInDay = "{ call getProByDay(?) }";
            PreparedStatement ps = connect.prepareStatement(SQLselectListInDay);
            ps.setString(1, day);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                Products p = new Products(rs.getInt("id"), rs.getString("name"), rs.getInt("id_cat"), rs.getInt("code"), rs.getFloat("price"), rs.getFloat("sale"), rs.getString("descript"), rs.getInt("quantity"), rs.getString("img"), rs.getInt("id_unit"), rs.getInt("status"), rs.getString("date_crated"), rs.getString("date_updated"));
                ListPro.add(p);
            }
            return ListPro;
        } catch (SQLException ex) {
            System.out.println("Lỗi lấy danh sách trong ngày "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống xin thử lại !");
            return ListPro;
        }
        
    }

    @Override
    public List<Products> listProOfWeek() {
        List<Products> listPro = new ArrayList<>();
        String SQLselectProOfWeek = "{ call listProOfWeek }";
        try {
            PreparedStatement ps = connect.prepareStatement(SQLselectProOfWeek);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Products p = new Products(rs.getInt("id"), rs.getString("name"), rs.getInt("id_cat"), rs.getInt("code"), rs.getFloat("price"), rs.getFloat("sale"), rs.getString("descript"), rs.getInt("quantity"), rs.getString("img"), rs.getInt("id_unit"), rs.getInt("status"), rs.getString("date_crated"), rs.getString("date_updated"));
                listPro.add(p);
            }
            return listPro;
        } catch (SQLException ex) {
             System.out.println("Lỗi lấy danh sách trong ngày "+ex.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống xin thử lại !");
            return listPro;
        }
        
    }
    
    

}
