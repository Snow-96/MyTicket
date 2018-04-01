package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import snow.myticket.service.ManagerService;
import snow.myticket.service.StadiumService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ManagerController {
    private final StadiumService stadiumService;
    private final ManagerService managerService;

    @Autowired
    public ManagerController(StadiumService stadiumService, ManagerService managerService) {
        this.stadiumService = stadiumService;
        this.managerService = managerService;
    }

    @RequestMapping("/managerCenter")
    public String getManagerCenter(){
        return "managerCenter";
    }

    @RequestMapping("/managerApply")
    public String getManagerApply(Model model){
        model.addAttribute("applyList", stadiumService.getStadiumApply());
        return "managerApply";
    }

    @RequestMapping("/managerReview")
    public String getManagerReview(Model model){
        model.addAttribute("reviewList", stadiumService.getStadiumReview());
        return "managerReview";
    }

    @RequestMapping("/passStadiumApplication")
    @ResponseBody
    public Map<String,String> passStadiumApplication(@RequestParam Integer stadiumId){
        Map<String,String> result = new HashMap<>();
        try {
            managerService.passStadiumApplication(stadiumId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/rejectStadiumApplication")
    @ResponseBody
    public Map<String,String> rejectStadiumApplication(@RequestParam Integer stadiumId){
        Map<String,String> result = new HashMap<>();
        try {
            managerService.rejectStadiumApplication(stadiumId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/passStadiumModifyInfo")
    @ResponseBody
    public Map<String,String> passStadiumModifyInfo(@RequestParam Integer reviewId){
        Map<String,String> result = new HashMap<>();
        try {
            managerService.passStadiumModifyInfo(reviewId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/rejectStadiumModifyInfo")
    @ResponseBody
    public Map<String,String> rejectStadiumModifyInfo(@RequestParam Integer reviewId){
        Map<String,String> result = new HashMap<>();
        try {
            managerService.rejectStadiumModifyInfo(reviewId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }
}
