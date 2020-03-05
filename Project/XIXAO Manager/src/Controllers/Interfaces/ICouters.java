/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.Couters;
import Emtitys.Employers;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface ICouters {
    public List<Couters> getAll();
    public void createCouter (Couters c);
    public void updateCouter(Couters c);
    public void changeSTTCouter(int id,int status);
    public List<Employers> showEmByCouter(int id_couter);
}
