package lwb.hdfs.entity;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class DataNode {

    private String id;
    private String ip;
    private int port;

    private int load;

    public DataNode(String id, String ip, int port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public void addLoad(int addon) {
        load += addon;
    }

    public String getData(String id) {
//        return "MOCK";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> paramMap = new HashMap<>();
        String url = getUrl() + "?id={id}";
        paramMap.put("id", id);
        HttpEntity<String> response = restTemplate.getForEntity(url, String.class, paramMap);
        return response.getBody();
    }

    public void setData(String id, String data) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> paramMap = new HashMap<>();
        String url = getUrl() + "?id={id}&data={data}";
        paramMap.put("id", id);
        paramMap.put("data", data);
        restTemplate.put(url, "", paramMap);
    }

    private String getUrl() {
        return "http://" + ip + ":" + port + "/data";
    }
}
