
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carson
 */
public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        connection = DBConnection.getConnection();
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?,?,?,?,?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentId());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.currentTimestamp);
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> studentSchedule = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleByStudent = connection.prepareStatement(String.format("select semester, coursecode, studentid, status, timestamp from app.schedule where semester='%s' AND studentId='%s' order by studentid", semester, studentID));
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
                String entrySemester = resultSet.getString("semester");
                String entryCourseCode = resultSet.getString("coursecode");
                String entryStudentID = resultSet.getString("studentid");
                String entryStatus = resultSet.getString("status");
                java.sql.Timestamp entryTimestamp = resultSet.getTimestamp("timestamp");
                
                ScheduleEntry scheduleEntry = new ScheduleEntry(entrySemester, entryCourseCode, entryStudentID, entryStatus, entryTimestamp);

                studentSchedule.add(scheduleEntry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentSchedule;
        
    }
    
    
    public static int getScheduledStudentCount(String currentSemester,String courseCode)
    {
        connection = DBConnection.getConnection();
        int scheduledStudentCount = -1;
        try
        {
            PreparedStatement getScheduleCount = connection.prepareStatement(String.format("SELECT COUNT(*) FROM app.schedule WHERE semester = '%s' AND courseCode = '%s'", currentSemester, courseCode));
            resultSet = getScheduleCount.executeQuery();
            
            if (resultSet.next())
            {
                scheduledStudentCount = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduledStudentCount;
        
        
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<>();
        Connection connection = DBConnection.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM app.schedule WHERE semester = ? AND courseCode = ? AND status = 'W'");
                statement.setString(1, semester);
                statement.setString(2, courseCode);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String studentID = resultSet.getString("studentID");
                    String status = resultSet.getString("status");
                    java.sql.Timestamp timestamp = resultSet.getTimestamp("timestamp");

                    ScheduleEntry entry = new ScheduleEntry(semester, courseCode, studentID, status, timestamp);
                    waitlistedStudents.add(entry);
                }

             
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        

        return waitlistedStudents;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM app.schedule WHERE semester = ? AND studentID = ? AND courseCode = ?");
                statement.setString(1, semester);
                statement.setString(2, studentID);
                statement.setString(3, courseCode);
                statement.executeUpdate();
                
                
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            try {
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM app.schedule WHERE semester = ? AND courseCode = ?");
                statement.setString(1, semester);
                statement.setString(2, courseCode);
                statement.executeUpdate();
                
             
            } catch (SQLException e) {
                e.printStackTrace(); 
            }
        }
    }
        public static ArrayList<ScheduleEntry> getScheduledStudentsByClass(String semester, String courseCode) {
            ArrayList<ScheduleEntry> scheduledStudents = new ArrayList<>();
            Connection connection = DBConnection.getConnection();

                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM app.schedule WHERE semester = ? AND courseCode = ? AND status = 'S'");
                    statement.setString(1, semester);
                    statement.setString(2, courseCode);
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        String studentID = resultSet.getString("studentID");
                        String status = resultSet.getString("status");
                        java.sql.Timestamp timestamp = resultSet.getTimestamp("timestamp");

                        ScheduleEntry entry = new ScheduleEntry(semester, courseCode, studentID, status, timestamp);
                        scheduledStudents.add(entry);
                    }


                } catch (SQLException e) {
                    e.printStackTrace(); 
                }


            return scheduledStudents;
        }
    
    public static void updateScheduleEntry(ScheduleEntry entry) {
        Connection connection = DBConnection.getConnection();
    PreparedStatement statement = null;
    
    try {
        statement = connection.prepareStatement(
                "UPDATE app.schedule SET status = 'S', timestamp = ? WHERE courseCode = ? AND studentID = ? AND semester = ?");
        statement.setTimestamp(1, entry.getTimestamp());
        statement.setString(2, entry.getCourseCode());
        statement.setString(3, entry.getStudentId());
        statement.setString(4, entry.getSemester());
        
       
        int rowsUpdated = statement.executeUpdate();
        
        if (rowsUpdated > 0) {
            System.out.println("Schedule entry updated successfully.");
        } else {
            System.out.println("No rows were updated. Entry not found or already updated.");
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
        
    }
    }}

