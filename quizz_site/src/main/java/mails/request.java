package mails;

public class request {
    private long fromId;
    private long toId;
    private long requestId;

    public request(long requestId, long fromId, long toId){
        this.requestId = requestId;
        this.fromId = fromId;
        this.toId = toId;
    }

    public request(long fromId, long toId){
        this.fromId = fromId;
        this.toId = toId;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", requestId=" + requestId +
                '}';
    }
}