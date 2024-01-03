package io.github.uxlabspk.cloudmeeting.Models;

public class AllChatUsersModel {
    private String userID;
    private String userProfilePic;
    private String userName, userEmail;
    private long lastSeen;

    public AllChatUsersModel() {
    }

    public AllChatUsersModel(String userID, String userName, String userEmail, String userProfilePic) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfilePic = userProfilePic;
    }

    public AllChatUsersModel(String userID, String userProfilePic, String userName, String userEmail, long lastSeen) {
        this.userID = userID;
        this.userProfilePic = userProfilePic;
        this.userName = userName;
        this.userEmail = userEmail;
        this.lastSeen = lastSeen;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
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

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
