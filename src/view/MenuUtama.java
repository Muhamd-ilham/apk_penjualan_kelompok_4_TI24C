package view;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuUtama extends JFrame {

    public MenuUtama() {
        setTitle("Dashboard - Aplikasi Penjualan");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblJudul = new JLabel("MAIN MENU APLIKASI PENJUALAN");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        lblJudul.setBounds(140, 20, 350, 30);
        add(lblJudul);

        JButton btnBarang = new JButton("Data Barang");
        btnBarang.setBounds(50, 80, 150, 40);
        add(btnBarang);

        btnBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormBarang().setVisible(true);
            }
        });

        JButton btnPelanggan = new JButton("Data Pelanggan");
        btnPelanggan.setBounds(220, 80, 150, 40);
        add(btnPelanggan);

        btnPelanggan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormPelanggan().setVisible(true);
            }
        });

        JButton btnTransaksi = new JButton("Transaksi Penjualan");
        btnTransaksi.setBounds(390, 80, 150, 40);
        add(btnTransaksi);

        JButton btnLaporan = new JButton("Laporan Penjualan");
        btnLaporan.setBounds(220, 140, 150, 40); 
        add(btnLaporan);

        btnLaporan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormLaporan().setVisible(true);
            }
        });

        btnTransaksi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormTransaksi().setVisible(true);
            }
        });

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(240, 250, 100, 30);
        add(btnLogout);

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tutup menu utama
                new FormLogin().setVisible(true); 
            }
        });
    }
}