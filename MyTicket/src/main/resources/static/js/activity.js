var currentActivityId;
var currentCouponId = -1;
var currentStadiumCode;
var currentMemberId;
var sum;

function calculateTotalPrice() {
    sum = 0;
    var reserveButton = $("#reserve");
    reserveButton.attr("disabled",false);
    var _1st_seat_buy_amount = $("#1st_seat_buy_amount").val();
    var _2nd_seat_buy_amount = $("#2nd_seat_buy_amount").val();
    var _3rd_seat_buy_amount = $("#3rd_seat_buy_amount").val();
    var total_seat_amount = $("#total_seat_buy_amount").val();

    if(parseInt(_1st_seat_buy_amount) + parseInt(_2nd_seat_buy_amount) + parseInt(_3rd_seat_buy_amount) > 6){
        $("#sum").text('选座购买一次最多购买6张票');
        reserveButton.attr("disabled",true);
        return;
    }

    if(parseInt(total_seat_amount) > 20){
        $("#sum").text('不选座购买一次最多购买20张票');
        reserveButton.attr("disabled",true);
        return;
    }

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
                    sum = data.sum;
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
                    sum = data.sum;
                } else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            }

        });
    }
}

function reserveOrders() {
    var _1st_seat_buy_amount = $("#1st_seat_buy_amount").val();
    var _2nd_seat_buy_amount = $("#2nd_seat_buy_amount").val();
    var _3rd_seat_buy_amount = $("#3rd_seat_buy_amount").val();
    var total_seat_amount = $("#total_seat_buy_amount").val();

    if(total_seat_amount === ""){
        $.ajax({
            type: "post",
            url: "/reserveOrders",
            data: JSON.stringify({
                "memberId": currentMemberId,
                "activityId": currentActivityId,
                "couponId": currentCouponId,
                "stadiumCode": currentStadiumCode,
                "seatStatus": 1,
                "firstAmount": _1st_seat_buy_amount,
                "secondAmount":_2nd_seat_buy_amount,
                "thirdAmount":_3rd_seat_buy_amount,
                "totalPrice":sum
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.result === "success") {
                    window.location.href= "pay.html?ordersId=" + data.ordersId;
                } else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            }

        });
    }else {
        $.ajax({
            type: "post",
            url: "/reserveOrders",
            data: JSON.stringify({
                "memberId": currentMemberId,
                "activityId": currentActivityId,
                "couponId": currentCouponId,
                "stadiumCode": currentStadiumCode,
                "seatStatus": 0,
                "randomAmount": total_seat_amount,
                "totalPrice":sum
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.result === "success") {
                    window.location.href= "pay.html?ordersId=" + data.ordersId;
                } else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            }

        });
    }
}

function resetSeats() {
    $("#1st_seat_buy_amount").val("");
    $("#2nd_seat_buy_amount").val("");
    $("#3rd_seat_buy_amount").val("");
    $("#total_seat_buy_amount").val("");
    $("#couponType_choose").text("不使用优惠券");
    currentCouponId = -1;
}
