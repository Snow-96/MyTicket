var currentActivityId;
var currentCouponId = -1;
var currentStadiumCode;
var currentMemberId;
var sum;

var firstSeat;
var secondSeat;
var thirdSeat;

var currentSeat;
var currentLevel;
var currentRow;
var currentCol;

var chooseSeatList = [];

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

    //保证是二维数组
    if(chooseSeatList.length === 1){
        chooseSeatList.push([-1,-1,-1]);
    }

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
                "totalPrice":sum,
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.result === "success") {
                    let orderId = data.ordersId;
                    $.ajax({
                        traditional: true,
                        type : 'post',
                        data : {
                            seatArray: chooseSeatList,
                            ordersId: orderId,
                            activityId: currentActivityId
                        },
                        url : "/reserveOrdersSeat",
                        success : function(data) {
                            if (data.result === "success") {
                                window.location.href = "pay.html?ordersId=" + orderId;
                            } else {
                                UIkit.notification(data.message,{pos: 'bottom-center',status:'warning'});
                            }
                        }
                    });
                }else {
                    UIkit.notification(data.message, {pos: 'bottom-center', status: 'warning'});
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

function changeColumn(index) {
    currentRow = index+1;
    $("#seat_row").text(index+1 + '排');
    $("#column").empty();
    for(var i=0; i<currentSeat[index].length; i++){
        var columnNumber = currentSeat[index][i];
        if(columnNumber !== 0) {
            var column = $(`<li><a href="#" onclick="changeColumnText(${i})" </a>${i+1}号</li>`);
            $("#column").append(column);
        }
    }
}

function changeColumnText(index) {
    currentCol = index+1;
    $("#seat_col").text(index+1 + '号')
}

function updateSeatAmount() {
    var amount_1 = 0;
    var amount_2 = 0;
    var amount_3 = 0;

    for(var i=0; i<chooseSeatList.length; i++){
        if(chooseSeatList[i][0] === 1)
            amount_1++;
        else if(chooseSeatList[i][0] === 2)
            amount_2++;
        else if(chooseSeatList[i][0] === 3)
            amount_3++;
    }

    $("#1st_seat_buy_amount").val(amount_1);
    $("#2nd_seat_buy_amount").val(amount_2);
    $("#3rd_seat_buy_amount").val(amount_3);
}

$(document).ready(function(){
    var chooseSeatAmount = 0;
    $("#plusSeat").click(function (e) {
        if(chooseSeatAmount < 6) {
            e.preventDefault();
            var level = $("#seat_level").text();
            var row = $("#seat_row").text();
            var col = $("#seat_col").text();
            currentSeat[currentRow][currentCol] = 0;
            chooseSeatList.push([currentLevel,currentRow,currentCol]);

            $("#seat_level").text("等级");
            $("#seat_row").text("选座/排");
            $("#seat_col").text("选座/号");

            var seat = $(`<button style="width: 30%; margin-right:20px;margin-bottom: 20px;background-color: #1e87f0;color: white" class="uk-inline uk-button uk-button-default"><span class="uk-position-top-right uk-icon" uk-icon="icon: close; ratio: 0.7" id="${chooseSeatList.length - 1}"></span>${level}${row}${col}</button>`);
            seat.find('span').click(function (e) {
                var seat_delete = chooseSeatList[e.currentTarget.id];
                if(seat_delete[0] === 1){
                    firstSeat[seat_delete[1]][seat_delete[2]] = 1;
                }else if(seat_delete[0] === 2){
                    secondSeat[seat_delete[1]][seat_delete[2]] = 1;
                }else if(seat_delete[0] === 3){
                    thirdSeat[seat_delete[1]][seat_delete[2]] = 1;
                }
                chooseSeatList.splice(e.currentTarget.id,1,[-1,-1,-1]);
                seat.hide();
                chooseSeatAmount--;
            });
            $("#seatList").append(seat);
            chooseSeatAmount++;
        }else {
            UIkit.notification("一次最多购买6张票", {pos: 'bottom-center', status: 'warning'});
        }
    });


    $("#choose_seat_button").click(function () {
        $.get("/getActivitySeat",
            {
                activityId: currentActivityId
            },
            function(data,status){
                if(status === "success") {
                    firstSeat = data.firstSeat;
                    secondSeat = data.secondSeat;
                    thirdSeat = data.thirdSeat;
                }else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            });
    });

    $("#first_seat").click(function () {
        $("#seat_level").text("一等");
        currentSeat = firstSeat;
        currentLevel = 1;
    });

    $("#second_seat").click(function () {
        $("#seat_level").text("二等");
        currentSeat = secondSeat;
        currentLevel = 2;
    });

    $("#third_seat").click(function () {
        $("#seat_level").text("三等");
        currentSeat = thirdSeat;
        currentLevel = 3;
    });
});
