@echo off
call mvnw.cmd test | findstr /C:"create table" /C:"Tests run" /C:"BUILD"
