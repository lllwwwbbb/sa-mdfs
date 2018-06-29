package lwb.hdfs.web;

import lwb.hdfs.entity.FileEntry;
import lwb.hdfs.service.FileManagerService;
import lwb.hdfs.service.FileService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@RequestMapping(value = "/fs")
public class FileManagerController {

    private String getFilePath(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI().substring("/fs".length());
        if (uri.charAt(0) == '/')
            uri = uri.substring(1);
        if (uri.length() > 1 && uri.charAt(uri.length() - 1) == '/')
            uri = uri.substring(0, uri.length() - 1);
        try {
            return URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return uri;
    }

    private final FileManagerService fileManagerService;
    private final FileService fileService;

    @Autowired
    public FileManagerController(FileManagerService fileManagerService, FileService fileService) {
        this.fileManagerService = fileManagerService;
        this.fileService = fileService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/**")
    public String getFileEntry(HttpServletRequest httpServletRequest) {
        String filePath = getFilePath(httpServletRequest);
        FileEntry fileEntry = fileManagerService.getFileEntry(filePath);
        if (fileEntry == null)
            return "NONE";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", fileEntry.getName());
        jsonObject.put("isDirectory", fileEntry.isDirectory());
        if (fileEntry.isDirectory())
            jsonObject.put("content", fileEntry.getContent());
        else // get File from DataNode
            jsonObject.put("content", fileService.getFile(filePath));
        return jsonObject.toString();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/**")
    public String setFileEntry(HttpServletRequest httpServletRequest,
                               @RequestParam(value = "file") String file) {
        String filePath = getFilePath(httpServletRequest);
        fileManagerService.setFile(filePath, file);
        return "OK";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/**")
    public String removeFileEntry(HttpServletRequest httpServletRequest) {
        String filePath = getFilePath(httpServletRequest);
        fileManagerService.removeFileEntry(filePath);
        return "OK";
    }
}
