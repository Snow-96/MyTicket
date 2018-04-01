function checkMemberBalance(memberId,sum,ordersId) {
    $.get("/checkMemberBalance",
        {
            memberId: memberId,
            sum: sum
        },
        function(data){
            if(data){
                $.get("/payByMemberAccount",
                    {
                        ordersId: ordersId,
                    },
                    function(data){
                        if(data.result === "success"){
                            UIkit.notification("支付成功", {pos: 'bottom-center', status: 'warning'});
                            setTimeout(function () {
                                window.location.href = "memberOrders.html";
                            }, 1200);
                        }else {
                            UIkit.notification("Sever Error", {pos: 'bottom-center', status: 'warning'});
                        }
                    })
            }else {
                UIkit.notification("余额不足", {pos: 'bottom-center', status: 'warning'});
                setTimeout(function () {
                    window.location.href = "memberInfo.html";
                }, 1200);
            }
        });
}

function checkAccountBalance(account,sum,ordersId) {
    $.get("/checkAccountBalance",
        {
            account: account,
            sum: sum
        },
        function(data){
            if(data){
                $.get("/payByExternalAccount",
                    {
                        ordersId: ordersId,
                        account: account
                    },
                    function(data){
                        if(data.result === "success"){
                            UIkit.notification("支付成功", {pos: 'bottom-center', status: 'warning'});
                            setTimeout(function () {
                                window.location.href = "memberOrders.html";
                            }, 1200);
                        }else {
                            UIkit.notification("Sever Error", {pos: 'bottom-center', status: 'warning'});
                        }
                    })
            }else {
                UIkit.notification("该账户余额不足", {pos: 'bottom-center', status: 'warning'});
            }
        });
}

function loginAccount(sum,ordersId) {
    $.ajax({
        type: "post",
        url: "/loginAccount",
        data: JSON.stringify({
            "account": $("#account_account").val(),
            "password": $("#account_password").val(),
        }),
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            if (data.result === "success") {
                checkAccountBalance($("#account_account").val(),sum,ordersId)
            } else {
                UIkit.notification(data.message, {pos: 'bottom-center', status: 'warning'});
            }
        }

    });
}