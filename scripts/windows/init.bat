call env.bat

if NOT DEFINED JAVA_HOME goto javahome
if "%JAVA_HOME%" == "" goto javahome

rem ----
rem Try to set proactive-tmp to the right place
rem 
rem -- try to set proactive-tmp at the same level as ProActive directory
IF NOT EXIST "%PROACTIVE_TMP%" set PROACTIVE_TMP=..\..\..\proactive-tmp
rem -- try to set proactive-tmp one level above ProActive directory
IF NOT EXIST "%PROACTIVE_TMP%" set PROACTIVE_TMP=..\..\..\..\proactive-tmp
rem -- proactive-tmp not created yet : back up to user directory
IF NOT EXIST "%PROACTIVE_TMP%" set PROACTIVE_TMP=%USERPROFILE%\proactive-tmp


rem ----
rem Set up the classpath using classes dir or jar files
rem 
set CLASSPATH=.;%PROACTIVE_TMP%
IF EXIST %PROACTIVE%\classes set CLASSPATH=%CLASSPATH%;%PROACTIVE%\classes
IF EXIST %PROACTIVE%\lib\ProActive.jar set CLASSPATH=%CLASSPATH%;%PROACTIVE%\lib\ProActive.jar
IF EXIST %PROACTIVE%\lib\ProActive_examples.jar set CLASSPATH=%CLASSPATH%;%PROACTIVE%\lib\ProActive_examples.jar
IF EXIST %PROACTIVE%\lib\ic2d.jar set CLASSPATH=%CLASSPATH%;%PROACTIVE%\lib\ic2d.jar

IF EXIST %PROACTIVE%\lib\jini-core.jar set CLASSPATH=%CLASSPATH%;%PROACTIVE%\lib\jini-core.jar
IF EXIST %PROACTIVE%\lib\jini-ext.jar set CLASSPATH=%CLASSPATH%;%PROACTIVE%\lib\jini-ext.jar
IF EXIST %PROACTIVE%\lib\reggie.jar set CLASSPATH=%CLASSPATH%;%PROACTIVE%\lib\reggie.jar
IF EXIST %PROACTIVE%\lib\bcel.jar set CLASSPATH=%CLASSPATH%;%PROACTIVE%\lib\bcel.jar


echo CLASSPATH=%CLASSPATH%

set JAVA_CMD=%JAVA_HOME%\bin\java.exe -Djava.security.manager -Djava.security.policy=proactive.java.policy 
set PATH=%JAVA_HOME%\bin;%PATH%
goto end


:javahome
echo.
echo The enviroment variable JAVA_HOME must be set the current jdk distribution
echo installed on your computer.
echo Use 
echo    set JAVA_HOME=<the directory where is the JDK>
goto end

 
:end
