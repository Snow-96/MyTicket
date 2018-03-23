package snow.myticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snow.myticket.bean.Member;
import snow.myticket.bean.Stadium;
import snow.myticket.service.RegisterService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @RequestMapping("/register")
    public String getRegister(){
        return "register";
    }

    @RequestMapping("/checkEmail")
    @ResponseBody
    public Boolean checkEmail(@RequestParam String email){
        return registerService.checkEmail(email);
    }

    @RequestMapping("/sendEmailCode")
    @ResponseBody
    public String sendEmailCode(@RequestParam String email){
        return registerService.sendEmailCode(email);
    }

    @RequestMapping("/memberRegister")
    @ResponseBody
    public Map<String,String> memberRegister(@RequestBody Member member){
        Map<String,String> result = new HashMap<>();
        try {
            registerService.memberRegister(member);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

    @RequestMapping("/stadiumRegister")
    @ResponseBody
    public Map<String,String> stadiumRegister(@RequestBody Stadium stadium){
        Map<String,String> result = new HashMap<>();
        try {
            registerService.stadiumRegisterApply(stadium);
        }catch (Exception e){
            result.put("result","fail");
            result.put("message","Server Error");
            return result;
        }
        result.put("result","success");
        return result;
    }

}
