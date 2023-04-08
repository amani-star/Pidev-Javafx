/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.User;
import Utils.MyDB;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ASUS
 */
public class UserService implements IService<User> {

    Connection cnx;
    //String password="testtest";
    //private String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(13));

    public UserService() {
        cnx = MyDB.getInstance().getCnx();
    }

    //afficher tout les users
    @Override
    public List<User> afficher() {
        List<User> users = new ArrayList();
        //String roles = Arrays.toString(new String[]{"ROLE_GAMER"});
        String qry = "SELECT * FROM `user` where roles='[\"ROLE_GAMER\"]'";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setRoles(rs.getString("roles"));
                u.setPassword(rs.getString("password"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setNum_tel(rs.getInt("num_Tel"));
                u.setDate_naissance(rs.getDate("date_naissance"));
                users.add(u); // add the user to the list
            }
            return users;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

    //afficher  user par son id
    public User afficheUser(int id) {
        User u = new User();
        try {
            String req = "Select * from  `user` where id=" + id;
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            rs.next();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString(2));
            u.setEmail(rs.getString(3));
            u.setRoles(rs.getString(4));
            u.setPassword(rs.getString(5));
            u.setNom(rs.getString(6));
            u.setPrenom(rs.getString(7));
            u.setNum_tel(rs.getInt(8));
            u.setDate_naissance(rs.getDate(9));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    //contole de saisie
    public static boolean estChaineValide(String chaine) {
        // Vérifier si la chaîne est vide ou nulle
        if (chaine == null || chaine.trim().isEmpty()) {
            return false;
        }

        // Vérifier si la chaîne ne contient que des lettres
        if (!chaine.matches("[a-zA-Z ]+")) {
            return false;
        }

        // La chaîne est valide si elle passe toutes les vérifications
        return true;
    }

    //encodage du mot de passe en sha-256
    public static String HashagePassword(String password) {
        /*try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        // handle exception
    }
    return null;*/
    /*    String hashedPassword = null;
        try {
            String salt = BCrypt.gensalt(13);
            hashedPassword = BCrypt.hashpw(password, salt);
        } catch (IllegalArgumentException e) {
            System.err.println("Error hashing password: " + e.getMessage());
        }*/
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(13));
        return hashedPassword;
    }
    
    public void checkPass(String password){
         
        System.out.println(HashagePassword(password));
        if (BCrypt.checkpw(password, HashagePassword(password))){
            System.out.println("matches");
        }else{
            System.out.println("doesn't matches");
        }
    }

    //ajouter our sign up d'un user
    @Override
    public void ajouter(User t) {
        try {
            String req = "INSERT INTO  `user`(`id`, `username`, `email`, `roles`,`password`, `nom`, `prenom`,`num_Tel`, `date_naissance`, `is_banned`) VALUES (?,?,?,?,?,?,?,?,?,?)";
            if (estChaineValide(t.getNom()) && estChaineValide(t.getPrenom())) {

                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setInt(1, t.getId());
                ps.setString(2, t.getUsername());
                ps.setString(3, t.getEmail());
                ps.setString(4, "[\"ROLE_GAMER\"]");
                ps.setString(5, HashagePassword(t.getPassword()));
                ps.setString(6, t.getNom());
                ps.setString(7, t.getPrenom());
                ps.setInt(8, t.getNum_tel());
                ps.setDate(9, new java.sql.Date(t.getDate_naissance().getTime()));
                ps.setBoolean(10, false);
                ps.executeUpdate();
                System.out.println("Utilisateur inséré");
            } else {
                System.out.println("Nom ou prénom invalide");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //authentification d'un user
    public User authentification(String email, String password) {
        User p = null;
        try {
            String req = "Select * from  `user` where email ='" + email + "' AND password ='" + HashagePassword(password) + "'";
            Statement st = cnx.createStatement();
            ResultSet RS = st.executeQuery(req);
            RS.next();
            p.setId(RS.getInt("id"));
            p.setUsername(RS.getString("username"));
            p.setEmail(RS.getString("email"));
            p.setRoles(RS.getString("roles"));
            p.setPassword(RS.getString("password"));
            p.setNom(RS.getString("nom"));
            p.setPrenom(RS.getString("prenom"));
            p.setNum_tel(RS.getInt("num_tel"));
            p.setDate_naissance(RS.getDate("date_naissance"));
            System.out.println("successful login!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return p;
    }

    //modifier des donner d'un utilisateur
    @Override
    public void modifier(User t) {
        try {
            String req = "UPDATE `user` SET `username`=?,`email`=?,`roles`=?,`password`=?,`nom`=?,`prenom`=?,`num_Tel`=?,`date_naissance`=? WHERE `user`.`id` = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, t.getUsername());
            ps.setString(2, t.getEmail());
            ps.setString(3, "gamer");
            ps.setString(4, HashagePassword(t.getPassword()));
            ps.setString(5, t.getNom());
            ps.setString(6, t.getPrenom());
            ps.setInt(7, t.getNum_tel());
            ps.setDate(8, new java.sql.Date(t.getDate_naissance().getTime()));
            ps.setInt(9, t.getId());
            ps.executeUpdate();
            System.out.println("Utilisateur mis a jour");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierAvecPassword(User t) {
        try {

            String req = "UPDATE `user` SET `password`=?,`nom`=?,`prenom`=?,`num_Tel`=?,`date_naissance`=? WHERE `user`.`id` = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, HashagePassword(t.getPassword()));
            ps.setString(2, t.getNom());
            ps.setString(3, t.getPrenom());
            ps.setInt(4, t.getNum_tel());
            ps.setDate(5, new java.sql.Date(t.getDate_naissance().getTime()));
            ps.setInt(6, t.getId());
            ps.executeUpdate();
            System.out.println("Utilisateur mis a jour");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifierSansPassword(User t) {
        try {

            String req = "UPDATE `user` SET `nom`=?,`prenom`=?,`num_Tel`=?,`date_naissance`=? WHERE `user`.`id` = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(2, t.getNom());
            ps.setString(3, t.getPrenom());
            ps.setInt(4, t.getNum_tel());
            ps.setDate(5, new java.sql.Date(t.getDate_naissance().getTime()));
            ps.setInt(6, t.getId());
            ps.executeUpdate();
            System.out.println("Utilisateur mis a jour");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //supprimer un user 
    @Override
    public void supprimer(User t) {
        try {
            String req = "DELETE FROM `user` WHERE id = " + t.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Utilisateur supprimé");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Ban a user
    public void banUser(int id) {
        try {
            String qry = "UPDATE user SET is_Banned = CASE WHEN is_banned = 1 THEN 0 ELSE 1 END WHERE id = ?";
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, id);
            stm.executeUpdate();
            System.out.println("User with ID " + id + " has been banned/unbanned successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //recherche par email
    public User rechUtilisateurByMail(String email) {
        User u = new User();
        try {
            String req = "Select * from  `user` where email ='" + email + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            rs.next();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString(2));
            u.setEmail(rs.getString(3));
            u.setRoles(rs.getString(4));
            u.setPassword(rs.getString(5));
            u.setNom(rs.getString(6));
            u.setPrenom(rs.getString(7));
            u.setNum_tel(rs.getInt(8));
            u.setDate_naissance(rs.getDate(9));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    //recherche par username
    public User rechUtilisateurByUsername(String username) {
        User u = new User();
        try {
            String req = "Select * from  `user` where username ='" + username + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            rs.next();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString(2));
            u.setEmail(rs.getString(3));
            u.setRoles(rs.getString(4));
            u.setPassword(rs.getString(5));
            u.setNom(rs.getString(6));
            u.setPrenom(rs.getString(7));
            u.setNum_tel(rs.getInt(8));
            u.setDate_naissance(rs.getDate(9));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    //recherche par nom
    public User rechUtilisateurByNom(String nom) {
        User u = new User();
        try {
            String req = "Select * from  `user` where nom ='" + nom + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            rs.next();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString(2));
            u.setEmail(rs.getString(3));
            u.setRoles(rs.getString(4));
            u.setPassword(rs.getString(5));
            u.setNom(rs.getString(6));
            u.setPrenom(rs.getString(7));
            u.setNum_tel(rs.getInt(8));
            u.setDate_naissance(rs.getDate(9));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    //recherche par prenom
    public User rechUtilisateurByPrenom(String prenom) {
        User u = new User();
        try {
            String req = "Select * from  `user` where prenom ='" + prenom + "'";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            rs.next();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString(2));
            u.setEmail(rs.getString(3));
            u.setRoles(rs.getString(4));
            u.setPassword(rs.getString(5));
            u.setNom(rs.getString(6));
            u.setPrenom(rs.getString(7));
            u.setNum_tel(rs.getInt(8));
            u.setDate_naissance(rs.getDate(9));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    //Trie des users avec ses noms
    public List<User> afficherTrie() {
        List<User> users = new ArrayList();
        String qry = "SELECT * FROM `user` ORDER BY nom";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString(2));
                u.setEmail(rs.getString(3));
                u.setRoles(rs.getString(4));
                u.setPassword(rs.getString(5));
                u.setNom(rs.getString(6));
                u.setPrenom(rs.getString(7));
                u.setNum_tel(rs.getInt(8));
                u.setDate_naissance(rs.getDate(9));
                users.add(u); // add the user to the list
            }
            return users;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }

}
