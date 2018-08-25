package product;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ItemListController {
    @FXML
    private Label listTitle;

    public void setListTitle(String title){
        this.listTitle.setText(title);
    }

}
