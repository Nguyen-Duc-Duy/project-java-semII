/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IAccount;
import Emtitys.Employers;
import Emtitys.UersPers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class AccountDAO implements IAccount {

    Connection connect;
    
//hàm khởi tạo
    public AccountDAO(Connection c) {
        this.connect = c;
    }
    
//  lấy toàn bộ tài khoản
    @Override
    public List<Employers> getAll() {
       List<Employers> listEm = new ArrayList<>();
       return listEm;
    }
//  thêm mới tài khoản

    @Override
    public void createAcc(Employers e) {
    }
//  cập nhật tài khoản

    @Override
    public void updateAcc(Employers e) {
    }
//   xóa tài khoản

    @Override
    public void deleteAcc(int id) {
    }

//   đăng nhập
    @Override
    public Employers login(String email, String pass) {
        List<Employers> listEm = checkEmail(email);
        Employers employee=null;
        if (listEm != null) {
            for (Employers listEm1 : listEm) {
//            kiểm tra mật khẩu
                if (listEm1.getPass().equals(pass)) {
                    employee = listEm1;
                }
            }
        }
        return employee;
    }

//  kiểm tra email
//    kiểm tra tính hợp lệ
    public boolean rgEmail(String email) {
        String rgEmail = "^([0-9a-zA-Z]+)+(.[0-9a-zA-Z]+)*@([a-z]{3,}).([a-z]{2,})$";
        Pattern pattenr = Pattern.compile(rgEmail);
        Matcher matcher = pattenr.matcher(email);
        return matcher.find();
    }   
//    kiểm tra email trên csdl
    private List checkEmail(String email) {
        List<Employers> listEm = new ArrayList<>();
        if (rgEmail(email)) {
            try {
                String SQLcheckeMail = "{ CALL checkEmail(?) }";
                PreparedStatement ps = connect.prepareStatement(SQLcheckeMail);
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Employers e = new Employers(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("pass"), rs.getString("avt"), rs.getString("phone"), rs.getInt("status"), rs.getString("date_created"), rs.getString("date_updated"), rs.getInt("id_couter"));
                    listEm.add(e);
                }
            } catch (SQLException ex) {
                System.out.println("Lỗi thực thi sql ! " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Lỗi hệ thống, Xin thử lại sau !");
            }
        }
        return listEm;
    }
}
