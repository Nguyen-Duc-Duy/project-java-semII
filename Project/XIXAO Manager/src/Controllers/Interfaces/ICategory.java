/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.Categorys;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface ICategory {
    public List<Categorys> getAll();
    public void createCat(Categorys c);
    public void updateCat(Categorys c);
    public void changeSTT(int id,int status);
    public void deleteCat(int id);
    public void printCat();
}
