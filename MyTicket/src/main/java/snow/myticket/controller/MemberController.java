package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import snow.myticket.bean.Member;
import snow.myticket.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller("/member")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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
}
