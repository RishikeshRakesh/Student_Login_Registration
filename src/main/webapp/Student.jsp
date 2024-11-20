<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Details</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap');

        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            animation: fadeIn 2s ease;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }

        h2 {
            text-align: center;
            color: #fff;
            font-size: 2em;
            margin-bottom: 20px;
        }

        table {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
            box-shadow: 0 0 30px rgba(0, 0, 0, 0.2);
            background-color: #fff;
            border-radius: 10px;
            overflow: hidden;
            animation: scaleUp 0.5s ease;
        }

        @keyframes scaleUp {
            from {
                transform: scale(0.9);
                opacity: 0;
            }
            to {
                transform: scale(1);
                opacity: 1;
            }
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #0072ff;
            color: white;
            font-weight: 500;
        }

        tr:hover {
            background-color: #f5f5f5;
            transition: background-color 0.3s ease;
        }

        td {
            font-size: 16px;
            color: #333;
        }

        tr:last-child td {
            border-bottom: none;
        }

        .container {
            width: 90%;
            max-width: 800px;
            background: #fff;
            padding: 20px;
            box-shadow: 0 0 30px rgba(0, 0, 0, 0.2);
            border-radius: 10px;
            overflow: hidden;
        }

        .container::before {
            content: "";
            position: absolute;
            top: -20%;
            left: -20%;
            width: 140%;
            height: 140%;
            background: radial-gradient(circle, #0072ff 30%, transparent 70%);
            z-index: -1;
            animation: rotateBg 20s linear infinite;
        }

        @keyframes rotateBg {
            from {
                transform: rotate(0deg);
            }
            to {
                transform: rotate(360deg);
            }
        }

    </style>
</head>
<body>
    <div class="container">
        <h2>Student Details</h2>
        <table>
            <tr>
                <th>First Name</th>
                <td>${sessionScope.firstName}</td>
            </tr>
            <tr>
                <th>Last Name</th>
                <td>${sessionScope.lastName}</td>
            </tr>
            <tr>
                <th>Date of Birth</th>
                <td>${sessionScope.dateOfBirth}</td>
            </tr>
            <tr>
                <th>Contact Number</th>
                <td>${sessionScope.contactNumber}</td>
            </tr>
            <tr>
                <th>Email</th>
                <td>${sessionScope.email}</td>
            </tr>
            <tr>
                <th>Address</th>
                <td>${sessionScope.address}</td>
            </tr>
            <tr>
                <th>Enrollment Date</th>
                <td>${sessionScope.enrollmentDate}</td>
            </tr>
            <tr>
                <th>Hostel</th>
                <td>${sessionScope.hostel}</td>
            </tr>
            <tr>
                <th>Room Number</th>
                <td>${sessionScope.roomNumber}</td>
            </tr>
            <tr>
                <th>Floor</th>
                <td>${sessionScope.floor}</td>
            </tr>
            <tr>
                <th>Amount Paid</th>
                <td>${sessionScope.amountPaid}</td>
            </tr>
            <tr>
                <th>10th Certificate</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty sessionScope.tenthCertificate}">
                            <a href="Download?studentId=${sessionScope.student_id}&type=tenth">Download 10th Certificate</a>
                        </c:when>
                        
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>12th Certificate</th>
                <td>
                    <c:choose>
                        <c:when test="${not empty sessionScope.twelfthCertificate}">
                            <a href="Download?studentId=${sessionScope.student_id}&type=twelfth">Download 12th Certificate</a>
                        </c:when>
                        
                    </c:choose>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
