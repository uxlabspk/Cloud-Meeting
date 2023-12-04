package io.github.uxlabspk.cloudmeeting.Models;

public class AllChatUsersModel {
    private String userID;
    private String userProfilePic, userAbout;
    private String userName, userEmail, userLastMessage;
    private long lastSeen;

    public AllChatUsersModel() {
    }
    public AllChatUsersModel(String userID, String userProfilePic, String userAbout, String userName, String userEmail, String userLastMessage, long lastSeen) {
        this.userID = userID;
        this.userProfilePic = userProfilePic;
        this.userAbout = userAbout;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userLastMessage = userLastMessage;
        this.lastSeen = lastSeen;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public String getUserAbout() {
        return userAbout;
    }

    public void setUserAbout(String userAbout) {
        this.userAbout = userAbout;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserLastMessage() {
        return userLastMessage;
    }

    public void setUserLastMessage(String userLastMessage) {
        this.userLastMessage = userLastMessage;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }
}
