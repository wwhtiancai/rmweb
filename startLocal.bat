
set "CATALINA_HOME=C:\apache-tomcat-6.0.44"
set "EXECUTABLE=%CATALINA_HOME%\bin\catalina.bat"

rem Get remaining unshifted command line arguments and save them in the
set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

call "%CATALINA_HOME%\bin\debug.bat"

call "%EXECUTABLE%" start %CMD_LINE_ARGS%