package com.fit.dao;

interface DAO {

    /**
     * 保存对象
     *
     * @param str
     * @param obj
     */
    Object save(String str, Object obj) throws Exception;

    /**
     * 修改对象
     *
     * @param str
     * @param obj
     */
    Object update(String str, Object obj) throws Exception;

    /**
     * 删除对象
     *
     * @param str
     * @param obj
     */
    Object delete(String str, Object obj) throws Exception;

    /**
     * 查找对象
     *
     * @param str
     * @param obj
     */
    Object findForObject(String str, Object obj) throws Exception;

    /**
     * 查找对象
     *
     * @param str
     * @param obj
     */
    Object findForList(String str, Object obj) throws Exception;

    /**
     * 查找对象封装成Map
     *
     * @param sql
     * @param obj
     * @param key
     * @param value
     */
    Object findForMap(String sql, Object obj, String key, String value) throws Exception;
}
