package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import snow.myticket.bean.*;
import snow.myticket.service.*;
import snow.myticket.tool.VOHelper;
import snow.myticket.vo.CouponListVO;
import snow.myticket.vo.OrdersVO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {
    private final MemberService memberService;
    private final CouponService couponService;
    private final OrdersService ordersService;
    private final AccountService accountService;
    private final VOHelper voHelper;
    private final int COUPON_1ST_POINT = 200;
    private final int COUPON_2ND_POINT = 400;
    private final int COUPON_3RD_POINT = 800;

    @Autowired
    public MemberController(MemberService memberService, CouponService couponService, OrdersService ordersService, AccountService accountService, VOHelper voHelper) {
        this.memberService = memberService;
        this.couponService = couponService;
        this.ordersService = ordersService;
        this.accountService = accountService;
        this.voHelper = voHelper;
    }

    @RequestMapping("/pay")
    public String getPay(Model model, @RequestParam Integer ordersId){
        model.addAttribute("ordersVO", voHelper.ordersConvert(ordersService.getOrders(ordersId)));
        return "pay";
    }

    @RequestMapping("/memberCenter")
    public String getMemberCenter(){
        return "memberCenter";
    }

    @RequestMapping("/memberInfo")
    public String getMemberInfo(Model model, HttpServletRequest httpServletRequest){
        String email = ((Member)httpServletRequest.getSession(false).getAttribute("member")).getEmail();
        model.addAttribute("member",memberService.getMember(email));
        return "memberInfo";
    }

    @RequestMapping("/memberModify")
    public String getMemberModify(){
        return "memberModify";
    }

    @RequestMapping("/memberOrders")
    public String getMemberOrders(Model model, HttpServletRequest httpServletRequest){
        Integer memberId = ((Member)httpServletRequest.getSession(false).getAttribute("member")).getId();
        List<Orders> ordersList = ordersService.getMemberOrders(memberId);
        List<OrdersVO> ordersVOList = new ArrayList<>();

        for(Orders orders : ordersList){
            ordersVOList.add(voHelper.ordersConvert(orders));
        }

        model.addAttribute("ordersList",ordersVOList);
        return "memberOrders";
    }

    @RequestMapping("/memberCoupon")
    public String getMemberCoupon(Model model, HttpServletRequest httpServletRequest){
        String email = ((Member)httpServletRequest.getSession(false).getAttribute("member")).getEmail();
        Member member = memberService.getMember(email);
        model.addAttribute("member", member);
        List<Coupon> couponList = couponService.getCouponsByMemberId(member.getId());
        int coupon_1st = 0;
        int coupon_2nd = 0;
        int coupon_3rd = 0;

        for(Coupon coupon : couponList){
            if(coupon.getNeedPoints() == COUPON_1ST_POINT)
                coupon_1st++;
            else if(coupon.getNeedPoints() == COUPON_2ND_POINT)
                coupon_2nd++;
            else if(coupon.getNeedPoints() == COUPON_3RD_POINT)
                coupon_3rd++;
        }
        model.addAttribute("coupon_1st",coupon_1st);
        model.addAttribute("coupon_2nd",coupon_2nd);
        model.addAttribute("coupon_3rd",coupon_3rd);
        return "memberCoupon";
    }

    @RequestMapping("/cancelMember")
    @ResponseBody
    public Map<String,String> cancelMember(@RequestParam String email, HttpServletRequest httpServletRequest){
        //注销会话
        httpServletRequest.getSession(false).invalidate();
        Map<String,String> result = new HashMap<>();
        try {
            memberService.cancelMember(email);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/modifyMemberInfo")
    @ResponseBody
    public Map<String,String> modifyMemberInfo(@RequestBody Member member){
        Map<String,String> result = new HashMap<>();
        try {
            memberService.modifyMemberInfo(member);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/convertMemberCoupon")
    @ResponseBody
    public Map<String,String> convertMemberCoupon(@RequestBody CouponListVO couponListVO){
        Map<String,String> result = new HashMap<>();
        try {
            Coupon coupon_1st = new Coupon();
            coupon_1st.setMemberId(couponListVO.getMemberId());
            coupon_1st.setDiscount(couponListVO.getDiscount_1st());
            coupon_1st.setNeedPoints(couponListVO.getNeedPoints_1st());
            memberService.convertCoupons(coupon_1st, couponListVO.getAmount_1st());

            Coupon coupon_2nd = new Coupon();
            coupon_2nd.setMemberId(couponListVO.getMemberId());
            coupon_2nd.setDiscount(couponListVO.getDiscount_2nd());
            coupon_2nd.setNeedPoints(couponListVO.getNeedPoints_2nd());
            memberService.convertCoupons(coupon_2nd, couponListVO.getAmount_2nd());

            Coupon coupon_3rd = new Coupon();
            coupon_3rd.setMemberId(couponListVO.getMemberId());
            coupon_3rd.setDiscount(couponListVO.getDiscount_3rd());
            coupon_3rd.setNeedPoints(couponListVO.getNeedPoints_3rd());
            memberService.convertCoupons(coupon_3rd, couponListVO.getAmount_3rd());
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
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
            result.put("message","订座错误");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/payByMemberAccount")
    @ResponseBody
    public Map<String,String> payByMemberAccount(@RequestParam Integer ordersId){
        Map<String,String> result = new HashMap<>();
        try {
            memberService.payByMemberAccount(ordersId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/payByExternalAccount")
    @ResponseBody
    public Map<String,String> payByExternalAccount(@RequestParam Integer ordersId, @RequestParam String account){
        Map<String,String> result = new HashMap<>();
        try {
            memberService.payByExternalAccount(ordersId,account);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/cancelOrders")
    @ResponseBody
    public Map<String,String> cancelOrders(@RequestParam Integer ordersId){
        Map<String,String> result = new HashMap<>();
        try {
            result = memberService.cancelOrders(ordersId);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/loginAccount")
    @ResponseBody
    public Map<String,String> loginAccount(@RequestBody Account account){
        Map<String,String> result = new HashMap<>();
        try {
            Account account_db = accountService.getAccount(account.getAccount());
            if(account_db != null && account_db.getPassword().equals(account.getPassword()))
                result.put("result","success");
            else {
                result.put("result", "fail");
                result.put("message", "账号密码不正确");
            }
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        return result;
    }

    @RequestMapping("/checkMemberBalance")
    @ResponseBody
    public Boolean checkMemberBalance(@RequestParam Integer memberId,@RequestParam Double sum){
        return memberService.checkMemberBalance(memberId,sum);
    }

    @RequestMapping("/checkAccountBalance")
    @ResponseBody
    public Boolean checkAccountBalance(@RequestParam String account,@RequestParam Double sum){
        return accountService.checkAccountBalance(account,sum);
    }

    @RequestMapping("/checkOrdersValid")
    @ResponseBody
    public Boolean checkOrdersValid(@RequestParam Integer ordersId){
        return ordersService.checkOrdersValid(ordersId);
    }
}
