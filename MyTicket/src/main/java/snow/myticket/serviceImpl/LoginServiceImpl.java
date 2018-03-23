package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Member;
import snow.myticket.service.LoginService;
import snow.myticket.service.MemberService;
import snow.myticket.service.StadiumService;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private StadiumService stadiumService;

    @Override
    public boolean checkMemberPassword(Member member) {
        return memberService.getMember(member.getEmail()).getPassword().equals(member.getPassword());
    }

    @Override
    public boolean checkMemberValid(String email) {
        return memberService.getMember(email).getIsValid() == 1;
    }

    @Override
    public boolean checkStadiumCodeValid(String stadiumCode) {
        return stadiumService.getStadium(stadiumCode) != null && stadiumService.getStadium(stadiumCode).getStatus() == 1;
    }
}
