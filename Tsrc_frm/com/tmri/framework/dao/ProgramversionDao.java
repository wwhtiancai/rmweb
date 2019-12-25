package com.tmri.framework.dao;

import java.util.List;

public interface ProgramversionDao {
    public List getProgramversionList()throws Exception;
  //获取存储过程版本
    public String getPkgversion(String pkgname);
}
