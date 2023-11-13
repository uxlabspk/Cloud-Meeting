package io.github.uxlabspk.cloudmeeting.Models;

public class AllMessagesModel {

    public static String SENT_BY_ME = "me";
    public static String SENT_BY_YOU = "you";

    private String message;
    private String sentBy;
    private long sentTime;

    private boolean isSeen;

    public long getSentTime() {
        return sentTime;
    }

    public void setSentTime(long sentTime) {
        this.sentTime = sentTime;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public AllMessagesModel(String message, String sentBy, long sentTime) {
        this.message = message;
        this.sentBy = sentBy;
        this.sentTime = sentTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
