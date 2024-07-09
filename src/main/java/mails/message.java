package mails;

import java.sql.Timestamp;

public class message {
    private long messageId;
    private long fromId;
    private long toId;
    private String message;
    private Timestamp sentTime;

    // Constructor to initialize all fields
    public message(long messageId, long fromId, long toId, String message, Timestamp sentTime){
        this.messageId = messageId;
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.sentTime = sentTime;
    }

    // Constructor without messageId, useful for creating new messages before saving to database
    public message(long fromId, long toId, String message, Timestamp sentTime){
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.sentTime = sentTime;
    }

    // Getter for messageId
    public long getMessageId() {
        return messageId;
    }

    // Setter for messageId
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    // Getter for fromId
    public long getFromId(){
        return fromId;
    }

    // Setter for fromId
    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    // Getter for toId
    public long getToId(){
        return toId;
    }

    // Setter for toId
    public void setToId(long toId) {
        this.toId = toId;
    }

    // Getter for message
    public String getMessage(){
        return message;
    }

    // Setter for message
    public void setMessage(String message){
        this.message = message;
    }

    // Getter for sentTime
    public Timestamp getSentTime() {
        return sentTime;
    }

    // toString method to provide string representation of the message object
    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", message='" + message + '\'' +
                ", sentTime=" + sentTime +
                '}';
    }
}
