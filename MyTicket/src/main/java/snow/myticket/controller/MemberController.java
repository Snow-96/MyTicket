package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import snow.myticket.bean.Coupon;
import snow.myticket.bean.CouponList;
import snow.myticket.bean.Member;
import snow.myticket.service.CouponService;
import snow.myticket.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/member")
public class MemberController {
    private final MemberService memberService;
    private final CouponService couponService;
    private final int COUPON_1ST_POINT = 200;
    private final int COUPON_2ND_POINT = 400;
    private final int COUPON_3RD_POINT = 800;

    @Autowired
    public MemberController(MemberService memberService, CouponService couponService) {
        this.memberService = memberService;
        this.couponService = couponService;
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

    @RequestMapping("/memberCoupon")
    public String getMemberCoupon(Model model, HttpServletRequest httpServletRequest){
        String email = ((Member)httpServletRequest.getSession(false).getAttribute("member")).getEmail();
        Member member = memberService.getMember(email);
        model.addAttribute("member", member);
        List<Coupon> couponList = couponService.getCoupons(member.getId());
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
    public Map<String,String> convertMemberCoupon(@RequestBody CouponList couponList){
        Map<String,String> result = new HashMap<>();
        try {
            Coupon coupon_1st = new Coupon();
            coupon_1st.setMemberId(couponList.getMemberId());
            coupon_1st.setDiscount(couponList.getDiscount_1st());
            coupon_1st.setNeedPoints(couponList.getNeedPoints_1st());
            memberService.convertCoupons(coupon_1st,couponList.getAmount_1st());

            Coupon coupon_2nd = new Coupon();
            coupon_2nd.setMemberId(couponList.getMemberId());
            coupon_2nd.setDiscount(couponList.getDiscount_2nd());
            coupon_2nd.setNeedPoints(couponList.getNeedPoints_2nd());
            memberService.convertCoupons(coupon_2nd,couponList.getAmount_2nd());

            Coupon coupon_3rd = new Coupon();
            coupon_3rd.setMemberId(couponList.getMemberId());
            coupon_3rd.setDiscount(couponList.getDiscount_3rd());
            coupon_3rd.setNeedPoints(couponList.getNeedPoints_3rd());
            memberService.convertCoupons(coupon_3rd,couponList.getAmount_3rd());
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }


}
