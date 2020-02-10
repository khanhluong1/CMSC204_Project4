import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * User interface supports functions to create concordance data from input text or input file.
 * 
 * @author Derek Luong
 *
 */
public class ConcordanceDataManagerGui extends Application {
	private ConcordanceDataManagerInterface concordanceManager;
	private File inputFile;
	private File outputFile;

	@Override
	public void start(Stage stage) throws Exception {
		concordanceManager = new ConcordanceDataManager();
		
		VBox mainPane = new VBox(10);
		
		// option pane
		GridPane optionPane = new GridPane();
		optionPane.setHgap(5);
		optionPane.setVgap(5);
		
		ToggleGroup optionGroup = new ToggleGroup();
		RadioButton createFromFileRdo = new RadioButton("Create concordance from file");
		createFromFileRdo.setToggleGroup(optionGroup);
		createFromFileRdo.setSelected(true);
		optionPane.add(createFromFileRdo, 1, 0);

		RadioButton createFromTextRdo = new RadioButton("Create concordance from text");
		createFromTextRdo.setToggleGroup(optionGroup);
		optionPane.add(createFromTextRdo, 2, 0);
		
		TitledPane optionTitle = new TitledPane("Please select from following options:",
				optionPane);
		optionTitle.setCollapsible(false);
		mainPane.getChildren().add(optionTitle);
		
		// text pane
		TextArea inputTxt = new TextArea();
		inputTxt.setPrefRowCount(15);
		inputTxt.setPrefColumnCount(100);
		inputTxt.setWrapText(true);
		inputTxt.setPrefWidth(150);
	    
		TitledPane inputTitle = new TitledPane("Enter text:",
				inputTxt);
		inputTitle.setCollapsible(false);
		//inputTitle.setVisible(false);
		mainPane.getChildren().add(inputTitle);
		
		// button pane
		GridPane buttonPane = new GridPane();
		buttonPane.setHgap(5);
		buttonPane.setVgap(5);
		
		// button create concordance from text
		Button createConcordanceBtn = new Button("Create _Concordance");
		createConcordanceBtn.setTooltip(new Tooltip("Create concordance from text."));
		createConcordanceBtn.setMnemonicParsing(true);
		createConcordanceBtn.setDisable(true);
		buttonPane.add(createConcordanceBtn, 1, 0);
		
		// button select input file
		Button selectInputBtn = new Button("Select _Input File");
		selectInputBtn.setTooltip(new Tooltip("Select file containing text to create concordance."));
		selectInputBtn.setMnemonicParsing(true);
		selectInputBtn.setDisable(false);
		buttonPane.add(selectInputBtn, 2, 0);
		
		// button select output file
		Button selectOutputBtn = new Button("Select _Output File");
		selectOutputBtn.setTooltip(new Tooltip("Select file to export result."));
		selectOutputBtn.setMnemonicParsing(true);
		selectOutputBtn.setDisable(true);
		buttonPane.add(selectOutputBtn, 3, 0);
		
		// button clear
		Button clearBtn = new Button("Cle_ar");
		clearBtn.setTooltip(new Tooltip("Reset form."));
		clearBtn.setMnemonicParsing(true);
		buttonPane.add(clearBtn, 4, 0);
		
		// button clear
		Button exitBtn = new Button("Ex_it");
		exitBtn.setTooltip(new Tooltip("Exit program."));
		exitBtn.setMnemonicParsing(true);
		buttonPane.add(exitBtn, 5, 0);
		mainPane.getChildren().add(buttonPane);
		
		createConcordanceBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
            public void handle(ActionEvent e) {
    			String input = inputTxt.getText();
    			if (input == null || input.length() == 0) {
    				showAlertMessage(AlertType.ERROR, "Create Concerdance Error", "Please enter a text");
    			} else {
	    			ArrayList<String> words = concordanceManager.createConcordanceArray(input);
	    			String result = "";
	    			for(int i=0; i<words.size(); i++) {
	    				result += words.get(i) + "\n";
	    			}
	    			inputTxt.setText(result);
    			}
    		}
        });
		
		selectInputBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
            public void handle(ActionEvent e) {
    			FileChooser fileChooser = new FileChooser();
            	fileChooser.setTitle("Select Input File");
            	inputFile = fileChooser.showOpenDialog(stage);
                if (inputFile != null) {
                	try {
                		String inputText = "";
						// FileReader reads text files in the default encoding.
						FileReader fileReader = new FileReader(inputFile);
						// Always wrap FileReader in BufferedReader.
	                    BufferedReader bufferedReader = new BufferedReader(fileReader);

	                    String line = null;
	                    while((line = bufferedReader.readLine()) != null) {
	                    	inputText += line + "\n";
	                    }   

	                    // Always close files.
	                    bufferedReader.close();
	                    fileReader.close();
	                    inputTxt.setText(inputText);
	                    selectOutputBtn.setDisable(false);
					} catch (FileNotFoundException e1) {
						showAlertMessage(AlertType.ERROR, "Reading input file", e1.getMessage());
					} catch (IOException e1) {
						showAlertMessage(AlertType.ERROR, "Reading input file", e1.getMessage());
					}
                }
    		}
        });
		
		selectOutputBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
            public void handle(ActionEvent e) {
    			FileChooser fileChooser = new FileChooser();
            	fileChooser.setTitle("Select Input File");
            	outputFile = fileChooser.showOpenDialog(stage);
                if (outputFile != null) {
					try {
						boolean isSuccessful = concordanceManager.createConcordanceFile(inputFile, outputFile);
						if (isSuccessful) {
							try {
		                		String outText = "";
								// FileReader reads text files in the default encoding.
								FileReader fileReader = new FileReader(outputFile);
								// Always wrap FileReader in BufferedReader.
			                    BufferedReader bufferedReader = new BufferedReader(fileReader);

			                    String line = null;
			                    while((line = bufferedReader.readLine()) != null) {
			                    	outText += line + "\n";
			                    }   

			                    // Always close files.
			                    bufferedReader.close();
			                    fileReader.close();
			                    inputTxt.setText(outText);
			                    selectOutputBtn.setDisable(true);
							} catch (FileNotFoundException e1) {
								showAlertMessage(AlertType.ERROR, "Writing output file", e1.getMessage());
							} catch (IOException e1) {
								showAlertMessage(AlertType.ERROR, "Writing output file", e1.getMessage());
							}
	                	}
					} catch (FileNotFoundException e1) {
						showAlertMessage(AlertType.ERROR, "Create Concerdance Error", e1.getMessage());
					}
                	
                }
    		}
        });
		
		createFromFileRdo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent e) {
				//inputTitle.setVisible(false);
				createConcordanceBtn.setDisable(true);
				selectInputBtn.setDisable(false);
				selectOutputBtn.setDisable(true);
				inputTxt.setText("");
			}
		});
		
		createFromTextRdo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent e) {
				//inputTitle.setVisible(true);
				createConcordanceBtn.setDisable(false);
				selectInputBtn.setDisable(true);
				selectOutputBtn.setDisable(true);
				inputTxt.setText("");
			}
		});
		
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
            public void handle(ActionEvent e) {
    			createFromFileRdo.setSelected(true);
    			//inputTitle.setVisible(false);
    			inputTxt.setText("");
				createConcordanceBtn.setDisable(true);
				selectInputBtn.setDisable(false);
				selectOutputBtn.setDisable(true);
    		}
        });
		
		exitBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
            public void handle(ActionEvent e) {
    			Platform.exit();
             	System.exit(0);
    		}
        });
		
		Scene scene = new Scene(mainPane, 500, 400);
		stage.setScene(scene);
		// Set stage title and show the stage.
		stage.setTitle("Concordance Generator");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void showAlertMessage(AlertType alertType, String title, String content) {
    	Alert alert = new Alert(alertType);
    	alert.setResizable(true);
    	alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
    }
}
