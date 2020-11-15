package Message;

import java.time.LocalDateTime;

public class Message {
    private int messageID;
    private String content = "";
    private int userID = -1;
    private int receiverID = -1;
    private LocalDateTime postTime;
    private boolean isSeen;

    public Message(int messageID, String content, int userID, int receiverID, LocalDateTime postTime) {
        this.messageID = messageID;
        this.content = content;
        this.userID = userID;
        this.receiverID = receiverID;
        this.postTime = postTime;
    }


    public Message(String content, int userID, int receiverID, LocalDateTime postTime) {
        this.content = content;
        this.userID = userID;
        this.receiverID = receiverID;
        this.postTime = postTime;
    }

    public int getMessageID() { return messageID; }

    public void setMessageID(int messageID) { this.messageID = messageID; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
