
import java.sql.Timestamp;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author carson
 */
public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentId;
    private String status;
    private Timestamp timestamp;
    java.sql.Timestamp currentTimestamp;

    public ScheduleEntry(String semester, String courseCode, String studentId, String status, Timestamp timestamp) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentId = studentId;
        this.status = status;
        this.timestamp = timestamp;
    }

   /// ScheduleEntry(String entrySemester, String entryCourseCode, String entryStudentID, String entryStatus, java.sql.Timestamp entryTimestamp) {
        ///throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    ///}

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    
    
}
