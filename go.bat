@echo off

rem set EMMA_OPTS=-cp %EMMA_HOME%\lib\emma.jar emmarun -r html

java %EMMA_OPTS% -cp mastermind\target\mastermind-1.0-SNAPSHOT.jar com.stoyanr.mastermind.Main %1 %2 %3 %4 %5 %6 %7 %8 %9