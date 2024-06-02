import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabelDataKaryawan extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtNIK, txtNama, txtJabatan, txtAlamat, txtEmail, txtTelepon;

    public TabelDataKaryawan() {
        setTitle("TABEL DATA KARYAWAN");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Membuat panel utama
        JPanel panel = new JPanel(new BorderLayout());

        // Membuat panel untuk input data karyawan
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        // Membuat label dan field untuk input data
        inputPanel.add(new JLabel("NIK:"));
        txtNIK = new JTextField();
        inputPanel.add(txtNIK);

        inputPanel.add(new JLabel("Nama:"));
        txtNama = new JTextField();
        inputPanel.add(txtNama);

        inputPanel.add(new JLabel("Jabatan:"));
        txtJabatan = new JTextField();
        inputPanel.add(txtJabatan);

        inputPanel.add(new JLabel("Alamat:"));
        txtAlamat = new JTextField();
        inputPanel.add(txtAlamat);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Telepon:"));
        txtTelepon = new JTextField();
        inputPanel.add(txtTelepon);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Membuat tabel
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Membuat tombol
        JButton buttonLoad = new JButton("TAMPILKAN TABEL");
        JButton buttonAdd = new JButton("TAMBAHKAN KARYAWAN");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttonLoad);
        buttonPanel.add(buttonAdd);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Menambahkan action listener untuk tombol "TAMPILKAN TABEL"
        buttonLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableData();
            }
        });

        // Menambahkan action listener untuk tombol "TAMBAHKAN KARYAWAN"
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        add(panel);
    }

    private void loadTableData() {
        String filePath = "F:\\Pem-Lan\\Karyawan.txt"; // Ganti dengan path file teks Anda
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            tableModel.setRowCount(0); // Mengosongkan data yang ada
            tableModel.setColumnCount(0); // Mengosongkan kolom yang ada
            boolean isFirstRow = true;

            while ((line = br.readLine()) != null) {
                if (line.contains("|")) {
                    String[] data = line.split("\\|");
                    for (int i = 0; i < data.length; i++) {
                        data[i] = data[i].trim();
                    }
                    if (isFirstRow) {
                        // Menambahkan kolom ke tabel
                        for (String columnName : data) {
                            tableModel.addColumn(columnName);
                        }
                        isFirstRow = false;
                    } else {
                        // Menambahkan baris ke tabel
                        tableModel.addRow(data);
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() {
        String nik = txtNIK.getText();
        String nama = txtNama.getText();
        String jabatan = txtJabatan.getText();
        String alamat = txtAlamat.getText();
        String email = txtEmail.getText();
        String telepon = txtTelepon.getText();
    
        // Mengecek apakah ada field yang kosong
        if (nik.isEmpty() || nama.isEmpty() || jabatan.isEmpty() || alamat.isEmpty() || email.isEmpty() || telepon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String filePath = "F:\\Pem-Lan\\Karyawan.txt"; // Ganti dengan path file teks Anda
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            // Menambahkan data karyawan ke file teks
            bw.write(nik + "|" + nama + "|" + jabatan + "|" + alamat + "|" + email + "|" + telepon);
            bw.newLine();
            bw.flush();
            JOptionPane.showMessageDialog(this, "Data karyawan berhasil ditambahkan!");
            
            // Memuat ulang data tabel setelah penambahan
            loadTableData();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TabelDataKaryawan().setVisible(true);
            }
        });
    }
}
