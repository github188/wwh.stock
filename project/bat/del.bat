cd E:\idea\wwh\project\bat
call tomshut.bat
ping 127.0.0.1 -n 10>nul
del E:\maven\apache-tomcat-7.0.59\webapps\securities-manager.war
rmdir /S /Q E:\maven\apache-tomcat-7.0.59\webapps\securities-manager
cd E:\idea\wwh\project\bat
call tomstart.bat
ping 127.0.0.1 -n 10>nul
