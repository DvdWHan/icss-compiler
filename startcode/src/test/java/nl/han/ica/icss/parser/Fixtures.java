package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.VariableIdentifier;
import nl.han.ica.icss.ast.expression.binary.BinaryAddition;
import nl.han.ica.icss.ast.expression.binary.BinaryMultiplication;
import nl.han.ica.icss.ast.expression.literal.BooleanLiteral;
import nl.han.ica.icss.ast.expression.literal.ColorLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.expression.literal.numeric.ScalarLiteral;
import nl.han.ica.icss.ast.selector.ClassSelector;
import nl.han.ica.icss.ast.selector.ElementSelector;
import nl.han.ica.icss.ast.selector.IdSelector;

// @formatter:off
public class Fixtures {
  public static Ast uncheckedLevel0() {
    var stylesheet = new Stylesheet();
    stylesheet.addChild(
        new Ruleset(
            new ElementSelector("p"),
            new Body()
                .addChild(new Declaration(
                    new Property("width"),
                    new PercentageLiteral(100)
                ))
                .addChild(new Declaration(
                    new Property("height"),
                    new PixelLiteral(250)
                ))
                .addChild(new Declaration(
                    new Property("background-color"),
                    new ColorLiteral("000000")
                ))
                .addChild(new Declaration(
                    new Property("color"),
                    new ColorLiteral("ffffff")
                ))
        )
    );
    stylesheet.addChild(
        new Ruleset(
            new IdSelector("menu"),
            new Body()
                .addChild(new Declaration(
                    new Property("width"),
                    new PercentageLiteral(75)
                ))
                .addChild(new Declaration(
                    new Property("height"),
                    new PixelLiteral(250)
                ))
        )
    );
    stylesheet.addChild(
        new Ruleset(
            new ClassSelector("menu"),
            new Body()
                .addChild(new Declaration(
                    new Property("background-color"),
                    new ColorLiteral("555555")
                ))
                .addChild(new Declaration(
                    new Property("color"),
                    new ColorLiteral("aaaaaa")
                ))
        )
    );
    return new Ast(stylesheet);
  }

  public static Ast uncheckedLevel1() {
    var stylesheet = new Stylesheet();
    stylesheet.addChild(new VariableAssignment(
            new VariableIdentifier("BackgroundColor"),
            new ColorLiteral("ffffff")
        ));
    stylesheet.addChild(
        new Ruleset(
            new ElementSelector("p"),
            new Body()
                .addChild(new Declaration(
                    new Property("background-color"),
                    new VariableIdentifier("BackgroundColor")
                ))
                .addChild(new VariableAssignment(
                    new VariableIdentifier("ParagraphWidth"),
                    new PixelLiteral(500)
                ))
                .addChild(new Declaration(
                    new Property("width"),
                    new VariableIdentifier("ParagraphWidth")
                ))
        )
    );
    return new Ast(stylesheet);
  }

  public static Ast uncheckedLevel2() {
    var stylesheet = new Stylesheet();
    stylesheet.addChild(new VariableAssignment(
            new VariableIdentifier("ParagraphHeight"),
            new PixelLiteral(500)
        ));
    stylesheet.addChild(
        new Ruleset(
            new ElementSelector("p"),
            new Body()
                .addChild(new Declaration(
                    new Property("height"),
                    new BinaryAddition(
                        new BinaryMultiplication(
                            new ScalarLiteral(2),
                            new VariableIdentifier("ParagraphHeight")
                        ),
                        new PixelLiteral(50)
                    )
                )
            )
        )
    );
    return new Ast(stylesheet);
  }

  public static Ast uncheckedLevel3() {
    var stylesheet = new Stylesheet();
    stylesheet.addChild(new VariableAssignment(
            new VariableIdentifier("UseColor"),
            new BooleanLiteral(true)
        ));
    stylesheet.addChild(
        new Ruleset(
            new ElementSelector("p"),
            new Body()
                .addChild(new ConditionalStatement(
                    new VariableIdentifier("UseColor"),
                    new Body()
                        .addChild(new VariableAssignment(
                            new VariableIdentifier("UseLightMode"),
                            new BooleanLiteral(false)
                        ))
                        .addChild(new ConditionalStatement(
                            new VariableIdentifier("UseLightMode"),
                            new Body()
                                .addChild(new Declaration(
                                    new Property("background-color"),
                                    new ColorLiteral("ffffff")
                                ))
                                .addChild(new Declaration(
                                    new Property("color"),
                                    new ColorLiteral("000000")
                                )),
                            new Body()
                                .addChild(new Declaration(
                                    new Property("background-color"),
                                    new ColorLiteral("000000")
                                ))
                                .addChild(new Declaration(
                                    new Property("color"),
                                    new ColorLiteral("ffffff")
                                ))
                        ))
                ))
        )
    );
    return new Ast(stylesheet);
  }

  public static Ast uncheckedLevel4() {
    var stylesheet = new Stylesheet();
    stylesheet
        .addChild(new VariableAssignment(
            new VariableIdentifier("ParagraphWidth"),
            new PercentageLiteral(100)
        ))
        .addChild(new VariableAssignment(
            new VariableIdentifier("ParagraphHeight"),
            new PixelLiteral(200)
        ));
    stylesheet.addChild(
        new Ruleset(
            new ElementSelector("p"),
            new Body()
                .addChild(new Declaration(
                    new Property("width"),
                    new VariableIdentifier("ParagraphWidth")
                ))
            .addChild(new VariableAssignment(
                new VariableIdentifier("UseColor"),
                new BooleanLiteral(true))
            ).addChild(new ConditionalStatement(
                new VariableIdentifier("UseColor"),
                new Body()
                    .addChild(new VariableAssignment(
                    new VariableIdentifier("UseLightMode"),
                    new BooleanLiteral(false)
                ))
                    .addChild(new ConditionalStatement(
                        new VariableIdentifier("UseLightMode"),
                        new Body()
                            .addChild(new Declaration(
                                new Property("background-color"),
                                new ColorLiteral("ffffff")
                            ))
                            .addChild(new Declaration(
                                new Property("color"),
                                new ColorLiteral("000000")
                            )),
                        new Body()
                            .addChild(new Declaration(
                                new Property("background-color"),
                                new ColorLiteral("000000")
                            ))
                            .addChild(new Declaration(
                                new Property("color"),
                                new ColorLiteral("ffffff")
                            ))
                    ))
            )).addChild(new Declaration(
            new Property("height"),
            new BinaryAddition(
                new BinaryMultiplication(
                    new ScalarLiteral(2),
                    new VariableIdentifier("ParagraphHeight")
                ),
                new PixelLiteral(50)
            )
        )))
    );
    stylesheet.addChild(new Ruleset(
        new IdSelector("menu"),
        new Body()
            .addChild(new Declaration(
                new Property("width"),
                new PercentageLiteral(75)
            ))
            .addChild(new Declaration(
                new Property("height"),
                new PixelLiteral(200)
            ))
    ));
    stylesheet.addChild(new Ruleset(
        new ClassSelector("menu"),
        new Body()
            .addChild(new Declaration(
                new Property("background-color"),
                new ColorLiteral("555555")
            ))
            .addChild(new Declaration(
                new Property("color"),
                new ColorLiteral("aaaaaa")
            ))
    ));
    return new Ast(stylesheet);
  }
}
