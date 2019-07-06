package com.xyh.mybatis;

import com.xyh.mybatis.entity.User;
import com.xyh.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xuyh
 * @date 2019/7/2
 */
public class Test {
    public static void main(String[] args) {
        SqlSession sqlSession=null;
        try (InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml")) {
            SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
            build.getConfiguration().addMapper(UserMapper.class);
            sqlSession = build.openSession();

            UserMapper userMapper= sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getOne(1);
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
}
