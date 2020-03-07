/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IEmployees;
import Emtitys.Employers;
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
public class EmployeesDAO implements IEmployees {

    Connection con;

    public EmployeesDAO(Connection con) {
        this.con = con;
    }

    @Override
    public Employers getEmById(int id) {
        try {
            String SQLselectEmById = "{ call selectEmById(?)}";
            PreparedStatement ps = con.prepareStatement(SQLselectEmById);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Employers e = new Employers();
            while (rs.next()) {
                e = new Employers(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("pass"), rs.getString("avt"), rs.getString("phone"), rs.getInt("status"), rs.getInt("id_couter"), rs.getInt("id_per"), rs.getString("date_created"), rs.getString("date_updated"));
            }
            return e;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " select em by id");
            return null;
        }

    }

    @Override
    public List<Employers> getAllEm() {
        List<Employers> listEm = new ArrayList<>();
        try {
            String SQLselectAll = "{ CALL  selectAttEm }";
            PreparedStatement ps = con.prepareStatement(SQLselectAll);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employers e = new Employers(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("pass"), rs.getString("avt"), rs.getString("phone"), rs.getInt("status"), rs.getInt("id_couter"), rs.getInt("id_per"), rs.getString("date_created"), rs.getString("date_updated"));
                listEm.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listEm;
    }
    @Override
    public List<Employers> getEm() {
        List<Employers> listEm = new ArrayList<>();
        try {
            String SQLselectAll = "{ CALL  selectEm }";
            PreparedStatement ps = con.prepareStatement(SQLselectAll);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employers e = new Employers(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("pass"), rs.getString("avt"), rs.getString("phone"), rs.getInt("status"), rs.getInt("id_couter"), rs.getInt("id_per"), rs.getString("date_created"), rs.getString("date_updated"));
                listEm.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listEm;
    }
    @Override
    public boolean createEm(Employers e) {
        try {
            String SQLcreatePro = "{ call createEm(?,?,?,?,?,?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreatePro);
            ps.setString(1, e.getName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPass());
            ps.setString(4, e.getPhone());
            if (e.getId_couter() == 0) {
                 ps.setString(5, null);
            } else {
                 ps.setInt(5, e.getId_couter());
            }
            ps.setInt(6, e.getId_per());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " nhân viên đã được thêm !");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại ! Xin thử lại sau");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateEm(Employers e) {
        try {
            String SQLcreatePro = "{ call updateEm(?,?,?,?,?,?,?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreatePro);
            ps.setString(1, e.getName());
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getPass());
            ps.setString(4, e.getPhone());
            ps.setInt(5, e.getId_couter());
            ps.setInt(6, e.getId_per());
            ps.setInt(7, e.getId());
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, row + " nhân viên đã cập nhật!");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại ! Xin thử lại sau");
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean changeSTT(int id, int status) {
        try {
            String SQLdeletePro = "{ call changeSTTEm(?,?)}";
            PreparedStatement ps = con.prepareStatement(SQLdeletePro);
            int change;
            String message = "";
            if (status == 1) {
                change = 0;
                message = " Nhân viên đã mất quyền sử dụng phần mềm !";
            } else {
                change = 1;
                message = " Nhân viên có thể dụng phần mềm !";
            }
            ps.setInt(1, id);
            ps.setInt(2, change);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null,message);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "Thay đổi thất bại Xin thử lại sau !");
            System.out.println("Lỗi csdl !" + ex.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean deleteEm(int id) {
         try {
            String SQLdeletePro = "{ call deleteEm(?)}";
            PreparedStatement ps = con.prepareStatement(SQLdeletePro);

            ps.setInt(1, id);
            System.out.println(id);
            int row = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Xóa thành công !");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showConfirmDialog(null, "Xóa nhân viên thất bại Xin thử lại sau !");
            System.out.println("Lỗi csdl !" + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Employers> listEmInNewDate() {
        List<Employers> listEm = new ArrayList<>();
        try {
            String SQLselectAll = "{ CALL  selectEmInNewDate }";
            PreparedStatement ps = con.prepareStatement(SQLselectAll);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employers e = new Employers(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("pass"), rs.getString("avt"), rs.getString("phone"), rs.getInt("status"), rs.getInt("id_couter"), rs.getInt("id_per"), rs.getString("date_created"), rs.getString("date_updated"));
                listEm.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống. Xin thử lại sau !");
        }
        return listEm;
    }

    @Override
    public List<Employers> listEmByGP(int id_per) {
        List<Employers> listEm = new ArrayList<>();
         try {
            String SQLselectEmById = "{ call selectEmByGP(?)}";
            PreparedStatement ps = con.prepareStatement(SQLselectEmById);
            ps.setInt(1, id_per);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employers e = new Employers(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("pass"), rs.getString("avt"), rs.getString("phone"), rs.getInt("status"), rs.getInt("id_couter"), rs.getInt("id_per"), rs.getString("date_created"), rs.getString("date_updated"));
                listEm.add(e);
            }
            return listEm;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " select em by id_per");
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống. Xin thử lại sau !");
            return null;
        }
    }



}
