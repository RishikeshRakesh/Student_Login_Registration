import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Download")
public class DownloadCertificateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentId = request.getParameter("studentId");
        String type = request.getParameter("type");

        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "rishi");
            String column = type.equals("tenth") ? "tenth_certificate" : "twelfth_certificate";
            String selectStr = "SELECT " + column + " FROM male_students WHERE student_id = ? UNION ALL SELECT " + column + " FROM female_students WHERE student_id = ?";
            PreparedStatement selectStmt = cn.prepareStatement(selectStr);
            selectStmt.setString(1, studentId);
            selectStmt.setString(2, studentId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                byte[] certificateBlob = rs.getBytes(1);
                if (certificateBlob != null) {
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + type + "_certificate.png\"");
                    OutputStream os = response.getOutputStream();
                    os.write(certificateBlob);
                    os.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Certificate not found.");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Certificate not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }
    }
}
