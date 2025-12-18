/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul8.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author hary
 */
public class PersegiPanjangView extends JFrame{
    // Komponen UI sebagai atribut
    private JTextField textPanjang = new JTextField(10);
    private JTextField textLebar = new JTextField(10);
    private JLabel labelHasilLuas = new JLabel("-");
    private JLabel labelHasilKeliling = new JLabel("-");
    private JButton btnReset = new JButton("Reset");
    private JButton btnHitung = new JButton("Hitung");
    
    public PersegiPanjangView() {
       // Inisialisasi UI
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setSize(400,300);
       this.setLayout(new GridLayout(5, 2, 10, 10)); // Grid 4 Baris
       this.setTitle("MVC Controller");
       
       this.add(new JLabel("Panjang : "));
       this.add(textPanjang);
       
       this.add(new JLabel("Lebar : "));
       this.add(textLebar);
       
       this.add(new JLabel("Hasil Luas : "));
       this.add(labelHasilLuas);
       
       this.add(new JLabel("Hasil Keliling : "));
       this.add(labelHasilKeliling);
       
       this.add(btnReset); 
       this.add(btnHitung);
    }
    // Mengambil nilai panjang dari text field
    public double getPanjang(){
        return Double.parseDouble(textPanjang.getText());
    }
    
    public double getLebar(){
        return Double.parseDouble(textLebar.getText());
    }
    
    public void setHasilLuas(double hasil){
        labelHasilLuas.setText(String.valueOf(hasil));
    }
    
    public void setHasilKeliling(double hasil){
        labelHasilKeliling.setText(String.valueOf(hasil));
    }
    
    public void tampilkanPesanError(String pesan){
        JOptionPane.showMessageDialog(this, pesan);
    }
    
    public void addHitungListener(ActionListener listener){
        btnHitung.addActionListener(listener);
    }
    
    public void addResetListener(ActionListener listener){
        btnReset.addActionListener(listener);
    }
    
    public void resetTampilan(){
        textPanjang.setText("");
        textLebar.setText("");
        labelHasilLuas.setText("-");
        labelHasilKeliling.setText("-");
        textPanjang.requestFocus();
    }
}
