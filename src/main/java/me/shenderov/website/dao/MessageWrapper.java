package me.shenderov.website.dao;

import me.shenderov.website.entities.Message;
import me.shenderov.website.entities.MessageDeliveryStatus;
import me.shenderov.website.entities.MessageStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.UUID;

@Document(collection = "message")
public class MessageWrapper {

    @Id
    private UUID uuid;
    private Long timestamp;
    private MessageStatus status;
    private MessageDeliveryStatus deliveryStatus;
    private Message message;

    public MessageWrapper(Message message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.status = MessageStatus.NEW;
        this.deliveryStatus = MessageDeliveryStatus.NEW;
        this.uuid = UUID.randomUUID();
    }

    public MessageWrapper() {
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

    public MessageDeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(MessageDeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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
                ", deliveryStatus=" + deliveryStatus +
                ", message=" + message +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageWrapper)) return false;
        MessageWrapper wrapper = (MessageWrapper) o;
        return Objects.equals(getUuid(), wrapper.getUuid()) &&
                Objects.equals(getTimestamp(), wrapper.getTimestamp()) &&
                getStatus() == wrapper.getStatus() &&
                getDeliveryStatus() == wrapper.getDeliveryStatus() &&
                Objects.equals(getMessage(), wrapper.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getTimestamp(), getStatus(), getDeliveryStatus(), getMessage());
    }
}
