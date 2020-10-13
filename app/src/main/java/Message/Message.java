package Message;

import java.sql.Timestamp;
import java.util.Date;

public class Message {
    private int MessageID;
    private String content = "";
    private int userID = -1;
    private int receiverID = -1;
    private int taskID = -1;
    private Timestamp postTime = new Timestamp(new Date().getTime());

    public Message(int messageID, String content, int userID, int receiverID, int taskID, Timestamp postTime) {
        MessageID = messageID;
        this.content = content;
        this.userID = userID;
        this.receiverID = receiverID;
        this.taskID = taskID;
        this.postTime = postTime;
    }

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }

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

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }
}
