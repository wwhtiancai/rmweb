package com.tmri.rfid.task;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.tmri.rfid.bean.OperationLog;
import com.tmri.rfid.common.OperationResult;
import com.tmri.rfid.service.ExchangeService;
import com.tmri.rfid.service.OperationLogService;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.tmri.rfid.bean.MyDataEx;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.rfid.util.MyConstantUtils;
import com.tmri.share.frm.dao.jdbc.FrmDaoJdbc;
import com.tmri.share.frm.util.DateUtil;


/*
 *wuweihong
 *2016-2-15
 */
@Repository
public class DataPassTask extends FrmDaoJdbc  {

    private final static Logger LOG = LoggerFactory.getLogger(DataPassTask.class);

    @Resource
    private ExchangeService exchangeService;

    @Resource
    private OperationLogService operationLogService;

    private List<Task> triggerTasks;

    public void setTriggerTasks(List<Task> triggerTasks) {
        this.triggerTasks = triggerTasks;
    }

    public void doit() throws Exception {
        List<MyDataEx> mapListJsonData = exchangeService.fetchData();
        if (mapListJsonData != null && !mapListJsonData.isEmpty()) {
            dealData(mapListJsonData);
        }
    }

    public void dealData(List<MyDataEx> mapListJson) throws Exception {
        String postbh = "";
        for (MyDataEx myDataEx : mapListJson) {
            int resultFlag = 0;
            String sj = myDataEx.getSj();
            Object bh = myDataEx.getBh();
            try {
                StringBuffer conditionBuffer = new StringBuffer();
                String sjlx = myDataEx.getSjlx();
                JSONObject jasonObject = JSONObject.fromObject(sj);
                String keyName = MyConstantUtils.getByTableName(sjlx.toUpperCase()).getKeyName();
                String className = MyConstantUtils.getByTableName(sjlx.toUpperCase()).getClassName();
                for (Object key : jasonObject.keySet()) {
                    if (String.valueOf(key).equalsIgnoreCase(keyName)) {
                        conditionBuffer.append(" where " + keyName + "='" + jasonObject.get(key) + "'");
                        break;
                    }
                }

                Object yourObj = JSONObject.toBean(jasonObject, Class.forName(className));
                String querySql = "select count(*) from " + sjlx.toUpperCase() + conditionBuffer;
                int selectFlag = jdbcTemplate.queryForObject(querySql, Integer.class);
                LOG.info(String.format("handling data %s, sql = %s, result = %s", bh, querySql, selectFlag));
                StringBuffer startBuffer = new StringBuffer();
                StringBuffer endBuffer = new StringBuffer();
                String sql;
                if (selectFlag <= 0) {
                    startBuffer.append("insert into " + sjlx + "(");
                    endBuffer.append(")values(");
                    Field[] field = yourObj.getClass().getDeclaredFields();
                    for (int j = 0; j < field.length; j++) {
                        String name = field[j].getName();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                        String type = field[j].getGenericType().toString();
                        if (type.equals("class java.util.Date")) {
                            Method m = yourObj.getClass().getMethod("get" + name);
                            Date value = (Date) m.invoke(yourObj);
                            if (value != null) {
                                startBuffer.append(name + ",");
                                endBuffer.append(" to_date('" + DateUtil.getDateString(value, "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd HH24:mi:ss'),");
                            }
                        } else {
                            Method m = yourObj.getClass().getMethod("get" + name);
                            Object value = m.invoke(yourObj);
                            if (value != null) {
                                startBuffer.append(name + ",");
                                endBuffer.append("'" + String.valueOf(value) + "',");
                            }
                        }
                    }
                    String start = startBuffer.substring(0, startBuffer.length() - 1);
                    String end = endBuffer.substring(0, endBuffer.length() - 1);
                    sql = start + end + ")";
                } else {
                    startBuffer.append("UPDATE " + sjlx + " set ");

                    Field[] field = yourObj.getClass().getDeclaredFields();
                    for (int j = 0; j < field.length; j++) {
                        String name = field[j].getName();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                        String type = field[j].getGenericType().toString();
                        if (type.equals("class java.util.Date")) {
                            Method m = yourObj.getClass().getMethod("get" + name);
                            Date value = (Date) m.invoke(yourObj);
                            if (value != null) {
                                startBuffer.append(name + "= to_date('" + DateUtil.getDateString(value, "yyyy-MM-dd HH:mm:ss") + "','yyyy-MM-dd HH24:mi:ss'),");
                            }
                        } else {
                            Method m = yourObj.getClass().getMethod("get" + name);
                            Object value = m.invoke(yourObj);
                            if (value != null) {
                                startBuffer.append(name + "='" + String.valueOf(value) + "',");
                            }
                        }
                    }

                    String start = startBuffer.substring(0, startBuffer.length() - 1);
                    sql = start + conditionBuffer;
                }
                resultFlag = jdbcTemplate.update(sql);
                LOG.info(String.format("handling data %s, sql = %s, result = %s", bh, sql, resultFlag));
                operationLogService.log("EXCHANGE_DATA", bh, sj, resultFlag >= 1 ? OperationResult.SUCCESS : OperationResult.FAIL);
                if (resultFlag >= 1) {
                    postbh += bh + ",";
                }
            } catch (Exception e) {
                LOG.error(String.format("handling data %s fail", bh), e);
                operationLogService.log("EXCHANGE_DATA", bh, sj, OperationResult.FAIL);
            } finally {
                if (resultFlag <= 0) {
                    //处理失败5次后删除数据
                    List<OperationLog> failList = operationLogService.fetchByCondition(
                            MapUtilities.buildMap("czmc", "EXCHANGE_DATA", "gjz", bh, "jg", OperationResult.FAIL.getResult()));
                    if (failList.size() >= 4) {
                        postbh += bh + ",";
                    }
                }
            }
        }
        if (!postbh.isEmpty()) {
            exchangeService.updateFlag(postbh.substring(0, postbh.length() - 1));
        }
        if (triggerTasks != null && !triggerTasks.isEmpty()) {
            for (Task task : triggerTasks) {
                task.execute();
            }
        }
    }
}
