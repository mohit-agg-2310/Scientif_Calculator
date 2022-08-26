
package calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

import calculator.BusinessLogic;

import lexer.Lexer;
import lexer.Token;
import lexer.Token.Kind;
import expressionTreeBuilderEvaluator.ExpressionTreeBuilderEvaluator;
import expressionTreeBuilderEvaluator.ExprNode;

/**
 * <p> Title: UserInterface Class. </p>
 * date 10 September 2020
 * <p> Description: The Java/FX-based user interface for the calculator. The class works with String
 * objects and passes work to other classes to deal with all other aspects of the computation.</p>
 * 
 *  <p> Copyright: Lynn Robert Carter, MD.Ashfak Â© 2020 </p>
 * 
 ** @author Lynn Robert Carter, MD.Ashfak
 
 * @version 4.0.1	2020-01-26 The JavaFX-based GUI for the implementation of a  integer calculator
 * @version 4.0.2	2020-02-16 The JavaFX-based GUI for the implementation of a double calculator with Square root  
 * @version 4.0.3	2020-03-30 The JavaFX-based GUI for the implementation of a double calculator with FSM
 * @version 4.0.4	2020-04-24 The JavaFX-based GUI for the implementation of a double calculator with ErrorTerm 
 * @version 4.0.5	2020-09-10 The JavaFX-based GUI for the implementation of a UNumber calculator with ErrorTerm and Square root using Newton Raphson Method 
 * @version 4.0.6	2020-11-28 The JavaFX-based GUI for the implementation of a UNumber calculator with Units 
 */

public class UserInterface {
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	/* Constants used to parameterize the graphical user interface.  We do not use a layout manager for
	   this application. Rather we manually control the location of each graphical element for exact
	   control of the look and feel. */
	private final double BUTTON_WIDTH = 30;
	private final double BUTTON_HEIGHT = 30;
	private final double BUTTON_OFFSET = BUTTON_WIDTH / 3;
	
	private Label label_DoubleCalculator = new Label("Science and Engineering Calculator");
	private Label label_expression = new Label("Expression");
	private TextField text_expression = new TextField();
	private Label label_results = new Label("Result");
	private Button button_solve = new Button("Solve");
	private TextField text_result = new TextField();
	private Button button_data = new Button("Data");
	public ExpressionTreeBuilderEvaluator eTree = new ExpressionTreeBuilderEvaluator();
	private static Lexer lexer;
	private static Token current;
	private static Token next;

	// These are the application values required by the user interface
	
	private Label label_Operand1 = new Label("First operand");
	private Label label_Unit = new Label("Units");
	private Label label_symbol1 = new Label("\u00B1");
	private Label label_symbol2 = new Label("±");
	private Label label_symbol3 = new Label("±");
	private TextField text_Operand1 = new TextField();
	//private Label label_Operand1errorterm=new Label("Error term");
	private TextField text_Operand1errorterm = new TextField();
	private Label label_Operand2 = new Label("Second operand");
	private TextField text_Operand2 = new TextField();
	//private Label label_Operand2errorterm=new Label("Error term");
	private TextField text_Operand2errorterm = new TextField();
	private Label label_Result = new Label("Result");
	private TextField text_Resulterrorterm = new TextField();
	private TextField text_Result = new TextField();
	private Button button_Add = new Button("+");
	private Button button_Sub = new Button("-");
	private Button button_Mpy = new Button("X");				
	private Button button_Div = new Button("\u00F7");				
	private Button button_sqrt = new Button("\u221A");
	private Label label_errOperand1 = new Label("");
	private Label label_errOperand2 = new Label("");
	private Label label_errOperand1errorterm = new Label("");
	private Label label_errOperand2errorterm = new Label("");
	private Label label_Resulterrorterm = new Label("");
	private Label label_errResult = new Label("");
	private TextFlow errMeasuredValue1;
    private Text errMVPart1 = new Text();
    private Text errMVPart2 = new Text();
    private TextFlow errMeasuredValue2;
    private Text errMVPart3 = new Text();
    private Text errMVPart4 = new Text();
    private TextFlow errerrorterm1;
    private TextFlow errerrorterm2;
    private Text errETPart1 = new Text();
    private Text errETPart2 = new Text();
    private Text errETPart3 = new Text();
    private Text errETPart4 = new Text();
    private ComboBox <String> operand1unit = new ComboBox<String>();
	private List <String> operand1unitlist = new Vector <String>();
	private ComboBox <String> operand2unit = new ComboBox<String>();
	private List <String> operand2unitlist = new Vector <String>();
	private ComboBox <String> resultunit = new ComboBox<String>();
	private List <String> resultunitlist = new Vector <String>();
	
	private ComboBox  varlist1 = new ComboBox();
	private ComboBox  varlist2 = new ComboBox();
	
	private List <Variable> list = new Vector <Variable>();
	private List <Variable> editlist = new Vector <Variable>();
	private Tab layout = new Tab();
	private TableView<Variable> table = new TableView<Variable>(); ;
	private ObservableList data;
	
    
	// If the multiplication and/or division symbols do not display properly, replace the 
	// quoted strings used in the new Button constructor call with the <backslash>u00xx values
	// shown on the same line. This is the Unicode  representation of those characters and will
	// work regardless of the underlying hardware being used.
	
	private double buttonSpace;		// This is the white space between the operator buttons.
	
	/* This is the link to the business logic */
	public BusinessLogic perform = new BusinessLogic();

	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the graphical user interface. These assignments
	 * determine the location, size, font, color, and change and event handlers for each GUI object.
	 *
	 *@param theRoot  the variable which will set all the UI components
	 */
	public UserInterface(Pane theRoot) {
				
		// There are five gaps. Compute the button space accordingly.
		buttonSpace = Calculator.WINDOW_WIDTH / 2;
		
		
				
		// Label theScene with the name of the calculator, centered at the top of the pane
		setupLabelUI(label_DoubleCalculator, "Arial", 24, Calculator.WINDOW_WIDTH, Pos.CENTER, 0, 10);
		
		setupLabelUI(label_expression, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 75);
		
		setupTextUI(text_expression, "Arial", 18, Calculator.WINDOW_WIDTH-100, Pos.BASELINE_LEFT, 100, 70, true);
		//text_Operand1.textProperty().addListener((observable, oldValue, newValue) -> {setOperand1(); });
		text_expression.setStyle("-fx-text-fill:black;");
		
		setupButtonUI(button_solve, "Symbol", 32, BUTTON_WIDTH,BUTTON_HEIGHT, Pos.BASELINE_LEFT, 150 , 120);
		button_solve.setStyle("-fx-text-fill:black;");
		button_solve.setOnAction((event) -> { compute(text_expression.getText()); });
		
		setupButtonUI(button_data, "Symbol", 32, BUTTON_WIDTH,BUTTON_HEIGHT, Pos.BASELINE_LEFT, 300, 120);
		button_sqrt.setStyle("-fx-text-fill:black;");
		button_data.setOnAction((event) -> {  datapopup(); });
		
		setupLabelUI(label_results, "Arial", 18, Calculator.WINDOW_WIDTH/3, Pos.BASELINE_LEFT, 10, 200);
		
		setupTextUI(text_result, "Arial", 18, Calculator.WINDOW_WIDTH-100, Pos.BASELINE_LEFT, 100, 195, true);
		//text_Operand1.textProperty().addListener((observable, oldValue, newValue) -> {setOperand1(); });
		text_result.setStyle("-fx-text-fill:black;");
		//text_result.setEditable(false);
		//------------------------------------------------------------------------//
		
		setupLabelUI(label_Unit, "Arial", 18, Calculator.WINDOW_WIDTH/3, Pos.BASELINE_LEFT,  Calculator.WINDOW_WIDTH-100, 40);
			
		// Label the first operand just above it, left aligned
		setupLabelUI(label_Operand1, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 75);
		
		setupLabelUI(label_symbol1, "Arial", 30, 150,  Pos.BASELINE_LEFT, Calculator.WINDOW_WIDTH/2 + 110,  65);
				
		setupLabelUI(label_symbol2, "Arial", 30, 150, Pos.BASELINE_LEFT, Calculator.WINDOW_WIDTH/2 + 110,  165);
				
		setupLabelUI(label_symbol3, "Arial", 30,150,  Pos.BASELINE_LEFT, Calculator.WINDOW_WIDTH/2 + 110,  285);
		
		
		// Establish the first text input operand field and when anything changes in operand 1,
		// process both fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand1, "Arial", 18, Calculator.WINDOW_WIDTH/3, Pos.BASELINE_LEFT, 250, 70, true);
		text_Operand1.textProperty().addListener((observable, oldValue, newValue) -> {setOperand1(); });
		text_Operand1.setStyle("-fx-text-fill:black;");
		// Move focus to the second operand when the user presses the enter (return) key
		text_Operand1.setOnAction((event) -> { text_Operand2.requestFocus(); });
		text_Operand1.textProperty().addListener((observable, oldValue, newValue) -> {activsqrtButton(); });		
		
		// Establish an error message for the first operand just above it with, left aligned
		label_errOperand1.setTextFill(Color.RED);
		label_errOperand1.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand1, "Arial", 14, Calculator.WINDOW_WIDTH/3+100, Pos.BASELINE_LEFT, 150, 126);
		
		setupLabelUI(label_Operand2, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 10, 175);
				
		// Establish the Error Term textfield for the first operand.  If anything changes, process
		// all fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand1errorterm, "Arial", 18, 150, Pos.BASELINE_LEFT, Calculator.WINDOW_WIDTH/2+140, 70, true);
		text_Operand1errorterm.textProperty().addListener((observable, oldValue, newValue) 
				-> {setOperand1(); });
		
		// Establish an error message for the first operand Error Term, left aligned
		label_errOperand1errorterm.setTextFill(Color.RED);
		label_errOperand1errorterm.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand1errorterm, "Arial", 14, Calculator.WINDOW_WIDTH-10-10, Pos.BASELINE_LEFT, 
					    Calculator.WINDOW_WIDTH/2- 150, 126);
		
				
		
		// Establish the second text input operand field and when anything changes in operand 2,
		// process both fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Operand2, "Arial", 18, Calculator.WINDOW_WIDTH/3 , Pos.BASELINE_LEFT, 250, 170, true);
		text_Operand2.textProperty().addListener((observable, oldValue, newValue) -> {setOperand2(); });
		text_Operand2.setStyle("-fx-text-fill:black;");
		// Move the focus to the result when the user presses the enter (return) key
		text_Operand2.setOnAction((event) -> { text_Result.requestFocus(); });
		text_Operand2.textProperty().addListener((observable, oldValue, newValue) -> {activateButton(); });	
		// Establish an error message for the second operand just above it with, left aligned
		label_errOperand2.setTextFill(Color.RED);
		label_errOperand2.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand2, "Arial", 14, Calculator.WINDOW_WIDTH/150-10, Pos.BASELINE_LEFT, 150, 220);
		
		// Establish the Error Term textfield for the second operand.  If anything changes, process
				// all fields to ensure that we are ready to perform as soon as possible.
				setupTextUI(text_Operand2errorterm, "Arial", 18, 150, Pos.BASELINE_LEFT, Calculator.WINDOW_WIDTH/2+140, 170, true);
				text_Operand2errorterm.textProperty().addListener((observable, oldValue, newValue) 
						-> {setOperand2(); });
				
		// Establish an error message for the first operand Error Term, left aligned
		label_errOperand2errorterm.setTextFill(Color.RED);
		label_errOperand2errorterm.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand2errorterm, "Arial", 14, Calculator.WINDOW_WIDTH-10-10, Pos.BASELINE_LEFT, 
		 Calculator.WINDOW_WIDTH/2- 150, 226);
				
		// Label the result just above the result output field, left aligned
		setupLabelUI(label_Result, "Arial", 18, Calculator.WINDOW_WIDTH-10, Pos.BASELINE_LEFT, 20, 355);
				
		// Establish the result output field.  It is not editable, so the text can be selected and copied, 
		// but it cannot be altered by the user.  The text is left aligned.
		setupTextUI(text_Result, "Arial", 18, Calculator.WINDOW_WIDTH/1.5, Pos.BASELINE_LEFT, 150, 350, false);
		text_Result.setStyle("-fx-text-fill:black;");
				
		// Establish an error message for the second operand just above it with, left aligned
		setupLabelUI(label_errResult, "Arial", 18, Calculator.WINDOW_WIDTH-20, Pos.BASELINE_LEFT, 400, 205);
		label_errResult.setTextFill(Color.RED);
				
		// Establish the Error Term textfield for the second operand.  If anything changes, process
		// all fields to ensure that we are ready to perform as soon as possible.
		setupTextUI(text_Resulterrorterm, "Arial", 18, 150, Pos.BASELINE_LEFT, Calculator.WINDOW_WIDTH/1.5, 290, true);
		// text_Resulterrorterm.textProperty().addListener((observable, oldValue, newValue) 
//				-> {setOperand1(); });
		
		// Establish an error message for the first operand Error Term, left aligned
				//label_Resulterrorterm.setTextFill(Color.RED);
			//	label_Resulterrorterm.setAlignment(Pos.BASELINE_RIGHT);
			//	setupLabelUI(label_Resulterrorterm, "Arial", 14, Calculator.WINDOW_WIDTH-10-10, Pos.BASELINE_LEFT, 
			//				    Calculator.WINDOW_WIDTH/2- 150, 126);
		
		// Establish the ADD "+" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Add, "Symbol", 32, BUTTON_WIDTH,BUTTON_HEIGHT, Pos.BASELINE_LEFT, 1* buttonSpace-BUTTON_OFFSET-60 , 250);
		button_Add.setStyle("-fx-text-fill:black;");
		button_Add.setOnAction((event) -> { addOperands(); });
				
		// Establish the SUB "-" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Sub, "Symbol", 32, BUTTON_WIDTH,BUTTON_HEIGHT, Pos.BASELINE_LEFT, 2 * buttonSpace-BUTTON_OFFSET-40, 250);
		button_Sub.setStyle("-fx-text-fill:black;");
		button_Sub.setOnAction((event) -> { subOperands(); });
				
		// Establish the MPY "x" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Mpy, "Symbol", 32, BUTTON_WIDTH,BUTTON_HEIGHT, Pos.BASELINE_LEFT, 3 * buttonSpace-BUTTON_OFFSET-40, 250);
		button_Mpy.setStyle("-fx-text-fill:black;");
		button_Mpy.setOnAction((event) -> { mpyOperands(); });
				
		// Establish the DIV "/" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_Div, "Symbol", 32, BUTTON_WIDTH,BUTTON_HEIGHT, Pos.BASELINE_LEFT, 4 * buttonSpace-BUTTON_OFFSET-40, 250);
		button_Div.setStyle("-fx-text-fill:black;");
		button_Div.setOnAction((event) -> { divOperands(); });
				
		// Establish the square root "sqrt" button, position it, and link it to methods to accomplish its work
		setupButtonUI(button_sqrt, "Symbol", 32, BUTTON_WIDTH,BUTTON_HEIGHT, Pos.BASELINE_LEFT, 5 * buttonSpace-BUTTON_OFFSET-40, 250);
		button_sqrt.setStyle("-fx-text-fill:black;");
		button_sqrt.setOnAction((event) -> { sqrtOperands(); });
		
		/*setupButtonUI(button_data, "Symbol", 32, BUTTON_WIDTH,BUTTON_HEIGHT, Pos.BASELINE_LEFT, 6 * buttonSpace-BUTTON_OFFSET-60, 250);
		button_sqrt.setStyle("-fx-text-fill:black;");
		button_data.setOnAction((event) -> {  datapopup(); });*/
				
		button_Add.setDisable(true);
		button_Sub.setDisable(true);
		button_Mpy.setDisable(true);
		button_Div.setDisable(true);
		button_sqrt.setDisable(true);
		
		// Error Message for the Measured Value for operand 1 
		errMVPart1.setFill(Color.BLACK);
		errMVPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		errMVPart2.setFill(Color.RED);
		errMVPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		errMeasuredValue1 = new TextFlow(errMVPart1, errMVPart2);
		errMeasuredValue1.setMinWidth(Calculator.WINDOW_WIDTH-10); 
		errMeasuredValue1.setLayoutX(150);  
		errMeasuredValue1.setLayoutY(100);
		
		// Error Message for the Error Term for operand 1
	    errETPart1.setFill(Color.BLACK);
	    errETPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    errETPart2.setFill(Color.RED);
	    errETPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
	    errerrorterm1 = new TextFlow(errETPart1, errETPart2);
		// Establish an error message for the first operand just above it with, left aligned
		errerrorterm1.setMinWidth(Calculator.WINDOW_WIDTH-10); 
		errerrorterm1.setLayoutX(Calculator.WINDOW_WIDTH/2+150);  
		errerrorterm1.setLayoutY(100);
		
		// Error Message for the Measured Value for operand 2
		errMVPart3.setFill(Color.BLACK);
		errMVPart3.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		errMVPart4.setFill(Color.RED);
		errMVPart4.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		errMeasuredValue2 = new TextFlow(errMVPart3, errMVPart4);
		errMeasuredValue2.setMinWidth(Calculator.WINDOW_WIDTH-10); 
		errMeasuredValue2.setLayoutX(150);  
		errMeasuredValue2.setLayoutY(200);		
		
		// Error Message for the Error Term for operand 2
	    errETPart3.setFill(Color.BLACK);
	    errETPart3.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
	    errETPart4.setFill(Color.RED);
	    errETPart4.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
	    errerrorterm2 = new TextFlow(errETPart3, errETPart4);
		// Establish an error message for the first operand just above it with, left aligned
		errerrorterm2.setMinWidth(Calculator.WINDOW_WIDTH-10); 
		errerrorterm2.setLayoutX(Calculator.WINDOW_WIDTH/2 +150);  
		errerrorterm2.setLayoutY(200);
		
		varlist1.setLayoutX(150);
		varlist1.setLayoutY(70);
		varlist1.setMaxWidth(90);
		varlist1.setMinWidth(90);
		varlist1.setMinHeight(33);
		varlist1.getItems().clear();
		list.clear();
		list = getInitialVariableData();
		for(int ndx = 0; ndx <list.size(); ndx++ ) {
			varlist1.getItems().add(list.get(ndx).getVariable());}	
		varlist1.getSelectionModel().selectFirst();
		varlist1.setOnAction((event)-> { loadTheDataForOp1();});
		
		varlist2.setLayoutX(150);
		varlist2.setLayoutY(170);
		varlist2.setMaxWidth(90);
		varlist2.setMinWidth(90);
		varlist2.setMinHeight(33);
		varlist2.getItems().clear();
		for(int ndx = 0; ndx <list.size(); ndx++ ) {
			varlist2.getItems().add(list.get(ndx).getVariable());}	
		varlist2.getSelectionModel().selectFirst();
		varlist2.setOnAction((event)-> { loadTheDataForOp2();});
		
		// Establish the comboBox for operand 1 units	
		operand1unit.setLayoutX(752);
		operand1unit.setLayoutY(70);
		operand1unit.setMinWidth(160);
		operand1unit.setMinHeight(33);
		String unit;
		Scanner unitScanner;
		try {
			unitScanner = new Scanner(new File("Repository/Unit.txt"));
		//Fetch all the units and put it in to the list
		while(unitScanner.hasNextLine()) {
			unit = unitScanner.nextLine();
			operand1unitlist.add(unit);
		}} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Set up the comboBox by putting all the units from the list and select one of the items 
		operand1unit.getItems().clear();
		for(int ndx = 0; ndx <operand1unitlist.size(); ndx++ ) {
			operand1unit.getItems().add(operand1unitlist.get(ndx));}	
		operand1unit.getSelectionModel().selectFirst();
		operand1unit.setOnAction((event)-> {           // link the dropdown to adjust the units of  result's comboBox
		
			resultunitlist.clear();             //clear the result's comboBox
			loadTheUnitForResult();             // Load the possible units for the result's comboBox 
			loadTheUnitForResult1();
			loadTheUnitForResult2();
		});
		// Establish the comboBox for operand 2 units
		operand2unit.setLayoutX(752);
		operand2unit.setLayoutY(170);
		operand2unit.setMinWidth(160);
		operand2unit.setMinHeight(33);
		try {
			loadTheUnitForOperand2();         //Fetch all the units and put it in to the list
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		operand2unit.getSelectionModel().selectFirst();
		operand2unit.setOnAction((event)-> {            // link the dropdown to adjust the units of  result's comboBox
			
				resultunitlist.clear();             //clear the result's comboBox
				loadTheUnitForResult(); 
				loadTheUnitForResult1();            // Load the possible units for the result's comboBox 
				loadTheUnitForResult2();
			});
		// Establish the comboBox for result units
		resultunit.setLayoutX(757);
		resultunit.setLayoutY(350);
		resultunit.setMinWidth(160);
		resultunit.setMinHeight(33);
		resultunit.getItems().add("No Unit Selected");
		resultunit.getSelectionModel().selectFirst();
		
		// Place all of the just-initialized GUI elements into the pane
		/*theRoot.getChildren().addAll(label_DoubleCalculator, label_Operand1, text_Operand1, label_errOperand1, label_symbol1, label_symbol2,
				label_Operand2, text_Operand2, label_errOperand2, label_Result, text_Result, label_errResult, label_errOperand1errorterm,
				label_errOperand2errorterm, label_Resulterrorterm, text_Operand1errorterm ,text_Operand2errorterm, button_Add, button_Sub,
				errerrorterm1, errerrorterm2, button_Mpy, button_Div, button_sqrt, errMeasuredValue1, errMeasuredValue2,operand1unit,operand2unit
				,resultunit,label_Unit,button_data,varlist1,varlist2);*/
		theRoot.getChildren().addAll(label_DoubleCalculator, label_expression, text_expression, label_results, text_result, 
				button_solve, button_data);
	}
			


		


		




		/**********
	   * Private local method to initialize the standard fields for a label
		 */
		private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
		}
			
		/**********
		 * Private local method to initialize the standard fields for a text field 
		 */
		private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
		}
			
		/**********
	 * Private local method to initialize the standard fields for a button
	 */
		private void setupButtonUI(Button b, String ff, double f, double w,double h, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setMaxHeight(h);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
		}
			
			
			/**********************************************************************************************

			User Interface Actions
			
			**********************************************************************************************/

			/**********
			 * Private local method to set the value of the first operand given a text value. The method uses the
			 * business logic class to perform the work of checking the string to see it is a valid value and if 
			 * so, saving that value internally for future computations. If there is an error when trying to convert
			 * the string into a value, the called business logic method returns false and actions are taken to
			 * display the error message appropriately.
			 */
			private void setOperand1() {
				text_Result.setText("");                    // Any change of an operand probably invalidates
//				text_Resulterrorterm("");
				errMVPart1.setText("");
				errMVPart2.setText("");
				label_Result.setText("Result");						// the result, so we clear the old result.
				label_errResult.setText("");
				label_errOperand1errorterm.setText("");
				errETPart1.setText("");
				errETPart2.setText("");
				performGo();
				if (perform.setOperand1(text_Operand1.getText(), text_Operand1errorterm.getText() )) {	// Set the operand and see if there was an error
					label_errOperand1.setText("");					// If no error, clear this operands error
					if (text_Operand2.getText().length() == 0)		// If the other operand is empty, clear its error
						label_errOperand2.setText("");				// as well.
					
				}
				else 												
					performGo();
			}
			

			/**********
			 * Private local method to set the value of the second operand given a text value. The logic is exactly the
			 * same as used for the first operand, above.
			 */
			private void setOperand2() {
				text_Result.setText("");								// See setOperand1's comments. The logic is the same!
				
				errMVPart3.setText("");
				errMVPart4.setText("");
				label_Result.setText("Result");				
				label_errResult.setText("");
				label_errOperand2errorterm.setText("");
				errETPart3.setText("");
				errETPart4.setText("");
				performGo1();
				label_Result.setText("Result");				
				label_errResult.setText("");
				if (perform.setOperand2(text_Operand2.getText(), text_Operand2errorterm.getText())) {
					label_errOperand2.setText("");
					if (text_Operand1.getText().length() == 0)
						label_errOperand1.setText("");
					}
				else
					performGo1();	
			}
			
			
			
			
			/**********
			 * This method is called when an binary operation button has been pressed. It assesses if there are issues 
			 * with either of the binary operands or they are not defined. If not return false (there are no issues)
			 * 
			 * @return	True if there are any issues that should keep the calculator from doing its work.
			 */
			private boolean binaryOperandIssues() {
				String errorMessage1 = perform.getOperand1ErrorMessage();	// Fetch the error messages, if there are any
				String errorMessage2 = perform.getOperand2ErrorMessage();
				if (errorMessage1.length() > 0) {						// Check the first.  If the string is not empty
					label_errOperand1.setText(errorMessage1);			// there's an error message, so display it.
					if (errorMessage2.length() > 0) {					// Check the second and display it if there is
						label_errOperand2.setText(errorMessage2);		// and error with the second as well.
						return true;										// Return true when both operands have errors
					}
					else {
						return true;										// Return true when only the first has an error
					}
				}
				else if (errorMessage2.length() > 0) {					// No error with the first, so check the second
					label_errOperand2.setText(errorMessage2);			// operand. If non-empty string, display the error
					return true;											// message and return true... the second has an error
				}														// Signal there are issues
				
				// If the code reaches here, neither the first nor the second has an error condition. The following code
				// check to see if the operands are defined.
				if (!perform.getOperand1Defined()) {						// Check to see if the first operand is defined
					label_errOperand1.setText("No value found");			// If not, this is an issue for a binary operator
					if (!perform.getOperand2Defined()) {					// Now check the second operand. It is is also
						label_errOperand2.setText("No value found");		// not defined, then two messages should be displayed
						return true;										// Signal there are issues
					}
					return true;
				} else if (!perform.getOperand2Defined()) {				// If the first is defined, check the second. Both
					label_errOperand2.setText("No value found");			// operands must be defined for a binary operator.
					return true;											// Signal there are issues
				}
				
				return false;											// Signal there are no issues with the operands
			}

			/*******************************************************************************************************
			 * This portion of the class defines the actions that take place when the various calculator
			 * buttons (add, subtract, multiply, and divide) are pressed.
			 */

			/**********
			 * This is the add routine
			 * 
			 */
			private void addOperands(){
				// Check to see if both operands are defined and valid
				if (binaryOperandIssues()) 									// If there are issues with the operands, return
					
				return;												// without doing the computation
				int index1 = operand1unit.getSelectionModel().getSelectedIndex(); // gets the index of the selected unit of operand 1
				int index2 = operand2unit.getSelectionModel().getSelectedIndex(); // gets the index of the selected unit of operand 2
				perform.unit(index1,index2); //normalizes the second operand
				resultunitlist.clear(); //clear the result combobox of any units if any 
				loadTheUnitForResult();         // loads the possible unit for the result's combobox
				
				// If the operands are defined and valid, request the business logic method to do the addition and return the
				// result as a String. If there is a problem with the actual computation, an empty string is returned
				String theAnswer = perform.addition();						// Call the business logic add method
				label_errResult.setText("");			// Reset any result error messages from before
				StringTokenizer St = new StringTokenizer(theAnswer,":");
				if (theAnswer.length() > 0) { // Check the returned String to see if it is okay
					
					text_Result.setText(St.nextToken()+" ± "+St.nextToken());			// If okay, display it in the result field and
					//text_Resulterrorterm.setText(St.nextToken());
					label_Result.setText("Sum");								// change the title of the field to "Sum"
				}
				else {														// Some error occurred while doing the addition.
					
					text_Result.setText("");									// Do not display a result if there is an error.				
					text_Resulterrorterm.setText("");
					label_Result.setText("Result");							// Reset the result label if there is an error.
					label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
				}
				
			}
			
			

			/**********
			 * This is the subtract routine
			 * 
			 */
			private void subOperands(){
				if (binaryOperandIssues()) 									// If there are issues with the operands, return
					return;													// without doing the computation
				int index1 = operand1unit.getSelectionModel().getSelectedIndex(); // gets the index of the selected unit of operand 1
				int index2 = operand2unit.getSelectionModel().getSelectedIndex(); // gets the index of the selected unit of operand 2
				perform.unit(index1,index2);//normalizes the second operand
				resultunitlist.clear();   //clear the result combobox of any units if any 
				loadTheUnitForResult();         // loads the possible unit for the result's combobox
				
				// If the operands are defined and valid, request the business logic method to do the subtraction and return the
				// result as a String. If there is a problem with the actual computation, an empty string is returned
				String theAnswer = perform.subtraction();						// Call the business logic sub method
				StringTokenizer St = new StringTokenizer(theAnswer,":");
				label_errResult.setText("");									// Reset any result error messages from before
				if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
					text_Result.setText(St.nextToken()+" ± "+St.nextToken());							// If okay, display it in the result field and
					text_Resulterrorterm.setText(St.nextToken());
					label_Result.setText("Difference");								// change the title of the field to "Difference"
				}
				else {														// Some error occurred while doing the subtraction.
					text_Result.setText("");									// Do not display a result if there is an error.				
					text_Resulterrorterm.setText("");
					label_Result.setText("Result");							// Reset the result label if there is an error.
					label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
			}
			}

			/**********
			 * This is the multiply routine
			 * 
			 */
			private void mpyOperands(){
				if (binaryOperandIssues()) 									// If there are issues with the operands, return
					return;													// without doing the computation
				int index1 = operand1unit.getSelectionModel().getSelectedIndex(); // gets the index of the selected unit of operand 1
				int index2 = operand2unit.getSelectionModel().getSelectedIndex(); // gets the index of the selected unit of operand 2
				perform.unit(index1,index2);//normalizes the second operand
				resultunitlist.clear();      //clear the result combobox of any units if any 
				loadTheUnitForResult1();         // loads the possible unit for the result's combobox
				
				// If the operands are defined and valid, request the business logic method to do the multiplication and return the
				// result as a String. If there is a problem with the actual computation, an empty string is returned
				String theAnswer = perform.multiplication();						// Call the business logic add method
				label_errResult.setText("");									// Reset any result error messages from before
				StringTokenizer St = new StringTokenizer(theAnswer,":");
				if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
					text_Result.setText(St.nextToken()+" ± "+St.nextToken());							// If okay, display it in the result field and
					text_Resulterrorterm.setText(St.nextToken());
					label_Result.setText("Product");								// change the title of the field to "Product"
				}
				else {														// Some error occurred while doing the multiplication.
					text_Result.setText("");									// Do not display a result if there is an error.				
					text_Resulterrorterm.setText("");
					label_Result.setText("Result");							// Reset the result label if there is an error.
					label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
				}										
			}

			
			private void divOperands(){
				if (binaryOperandIssues()) 									// If there are issues with the operands, return
					return;													// without doing the computation
				int index1 = operand1unit.getSelectionModel().getSelectedIndex(); // gets the index of the selected unit of operand 1
				int index2 = operand2unit.getSelectionModel().getSelectedIndex(); // gets the index of the selected unit of operand 2
				perform.unit(index1,index2);//normalizes the second operand
				resultunitlist.clear();      //clear the result combobox of any units if any 
				loadTheUnitForResult2();         // loads the possible unit for the result's combobox
				
				// If the operands are defined and valid, request the business logic method to do the division and return the
				// result as a String. If there is a problem with the actual computation, an empty string is returned
				String theAnswer = perform.division();						// Call the business logic add method
				label_errResult.setText("");									// Reset any result error messages from before
				
				if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
					StringTokenizer St = new StringTokenizer(theAnswer,":");
					text_Result.setText(St.nextToken()+" ± "+St.nextToken());							// If okay, display it in the result field and
					text_Resulterrorterm.setText(St.nextToken());
					label_Result.setText("Quotient");								// change the title of the field to "Quotient"
				
				
				 if (BusinessLogic.zero) {
					text_Result.setText("");
					label_Result.setText("Error");
					label_errResult.setText("error");
					
				}}
				else {														// Some error occurred while doing the division.
					text_Result.setText("");									// Do not display a result if there is an error.				
					text_Resulterrorterm.setText("");
					label_Result.setText("Result");							// Reset the result label if there is an error.
					label_errResult.setText(perform.getResultErrorMessage());	// Display the error message.
				}
			}
			private void sqrtOperands(){
				// If the operand are defined and valid, request the business logic method to do the square root calculation and return the
				// result as a String. If there is a problem with the actual computation, an empty string is returned
		       resultunitlist.clear();
		       resultunitlist.add(operand1unit.getSelectionModel().getSelectedItem());
				String theAnswer = perform.squareroot();	// Call the business logic square root function method
			   label_errResult.setText("");									// Reset any result error messages from before
			   if (theAnswer.length() > 0) {								// Check the returned String to see if it is okay
				   StringTokenizer St = new StringTokenizer(theAnswer,":");
					text_Result.setText(St.nextToken()+" ± "+St.nextToken());
				  // text_Result.setText(theAnswer);	
				// If okay, display it in the result field and
			
			   label_Result.setText("Square Root");								// change the title of the field to 'sqrt'
			}
			else {														// Some error occurred while doing the square root.
			text_Result.setText("");									// Do not display a result if there is an error.				
				label_Result.setText("Result");							// Reset the result label if there is an error.
				label_errResult.setText(perform.getResultErrorMessage());
			}// Display the error message.
			}	
			
			private void datapopup() {
				
				Stage popupwindow=new Stage();
			      
				popupwindow.initModality(Modality.APPLICATION_MODAL);
				popupwindow.setTitle("Add/ Edit Vaiable data:");
 
				table = new TableView();
				table.setEditable(true);
				data = getInitialVariableData();
			    table.setItems(data);
			        
					TableColumn variable = new TableColumn("Variable");
					variable.setCellValueFactory(new PropertyValueFactory<Variable,String>("variable"));
					variable.setCellFactory(TextFieldTableCell.forTableColumn());
					variable.setOnEditCommit(
				            new EventHandler<CellEditEvent<Variable, String>>() {
				                @Override
				                public void handle(CellEditEvent<Variable, String> t) {
				                    ((Variable) t.getTableView().getItems().get(
				                            t.getTablePosition().getRow())
				                            ).setVariable(t.getNewValue());
				                }
				            }
				        );
					TableColumn value = new TableColumn("Value");
					value.setCellValueFactory(new PropertyValueFactory<Variable,String>("value"));
					value.setCellFactory(TextFieldTableCell.forTableColumn());
					value.setOnEditCommit(
				            new EventHandler<CellEditEvent<Variable, String>>() {
				                @Override
				                public void handle(CellEditEvent<Variable, String> t) {
				                    ((Variable) t.getTableView().getItems().get(
				                            t.getTablePosition().getRow())
				                            ).setValue(t.getNewValue());
				                    saveValues();}});
					
					TableColumn er = new TableColumn("Error Term");
					er.setCellValueFactory(new PropertyValueFactory<Variable,String>("errorterm"));
					er.setCellFactory(TextFieldTableCell.forTableColumn());
					er.setOnEditCommit(
				            new EventHandler<CellEditEvent<Variable, String>>() {
				                @Override
				                public void handle(CellEditEvent<Variable, String> t) {
				                    ((Variable) t.getTableView().getItems().get(
				                            t.getTablePosition().getRow())
				                            ).setErrorterm(t.getNewValue());
				                    saveValues();} });
					
					TableColumn unit = new TableColumn("Unit");
					unit.setCellValueFactory(new PropertyValueFactory<Variable,String>("unit"));
					unit.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections
			                .observableArrayList(operand1unitlist)));
			        unit.setEditable(true);
					unit.setOnEditCommit(
				            new EventHandler<CellEditEvent<Variable, String>>() {
				                @Override
				                public void handle(CellEditEvent<Variable, String> t) {
				                    ((Variable) t.getTableView().getItems().get(
				                            t.getTablePosition().getRow())
				                            ).setUnit(t.getNewValue());
				                    saveValues(); } } );
					
					table.getColumns().addAll(variable,value,er,unit);	
					table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);		 
	 
				final Button add = new Button("Add");
				add.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				        data.add(new Variable("_____","_____","______","______" ));
				        saveValues();
				    }});

				VBox layout= new VBox();
				
				layout.getChildren().addAll( table,add);
				 
				layout.setAlignment(Pos.CENTER);
				      
				Scene scene1= new Scene(layout, 600, 400);
				      
				popupwindow.setScene(scene1);
				      
				popupwindow.showAndWait();
				
			}

	
	private ObservableList getInitialVariableData() {
		String var = null; 
		String val =null;
		String  err = null;
		String unit= null;
		list.clear();
		//Scanner to scan the old records from the repository
		Scanner scanner;
		try {
			scanner = new Scanner(new File("Repository/Variables.txt"));
		
		while(scanner.hasNextLine()) {
			var=scanner.nextLine();
			val=scanner.nextLine();
			err=scanner.nextLine();
			unit=scanner.nextLine();
			Variable variable = new Variable(var, val, err, unit);
			list.add(variable);
			if(scanner.nextLine().equals(" ----------")) continue;
			//scanner.close();
			
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 data = FXCollections.observableList(list);
		 
	        return data;
			}


	private List getDataF() {
		String var = null; 
		String val =null;
		String  err = null;
		String unit= null;
		list.clear();
		//Scanner to scan the old records from the repository
		Scanner scanner;
		try {
			scanner = new Scanner(new File("Repository/Variables.txt"));
		
		while(scanner.hasNextLine()) {
			var=scanner.nextLine();
			val=scanner.nextLine();
			err=scanner.nextLine();
			unit=scanner.nextLine();
			Variable variable = new Variable(var, val, err, unit);
			list.add(variable);
			if(scanner.nextLine().equals(" ----------")) continue;
			//scanner.close();
			
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		 data = FXCollections.observableList(list);
		 
	        return list;
			}
	public void saveValues()
	
	{
		
		File theDataFile = new File("Repository/Variables.txt");
		
		try{
			FileWriter writer = new FileWriter(theDataFile);
			for(int ndx =0; ndx < list.size(); ndx++)
				{
					Variable vr = list.get(ndx);
					writer.write(vr.getVariable()+"\n");
					writer.write(vr.getValue()+"\n");
					writer.write(vr.getErrorterm()+"\n");
					writer.write(vr.getUnit()+"\n");
					writer.write("------------"+"\n");
				}
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadTheDataForOp1() {
		
		for(int i=0; i<list.size(); i++)
		{
			if(list.get(i).getVariable()== varlist1.getSelectionModel().getSelectedItem())
			{
				text_Operand1.setText(list.get(i).getValue());
				text_Operand1errorterm .setText(list.get(i).getErrorterm());
				operand1unit.setValue(list.get(i).getUnit());
			}
		}
	
}

	private void loadTheDataForOp2() {
		
		for(int i=0; i<list.size(); i++)
		{
			if(list.get(i).getVariable()==varlist2.getSelectionModel().getSelectedItem())
			{
				text_Operand2.setText(list.get(i).getValue());
				text_Operand2errorterm .setText(list.get(i).getErrorterm());
				operand2unit.setValue(list.get(i).getUnit());
			}
		}
	
}




	private void performGo() {
	String errMessage = CalculatorValue.checkMeasureValue(text_Operand1.getText());
	if (errMessage != "") {
	System.out.println(errMessage);
	label_errOperand1.setText(CalculatorValue.measuredValueErrorMessage);
	if (CalculatorValue.measuredValueIndexofError <= -1) return;
	String input = CalculatorValue.measuredValueInput;
	errMVPart1.setText(input.substring(0, CalculatorValue.measuredValueIndexofError));
	errMVPart2.setText("\u21EB");
    }
    else {
 	errMessage = CalculatorValue.checkerrorterm(text_Operand1errorterm.getText());
	if (errMessage != "") {
		System.out.println(errMessage); 
		label_errOperand1errorterm.setText(CalculatorValue.errortermErrorMessage1);
		if (CalculatorValue.errortermIndexofError1 <= -1) return;
		String input = CalculatorValue.errortermInput1;
		errETPart1.setText(input.substring(0, CalculatorValue.errortermIndexofError1));
		errETPart2.setText("\u21EB");
	}
    }
   }
	private void performGo1() {
		String errMessage = CalculatorValue.checkMeasureValue(text_Operand2.getText());
		if (errMessage != "") {
			System.out.println(errMessage); 
			label_errOperand2.setText(CalculatorValue.measuredValueErrorMessage);
			if (CalculatorValue.measuredValueIndexofError <= -1) return;
			String input = CalculatorValue.measuredValueInput;
			errMVPart3.setText(input.substring(0, CalculatorValue.measuredValueIndexofError));
			errMVPart4.setText("\u21EB");
		}
		else {
			errMessage = CalculatorValue.checkerrorterm(text_Operand2errorterm.getText());
			if (errMessage != "") {
				System.out.println(errMessage); 
				label_errOperand2errorterm.setText(CalculatorValue.errortermErrorMessage1);
				if (CalculatorValue.errortermIndexofError1 <= -1) return;
				String input = CalculatorValue.errortermInput1;
				errETPart3.setText(input.substring(0, CalculatorValue.errortermIndexofError1));
				errETPart4.setText("\u21EB");
			}
			
}
}
	
	public void compute(String expression) {
		
		
	eTree.theReader = new Scanner (expression);	
	//System.out.println(m+x+n);
	
	eTree.lexer = new Lexer(eTree.theReader);
	eTree.current = eTree.lexer.accept();
	eTree.next = eTree.lexer.accept();

	//Invoke the parser and the tree builder
	boolean isValid = eTree.addSubExpr();
	System.out.println("The expression is valid: " + isValid + "\n");
	if (isValid) {
		
		// Display the expression tree
		ExprNode theTree = eTree.exprStack.pop();
		System.out.println(theTree);
		double ss = eTree.compute(theTree);
		String r= Double.toString(ss);
		text_result.setText(r);
		// Evaluate the expression tree
		
		System.out.println("\nThe resulting value is: " + eTree.compute(theTree));
	}
	}
	
	/*char[] ch = expression.toCharArray();
	String a= Character.toString(ch[0]);
	String b= Character.toString(ch[2]);
	String x= Character.toString(ch[1]);
	
	List <Variable> list = new Vector <Variable>();
	list = getDataF();
	String m = "";
	String n = "";
	String TheExpression = "";
	
	for(int i=0; i<list.size(); i++)
	{
		if(a==list.get(i).getVariable())
		{
			m = list.get(i).getValue();
			System.out.println(list.get(i).getValue());
			//text_Operand1errorterm .setText(list.get(i).getErrorterm());
			//operand1unit.setValue(list.get(i).getUnit());
			break;
		}
		}
		
	
		
		for(int j=0; j<list.size(); j++)
		{
			if(list.get(j).getVariable()== b)
			{
				n = list.get(j).getValue();
				System.out.println(list.get(j).getValue());
				//text_Operand1errorterm .setText(list.get(i).getErrorterm());
				//operand1unit.setValue(list.get(i).getUnit());
				break;
			}}
		//TheExpression =  ;
		*/
	/**********
	 * This method checks for only first operand and enables the square root button if there are no errors and 
	 * only after a valid input else it disables the square root button. it also disables the button if there 
	 * are any input in second operand.
	 */
	private void activsqrtButton()
	{
		String op1 = text_Operand1.getText();
		String op2 = text_Operand2.getText();
		String err= CalculatorValue.checkMeasureValue(text_Operand1.getText());
		String err1 = CalculatorValue.checkerrorterm(text_Operand1errorterm.getText());
		String err2 = CalculatorValue.checkMeasureValue(text_Operand1.getText());
		String err12 = CalculatorValue.checkerrorterm(text_Operand1errorterm.getText());
		if(op1.length()<=0)
					 button_sqrt.setDisable(true);
		else
			
			{ if (err.length()<=0) {
				 if(err1.length()<=0)
					 
			       button_sqrt.setDisable(false);			      
			 else
				 button_sqrt.setDisable(true);
				 }
			 else
				 button_sqrt.setDisable(true);}
		
		if( op2.length()>0) 
		{
		if (err.length()<=0 && err1.length()<=0 && err2.length()<=0 && err12.length()<=0) 
		{
		button_Add.setDisable(false);
		button_Sub.setDisable(false);
		button_Mpy.setDisable(false);
		button_Div.setDisable(false);
		button_sqrt.setDisable(true);}
		}
		if(op2.length()<=0 && op1.length()<=0) {
			button_Add.setDisable(true);
			button_Sub.setDisable(true);
			button_Mpy.setDisable(true);
			button_Div.setDisable(true);
			button_sqrt.setDisable(true);
		}
		
}
	/***************
	 * This method checks for input in both the operands and enables the add, subtract, multiplication and 
	 * division button if there are no errors and only after a valid input in both the operands or else it 
	 * disables the add, subtract, multiplication and division button. It also disables the buttons if there 
	 * are no input in any of the operand.
	 */

	public void activateButton()
	{
		String op1 = text_Operand1.getText();
		String op2 = text_Operand2.getText();
		
		String err= CalculatorValue.checkMeasureValue(text_Operand1.getText());
		String err1 = CalculatorValue.checkerrorterm(text_Operand1errorterm.getText());
		String err2 = CalculatorValue.checkMeasureValue(text_Operand1.getText());
		String err12 = CalculatorValue.checkerrorterm(text_Operand1errorterm.getText());
		if(op1.length()>0) {
			if( op2.length()>0) 
				{
				if (err.length()<=0 && err1.length()<=0 && err2.length()<=0 && err12.length()<=0) 
				{
				button_Add.setDisable(false);
				button_Sub.setDisable(false);
				button_Mpy.setDisable(false);
				button_Div.setDisable(false);
				button_sqrt.setDisable(true);}
				}
			else {
				button_Add.setDisable(true);
				button_Sub.setDisable(true);
				button_Mpy.setDisable(true);
				button_Div.setDisable(true);
				button_sqrt.setDisable(false);
			}
			}
		 else {
				button_Add.setDisable(true);
				button_Sub.setDisable(true);
				button_Mpy.setDisable(true);
				button_Div.setDisable(true);
				button_sqrt.setDisable(true);
			}
	}
	/************
	 * This method loads all the units for the second operand's combooBox
	 * @throws FileNotFoundException is thrown if the file is not found
	 */
	private void loadTheUnitForOperand2() throws FileNotFoundException{
		int index = operand1unit.getSelectionModel().getSelectedIndex();
		if(index < 0)index = 0;
		String unit;
		Scanner unitScanner;
		operand2unitlist.clear();
		try {
				unitScanner = new Scanner(new File("Repository/Unit.txt"));
				
			//Fetch all the task and put it in to the list
			while(unitScanner.hasNextLine()) {
				unit = unitScanner.nextLine();
				operand2unitlist.add(unit);
			}} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// Set up the comboBox and select one of the items 
			operand2unit.getItems().clear();
			for(int ndx = 0; ndx <operand2unitlist.size(); ndx++ ) {
				operand2unit.getItems().add(operand2unitlist.get(ndx));}	
			operand2unit.getSelectionModel().selectFirst();
			operand2unit.setOnAction((event)-> {loadTheUnitForResult(); });        // load the units for the result's combobox
		
	}
	/************
	 * This method loads all possible the units for the result's combooBox after any addition or substraction 
	 */
	private void loadTheUnitForResult() {
		int index1 = operand1unit.getSelectionModel().getSelectedIndex();
		int index2 = operand2unit.getSelectionModel().getSelectedIndex();
		
		button_Add.setDisable(false);
		button_Sub.setDisable(false);
			
		if(index1==0 && index2==0)
				resultunitlist.add("No Unit Selected");
			
			else if(index2==1 && index1==1)
				{
				resultunitlist.add("kilometer");
				resultunitlist.add("meter");
				resultunitlist.add("miles");
				}
			else if(index1==1 && index2==2) {
				resultunitlist.add("kilometer");
			resultunitlist.add("meter");
			resultunitlist.add("miles");
			}
			else if( index1==1 && index2==3 ){
				resultunitlist.add("kilometer");
			resultunitlist.add("meter");
			resultunitlist.add("miles");
			}
			else if(index1==2  && index2==2){
				resultunitlist.add("meter");
				resultunitlist.add("kilometer");
			resultunitlist.add("miles");
			}
			else if(index1==2 && index2==1){
				resultunitlist.add("meter");
				resultunitlist.add("kilometer");
			resultunitlist.add("miles");
			}
			else if(index1==2 && index2==3){
				resultunitlist.add("meter");
				resultunitlist.add("kilometer");
			resultunitlist.add("miles");
			}
			else if(index1==3 && index2==3){
				resultunitlist.add("miles");
				resultunitlist.add("meter");
				resultunitlist.add("kilometer");
			}
			else if(index1==3 && index2==1){
				resultunitlist.add("miles");
				resultunitlist.add("meter");
				resultunitlist.add("kilometer");
				}
			else if(index1==3 && index2==2){
				resultunitlist.add("miles");
				resultunitlist.add("meter");
				resultunitlist.add("kilometer");
			}
			else if(index1==4 && index2==4) {
				resultunitlist.add("meter/second");
				resultunitlist.add("kilometer/second");
				}
			else if(index1==4 && index2==5) {
				resultunitlist.add("meter/second");
				resultunitlist.add("kilometer/second");
			}
			else if(index1==5 && index2==5) {
				resultunitlist.add("kilometer/second");
				resultunitlist.add("meter/second");
			}
			else if(index1==5 && index2==4) {
				resultunitlist.add("kilometer/second");
				resultunitlist.add("meter/second");
			}
			else if(index1==6 && index2==6) {
				resultunitlist.add("second");
				resultunitlist.add("days");
			}
			else if(index1==6 && index2==7) {
				resultunitlist.add("second");
				resultunitlist.add("days");
			}
			else if(index1==7 && index2==7) {
				resultunitlist.add("days");
				resultunitlist.add("second");
			}
		else if(index1==7 && index2==6) {
				resultunitlist.add("days");
				resultunitlist.add("second");
			}
			else if(index1==8 && index2==8){
				resultunitlist.add("kilometer^3/second^2");
				resultunitlist.add("meter^3/second^2");
			}
			else {
				button_Add.setDisable(true);
				button_Sub.setDisable(true);}
			
			// Set up the comboBox and select one of the items 
			resultunit.getItems().clear();
			for(int ndx = 0; ndx <resultunitlist.size(); ndx++ ) {
			resultunit.getItems().add(resultunitlist.get(ndx));}	 
			resultunit.getSelectionModel().selectFirst();
			resultunit.setOnAction((event)-> {	changeTheResult();});
	}
	/************
	 * This method loads all possible the units for the result's combooBox after any multiplication 
	 */
	private void loadTheUnitForResult1() {
		int a = operand1unit.getSelectionModel().getSelectedIndex();
		int b = operand2unit.getSelectionModel().getSelectedIndex();
		button_Mpy.setDisable(false);
		
		if(a==0 || b==0) 
		{
			if(a==0) resultunitlist.add(operand2unit.getSelectionModel().getSelectedItem());
			if(b==0) resultunitlist.add(operand1unit.getSelectionModel().getSelectedItem());

		}
		else if(a==0 && b==0)
			resultunitlist.add("No Unit Selected");
			else if(a==1 && b==0)
			{
				resultunitlist.add("kilometer");
				resultunitlist.add("meter");
				resultunitlist.add("miles");
				}
		
		else if(a==1 && b==1) {
			resultunitlist.add("kilometer^2");
			resultunitlist.add("meter^2");
			resultunitlist.add("miles^2");
		
		}
		else if(a==1 && b==2) {
		resultunitlist.add("kilometer^2");
		resultunitlist.add("meter^2");
		resultunitlist.add("miles^2");
		}
	
		else if(a==1 && b==3){
		resultunitlist.add("kilometer^2");
		resultunitlist.add("meter^2");
		resultunitlist.add("miles^2");
		}
		else if(a==1 && b==4) {
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("meter^2/second");
			resultunitlist.add("miles^2/second");
		}
		else if(a==1 && b==5){
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("meter^2/second");
			resultunitlist.add("miles^2/second");
		}	
		else if(a==1 && b==6) {
			resultunitlist.add("kilometer second");
			resultunitlist.add("meter second");
			resultunitlist.add("miles second");
		}
		else if(a==1 && b==7){
			resultunitlist.add("kilometer second");
			resultunitlist.add("meter second");
			resultunitlist.add("miles second");
		}
		else if(a==1 && b==8)
			resultunitlist.add("kilometer^4/second^2");
		else if(a==2 && b==0)
		{
		resultunitlist.add("meter");
		resultunitlist.add("kilometer");
		resultunitlist.add("miles");
		}
		else if(a==2 && b==1) {
			resultunitlist.add("meter^2");
			resultunitlist.add("kilometer^2");
			resultunitlist.add("miles^2");
		}
		else if(a==2 && b==2){
			resultunitlist.add("meter^2");
			resultunitlist.add("kilometer^2");
			resultunitlist.add("miles^2");
		}
		else if(a==2 && b==3){
			resultunitlist.add("meter^2");
			resultunitlist.add("kilometer^2");
			resultunitlist.add("miles^2");
		}
		else if(a==2 && b==4) {
			resultunitlist.add("meter^2/second");
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("miles^2/second");
		}
		else if(a==2 && b==5){
			resultunitlist.add("meter^2/second");
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("miles^2/second");
		}
		else if(a==2 && b==6) {
			resultunitlist.add("meter second");
			resultunitlist.add("kilometer second");
			resultunitlist.add("miles second");
		}
		else if(a==2 && b==7){
			resultunitlist.add("meter second");
			resultunitlist.add("kilometer second");
			resultunitlist.add("miles second");
		}
		else if(a==2 && b==8)
			resultunitlist.add("meter^4/second^2");
		else if(a==3 && b==0)
		{
			resultunitlist.add("miles");
			resultunitlist.add("meter");
			resultunitlist.add("kilometer");
		
		}
		else if(a==3 && b==1) {
			resultunitlist.add("miles^2");
			resultunitlist.add("meter^2");
			resultunitlist.add("kilometer^2");
		}
		else if(a==3 && b==2){
			resultunitlist.add("miles^2");
			resultunitlist.add("meter^2");
			resultunitlist.add("kilometer^2");
		}
		else if(a==3 && b==3){
			resultunitlist.add("miles^2");
			resultunitlist.add("meter^2");
			resultunitlist.add("kilometer^2");
		}
		else if(a==3 && b==4) {
			resultunitlist.add("miles^2/second");
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("meter^2/second");
		}
		else if(a==3 && b==5){
			resultunitlist.add("miles^2/second");
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("meter^2/second");
		}
		else if(a==3 && b==6) {
			resultunitlist.add("miles second");
			resultunitlist.add("meter second");
			resultunitlist.add("kilometer second");
			}
		else if(a==3 && b==7){
			resultunitlist.add("miles second");
			resultunitlist.add("meter second");
			resultunitlist.add("kilometer second");
			}
		else if(a==3 && b==8)
			resultunitlist.add("miles^4/second^2");
		
		else if(a==4 && b==0)
		{
			resultunitlist.add("meter/second");
			resultunitlist.add("kilometer/second");
			}
		else if(a==4 && b==1){
			resultunitlist.add("meter^2/second");
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("miles^2/second");
		}
		else if(a==4 && b==2){
			resultunitlist.add("meter^2/second");
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("miles^2/second");
		}
		else if(a==4 && b==3){
			resultunitlist.add("meter^2/second");
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("miles^2/second");
		}
		else if(a==4 && b==4) {
			resultunitlist.add("meter^2/second^2");
		resultunitlist.add("kilometer^2/second^2");
	}
		else if(a==4 && b==5){
			resultunitlist.add("meter^2/second^2");
		resultunitlist.add("kilometer^2/second^2");
	}
		else if(a==4 && b==6)
		{
		resultunitlist.add("meter");
		resultunitlist.add("kilometer");
		resultunitlist.add("miles");
		}
		else if(a==4 && b==7)
		{
			resultunitlist.add("meter");
			resultunitlist.add("kilometer");
		resultunitlist.add("miles");
		}
		else if(a==4 && b==8)
			resultunitlist.add("meter^4/second^3");
		
		else if(a==5 && b==0)
		{
			resultunitlist.add("kilometer/second");
			resultunitlist.add("meter/second");
		}
		else if(a==5 && b==1) {
		resultunitlist.add("kilometer^2/second");
		resultunitlist.add("meter^2/second");
		resultunitlist.add("milesr^2/second");
		}
		else if(a==5 && b==2){
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("meter^2/second");
			resultunitlist.add("milesr^2/second");
			}
		else if(a==5 && b==3){
			resultunitlist.add("kilometer^2/second");
			resultunitlist.add("meter^2/second");
			resultunitlist.add("milesr^2/second");
			}
		else if(a==5 && b==4){
			resultunitlist.add("kilometer^2/second^2");
			resultunitlist.add("meter^2/second^2");
			resultunitlist.add("milesr^2/second^2");
			}
		else if(a==5 && b==5){
			resultunitlist.add("kilometer^2/second^2");
			resultunitlist.add("meter^2/second^2");
			resultunitlist.add("milesr^2/second^2");
			}
		else if(a==5 && b==6) {
			resultunitlist.add("kilometer second");
		resultunitlist.add("meter second");
	}
		else if(a==5 && b==7){
			resultunitlist.add("kilometer second");
		resultunitlist.add("meter second");
	}
		else if(a==5 && b==8)
			resultunitlist.add("kilometer^4/second^3");
		
		else if(a==6 && b==0)
		{
			resultunitlist.add("second");
			resultunitlist.add("days");
		}
		else if(a==6 && b==1){
			resultunitlist.add("kilometer second");
		resultunitlist.add("meter second");
	}
	else if(a==6 && b==2){
		resultunitlist.add("kilometer second");
	resultunitlist.add("meter second");
}
	else if(a==6 && b==3) {
		resultunitlist.add("miles second");
			resultunitlist.add("kilometer second");
		resultunitlist.add("meter second");
	}
	else if(a==6 && b==4)
	{
		resultunitlist.add("meter");
		resultunitlist.add("kilometer");
	resultunitlist.add("miles");
	
	}
	else if(a==6 && b==5)
	{
		resultunitlist.add("kilometer");
		resultunitlist.add("meter");
		resultunitlist.add("miles");
		}
	else if(a==6 && b==6)
		resultunitlist.add("second^2");
	else if(a==6 && b==7)
		resultunitlist.add("second^2");
		
	else if(a==6 && b==8)
		resultunitlist.add("kilometer^3/second");
		
	else if(a==8 && b==7)
			resultunitlist.add("kilometer^3/second");
		else if(a==8 && b==6)
			resultunitlist.add("kilometer^3/second");
		else
			button_Mpy.setDisable(true);
			
		// Set up the comboBox and select one of the items 
					resultunit.getItems().clear();
					for(int ndx = 0; ndx <resultunitlist.size(); ndx++ ) {
					resultunit.getItems().add(resultunitlist.get(ndx));}	 
					resultunit.getSelectionModel().selectFirst();
					resultunit.setOnAction((event)-> {	changeTheResult();});
					
	}
	/************
	 * This method loads all possible the units for the result's combooBox after any division 
	 */
	private void loadTheUnitForResult2(){
		int a = operand1unit.getSelectionModel().getSelectedIndex();
		int b = operand2unit.getSelectionModel().getSelectedIndex();
		button_Div.setDisable(false);
		if(a==0 || b==0) 
		{
			if(a==0) resultunitlist.add(operand2unit.getSelectionModel().getSelectedItem());
			if(b==0) resultunitlist.add(operand1unit.getSelectionModel().getSelectedItem());

		}
		else if(a==0 && b==0)
			resultunitlist.add("No Unit Selected");
			else if(a==1 && b==0)
			{
				resultunitlist.add("kilometer");
				resultunitlist.add("meter");
				resultunitlist.add("miles");
				}
		else if(a==1 && b==1)
			resultunitlist.add("Null");
		else if(a==1 && b==2)
			resultunitlist.add("Null");
		else if(a==1 && b==3)
			resultunitlist.add("Null");
		else if(a==1 && b==4)
		{
			resultunitlist.add("second");
			resultunitlist.add("days");
		}
		else if(a==1 && b==5)
		{
			resultunitlist.add("second");
			resultunitlist.add("days");
		}	
		else if(a==1 && b==6)
		{
			resultunitlist.add("kilometer/second");
			resultunitlist.add("meter/second");
		}
		else if(a==1 && b==7)
		{
			resultunitlist.add("kilometer/second");
			resultunitlist.add("meter/second");
		}
		else if(a==1 && b==8)
			resultunitlist.add("second^2/kilometer^2");
		else if(a==2 && b==0)
		{
			resultunitlist.add("meter");
			resultunitlist.add("kilometer");
		resultunitlist.add("miles");
		}
		else if(a==2 && b==1)
			resultunitlist.add("Null");
		else if(a==2 && b==2)
			resultunitlist.add("Null");
		else if(a==2 && b==3)
			resultunitlist.add("Null");
		else if(a==2 && b==4)
		{
			resultunitlist.add("second");
			resultunitlist.add("days");
		}
		else if(a==2 && b==5)
		{
			resultunitlist.add("second");
			resultunitlist.add("days");
		}
		else if(a==2 && b==6)
		{
			resultunitlist.add("meter/second");
			resultunitlist.add("kilometer/second");
			}
		else if(a==2 && b==7)
		{
			resultunitlist.add("meter/second");
			resultunitlist.add("kilometer/second");
			}
		else if(a==2 && b==8)
			resultunitlist.add("second^2/meter^2");
		else if(a==3 && b==0)
			{
				resultunitlist.add("miles");
				resultunitlist.add("meter");
				resultunitlist.add("kilometer");
			}
		else if(a==3 && b==1)
			resultunitlist.add("Null");
		else if(a==3 && b==2)
			resultunitlist.add("Null");
		else if(a==3 && b==3)
			resultunitlist.add("Null");
		else if(a==3 && b==4)
		{
			resultunitlist.add("second");
			resultunitlist.add("days");
		}
		else if(a==3 && b==5)
			{
				resultunitlist.add("second");
				resultunitlist.add("days");
			}
		else if(a==3 && b==6)
			{
				resultunitlist.add("second");
				resultunitlist.add("days");
			}
		else if(a==3 && b==7)
			resultunitlist.add("miles/ second");
		else if(a==3 && b==8)
			resultunitlist.add("second^2/miles^2");
		
		else if(a==4 && b==0)
		{
			resultunitlist.add("meter/second");
			resultunitlist.add("kilometer/second");
			}
		else if(a==4 && b==1)
			resultunitlist.add("second^-1");
		else if(a==4 && b==2)
			resultunitlist.add("second^-1");
		else if(a==4 && b==3)
			resultunitlist.add("second^-1");
		else if(a==4 && b==4)
			resultunitlist.add("Null");
		else if(a==4 && b==5)
			resultunitlist.add("Null");
		else if(a==4 && b==6)
			resultunitlist.add("meter/second^2");
		else if(a==4 && b==7)
			resultunitlist.add("meter/second^2");
		else if(a==4 && b==8)
			resultunitlist.add("second^2/meter^2");
		else if(a==4 && b==5)
			resultunitlist.add("Null");
		else if(a==4 && b==8)
			resultunitlist.add("second/meter^2");
		
		else if(a==5 && b==0)
		{
			resultunitlist.add("kilometer/second");
			resultunitlist.add("meter/second");
		}
		else if(a==5 && b==1)
			resultunitlist.add("second^-1");
		else if(a==5 && b==2)
			resultunitlist.add("second^-1");
		else if(a==5 && b==3)
			resultunitlist.add("second^-1");
		else if(a==5 && b==4)
			resultunitlist.add("Null");
		else if(a==5 && b==5)
			resultunitlist.add("Null");
		else if(a==5 && b==8)
			resultunitlist.add("second/kilometer^2");
		else if(a==5 && b==7)
			resultunitlist.add("kilometer/second^2");
		else if(a==5 && b==6)
			resultunitlist.add("kilometer/second^2");
		else if(a==6 && b==7)
			resultunitlist.add("Null");
		else if(a==7 && b==6)
			resultunitlist.add("Null");
		else if(a==8 && b==7)
			resultunitlist.add("kilometer^3/second^3");
		else if(a==8 && b==6)
			resultunitlist.add("kilometer^3/second^3");
		else
			button_Div.setDisable(true);
			
		// Set up the comboBox and select one of the items 
					resultunit.getItems().clear();
					for(int ndx = 0; ndx <resultunitlist.size(); ndx++ ) {
					resultunit.getItems().add(resultunitlist.get(ndx));}	 
					resultunit.getSelectionModel().selectFirst();
					resultunit.setOnAction((event)-> {	changeTheResult();});
				
	}
	/*********
	 * This method changes the result according the unit in the result area if the user 
	 * wants to see the result in any other possible unit system
	 */
	private void changeTheResult() {
		
		String a = resultunitlist.get(0);
		String b = resultunit.getSelectionModel().getSelectedItem();
		//int index = resultunit.getSelectionModel().getSelectedIndex();
		
		StringTokenizer St = new StringTokenizer(text_Result.getText(),"±");
		Double operand= Double.parseDouble( St.nextToken());
		Double error= Double.parseDouble( St.nextToken());
		
		if(a=="kilometer" || a=="kilometer/second" || a=="kilometer second" ) 
		{
			if(b=="meter" || b=="meter/second" || b=="meter second")
			{
				operand = operand*1000;
				error = error*1000;
			}
			else if (b=="miles" || b=="miles/second" || b=="miles second")
			{
				operand = operand/1.609;
				error = error/1.609;
			}
		}
		else if(a=="meter" || a=="meter/second" || a=="meter second") 
		{
			if(b=="kilometer" || b=="kilometer/second" || b=="kilometer second")
			{
				operand = operand/1000;
				error = error/1000;
			}
			else if (b=="miles" || b=="miles/second" || b=="miles second")
			{
				operand = operand/1609;
				error = error/1609;
			}
		}
		else if(a=="miles" || a=="miles/second" || a=="miles second") 
		{
			if(b=="kilometer" || b=="kilometer/second" || b=="kilometer second")
			{
				operand = operand*1.609;
				error = error*1.609;
			}
			else if (b=="meter" || b=="meter/second" || b=="meter second")
			{
				operand = operand*1609;
				error = error*1609;
			}
		}
		else if(a=="kilometer^2" || a=="kilometer^2/second" || a=="kilometer^2/second^2") 
		{
			if(b=="meter^2" || b=="meter^2/second" || b=="meter^2/second^2")
			{
				operand = operand*1000000;
				error = error*1000000;
			}
			else if(b=="miles^2" || b=="miles^2/second" || b=="miles^2/second^2")
			{
				operand = operand/2.59;
				error = error/2.59;
			}
		}
		else if(a=="meter^2" || a=="meter^2/second" || a=="meter^2/second^2") 
		{
			if(b=="kilometer^2" || b=="kilometer^2/second" || b=="kilometer^2/second^2")
			{
				operand = operand/1000000;
				error = error/1000000;
			}
			else if(b=="miles^2" || b=="miles^2/second" || b=="miles^2/second^2")
			{
				operand = operand/(2.59e+6);
				error = error/(2.59e+6);
			}
		}
		else if(a=="second") 
		{
			if(b=="days")
			{
				operand = operand/86400;
				error = error/86400;
			}
		}
		else if(a=="days") 
		{
			if(b=="second")
			{
				operand = operand*86400;
				error = error*86400;
			}
		}
		else if(a=="kilometer^3/second^2") 
		{
			if(b=="meter^3/second^2")
			{
				operand = operand*1000000000;
				error = error*1000000000;
			}
		}
		else if(a=="meter^3/second^2") 
		{
			if(b=="kilometer^3/second^2")
			{
				operand = operand/1000000000;
				error = error/1000000000;
			}
		}
		//Set the result  
		text_Result.setText(operand+" ± "+error);
		
		// Switch the Units in the Result ComboBox so that we get the correct result when the units is changed multiple times
		/*List <String> resultunitlistcopy = new Vector <String>();
		resultunitlistcopy.clear();
		resultunitlistcopy.add(b);
		System.out.println(resultunitlistcopy.get(0));
		//resultunitlistcopy.add(resultunitlist.get(index));
		for(int ndx = 1; ndx <resultunitlist.size(); ndx++ ) {
			if(ndx==index) 
				resultunitlistcopy.add(a);
			else 
				resultunitlistcopy.add(resultunitlist.get(ndx));
			
			System.out.println(resultunitlistcopy.get(ndx));
		}
		
		resultunitlist.clear();
		for(int ndx = 0; ndx <resultunitlistcopy.size(); ndx++ ) {
			resultunitlist.add(resultunitlistcopy.get(ndx));
			System.out.println(resultunitlist.get(ndx));
		}
				
		resultunit.getItems().clear();
		for(int ndx = 0; ndx <resultunitlist.size(); ndx++ ) {
		resultunit.getItems().add(resultunitlist.get(ndx));}	 
		resultunit.getSelectionModel().selectFirst();
		resultunit.setOnAction((event)-> {	changeTheResult();});*/
		
	}
}