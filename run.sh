 #!/bin/sh

compiler="./tools/compiler.sh";
sources="core/Main.java";
mainClass="core.Main";
default="eth0";

function getIp(){
    ifconfig "$1" | grep -oE "(inet\s(end\.:\s)?)([[:digit:]]*(\.[[:digit:]]*){3})" | grep -oE "([[:digit:]]*(\.[[:digit:]]*){3})"
}

if [ $# -le 0 ]; then
    interface="$default";
else
    interface="$1";
fi

ip=$(getIp "$interface");

if [[ -z "$ip" ]]; then
    echo "A interface \"$interface\" não existe ou não está em uso.";
    exit 1;
fi

bash -c "$compiler $sources"
if [[ $? -ne 0 ]]; then
    exit 1;
fi

java -classpath ./build/ "$mainClass" $ip;
