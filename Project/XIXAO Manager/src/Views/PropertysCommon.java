/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author NGUYEN DUC DUY
 */
public class PropertysCommon {
    public <T> PropertysCommon(Class<T> c,JFrame frame, String file){
        frame.setIconImage(new ImageIcon(Class.class.getResource("/Commons/img/"+file)).getImage());
    }
}
