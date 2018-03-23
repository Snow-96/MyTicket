package snow.myticket.service;

import snow.myticket.bean.Member;
import snow.myticket.bean.Stadium;

public interface RegisterService {
    /**
     * 会员注册
     * @param member 会员实体
     */
    void memberRegister(Member member);

    /**
     * 场馆注册申请
     * @param stadium 场馆实体
     */
    void stadiumRegisterApply(Stadium stadium);

    /**
     * 检测邮箱是否已经注册
     * @param email 邮箱
     * @return true:可以使用/false:邮箱已被注册
     */
    boolean checkEmail(String email);

    /**
     * 向邮箱发送验证码
     * @return 随机生成的6位验证码
     */
    String sendEmailCode(String email);
}
