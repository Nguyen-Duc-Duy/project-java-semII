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
public class ActionViewModel {
    int id;
    String name;
    int id_view;
    boolean status;
    public ActionViewModel(int id, String name, int id_view,boolean  status) {
        this.id = id;
        this.name = name;
        this.id_view = id_view;
        this.status = status;
    }

    public ActionViewModel() {
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

    public int getId_view() {
        return id_view;
    }

    public void setId_view(int id_view) {
        this.id_view = id_view;
    }
    
}
