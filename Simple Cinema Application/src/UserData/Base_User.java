package UserData;

public abstract class Base_User {
    protected String username;

    public Base_User(String username) {
        this.username = username;
    }

    public abstract void showMenu(); // Polymorphic method
}
