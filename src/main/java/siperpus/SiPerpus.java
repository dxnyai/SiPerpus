/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package siperpus;

/**
 *
 * @author Dany
 */
import siperpus.barcode.PembacaBarcode;
import siperpus.barcode.ScannerFisikBarcode;
import siperpus.model.Anggota;
import siperpus.model.Buku;
import siperpus.model.Pengguna;
import siperpus.model.Petugas;
import siperpus.model.RakBuku;
import siperpus.repository.MemoryTransaksiRepository;
import siperpus.repository.TransaksiRepository;
import siperpus.service.SirkulasiService;
import siperpus.ui.PerpustakaanView;
import siperpus.ui.SirkulasiController;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SiPerpus {
    public static void main(String[] args) {
        Scanner consoleScanner = new Scanner(System.in);

        // 1. Inisialisasi Data Master
        Petugas petugas1 = new Petugas("PTG-01", "Dany Akhdan Imaduddin", "dany_admin", "untidar2026");
        Anggota anggota1 = new Anggota("AGT-01", "Muhammad Hanif Raharjo", "2505060074", "KTM-0074", true);
        Anggota anggota2 = new Anggota("AGT-02", "Nayla Rahmadani", "2505060075", "KTM-0075", true);
        Anggota anggotaNonAktif = new Anggota("AGT-03", "Budi Nonaktif", "2505060099", "KTM-0099", false);

        List<Pengguna> daftarPengguna = List.of(petugas1, anggota1, anggota2);
        System.out.println("=== VERIFIKASI ENTITAS PENGGUNA (POLIMORFISME) ===");
        for (Pengguna p : daftarPengguna) {
            System.out.println("- " + p.getNama() + " -> Peran: " + p.getPeran());
        }

        Buku b1 = new Buku("978-602-01", "BK-101", "Pemrograman Berorientasi Objek Java", 3);
        Buku b2 = new Buku("978-602-02", "BK-102", "Clean Architecture & Prinsip SOLID", 2);
        Buku b3 = new Buku("978-602-03", "BK-103", "Basis Data & Big Data Spark", 1);
        Buku b4 = new Buku("978=602-04", "BK-104", "Matematika Discrete", 3);

        RakBuku rakTI = new RakBuku("RAK-A1", "Lantai 2 - Zona Teknologi Informasi");
        rakTI.tambahBuku(b1);
        rakTI.tambahBuku(b2);
        rakTI.tambahBuku(b3);
        rakTI.tambahBuku(b4);

        List<RakBuku> daftarRak = new ArrayList<>();
        daftarRak.add(rakTI);

        List<Anggota> daftarAnggota = List.of(anggota1, anggota2, anggotaNonAktif);

        // 2. Merakit Dependensi (Dependency Injection)
        TransaksiRepository transaksiRepo = new MemoryTransaksiRepository();
        PembacaBarcode barcodeScanner = new ScannerFisikBarcode("USB-COM3", consoleScanner);
        SirkulasiService sirkulasiService = new SirkulasiService(transaksiRepo, barcodeScanner);

        SirkulasiController controller = new SirkulasiController(
                sirkulasiService, daftarRak, daftarAnggota, petugas1
        );
        PerpustakaanView view = new PerpustakaanView(controller, consoleScanner);

        // 3. Jalankan Aplikasi
        view.tampilkanMenuUtama();
    }
}
