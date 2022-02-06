package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-27 20:37
 */
public interface ProjectService {
    void saveProject(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getportalTypeVO();

    DetailProjectVO getDetailProjectVO(Integer id);

}
