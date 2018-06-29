package lwb.mdfs.web;

import lwb.mdfs.entity.DataBlock;
import lwb.mdfs.service.DataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/data")
public class DataController {

    private Logger logger = Logger.getLogger(DataController.class);

    private DataService dataService;

    @Autowired
    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String setDate(@RequestParam(value = "id")String id,
                          @RequestParam(value = "data")String data) {
        logger.info("save (id=" + id + "): " + data);
        dataService.setDataBlock(new DataBlock(id, data));
        return "OK";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getData(@RequestParam(value = "id")String id) {
        logger.info("get (id=" + id + ")");
        DataBlock dataBlock = dataService.getDataBlock(id);
        return dataBlock.getData();
    }
}
