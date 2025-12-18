/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modul10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author hary
 */
public class KoneksiDB {
    private static Connection mysqlconfig;
    
    public static Connection configDB() throws SQLException {
        try{
            // URL Database (Ganti 'root' dan '' sesuai dengan user dan pass database lokal anda
            String url = "jdbc:mysql://localhost:8889/kampus_db";
            String user = "root";
            String pass = "root";
            
            // Registrasi Driver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
            // Buat koneksi
            mysqlconfig = DriverManager.getConnection(url, user, pass);
            
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Koneksi gagal : " + e.getMessage());
        }
        return mysqlconfig;
    }
}
