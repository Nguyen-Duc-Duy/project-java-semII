/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.Units;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface IUnits {
    public List<Units> getAll();
    public void createUnit(Units u);
    public void updateUnit(Units u);
    public void deleteUnit(int id);
    public void printUnit();
}
