package io.github.uxlabspk.cloudmeeting.Models;

public class AttendanceViewModel {
    private String className;
    private String studentName;
    private String attendanceCount;

    public AttendanceViewModel() {
    }

    public AttendanceViewModel(String className, String studentName, String attendanceCount) {
        this.className = className;
        this.studentName = studentName;
        this.attendanceCount = attendanceCount;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(String attendanceCount) {
        this.attendanceCount = attendanceCount;
    }
}
