package me.shenderov.website.dao;

import me.shenderov.website.entities.Message;
import me.shenderov.website.entities.MessageStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "message")
public class MessageWrapper {

    @Id
    private UUID uuid;
    private Long timestamp;
    private MessageStatus status;
    private Message message;

    public MessageWrapper(Message message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.status = MessageStatus.NEW;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "uuid=" + uuid +
                ", timestamp=" + timestamp +
                ", status=" + status +
                ", message=" + message +
                '}';
    }
}
