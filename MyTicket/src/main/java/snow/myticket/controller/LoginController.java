package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snow.myticket.bean.Manager;
import snow.myticket.bean.Member;
import snow.myticket.bean.Stadium;
import snow.myticket.service.ManagerService;
import snow.myticket.service.MemberService;
import snow.myticket.service.StadiumService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final MemberService memberService;
    private final StadiumService stadiumService;
    private final ManagerService managerService;

    @Autowired
    public LoginController(MemberService memberService, StadiumService stadiumService, ManagerService managerService) {
        this.memberService = memberService;
        this.stadiumService = stadiumService;
        this.managerService = managerService;
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
        if(httpServletRequest.getSession(false).getAttribute("member") != null)
            return "member";
        if(httpServletRequest.getSession(false).getAttribute("stadium") != null)
            return "stadium";
        return "manager";
    }

    @RequestMapping("/memberLogin")
    @ResponseBody
    public Map<String,String> memberLogin(HttpServletRequest httpServletRequest, @RequestBody Member member){
        Map<String,String> result = new HashMap<>();
        Member member_db = memberService.getMember(member.getEmail());
        if(member_db != null && member_db.getIsValid() == 1 && member_db.getPassword().equals(member.getPassword())){
            httpServletRequest.getSession(true).setAttribute("member",member_db);
            result.put("result","success");
        }else{
            result.put("result","fail");
            if(member_db == null)
                result.put("message","会员不存在");
            else if(member_db.getIsValid() != 1)
                result.put("message","会员已注销!");
            else if(!member_db.getPassword().equals(member.getPassword()))
                result.put("message","密码不正确");
        }
        return result;
    }

    @RequestMapping("/stadiumLogin")
    @ResponseBody
    public Map<String,String> stadiumLogin(HttpServletRequest httpServletRequest, @RequestParam String stadiumCode){
        Map<String,String> result = new HashMap<>();
        Stadium stadium_db = stadiumService.getStadium(stadiumCode);
        if(stadium_db != null && stadium_db.getStatus() == 1){
            httpServletRequest.getSession(true).setAttribute("stadium",stadium_db);
            result.put("result","success");
        }else{
            result.put("result","fail");
            result.put("message","场馆编码不存在或申请审批未通过");
        }
        return result;
    }

    @RequestMapping("/managerLogin")
    @ResponseBody
    public Map<String,String> managerLogin(HttpServletRequest httpServletRequest, @RequestBody Manager manager){
        Map<String,String> result = new HashMap<>();
        Manager manager_db = managerService.getManager(manager.getAccount());
        if(manager_db != null && manager_db.getPassword().equals(manager.getPassword())){
            httpServletRequest.getSession(true);
            result.put("result","success");
        }else{
            result.put("result","fail");
            result.put("message","管理员账号密码错误");
        }
        return result;
    }

}
