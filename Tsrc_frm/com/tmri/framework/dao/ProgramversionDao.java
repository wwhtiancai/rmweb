package com.tmri.framework.dao;

import java.util.List;

public interface ProgramversionDao {
    public List getProgramversionList()throws Exception;
  //��ȡ�洢���̰汾
    public String getPkgversion(String pkgname);
}
