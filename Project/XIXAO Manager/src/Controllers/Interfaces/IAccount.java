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
public interface IAccount {
    public List<Employers> getAll();
    public void createAcc(Employers e);
    public void updateAcc(Employers e);
    public void deleteAcc(int id);
    public Employers login(String email, String pass);
}
