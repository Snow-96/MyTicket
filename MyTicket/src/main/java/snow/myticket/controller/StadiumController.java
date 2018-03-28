package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import snow.myticket.bean.Stadium;
import snow.myticket.service.StadiumService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StadiumController {
    private final StadiumService stadiumService;

    @Autowired
    public StadiumController(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }

    @RequestMapping("/stadiumCenter")
    public String getStadiumCenter(){
        return "stadiumCenter";
    }

    @RequestMapping("/stadiumInfo")
    public String getStadiumInfo(Model model, HttpServletRequest httpServletRequest){
        String stadiumCode = ((Stadium)httpServletRequest.getSession(false).getAttribute("stadium")).getCode();
        model.addAttribute("stadium", stadiumService.getStadium(stadiumCode));
        return "stadiumInfo";
    }

}
