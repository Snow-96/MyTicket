package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import snow.myticket.bean.*;
import snow.myticket.service.ActivityService;
import snow.myticket.service.CouponService;
import snow.myticket.service.MemberService;
import snow.myticket.service.OrdersService;
import snow.myticket.tool.VOHelper;
import snow.myticket.vo.ActivityVO;
import snow.myticket.vo.CouponListVO;
import snow.myticket.vo.SeatVO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {
    private final ActivityService activityService;
    private final CouponService couponService;
    private final VOHelper voHelper;

    @Autowired
    public ActivityController(ActivityService activityService, CouponService couponService, VOHelper voHelper) {
        this.activityService = activityService;
        this.couponService = couponService;
        this.voHelper = voHelper;
    }

    @RequestMapping("/activity")
    public String getActivity(Model model, HttpServletRequest httpServletRequest){
        if(httpServletRequest.getSession(false) != null){
            Member member = ((Member)httpServletRequest.getSession(false).getAttribute("member"));

            if(member != null) {
                CouponListVO couponListVO = voHelper.couponListConvert(couponService.getCouponsByMemberId(member.getId()),member.getId());
                model.addAttribute("couponListVO", couponListVO);
            }
        }

        List<Activity> activityList = activityService.getAllActivities();
        List<ActivityVO> activityVOList = new ArrayList<>();

        for(Activity activity : activityList)
            activityVOList.add(voHelper.activityConvert(activity));

        model.addAttribute("activityList",activityVOList);
        return "activity";
    }

    @RequestMapping("/getActivitySeat")
    @ResponseBody
    public SeatVO getActivitySeat(@RequestParam Integer activityId){
        List<Seat> seatList = activityService.getActivitySeat(activityId);
        return voHelper.seatConvert(seatList,activityId);
    }
}