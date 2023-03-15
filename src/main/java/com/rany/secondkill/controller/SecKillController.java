package com.rany.secondkill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rany.secondkill.pojo.Order;
import com.rany.secondkill.pojo.SeckillOrder;
import com.rany.secondkill.pojo.User;
import com.rany.secondkill.service.IGoodsService;
import com.rany.secondkill.service.IOrderService;
import com.rany.secondkill.service.ISeckillOrderService;
import com.rany.secondkill.vo.GoodsVo;
import com.rany.secondkill.vo.RespBean;
import com.rany.secondkill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/secKill")
public class SecKillController {

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private ISeckillOrderService seckillOrderService;

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/doSecKill")
    @ResponseBody
    public RespBean doSecKill(Model model, User user, Long goodsId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.DOSECKILL_ERROR);
        }
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);

        if (goods.getStockCount() < 1) {
            return RespBean.error(RespBeanEnum.EMPTY_STOCK);
        }

        SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));

        if (seckillOrder != null) {
            return RespBean.error(RespBeanEnum.REPEATE_ERROR);
        }

        Order order = orderService.secKill(user, goods);
        return RespBean.success(order);
    }


}
