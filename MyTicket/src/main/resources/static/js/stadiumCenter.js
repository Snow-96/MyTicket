function modifyStadiumInfo(stadiumCode) {
    $.post("/checkModifyStatus",
        {
            stadiumCode: stadiumCode
        },
        function(data,status){
            if (data.result === "success") {
                $.ajax({
                    type: "post",
                    url: "/modifyStadiumInfo",
                    data: JSON.stringify({
                        "location": $("#newLocation").val(),
                        "seatAmount": $("#newSeatAmount").val(),
                        "code": stadiumCode
                    }),
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data) {
                        if (data.result === "success") {
                            UIkit.notification("场馆信息修改申请成功", {pos: 'bottom-center', status: 'warning'});
                            setTimeout(function () {
                                window.location.href = "stadiumModify.html";
                            }, 1200);
                        } else {
                            UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                        }
                    }

                });
            } else {
                UIkit.notification(data.message,{pos: 'bottom-center',status:'warning'});
            }
        });
}

function releaseActivity(stadiumCode,stadiumLocation) {
    var activityHoldDate = $("#activityHoldDate").val();
    var activitySellDate = $("#activitySellDate").val();
    var activityType = $("#activityType").text();
    var activityName = $("#activityName").val();
    var activityDescription = $("#activityDescription").val();
    var activityPrice_1st = $("#activityPrice_1st").val();
    var activityPrice_2nd = $("#activityPrice_2nd").val();
    var activityPrice_3rd = $("#activityPrice_3rd").val();
    var activityAmount_1st = $("#activityAmount_1st").val();
    var activityAmount_2nd = $("#activityAmount_2nd").val();
    var activityAmount_3rd = $("#activityAmount_3rd").val();

    if(activityHoldDate !== "" && activitySellDate !== "" && activityType !== "" && activityName !== "" && activityDescription !== "" && activityPrice_1st !== "" && activityPrice_2nd !== "" && activityPrice_3rd !== "" && activityAmount_1st !== "" && activityAmount_2nd !== "" && activityAmount_3rd !== "") {
        $.ajax({
            type: "post",
            url: "/releaseActivity",
            data: JSON.stringify({
                "holdDate": activityHoldDate,
                "sellDate": activitySellDate,
                "stadiumCode": stadiumCode,
                "type": activityType,
                "title": activityName,
                "location": stadiumLocation,
                "description": activityDescription,
                "firstClassPrice": activityPrice_1st,
                "secondClassPrice": activityPrice_2nd,
                "thirdClassPrice": activityPrice_3rd,
                "firstClassSeats": activityAmount_1st,
                "secondClassSeats": activityAmount_2nd,
                "thirdClassSeats": activityAmount_3rd,
                "totalFirstClassSeats": activityAmount_1st,
                "totalSecondClassSeats": activityAmount_2nd,
                "totalThirdClassSeats": activityAmount_3rd,
                "activityStatus": 0

            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.result === "success") {
                    UIkit.notification("活动发布成功", {pos: 'bottom-center', status: 'warning'});
                    setTimeout(function () {
                        window.location.href = "stadiumActivity.html";
                    }, 1200);
                } else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            }

        });
    }else {
        UIkit.notification("请完善全部信息", {pos: 'bottom-center', status: 'warning'});
    }
}

function refresh() {
    window.location.href= "stadiumTicket.html";
}

function distributeTicket(activityId) {
    $.get("/distributeTicketsForActivity",
        {
            activityId: activityId
        },
        function(data,status){
            if(status === "success") {
                $("#feedbackInfo").text("成功配票订单数：" + data.successDistribute + " 失败配票退款数：" + data.failDistribute);
            }else {
                UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
            }
        });
}

function checkTicket(activityId) {
    $.get("/checkTicketsForActivity",
        {
            activityId: activityId
        },
        function(data,status){
            if(status === "success") {
                $("#feedbackInfo").text("检票成功");
            }else {
                UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
            }
        });
}


$(document).ready(function(){
    $("#sing").click(function(){
        $("#activityType").text("演唱会");
    });

    $("#dance").click(function(){
        $("#activityType").text("舞蹈");
    });

    $("#speak").click(function(){
        $("#activityType").text("脱口秀");
    });

    $("#music").click(function(){
        $("#activityType").text("音乐会");
    });
});