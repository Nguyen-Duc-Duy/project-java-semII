/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.Employers;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface IEmployees {
    public List<Employers> getAllEm();
    public void createEm(Employers e);
    public void updateEm(Employers e);
    public void changeSTT(int id,int status);
    public void deleteEm(int id);
    public Employers getEmById(int id);
}
