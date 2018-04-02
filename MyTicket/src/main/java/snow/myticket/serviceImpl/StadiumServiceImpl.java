package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.*;
import snow.myticket.repository.ReviewRepository;
import snow.myticket.repository.SeatRepository;
import snow.myticket.repository.StadiumRepository;
import snow.myticket.service.ActivityService;
import snow.myticket.service.OrdersService;
import snow.myticket.service.StadiumService;

import java.util.*;

@Service
public class StadiumServiceImpl implements StadiumService {
    private final ReviewRepository reviewRepository;
    private final StadiumRepository stadiumRepository;
    private final SeatRepository seatRepository;
    private final ActivityService activityService;
    private final OrdersService ordersService;

    private final Integer ROW = 20;

    @Autowired
    public StadiumServiceImpl(ReviewRepository reviewRepository, StadiumRepository stadiumRepository, SeatRepository seatRepository, ActivityService activityService, OrdersService ordersService) {
        this.reviewRepository = reviewRepository;
        this.stadiumRepository = stadiumRepository;
        this.seatRepository = seatRepository;
        this.activityService = activityService;
        this.ordersService = ordersService;
    }

    @Override
    public Stadium getStadium(String stadiumCode) {
        return stadiumRepository.findByCode(stadiumCode);
    }

    @Override
    public List<Stadium> getAllStadiums() {
        return stadiumRepository.findByStatus(1);
    }

    @Override
    public List<Stadium> getStadiumApply() {
        return stadiumRepository.findByStatus(0);
    }

    @Override
    public List<Review> getStadiumReview() {
        return reviewRepository.findByStatus(0);
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
        reviewRepository.save(new Review(stadium.getLocation(),stadium.getSeatAmount(),0,stadium.getCode()));
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
    public void setStadiumModifyInfoValid(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId);
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

        //记录已经被占的座位号
        List<Seat> seatList = activityService.getActivitySeat(activityId);
        Set<String> occupiedSeats = new HashSet<>();
        for(Seat seat : seatList)
            occupiedSeats.add(seat.getSeatLevel() + "-" + seat.getRow() + "-" + seat.getCol());

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

                        for(int i=0;i<activity.getTotalThirdClassSeats();i++){
                            int row = i/ROW + 1;
                            int col = i%ROW + 1;
                            if(!occupiedSeats.contains(3+"-"+row+"-"+col)){
                                occupiedSeats.add(3+"-"+row+"-"+col);
                                Seat seat = new Seat(orders.getId(),activityId,row,col,3);
                                seatRepository.save(seat);
                                break;
                            }
                        }

                        continue;
                    }

                    if(random < 0.9 && secondClass > 0) {
                        second++;
                        secondClass--;

                        for(int i=0;i<activity.getTotalSecondClassSeats();i++){
                            int row = i/ROW + 1;
                            int col = i%ROW + 1;
                            if(!occupiedSeats.contains(2+"-"+row+"-"+col)){
                                occupiedSeats.add(2+"-"+row+"-"+col);
                                Seat seat = new Seat(orders.getId(),activityId,row,col,2);
                                seatRepository.save(seat);
                                break;
                            }
                        }

                        continue;
                    }

                    if(random < 1 && firstClass > 0) {
                        first++;
                        firstClass--;

                        for(int i=0;i<activity.getTotalFirstClassSeats();i++){
                            int row = i/ROW + 1;
                            int col = i%ROW + 1;
                            if(!occupiedSeats.contains(1+"-"+row+"-"+col)){
                                occupiedSeats.add(1+"-"+row+"-"+col);
                                Seat seat = new Seat(orders.getId(),activityId,row,col,1);
                                seatRepository.save(seat);
                                break;
                            }
                        }

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
        result.put("successDistribute",String.valueOf(successDistribution));
        result.put("failDistribute",String.valueOf(failDistribution));
        //设置活动已配票
        activityService.setActivityDistributed(activityId);

        return result;
    }

    @Override
    public void checkTicketsForActivity(Integer activityId) {
        activityService.setActivityChecked(activityId);
        ordersService.setActivityOrdersChecked(activityId);
    }

    @Override
    public void receiveIncome(String stadiumCode, Double income) {
        stadiumRepository.addIncome(stadiumCode,income);
    }
}
