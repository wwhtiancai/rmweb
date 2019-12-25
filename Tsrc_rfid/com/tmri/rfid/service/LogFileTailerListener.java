package com.tmri.rfid.service;

public abstract interface LogFileTailerListener {
    public abstract void newLogFileLine(String line);      
}