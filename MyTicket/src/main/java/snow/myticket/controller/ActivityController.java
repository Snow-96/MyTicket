package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Coupon;
import snow.myticket.bean.Member;
import snow.myticket.bean.Orders;
import snow.myticket.service.ActivityService;
import snow.myticket.service.CouponService;
import snow.myticket.service.MemberService;
import snow.myticket.service.OrdersService;
import snow.myticket.tool.VOHelper;
import snow.myticket.vo.ActivityVO;
import snow.myticket.vo.CouponListVO;

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
                CouponListVO couponListVO = new CouponListVO(member.getId(), 0, 0, 0);
                List<Coupon> couponList = couponService.getCouponsByMemberId(member.getId());
                for (Coupon coupon : couponList) {
                    if (coupon.getDiscount() == 0.95) {
                        couponListVO.setAmount_1st(couponListVO.getAmount_1st() + 1);
                        couponListVO.getId_1st().add(coupon.getId());
                    }
                    else if (coupon.getDiscount() == 0.9) {
                        couponListVO.setAmount_2nd(couponListVO.getAmount_2nd() + 1);
                        couponListVO.getId_2nd().add(coupon.getId());
                    }
                    else if (coupon.getDiscount() == 0.85) {
                        couponListVO.setAmount_3rd(couponListVO.getAmount_3rd() + 1);
                        couponListVO.getId_3rd().add(coupon.getId());
                    }
                }
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
}