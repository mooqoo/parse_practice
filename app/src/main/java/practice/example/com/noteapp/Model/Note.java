package practice.example.com.noteapp.Model;

/**
 * Created by mooqoo on 15/8/22.
 */
public class Note {

    public static final String EXTRA_ID = "noteId";
    public static final String EXTRA_TITLE = "noteTitle";
    public static final String EXTRA_CONTENT = "noteContent";

    //variables
    private String id;
    private String title;
    private String content;

    //constructor
    public Note() {
        id = "";
        title = "";
        content = "";
    }

    public Note(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    //getter and setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
