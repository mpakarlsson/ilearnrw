A Doxyfile for generating documentation can be found at `docs/Doxygen`.

##Installation

###Linux

_todo:_ Provide installation documentation.

###Windows

To build the documentation a couple of dependencies are required:

|Name|Version|Url|Exe|Note|
|----|-------|---|---|----|
|Doxygen|1.8.4|http://doxygen.org|doxygen.exe |- |
|Mikitex|2.9.3759|http://miktex.org/download|tex.exe |Should work with other tex packages as well. |
|ghostscript|9.0.7|http://sourceforge.net/projects/ghostscript/|? |- |
|Graphviz (dot)|2.8|http://www.graphviz.org/|dot.exe |- |

The exe files listed must be available in the windows %PATH%. variable.

##Build documentation

###Windows

To build the documentation run the `make_doc.bat` file located at docs/make_doc.bat.

The batchfile will run doxygen and then tex. The output `bin/doxygen/latex/refman.pdf` will be copied to `docs/doxygen.pdf`.

