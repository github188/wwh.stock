@echo off
E:
cd E:\idea\wwh\project\securities-portal
call mvn clean -Dmaven.test.skip=true tomcat7:redeploy
