/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IViews;
import Emtitys.Categorys;
import Emtitys.Views;
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
public class ViewsDAO implements IViews{
    Connection con;

    public ViewsDAO(Connection c) {
        this.con = c;
    }
    
    @Override
    public List<Views> getAll() {
        List<Views> listViews = new ArrayList<>();
        try {
            String SQLselectCat = "{ call selectAllViews }";
            PreparedStatement ps = con.prepareStatement(SQLselectCat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Views v = new Views(rs.getInt("id"), rs.getString("name"), rs.getString("code"), rs.getString("date_created"), rs.getString("date_updated"));
                listViews.add(v);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi hệ thống xin thử lại !");
            System.out.println("lỗi csdl cat" + ex.getMessage());
        }
        return listViews;
    }
    
}
