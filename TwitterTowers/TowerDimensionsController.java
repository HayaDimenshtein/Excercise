
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TowerDimensionsController {
	private String shape;

    @FXML
    private TextField hightText;

    @FXML
    private TextField widthText;
    
    void initData(String shape) {
        this.shape=shape;
      }

    @FXML
    void submitPressed(ActionEvent event) {
    	if(shape.equals("rectangle"))
    		openRectangleOutputStage("rectangle", event);
    	else
    		openTriangleOutputStage("triangle", event);
    	
    }
    
    void openRectangleOutputStage(String shape, ActionEvent event)
    {
    	final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close(); 
        Stage newWindow = new Stage();
    	newWindow.setTitle("RectangleOutput");
    	//Create view from FXML
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("RectangleOutput.fxml"));
    	//Set view in window
    	try {
			newWindow.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	RectangleOutputController controller = loader.getController();
    	controller.initialize();
    	controller.initData(Integer.parseInt(hightText.getText()),Integer.parseInt(widthText.getText()));
    	//Launch
    	newWindow.show();
    }
    
    void openTriangleOutputStage(String shape, ActionEvent event)
    {
    	final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close(); 
        Stage newWindow = new Stage();
    	newWindow.setTitle("TriangleOutput");
    	//Create view from FXML
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("TriangleOutput.fxml"));
    	//Set view in window
    	try {
			newWindow.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	TriangleOutputController controller = loader.getController();
    	controller.initialize();
    	controller.initData(Integer.parseInt(hightText.getText()),Integer.parseInt(widthText.getText()));
    	//Launch
    	newWindow.show();
    }

    }


