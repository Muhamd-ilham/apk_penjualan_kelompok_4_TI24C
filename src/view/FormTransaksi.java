package view;

import koneksi.KoneksiDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class FormTransaksi extends JFrame {
    private JComboBox<String> cmbPelanggan, cmbBarang;
    private JTextField txtHarga, txtQty, txtTotal;
    private JButton btnTambah, btnSimpan, btnKembali;
    private JTable tabelKeranjang;
    private DefaultTableModel tabmode;
    private double totalBelanja = 0;

    public FormTransaksi() {
        setTitle("Transaksi Penjualan");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblPelanggan = new JLabel("Pilih Pelanggan:");
        lblPelanggan.setBounds(30, 30, 120, 25);
        add(lblPelanggan);
        cmbPelanggan = new JComboBox<>();
        cmbPelanggan.setBounds(150, 30, 250, 25);
        add(cmbPelanggan);

        JLabel lblBarang = new JLabel("Pilih Barang:");
        lblBarang.setBounds(30, 70, 120, 25);
        add(lblBarang);
        cmbBarang = new JComboBox<>();
        cmbBarang.setBounds(150, 70, 250, 25);
        add(cmbBarang);

        JLabel lblHarga = new JLabel("Harga:");
        lblHarga.setBounds(30, 110, 120, 25);
        add(lblHarga);
        txtHarga = new JTextField();
        txtHarga.setBounds(150, 110, 150, 25);
        txtHarga.setEditable(false); // Harga otomatis, nggak bisa diedit
        add(txtHarga);

        JLabel lblQty = new JLabel("Qty:");
        lblQty.setBounds(320, 110, 30, 25);
        add(lblQty);
        txtQty = new JTextField();
        txtQty.setBounds(360, 110, 40, 25);
        add(txtQty);

        btnTambah = new JButton("Tambah ke Keranjang");
        btnTambah.setBounds(420, 110, 180, 25);
        add(btnTambah);

        Object[] baris = {"ID Barang", "Nama Barang", "Harga", "Qty", "Subtotal"};
        tabmode = new DefaultTableModel(null, baris);
        tabelKeranjang = new JTable(tabmode);
        JScrollPane scrollPane = new JScrollPane(tabelKeranjang);
        scrollPane.setBounds(30, 160, 620, 200);
        add(scrollPane);

        JLabel lblTotal = new JLabel("Total Belanja: Rp");
        lblTotal.setBounds(380, 380, 120, 25);
        add(lblTotal);
        txtTotal = new JTextField("0");
        txtTotal.setBounds(500, 380, 150, 25);
        txtTotal.setEditable(false);
        add(txtTotal);

        btnSimpan = new JButton("Simpan Transaksi");
        btnSimpan.setBounds(500, 420, 150, 40);
        add(btnSimpan);

        btnKembali = new JButton("Kembali");
        btnKembali.setBounds(30, 420, 100, 40);
        add(btnKembali);

        loadDataPelanggan();
        loadDataBarang();


        cmbBarang.addActionListener(e -> {
            String item = (String) cmbBarang.getSelectedItem();
            if (item != null && !item.equals("-- Pilih Barang --")) {
                try {
                    String idBarang = item.split(" - ")[0]; 
                    Connection conn = KoneksiDB.getKoneksi();
                    PreparedStatement pst = conn.prepareStatement("SELECT harga FROM barang WHERE id_barang=?");
                    pst.setString(1, idBarang);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        txtHarga.setText(rs.getString("harga"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                txtHarga.setText("");
            }
        });

        btnTambah.addActionListener(e -> {
            if (cmbBarang.getSelectedIndex() == 0 || txtQty.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih barang dan isi Qty!"); return;
            }
            try {
                int qty = Integer.parseInt(txtQty.getText());
                double harga = Double.parseDouble(txtHarga.getText());
                
                Connection conn = KoneksiDB.getKoneksi();
                PreparedStatement pst = conn.prepareStatement("SELECT fn_hitung_subtotal(?, ?) AS subtotal_func");
                pst.setInt(1, qty);
                pst.setDouble(2, harga);
                ResultSet rs = pst.executeQuery();
                
                double subtotal = 0;
                if(rs.next()){
                    subtotal = rs.getDouble("subtotal_func"); 
                }

                String idBarang = cmbBarang.getSelectedItem().toString().split(" - ")[0];
                String namaBarang = cmbBarang.getSelectedItem().toString().split(" - ")[1];

                tabmode.addRow(new Object[]{idBarang, namaBarang, harga, qty, subtotal});

                totalBelanja += subtotal;
                txtTotal.setText(String.valueOf(totalBelanja));

                cmbBarang.setSelectedIndex(0);
                txtQty.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        btnSimpan.addActionListener(e -> {
            if (tabmode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Keranjang masih kosong!"); return;
            }
            try {
                Connection conn = KoneksiDB.getKoneksi();
                conn.setAutoCommit(false); 

                String idPelanggan = cmbPelanggan.getSelectedItem().toString().split(" - ")[0];
                int idUserAdmin = 1; 
                java.sql.Date tglSekarang = new java.sql.Date(System.currentTimeMillis());

                CallableStatement cs = conn.prepareCall("{CALL sp_tambah_transaksi(?, ?, ?, ?)}");
                cs.setInt(1, idUserAdmin);
                cs.setString(2, idPelanggan);
                cs.setDate(3, tglSekarang);
                cs.setDouble(4, totalBelanja);
                cs.executeUpdate();

                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()");
                int idTransaksi = 0;
                if (rs.next()) {
                    idTransaksi = rs.getInt(1);
                }

                String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_barang, jumlah, subtotal) VALUES (?, ?, ?, ?)";
                PreparedStatement pstDetail = conn.prepareStatement(sqlDetail);
                
                for (int i = 0; i < tabelKeranjang.getRowCount(); i++) {
                    pstDetail.setInt(1, idTransaksi);
                    pstDetail.setString(2, tabelKeranjang.getValueAt(i, 0).toString());
                    pstDetail.setString(3, tabelKeranjang.getValueAt(i, 3).toString());
                    pstDetail.setString(4, tabelKeranjang.getValueAt(i, 4).toString());
                    pstDetail.executeUpdate(); 
                }

                conn.commit();
                JOptionPane.showMessageDialog(null, "Transaksi Berhasil Disimpan! Stok barang otomatis berkurang.");
                
                tabmode.setRowCount(0);
                totalBelanja = 0;
                txtTotal.setText("0");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Gagal Transaksi: " + ex.getMessage());
            }
        });

        btnKembali.addActionListener(e -> this.dispose());
    }

    private void loadDataPelanggan() {
        try {
            Connection conn = KoneksiDB.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_pelanggan, nama_pelanggan FROM pelanggan");
            while (rs.next()) {
                cmbPelanggan.addItem(rs.getString("id_pelanggan") + " - " + rs.getString("nama_pelanggan"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void loadDataBarang() {
        cmbBarang.addItem("-- Pilih Barang --");
        try {
            Connection conn = KoneksiDB.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id_barang, nama_barang FROM barang");
            while (rs.next()) {
                cmbBarang.addItem(rs.getString("id_barang") + " - " + rs.getString("nama_barang"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}