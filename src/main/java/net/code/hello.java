package net.code;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;

@WebService
public class hello {
    
    @WebMethod
    public String getLaptopInfo(@WebParam(name = "id") int id) {
        // Data laptop statis (tanpa menggunakan database)
        if (id == 1) {
            return "Merek: MacBook, Harga: 15.000.000, Deskripsi: Laptop ringan dan stylish dengan macOS., Gambar: http://localhost:8080/images/macbook.jpeg";
        } else if (id == 2) {
            return "Merek: Asus, Harga: 12.000.000, Deskripsi: Laptop ultrabook ringan, Gambar: http://localhost:8080/images/asu.jpg";
        } else if (id == 3) {
            return "Merek: HP, Harga: 13.000.000, Deskripsi: Laptop bisnis dengan daya tahan baterai lama, Gambar: http://localhost:8080/images/hp.jpg";
        } else {
            return "Data laptop tidak ditemukan.";
        }
    }
}
