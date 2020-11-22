package User;

public class User {
    private String name;
    private int id;
    private String firebaseUID;
    private int point = 0;

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

    public String getFirebaseUID() {
        return firebaseUID;
    }

    public User setFirebaseUID(String firebaseUID) {
        this.firebaseUID = firebaseUID;
        return this;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "User{" +
                "name=" + name +
                ", id=" + id +
                ", firebaseUID=" + firebaseUID +
                ", point=" + point +
                '}';
    }
}
