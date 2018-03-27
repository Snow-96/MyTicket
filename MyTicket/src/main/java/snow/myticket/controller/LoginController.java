package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snow.myticket.bean.Member;
import snow.myticket.service.LoginService;
import snow.myticket.service.MemberService;
import snow.myticket.service.StadiumService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final LoginService loginService;
    private final MemberService memberService;
    private final StadiumService stadiumService;

    @Autowired
    public LoginController(LoginService loginService, MemberService memberService, StadiumService stadiumService) {
        this.loginService = loginService;
        this.memberService = memberService;
        this.stadiumService = stadiumService;
    }

    @RequestMapping("/login")
    public String getLogin(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().invalidate();
        return "homepage";
    }

    @RequestMapping("/checkLoginStatus")
    @ResponseBody
    public boolean checkLoginStatus(HttpServletRequest httpServletRequest){
        return httpServletRequest.getSession(false) != null;
    }

    @RequestMapping("/checkLoginType")
    @ResponseBody
    public String checkLoginType(HttpServletRequest httpServletRequest){
        if(httpServletRequest.getSession().getAttribute("member") != null)
            return "member";
        if(httpServletRequest.getSession().getAttribute("stadium") != null)
            return "stadium";
        return "manager";
    }

    @RequestMapping("/memberLogin")
    @ResponseBody
    public Map<String,String> memberLogin(HttpServletRequest httpServletRequest, @RequestBody Member member){
        Map<String,String> result = new HashMap<>();
        if(loginService.checkMemberValid(member.getEmail()) && loginService.checkMemberPassword(member)){
            httpServletRequest.getSession(true).setAttribute("member",memberService.getMember(member.getEmail()));
            result.put("result","success");
        }else{
            result.put("result","fail");
            if(!loginService.checkMemberValid(member.getEmail()))
                result.put("message","会员已失效!");
            if(!loginService.checkMemberPassword(member))
                result.put("message","密码不正确!");
        }
        return result;
    }

    @RequestMapping("/stadiumLogin")
    @ResponseBody
    public Map<String,String> stadiumLogin(HttpServletRequest httpServletRequest, @RequestParam String stadiumCode){
        Map<String,String> result = new HashMap<>();
        if(loginService.checkStadiumCodeValid(stadiumCode)){
            httpServletRequest.getSession(true).setAttribute("stadium",stadiumService.getStadium(stadiumCode));
            result.put("result","success");
        }else{
            result.put("result","fail");
            result.put("message","场馆编码不存在或申请审批未通过");
        }
        return result;
    }

}
