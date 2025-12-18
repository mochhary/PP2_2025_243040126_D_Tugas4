/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modul10;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 *
 * @author hary
 */
public class MahasiswaApp extends JFrame {
    
    // Komponen GUI
    JTextField txtName, txtNIM, txtJurusan, txtCari;
    JButton btnSimpan, btnHapus, btnClear, btnEdit, btnCari;
    JTable tableMahasiswa;
    DefaultTableModel model;
    
    public MahasiswaApp() {
        // Setup Frame
        setTitle("Aplikasi CRUD Mahasiswa JDBC");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 1. Panel Form (Input data)
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelForm.add(new JLabel("Nama : "));
        txtName = new JTextField();
        panelForm.add(txtName);
        
        panelForm.add(new JLabel("NIM : "));
        txtNIM = new JTextField();
        panelForm.add(txtNIM);
        
        panelForm.add(new JLabel("Jurusan : "));
        txtJurusan = new JTextField();
        panelForm.add(txtJurusan);
        
        // panel Tombol
        JPanel panelTombol = new JPanel(new FlowLayout());
        btnSimpan = new JButton("Simpan");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        
        panelTombol.add(btnSimpan);
        panelTombol.add(btnEdit);
        panelTombol.add(btnHapus);
        panelTombol.add(btnClear);
        
        panelTombol.add(new JLabel("| Cari: "));
        txtCari = new JTextField(10);
        btnCari = new JButton("Cari");
        
        panelTombol.add(txtCari);
        panelTombol.add(btnCari);
        
        // Gabungkan Panel Form dan tombol di bagian atas (North)
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);
        add(panelAtas, BorderLayout.NORTH);
        
        // 2. Gabungkan data (Menampilkan Data)
        model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("NIM");
        model.addColumn("Jurusan");
        
        tableMahasiswa = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableMahasiswa);
        add(scrollPane, BorderLayout.CENTER);
        
        // --- Event Listener ---
        // Listener klik table (untuk mengambil data saat baris diklik)
        tableMahasiswa.addMouseListener(new MouseAdapter(){
           @Override
           public void mouseClicked(MouseEvent e){
               int row = tableMahasiswa.getSelectedRow();
               txtName.setText(model.getValueAt(row, 1).toString());
               txtNIM.setText(model.getValueAt(row, 2).toString());
               txtJurusan.setText(model.getValueAt(row, 3).toString());
           }
        });
        
        // aksi tombol simpan
        btnSimpan.addActionListener(e -> tambahData());
        
        // aksi tombol edit
        btnEdit.addActionListener(e -> ubahData());
        
        // aksi tombol hapus
        btnHapus.addActionListener(e -> hapusData());
        
        // aksi tombol clear
        btnClear.addActionListener(e -> {
            kosongkanForm();
            loadData(); // Tampilkan semua data lagi saat clear
        });
        
        // Aksi tombol cari
        btnCari.addActionListener(e -> cari());
        
        // Load data saat aplikasi pertama kali jalan
        loadData(); 
    }
    
    // --- LOGIKA CRUD ---
    
    // 1. READ 
    private void loadData() {
        model.setRowCount(0); // Reset table agar tidak double
        try{
            Connection conn = KoneksiDB.configDB();
            Statement stm = conn.createStatement();
            ResultSet res = stm.executeQuery("SELECT * FROM mahasiswa");
            
            int no = 1;
            while(res.next()){
                model.addRow(new Object[]{
                    no++,
                    res.getString("nama"),
                    res.getString("nim"),
                    res.getString("jurusan"),
                });
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Gagal Load Data : " + e.getMessage());
        }
    }
    
    // 2. CREATE 
    private void tambahData() {
        try {
            // Validasi input kosong
            if(txtName.getText().trim().isEmpty() || txtNIM.getText().trim().isEmpty()){
                throw new Exception("Nama atau NIM tidak boleh kosong!");
            }
            
            Connection conn = KoneksiDB.configDB();

            // Pengecekan NIM Duplikat
            String cekNIM = "SELECT * FROM mahasiswa WHERE nim = ?";
            PreparedStatement pstCek = conn.prepareStatement(cekNIM);
            pstCek.setString(1, txtNIM.getText());
            ResultSet resCek = pstCek.executeQuery();            
            
            if(resCek.next()){
                throw new Exception("NIM " + txtNIM.getText() + " sudah terdaftar! Silakan gunakan NIM lain.");
            }
            
            // Proses Simpan
            String sql = "INSERT INTO mahasiswa (nama, nim, jurusan) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, txtName.getText());
            pst.setString(2, txtNIM.getText());
            pst.setString(3, txtJurusan.getText());
            
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan");
            loadData();
            kosongkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan : " + e.getMessage());
        }
    }
    
    // 3. UPDATE
    private void ubahData() {
        try {
            if(txtName.getText().trim().isEmpty() || txtNIM.getText().trim().isEmpty()){
                throw new Exception("Nama atau NIM tidak boleh kosong!");
            }
            
            String sql = "UPDATE mahasiswa SET nama = ?, jurusan = ? WHERE nim = ?";
            Connection conn = KoneksiDB.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setString(1, txtName.getText());
            pst.setString(2, txtJurusan.getText());
            pst.setString(3, txtNIM.getText());
            
            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diubah");
            loadData();
            kosongkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal edit : " + e.getMessage());
        }
    }
    
    // 4. DELETE
    private void hapusData() {
        try {
            if(txtName.getText().trim().isEmpty() || txtNIM.getText().trim().isEmpty()){
                throw new Exception("Nama atau NIM tidak boleh kosong!");
            }
            
            // Konfirmasi hapus
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data NIM " + txtNIM.getText() + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                String sql = "DELETE FROM mahasiswa WHERE nim = ?";
                Connection conn = KoneksiDB.configDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                
                pst.setString(1, txtNIM.getText());
                
                pst.execute();
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
                loadData();
                kosongkanForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus : " + e.getMessage());
        }
    }
    
    // 5. CARI
    private void cari(){
        model.setRowCount(0); // Reset table sebelum menampilkan hasil cari
        try {
           String keyword = txtCari.getText();
           String sql = "SELECT * FROM mahasiswa WHERE nama LIKE ? OR nim LIKE ? OR jurusan LIKE ?";
           Connection conn = KoneksiDB.configDB();
           PreparedStatement pst = conn.prepareStatement(sql);
           
           pst.setString(1, "%" + keyword + "%");
           pst.setString(2, "%" + keyword + "%");
           pst.setString(3, "%" + keyword + "%");
           
           ResultSet res = pst.executeQuery();
           
           int no = 1;
           while(res.next()){
               model.addRow(new Object[]{
                   no++,
                   res.getString("nama"),
                   res.getString("nim"),
                   res.getString("jurusan"),
               });
           }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Gagal cari data : " + e.getMessage());
        }
    }
    
    private void kosongkanForm(){
        txtName.setText(null);
        txtNIM.setText(null);
        txtJurusan.setText(null);
        txtCari.setText(null);
    }
    
    public static void main(String[] args) {
        // Menjalankan aplikasi
        SwingUtilities.invokeLater(() -> new MahasiswaApp().setVisible(true));
    }
}