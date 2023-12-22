
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carson
 */
public class MultiTableQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement getAllClassDescriptions;
    private static ResultSet resultSet;


    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> description = new ArrayList<ClassDescription>();
        try
        {
            getAllClassDescriptions = connection.prepareStatement("select coursecode,description from app.course order by coursecode");
            resultSet = getAllClassDescriptions.executeQuery();
            
            ArrayList<String> semesterClasses=ClassQueries.getAllCourseCodes(semester);
            while(resultSet.next())
            {
                if(semesterClasses.contains(resultSet.getString(1))){
                    description.add(new ClassDescription(resultSet.getString(1),resultSet.getString(2), ClassQueries.getClassSeats(semester,resultSet.getString(1))));
                }

            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return description;
    }
    
    
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode) {
        ArrayList<StudentEntry> scheduledStudents = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT s.studentID, s.firstName, s.lastName FROM app.student AS s " +
                                "JOIN app.schedule AS sc ON s.studentID = sc.studentID " +
                                "WHERE sc.semester = ? AND sc.courseCode = ? AND sc.status = 'S'");
                statement.setString(1, semester);
                statement.setString(2, courseCode);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String studentID = resultSet.getString("studentID");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    StudentEntry student = new StudentEntry(studentID, firstName, lastName);
                    scheduledStudents.add(student);
                }


            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }

        return scheduledStudents;
    }

    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode) {
        ArrayList<StudentEntry> waitlistedStudents = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT s.studentID, s.firstName, s.lastName FROM app.student AS s " +
                                "JOIN app.schedule AS sc ON s.studentID = sc.studentID " +
                                "WHERE sc.semester = ? AND sc.courseCode = ? AND sc.status = 'W'");
                statement.setString(1, semester);
                statement.setString(2, courseCode);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String studentID = resultSet.getString("studentID");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");

                    StudentEntry student = new StudentEntry(studentID, firstName, lastName);
                    waitlistedStudents.add(student);
                }

              
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }

        return waitlistedStudents;
    }
}
