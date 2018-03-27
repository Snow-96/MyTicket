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