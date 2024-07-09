package mails;

public class request {
    private long fromId;
    private long toId;
    private long requestId;

    // Constructor to initialize all fields
    public request(long requestId, long fromId, long toId){
        this.requestId = requestId;
        this.fromId = fromId;
        this.toId = toId;
    }

    // Constructor without requestId, useful for creating new requests before saving to database
    public request(long fromId, long toId){
        this.fromId = fromId;
        this.toId = toId;
    }

    // Getter for requestId
    public long getRequestId() {
        return requestId;
    }

    // Setter for requestId
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    // Getter for fromId
    public long getFromId() {
        return fromId;
    }

    // Setter for fromId
    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    // Getter for toId
    public long getToId() {
        return toId;
    }

    // Setter for toId
    public void setToId(long toId) {
        this.toId = toId;
    }

    // toString method to provide string representation of the request object
    @Override
    public String toString() {
        return "FriendRequest{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", requestId=" + requestId +
                '}';
    }
}
