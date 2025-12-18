/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modul10.controller;

/**
 *
 * @author hary
 */

import com.mycompany.modul10.model.MahasiswaModel;
import com.mycompany.modul10.view.MahasiswaView;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

public class MahasiswaController {
    
    private MahasiswaView view;
    private String nimLama = ""; // Untuk menyimpan NIM sebelum diedit

    public MahasiswaController(MahasiswaView view) {
        this.view = view;
        
        // --- 1. PASANG EVENT LISTENER ---
        // Menghubungkan tombol di View dengan method di Controller ini
        
        this.view.addSimpanListener(e -> simpanData());
        this.view.addEditListener(e -> ubahData());
        this.view.addHapusListener(e -> hapusData());
        this.view.addCariListener(e -> cariData());
        
        this.view.addClearListener(e -> {
            view.clearForm();
            loadData(); // Kembalikan tabel ke kondisi awal
        });
        
        // Listener saat baris tabel diklik
        this.view.addTabelListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTable().getSelectedRow();
                // Ambil data dari tabel dan taruh di form
                view.setNama(view.getModel().getValueAt(row, 1).toString());
                view.setNIM(view.getModel().getValueAt(row, 2).toString());
                view.setJurusan(view.getModel().getValueAt(row, 3).toString());
                
                // Simpan NIM lama (penting untuk proses Edit)
                nimLama = view.getModel().getValueAt(row, 2).toString();
            }
        });
        
        // Load data saat aplikasi pertama kali jalan
        loadData();
    }
    
    // --- 2. LOGIKA UTAMA ---
    
    private void loadData() {
        // Kosongkan tabel di View
        view.getModel().setRowCount(0);
        
        // Minta data ke Model (Mahasiswa.java)
        List<MahasiswaModel> data = MahasiswaModel.getAll();
        
        // Masukkan data ke Tabel View
        int no = 1;
        for (MahasiswaModel m : data) {
            view.getModel().addRow(new Object[]{
                no++, 
                m.getNama(), 
                m.getNIM(), 
                m.getJurusan()
            });
        }
    }
    
    private void simpanData() {
        // Validasi Input
        if (view.getNama().trim().isEmpty() || view.getNIM().trim().isEmpty()) {
            view.showMessage("Nama dan NIM tidak boleh kosong!");
            return;
        }
        
        try {
            // Bungkus data view ke objek Model
            MahasiswaModel m = new MahasiswaModel(view.getNama(), view.getNIM(), view.getJurusan());
            
            // Perintahkan Model untuk menyimpan dirinya sendiri
            m.insert();
            
            view.showMessage("Data Berhasil Disimpan!");
            loadData();      // Refresh tabel
            view.clearForm(); // Bersihkan form
            
        } catch (Exception e) {
            view.showMessage("Gagal Simpan: " + e.getMessage());
        }
    }
    
    private void ubahData() {
        // Validasi apakah sudah pilih data
        if (nimLama.isEmpty()) {
            view.showMessage("Pilih data yang akan diedit dari tabel!");
            return;
        }
        
        try {
            MahasiswaModel m = new MahasiswaModel(view.getNama(), view.getNIM(), view.getJurusan());
            
            // Perintahkan Model untuk update
            m.update(nimLama);
            
            view.showMessage("Data Berhasil Diubah!");
            loadData();
            view.clearForm();
            nimLama = ""; // Reset nimLama
            
        } catch (Exception e) {
            view.showMessage("Gagal Edit: " + e.getMessage());
        }
    }
    
    private void hapusData() {
        if (nimLama.isEmpty()) {
            view.showMessage("Pilih data yang akan dihapus!");
            return;
        }
        
        if (view.showConfirm("Yakin hapus data NIM " + view.getNIM() + "?")) {
            try {
                // Kita hanya butuh NIM untuk menghapus
                MahasiswaModel m = new MahasiswaModel();
                m.setNIM(nimLama);
                
                m.delete();
                
                view.showMessage("Data Berhasil Dihapus!");
                loadData();
                view.clearForm();
                nimLama = "";
                
            } catch (Exception e) {
                view.showMessage("Gagal Hapus: " + e.getMessage());
            }
        }
    }
    
    private void cariData() {
        String keyword = view.getKeywordCari();
        view.getModel().setRowCount(0);
        
        // Minta Model mencari data
        List<MahasiswaModel> data = MahasiswaModel.cari(keyword);
        
        int no = 1;
        for (MahasiswaModel m : data) {
            view.getModel().addRow(new Object[]{
                no++, 
                m.getNama(), 
                m.getNIM(), 
                m.getJurusan()
            });
        }
    }
}
