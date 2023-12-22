
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
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentId());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select studentid, firstname, lastname from app.student order by studentid");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                String entryStudentID = resultSet.getString("studentid");
                String entryFirstName = resultSet.getString("firstname");
                String entryLastName = resultSet.getString("lastname");
                
                StudentEntry studentEntry = new StudentEntry(entryStudentID, entryFirstName, entryLastName);

                student.add(studentEntry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
        
    }
    
    
    public static StudentEntry getStudent(String studentID) {
        StudentEntry student = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement getStudentStatement = connection.prepareStatement("SELECT * FROM app.student WHERE studentID = ?");
            getStudentStatement.setString(1, studentID);
            
            ResultSet resultSet = getStudentStatement.executeQuery();
            
            if (resultSet.next()) {
                String id = resultSet.getString("studentID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
               
                
               
                student = new StudentEntry(id, firstName, lastName);
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
            return student;
    }
    
    public static void dropStudent(String studentID) {
        try {
            Connection connection = DBConnection.getConnection();
               
            
            dropStudent = connection.prepareStatement("DELETE from app.student where studentID = ?");        
            dropStudent.setString(1,studentID);
            dropStudent.execute();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        } 
    
        public static String getStudentIdByNames(String firstName, String lastName) {
        String studentID = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT studentID FROM app.student WHERE firstName = ? AND lastName = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                studentID = resultSet.getString("studentID");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return studentID;
        }
    
        
        
        public static String getIDFromName(String firstName, String lastName) {
        String studentID = null;
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT studentID FROM app.student WHERE firstName = ? AND lastName = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                studentID = resultSet.getString("studentID");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return studentID;
}
        
        
        public static StudentEntry getStudentByNames(String firstName, String lastName) {
            StudentEntry student = null;
            try {
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM app.student WHERE firstName = ? AND lastName = ?");
                statement.setString(1, firstName);
                statement.setString(2, lastName);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String id = resultSet.getString("studentID");
                    student = new StudentEntry(id, firstName, lastName);
                }
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
            return student;
        }
}
