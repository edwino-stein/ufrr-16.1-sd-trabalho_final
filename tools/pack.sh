 #!/bin/sh

 # Função que remove apenas a extensão de um arquivo
 function removeExtension(){
    echo ${1} | cut -f1 -d'.';
 }

output="$(removeExtension $1).jar"
main="$(removeExtension $2)";
classPath="$3";

bash -c "jar cfe $output $main -C $classPath ."
