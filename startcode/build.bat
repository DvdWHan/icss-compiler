mvn clean compile

set package=nl/han/ica/generated
set output_directory=src/main/java/%package%
set grammar_file=src/main/antlr4/nl/han/ica/icss/parser/Icss.g4

antlr4 -o %output_directory% -package %package% %grammar_file%
antlr4 -visitor -o %output_directory% -package %package% -Dlanguage=Java %grammar_file%

for %%f in (%output_directory%\*) do (
    if not "%%~xf"==".java" del "%%f"
)
