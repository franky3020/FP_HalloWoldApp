package Task;

public class ShowTask {
    private int imageId;
    private String title;
    private String type;
    private String address;
    private String date;
    private String time;
    private String status;

    public ShowTask(int imageId, String title, String type, String address,String date, String time) {
        this.imageId = imageId;
        this.title = title;
        this.type = type;
        this.address = address;
        this.date = date;
        this.time = time;
    }

    public ShowTask(int imageId, String title, String type, String address,String date, String time, String status) {
        this.imageId = imageId;
        this.title = title;
        this.type = type;
        this.address = address;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getImageId() {
        return imageId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
