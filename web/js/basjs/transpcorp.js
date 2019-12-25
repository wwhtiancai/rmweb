
function showVehDetail(xh)
{
   formain.xh.value=xh;
   winid = openwin1024("","bussiness3",false);
   formain.target="bussiness3";
   formain.action="transpcorp.bas?method=KehuoyunEnterpriseVehDetail";
   formain.submit();
   winid.focus();
}
function showDrvDetail(dabh)
{
   formain.dabh.value=dabh;
   winid = openwin1024("","bussiness3",false);
   formain.target="bussiness3";
   formain.action="transpcorp.bas?method=KehuoyunEnterpriseDrvDetail";
   formain.submit();
   winid.focus();
}

function showAcdDetail(sgbh)
{
   formain.sgbh.value=sgbh;
   winid = openwin1024("","bussiness3",false);
   formain.target="bussiness3";
   formain.action="transpcorp.bas?method=KehuoyunEnterpriseAcdDetail";
   formain.submit();
   winid.focus();
}

function showVioDetail(wfbh)
{
   formain.wfbh.value=wfbh;
   winid = openwin1024("","bussiness3",false);
   formain.target="bussiness3";
   formain.action="transpcorp.bas?method=KehuoyunEnterpriseVioDetail";
   formain.submit();
   winid.focus();
}

function showSurveilDetail(xh)
{
   formain.xh.value=xh;
   winid = openwin1024("","bussiness3",false);
   formain.target="bussiness3";
   formain.action="transpcorp.bas?method=KehuoyunEnterpriseSurveilDetail";
   formain.submit();
   winid.focus();
}
