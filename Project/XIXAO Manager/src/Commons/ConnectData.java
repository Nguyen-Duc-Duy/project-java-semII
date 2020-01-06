/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class ConnectData {

    Connection connect;

    public Connection Connecting() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection("jdbc:sqlserver://localhost\\DESKTOP-2UE24A8\\SQLEXPRESS:1433;databaseName=XiXaoManger", "sa", "1234$");
            System.out.println("Đang kết nối csdl !");
            return connect;
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! vui lòng thử lại sau !");
            System.out.println("Lỗi thư viện jdbc !" + ex.toString());
            return null;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi hệ thống ! vui lòng thử lại sau !");
            System.out.println("Lỗi kết nối csdj !" + ex.toString());
            return null;

        }

    }
}
