package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Seat;
import snow.myticket.repository.ActivityRepository;
import snow.myticket.repository.SeatRepository;
import snow.myticket.service.ActivityService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository activityRepository, SeatRepository seatRepository) {
        this.activityRepository = activityRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void createActivity(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public Activity getActivity(Integer activityId) {
        return activityRepository.findById(activityId);
    }

    @Override
    public List<Seat> getActivitySeat(Integer activityId) {
        return seatRepository.findByActivityId(activityId);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public List<Activity> getAllActivitiesNeedChecked(String stadiumCode) {
        //设置时间
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
        Date start = c.getTime();
        c.setTime(now);
        c.add(Calendar.DAY_OF_MONTH, -1);// 今天-1天
        Date end = c.getTime();
        return activityRepository.findByHoldDateBetweenAndStadiumCodeAndActivityStatus(start,end,stadiumCode,1);
    }

    @Override
    public List<Activity> getAllActivitiesNeedDistributed(String stadiumCode) {
        //设置时间
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DAY_OF_MONTH, 7);// 今天+7天
        Date date = c.getTime();
        return activityRepository.findByHoldDateAfterAndStadiumCodeAndActivityStatus(date,stadiumCode,0);
    }

    @Override
    public List<Activity> getActivitiesByStadiumCode(String stadiumCode) {
        return activityRepository.findByStadiumCode(stadiumCode);
    }

    @Override
    public void setActivityChecked(Integer activityId) {
        activityRepository.setActivityStatus(activityId,2);
    }

    @Override
    public void setActivityDistributed(Integer activityId) {
        activityRepository.setActivityStatus(activityId,1);
    }

    @Override
    public void addSeats(Integer activityId, Integer seatClass, Integer amount) {
        if(amount == 0)
            return;

        switch (seatClass){
            case 1:
                activityRepository.addFirstClassSeats(activityId,amount);
                break;
            case 2:
                activityRepository.addSecondClassSeats(activityId,amount);
                break;
            case 3:
                activityRepository.addThirdClassSeats(activityId,amount);
                break;
        }
    }

    @Override
    public void deductSeats(Integer activityId, Integer seatClass, Integer amount) {
        if(amount == 0)
            return;

        switch (seatClass){
            case 1:
                activityRepository.deductFirstClassSeats(activityId,amount);
                break;
            case 2:
                activityRepository.deductSecondClassSeats(activityId,amount);
                break;
            case 3:
                activityRepository.deductThirdClassSeats(activityId,amount);
                break;
        }
    }
}
