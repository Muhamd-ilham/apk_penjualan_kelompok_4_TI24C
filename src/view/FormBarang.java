package view;

import koneksi.KoneksiDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class FormBarang extends JFrame {
    private JTextField txtNama, txtHarga, txtStok;
    private JButton btnSimpan, btnUbah, btnHapus, btnBatal, btnKembali;
    private JTable tabelBarang;
    private DefaultTableModel tabmode;
    private String idBarangPilih = ""; 

    public FormBarang() {
        setTitle("Kelola Data Barang");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblNama = new JLabel("Nama Barang:");
        lblNama.setBounds(30, 30, 100, 25);
        add(lblNama);
        txtNama = new JTextField();
        txtNama.setBounds(130, 30, 200, 25);
        add(txtNama);

        JLabel lblHarga = new JLabel("Harga:");
        lblHarga.setBounds(30, 70, 100, 25);
        add(lblHarga);
        txtHarga = new JTextField();
        txtHarga.setBounds(130, 70, 200, 25);
        add(txtHarga);

        JLabel lblStok = new JLabel("Stok:");
        lblStok.setBounds(30, 110, 100, 25);
        add(lblStok);
        txtStok = new JTextField();
        txtStok.setBounds(130, 110, 200, 25);
        add(txtStok);

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

        Object[] baris = {"ID", "Nama Barang", "Harga", "Stok"};
        tabmode = new DefaultTableModel(null, baris);
        tabelBarang = new JTable(tabmode);
        JScrollPane scrollPane = new JScrollPane(tabelBarang);
        scrollPane.setBounds(30, 210, 520, 200);
        add(scrollPane);

        tampilData();

        
        btnSimpan.addActionListener(e -> {
            try {
                Connection conn = KoneksiDB.getKoneksi();
                String sql = "INSERT INTO barang (nama_barang, harga, stok) VALUES (?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtNama.getText());
                pst.setString(2, txtHarga.getText());
                pst.setString(3, txtStok.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
                kosong();
                tampilData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Gagal Simpan: " + ex.getMessage());
            }
        });

        tabelBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabelBarang.getSelectedRow();
                idBarangPilih = tabmode.getValueAt(baris, 0).toString();
                txtNama.setText(tabmode.getValueAt(baris, 1).toString());
                txtHarga.setText(tabmode.getValueAt(baris, 2).toString());
                txtStok.setText(tabmode.getValueAt(baris, 3).toString());
            }
        });

        btnUbah.addActionListener(e -> {
            if(idBarangPilih.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data di tabel dulu bang!"); return;
            }
            try {
                Connection conn = KoneksiDB.getKoneksi();
                String sql = "UPDATE barang SET nama_barang=?, harga=?, stok=? WHERE id_barang=?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, txtNama.getText());
                pst.setString(2, txtHarga.getText());
                pst.setString(3, txtStok.getText());
                pst.setString(4, idBarangPilih);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
                kosong();
                tampilData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Gagal Ubah: " + ex.getMessage());
            }
        });

        btnHapus.addActionListener(e -> {
            if(idBarangPilih.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data di tabel dulu bang!"); return;
            }
            int konfirmasi = JOptionPane.showConfirmDialog(null, "Yakin mau hapus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try {
                    Connection conn = KoneksiDB.getKoneksi();
                    String sql = "DELETE FROM barang WHERE id_barang=?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, idBarangPilih);
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
        tabmode.setRowCount(0); // Bersihkan tabel sebelum diisi ulang
        try {
            Connection conn = KoneksiDB.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM barang");
            while (rs.next()) {
                tabmode.addRow(new Object[]{
                    rs.getString("id_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("harga"),
                    rs.getString("stok")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Data: " + e.getMessage());
        }
    }
  
    private void kosong() {
        txtNama.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        idBarangPilih = "";
    }
}