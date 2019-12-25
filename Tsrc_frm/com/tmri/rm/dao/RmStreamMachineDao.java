package com.tmri.rm.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.rm.bean.RmStreamConsumer;
import com.tmri.rm.bean.RmStreamMachine;
import com.tmri.rm.bean.RmStreamTopic;
import com.tmri.share.frm.dao.FrmDao;
import com.tmri.share.ora.bean.DbResult;

public interface RmStreamMachineDao extends FrmDao {

    public int deleteMachines(RmStreamMachine bean) throws SQLException;

    public List<RmStreamMachine> queryList(RmStreamMachine bean) throws SQLException;

    public DbResult insertBatch(final List<RmStreamMachine> list) throws SQLException;

    public List<RmStreamTopic> queryTopicList(RmStreamTopic bean) throws SQLException;

    public RmStreamTopic getTopicInfo(String tpkey) throws SQLException;

    public DbResult updateTopicBatch(final List<RmStreamTopic> list) throws SQLException;

    public DbResult updateConsumerBatch(final List<RmStreamConsumer> list) throws SQLException;

    public List<RmStreamConsumer> queryConsumerList(RmStreamConsumer bean) throws SQLException;

	public List<RmStreamMachine> queryMachineList() throws Exception;

	public DbResult saveStreamMachines(List<RmStreamMachine> machineList) throws Exception;

	public void deleteMachineSjs() throws Exception;

	public void saveMachineSj(RmStreamMachine streamMachine) throws Exception;
}
