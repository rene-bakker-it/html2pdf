#!/usr/bin/env bash

CLEAN=''
ENCODING=''

case "$1" in
  -c)
    CLEAN='-c'
    shift
    ;;
  -e)
    shift
    if [ -z $1 ]; then
      ENCODING="-e $1"
      shift
    fi
    ;;
  -h)
    cat <<EOF
html2pdf [-ceh] <html> <pdf>

html  a html input file or url
pdf   name of the pdf output file

options:
-c    cleanup the html before generating the pdf.
-e    encoding. Defaults to utf-8. Only used to clean files.
-h    get help.
EOF
    exit 0
    ;;
esac

if [ -z "$1" ]; then
  echo "ERROR: input not specified."
  exit 1
fi

if [ -z "$2" ]; then
  echo "ERROR: output not specified."
  exit 1
fi

java -jar /usr/share/html2pdf/html2pdf.jar $ENCODING $CLEAN -i "$1" -o "$2"

