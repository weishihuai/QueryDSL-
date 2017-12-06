package com.yuqiyu.querydsl.sample.chapter3.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuqiyu.querydsl.sample.chapter3.bean.QUserBean;
import com.yuqiyu.querydsl.sample.chapter3.bean.UserBean;
import com.yuqiyu.querydsl.sample.chapter3.jpa.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * ========================
 * Created with IntelliJ IDEA.
 * User：恒宇少年
 * Date：2017/7/4
 * Time：22:15
 * 码云：http://git.oschina.net/jnyqy
 * ========================
 */
@RestController
public class UserController
{
    @Autowired
    private UserJPA userJPA;

    //实体管理者
    @Autowired
    private EntityManager entityManager;

    //JPA查询工厂
    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void initFactory()
    {
        queryFactory = new JPAQueryFactory(entityManager);
        System.out.println("init JPAQueryFactory successfully");
    }


    /**
     * 使用Jpa更新会员信息
     * @param userBean
     */
    @RequestMapping(value = "/updateWithJpa")
    public String updateWithJpa(UserBean userBean)
    {
        //保存会员信息相当于Hibernate内的SaveOrUpdate
        userJPA.save(userBean);
        return "SUCCESS";
    }

    /**
     * 使用QueryDsl更新会员信息
     * @param userBean
     */
    @RequestMapping(value = "/updateWithQueryDsl")
    @Transactional
    public String updateWithQueryDsl(UserBean userBean)
    {
        //querydsl查询实体
        QUserBean _Q_user = QUserBean.userBean;

        queryFactory
                .update(_Q_user)//更新对象
                //更新字段列表
                .set(_Q_user.name,userBean.getName())
                .set(_Q_user.address,userBean.getAddress())
                .set(_Q_user.age,userBean.getAge())
                .set(_Q_user.pwd,userBean.getPwd())
                //更新条件
                .where(_Q_user.id.eq(userBean.getId()))
                //执行更新
                .execute();
        return "SUCCESS";
    }

    /**
     * 使用Jpa删除会员信息
     * @param userBean
     */
    @RequestMapping(value = "/deleteWithJpa")
    public String deleteWithJpa(UserBean userBean)
    {
        //执行删除指定主键的值
        userJPA.delete(userBean.getId());
        return "SUCCESS";
    }

    /**
     * 使用QueryDsl删除会员信息
     * @param userBean
     */
    @RequestMapping(value = "/deleteWithQueryDsl")
    @Transactional
    public String deleteWithQueryDsl(UserBean userBean)
    {
        //querydsl查询实体
        QUserBean _Q_user = QUserBean.userBean;

        queryFactory
                //删除对象
                .delete(_Q_user)
                //删除条件
                .where(
                        _Q_user.name.eq(userBean.getName())
                        .and(_Q_user.age.gt(20))
                )
                //执行删除
                .execute();
        return "SUCCESS";
    }
}
