REM This script prepares the current shell's environment variables (not permanently)

REM Used for backing services like the PostgreSQL database
SET VCAP_APPLICATION={}
SET VCAP_SERVICES={}

REM Overwrite logging library defaults
SET APPENDER=STDOUT
SET LOG_APP_LEVEL=INFO
