/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.Actions;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface IActions {
    public List<Actions> listActById_view(int id_view);
    
}
