package model.user;




public abstract class User{
    protected final String username;
    protected final String password;
    protected final boolean isAdmin;
    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername() { return username; }

    public boolean getIsAdmin() {
        return isAdmin;
    }
    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public abstract void displayMenu();
}

