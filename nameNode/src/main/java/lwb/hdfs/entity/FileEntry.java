package lwb.hdfs.entity;

public abstract class FileEntry {

    private String name;
    private final Boolean isDirectory;

    public FileEntry(String name, Boolean isDirectory) {
        this.name = name;
        this.isDirectory = isDirectory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Boolean isDirectory() {
        return isDirectory;
    }

    public abstract String getContent();

}
