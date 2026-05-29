package fa.training.entity;

import java.time.LocalDateTime;

public class TransactionHistory {
    private int transactionId;
    private int userId;
    private int customerId;
    private long point;
    private String action;
    private String note;
    private LocalDateTime createdDateTime;

    public TransactionHistory() {}

    public TransactionHistory(int transactionId, int userId, int customerId, long point, String action, String note, LocalDateTime createdDateTime) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.customerId = customerId;
        this.point = point;
        this.action = action;
        this.note = note;
        this.createdDateTime = createdDateTime;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", customerId=" + customerId +
                ", point=" + point +
                ", action='" + action + '\'' +
                ", note='" + note + '\'' +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
