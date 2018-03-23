package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Member;
import snow.myticket.bean.Stadium;
import snow.myticket.service.MemberService;
import snow.myticket.service.RegisterService;
import snow.myticket.service.StadiumService;
import snow.myticket.tool.EmailHelper;

@Service
public class RegisterServiceImpl implements RegisterService{
    @Autowired
    private MemberService memberService;
    @Autowired
    private StadiumService stadiumService;
    @Autowired
    private EmailHelper emailHelper;

    @Override
    public void memberRegister(Member member) {
        memberService.createMember(member);
    }

    @Override
    public void stadiumRegisterApply(Stadium stadium) {
        stadiumService.createStadiumApply(stadium);
    }


    @Override
    public boolean checkEmail(String email) {
        return memberService.getMember(email) == null;
    }

    @Override
    public String sendEmailCode(String email) {
        return emailHelper.sendSimpleEmail(email);
    }
}
