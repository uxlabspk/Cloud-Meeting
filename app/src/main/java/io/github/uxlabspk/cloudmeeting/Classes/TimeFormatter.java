package io.github.uxlabspk.cloudmeeting.Classes;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class TimeFormatter {
    private SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
    private long time;

    public TimeFormatter(long time) {
        this.time = time;
    }

    public String formattedTime() {
        return formatter.format(new Time(Long.parseLong(time + "")));
    }
}
