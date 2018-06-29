package lwb.hdfs.service;

import lwb.hdfs.entity.Directory;
import lwb.hdfs.entity.File;
import lwb.hdfs.entity.FileEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileManagerService {

    private Directory rootDirectory = new Directory("");
    private final FileService fileService;

    @Autowired
    public FileManagerService(FileService fileService) {
        this.fileService = fileService;
    }


    public FileEntry getFileEntry(String filePath) {
        if (filePath.length() == 0)
            return rootDirectory;
        return rootDirectory.getFileEntryByPath(filePath);
    }

    public void setFile(String filePath, String content) {
        String[] pathArray = filePath.split("/");
        File file = new File(pathArray[pathArray.length - 1], content);
        rootDirectory.setFileEntryByPath(filePath, file);
        fileService.setFile(filePath, content);
    }

    public void removeFileEntry(String filePath) {
        if (filePath.length() == 0)
            return;
        rootDirectory.removeFileEntryByPath(filePath);
    }
}
