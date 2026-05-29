package fa.training.entity;

import java.time.LocalDate;

public class User {
    private int userId;
    private String username;
    private String email;
    private long point;
    private LocalDate dateOfBirth;

    public User() {}

    public User(int userId, String username, String email, long point, LocalDate dateOfBirth) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.point = point;
        this.dateOfBirth = dateOfBirth;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", point=" + point +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
