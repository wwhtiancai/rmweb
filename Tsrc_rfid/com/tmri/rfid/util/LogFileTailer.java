package com.tmri.rfid.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class LogFileTailer extends Thread {

    private final static Logger LOG = LoggerFactory.getLogger(LogFileTailer.class);

    private long sampleInterval = 9000;

    private String keyword;
   
    private File logfile;
   
    private boolean startAtBeginning = false;    
   
    private boolean tailing = false;    
   
    private Set listeners = new HashSet();

    private long lastFetchTime;

    private List<String> bufferedLines;

    private int lines;

    private static LogFileTailer tailer;

    private String owner;

    private LogFileTailer(File file, long sampleInterval, boolean startAtBeginning,
                          String keyword, int lines, String owner) {
        this.logfile = file;
        this.sampleInterval = sampleInterval;
        this.startAtBeginning = startAtBeginning;
        this.keyword = keyword;
        this.lines = lines;
        bufferedLines = new ArrayList<String>();
        this.owner = owner;
    }

    public static LogFileTailer getInstance(File file, long sampleInterval,
            boolean startAtBeginning, String keyword, int lines, String owner) {
        if (tailer == null || !tailer.isTailing()) {
            tailer = new LogFileTailer(file, sampleInterval, startAtBeginning, keyword, lines, owner);
            return tailer;
        } else {
            return null;
        }
    }
   
    protected void fireNewLogFileLine(String line) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(keyword) || line.indexOf(keyword) >= 0) {
            bufferedLines.add(new String(line.getBytes("ISO-8859-1"),"GBK"));
        }
    }

    public List<String> fetchLog() {
        List<String> returnList = new ArrayList<String>();
        returnList.addAll(bufferedLines);
        bufferedLines.clear();
        lastFetchTime = System.currentTimeMillis();
        return returnList;
    }
   
    public void stopTailing() {    
        this.tailing = false;    
    }    
   
    public void run() {
        this.tailing = true;
        long filePointer = 0;    
   
        if (this.startAtBeginning) {    
            filePointer = lines;
        } else {    
            filePointer = this.logfile.length() - lines;
            filePointer = filePointer < 0 ? 0 : filePointer;
        }    
   
        try {    
            RandomAccessFile file = new RandomAccessFile(logfile, "r");
            this.lastFetchTime = System.currentTimeMillis();
            while (this.tailing && (System.currentTimeMillis() - lastFetchTime <= 30000)) {
                long fileLength = this.logfile.length();    
                if (fileLength < filePointer) {    
                    file = new RandomAccessFile(logfile, "r");    
                    filePointer = 0;    
                }    
                if (fileLength > filePointer) {    
                    file.seek(filePointer);    
                    String line = file.readLine();
                    while (line != null) {
                        this.fireNewLogFileLine(line);    
                        line = file.readLine();
                    }    
                    filePointer = file.getFilePointer();    
                }    
                sleep(this.sampleInterval);    
            }    
            file.close();
            this.tailing = false;
        } catch (IOException e) {
               
        } catch (InterruptedException e) {    
               
        }    
    }

    public boolean isTailing() {
        return tailing;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}