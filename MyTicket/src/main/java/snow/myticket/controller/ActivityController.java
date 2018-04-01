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
    private final MemberService memberService;
    private final OrdersService ordersService;
    private final VOHelper voHelper;

    @Autowired
    public ActivityController(ActivityService activityService, CouponService couponService, MemberService memberService, OrdersService ordersService,VOHelper voHelper) {
        this.activityService = activityService;
        this.couponService = couponService;
        this.memberService = memberService;
        this.ordersService = ordersService;
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

    @RequestMapping("/pay")
    public String getPay(Model model, @RequestParam Integer ordersId){
        model.addAttribute("ordersVO", voHelper.ordersConvert(ordersService.getOrders(ordersId)));
        return "pay";
    }

    @RequestMapping("/calculatePrice")
    @ResponseBody
    public Map<String,String> calculatePrice(@RequestBody Orders orders){
        Map<String,String> result = new HashMap<>();
        try {
            result.put("sum", memberService.calculateTotalPrice(orders).toString());
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/reserveOrders")
    @ResponseBody
    public Map<String,String> reserveOrders(@RequestBody Orders orders){
        Map<String,String> result = new HashMap<>();
        try {
            result.put("ordersId", memberService.reserveOrders(orders).getId().toString());
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/reserveOrdersSeat")
    @ResponseBody
    public Map<String,String> reserveOrdersSeat(@RequestParam Integer[][] seatArray, @RequestParam Integer ordersId, @RequestParam Integer activityId){
        Map<String,String> result = new HashMap<>();
        try {
            for(int i=0; i<seatArray.length; i++){
                if(seatArray[i][0] != -1){
                    Seat seat = new Seat(ordersId,activityId,seatArray[i][1],seatArray[i][2],seatArray[i][0]);
                    memberService.reserveOrdersSeat(seat);
                }
            }
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/getActivitySeat")
    @ResponseBody
    public SeatVO getActivitySeat(@RequestParam Integer activityId){
        List<Seat> seatList = activityService.getActivitySeat(activityId);
        return voHelper.seatConvert(seatList,activityId);
    }
}