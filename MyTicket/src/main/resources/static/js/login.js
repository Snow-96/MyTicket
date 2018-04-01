function memberLogin() {
    $.ajax({
        type: "post",
        url: "/memberLogin",
        data: JSON.stringify({
            "email": $("#loginMemberEmail").val(),
            "password": $("#loginMemberPassword").val()
        }),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.result === "success") {
                showAlert("用户登陆成功");
                setTimeout(function () {
                    window.location.href = "activity.html";
                },2000);
            } else {
                showAlert(data.message);
            }
        }

    });
}

function stadiumLogin() {
    $.post("/stadiumLogin",
        {
            stadiumCode: $("#loginStadiumCode").val()
        },
        function(data,status){
            if (data.result === "success") {
                showAlert("场馆登陆成功");
                setTimeout(function () {
                    window.location.href = "homepage.html";
                },2000);
            } else {
                showAlert(data.message);
            }
        });
}

function managerLogin() {
    $.ajax({
        type: "post",
        url: "/managerLogin",
        data: JSON.stringify({
            "account": $("#loginManagerAccount").val(),
            "password": $("#loginManagerPassword").val()
        }),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.result === "success") {
                showAlert("经理登陆成功");
                setTimeout(function () {
                    window.location.href = "managerCenter.html";
                },2000);
            } else {
                showAlert(data.message);
            }
        }

    });
}