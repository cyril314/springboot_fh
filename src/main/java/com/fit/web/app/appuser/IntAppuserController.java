package com.fit.web.app.appuser;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.fit.common.utils.DateUtil;
import com.fit.common.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fit.common.base.BaseController;
import com.fit.service.system.appuser.AppuserManager;
import com.fit.util.AppUtil;
import com.fit.entity.PageData;


/**
 * @author 会员-接口类
 * 相关参数协议：
 * 00	请求失败
 * 01	请求成功
 * 02	返回空值
 * 03	请求协议参数不完整
 * 04  用户名或密码错误
 * 05  FKEY验证失败
 */
@Slf4j
@Controller
@RequestMapping(value = "/appuser")
public class IntAppuserController extends BaseController {

    @Resource(name = "appuserService")
    private AppuserManager appuserService;

    /**
     * 根据用户名获取会员信息
     *
     * @return
     */
    @RequestMapping(value = "/getAppuserByUm")
    @ResponseBody
    public Object getAppuserByUsernmae() {
        logBefore(log, "根据用户名获取会员信息");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = null;
        pd = this.getPageData();
        String result = "00";
        try {
            if (MD5.checkKey("USERNAME", DateUtil.getDays(), pd.getString("FKEY"))) {    //检验请求key值是否合法
                if (AppUtil.checkParam("getAppuserByUsernmae", pd)) {    //检查参数
                    pd = appuserService.findByUsername(pd);
                    map.put("pd", pd);
                    result = (null == pd) ? "02" : "01";
                } else {
                    result = "03";
                }
            } else {
                result = "05";
            }
        } catch (Exception e) {
            log.error(e.toString(), e);
        } finally {
            map.put("result", result);
            logAfter(log);
        }
        return AppUtil.returnObject(new PageData(), map);
    }
}
	
 