package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    private static final String URL = "jdbc:mysql://localhost:3306/db_penjualan";
    private static final String USER = "root"; 
    private static final String PASS = ""; 

    public static Connection getKoneksi() {
        Connection conn = null;
        try {
         
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Koneksi ke Database MySQL Berhasil!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Gagal Koneksi: " + e.getMessage());
        }
        return conn;
    }
}