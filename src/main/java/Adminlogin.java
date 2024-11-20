import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



@WebServlet("/Adminlogin")
@MultipartConfig
public class Adminlogin extends HttpServlet {

    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String val = req.getParameter("Purpose");

        if ("V".equals(val)) {
            String id = req.getParameter("id");
            String pwd = req.getParameter("pwd");

            if ("VSBEC".equals(id) && "vsb@123".equals(pwd)) {
                RequestDispatcher rs = req.getRequestDispatcher("admin.html");
                rs.forward(req, res);
            }
        } else if ("D".equals(val)) {
            try {
                String stuId = req.getParameter("id");
                String firstName = req.getParameter("first_name");
                String lastName = req.getParameter("last_name");
                String dateOfBirth = req.getParameter("date_of_birth");
                String gender = req.getParameter("gender");
                String contactNumber = req.getParameter("contact_number");
                String email = req.getParameter("email");
                String address = req.getParameter("address");
                String enrollmentDate = req.getParameter("enrollment_date");
                String hostel = req.getParameter("hostel");
                String roomNumber = req.getParameter("room_number");
                String floor = req.getParameter("floor");
                String amountPaid = req.getParameter("amount_paid");

                // Retrieve file parts
                Part tenthCertPart = req.getPart("tenth_certificate");
                Part twelfthCertPart = req.getPart("twelfth_certificate");

                // Convert file parts to InputStream
                InputStream tenthCertStream = (tenthCertPart != null) ? tenthCertPart.getInputStream() : null;
                InputStream twelfthCertStream = (twelfthCertPart != null) ? twelfthCertPart.getInputStream() : null;

                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "rishi");

                String str;
                if ("male".equalsIgnoreCase(gender)) {
                    str = "INSERT INTO male_students (student_id, first_name, last_name, date_of_birth, contact_number, email, address, enrollment_date, hostel, room_number, floor, amount_paid, tenth_certificate, twelfth_certificate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                } else if ("female".equalsIgnoreCase(gender)) {
                    str = "INSERT INTO female_students (student_id, first_name, last_name, date_of_birth, contact_number, email, address, enrollment_date, hostel, room_number, floor, amount_paid, tenth_certificate, twelfth_certificate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                } else {
                    throw new ServletException("Invalid gender specified");
                }

                PreparedStatement pw = cn.prepareStatement(str);
                pw.setString(1, stuId);
                pw.setString(2, firstName);
                pw.setString(3, lastName);
                pw.setString(4, dateOfBirth);
                pw.setString(5, contactNumber);
                pw.setString(6, email);
                pw.setString(7, address);
                pw.setString(8, enrollmentDate);
                pw.setString(9, hostel);

                if (roomNumber == null || roomNumber.trim().isEmpty()) {
                    pw.setNull(10, java.sql.Types.VARCHAR);
                } else {
                    pw.setString(10, roomNumber);
                }

                if (floor == null || floor.trim().isEmpty()) {
                    pw.setNull(11, java.sql.Types.VARCHAR);
                } else {
                    pw.setString(11, floor);
                }

                if (amountPaid == null || amountPaid.trim().isEmpty()) {
                    pw.setNull(12, java.sql.Types.DECIMAL);
                } else {
                    pw.setBigDecimal(12, new java.math.BigDecimal(amountPaid));
                }
                if (tenthCertStream != null) {
                    pw.setBlob(13, tenthCertStream);
                } else {
                    pw.setNull(13, java.sql.Types.BLOB);
                }

                if (twelfthCertStream != null) {
                    pw.setBlob(14, twelfthCertStream);
                } else {
                    pw.setNull(14, java.sql.Types.BLOB);
                }

               
                pw.execute();
                pw.close();
                cn.close();

                RequestDispatcher rs = req.getRequestDispatcher("record.html");
                rs.forward(req, res);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new ServletException("Database error", e);
            }
        }
    }
}
