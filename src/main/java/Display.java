import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Display")
public class Display extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String studentId = req.getParameter("f1");
        String email = req.getParameter("f2");

        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "rishi");
            PreparedStatement selectStmt;
            ResultSet rs;
            boolean recordFound = false;

            String selectStr = "SELECT * FROM male_students WHERE student_id = ? AND email = ?";
            selectStmt = cn.prepareStatement(selectStr);
            selectStmt.setString(1, studentId);
            selectStmt.setString(2, email);
            rs = selectStmt.executeQuery();

            if (rs.next()) {
                recordFound = true;
            } else {
                selectStr = "SELECT * FROM female_students WHERE student_id = ? AND email = ?";
                selectStmt = cn.prepareStatement(selectStr);
                selectStmt.setString(1, studentId);
                selectStmt.setString(2, email);
                rs = selectStmt.executeQuery();

                if (rs.next()) {
                    recordFound = true;
                }
            }

            if (recordFound) {
                HttpSession session = req.getSession();
                session.setAttribute("student_id", rs.getString("student_id"));
                session.setAttribute("firstName", rs.getString("first_name"));
                session.setAttribute("lastName", rs.getString("last_name"));
                session.setAttribute("dateOfBirth", rs.getDate("date_of_birth"));
                session.setAttribute("contactNumber", rs.getString("contact_number"));
                session.setAttribute("email", rs.getString("email"));
                session.setAttribute("address", rs.getString("address"));
                session.setAttribute("enrollmentDate", rs.getDate("enrollment_date"));
                session.setAttribute("hostel", rs.getString("hostel"));
                session.setAttribute("roomNumber", rs.getString("room_number"));
                session.setAttribute("floor", rs.getString("floor"));
                session.setAttribute("amountPaid", rs.getDouble("amount_paid"));

                // Retrieve 10th and 12th certificate files
                byte[] tenthCertBlob = rs.getBytes("tenth_certificate");
                byte[] twelfthCertBlob = rs.getBytes("twelfth_certificate");

                // Convert blobs to Base64 strings
                String tenthCertBase64 = (tenthCertBlob != null) ? Base64.getEncoder().encodeToString(tenthCertBlob) : "";
                String twelfthCertBase64 = (twelfthCertBlob != null) ? Base64.getEncoder().encodeToString(twelfthCertBlob) : "";

                session.setAttribute("tenthCertificate", tenthCertBase64);
                session.setAttribute("twelfthCertificate", twelfthCertBase64);

                // Forward to Student.jsp
                RequestDispatcher rd = req.getRequestDispatcher("Student.jsp");
                rd.forward(req, res);
            } else {
                // If no record is found, forward to an error page or back to the index
                RequestDispatcher rd = req.getRequestDispatcher("index.html");
                rd.forward(req, res);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new ServletException("Invalid number format", e);
        }
    }
}
