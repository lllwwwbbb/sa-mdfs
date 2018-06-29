package lwb.mdfs.entity;

public class DataBlock {

    private String id;
    private String data;

    public DataBlock(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public DataBlock(String id) {
        this(id, "");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
