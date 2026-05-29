package fa.training.dto;

public record UserVO (int userId, String username, int totalTransaction){

    @Override
    public String toString() {
        return "UserVO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", totalTransaction=" + totalTransaction +
                '}';
    }
}
