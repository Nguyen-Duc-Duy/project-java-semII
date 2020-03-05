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
public class Actions {
//Trường thuộc tính
    int id;
    String name;
    String code;
    String date_created;
    String date_updated;
    int id_view;
//Hàn khởi tạo
    //có id

    public Actions(int id, String name, String code, String date_created, String date_updated, int id_view) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.date_created = date_created;
        this.date_updated = date_updated;
        this.id_view = id_view;
    }
    
    //không có id
    //không có tham số truyền vào
    public Actions() {
    }

    public Actions(int id, String name, int id_view) {
        this.id = id;
        this.name = name;
        this.id_view = id_view;
    }
    
//Phương thức set & get

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

    public int getId_view() {
        return id_view;
    }

    public void setId_view(int id_view) {
        this.id_view = id_view;
    }
    
}
