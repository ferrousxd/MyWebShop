package domain;

public class Message {
    private String sender;
    private String receiver;
    private String message;
    private long sender_id;
    private long receiver_id;

    public Message(long sender_id, String message, long receiver_id) {
        setSender_id(sender_id);
        setMessage(message);
        setReceiver_id(receiver_id);
    }

    public Message(String sender, String message, String receiver) {
        setSender(sender);
        setMessage(message);
        setReceiver(receiver);
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSender_id() {
        return sender_id;
    }

    public void setSender_id(long sender_id) {
        this.sender_id = sender_id;
    }

    public long getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(long receiver_id) {
        this.receiver_id = receiver_id;
    }
}
