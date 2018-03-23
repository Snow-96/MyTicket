package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snow.myticket.bean.Member;
import snow.myticket.service.MemberService;

import java.util.HashMap;
import java.util.Map;

@Controller("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/cancelMember")
    @ResponseBody
    public Map<String,String> cancelMember(@RequestParam String email){
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
