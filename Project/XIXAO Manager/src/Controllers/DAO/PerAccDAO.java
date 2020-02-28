/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.DAO;

import Controllers.Interfaces.IPerAcc;
import Emtitys.PersActions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class PerAccDAO implements IPerAcc {

    Connection con;

    public PerAccDAO(Connection c) {
        this.con = c;
    }

    @Override
    public List<PersActions> listPerAccById(int id_per) {
        try {
            List<PersActions> listPA = new ArrayList<>();
            String SQLselectAllById = "{ call selectPerActionById(?) }";
            PreparedStatement ps = con.prepareStatement(SQLselectAllById);
            ps.setInt(1, id_per);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PersActions p = new PersActions(rs.getInt("id_per"), rs.getInt("id_act"));
                listPA.add(p);
            }
            return listPA;
        } catch (SQLException ex) {
            System.out.println("Lỗi select PerActions " + ex.getMessage());
            return null;
        }

    }

    @Override
    public void createPerAcc(PersActions pa) {
        try {
            String SQLcreatePerAct = "{ call createPerAction(?,?)}";
            PreparedStatement ps = con.prepareStatement(SQLcreatePerAct);
            ps.setInt(1, pa.getId_per());
            ps.setInt(2, pa.getId_act());
            ps.execute();
            System.out.println("A perAct added");
        } catch (SQLException ex) {
            System.out.println("Lỗi create PerActions " + ex.getMessage());
        }

    }

    @Override
    public void deletePerAcc(int id_per, int id_act) {
        try {
            String SQupdatePA = "{ call deletePerAction(?,?)}";
            PreparedStatement ps = con.prepareStatement(SQupdatePA);
            ps.setInt(1, id_per);
            ps.setInt(2, id_act);
            ps.execute();
            System.out.println("Delete peraction success");
        } catch (SQLException ex) {
            System.out.println("Lỗi Xóa perActions !" + ex.getMessage());
        }
    }

    @Override
    public void updatePerAcc(int id_per, int id_act,int id_act_new) {
        try {
            String SQupdatePA = "{ call updatePerAction(?,?,?)}";
            PreparedStatement ps = con.prepareStatement(SQupdatePA);
            ps.setInt(1, id_per);
            ps.setInt(2, id_act);
            ps.setInt(3, id_act_new);
            ps.execute();
            System.out.println("update perAtion success");
        } catch (SQLException ex) {
            System.out.println("Lỗi cập nhật perActions !" + ex.getMessage());
        }
    }

}
