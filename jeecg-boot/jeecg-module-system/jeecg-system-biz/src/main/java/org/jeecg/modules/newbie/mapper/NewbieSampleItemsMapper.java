package org.jeecg.modules.newbie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.newbie.model.entity.NewbieSampleItem;
import org.jeecg.modules.newbie.model.vo.NewbieSampleItemVo;

import java.util.List;

public interface NewbieSampleItemsMapper extends BaseMapper<NewbieSampleItem> {

    /**
     * 根据领用清单请求ID查询所有样品项目，
     * 关联品牌表和类别表，返回包含品牌名称和类别名称的VO对象。
     *
     * @param reqId
     * @return
     */
    List<NewbieSampleItemVo> selectAllItemsByReqId(String reqId);
}
