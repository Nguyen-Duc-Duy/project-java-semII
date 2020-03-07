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
public class PersActions {
//Trường thuộc tính
    int id_per;
    int id_act;
    String date_created;
    String date_updated;
    
//Hàm tạo
    public PersActions(int id_per, int id_act, String date_created, String date_updated) {
        this.id_per = id_per;
        this.id_act = id_act;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public PersActions(int id_per, int id_act) {
        this.id_per = id_per;
        this.id_act = id_act;
    }
    
    //không có tham số
    public PersActions() {
    }
    
//Phương thức set & get
    public int getId_per() {
        return id_per;
    }

    public void setId_per(int id_per) {
        this.id_per = id_per;
    }

    public int getId_act() {
        return id_act;
    }

    public void setId_act(int id_act) {
        this.id_act = id_act;
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
