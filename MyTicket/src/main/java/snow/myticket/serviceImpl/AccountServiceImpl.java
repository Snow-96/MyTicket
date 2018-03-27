package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Account;
import snow.myticket.repository.AccountRepository;
import snow.myticket.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(String account) {
        return accountRepository.findByAccount(account);
    }

    @Override
    public boolean checkAccountBalance(String account, Double sum) {
        Account accountInstance = accountRepository.findByAccount(account);
        return accountInstance.getBalance() >= sum;
    }

    @Override
    public Double deductAccountBalance(String account, Double sum) {
        accountRepository.deductBalance(account,sum);
        return accountRepository.findByAccount(account).getBalance();
    }
}
