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
public class Categorys {
    int id;
    String name;
    int status;
    String date_created;
    String date_updated;

    public Categorys(int id, String name, int status, String date_created, String date_updated) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Categorys(String name, int status, String date_created) {
        this.name = name;
        this.status = status;
        this.date_created = date_created;
    }

    public Categorys(int id, String name, int status, String date_updated) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.date_updated = date_updated;
    }

    public Categorys() {
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
