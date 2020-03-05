/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IGroupsPers;
import Emtitys.Categorys;
import Emtitys.GroupsPers;
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
public class GroupsPerDAO implements IGroupsPers{
    Connection con;

    public GroupsPerDAO(Connection con) {
        this.con = con;
    }
    
    @Override
    public List<GroupsPers> getAll() {
        List<GroupsPers> listGP = new ArrayList<>();
        try {
            String SQLselectCat = "{ call selectAllGP }";
            PreparedStatement ps = con.prepareStatement(SQLselectCat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GroupsPers c = new GroupsPers(rs.getInt("id"), rs.getString("name"), rs.getString("date_created"), rs.getString("date_updated"),rs.getInt("status"));
                listGP.add(c);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi hệ thống xin thử lại !");
            System.out.println("lỗi csdl cat" + ex.getMessage());
        }
        return listGP;
    }

    @Override
    public boolean createGroupsPer(GroupsPers gp) {
         try {
            String SQLcreateCat = "{ call createGP(?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateCat);
            ps.setString(1, gp.getName());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Thêm Mới thành công !");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql tạo mới groupsPers !" + ex.getMessage());
            return false;
        }
    }

    @Override
    public void updateGroupsPer(GroupsPers gp) {
        try {
            String SQLcreateCat = "{ call updateGP(?,?,?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateCat);
            ps.setInt(1, gp.getId());
            ps.setString(2, gp.getName());
            ps.setInt(3, gp.getStatus());
            
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " mục đã được Cập Nhật thành công !");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql groupsPers!" + ex.getMessage());
        }
    }

    @Override
    public void ChangeSTTGroupsPer(int id,int status) {
        
        try {
            String SQLcreateCat = "{ call changeSTTGroupsPers(?,?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreateCat);
            int newSTT;
            String message;
            if (status == 1) {
                newSTT = 0;
                message = "Nhóm quyền đã bị dừng !";
            } else {
                newSTT = 1;
                message = "Nhóm quyền đã được kích hoạt !";
            }
            ps.setInt(1,id);
            ps.setInt(2, newSTT);
            
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, message);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! Xin thử lại sau !");
            System.out.println("lỗi lệnh sql sgroupsPers!" + ex.getMessage());
        }        
                
    }
    public boolean checkPerPAY(int id_per){
        try {
            String SQLcheckPer = "{ call checkPer(?)}";
            PreparedStatement ps = con.prepareStatement(SQLcheckPer);
            ps.setInt(1, id_per);
            ResultSet rs = ps.executeQuery();
            boolean check = false;
            while (rs.next()) {   
                if(rs.getString("code").contains("PAY")){
                    check = true;
                }
            }
            return check;
        } catch (SQLException ex) {
            System.out.println("lối truy vấn sql kiển tra quyền thanh toán đơn hàng");
            return false;
        }
    }

    @Override
    public List<String> listPerActByEm(int id_e) {
        List<String> listPAByEm = new ArrayList<>();
        try {
            
            String SQLselectPAByEm = "{ call selectPerActByEm (?)}";
            PreparedStatement ps = con.prepareStatement(SQLselectPAByEm);
            ps.setInt(1, id_e);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                listPAByEm.add(rs.getString("code"));
            }
            
        } catch (SQLException ex) {
            System.out.println("Lỗi thực thi sql "+ex.getMessage());
        }
        return listPAByEm;
    }

    @Override
    public List<String> listActByEm(int id_e) {
        List<String> listAByEm = new ArrayList<>();
        try {
            
            String SQLselectPAByEm = "{ call selectActByEm (?)}";
            PreparedStatement ps = con.prepareStatement(SQLselectPAByEm);
            ps.setInt(1, id_e);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                listAByEm.add(rs.getString("code"));
            }
            
        } catch (SQLException ex) {
            System.out.println("Lỗi thực thi sql "+ex.getMessage());
        }
        return listAByEm;
    }
    
}
