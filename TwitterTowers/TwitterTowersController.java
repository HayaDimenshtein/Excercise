
import java.io.IOException;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TwitterTowersController {
	

    @FXML
    void exitPressed(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void rectanglePressed(ActionEvent event) {
    	openTowerDimesionsWindow("rectangle");
    }

    @FXML
    void trianglePressed(ActionEvent event) {
    	openTowerDimesionsWindow("triangle");
    }
    
    void openTowerDimesionsWindow(String shape)
    {
    	Stage newWindow = new Stage();
    	newWindow.setTitle("TowerDimensions");
    	//Create view from FXML
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("TowerDimensions.fxml"));
    	//Set view in window
    	try {
			newWindow.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	TowerDimensionsController controller = loader.getController();
    	  controller.initData(shape);

    	//Launch
    	newWindow.show();
    }

}
