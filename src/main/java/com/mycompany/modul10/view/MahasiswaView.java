/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modul10.view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author hary
 */
public class MahasiswaView extends JFrame{
    private JTextField txtNama, txtNIM, txtJurusan, txtCari;
    private JButton btnSimpan, btnEdit, btnHapus, btnClear, btnCari;
    private JTable tableMahasiswa;
    private DefaultTableModel model;
    
    public MahasiswaView(){
        setTitle("Aplikasi CRUD Mahasiswa JDBC - MVC");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelForm.add(new JLabel("Nama : "));
        txtNama = new JTextField();
        panelForm.add(txtNama);
        
        panelForm.add(new JLabel("NIM : "));
        txtNIM = new JTextField();
        panelForm.add(txtNIM);
        
        panelForm.add(new JLabel("Jurusan : "));
        txtJurusan = new JTextField();
        panelForm.add(txtJurusan);
        
        JPanel panelTombol = new JPanel(new FlowLayout());
        btnSimpan = new JButton("Simpan");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Clear");
        txtCari = new JTextField(10);
        btnCari = new JButton("Cari");
        
        panelTombol.add(btnSimpan);
        panelTombol.add(btnEdit);
        panelTombol.add(btnHapus);
        panelTombol.add(btnClear);
        panelTombol.add(new JLabel("| Cari : "));
        panelTombol.add(txtCari);
        panelTombol.add(btnCari);
        
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);
        add(panelAtas, BorderLayout.NORTH);
        
        model = new DefaultTableModel();
        model.addColumn("No");
        model.addColumn("Nama");
        model.addColumn("NIM");
        model.addColumn("Jurusan");
        
        tableMahasiswa = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableMahasiswa);
        add(scrollPane, BorderLayout.CENTER);
    }
    public String getNama(){
        return txtNama.getText();
    }
    public String getNIM(){
        return txtNIM.getText();
    }
    public String getJurusan(){
        return txtJurusan.getText();
    }
    public String getKeywordCari(){
        return txtCari.getText();
    }

    public void setNama(String nama){
        txtNama.setText(nama);
    }
    public void setNIM(String nim){
        txtNIM.setText(nim);
    }
    public void setJurusan(String jurusan){
        txtJurusan.setText(jurusan);
    }
    
    public DefaultTableModel getModel() {
        return model; 
    }
    public JTable getTable() { 
        return tableMahasiswa; 
    }
    
    public void addSimpanListener(ActionListener listener) { 
        btnSimpan.addActionListener(listener); 
    }
    public void addEditListener(ActionListener listener) {
        btnEdit.addActionListener(listener); 
    }
    public void addHapusListener(ActionListener listener) {
        btnHapus.addActionListener(listener); 
    }
    public void addClearListener(ActionListener listener) { 
        btnClear.addActionListener(listener); 
    }
    public void addCariListener(ActionListener listener) { 
        btnCari.addActionListener(listener); 
    }
    public void addTabelListener(MouseAdapter adapter) { 
        tableMahasiswa.addMouseListener(adapter); 
    }   
    
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    public boolean showConfirm(String msg) {
        int pilihanUser = JOptionPane.showConfirmDialog(this, msg, "Konfirmasi", JOptionPane.YES_NO_OPTION);
        return pilihanUser == JOptionPane.YES_OPTION;
    }
    
    public void clearForm(){
        txtNama.setText("");
        txtNIM.setText("");
        txtJurusan.setText("");
        txtCari.setText("");
    }
    
}
