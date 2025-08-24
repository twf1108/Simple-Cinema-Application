package UserData;

public abstract class UserData {
    protected String username;

    public UserData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract void accessPage();
}
