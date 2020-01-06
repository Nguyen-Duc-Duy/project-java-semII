/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emtitys;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class Employers {
//Trường thuộc tính
    int id;
    String name;
    String email;
    String pass;
    String avt;
    String phone;
    int status;
    int id_couter;
    String date_created;
    String date_updated;
//Hàm khởi tạo
    //  có id
    public Employers(int id, String name, String email, String pass, String avt, String phone, int status, String date_created, String date_updated,int id_couter) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.avt = avt;
        this.phone = phone;
        this.status = status;
        this.date_created = date_created;
        this.date_updated = date_updated;
        this.id_couter = id_couter;
    }
    //  không có id,có ngày tạo
    public Employers(String name, String email, String pass, String avt, String phone, int status, String date_created,int id_couter) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.avt = avt;
        this.phone = phone;
        this.status = status;
        this.date_created = date_created;
        this.id_couter = id_couter;
    }
    // có ngày cập nhật
    public Employers(int id, String name, String email, String pass, String avt, String phone, int status, int id_couter, String date_updated) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.avt = avt;
        this.phone = phone;
        this.status = status;
        this.id_couter = id_couter;
        this.date_updated = date_updated;
    }
    
    //  không có tham số
    public Employers() {
    }
//Phương thức set & get

    public int getId_couter() {
        return id_couter;
    }

    public void setId_couter(int id_couter) {
        this.id_couter = id_couter;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }
    
}
