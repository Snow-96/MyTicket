package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Manager;
import snow.myticket.bean.Orders;
import snow.myticket.bean.Review;
import snow.myticket.repository.ManagerRepository;
import snow.myticket.service.ManagerService;
import snow.myticket.service.OrdersService;
import snow.myticket.service.StadiumService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerRepository managerRepository;
    private final StadiumService stadiumService;
    private final OrdersService ordersService;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository ,StadiumService stadiumService, OrdersService ordersService) {
        this.managerRepository = managerRepository;
        this.stadiumService = stadiumService;
        this.ordersService = ordersService;
    }

    @Override
    public Manager getManager(String account) {
        return managerRepository.findByAccount(account);
    }

    @Override
    public void passStadiumApplication(Integer stadiumId) {
        stadiumService.setStadiumApplicationValid(getStringRandom(7),stadiumId);
    }

    @Override
    public void rejectStadiumApplication(Integer stadiumId) {
        stadiumService.setStadiumApplicationInvalid(stadiumId);
    }

    @Override
    public void passStadiumModifyInfo(Integer reviewId) {
        stadiumService.setStadiumModifyInfoValid(reviewId);
    }

    @Override
    public void rejectStadiumModifyInfo(Integer reviewId) {
        stadiumService.setStadiumModifyInfoInvalid(reviewId);
    }

    @Override
    public Map<String, String> ordersTransfer(Integer ordersId) {
        Map<String, String>  result = new HashMap<>();
        Orders orders = ordersService.getOrders(ordersId);
        Double sum = orders.getTotalPrice();

        Double incomeForStadium = sum * 0.9;
        Double incomeForPlatform = sum * 0.1;

        //结果保留2位小数
        long tmp = Math.round(incomeForStadium*100);
        incomeForStadium = tmp/100.0;
        tmp = Math.round(incomeForPlatform*100);
        incomeForPlatform = tmp/100.0;

        //结算给场馆
        stadiumService.receiveIncome(orders.getStadiumCode(),incomeForStadium);
        //结算给平台
        managerRepository.addIncome("123",incomeForPlatform);
        //设置订单已结算
        ordersService.setPlatformOrdersTransfer(ordersId);

        result.put("stadiumIncome", incomeForStadium.toString());
        result.put("platformIncome", incomeForPlatform.toString());

        return result;
    }

    //生成随机数字和字母,
    private String getStringRandom(int length) {

        StringBuilder val = new StringBuilder();
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }
}
