/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modul10.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.modul10.KoneksiDB;

/**
 *
 * @author hary
 */
public class MahasiswaModel {
    private String nama;
    private String NIM;
    private String jurusan;
    
    public MahasiswaModel(){}
    
    public MahasiswaModel(String nama, String NIM, String jurusan){
        this.nama = nama;
        this.NIM = NIM;
        this.jurusan = jurusan;
    }
    
    public String getNama(){
        return nama;
    }
    
    public String getNIM(){
        return NIM;
    }
    
    public String getJurusan(){
        return jurusan;
    }
    
    public void setNama(String nama){
        this.nama = nama;
    }
    
    public void setNIM(String NIM){
        this.NIM = NIM;
    }
    
    public void setJurusan(String jurusan){
        this.jurusan = jurusan;
    }
    
    public static List<MahasiswaModel> getAll() {
        List<MahasiswaModel> listMahasiswa = new ArrayList<>();
        try {
            Connection conn = KoneksiDB.configDB();
            String sql = "SELECT * FROM mahasiswa";
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            
            while (res.next()) {
                // Bungkus data database ke dalam objek Mahasiswa
                MahasiswaModel m = new MahasiswaModel(
                    res.getString("nama"),
                    res.getString("nim"),
                    res.getString("jurusan")
                );
                listMahasiswa.add(m); // Masukkan ke list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMahasiswa;
    }
    
    public void insert() throws Exception {
        Connection conn = KoneksiDB.configDB();
        
        // Cek dulu apakah NIM sudah ada
        String sqlCek = "SELECT * FROM mahasiswa WHERE nim = ?";
        PreparedStatement pstCek = conn.prepareStatement(sqlCek);
        pstCek.setString(1, this.NIM);
        ResultSet resCek = pstCek.executeQuery();
        
        if (resCek.next()) {
            throw new Exception("NIM " + this.NIM + " sudah terdaftar!");
        }

        // Jika aman, lakukan Insert
        String sql = "INSERT INTO mahasiswa (nama, nim, jurusan) VALUES (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.nama);
        pst.setString(2, this.NIM);
        pst.setString(3, this.jurusan);
        pst.execute();
    }
    
    public void update(String nimLama) throws Exception {
        Connection conn = KoneksiDB.configDB();
        String sql = "UPDATE mahasiswa SET nama = ?, jurusan = ?, nim = ? WHERE nim = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.nama);
        pst.setString(2, this.jurusan);
        pst.setString(3, this.NIM);     // NIM Baru
        pst.setString(4, nimLama);      // NIM Lama (untuk WHERE)
        pst.executeUpdate();
    }

    
    public void delete() throws Exception {
        Connection conn = KoneksiDB.configDB();
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, this.NIM);
        pst.execute();
    }
    
    public static List<MahasiswaModel> cari(String keyword) {
        List<MahasiswaModel> listMahasiswa = new ArrayList<>();
        try {
            Connection conn = KoneksiDB.configDB();
            String sql = "SELECT * FROM mahasiswa WHERE nama LIKE ? OR nim LIKE ? OR jurusan LIKE ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            String key = "%" + keyword + "%";
            pst.setString(1, key);
            pst.setString(2, key);
            pst.setString(3, key);
            
            ResultSet res = pst.executeQuery();
            
            while (res.next()) {
                MahasiswaModel m = new MahasiswaModel(
                    res.getString("nama"),
                    res.getString("nim"),
                    res.getString("jurusan")
                );
                listMahasiswa.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listMahasiswa;
    }
}
