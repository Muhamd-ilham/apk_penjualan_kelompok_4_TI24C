package view;

import koneksi.KoneksiDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class FormPelanggan extends JFrame {
    private JTextField txtNama, txtAlamat, txtNoTelp;
    private JButton btnSimpan, btnUbah, btnHapus, btnBatal, btnKembali;
    private JTable tabelPelanggan;
    private DefaultTableModel tabmode;
    private String idPelangganPilih = ""; 

    public FormPelanggan() {
        setTitle("Kelola Data Pelanggan");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblNama = new JLabel("Nama Pelanggan:");
        lblNama.setBounds(30, 30, 120, 25);
        add(lblNama);
        txtNama = new JTextField();
        txtNama.setBounds(150, 30, 250, 25);
        add(txtNama);

        JLabel lblAlamat = new JLabel("Alamat:");
        lblAlamat.setBounds(30, 70, 120, 25);
        add(lblAlamat);
        txtAlamat = new JTextField();
        txtAlamat.setBounds(150, 70, 250, 25);
        add(txtAlamat);

        JLabel lblNoTelp = new JLabel("No. Telp:");
        lblNoTelp.setBounds(30, 110, 120, 25);
        add(lblNoTelp);
        txtNoTelp = new JTextField();
        txtNoTelp.setBounds(150, 110, 250, 25);
        add(txtNoTelp);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(30, 160, 80, 30);
        add(btnSimpan);

        btnUbah = new JButton("Ubah");
        btnUbah.setBounds(120, 160, 80, 30);
        add(btnUbah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(210, 160, 80, 30);
        add(btnHapus);

        btnBatal = new JButton("Batal");
        btnBatal.setBounds(300, 160, 80, 30);
        add(btnBatal);

        btnKembali = new JButton("Kembali");
        btnKembali.setBounds(480, 20, 80, 30);
        add(btnKembali);

        Object[] baris = {"ID", "Nama Pelanggan", "Alamat", "No. Telp"};
        tabmode = new DefaultTableModel(null, baris);
        tabelPelanggan = new JTable(tabmode);
        JScrollPane scrollPane = new JScrollPane(tabelPelanggan);
        scrollPane.setBounds(30, 210, 520, 200);
        add(scrollPane);

        tampilData();


        btnSimpan.addActionListener(e -> {
            try {
                Connection conn = KoneksiDB.getKoneksi();
                String sql = "INSERT INTO pelanggan (nama_pelanggan, alamat, no_telp) VALUES (?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtNama.getText());
                pst.setString(2, txtAlamat.getText());
                pst.setString(3, txtNoTelp.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Pelanggan Berhasil Disimpan!");
                kosong();
                tampilData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Gagal Simpan: " + ex.getMessage());
            }
        });

        tabelPelanggan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelPelanggan.getSelectedRow();
                idPelangganPilih = tabmode.getValueAt(baris, 0).toString();
                txtNama.setText(tabmode.getValueAt(baris, 1).toString());
                txtAlamat.setText(tabmode.getValueAt(baris, 2).toString());
                txtNoTelp.setText(tabmode.getValueAt(baris, 3).toString());
            }
        });

        btnUbah.addActionListener(e -> {
            if(idPelangganPilih.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data di tabel dulu!"); return;
            }
            try {
                Connection conn = KoneksiDB.getKoneksi();
                String sql = "UPDATE pelanggan SET nama_pelanggan=?, alamat=?, no_telp=? WHERE id_pelanggan=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtNama.getText());
                pst.setString(2, txtAlamat.getText());
                pst.setString(3, txtNoTelp.getText());
                pst.setString(4, idPelangganPilih);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
                kosong();
                tampilData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Gagal Ubah: " + ex.getMessage());
            }
        });

        btnHapus.addActionListener(e -> {
            if(idPelangganPilih.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data di tabel dulu!"); return;
            }
            int konfirmasi = JOptionPane.showConfirmDialog(null, "Yakin mau hapus pelanggan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection conn = KoneksiDB.getKoneksi();
                    String sql = "DELETE FROM pelanggan WHERE id_pelanggan=?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, idPelangganPilih);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
                    kosong();
                    tampilData();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Gagal Hapus: " + ex.getMessage());
                }
            }
        });

        btnBatal.addActionListener(e -> kosong());
        btnKembali.addActionListener(e -> this.dispose());
    }

    private void tampilData() {
        tabmode.setRowCount(0); 
        try {
            Connection conn = KoneksiDB.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM pelanggan");
            while (rs.next()) {
                tabmode.addRow(new Object[]{
                    rs.getString("id_pelanggan"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("alamat"),
                    rs.getString("no_telp")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Data: " + e.getMessage());
        }
    }

    private void kosong() {
        txtNama.setText("");
        txtAlamat.setText("");
        txtNoTelp.setText("");
        idPelangganPilih = "";
    }
}