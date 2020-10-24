package User;

public class User {
    private String name;
    private int id;
    private String phone;
    private String firebaseUID;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirebaseUID() {
        return firebaseUID;
    }

    public User setFirebaseUID(String firebaseUID) {
        this.firebaseUID = firebaseUID;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", phone='" + phone + '\'' +
                ", firebaseUID='" + firebaseUID + '\'' +
                '}';
    }
}
