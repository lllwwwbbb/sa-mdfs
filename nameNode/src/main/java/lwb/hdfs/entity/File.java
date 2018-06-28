package lwb.hdfs.entity;

public class File extends FileEntry {

    private String content;

    public File(String name, String content) {
        super(name, false);
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
