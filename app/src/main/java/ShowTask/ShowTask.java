package ShowTask;

public class ShowTask {
    private String name;
    private String content;
    private int imageId;

    public ShowTask(String name, String content, int imageId) {
        this.name = name;
        this.content = content;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public int getImageId() {
        return imageId;
    }
}
