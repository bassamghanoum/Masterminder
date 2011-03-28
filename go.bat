@echo off

set EMMA_OPTS=
if "%1"=="emma" set EMMA_OPTS=-cp %EMMA_HOME%\lib\emma.jar emmarun -r html

java -ea %EMMA_OPTS% -cp mastermind\target\mastermind-1.0-SNAPSHOT.jar org.pharaox.mastermind.Main