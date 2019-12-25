package com.tmri.rfid.service.impl;

import com.tmri.rfid.bean.DBOperation;
import com.tmri.rfid.mapper.DBOperationMapper;
import com.tmri.rfid.property.RuntimeProperty;
import com.tmri.rfid.service.DBOperationService;
import com.tmri.rfid.util.MapUtilities;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Connection;
import java.util.*;

/**
 * Created by Joey on 2015/11/10.
 */
@Service
public class DBOperationServiceImpl implements DBOperationService {

    private final static Logger LOG = LoggerFactory.getLogger(DBOperationServiceImpl.class);

    @Autowired
    private DBOperationMapper dbOperationMapper;

    @Resource
    private SqlSessionFactory sessionFactory;

    @Resource(name = "runtimeProperty")
    private RuntimeProperty runtimeProperty;

    @Override
    public List<DBOperation> fetchAll() {
        File dbscriptsFolder = new File(runtimeProperty.getDbScriptFolder());
        File[] scripts = dbscriptsFolder.listFiles();
        if (scripts == null) return null;
        Collections.sort(Arrays.asList(scripts), new Comparator<File>() {
            public int compare(File f1, File f2) {
                if (f1.isDirectory() && f2.isFile())
                    return -1;
	            if (f1.isFile() && f2.isDirectory())
                    return 1;
	            return f2.getName().compareTo(f1.getName());
            }

            public boolean equals(Object obj) {
                return true;
            }
        });

        List<DBOperation> operationList = dbOperationMapper.queryAll();
        List<DBOperation> wholeOperationList = new ArrayList<DBOperation>();
        Map<String, DBOperation> operationMap = new HashMap<String, DBOperation>();
        for (DBOperation dbOperation : operationList) {
            operationMap.put(dbOperation.getFileName(), dbOperation);
        }

        for (File script : scripts) {
            DBOperation operation = operationMap.get(script.getName());
            if (operation == null) {
                operation = new DBOperation();
                operation.setFileName(script.getName());
                operation.setStatus(0);
                operation.setOperator(getOperatorByFileName(script.getName()));
                wholeOperationList.add(operation);
            }
        }
        wholeOperationList.addAll(operationList);
        return wholeOperationList;
    }

    @Override
    public List<DBOperation> fetchExecutable() {
        File dbscriptsFolder = new File(runtimeProperty.getDbScriptFolder());
        File[] scripts = dbscriptsFolder.listFiles();
        if (scripts == null) return null;
        Collections.sort(Arrays.asList(scripts), new Comparator<File>() {
            public int compare(File f1, File f2) {
                if (f1.isDirectory() && f2.isFile())
                    return -1;
                if (f1.isFile() && f2.isDirectory())
                    return 1;
                return f2.getName().compareTo(f1.getName());
            }

            public boolean equals(Object obj) {
                return true;
            }
        });

        List<DBOperation> operationList = dbOperationMapper.queryByCondition(
                MapUtilities.buildMap("status", 1)
        );
        Stack<DBOperation> executable = new Stack<DBOperation>();
        Map<String, DBOperation> operationMap = new HashMap<String, DBOperation>();
        for (DBOperation dbOperation : operationList) {
            operationMap.put(dbOperation.getFileName(), dbOperation);
        }

        for (File script : scripts) {
            DBOperation operation = operationMap.get(script.getName());
            if (operation == null) {
                operation = new DBOperation();
                operation.setFileName(script.getName());
                operation.setStatus(0);
                operation.setOperator(getOperatorByFileName(script.getName()));
                executable.push(operation);
            }
        }
        return executable;
    }

    @Override
    @Transactional
    public void executeScripts() throws Exception {
        SqlSession session = sessionFactory.openSession();
        Connection connection = session.getConnection();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        Stack<DBOperation> operationList = (Stack<DBOperation>)fetchExecutable();
        StringWriter resultWriter;
        StringWriter errorWriter;
        while (!operationList.empty()) {
            DBOperation operation = operationList.pop();
            resultWriter = new StringWriter();
            errorWriter = new StringWriter();
            scriptRunner.setErrorLogWriter(new PrintWriter(resultWriter));
            scriptRunner.setLogWriter(new PrintWriter(resultWriter));
            InputStream is = null;
            try {
                is = new FileInputStream(runtimeProperty.getDbScriptFolder() + operation.getFileName());
                operation.setStartAt(new Date());
                scriptRunner.runScript(
                        new InputStreamReader(is));
                operation.setFinishAt(new Date());
                if (resultWriter.toString().contains("Error executing")) {
                    operation.setResult(resultWriter.toString());
                    operation.setStatus(2);
                    connection.rollback();
                } else {
                    operation.setResult(resultWriter.toString());
                    operation.setStatus(1);
                    connection.commit();
                }
            } catch (Exception e) {
                LOG.error("execute scripts fail", e);
                operation.setFinishAt(new Date());
                operation.setStatus(2);
                operation.setResult(errorWriter.toString());
            } finally {
                if (is != null){
                    try {
                        is.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            dbOperationMapper.create(operation);
            connection.commit();
        }
        connection.close();
        session.close();
    }

    private String getOperatorByFileName(String fileName) {
        int spliterPos = fileName.lastIndexOf("_");
        int fileExtensionPos = fileName.lastIndexOf(".sql");
        return fileName.substring(spliterPos + 1, fileExtensionPos);
    }

    public static void main(String args[]) {
        String fileName = "01_alter_table_fdfd_zhangxd.sql";
        int spliterPos = fileName.lastIndexOf("_");
        int fileExtensionPos = fileName.lastIndexOf(".sql");
        System.out.println(fileName.substring(spliterPos + 1, fileExtensionPos - 1));
    }
}
