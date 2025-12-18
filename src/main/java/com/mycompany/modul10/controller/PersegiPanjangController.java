/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul8.controller;

import modul8.view.PersegiPanjangView;
import modul8.model.PersegiPanjangModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author hary
 */
public class PersegiPanjangController {
    private PersegiPanjangModel model;
    private PersegiPanjangView view;

    public PersegiPanjangController(PersegiPanjangModel model, PersegiPanjangView view) {
        this.model = model;
        this.view = view;

        this.view.addHitungListener(new HitungListener());
        this.view.addResetListener(new ResetListener());
    }

    class HitungListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double p = view.getPanjang();
                double l = view.getLebar();

                model.setPanjang(p);
                model.setLebar(l);

                model.hitungLuas();
                model.hitungKeliling();

                double hasilLuas = model.getLuas();
                view.setHasilLuas(hasilLuas);
                
                double hasilKeliling = model.getKeliling();
                view.setHasilKeliling(hasilKeliling);

            } catch (NumberFormatException ex) {
                view.tampilkanPesanError("Masukkan angka yang valid!");
            }
        }
    }
    
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            
                view.resetTampilan();
            
        }
    }
}
