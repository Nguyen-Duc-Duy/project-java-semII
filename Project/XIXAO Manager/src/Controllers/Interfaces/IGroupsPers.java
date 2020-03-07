/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.GroupsPers;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface IGroupsPers {
    public List<GroupsPers> getAll();
    public boolean createGroupsPer(GroupsPers gp);
    public void updateGroupsPer(GroupsPers gp);
    public void ChangeSTTGroupsPer(int id,int status);
    public List<String> listPerActByEm(int id_e);
    public List<String> listActByEm(int id_e);
}
