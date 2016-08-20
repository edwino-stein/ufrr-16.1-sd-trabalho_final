 #!/bin/sh

#  compiler
#
#  Shellscript para compilar pragramas didaticos em Java.
#
#  Versao 1.0
#
#  Created by Edwino Stein - edwino.stein@gmail.com on 15/07/16.
#

# Modo de uso: $ ./tools/compiler <classe_Principal> [<dependencia_1> <dependencia_2> <dependencia_n>]

# ************************* Variaveis de Configuração **************************

common=();
forceCommonCompilation=0;

autoExec=1;
showSplash=1;

baseDir='./';
dirBuild="build/";

compilerInvocation="javac -Xlint:all -d {output} -classpath {output} -cp . {input}";

tempLogFile="tools/compileTemp.log";
sourceFileExtension=".java";
outputFileExtension=".class";

# ***************************** Variaveis globais ******************************

ARGS=0;
returnedData='';
error='';
errorDesc='';
warnings=0;
dependencies='';

# ******************************** Sub-rotinas *********************************

function showError(){

   erroType=$1;

   if [ $erroType == "fatal" ]; then

       echo;
       echo "$error";
       echo "********************** DESCRIÇÃO DO ERRO **********************";
       echo -e "$errorDesc";
       echo "***************************************************************";
       echo;
       echo "O processo foi abortado!";
       exit 1;

   else

       echo;
       echo "$error";
       echo "********************** DESCRIÇÃO DO ALERTA **********************";
       echo "$errorDesc";
       echo "*****************************************************************";
       echo;
       warnings=$(($warnings + 1));

   fi
}

#Função verifica o nivel do erro e o exibe na tela
function checkError(){

   code=$1;

   case $code in

       0) # Nenhum erro ocorreu
           return;
       ;;

       1) # Erro durante a compilação ou linkedição
           showError "fatal";
       ;;

       2) # Alerta durante a compilação ou linkedição
           showError "warning";
       ;;

       3) # O arquivo não foi encontrado para a compilação
           showError "fatal";
       ;;

       101)
           error="Erro no processo.";
           errorDesc="Nenhum arquivo foi definido para ser compilado.\nÉ necessário pelo menos o arquivo fonte principal do programa.";
           showError "fatal";
       ;;

       *)
           error="Erro desconhecido.";
           errorDesc="Algum erro desconhecido ocorreu.";
           showError "fatal";
       ;;
   esac
}

# Função que remove apenas a extensão de um arquivo
function removeExtension(){
   echo ${1} | cut -f1 -d'.';
}

# Função que retorna apenas o nome do arquivo sem extensão
function getFileName(){
   fileName=`basename $1`;
   removeExtension $fileName;
}

# Função que subistitui uma substring por outra
function strReplace(){
   search="$1";
   replace="$2";
   subject="$3";
   echo "${subject/$search/$replace}";
}

#Converte um path para namesapace
function pathToNS(){
    path=$1;
    echo $path | tr "/" ".";
}

#Converte um namespace para path
function NSToPath(){
    ns=$1;
    echo $ns | tr "." "/";
}

# Função que realiza a compilação do codigo fonte em um codigo objeto
function compile(){

   error="";
   errorDesc="";
   local fileName="$(removeExtension $1)$sourceFileExtension";
   local fileTarget=$(getFileName $1);
   local invocation="$compilerInvocation";
   local javacCode;

   # Verifica se o arquivo fonte existe
   if [ ! -f "${baseDir}${fileName}" ] ; then
       error="Erro antes da compilação:";
       errorDesc="O arquivo \"$baseDir$fileName\" é inválido ou não existe!";
       return 3;
   fi

   # Remove arquivo objeto antigo, se existir
   if [ -e "${baseDir}${dirBuild}${fileTarget}.o" ] ; then
       rm "${baseDir}${dirBuild}${fileTarget}.o"
   fi

   # Prepara a linha de comando para a compilação
   invocation=$(strReplace "{output}" "${baseDir}${dirBuild}" "$invocation");
   invocation=$(strReplace "{output}" "${baseDir}${dirBuild}" "$invocation");
   invocation=$(strReplace "{input}" "${baseDir}${fileName}" "$invocation");

   # Salva o log gerado em uma varaivel e remove arquivo temporário
   bash -c "$invocation > $tempLogFile 2>&1";
   javacCode=$?;

   # Salva o arquivo de log gerado em uma varaivel e o remove
   errorDesc=$(<$tempLogFile);
   rm "$tempLogFile";

   #Verifica se ocorreu algum erro
   if [ $javacCode -ne 0 ];  then
       error="Ocorreu algum(s) erro(s) durante a compilação do arquivo \"$fileName\":";
       return 1;

   # Verifica se ocorreu algum alerta
   elif [ -n "$errorDesc" ]; then
       error="Ocorreu algum(s) alerta(s) durante a compilação do arquivo \"$fileName\":";
       return 2;
   fi

   return 0;
}

# ***************************** Programa principal *****************************

if [ $showSplash -eq 1 ]; then
   echo;
   echo " ****** Processo de compilação ******";
fi


#************* Verifica se existe pelo menos um argumento *************
if [ $# -le 0 ]; then
   checkError 101;
fi


# ************* Trata os argumentos *************
for i in `seq 1 $#`
do
   eval "ARGS[$i]=\$$i";
done;


# ************* Cria o diretório onde ficarão os binarios, caso nao exista *************
if [ ! -d "$baseDir$dirBuild" ]; then
 echo -n "# Criando diretório para binários em \"$dirBuild\"...";
 mkdir "$baseDir$dirBuild";
 echo " OK"
fi

# ************* Compila as dependencias comum *************
if [ ${#common[@]} -gt 0 ]; then

    if [ $forceCommonCompilation -ne 0 ]; then
        echo "# Forçando a compilação de todas dependências comum!";
    fi

    for i in `seq 0 $((${#common[@]} - 1))`
    do
        # Verifica se existe a necessidade de compilar o arquivo
        returnedData=$(removeExtension ${common[$i]});

    	if [ ! -f "${baseDir}${dirBuild}${returnedData}$outputFileExtension" ] || [ $forceCommonCompilation -ne 0 ] ; then
            echo -n "# Compilando a dependência comum \"$(removeExtension ${common[$i]})$sourceFileExtension\"... ";

            compile ${common[$i]};
            returnedData=$?;

            if [ $returnedData -eq 0 ]; then
                echo "OK!";
            elif [ $returnedData -eq 1 ]; then
                echo "Falhou!";
            elif [ $returnedData -eq 3 ]; then
                echo "Falhou!";
            elif [ $returnedData -eq 2 ]; then
                echo "Alerta!";
            fi

            checkError $returnedData;
    	fi

    done;
fi

# ************* Percorre os argumentos e compila os arquivos fontes *************
if [ $# -gt 1 ]; then
	for i in `seq 2 $#`
	do
        echo -n "# Compilando o arquivo \"$(removeExtension ${ARGS[$i]})$sourceFileExtension\"... ";

		compile ${ARGS[$i]};
        returnedData=$?;

        if [ $returnedData -eq 0 ]; then
            echo "OK!";
        elif [ $returnedData -eq 1 ]; then
            echo "Falhou!";
        elif [ $returnedData -eq 3 ]; then
            echo "Falhou!";
        elif [ $returnedData -eq 2 ]; then
            echo "Alerta!";
        fi

		checkError $returnedData;

	done;
fi

# ************* Compila o arquivo fonte do programa principal *************
echo -n "# Compilando o programa principal \"$(removeExtension ${ARGS[1]})$sourceFileExtension\"... ";

compile ${ARGS[1]};
returnedData=$?

if [ $returnedData -eq 0 ]; then
    echo "OK!";
elif [ $returnedData -eq 1 ]; then
    echo "Falhou!";
elif [ $returnedData -eq 3 ]; then
    echo "Falhou!";
elif [ $returnedData -eq 2 ]; then
    echo "Alerta!";
fi

checkError $returnedData;

# Guarda o namespace da classe principal
main=$(removeExtension ${ARGS[1]});
main=$(pathToNS $main);


# ************* Analiza se ocorreu aletas *************
if [ $warnings -ne 0 ];  then
	echo -n "# Ocorreu(ram) $warnings alerta(s) no processo! ";
else
	echo "# Não houve erros durante durante o processo!";
fi

# ************* Executa o programa se possivel *************
if [ $autoExec -ne 0 ] ; then

	if [ $warnings -ne 0 ];  then
		echo -n "Precione enter para continuar...";
		read KEY;
	fi

	echo "# Executando \"${main}\"...";
	sleep 1s;
    echo;

    # Executa o programa
	bash -c "java -classpath ${baseDir}${dirBuild} ${main}";
fi

exit 0;
