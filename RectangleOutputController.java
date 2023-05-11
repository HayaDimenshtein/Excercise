
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RectangleOutputController {
	
	private int hight;
	private int width;

	@FXML
    private Label outputText;
    
    void initialize()
    {
    	if(Math.abs(hight-width)>5)
    		outputText.setText("The rectangle's area is: " +hight*width);
    	else
    		outputText.setText("The rectangle's perimeter is: "+ ((hight*2)+(width*2)));
    
    }
    
    void initData(int hight, int width) {
        this.hight=hight;
        this.width=width;
      }

    @FXML
    void okPressed(ActionEvent event) {
    	final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
    void printOutput()
    {
    	if(Math.abs(hight-width)>5)
    		outputText.setText("The rectangle's area is: " +hight*width);
    	else
    		outputText.setText("The rectangle's perimeter is: "+ ((hight*2)+(width*2)));
    }

}
