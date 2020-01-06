/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class MethodCommon {

    public MethodCommon() {
    }
    
//  hàm khởi tạo icom

    public <T> MethodCommon(Class<T> c, JFrame frame, String file) {
        frame.setIconImage(new ImageIcon(Class.class.getResource("/Commons/img/" + file)).getImage());
    }
// show popupmenu interat


}
