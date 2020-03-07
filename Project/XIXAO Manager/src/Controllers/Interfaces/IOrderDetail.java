/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.OrderDetails;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface IOrderDetail {
    public List<OrderDetails> listOD(int id);
    public void createOD (OrderDetails od);
    public void updateOD (OrderDetails od);
    public void deleteOD (OrderDetails od);
}
