package lwb.hdfs.entity;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Directory extends FileEntry {

    private Map<String, FileEntry> fileEntryMap = new TreeMap<String, FileEntry>();

    public Directory(String name) {
        super(name, true);
    }

    public void setFileEntryByPath(String path, FileEntry fileEntry) {
        String[] pathParts = path.split("/", 2);
        if (pathParts.length == 1) {
            fileEntryMap.put(pathParts[0], fileEntry);
            return;
        }
        FileEntry directFileEntry = fileEntryMap.get(pathParts[0]);
        if (directFileEntry == null || !directFileEntry.isDirectory()) {
            directFileEntry = new Directory(pathParts[0]);
            fileEntryMap.put(pathParts[0], directFileEntry);
        }
        ((Directory)directFileEntry).setFileEntryByPath(pathParts[1], fileEntry);
    }

    public FileEntry getFileEntryByPath(String path) {
        String[] pathParts = path.split("/", 2);
        FileEntry directFileEntry = fileEntryMap.get(pathParts[0]);
        if (directFileEntry == null)
            return null;
        if (pathParts.length == 1 || !directFileEntry.isDirectory())
            return directFileEntry;
        return ((Directory)directFileEntry).getFileEntryByPath(pathParts[1]);

    }

    public void removeFileEntryByPath(String path) {
        String[] pathParts = path.split("/", 2);
        if (pathParts.length == 1) {
            fileEntryMap.remove(pathParts[0]);
            return;
        }
        FileEntry directFileEntry = fileEntryMap.get(pathParts[0]);
        if (directFileEntry == null || !directFileEntry.isDirectory())
            return;
        ((Directory)directFileEntry).removeFileEntryByPath(pathParts[1]);
    }

    @Override
    public String getContent() {
//        StringBuilder entryList = new StringBuilder();
//        for (FileEntry fileEntry : fileEntryMap.values())
//            entryList.append(fileEntry.getName()).append('\n');
//        return entryList.toString();
        JSONObject jsonObject = new JSONObject();
        for (String name : fileEntryMap.keySet()) {
            FileEntry fileEntry = fileEntryMap.get(name);
            JSONObject entryJson = new JSONObject();
            entryJson.put("name", fileEntry.getName());
            entryJson.put("isDirectory", fileEntry.isDirectory());
            jsonObject.put(name, entryJson);
        }
        return jsonObject.toString();
    }
}
