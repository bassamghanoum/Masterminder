@echo off

call mvn clean install checkstyle:checkstyle

rd /s /q target