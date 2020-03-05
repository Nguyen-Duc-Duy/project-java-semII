/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IActions;
import Emtitys.Actions;
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
public class ActionsDAO implements IActions{
    Connection con;

    public ActionsDAO(Connection c) {
        this.con = c;
    }
    
    @Override
    public List<Actions> listActById_view(int id_view) {
        List<Actions> listAct = new ArrayList<>();
        try {
            String SQLselectCat = "{ call selectActByView(?) }";
            PreparedStatement ps = con.prepareStatement(SQLselectCat);
            ps.setInt(1, id_view);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Actions a = new Actions(rs.getInt("id"), rs.getString("name"), rs.getString("code"), rs.getString("date_created"), rs.getString("date_updated"), rs.getInt("id_view"));
                listAct.add(a);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "lỗi hệ thống xin thử lại !");
            System.out.println("lỗi csdl actions" + ex.getMessage());
        }
        return listAct;
    }
    
}
