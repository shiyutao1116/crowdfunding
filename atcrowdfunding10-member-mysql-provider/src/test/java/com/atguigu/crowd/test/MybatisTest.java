package com.atguigu.crowd.test;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.po.ProjectItemPicPO;
import com.atguigu.crowd.entity.vo.DetailProjectVO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.mapper.ProjectPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-23 20:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {

    @Autowired
    private MemberPOMapper memberPOMapper;
    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;
    @Test
    public void test1() throws SQLException {
        System.out.println(dataSource.getConnection());
    }
    @Test
    public void testmapper(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String source = "123123";
        String encode = passwordEncoder.encode(source);
        MemberPO memberPO = new MemberPO(null, "jack", encode, "杰克", "jack@qq.com", 1, 1, "杰克", "123123", 2);
        memberPOMapper.insert(memberPO);


    }
    @Test
    public void project(){
        List<PortalTypeVO> portalTypeVOS = projectPOMapper.selectPortalTypeVOList();
for (PortalTypeVO portalTypeVO:portalTypeVOS){
    System.out.println(portalTypeVO);
}

    }
    @Test
    public void project2(){
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(9);
        System.out.println(detailProjectVO);
        detailProjectVO.getDetailPicturePathList();

    }
}
