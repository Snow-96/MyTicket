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