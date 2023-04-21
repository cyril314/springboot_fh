package com.fit.common.base;

import javax.servlet.http.HttpServletRequest;

import com.fit.entity.Page;
import com.fit.entity.PageData;
import org.slf4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fit.common.utils.UuidUtil;

import java.util.Random;

/**
 * 基类控制器
 */
public class BaseController {

    /**
     * new PageData对象
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 得到32位的uuid
     */
    public String get32UUID() {
        return UuidUtil.get32UUID();
    }

    /**
     * 得到分页列表的信息
     */
    public Page getPage() {
        return new Page();
    }

    public static void logBefore(Logger logger, String interfaceName) {
        logger.info("-------------------------- START --------------------------");
        logger.info(interfaceName);
    }

    public static void logAfter(Logger logger) {
        logger.info("--------------------------- END ---------------------------\n");
    }

    /**
     * 随机生成六位数验证码
     */
    public static int getRandomNum() {
        Random r = new Random();
        return r.nextInt(900000) + 100000;//(Math.random()*(999999-100000)+100000)
    }
}
