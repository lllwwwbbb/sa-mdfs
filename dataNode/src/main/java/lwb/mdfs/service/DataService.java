package lwb.mdfs.service;

import lwb.mdfs.entity.DataBlock;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class DataService {

    private Map<String, DataBlock> dataBlockMap = new TreeMap<String, DataBlock>();

    public void setDataBlock(DataBlock dataBlock) {
        dataBlockMap.put(dataBlock.getId(), dataBlock);
    }

    public DataBlock getDataBlock(String id) {
        return dataBlockMap.get(id);
    }
}
