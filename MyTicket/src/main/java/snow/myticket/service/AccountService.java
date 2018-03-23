package snow.myticket.service;

import snow.myticket.bean.Account;

public interface AccountService {
    /**
     * 根据账号获取支付账户实体
     * @param account 账号
     * @return 支付账户实体
     */
    Account getAccount(String account);

    /**
     * 查看支付账户余额是否足够支付
     * @param account 账户
     * @param sum 待支付总金额
     * @return 是否足够
     */
    boolean checkAccountBalance(String account, Double sum);

    /**
     * 在账户余额中扣除相应费用
     * @param account 账户
     * @param sum 待支付总金额
     * @return 扣除后余额
     */
    Double deductAccountBalance(String account, Double sum);
}
