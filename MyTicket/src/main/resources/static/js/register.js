var code;

function checkEmail() {
    $.get("/checkEmail",
        {
            email: $("#registerEmail").val()
        },
        function(data,status){
            if(status === "success") {
                if (!data) {
                    showAlert("邮箱已被注册");
                }else {
                    sendEmailCode();
                }
            }else {
                showAlert("Server Error");
            }
        });
}

function sendEmailCode() {
    $.get("/sendEmailCode",
        {
            email: $("#registerEmail").val()
        },
        function (data, status) {
            if (status === "success") {
                code = data;
            } else {
                showAlert("Server Error");
            }
        });
}

function memberRegister() {
    if($("#codeInput").val() === code) {
        $.ajax({
            type: "post",
            url: "/memberRegister",
            data: JSON.stringify({
                "username": $("#registerUsername").val(),
                "email": $("#registerEmail").val(),
                "password": $("#registerPassword").val(),
                "isValid": 1,
                "level": 1,
                "point": 0,
                "balance": 0
            }),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                if (data.result === "success") {
                    showAlert("会员注册成功");
                    setTimeout(function () {
                        window.location.href = "homepage.html";
                    },2000);

                } else {
                    showAlert("Server Error");
                }
            }

        });
    }else {
        showAlert("验证码不正确");
    }
}

function stadiumRegister() {
    $.ajax({
        type: "post",
        url: "/stadiumRegister",
        data: JSON.stringify({
            "name": $("#registerStadiumName").val(),
            "location": $("#registerStadiumLocation").val(),
            "seatAmount": $("#registerStadiumSeatAmount").val(),
            "income": 0,
            "status": 0
        }),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.result === "success") {
                showAlert("场馆注册申请成功");
                setTimeout(function () {
                    window.location.href = "homepage.html";
                },2000);

            } else {
                showAlert("Server Error");
            }
        }

    });
}