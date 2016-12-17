package photoNet.utils;

/**
 * Created by Julien on 17/12/2016.
 */
public class Photo {

    private int id;
    private String path;
    private String title;
    private String desc;
    private Profile author;


    public int getId() {
        return id;
    }

    public Photo setId(int id) {
        this.id = id;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Photo setPath(String path) {
        this.path = path;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Photo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public Photo setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Profile getAuthor() {
        return author;
    }

    public Photo setAuthor(Profile author) {
        this.author = author;
        return this;
    }


}
