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
public class Orders {
    int id;
    int id_employee;
    int status;
    int code;
    String date_created;
    String date_updated;

    public Orders(int id, int id_employee, int status, int code, String date_created, String date_updated) {
        this.id = id;
        this.id_employee = id_employee;
        this.status = status;
        this.code = code;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    public Orders(int id_employee, int status, int code) {
        this.id_employee = id_employee;
        this.status = status;
        this.code = code;
    }

    public Orders(int id, int id_employee, int status, int code, String date_updated) {
        this.id = id;
        this.id_employee = id_employee;
        this.status = status;
        this.code = code;
        this.date_updated = date_updated;
    }

    public Orders() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
