package nl.han.ica.icss.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import nl.han.ica.icss.ast.Ast;
import nl.han.ica.icss.ast.AstNode;

public class AstPane extends BorderPane {

    private TreeView<AstNode> content;
    private Label title;

    public AstPane() {
		super();

		title = new Label("Internal (AST):");
		content = new TreeView<AstNode>();
        content.setCellFactory(treeview ->  new TreeCell<AstNode>() {
            @Override
            public void updateItem(AstNode item, boolean empty) {
                super.updateItem(item, empty);

                getStyleClass().removeAll("error");

                if(empty) {
                    setText("");
                } else {
                    setText(item.getNodeLabel());
                    if(item.hasError()) {
                        getStyleClass().add("error");
                    }
                }
            }
        });
		title.setPadding(new Insets(5, 5, 5, 5));

		setTop(title);
		setCenter(content);
	    setMinWidth(200);
        setPrefWidth(400);
	}
    /**
     * Updates the panes based on the current content of the AST
     * @param ast
     */
    public void update(Ast ast) {
        content.setRoot(astNodeToTreeItem(ast.getRoot()));
    }
    private TreeItem<AstNode> astNodeToTreeItem(AstNode astNode) {

        TreeItem<AstNode> tvNode = new TreeItem<AstNode>(astNode);
        tvNode.setExpanded(true);

        for(AstNode child : astNode.getChildren()) {
            tvNode.getChildren().add(astNodeToTreeItem(child));
        }
        return tvNode;
    }
}
