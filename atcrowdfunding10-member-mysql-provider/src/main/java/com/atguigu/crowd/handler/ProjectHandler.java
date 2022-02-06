package com.atguigu.crowd.handler;

import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.entity.vo.ProjectVO;
import com.atguigu.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-27 20:37
 */
@RestController
public class ProjectHandler {
    @Autowired
    private ProjectService projectService;
    @RequestMapping("get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getportalTypeProjectData(){
       try {

           List<PortalTypeVO> portalTypeVOS = projectService.getportalTypeVO();
           return ResultEntity.successwithdata(portalTypeVOS);
       }catch (Exception e){

           return ResultEntity.failed(e.getMessage());
       }






    }
    @RequestMapping("save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){
          try {
              projectService.saveProject(projectVO,memberId);

              return ResultEntity.successwithoutdata();
          }catch (Exception e){
              return ResultEntity.failed(e.getMessage());
          }
    }
    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer id) {
        try {
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(id);
            return ResultEntity.successwithdata(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }






}
