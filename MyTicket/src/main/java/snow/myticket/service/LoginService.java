package snow.myticket.service;

import snow.myticket.bean.Member;

public interface LoginService {
    /**
     * 检测会员密码
     * @param member 会员实体
     * @return 是否正确
     */
    boolean checkMemberPassword(Member member);

    /**
     * 查看会员资格
     * @param email 会员邮箱
     * @return 是否有效
     */
    boolean checkMemberValid(String email);

    /**
     * 查看场馆编码是否有效
     * @param stadiumCode 场馆编码
     * @return 是否有效
     */
    boolean checkStadiumCodeValid(String stadiumCode);
}
