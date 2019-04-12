package server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PagesController {

    @RequestMapping("/index")
    public String indexHtml() {

        return "index";
    }

    @RequestMapping("/info")
    public String infoHtml(ModelMap modelMap, String ipmiHostId, String hostName) {
        modelMap.addAttribute("ipmiHostId",ipmiHostId);
        modelMap.addAttribute("hostName",hostName);
        return "info";
    }
}

