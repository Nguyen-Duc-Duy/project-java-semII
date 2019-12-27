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
public class UersPers {
//Trường thuộc tính
    int id_user;
    int id_per;
    String date_created;
    String date_updated;
//Phương thức khởi tạo
    public UersPers(int id_user, int id_per, String date_created, String date_updated) {
        this.id_user = id_user;
        this.id_per = id_per;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }
    //không có tham số
    public UersPers() {
    }
//Phương thức khởi tạo

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_per() {
        return id_per;
    }

    public void setId_per(int id_per) {
        this.id_per = id_per;
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
