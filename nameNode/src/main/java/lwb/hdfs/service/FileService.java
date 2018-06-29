package lwb.hdfs.service;

import lwb.hdfs.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class FileService {

    @Value("${data.block.size}")
    private int BLOCK_SIZE;
    private Map<String, Integer> fileBlockNumMap = new TreeMap<>();
    private final DataNodeService dataNodeService;

    @Autowired
    public FileService(DataNodeService dataNodeService) {
        this.dataNodeService = dataNodeService;
    }

    public void setFile(String filePath, String content) {
        fileBlockNumMap.put(filePath, content.length() / BLOCK_SIZE + 1);
        for (int i = 0; i * BLOCK_SIZE < content.length(); i ++) {
            int begin = i * BLOCK_SIZE;
            int end = Math.min((i+1) * BLOCK_SIZE, content.length());
            String subData = content.substring(begin, end);
            dataNodeService.setData(getFileBlockId(filePath, i), subData);
        }
    }

    public String getFile(String filePath) {
        int blockNum = fileBlockNumMap.get(filePath);
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < blockNum; i ++) {
            String subData = dataNodeService.getData(getFileBlockId(filePath, i));
            data.append(subData);
        }
        return data.toString();
    }

    private String getFileBlockId(String filePath, int n) {
        return filePath + n;
    }
}
