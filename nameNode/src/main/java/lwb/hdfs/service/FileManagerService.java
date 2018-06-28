package lwb.hdfs.service;

import lwb.hdfs.entity.Directory;
import lwb.hdfs.entity.File;
import lwb.hdfs.entity.FileEntry;
import org.springframework.stereotype.Service;

@Service
public class FileManagerService {

    private Directory rootDirectory = new Directory("");


    public FileEntry getFileEntry(String filePath) {
        if (filePath.length() == 0)
            return rootDirectory;
        return rootDirectory.getFileEntryByPath(filePath);
    }

    public void setFile(String filePath, String content) {
        String[] pathArray = filePath.split("/");
        File file = new File(pathArray[pathArray.length - 1], content);
        rootDirectory.setFileEntryByPath(filePath, file);
    }

    public void removeFileEntry(String filePath) {
        if (filePath.length() == 0)
            return;
        rootDirectory.removeFileEntryByPath(filePath);
    }
}
