@rem -----------------------------------------------------------------------------------------------
@rem build_samples.bat
@rem build the samples
@rem -----------------------------------------------------------------------------------------------

@if "%JDK%"=="" goto variable_undef

if exist dog_demo.class del dog_demo.class
if exist dog_update.class del dog_update.class
"%JDK%\bin\javac" -classpath . -Xlint:deprecation dog_demo.java
"%JDK%\bin\javac" -classpath . -Xlint:deprecation dog_update.java
"%JDK%\bin\javac" -classpath . -Xlint:deprecation DogStatusManager.java
@goto exit

:variable_undef
@echo.
@echo Error: please set the variable JDK first

:exit

pause