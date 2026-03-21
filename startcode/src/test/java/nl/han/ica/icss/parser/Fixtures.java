package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.expression.math.operation.BinaryAddition;
import nl.han.ica.icss.ast.expression.math.operation.BinaryMultiplication;
import nl.han.ica.icss.ast.literal.ColorLiteral;
import nl.han.ica.icss.ast.literal.numeric.PercentageLiteral;
import nl.han.ica.icss.ast.literal.numeric.PixelLiteral;
import nl.han.ica.icss.ast.literal.numeric.ScalarLiteral;
import nl.han.ica.icss.ast.selector.ClassSelector;
import nl.han.ica.icss.ast.selector.ElementSelector;
import nl.han.ica.icss.ast.selector.IdSelector;
import nl.han.ica.icss.ast.variable.VariableAssignment;
import nl.han.ica.icss.ast.variable.VariableIdentifier;

// @formatter:off
public class Fixtures {
  public static Ast uncheckedLevel0() {
    var stylesheet = new Stylesheet();
    stylesheet.addChild(new Ruleset()
        .addChild(new ElementSelector("p"))
        .addChild(new Declaration()
            .addChild(new Property("background-color"))
            .addChild(new ColorLiteral("#ffffff"))
        ).addChild(new Declaration()
            .addChild(new Property("color"))
            .addChild(new ColorLiteral("#000000"))
        )
    ).addChild(new Ruleset()
        .addChild(new IdSelector("#menu"))
        .addChild(new Declaration()
            .addChild(new Property("width"))
            .addChild(new PixelLiteral("500px"))
        )
    ).addChild(new Ruleset()
        .addChild(new ClassSelector(".menu"))
        .addChild(new Declaration()
            .addChild(new Property("height"))
            .addChild(new PercentageLiteral("100%"))
        )
    );
    return new Ast(stylesheet);
  }

	public static Ast uncheckedLevel1() {
		var stylesheet = new Stylesheet();
    stylesheet.addChild(new VariableAssignment()
        .addChild(new VariableIdentifier("BackgroundColor"))
        .addChild(new ColorLiteral("#ffffff"))
    ).addChild(new Ruleset()
        .addChild(new ElementSelector("p"))
        .addChild(new VariableAssignment()
            .addChild(new VariableIdentifier("ParagraphWidth"))
            .addChild(new PixelLiteral("500px"))
        ).addChild(new Declaration()
            .addChild(new Property("background-color"))
            .addChild(new VariableIdentifier("BackgroundColor"))
        ).addChild(new Declaration()
            .addChild(new Property("width"))
            .addChild(new VariableIdentifier("ParagraphWidth"))
        )
    );
		return new Ast(stylesheet);
	}

  public static Ast uncheckedLevel2() {
    Stylesheet stylesheet = new Stylesheet();
    stylesheet.addChild(new Ruleset()
        .addChild(new ElementSelector("p"))
        .addChild(new VariableAssignment()
            .addChild(new VariableIdentifier("ParagraphWidth"))
            .addChild(new PixelLiteral("500px"))
        )
        .addChild(new Declaration()
            .addChild(new Property("width"))
            .addChild(new BinaryAddition(
                new VariableIdentifier("ParagraphWidth"),
                new BinaryMultiplication(
                    new ScalarLiteral("2"),
                    new PixelLiteral("10px")
                )
            ))
        )
    );
    return new Ast(stylesheet);
  }
//
//  public static Ast uncheckedLevel3() {
//    Stylesheet stylesheet = new Stylesheet();
//		/*
//			LinkColor := #ff0000;
//			ParWidth := 500px;
//			AdjustColor := TRUE;
//			UseLinkColor := FALSE;
//		 */
//    stylesheet.addChild((new VariableAssignment()).addChild(new VariableReference("LinkColor"))
//        .addChild(new ColorLiteral("#ff0000")));
//    stylesheet.addChild((new VariableAssignment()).addChild(new VariableReference("ParWidth"))
//        .addChild(new PixelLiteral("500px")));
//    stylesheet.addChild((new VariableAssignment()).addChild(new VariableReference("AdjustColor"))
//        .addChild(new BooleanLiteral(true)));
//    stylesheet.addChild((new VariableAssignment()).addChild(new VariableReference("UseLinkColor"))
//        .addChild(new BooleanLiteral(false)));
//   	    /*
//	        p {
//				background-color: #ffffff;
//				width: ParWidth;
//				if[AdjustColor] {
//	    			color: #124532;
//	    			if[UseLinkColor]{
//	        			bg-color: LinkColor;
//	    			}
//				}
//			}
//			p {
//				background-color: #ffffff;
//				width: ParWidth;
//				if[AdjustColor] {
//	    			color: #124532;
//	    		if[UseLinkColor]{
//	        		background-color: LinkColor;
//	    		} else {
//	        		background-color: #000000;
//	    		}
//	    		height: 20px;
//			}
//}
//	    */
//    stylesheet.addChild((new Ruleset()).addChild(new ElementSelector("p"))
//        .addChild((new Declaration("background-color")).addChild(new ColorLiteral("#ffffff")))
//        .addChild((new Declaration("width")).addChild(new VariableReference("ParWidth")))
//        .addChild((new ConditionalIf()).addChild(new VariableReference("AdjustColor"))
//            .addChild((
//                new Declaration("color").addChild(new ColorLiteral("#124532"))
//            ))
//            .addChild((new ConditionalIf()).addChild(new VariableReference("UseLinkColor"))
//                .addChild(new Declaration("background-color").addChild(new VariableReference("LinkColor")))
//                .addChild((new ConditionalElse()).addChild(new Declaration("background-color").addChild(new ColorLiteral(
//                    "#000000")))
//
//                )))
//        .addChild((new Declaration("height")).addChild(new PixelLiteral("20px"))));
//        /*
//        a {
//	        color: LinkColor;
//        }
//        */
//    stylesheet.addChild((new Ruleset()).addChild(new ElementSelector("a"))
//        .addChild((new Declaration("color")).addChild(new VariableReference("LinkColor"))));
//        /*
//            #menu {
//        	width: ParWidth + 20px;
//            }
//        */
//    stylesheet.addChild((new Ruleset()).addChild(new IdSelector("#menu"))
//        .addChild((new Declaration("width")).addChild((new Addition()).addChild(new VariableReference("ParWidth"))
//            .addChild(new PixelLiteral("20px")))));
//        /*
//
//
//         .menu {
//				color: #000000;
//    			background-color: LinkColor;
//
//			}
//
//        */
//    stylesheet.addChild((new Ruleset()).addChild(new ClassSelector(".menu"))
//
//        .addChild((new Declaration("color")).addChild(new ColorLiteral("#000000")))
//        .addChild((new Declaration("background-color")).addChild(new VariableReference("LinkColor")))
//
//    );
//
//    return new Ast(stylesheet);
//  }
}
