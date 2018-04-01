function passApply(stadiumId) {
    $.get("/passStadiumApplication",
        {
            stadiumId: stadiumId
        },
        function(data,status){
            if(status === "success") {
                window.location.href = "managerApply.html"
            }else {
                UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
            }
        });
}

function rejectApply(stadiumId) {
    $.get("/rejectStadiumApplication",
        {
            stadiumId: stadiumId
        },
        function(data,status){
            if(status === "success") {
                window.location.href = "managerApply.html"
            }else {
                UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
            }
        });
}

function passReview(reviewId) {
    $.get("/passStadiumModifyInfo",
        {
            reviewId: reviewId
        },
        function(data,status){
            if(status === "success") {
                window.location.href = "managerReview.html"
            }else {
                UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
            }
        });
}

function rejectReview(reviewId) {
    $.get("/rejectStadiumModifyInfo",
        {
            reviewId: reviewId
        },
        function(data,status){
            if(status === "success") {
                window.location.href = "managerReview.html"
            }else {
                UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
            }
        });
}

function ordersTransfer(ordersId) {
    $.get("/ordersTransfer",
        {
            ordersId: ordersId
        },
        function(data,status){
            if(status === "success") {
                $("#transferInfo").text("场馆收入："+data.stadiumIncome + " 平台收入：" + data.platformIncome);
            }else {
                UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
            }
        });
}

function refresh() {
    window.location.href = "managerSettle.html"
}