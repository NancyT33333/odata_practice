echo "Hint: run script with 'source localEnvironmentSetup.sh'"
echo "This script prepares the current shell's environment variables (not permanently)"

# Used for backing services like the PostgreSQL database
export VCAP_APPLICATION={}
export VCAP_SERVICES='{}'

# Overwrite logging library defaults
export APPENDER=STDOUT
export LOG_APP_LEVEL=INFO

echo \$VCAP_SERVICES=$VCAP_SERVICES
