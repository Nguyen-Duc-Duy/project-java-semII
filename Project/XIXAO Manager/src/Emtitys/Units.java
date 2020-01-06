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
public class Units {
    int id;
    String name;
    String date_created;
    String date_updated;

    public Units(int id, String name, String date_created, String date_updated) {
        this.id = id;
        this.name = name;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Units(String name, String date_created) {
        this.name = name;
        this.date_created = date_created;
    }

    public Units(int id, String name, String date_updated) {
        this.id = id;
        this.name = name;
        this.date_updated = date_updated;
    }

    public Units() {
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
