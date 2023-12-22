
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
public class ClassQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addClass;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getClassSeats;
    private static PreparedStatement dropClass;
    private static ResultSet resultSet;
    
    public static void addClass(ClassEntry classEntry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addClass = connection.prepareStatement("insert into app.class (semester, coursecode,seats) values (?,?,?)");
            addClass.setString(1, classEntry.getSemester());
            addClass.setString(2, classEntry.getCourseCode());
            addClass.setInt(3, classEntry.getSeats());
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement(String.format("select coursecode from app.class where semester= '%s' order by coursecode", semester));
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }
    
    public static int getClassSeats(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        int seatsInt = -1;
        try
        {
            getClassSeats = connection.prepareStatement(String.format("select seats from app.class where semester= '%s' and courseCode = '%s' order by seats", semester, courseCode));
            resultSet = getClassSeats.executeQuery();
            
            if(resultSet.next())
            {
                seatsInt = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seatsInt;
        
        
    }

    public static void dropClass(String semester, String courseCode) {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement dropClassStatement = connection.prepareStatement("DELETE FROM app.class WHERE semester = ? AND courseCode = ?");
            dropClassStatement.setString(1, semester);
            dropClassStatement.setString(2, courseCode);
            dropClassStatement.executeUpdate();
            

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    
    public static void updateSeats(String semester, String courseCode) {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE app.class SET seats = seats + 1 WHERE semester = ? AND courseCode = ?");
            statement.setString(1, semester);
            statement.setString(2, courseCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
}




}
