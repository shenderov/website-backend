package me.shenderov.website.entities;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MessageStatusWrapper {

    private List<UUID> uuids;
    private MessageStatus status;

    public List<UUID> getUuids() {
        return uuids;
    }

    public void setUuids(List<UUID> uuids) {
        this.uuids = uuids;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageStatusWrapper)) return false;
        MessageStatusWrapper that = (MessageStatusWrapper) o;
        return Objects.equals(getUuids(), that.getUuids()) &&
                getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuids(), getStatus());
    }
}
