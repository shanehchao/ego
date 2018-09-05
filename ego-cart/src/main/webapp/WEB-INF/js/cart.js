var TTCart = {
	load : function(){ // 加载购物车数据
		
	},
	itemNumChange : function(){
		$(".increment").click(function(){//＋
			var _thisInput = $(this).siblings("input");
			_thisInput.val(eval(_thisInput.val()) + 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				TTCart.refreshTotalPrice();
			});
		});
		$(".decrement").click(function(){//-
			var _thisInput = $(this).siblings("input");
			if(eval(_thisInput.val()) == 1){
				return ;
			}
			_thisInput.val(eval(_thisInput.val()) - 1);
			$.post("/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val() + ".action",function(data){
				TTCart.refreshTotalPrice();
			});
		});
		$(".quantity-form .quantity-text").rnumber(1);//限制只能输入数字
		$(".quantity-form .quantity-text").change(function(){
			var _thisInput = $(this);
			$.post("/service/cart/update/num/"+_thisInput.attr("itemId")+"/"+_thisInput.val(),function(data){
				TTCart.refreshTotalPrice();
			});
		});
	},
	refreshTotalPrice : function(){ //重新计算总价
		var total = 0;
		$(".quantity-form .quantity-text").each(function(i,e){
			var _this = $(e);
			total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
		});
		$(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
			 prefix: '￥',
			 thousandsSeparator: ',',
			 centsLimit: 2
		});
	}
};

$(function(){
	TTCart.load();
	TTCart.itemNumChange();

	// 给“删除”按钮绑定事件
    $('.mycart_remove').click(function () {
        var $a = $(this);
        var href = $a.attr('href');
        $.post(href, function (data) {
            if (data.status == 200) {
                $a.parent().parent().parent().remove();
                TTCart.refreshTotalPrice();
            }
        });
        return false;
    });

    // 给复选框绑定点击事件
    $('.checkbox').click(function () {
        var total = 0;
        $(".quantity-form .quantity-text").each(function(i,e){
            var _this = $(e);
            var isChecked = _this.parent().parent().siblings('.p-checkbox').children().eq(0).is(':checked');
            if (isChecked) {
                total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
            }
        });
        $(".totalSkuPrice").html(new Number(total/100).toFixed(2)).priceFormat({ //价格格式化插件
            prefix: '￥',
            thousandsSeparator: ',',
            centsLimit: 2
        });
    });

	// 给“去结算”绑定点击事件
	$('#toSettlement').click(function () {
		var href = $(this).attr('href');
		var param = '';
        $.each($('.checkbox:checked'), function(i, e) {
            var _this = $(e);
            param += 'id='+_this.val();
            if (i < $('.checkbox:checked').length - 1) {
                param += '&';
            }
        });
        console.info(param)
        location.href = href + '?' + param;
        return false;
    });
});