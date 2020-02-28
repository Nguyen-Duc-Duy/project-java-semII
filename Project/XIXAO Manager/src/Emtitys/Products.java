/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emtitys;

import java.io.Serializable;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class Products implements Serializable,Comparable<Products>{
    int id;
    String name;
    int id_cat;
    int code;
    float price;
    float sale;
    String descript;
    int quantity;
    String img;
    int id_unit;
    int status;
    String date_crated;
    String date_updated;

    public Products(int id, String name,int id_cat,int code, float price, float sale, String descript, int quantity, String img, int id_unit, int status, String date_crated, String date_updated) {
        this.id = id;
        this.name = name;
        this.id_cat = id_cat;
        this.code = code;
        this.price = price;
        this.sale = sale;
        this.descript = descript;
        this.quantity = quantity;
        this.img = img;
        this.id_unit = id_unit;
        this.status = status;
        this.date_crated = date_crated;
        this.date_updated = date_updated;
    }

    public Products(String name, int id_cat,int code, float price, float sale, String descript, int quantity, String img,int id_unit, int status, String date_crated) {
        this.name = name;
        this.id_cat = id_cat;
        this.code = code;
        this.price = price;
        this.sale = sale;
        this.descript = descript;
        this.quantity = quantity;
        this.img = img;
        this.id_unit = id_unit;
        this.status = status;
        this.date_crated = date_crated;
    }

    public Products(int id, String name, int id_cat, float price, float sale, String descript, int quantity, String img, int id_unit, int status, String date_updated) {
        this.id = id;
        this.name = name;
        this.id_cat = id_cat;
        this.price = price;
        this.sale = sale;
        this.descript = descript;
        this.quantity = quantity;
        this.img = img;
        this.id_unit = id_unit;
        this.status = status;
        this.date_updated = date_updated;
    }

    public Products() {
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


    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSale() {
        return sale;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId_unit() {
        return id_unit;
    }

    public void setId_unit(int id_unit) {
        this.id_unit = id_unit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate_crated() {
        return date_crated;
    }

    public void setDate_crated(String date_crated) {
        this.date_crated = date_crated;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    @Override
    public String toString() {
        return "Products{" + "id=" + id + ", name=" + name + ", id_cat=" + id_cat + ", code=" + code + ", price=" + price + ", sale=" + sale + ", descript=" + descript + ", quantity=" + quantity + ", img=" + img + ", id_unit=" + id_unit + ", status=" + status + ", date_crated=" + date_crated + ", date_updated=" + date_updated + '}';
    }

    @Override
    public int compareTo(Products o) {
        return o.getName().compareToIgnoreCase(this.getName());
    }




    
    
}
