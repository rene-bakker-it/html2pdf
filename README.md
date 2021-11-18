# html2pdf
A simple wrapper for [openhtmltopdf](https://github.com/danfickle/openhtmltopdf).

## Installation

In the project directory:

1. Generate the jar file

       gradle html2pdf

2. Copy the jar file to a convenient location, e.g., 

       sudo mkdir /usr/share/html2pdf
       sudo cp build/libs/html2pdf-1.0.jar /usr/share/html2pdf/html2pdf.jar

3. Copy the shell-wrapper to a convenient location. Modify the locations if 
   necessary. Example:

       sudo cp html2pdf.sh /usr/local/bin/html2pdf

#Use

    html2pdf [-ceh] <html> <pdf>
    
    html  a html input file or url
    pdf   name of the pdf output file
    
    options:
    -c    cleanup the html before generating the pdf.
    -e    encoding. Defaults to utf-8. Only used to clean files.
    -h    get help.
    
**Note:** resource links in the html are only used if directly accessible.
Relative links are not followed.

