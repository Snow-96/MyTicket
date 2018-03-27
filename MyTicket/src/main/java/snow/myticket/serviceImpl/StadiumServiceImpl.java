package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Orders;
import snow.myticket.bean.Review;
import snow.myticket.bean.Stadium;
import snow.myticket.repository.ReviewRepository;
import snow.myticket.repository.StadiumRepository;
import snow.myticket.service.ActivityService;
import snow.myticket.service.OrdersService;
import snow.myticket.service.StadiumService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StadiumServiceImpl implements StadiumService {
    private final ReviewRepository reviewRepository;
    private final StadiumRepository stadiumRepository;
    private final ActivityService activityService;
    private final OrdersService ordersService;

    @Autowired
    public StadiumServiceImpl(ReviewRepository reviewRepository, StadiumRepository stadiumRepository, ActivityService activityService, OrdersService ordersService) {
        this.reviewRepository = reviewRepository;
        this.stadiumRepository = stadiumRepository;
        this.activityService = activityService;
        this.ordersService = ordersService;
    }

    @Override
    public Stadium getStadium(String stadiumCode) {
        return stadiumRepository.findByCode(stadiumCode);
    }

    @Override
    public void createStadiumApply(Stadium stadium) {
        stadiumRepository.save(stadium);
    }

    @Override
    public Integer checkModifyStatus(String stadiumCode) {
        return reviewRepository.getModifyStatus(stadiumCode);
    }

    @Override
    public void modifyStadiumInfo(Stadium stadium) {
        reviewRepository.save(new Review(stadium.getLocation(),stadium.getSeatAmount(),0));
    }

    @Override
    public void setStadiumApplicationValid(String stadiumCode, Integer stadiumId) {
        stadiumRepository.setStadiumApplicationValid(stadiumCode,stadiumId);
    }

    @Override
    public void setStadiumApplicationInvalid(Integer stadiumId) {
        stadiumRepository.setStadiumApplicationInvalid(stadiumId);
    }

    @Override
    public void setStadiumModifyInfoValid(Review review) {
        stadiumRepository.modifyStadiumInfo(review.getLocation(),review.getSeatAmount(),review.getStadiumCode());
        reviewRepository.changeModifyStatus(review.getId(),-1);
    }

    @Override
    public void setStadiumModifyInfoInvalid(Integer reviewId) {
        reviewRepository.changeModifyStatus(reviewId,-2);
    }

    @Override
    public void releaseActivity(Activity activity) {
        activityService.createActivity(activity);
    }

    @Override
    public Map<String,String> distributeTicketsForActivity(Integer activityId) {
        List<Orders> ordersList = ordersService.getActivityOrdersWaitingDistributed(activityId);
        Activity activity = activityService.getActivity(activityId);
        int firstClass = activity.getFirstClassSeats();
        int secondClass = activity.getSecondClassSeats();
        int thirdClass = activity.getThirdClassSeats();

        int successDistribution = 0;
        int failDistribution = 0;
        for(Orders orders : ordersList){
            //分配座位
            if(orders.getRandomAmount() <= firstClass + secondClass + thirdClass){
                int first = 0;
                int second = 0;
                int third = 0;

                while (first + second + third != orders.getRandomAmount()){
                    double random = Math.random();
                    if(random < 0.7 && thirdClass > 0) {
                        third++;
                        thirdClass--;
                        continue;
                    }

                    if(random < 0.9 && secondClass > 0) {
                        second++;
                        secondClass--;
                        continue;
                    }

                    if(random < 1 && firstClass > 0) {
                        first++;
                        firstClass--;
                    }
                }
                ordersService.setSeatsForOrders(orders.getId(),first,second,third);
                activityService.deductSeats(activityId,1,first);
                activityService.deductSeats(activityId,2,second);
                activityService.deductSeats(activityId,3,third);
                successDistribution++;
            }
            //退款
            else {
                ordersService.cancelOrders(orders);
                failDistribution++;
            }
        }

        Map<String, String> result = new HashMap<>();
        result.put("成功配票",String.valueOf(successDistribution));
        result.put("失败退款",String.valueOf(failDistribution));

        return result;
    }
}
