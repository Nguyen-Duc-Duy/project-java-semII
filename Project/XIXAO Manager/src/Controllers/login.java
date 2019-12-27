/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.sql.Connection;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class login {
    Connection c;
//hàm khởi tạo
    public login(Connection c) {
        this.c = c;
    }
//phương thức kiểm tra thôgn tin tài khoản
    public void checkLogin(String email, String pass){
    }
}
