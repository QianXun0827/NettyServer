package com.pm.cms.service.impl;

import com.pm.cms.mapper.ShelterMapper;
import com.pm.cms.pojo.PmShelter;
import com.pm.cms.service.AdditionService;
import com.pm.cms.vo.ShelterVo;
import com.pm.common.dto.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 站牌选择Service实现类
 *
 * @author huhaiqiang
 * @date 2018/08/07 17:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdditionServiceImpl implements AdditionService {

    @Autowired
    private ShelterMapper shelterMapper;

    @Override
    public SysResult chooseShelter(Integer subareaNum) {
        List<PmShelter> list = shelterMapper.selectShelterInfo(subareaNum);
        List<ShelterVo> shelterVos = new ArrayList<>();

        for (PmShelter pmShelter : list) {
            ShelterVo shelterVo = ShelterVo.builder()
                    .iccid(pmShelter.getIccid())
                    .shelterName(pmShelter.getShelterName())
                    .shelterDirection(pmShelter.getShelterDirection())
                    .build();
            shelterVos.add(shelterVo);
        }
        return SysResult.oK(shelterVos);
    }
}
