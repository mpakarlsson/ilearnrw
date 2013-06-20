doxygen Doxyfile
pushd ..\bin\doxygen\latex
call make.bat
copy /y refman.pdf ..\..\..\docs\doxygen.pdf
popd