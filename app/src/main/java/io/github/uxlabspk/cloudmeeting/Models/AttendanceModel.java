package io.github.uxlabspk.cloudmeeting.Models;

import java.util.Map;

public class AttendanceModel {
    private Map<String, String> attendanceDate;

    public AttendanceModel() {
        // empty constructor
    }

    public AttendanceModel(Map<String, String> attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Map<String, String> getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Map<String, String> attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}
