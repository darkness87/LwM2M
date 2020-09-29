ps -elf | grep lwm2m-Server | grep -v grep | awk '{print $4}' | while read PID; do echo "killing $PID ..."; kill -9 $PID; done
echo "lwm2m-Server server is stop!!"