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
public class OrderDetails {
    int id_order;
    int id_pro;
    int quantity;

    public OrderDetails(int id_order, int id_pro, int quantity) {
        this.id_order = id_order;
        this.id_pro = id_pro;
        this.quantity = quantity;
    }

    public OrderDetails() {
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
