package org.jeecg.modules.newbie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.executor.BatchResult;
import org.jeecg.modules.newbie.mapper.*;
import org.jeecg.modules.newbie.model.dto.NewbieRequestSampleFormDto;
import org.jeecg.modules.newbie.model.dto.NewbieSampleItemsDto;
import org.jeecg.modules.newbie.model.entity.NewbieBrand;
import org.jeecg.modules.newbie.model.entity.NewbieRequestProcessNumber;
import org.jeecg.modules.newbie.model.entity.NewbieRequestSample;
import org.jeecg.modules.newbie.model.entity.NewbieSampleItem;
import org.jeecg.modules.newbie.model.vo.NewbieRequestSampleVo;
import org.jeecg.modules.newbie.model.vo.NewbieSampleItemVo;
import org.jeecg.modules.newbie.service.INewbieBrandService;
import org.jeecg.modules.newbie.service.INewbieCategoryService;
import org.jeecg.modules.newbie.service.INewbieRequestSampleService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NewbieRequestSampleServiceImpl
        extends ServiceImpl<NewbieRequestSampleMapper, NewbieRequestSample>
        implements INewbieRequestSampleService {

    @Autowired
    private NewbieSampleItemsMapper sampleItemsMapper;

    @Autowired
    private NewbieRequestProcessNumberMapper requestProcessNumberMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Transactional
    @Override
    public NewbieRequestSample saveForm(NewbieRequestSampleFormDto formDto) {
        /*
            Generate process number (e.g. 研发部-20251111-001),
            and insert it to table: newbie_request_process_number.
            If the number exists in the table already,
            generate a new one until success by adding the seq part
            of the process number.
         */
        final String reqDeptStr = formDto.getReqDept();
        final LocalDate reqDate = formDto.getReqDate()
                .toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        final String reqDateStr = reqDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 获取最近使用的流程单号序列号，并加锁以防并发冲突
        Integer maxSeq =
                requestProcessNumberMapper.seleteMaxSeqByDeptAndDateForUpdate(reqDeptStr, reqDateStr);
        if (maxSeq == null) {
            maxSeq = 0;
        } else if (maxSeq >= 999) {
            throw new RuntimeException("流程单号序列超出限制，部门：" + reqDeptStr + "，日期：" + reqDateStr);
        }

        // 生成新的流程单号
        final String seqStr = String.format("%03d", ++maxSeq);
        String newProcessNumber =
                reqDeptStr + "-" + reqDateStr + "-" + seqStr;

        requestProcessNumberMapper.insert(new NewbieRequestProcessNumber() {{
            setReqDept(reqDeptStr);
            setReqDate(reqDateStr);
            setSeq(Integer.parseInt(seqStr));
            setIsDeleted(0);
        }});

        NewbieRequestSample requestSample = new NewbieRequestSample() {{
            setTitle(formDto.getTitle());
            setRequester(formDto.getRequester());
            setReqDept(formDto.getReqDept());
            setReqDate(formDto.getReqDate());
            setProcNo(newProcessNumber);
            setItemCategory(formDto.getItemCategory());
            setPhone(formDto.getPhone());
            setFileUploadUrl(formDto.getFileUploadUrl());
            setPersonInChargeId(formDto.getPersonInChargeId());
            setStatus(0);
            setIsDeleted(0);
        }};

        boolean isOk = save(requestSample);
        if (!isOk) {
            throw new RuntimeException("Failed to save request sample, phase 1.");
        }

        List<NewbieSampleItem> itemList = new ArrayList<>();
        for (NewbieSampleItemsDto itemDto : formDto.getItems()) {
            NewbieSampleItem item = new NewbieSampleItem() {{
                setOrdinal(itemDto.getOrdinal());
                setReqId(requestSample.getId());
                setBrandId(itemDto.getBrandId());
                setCategoryId(itemDto.getCategoryId());
                setProductId(itemDto.getProductId());
                setQtyBox(itemDto.getQtyBox());
                setQtyOrder(itemDto.getQtyOrder());
                setIsDeleted(0);
            }};
            itemList.add(item);
        }
        List<BatchResult> insert = sampleItemsMapper.insert(itemList);
        if (insert == null || insert.isEmpty()) {
            throw new RuntimeException("Failed to save sample items, phase 2.");
        } else {
            insert.stream().forEach(r -> {
                Arrays.stream(r.getUpdateCounts()).forEach(count -> {
                    if (count != 1) {
                        throw new RuntimeException("Failed to save sample items, phase 2.");
                    }
                });
            });
        }

        return requestSample;
    }

    @Override
    public NewbieRequestSampleVo getRequestById(String reqId) {

        // 获取样品领用申请单主表信息
        NewbieRequestSample requestSample = getBaseMapper().selectById(reqId);

        // 获取样品项目明细，关联品牌表和类别表
        List<NewbieSampleItemVo> sampleItemVoList =
                sampleItemsMapper.selectAllItemsByReqId(reqId);

        // 获取负责人姓名
        SysUser sysUser = userMapper.selectById(requestSample.getPersonInChargeId());

        // 组装返回VO对象
        NewbieRequestSampleVo requestSampleVo =
                new NewbieRequestSampleVo() {{
                    setTitle(requestSample.getTitle());
                    setRequester(requestSample.getRequester());
                    setReqDept(requestSample.getReqDept());
                    setReqDate(requestSample.getReqDate());
                    setProcNo(requestSample.getProcNo());
                    setItemCategory(requestSample.getItemCategory());
                    setPhone(requestSample.getPhone());
                    setItems(sampleItemVoList);
                    setFileUploadUrl(requestSample.getFileUploadUrl());
                    setPersonInChargeName(sysUser != null ? sysUser.getRealname() : null);
                    setStatus(requestSample.getStatus());
                    setComment(requestSample.getComment());
                }};

        return requestSampleVo;
    }

    /**
     * 更新样品领用申请单状态
     */
    @Override
    public void updateRequestStatus(NewbieRequestSample requestSample) {
        getBaseMapper().updateById(requestSample);
    }
}
