function cancelMember(email) {
    $.post("/cancelMember",
        {
            email: email
        },
        function(data,status){
            if (data.result === "success") {
                UIkit.notification("会员注销成功",{pos: 'bottom-center',status:'warning'});
                setTimeout(function () {
                    window.location.href = "homepage.html";
                },2000);
            } else {
                UIkit.notification("Server Error",{pos: 'bottom-center',status:'warning'});
            }
        });
}

function checkPassword() {
    return $("#1stPWD").val() === $("#2ndPWD").val()
}

function modifyMemberInfo(email) {
    if(checkPassword()) {
        $.ajax({
            type: "post",
            url: "/modifyMemberInfo",
            data: JSON.stringify({
                "username": $("#newUsername").val(),
                "password": $("#1stPWD").val(),
                "email": email
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.result === "success") {
                    UIkit.notification("会员信息修改成功", {pos: 'bottom-center', status: 'warning'});
                    setTimeout(function () {
                        window.location.href = "memberModify.html";
                    },1200);
                } else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            }

        });
    }else {
        UIkit.notification("两次密码输入不一致！", {pos: 'bottom-center', status: 'warning'});
    }
}

function addCoupons(id) {
    var label = $("#" + id);
    label.text(parseInt(label.text()) + 1);
}

function subCoupons(id) {
    var label = $("#" + id);
    if(parseInt(label.text()) > 0)
        label.text(parseInt(label.text()) - 1);
}

function resetCoupons() {
    $("#1st").text(0);
    $("#2nd").text(0);
    $("#3rd").text(0);
}

function convertCoupons(memberId, memberPoints) {
    var amount1 = parseInt($("#1st").text());
    var amount2 = parseInt($("#2nd").text());
    var amount3 = parseInt($("#3rd").text());

    var totalPoints = amount1 * 200 + amount2 * 400 + amount3 * 800;

    if( memberPoints < totalPoints){
        UIkit.notification("积分不足",{pos: 'bottom-center',status:'warning'});
    }else{
        $.ajax({
            type: "post",
            url: "/convertMemberCoupon",
            data: JSON.stringify({
                "memberId": memberId,
                "needPoints_1st": 200,
                "needPoints_2nd": 400,
                "needPoints_3rd": 800,
                "discount_1st": 0.95,
                "discount_2nd": 0.90,
                "discount_3rd": 0.85,
                "amount_1st": amount1,
                "amount_2nd": amount2,
                "amount_3rd": amount3
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data,status) {
                if (data.result === "success") {
                    UIkit.notification("兑换成功", {pos: 'bottom-center', status: 'warning'});
                    setTimeout(function () {
                        window.location.href = "memberCoupon.html";
                    },1200);
                } else {
                    UIkit.notification("Server Error", {pos: 'bottom-center', status: 'warning'});
                }
            }
        });
    }
}