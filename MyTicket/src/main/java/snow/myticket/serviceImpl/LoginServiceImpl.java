package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Member;
import snow.myticket.bean.Stadium;
import snow.myticket.service.LoginService;
import snow.myticket.service.MemberService;
import snow.myticket.service.StadiumService;

@Service
public class LoginServiceImpl implements LoginService {
    private final MemberService memberService;
    private final StadiumService stadiumService;

    @Autowired
    public LoginServiceImpl(MemberService memberService, StadiumService stadiumService) {
        this.memberService = memberService;
        this.stadiumService = stadiumService;
    }

    @Override
    public boolean checkMemberPassword(Member member) {
        return memberService.getMember(member.getEmail()).getPassword().equals(member.getPassword());
    }

    @Override
    public boolean checkMemberValid(String email) {
        Member member = memberService.getMember(email);
        return member != null && member.getIsValid() == 1;

    }

    @Override
    public boolean checkStadiumCodeValid(String stadiumCode) {
        Stadium stadium = stadiumService.getStadium(stadiumCode);
        return  stadium != null && stadium.getStatus() == 1;
    }
}
