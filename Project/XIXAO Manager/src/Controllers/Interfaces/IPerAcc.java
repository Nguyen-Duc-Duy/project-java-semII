/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.PersActions;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface IPerAcc {
    public List<PersActions> listPerAccById(int id_per);
    public void createPerAcc(PersActions pa);
    public void updatePerAcc(int id_per,int id_act,int id_act_new);
    public void deletePerAcc(int id_per, int id_act);
    
    
}
