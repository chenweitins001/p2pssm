package com.zihao.mybatisdemo.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.javafx.scene.paint.GradientUtils;
import javafx.scene.effect.Light;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

@Component
@Aspect
public class pagingInterceptor {


@Around( value = "execution(* *..*service.*Paging(..))")
public Object Paging(ProceedingJoinPoint proceedingJoinPoint)throws  Throwable{

      Object[] args =  proceedingJoinPoint.getArgs();
       PageBean pageBean = null ;
         for(Object arg : args){
             if(arg instanceof  PageBean){
               pageBean = (PageBean)arg;
            }
        }

    if(pageBean != null &&  pageBean.isPagination()){
        PageHelper.startPage(pageBean.getPage(),pageBean.getRows());
       }

          Object rv =    proceedingJoinPoint.proceed();

    if(pageBean != null && pageBean.isPagination()){
         PageInfo pageInfo = new PageInfo((List)rv);
            pageBean.setTotal(Long.valueOf(pageInfo.getTotal()).intValue());
        }
            return rv;
 }

}
