var currentActivityId;
var currentCouponId = -1;
var currentStadiumCode;
var currentMemberId;

function calculateTotalPrice() {
    var _1st_seat_buy_amount = $("#1st_seat_buy_amount").val();
    var _2nd_seat_buy_amount = $("#2nd_seat_buy_amount").val();
    var _3rd_seat_buy_amount = $("#3rd_seat_buy_amount").val();
    var total_seat_amount = $("#total_seat_buy_amount").val();

    if(total_seat_amount === ""){
        $.ajax({
            type: "post",
            url: "/calculatePrice",
            data: JSON.stringify({
                "memberId": currentMemberId,
                "activityId": currentActivityId,
                "couponId": currentCouponId,
                "stadiumCode": currentStadiumCode,
                "seatStatus": 1,
                "firstAmount": _1st_seat_buy_amount,
                "secondAmount":_2nd_seat_buy_amount,
                "thirdAmount":_3rd_seat_buy_amount
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.result === "success") {
                    $("#sum").text('总价为：' + data.sum);
                } else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            }

        });
    }else {
        $.ajax({
            type: "post",
            url: "/calculatePrice",
            data: JSON.stringify({
                "memberId": currentMemberId,
                "activityId": currentActivityId,
                "couponId": currentCouponId,
                "stadiumCode": currentStadiumCode,
                "seatStatus": 0,
                "randomAmount": total_seat_amount
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.result === "success") {
                    $("#sum").text('总价为：' + data.sum);
                } else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            }

        });
    }

}
