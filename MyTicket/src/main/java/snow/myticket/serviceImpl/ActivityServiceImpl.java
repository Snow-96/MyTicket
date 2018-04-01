package snow.myticket.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snow.myticket.bean.Activity;
import snow.myticket.bean.Seat;
import snow.myticket.repository.ActivityRepository;
import snow.myticket.repository.SeatRepository;
import snow.myticket.service.ActivityService;

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
    public List<Activity> getActivitiesByStadiumCode(String stadiumCode) {
        return activityRepository.findByStadiumCode(stadiumCode);
    }

    @Override
    public List<Activity> getActivitiesByDate(Date start, Date end) {
        return activityRepository.findByHoldDateBetween(start,end);
    }

    @Override
    public List<Activity> getActivitiesByType(String type) {
        return activityRepository.findByType(type);
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
