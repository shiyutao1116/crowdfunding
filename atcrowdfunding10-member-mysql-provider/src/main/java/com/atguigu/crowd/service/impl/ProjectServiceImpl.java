package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.po.MemberConfirmInfoPO;
import com.atguigu.crowd.entity.po.MemberLaunchInfoPO;
import com.atguigu.crowd.entity.po.ProjectPO;
import com.atguigu.crowd.entity.po.ReturnPO;
import com.atguigu.crowd.entity.vo.*;
import com.atguigu.crowd.mapper.*;
import com.atguigu.crowd.service.api.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-27 20:37
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;
    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;
   @Autowired
   private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
   @Autowired
   private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
   @Autowired
   private ReturnPOMapper returnPOMapper;




    @Transactional( readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        ProjectPO projectPO =new ProjectPO();
        BeanUtils.copyProperties(projectVO,projectPO);

        projectPO.setMemberid(memberId);
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(format);
        projectPO.setStatus(0);




        projectPOMapper.insertSelective(projectPO);
        Integer projectId = projectPO.getId();

        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertTypeRelationship(typeIdList,projectId);

        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertTagRelationship(tagIdList,projectId);

        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        projectItemPicPOMapper.insertDetailPathList(detailPicturePathList,projectId);

        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO,memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        memberLaunchInfoPOMapper.insertSelective(memberLaunchInfoPO);

        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        ArrayList<ReturnPO> list = new ArrayList<>();
        for (ReturnVO returnVO:returnVOList){
            ReturnPO returnPO = new ReturnPO();

            BeanUtils.copyProperties(returnVO,returnPO);
            list.add(returnPO);


        }


        returnPOMapper.insertReturnPOBatch(list,projectId);




        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO,memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        memberConfirmInfoPOMapper.insertSelective(memberConfirmInfoPO);



    }

    @Override
    public List<PortalTypeVO> getportalTypeVO() {
        return projectPOMapper.selectPortalTypeVOList();

    }

    @Override
    public DetailProjectVO getDetailProjectVO(Integer id) {
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(id);
        Integer status = detailProjectVO.getStatus();
        switch (status)
        {
            case 0:
                detailProjectVO.setStatusText("审核中");
                break;
            case 1:
                detailProjectVO.setStatusText("众筹中");
                break;
            case 2:
                detailProjectVO.setStatusText("众筹成功");
                break;
            case 3:
                detailProjectVO.setStatusText("筹集失败,已关闭");
                break;
            default:
                break;
        }
        String deployDate = detailProjectVO.getDeployDate();

        Date date = new Date();



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = simpleDateFormat.parse(deployDate);
            long time = date.getTime();  //现在时间
            long time1 = parse.getTime();//开始时间
            long pass = (time - time1)/1000/60/60/24;//过去时间
            Integer day = detailProjectVO.getDay();
            int remain =(int)(day - pass) ;//还剩多久
            detailProjectVO.setLastDay(remain);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return detailProjectVO;
    }


}
