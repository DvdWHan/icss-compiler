mvn clean compile

set package_name=nl/han/ica/generated
set output_directory=src/main/java/%package_name%
set grammar_file=src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4

antlr4 -o %output_directory% -package %package_name% %grammar_file%
antlr4 -visitor -o %output_directory% -package %package_name% -Dlanguage=Java %grammar_file%

for %%f in (%output_directory%\*) do (
    if not "%%~xf"==".java" del "%%f"
)
