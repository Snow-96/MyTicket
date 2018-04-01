package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import snow.myticket.bean.Orders;
import snow.myticket.service.ManagerService;
import snow.myticket.service.OrdersService;
import snow.myticket.service.StadiumService;
import snow.myticket.tool.VOHelper;
import snow.myticket.vo.OrdersVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ManagerController {
    private final StadiumService stadiumService;
    private final ManagerService managerService;
    private final OrdersService ordersService;
    private final VOHelper voHelper;

    @Autowired
    public ManagerController(StadiumService stadiumService, ManagerService managerService, OrdersService ordersService, VOHelper voHelper) {
        this.stadiumService = stadiumService;
        this.managerService = managerService;
        this.ordersService = ordersService;
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
