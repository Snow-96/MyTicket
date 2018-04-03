package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Member;
import snow.myticket.bean.Orders;
import snow.myticket.bean.Stadium;
import snow.myticket.service.ActivityService;
import snow.myticket.service.MemberService;
import snow.myticket.service.OrdersService;
import snow.myticket.service.StadiumService;
import snow.myticket.tool.VOHelper;
import snow.myticket.vo.ActivityVO;
import snow.myticket.vo.OffLineOrdersVO;
import snow.myticket.vo.OrdersVO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StadiumController {
    private final StadiumService stadiumService;
    private final ActivityService activityService;
    private final OrdersService ordersService;
    private final MemberService memberService;
    private final VOHelper voHelper;

    @Autowired
    public StadiumController(StadiumService stadiumService, ActivityService activityService, OrdersService ordersService, MemberService memberService,VOHelper voHelper) {
        this.stadiumService = stadiumService;
        this.activityService = activityService;
        this.ordersService = ordersService;
        this.memberService = memberService;
        this.voHelper = voHelper;
    }

    @RequestMapping("/stadiumCenter")
    public String getStadiumCenter(){
        return "stadiumCenter";
    }

    @RequestMapping("/stadiumInfo")
    public String getStadiumInfo(Model model, HttpServletRequest httpServletRequest){
        String stadiumCode = ((Stadium)httpServletRequest.getSession(false).getAttribute("stadium")).getCode();
        List<Orders> ordersList = ordersService.getStadiumOrders(stadiumCode);
        Double sum = 0.0;
        Integer pay = 0;
        for(Orders orders : ordersList){
            if(orders.getStatus() == 1 || orders.getStatus() == 2 || orders.getStatus() == 3) {
                sum += orders.getTotalPrice();
                pay++;
            }
        }
        model.addAttribute("orderTotalAmount",ordersList.size());
        model.addAttribute("orderPayAmount",pay);
        model.addAttribute("orderSum",sum);
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

    @RequestMapping("/stadiumOrders")
    public String getStadiumOrders(Model model, HttpServletRequest httpServletRequest){
        String stadiumCode = ((Stadium)httpServletRequest.getSession(false).getAttribute("stadium")).getCode();

        List<Orders> ordersList = ordersService.getStadiumOrders(stadiumCode);
        List<OrdersVO> ordersVOList = new ArrayList<>();

        for(Orders orders : ordersList)
            ordersVOList.add(voHelper.ordersConvert(orders));

        model.addAttribute("ordersList",ordersVOList);

        return "stadiumOrders";
    }

    @RequestMapping("/stadiumTicket")
    public String getStadiumTicket(Model model, HttpServletRequest httpServletRequest){
        String stadiumCode = ((Stadium)httpServletRequest.getSession(false).getAttribute("stadium")).getCode();
        List<Activity> activitiesNeedChecked = activityService.getAllActivitiesNeedChecked(stadiumCode);
        List<Activity> activitiesNeedDistributed = activityService.getAllActivitiesNeedDistributed(stadiumCode);

        List<ActivityVO> activityVOListChecked = new ArrayList<>();
        List<ActivityVO> activityVOListDistributed = new ArrayList<>();

        for(Activity activity : activitiesNeedChecked)
            activityVOListChecked.add(voHelper.activityConvert(activity));

        for(Activity activity : activitiesNeedDistributed)
            activityVOListDistributed.add(voHelper.activityConvert(activity));

        model.addAttribute("checkedList",activityVOListChecked);
        model.addAttribute("distributedList",activityVOListDistributed);

        return "stadiumTicket";
    }

    @RequestMapping("/stadiumBuy")
    public String getStadiumBuy(){
        return "stadiumBuy";
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

    @RequestMapping("/distributeTicketsForActivity")
    @ResponseBody
    public Map<String,String> distributeTicketsForActivity(@RequestParam Integer activityId){
        Map<String,String> result = new HashMap<>();
        try {
            result = stadiumService.distributeTicketsForActivity(activityId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/checkTicketsForActivity")
    @ResponseBody
    public Map<String,String> checkTicketsForActivity(@RequestParam Integer activityId){
        Map<String,String> result = new HashMap<>();
        try {
            stadiumService.checkTicketsForActivity(activityId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/calculateOffLineOrders")
    @ResponseBody
    public Map<String,String> calculateOffLineOrders(@RequestBody OffLineOrdersVO offLineOrdersVO){
        Map<String,String> result = new HashMap<>();
        try {
            Member member = memberService.getMember(offLineOrdersVO.getMemberEmail());
            if(member == null && !offLineOrdersVO.getMemberEmail().equals("")){
                result.put("result","fail");
                result.put("message","会员不存在");
                return result;
            }
            Orders orders = new Orders(member==null?-1:member.getId(),offLineOrdersVO.getActivityId(),offLineOrdersVO.getStadiumCode(),offLineOrdersVO.getTotalPrice(),offLineOrdersVO.getFirstAmount(),offLineOrdersVO.getSecondAmount(),offLineOrdersVO.getThirdAmount());
            Double sum = stadiumService.calculateOffLineOrders(orders,member);
            result.put("sum",sum.toString());
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/createOffLineOrders")
    @ResponseBody
    public Map<String,String> createOffLineOrders(@RequestBody OffLineOrdersVO offLineOrdersVO){
        Map<String,String> result = new HashMap<>();
        try {
            Member member = memberService.getMember(offLineOrdersVO.getMemberEmail());
            Orders orders = new Orders(member==null?-1:member.getId(),offLineOrdersVO.getActivityId(),offLineOrdersVO.getStadiumCode(),offLineOrdersVO.getTotalPrice(),offLineOrdersVO.getFirstAmount(),offLineOrdersVO.getSecondAmount(),offLineOrdersVO.getThirdAmount());
            stadiumService.createOffLineOrders(orders);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }



}
