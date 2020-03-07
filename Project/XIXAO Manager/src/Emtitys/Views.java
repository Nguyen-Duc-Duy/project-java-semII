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
public class Views {
//Trường thuộc tính
    int id;
    String name;
    String code;
    String date_created;
    String date_updated;
//Phương thức khởi tạo

    public Views(int id, String name, String code, String date_created, String date_updated) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }
    
    //không có tham số
    public Views() {
    }
//Phương thức khởi tạo

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
