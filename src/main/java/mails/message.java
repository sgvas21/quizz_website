package mails;

import java.sql.Timestamp;

public class message {
    private long messageId;
    private long fromId;
    private long toId;
    private String message;
    private Timestamp sentTime;

    public message(long messageId, long fromId, long toId, String message, Timestamp sentTime){
        this.messageId = messageId;
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.sentTime = sentTime;
    }

    public message(long fromId, long toId, String message, Timestamp sentTime){
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.sentTime = sentTime;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getFromId(){
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId(){
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public Timestamp getSentTime() { return sentTime; }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", Message='" + message + '\'' +
                '}';
    }
}