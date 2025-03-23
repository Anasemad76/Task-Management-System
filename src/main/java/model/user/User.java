package model.user;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// used by JPA to differentiate between different subclass types
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
public abstract class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    public User(){

    }

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }



    public String getUsername() { return username; }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public abstract void displayMenu();
}

