@echo off
E:
cd E:\idea\wwh\project\securities-manager
call mvn clean -Dmaven.test.skip=true tomcat7:redeploy
