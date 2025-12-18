/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modul10;

import com.mycompany.modul10.view.MahasiswaView;
import com.mycompany.modul10.controller.MahasiswaController;
import javax.swing.SwingUtilities;

/**
 *
 * @author hary
 */
import com.mycompany.modul10.view.MahasiswaView;
import com.mycompany.modul10.controller.MahasiswaController;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
            // 1. Siapkan View (Tampilan)
            MahasiswaView view = new MahasiswaView();
            
            // 2. Siapkan Controller (Hubungkan View dengan Logika)
            new MahasiswaController(view);
            
            // 3. Tampilkan Aplikasi
            view.setVisible(true);
        });
    }
}
