package sa.luowebb;

import cn.edu.nju.sa2017.pipefilter.App;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BatchController {

    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public String launchBatch(ModelMap model) {
        App.main(new String[]{"App"});
        return "batch";
    }
}
