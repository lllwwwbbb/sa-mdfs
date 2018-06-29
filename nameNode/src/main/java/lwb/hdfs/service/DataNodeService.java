package lwb.hdfs.service;

import lwb.hdfs.entity.DataNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DataNodeService {

    @Value("${data.duplicate.num}")
    private int DUPLICATE_NUM;

    private List<DataNode> dataNodeList = new ArrayList<>();
    private Map<String, Set<DataNode>> dataNodeMap = new TreeMap<>();

    public DataNodeService() {

    }

    public void onDataNodeRegister(String id, String ip, int port) {
        dataNodeList.add(new DataNode(id, ip, port));
    }

    public void onDataNodeDown(String id) {
        DataNode dataNode = null;
        for (DataNode node : dataNodeList) {
            if (node.getId().equals(id)) {
                dataNode = node;
                break;
            }
        }
        dataNodeList.remove(dataNode);
        for (String key : dataNodeMap.keySet()) {
            Set<DataNode> dataNodeSet = dataNodeMap.get(key);
            if (dataNodeSet.contains(dataNode)) {
                dataNodeSet.remove(dataNode);
                setData(key, getData(key));
            }
        }
    }

    public void setData(String id, String data) {
        dataNodeList.sort((a, b) -> a.getLoad() - b.getLoad());
        for (int i = 0; i < dataNodeList.size() && i < DUPLICATE_NUM; i ++) {
            DataNode dataNode = dataNodeList.get(i);
            Set<DataNode> dataNodeSet = dataNodeMap.get(id);
            if (dataNodeSet == null) {
                dataNodeSet = new HashSet<>();
                dataNodeSet.add(dataNode);
                dataNodeMap.put(id, dataNodeSet);
            } else {
                dataNodeSet.add(dataNode);
            }
            dataNode.setData(id, data);
            dataNode.addLoad(1);
        }
    }

    public String getData(String id) {
        Set<DataNode> dataNodeSet =  dataNodeMap.get(id);
        DataNode dataNode = Collections.min(dataNodeSet, Comparator.comparingInt(DataNode::getLoad));
        dataNode.addLoad(1);
        return dataNode.getData(id);
    }
}
