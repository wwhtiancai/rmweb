package com.tmri.framework.dao;

import java.sql.SQLException;
import java.util.List;

import com.tmri.share.frm.util.PageController;
import com.tmri.share.frm.bean.Codetype;

public interface CodetypeDao {
  public List getCodetypes(Codetype codetype) throws SQLException ;
  public List getCodetypesByPageSize(Codetype codetype,PageController controller) throws SQLException;
}
