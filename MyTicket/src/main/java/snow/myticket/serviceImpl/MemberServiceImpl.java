package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Coupon;
import snow.myticket.bean.Member;
import snow.myticket.bean.Orders;
import snow.myticket.repository.MemberRepository;
import snow.myticket.service.MemberService;
import snow.myticket.service.OrdersService;

import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrdersService ordersService;

    @Override
    public Member getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Integer getMemberLevel(Integer memberId) {
        return memberRepository.findById(memberId).getLevel();
    }

    @Override
    public Integer getMemberPoints(Integer memberId) {
        return memberRepository.findById(memberId).getPoint();
    }

    @Override
    public void createMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void cancelMember(String email) {
        memberRepository.cancelMember(email);
    }

    @Override
    public void modifyMemberInfo(Member member) {
        memberRepository.modifyMemberInfo(member.getUsername(),member.getPassword(),member.getEmail());
    }

    @Override
    public boolean checkMemberBalance(Integer memberId, Double sum) {
        Member member = memberRepository.findById(memberId);
        return member.getBalance() >= sum;
    }

    @Override
    public Orders reserveOrders(Orders orders, Coupon coupon) {
        return ordersService.createOrders(orders,coupon);
    }

    @Override
    public Map<String,String> cancelOrders(Orders orders) {
        return ordersService.cancelOrders(orders);
    }

    @Override
    public Map<String,String> payByMemberAccount(Orders orders) {
        return ordersService.payOrders(orders);
    }

    @Override
    public Map<String,String> payByExternalAccount(Orders orders, String account) {
        return ordersService.payOrders(orders, account);
    }

    @Override
    public Integer upgrade(Integer memberId) {
        memberRepository.upgrade(memberId);
        return memberRepository.findById(memberId).getLevel();
    }

    @Override
    public Integer addPoints(Integer memberId, int point) {
        memberRepository.addPoints(memberId,point);
        return memberRepository.findById(memberId).getPoint();
    }

    @Override
    public Integer deductPoints(Integer memberId, int point) {
        memberRepository.deductPoints(memberId,point);
        return memberRepository.findById(memberId).getPoint();
    }

    @Override
    public Double deductBalance(Integer memberId, Double sum) {
        memberRepository.deductBalance(memberId,sum);
        return memberRepository.findById(memberId).getBalance();
    }

    @Override
    public Double returnBalance(Integer memberId, Double sum) {
        memberRepository.returnBalance(memberId,sum);
        return memberRepository.findById(memberId).getBalance();
    }


}
