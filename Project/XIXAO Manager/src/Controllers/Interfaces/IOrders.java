/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Interfaces;

import Emtitys.Orders;
import java.util.List;

/**
 *
 * @author NGUYEN DUC DUY
 */
public interface IOrders {
    public List<Orders> getAll();
    public void createOrder(Orders o,int id_em);
    public void updateOrder(Orders o);
    public void deleteOrder(int id);
    public List<Orders> getAllByEm(int id_em);
    public Orders selectById(int id);
}
