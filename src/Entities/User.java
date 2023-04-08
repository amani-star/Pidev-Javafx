/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class User {
    private int id;
    private String username, email, roles, password, nom, prenom;
    private int num_tel;
    private Date date_naissance;
    private boolean is_Banned=false;
    private boolean is_Verified=true;
    //public static User Current_User;

    //sans param
    public User() {
    }
    
    
    //avec tout les param & sans isBlocked
    public User(int id, String username, String email, String roles, String password, String nom, String prenom, int num_Tel, Date date_naissance) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_Tel;
        this.date_naissance = date_naissance;
    }
    
    //sans id & sans role & sans isBlocked
    public User(String username, String email, String password, String nom, String prenom, int num_Tel, Date date_naissance) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_Tel;
        this.date_naissance = date_naissance;
    }
    
    //sans id & sans role & sans pwd & sans isBlocked
    public User(String username, String email, String nom, String prenom, int num_Tel, Date date_naissance) {
        this.username = username;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_Tel;
        this.date_naissance = date_naissance;
    }
    
    //sans role & sans isBlocked
    public User(int id, String username, String email, String password, String nom, String prenom, int num_tel, Date date_naissance) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_tel;
        this.date_naissance = date_naissance;
    }
    
    //seulement id,password ,nom,prenom, num_tel, date_naissance
    public User(int id, String password, String nom, String prenom, int num_tel, Date date_naissance) {
        this.id = id;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_tel;
        this.date_naissance = date_naissance;
    }
    
    //seulement id,nom,prenom, num_tel, date_naissance
    public User(int id, String nom, String prenom, int num_tel, Date date_naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_tel;
        this.date_naissance = date_naissance;
    }
    
    //avec seulement id
    public User(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    /*public static User getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }*/

    public boolean isIsBanned() {
        return is_Banned;
    }

    public void setIsBanned(boolean isBanned) {
        this.is_Banned = isBanned;
    }

    public boolean isIsVerified() {
        return is_Verified;
    }

    public void setIsVerified(boolean isVerified) {
        this.is_Verified = isVerified;
    }
    
    

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", email=" + email + ", roles=" + roles + ", password=" + password + ", nom=" + nom + ", prenom=" + prenom + ", num_Tel=" + num_tel + ", date_naissance=" + date_naissance + '}';
    }
         
}
