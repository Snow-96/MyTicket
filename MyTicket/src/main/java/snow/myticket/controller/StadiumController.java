package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Stadium;
import snow.myticket.service.ActivityService;
import snow.myticket.service.StadiumService;
import snow.myticket.tool.VOHelper;
import snow.myticket.vo.ActivityVO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StadiumController {
    private final StadiumService stadiumService;
    private final ActivityService activityService;
    private final VOHelper voHelper;

    @Autowired
    public StadiumController(StadiumService stadiumService, ActivityService activityService, VOHelper voHelper) {
        this.stadiumService = stadiumService;
        this.activityService = activityService;
        this.voHelper = voHelper;
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

    @RequestMapping("/stadiumModify")
    public String getStadiumModify(){
        return "stadiumModify";
    }

    @RequestMapping("/stadiumActivity")
    public String getStadiumActivity(Model model, HttpServletRequest httpServletRequest){
        String stadiumCode = ((Stadium)httpServletRequest.getSession(false).getAttribute("stadium")).getCode();
        List<Activity> activityList = activityService.getActivitiesByStadiumCode(stadiumCode);

        List<ActivityVO> activityVOList = new ArrayList<>();

        for(Activity activity : activityList)
            activityVOList.add(voHelper.activityConvert(activity));

        model.addAttribute("activityList",activityVOList);

        return "stadiumActivity";
    }

    @RequestMapping("/checkModifyStatus")
    @ResponseBody
    public Map<String,String> checkModifyStatus(@RequestParam String stadiumCode){
        Map<String,String> result = new HashMap<>();
        try {
            if(stadiumService.checkModifyStatus(stadiumCode) == null) {
                result.put("result", "success");
            }
            else {
                result.put("result", "fail");
                result.put("message", "已存在修改申请，暂时无法提交");
            }
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        return result;
    }

    @RequestMapping("/modifyStadiumInfo")
    @ResponseBody
    public Map<String,String> modifyStadiumInfo(@RequestBody Stadium stadium){
        Map<String,String> result = new HashMap<>();
        try {
           stadiumService.modifyStadiumInfo(stadium);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/releaseActivity")
    @ResponseBody
    public Map<String,String> releaseActivity(@RequestBody Activity activity){
        Map<String,String> result = new HashMap<>();
        try {
            stadiumService.releaseActivity(activity);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

}
