/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.Products;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface IProduct {
    public List<Products> getAll();
    public void createPro(Products p);
    public void updatePro(Products p);
    public void changeSTT(int id, int status);
    public void deletePro(int id);
    public void printPro();
}
