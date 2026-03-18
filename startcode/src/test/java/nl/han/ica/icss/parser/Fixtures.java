package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.ElementSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;

// @formatter:off
public class Fixtures {
  public static Ast uncheckedLevel0() {
    Stylesheet stylesheet = new Stylesheet();
    stylesheet.addChild((new Ruleset(new ElementSelector("p")))
        .addChild((new Declaration(new Property("background-color"), new ColorLiteral("#ffffff")))
        .addChild((new Declaration(new Property("width"), new PixelLiteral("500px"))))
    ));
    stylesheet.addChild((new Ruleset(new ElementSelector("a")))
        .addChild((new Declaration(new Property("color"), new ColorLiteral("#ff0000")))
    ));
    stylesheet.addChild((new Ruleset(new ClassSelector("#menu")))
        .addChild((new Declaration(new Property("width"), new PixelLiteral("520px")))
    ));
    stylesheet.addChild((new Ruleset(new IdSelector(".menu")))
        .addChild((new Declaration(new Property("color"), new ColorLiteral("#000000")))
    ));
    return new Ast(stylesheet);
  }

//	public static Ast uncheckedLevel1() {
//		Stylesheet stylesheet = new Stylesheet();
//		stylesheet.addChild((new VariableAssignment())
//        .addChild(new VariableReference("LinkColor"))
//        .addChild(new ColorLiteral("#ff0000"))
//    );
//    stylesheet.addChild((new VariableAssignment())
//        .addChild(new VariableReference("ParWidth"))
//        .addChild(new PixelLiteral("500px"))
//    );
//    stylesheet.addChild((new VariableAssignment())
//        .addChild(new VariableReference("AdjustColor"))
//        .addChild(new BooleanLiteral(true))
//    );
//    stylesheet.addChild((new VariableAssignment())
//        .addChild(new VariableReference("UseLinkColor"))
//        .addChild(new BooleanLiteral(false))
//    );
//    stylesheet.addChild((new Ruleset(new ElementSelector("p")))
//        .addChild((new Declaration(new Property("background-color"), new ColorLiteral("#ffffff")))
//        .addChild((new Declaration(new Property("width"), new VariableReference("ParWidth"))))
//    ));
//    stylesheet.addChild((new Ruleset(new ElementSelector("a")))
//        .addChild((new Declaration(new Property("color"), new VariableReference("LinkColor")))
//    ));
//    stylesheet.addChild((new Ruleset(new ClassSelector("#menu")))
//        .addChild((new Declaration(new Property("width"), new PixelLiteral("520px")))
//    ));
//    stylesheet.addChild((new Ruleset(new IdSelector(".menu")))
//        .addChild((new Declaration(new Property("color"), new ColorLiteral("#000000")))
//    ));
//		return new Ast(stylesheet);
//	}

//  public static Ast uncheckedLevel2() {
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
//	        background-color: #ffffff;
//	        width: ParWidth;
//            }
//	    */
//    stylesheet.addChild((new Ruleset()).addChild(new ElementSelector("p"))
//        .addChild((new Declaration("background-color")).addChild(new ColorLiteral("#ffffff")))
//        .addChild((new Declaration("width")).addChild(new VariableReference("ParWidth"))));
//        /*
//        a {
//	        color: LinkColor;
//        }
//        */
//    stylesheet.addChild((new Ruleset()).addChild(new ElementSelector("a"))
//        .addChild((new Declaration("color")).addChild(new VariableReference("LinkColor"))));
//        /*
//            #menu {
//        	width: ParWidth + 2 * 10px;
//            }
//        */
//    stylesheet.addChild((new Ruleset()).addChild(new IdSelector("#menu"))
//        .addChild((new Declaration("width")).addChild((new Addition(new VariableReference("ParWidth"), new ScalarLiteral("2")))
//            .addChild((new Multiplication()).addChild().addChild(new PixelLiteral("10px"))
//
//            ))));
//        /*
//            .menu {
//	            color: #000000;
//            }
//        */
//    stylesheet.addChild((new Ruleset()).addChild(new ClassSelector(".menu"))
//        .addChild((new Declaration("color")).addChild(new ColorLiteral("#000000"))));
//    return new Ast(stylesheet);
//  }
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
