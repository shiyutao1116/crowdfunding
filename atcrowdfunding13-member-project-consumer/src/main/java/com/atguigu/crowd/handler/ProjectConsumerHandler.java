package com.atguigu.crowd.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.atguigu.crowd.CorwdConstant;
import com.atguigu.crowd.CrowdUtil;
import com.atguigu.crowd.MySQLRemoteService;
import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.vo.*;
import jdk.jfr.events.ThrowablesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.crowd.config.OSSProperties;

import org.springframework.web.servlet.ModelAndView;


@Controller
public class ProjectConsumerHandler {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @Autowired
    private OSSProperties ossProperties;
    @RequestMapping("/get/project/detail/{projectId}")
    public String getprojectDetail(@PathVariable("projectId") Integer id,ModelMap modelMap){
        ResultEntity<DetailProjectVO> detailProjectVORemote = mySQLRemoteService.getDetailProjectVORemote(id);
        if (ResultEntity.SUCCESS.equals(detailProjectVORemote.getResult())){
            DetailProjectVO data = detailProjectVORemote.getData();
            modelMap.addAttribute("detailProjectVO",data);
        }

        return "project-show-detail";
    }





    @RequestMapping("/create/confirm")
    public String saveConfirm(ModelMap modelMap, HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO){
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CorwdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if (projectVO==null){
            throw new RuntimeException(CorwdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }

        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        MemberLoginVO loginMemberVO =(MemberLoginVO) session.getAttribute(CorwdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId=loginMemberVO.getId();
        ResultEntity<String> saveResult= mySQLRemoteService.saveProjectVORemote(projectVO,memberId);

        String result=saveResult.getResult();
        if (ResultEntity.FAILED.equals(result)){
            modelMap.addAttribute(CorwdConstant.ATTR_NAME_MESSAGE,saveResult.getMessage());

            return "project-confirm";
        }
        session.removeAttribute(CorwdConstant.ATTR_NAME_TEMPLE_PROJECT);
        return "redirect:http://localhost/project/create/success";
    }



    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> savereturn(ReturnVO returnVO,HttpSession session){

       try {

           ProjectVO projectVO = (ProjectVO) session.getAttribute(CorwdConstant.ATTR_NAME_TEMPLE_PROJECT);
           if (projectVO==null){

               return ResultEntity.failed(CorwdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
           }
           List<ReturnVO> returnVOList = projectVO.getReturnVOList();

           if (returnVOList == null ||returnVOList.size()==0) {
               returnVOList = new ArrayList<>();
               projectVO.setReturnVOList(returnVOList);
           }
           returnVOList.add(returnVO);
           session.setAttribute(CorwdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
           return ResultEntity.successwithoutdata();
       }
       catch (Exception e){
            return ResultEntity.failed(e.getMessage());

       }

    }
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String>  uploadPicture(MultipartFile returnPicture) throws IOException {
        ResultEntity<String> uploadFileToOss = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());
        return uploadFileToOss;

    }



    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(

            // 接收除了上传图片之外的其他普通数据
            ProjectVO projectVO,

            // 接收上传的头图
            MultipartFile headerPicture,

            // 接收上传的详情图片
            List<MultipartFile> detailPictureList,

            // 用来将收集了一部分数据的ProjectVO对象存入Session域
            HttpSession session,

            // 用来在当前操作失败后返回上一个表单页面时携带提示消息
            ModelMap modelMap
    ) throws IOException {

        // 一、完成头图上传
        // 1.获取当前headerPicture对象是否为空
        boolean headerPictureIsEmpty = headerPicture.isEmpty();

        if(headerPictureIsEmpty) {

            // 2.如果没有上传头图则返回到表单页面并显示错误消息
            modelMap.addAttribute(CorwdConstant.ATTR_NAME_MESSAGE, CorwdConstant.MESSAGE_HEAD_PIC_EMPTY);

            return "project-launch";

        }
        // 3.如果用户确实上传了有内容的文件，则执行上传
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());

        String result = uploadHeaderPicResultEntity.getResult();

        // 4.判断头图是否上传成功
        if(ResultEntity.SUCCESS.equals(result)) {

            // 5.如果成功则从返回的数据中获取图片访问路径
            String headerPicturePath = uploadHeaderPicResultEntity.getData();

            // 6.存入ProjectVO对象中
            projectVO.setHeaderPicturePath(headerPicturePath);
        } else {

            // 7.如果上传失败则返回到表单页面并显示错误消息
            modelMap.addAttribute(CorwdConstant.ATTR_NAME_MESSAGE, CorwdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);

            return "project-launch";

        }

        // 二、上传详情图片
        // 1.创建一个用来存放详情图片路径的集合
        List<String> detailPicturePathList = new ArrayList<String>();

        // 2.检查detailPictureList是否有效
        if(detailPictureList == null || detailPictureList.size() == 0) {
            modelMap.addAttribute(CorwdConstant.ATTR_NAME_MESSAGE, CorwdConstant.MESSAGE_DETAIL_PIC_EMPTY);

            return "project-launch";
        }

        // 3.遍历detailPictureList集合
        for (MultipartFile detailPicture : detailPictureList) {

            // 4.当前detailPicture是否为空
            if(detailPicture.isEmpty()) {

                // 5.检测到详情图片中单个文件为空也是回去显示错误消息
                modelMap.addAttribute(CorwdConstant.ATTR_NAME_MESSAGE, CorwdConstant.MESSAGE_DETAIL_PIC_EMPTY);

                return "project-launch";
            }

            // 6.执行上传
            ResultEntity<String> detailUploadResultEntity = CrowdUtil.uploadFileToOss(
                    ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename());

            // 7.检查上传结果
            String detailUploadResult = detailUploadResultEntity.getResult();

            if(ResultEntity.SUCCESS.equals(detailUploadResult)) {

                String detailPicturePath = detailUploadResultEntity.getData();

                // 8.收集刚刚上传的图片的访问路径
                detailPicturePathList.add(detailPicturePath);
            } else {

                // 9.如果上传失败则返回到表单页面并显示错误消息
                modelMap.addAttribute(CorwdConstant.ATTR_NAME_MESSAGE, CorwdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);

                return "project-launch";
            }

        }

        // 10.将存放了详情图片访问路径的集合存入ProjectVO中
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 三、后续操作
        // 1.将ProjectVO对象存入Session域
        session.setAttribute(CorwdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

        // 2.以完整的访问路径前往下一个收集回报信息的页面
        return "redirect:http://localhost/project/return/info/page";
    }

}
