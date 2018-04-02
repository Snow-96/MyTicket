package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import snow.myticket.bean.Member;
import snow.myticket.bean.Orders;
import snow.myticket.bean.Stadium;
import snow.myticket.service.ManagerService;
import snow.myticket.service.MemberService;
import snow.myticket.service.OrdersService;
import snow.myticket.service.StadiumService;
import snow.myticket.tool.VOHelper;
import snow.myticket.vo.OrdersVO;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ManagerController {
    private final StadiumService stadiumService;
    private final ManagerService managerService;
    private final OrdersService ordersService;
    private final MemberService memberService;
    private final VOHelper voHelper;

    @Autowired
    public ManagerController(StadiumService stadiumService, ManagerService managerService, OrdersService ordersService, MemberService memberService ,VOHelper voHelper) {
        this.stadiumService = stadiumService;
        this.managerService = managerService;
        this.ordersService = ordersService;
        this.memberService = memberService;
        this.voHelper = voHelper;
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

    @RequestMapping("/managerSettle")
    public String getManagerSettle(Model model){
        List<Orders> ordersList = ordersService.getFinishedOrders();
        List<OrdersVO> ordersVOList = new ArrayList<>();

        for(Orders orders : ordersList)
            ordersVOList.add(voHelper.ordersConvert(orders));

        model.addAttribute("ordersList",ordersVOList);
        return "managerSettle";
    }

    @RequestMapping("/managerDiagram")
    public String getManagerDiagram(Model model){
        Map<String,Integer> stadiumOrdersAmount = new HashMap<>();
        Map<String,Integer> ordersStatusAmount = new HashMap<>();
        Map<String,Double> totalPlatformIncome = new LinkedHashMap<>();
        Map<String,Integer> memberLevelAmount = new LinkedHashMap<>();
        List<Stadium> stadiumList = stadiumService.getAllStadiums();
        for(Stadium stadium : stadiumList){
            stadiumOrdersAmount.put(stadium.getName(),ordersService.getStadiumOrders(stadium.getCode()).size());
        }
        ordersStatusAmount.put("取消",ordersService.getOrdersAmountByStatus(-1));
        ordersStatusAmount.put("预定",ordersService.getOrdersAmountByStatus(0));
        ordersStatusAmount.put("支付",ordersService.getOrdersAmountByStatus(1));
        ordersStatusAmount.put("结算",ordersService.getOrdersAmountByStatus(2));
        ordersStatusAmount.put("完成",ordersService.getOrdersAmountByStatus(3));

        List<Orders> ordersList = ordersService.getRecentOrders();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Orders orders : ordersList){
            String time = sdf.format(orders.getPayDate());
            if(!totalPlatformIncome.containsKey(time)){
                totalPlatformIncome.put(time,orders.getTotalPrice());
            }else {
                double sum = totalPlatformIncome.get(time) + orders.getTotalPrice();
                totalPlatformIncome.put(time,sum);
            }
        }

        List<Member> memberList = memberService.getAllMembers();
        int[] num = new int[11];
        for(int i=0;i<11;i++)
            num[i] = 0;
        for(Member member: memberList){
            num[member.getLevel()-1]++;
            num[10]++;
        }
        memberLevelAmount.put("会员总数",num[10]);
        for(int i=1;i<11;i++){
            memberLevelAmount.put(i + "级", num[i-1]);
        }

        model.addAttribute("pieStadiumList",stadiumOrdersAmount);
        model.addAttribute("pieOrdersList",ordersStatusAmount);
        model.addAttribute("lineIncomeList",totalPlatformIncome);
        model.addAttribute("barLevelList",memberLevelAmount);

        return "managerDiagram";
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

    @RequestMapping("/ordersTransfer")
    @ResponseBody
    public Map<String,String> ordersTransfer(@RequestParam Integer ordersId){
        Map<String,String> result = new HashMap<>();
        try {
            result = managerService.ordersTransfer(ordersId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }
}
