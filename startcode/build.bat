set output_directory=src/main/java/nl/han/ica/generated
set package=nl.han.ica.generated
set grammar_file=src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4

@REM mvn clean compile
antlr4 -o %output_directory% -package %package% %grammar_file%
antlr4 -visitor -o %output_directory% -package %package% -Dlanguage=Java %grammar_file%

for %%f in (%output_directory%\*) do (
    if not "%%~xf"==".java" del "%%f"
)
