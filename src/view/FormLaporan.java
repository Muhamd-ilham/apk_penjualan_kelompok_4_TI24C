package view;

import koneksi.KoneksiDB;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FormLaporan extends JFrame {
    private JTable tabelLaporan;
    private DefaultTableModel tabmode;
    private JButton btnRefresh, btnKembali;

    public FormLaporan() {
        setTitle("Laporan Penjualan");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblJudul = new JLabel("LAPORAN TRANSAKSI PENJUALAN");
        lblJudul.setFont(new Font("Arial", Font.BOLD, 16));
        lblJudul.setBounds(230, 20, 300, 30);
        add(lblJudul);

        Object[] baris = {"ID Transaksi", "Tanggal", "Nama Pelanggan", "Nama Barang", "Qty", "Subtotal"};
        tabmode = new DefaultTableModel(null, baris);
        tabelLaporan = new JTable(tabmode);
        JScrollPane scrollPane = new JScrollPane(tabelLaporan);
        scrollPane.setBounds(30, 70, 670, 250);
        add(scrollPane);

        btnRefresh = new JButton("Refresh Data");
        btnRefresh.setBounds(30, 340, 120, 30);
        add(btnRefresh);

        btnKembali = new JButton("Kembali");
        btnKembali.setBounds(600, 340, 100, 30);
        add(btnKembali);

        tampilLaporan();

        btnRefresh.addActionListener(e -> tampilLaporan());
        btnKembali.addActionListener(e -> this.dispose());
    }

    private void tampilLaporan() {
        tabmode.setRowCount(0);
        try {
            Connection conn = KoneksiDB.getKoneksi();
            Statement st = conn.createStatement();
            
            String sql = "SELECT * FROM view_laporan_penjualan ORDER BY tanggal DESC, id_transaksi DESC";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                tabmode.addRow(new Object[]{
                    rs.getString("id_transaksi"),
                    rs.getString("tanggal"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("nama_barang"),
                    rs.getString("jumlah"),
                    rs.getString("subtotal")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Menampilkan Laporan: " + e.getMessage());
        }
    }
}