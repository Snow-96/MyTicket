package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Review;
import snow.myticket.service.ManagerService;
import snow.myticket.service.StadiumService;

import java.util.Random;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final StadiumService stadiumService;

    @Autowired
    public ManagerServiceImpl(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
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
    public void passStadiumModifyInfo(Review review) {
        stadiumService.setStadiumModifyInfoValid(review);
    }

    @Override
    public void rejectStadiumModifyInfo(Integer reviewId) {
        stadiumService.setStadiumModifyInfoInvalid(reviewId);
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
