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
    public List<Employers> getEm();
    public boolean createEm(Employers e);
    public boolean updateEm(Employers e);
    public boolean changeSTT(int id,int status);
    public boolean deleteEm(int id);
    public Employers getEmById(int id);
    public List<Employers> listEmInNewDate();
    public List<Employers> listEmByGP(int id_per);
}
