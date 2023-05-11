

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;

public class TriangleOutputController {
	
	private int hight;
	private int width;

	@FXML
	private TextArea outputTxt;
	
	@FXML
	private Button oK;
    
    @FXML
    private RadioButton perimeter;

    @FXML
    private RadioButton print;
    
    void initialize()
    {
    	ToggleGroup toggleGroup = new ToggleGroup();

    	perimeter.setToggleGroup(toggleGroup);
    	print.setToggleGroup(toggleGroup);
    	
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
    
    @FXML
    void perimeterPressed(ActionEvent event) {
    	print.setDisable(true);
    	outputTxt.setText("The triangle's perimeter is: "+(width+2*(Math.sqrt((hight*hight) + (width/2)*(width/2)))));
    	oK.setVisible(true);
    }

    @FXML
    void printPressed(ActionEvent event) {
    	perimeter.setDisable(true);
    	if(width%2==0 || width>(hight*2))
    		outputTxt.setText("Unable to print the triangle");
    	else
    		outputTxt.setText(printTriangle());
    	oK.setVisible(true);
    }

    
    String printTriangle()
    {
    	String trglToPrint="";
    	int eachWidthHight=(hight-2)/((width-3)/2);
    	int extra=(hight-2)%((width-3)/2);
    	int spaces=(width-1)/2;
    	
    	//add the 1st line 
    	trglToPrint=addSpaces(trglToPrint, spaces);
    	trglToPrint+="*";
    	trglToPrint=addSpaces(trglToPrint, spaces);
    	spaces--;
    	trglToPrint+="\n";
    	
    	//add all middle lines
    	for(int i=1; i<=(width-3)/2; i++)
    	{
    		if(i==1) 
    			for(int k=0; k<extra; k++) {
    				trglToPrint=addSpaces(trglToPrint, spaces);
    				for(int j=0; j<i+(i-1)+2; j++)
    				{
    					trglToPrint+="*";
    				}
    				trglToPrint=addSpaces(trglToPrint, spaces);
    				trglToPrint+="\n";
    			}
    	    for(int k=0; k<eachWidthHight; k++)
    	    {
    	    	trglToPrint=addSpaces(trglToPrint, spaces);
    			for(int j=0; j<i+(i-1)+2; j++)
    			{
    				trglToPrint+="*";
    			}
    			trglToPrint=addSpaces(trglToPrint, spaces);
    	    	trglToPrint+="\n";
    	    }
    	    spaces--;
    	}
    	
    	//add the last line
    	trglToPrint=addSpaces(trglToPrint, spaces);
    	for(int i=0; i<width; i++)
    	{
    		trglToPrint+="*";
    	}
    	trglToPrint=addSpaces(trglToPrint, spaces);
    	return trglToPrint;
    }
    
    String addSpaces(String str, int spaces)
    {
    	for(int i=0; i<spaces; i++)
    		str+=" ";
    	return str;
    }

}

