ps -elf | grep lwm2m-Client | grep -v grep | awk '{print $4}' | while read PID; do echo "killing $PID ..."; kill -9 $PID; done
echo "lwm2m-Client is stop!!"