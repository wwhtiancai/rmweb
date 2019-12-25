package com.tmri.rfid.service;

import com.tmri.rfid.bean.CustomizeTask;
import com.tmri.rfid.common.CustomizeTaskStatus;
import com.tmri.rfid.util.MapUtilities;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Joey on 2016/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
public class CustomizeTaskServiceTest extends TestCase{

    @Resource
    private CustomizeTaskService customizeTaskService;

    @Test
    public void testQueryList() throws Exception{
        List<CustomizeTask> taskList = customizeTaskService.queryList(MapUtilities.buildMap("tid", "E88100011D28EFCF",
                "zts", new int[] {CustomizeTaskStatus.PENDING.getStatus(), CustomizeTaskStatus.SUBMIT.getStatus()}));
        System.out.println(taskList.size());
    }

}
