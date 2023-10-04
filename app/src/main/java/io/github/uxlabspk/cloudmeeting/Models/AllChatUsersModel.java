package io.github.uxlabspk.cloudmeeting.Models;

public class AllChatUsersModel {
    private String userProfilePic, userAbout;
    private String userName, userEmail, userGender, userLastMessage, userPassword;
    private long lastSeen;

    public AllChatUsersModel(String userName, String userLastMessage, long lastSeen) {
        this.userName = userName;
        this.userLastMessage = userLastMessage;
        this.lastSeen = lastSeen;
    }

    public AllChatUsersModel(String userName, String userEmail, String userGender, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userPassword = userPassword;
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

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserLastMessage() {
        return userLastMessage;
    }

    public void setUserLastMessage(String userLastMessage) {
        this.userLastMessage = userLastMessage;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }
}
