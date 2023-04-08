/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev_devup;

import Entities.User;
import Services.UserService;
import Utils.MyDB;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Pidev_DevUp {

    public static void main(String[] args) {
       UserService sp = new UserService();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaissance = null;
        try {
            dateNaissance = sdf.parse("2000-05-12");
        } catch (ParseException ex) {
            Logger.getLogger(Pidev_DevUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        User u = new User(5,"foulen", "foulen@example.com", "testtest", "benfoulen", "Doe", 1234567890, dateNaissance);
        User u1 = new User("jia", "eyaaa@gmail.com", "testest", "araari", "eya", 21548685, dateNaissance);
        //sp.ajouter(u1);
        //sp.supprimer(u1);
        //sp.authentification("marwa", "Marwatest1");
        //sp.login("marwa", "Marwatest1");
        sp.login("jia", "testest");
        //sp.modifier(u);
        //System.out.println(sp.afficheUser(49));
        //System.out.println(sp.afficherTrie());
        System.out.println(sp.afficher());
        //sp.checkPass("testtest");
        //sp.banUser(51);
    }

}
