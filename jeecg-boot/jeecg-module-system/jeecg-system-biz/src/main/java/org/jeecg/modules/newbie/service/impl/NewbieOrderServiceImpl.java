package org.jeecg.modules.newbie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.newbie.mapper.NewbieOrderMapper;
import org.jeecg.modules.newbie.mapper.NewbieOrderNumberMapper;
import org.jeecg.modules.newbie.mapper.NewbieSampleItemsMapper;
import org.jeecg.modules.newbie.model.dto.NewbieOrderListSearchDto;
import org.jeecg.modules.newbie.model.entity.NewbieOrderList;
import org.jeecg.modules.newbie.model.entity.NewbieOrderNumber;
import org.jeecg.modules.newbie.model.entity.NewbieRequestSample;
import org.jeecg.modules.newbie.model.entity.NewbieSampleItem;
import org.jeecg.modules.newbie.model.vo.NewbieOrderListVo;
import org.jeecg.modules.newbie.service.INewbieOrderService;
import org.jeecg.modules.newbie.service.INewbieProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewbieOrderServiceImpl extends ServiceImpl<NewbieOrderMapper, NewbieOrderList>
        implements INewbieOrderService {

    @Autowired
    private INewbieProductService newbieProductService;

    @Autowired
    private NewbieSampleItemsMapper newbieSampleItemsMapper;

    @Autowired
    private NewbieOrderNumberMapper newbieOrderNumberMapper;

    @Override
    public IPage<NewbieOrderListVo> getAllOrders(
            IPage<NewbieOrderListVo> page,
            NewbieOrderListSearchDto searchDto
    ) {
        IPage<NewbieOrderListVo> allOrders =
                getBaseMapper().getAllOrders(page, searchDto);
        return allOrders;
    }

    /**
     * 在样品领用请求审批通过后，立即生成相应订单。
     * 其中订单号的生成规则是: yyyymmdd加4位序列号，如202307070001。
     *
     * @param requestSample
     */
    @Transactional
    @Override
    public void createOrders(NewbieRequestSample requestSample) {
        LambdaQueryWrapper<NewbieSampleItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NewbieSampleItem::getReqId, requestSample.getId());
        final List<NewbieSampleItem> sampleItemList =
                newbieSampleItemsMapper.selectList(queryWrapper);

        // 获取最近使用的订单号序列号，并加锁以防并发冲突
        final String orderDateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Integer seq = newbieOrderNumberMapper.selectLatestOrderNumberForUpdate(orderDateStr);
        if (seq == null) {
            seq = 0;
        } else if (seq > 9999) {
            throw new RuntimeException("Order number sequence exceeded limit for date: " + orderDateStr);
        }

        /*
            生成订单数据，张数对应生成订单的条数，
            即申请时填写10张，则会生成相同箱数的10条订单。
         */
        final List<NewbieOrderList> orderList = new ArrayList<>();
        for (NewbieSampleItem item : sampleItemList) {
            final int qtyOrder = item.getQtyOrder() != null ? item.getQtyOrder() : 0;
            for (int i = 0; i < qtyOrder; i++) {
                final String newSeqStr = String.format("%04d", ++seq);
                NewbieOrderList order = new NewbieOrderList() {{
                    setOrderNo(orderDateStr + newSeqStr);
                    setReqId(requestSample.getId());
                    setItemId(item.getId());
                }};

                orderList.add(order);
            }
        }

        // 更新订单号表
        NewbieOrderNumber orderNumber = new NewbieOrderNumber();
        orderNumber.setOrderDate(orderDateStr);
        orderNumber.setSeq(seq);
        newbieOrderNumberMapper.insertOrUpdate(orderNumber);

        // 批量插入订单
        getBaseMapper().insert(orderList);
    }
}
