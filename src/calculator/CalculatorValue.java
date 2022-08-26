package calculator;

import java.util.Scanner;

import calculator.UNumber;


/**
 * <p> Title: CalculatorValue Class. </p>
 * date 10 September 2020
 * <p> Description: A component of a JavaFX demonstration application that performs computations </p>
 * 
 * <p> Copyright: Lynn Robert Carter, MD.Ashfak © 2020 </p>
 * 
 ** @author Lynn Robert Carter, MD.Ashfak
 
 * @version 4.0.1	2020-01-26 The JavaFX-based GUI for the implementation of a  integer calculator
 * @version 4.0.2	2020-02-16 The JavaFX-based GUI for the implementation of a double calculator with Square root  
 * @version 4.0.3	2020-03-30 The JavaFX-based GUI for the implementation of a double calculator with FSM
 * @version 4.0.4	2020-04-24 The JavaFX-based GUI for the implementation of a double calculator with ErrorTerm 
 * @version 4.0.5	2020-09-10 The JavaFX-based GUI for the implementation of a UNumber calculator with ErrorTerm and Square root using Newton Raphson Method
 * @version 4.0.6	2020-11-28 The JavaFX-based GUI for the implementation of a UNumber calculator with Units
 */
public class CalculatorValue {
public static String measuredValueErrorMessage = "";	// The alternate error message text
public static String measuredValueInput = "";		// The input being processed
public static int measuredValueIndexofError = -1;		// The index where the error was located
private static int state = 0;						// The current state value
private static int nextState = 0;					// The next state value
private static boolean finalState = false;			// Is this state a final state
private static String inputLine = "";				// The input line
private static char currentChar;						// The current character in the line
private static int currentCharNdx;					// The index of the current character
private static boolean running;						// The flag that specifies if it is running

					// The flag that specifies if it is running
/**********
 * This private method display the input line and then on a line under it displays an up arrow
 * at the point where an error was detected.  This method is designed to be used to display the
 * error message on the console terminal.
 * 
 * @param input				The input string
 * @param currentCharNdx		The location where an error was found
 * @return					Two lines, the entire input line followed by a line with an up arrow
 */
private static String displayInput(String input, int currentCharNdx) {
	// Display the entire input line
	String result = input + "\n";

	// Display a line with enough spaces so the up arrow point to the point of an error
	for (int ndx=0; ndx < currentCharNdx; ndx++) result += " ";

	// Add the up arrow to the end of the second line
	return result + "\u21EB";				// A Unicode up arrow with a base
}


private static void displayDebuggingInfo() {
	if (currentCharNdx >= inputLine.length()) 
		System.out.println(((state < 10) ? "  " : " ") + state + 
				((finalState) ? "       F   " : "           ") + "None");
	else
		System.out.println(((state < 10) ? "  " : " ") + state + 
				((finalState) ? "       F   " : "           ") + "  " + currentChar + " " + 
				((nextState < 10) && (nextState != -1) ? "    " : "   ") + nextState );
}

private static void moveToNextCharacter() {
	currentCharNdx++;
	if (currentCharNdx < inputLine.length())
		currentChar = inputLine.charAt(currentCharNdx);
	else {
		currentChar = ' ';
		running = false;
	}
}

/**********
 * This method is a mechanical transformation of a Finite State Machine diagram into a Java
 * method.
 * 
 * @param input		The input string for the Finite State Machine
 * @return			An output string that is empty if every things is okay or it will be
 * 						a string with a help description of the error follow by two lines
 * 						that shows the input line follow by a line with an up arrow at the
 *						point where the error was found.
 */
public static String checkMeasureValue(String input) {
	if(input.length() <= 0) return "";
	// The following are the local variable used to perform the Finite State Machine simulation
	state = 0;							// This is the FSM state number
	inputLine = input;					// Save the reference to the input line as a global
	currentCharNdx = 0;					// The index of the current character
	currentChar = input.charAt(0);		// The current character from the above indexed position

	// The Finite State Machines continues until the end of the input is reached or at some 
	// state the current character does not match any valid transition to a next state

	measuredValueInput = input;			// Set up the alternate result copy of the input
	running = true;						// Start the loop
	System.out.println("\nCurrent Final Input  Next\nState   State Char  State");

	// The Finite State Machines continues until the end of the input is reached or at some 
	// state the current character does not match any valid transition to a next state
	while (running) {
		// The switch statement takes the execution to the code for the current state, where
		// that code sees whether or not the current character is valid to transition to a
		// next state
		switch (state) {
		case 0: 
			// State 0 has three valid transitions.  Each is addressed by an if statement.
			
			// This is not a final state
			finalState = false;
			
			// If the current character is in the range from 1 to 9, it transitions to state 1
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 1;
				break;
			}
			// If the current character is a decimal point, it transitions to state 3
			else if (currentChar == '.') {
				nextState = 3;
				break;					
			}
			
			// If it is none of those characters, the FSM halts
			else 
				running = false;
			
			// The execution of this state is finished
			break;
		
		case 1: 
			// State 1 has three valid transitions.  Each is addressed by an if statement.
			
			// This is a final state
			finalState = true;
			
			// In state 1, if the character is 0 through 9, it is accepted and we stay in this
			// state
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 1;
				break;
			}
			
			// If the current character is a decimal point, it transitions to state 2
			else if (currentChar == '.') {
				nextState = 2;
				break;
			}
			// If the current character is an E or an e, it transitions to state 5
			else if (currentChar == 'E' || currentChar == 'e') {
				nextState = 5;
				break;
			}
			// If it is none of those characters, the FSM halts
			else
				running = false;
			
			// The execution of this state is finished
			break;			
			
		case 2: 
			// State 2 has two valid transitions.  Each is addressed by an if statement.
			
			// This is a final state
			finalState = true;
			
			// If the current character is in the range from 1 to 9, it transitions to state 1
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 2;
				break;
			}
			// If the current character is an 'E' or 'e", it transitions to state 5
			else if (currentChar == 'E' || currentChar == 'e') {
				nextState = 5;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running = false;

			// The execution of this state is finished
			break;

		case 3:
			// State 3 has only one valid transition.  It is addressed by an if statement.
			
			// This is not a final state
			finalState = false;
			
			// If the current character is in the range from 1 to 9, it transitions to state 1
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 4;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running = false;

			// The execution of this state is finished
			break;

		case 4: 
			// State 4 has two valid transitions.  Each is addressed by an if statement.
			
			// This is a final state
			finalState = true;
			
			// If the current character is in the range from 1 to 9, it transitions to state 4
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 4;
				break;
			}
			// If the current character is an 'E' or 'e", it transitions to state 5
			else if (currentChar == 'E' || currentChar == 'e') {
				nextState = 5;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running = false;

			// The execution of this state is finished
			break;

		case 5: 
        // State 5 has two valid transitions.  Each is addressed by an if statement.
			
			// This is a final state
			finalState = false;
			
			// If the current character is in the range from 1 to 9, it transitions to state 4
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 7;
				break;
			}
			// If the current character is an '+' or '-", it transitions to state 5
			else if (currentChar == '+' || currentChar == '-') {
				nextState = 6;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running = false;

			// The execution of this state is finished
			break;

	
		case 6: 
           // State 6 has one valid transitions.  Each is addressed by an if statement.
			// This is a final state
			finalState = false;
			
			// If the current character is in the range from 1 to 9, it transitions to state 4
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 7;
				break;}

			else 
				running = false;
			// The execution of this state is finished
			break;
			
			
		case 7: 
			
           finalState = true;
			
			// If the current character is in the range from 1 to 9, it transitions to state 4
			if (currentChar >= '0' && currentChar <= '9') {
				nextState = 7;
				break;}
			else 
				running = false;
			// The execution of this state is finished
				break;
		}
		
		if (running) {
			displayDebuggingInfo();
			// When the processing of a state has finished, the FSM proceeds to the next character
			// in the input and if there is one, it fetches that character and updates the 
			// currentChar.  If there is no next character the currentChar is set to a blank.
			moveToNextCharacter();
			
			// Move to the next state
			state = nextState;

		}
		// Should the FSM get here, the loop starts again

	}

	System.out.println("The loop has ended.");

	measuredValueIndexofError = currentCharNdx;		// Copy the index of the current character;
	
	// When the FSM halts, we must determine if the situation is an error or not.  That depends
	// of the current state of the FSM and whether or not the whole string has been consumed.
	// This switch directs the execution to separate code for each of the FSM states.
	switch (state) {
	case 0:
		// State 0 is not a final state, so we can return a very specific error message
		measuredValueIndexofError = currentCharNdx;		// Copy the index of the current character;
		measuredValueErrorMessage = "The first character must be a digit or a decimal point.";
		return "The first character must be a digit or a decimal point.";

	case 1:
		// State 1 is a final state, so we must see if the whole string has been consumed.
		if (currentCharNdx<input.length()) {
			// If not all of the string has been consumed, we point to the current character
			// in the input line and specify what that character must be in order to move
			// forward.
			measuredValueErrorMessage = "This character may only be an \"E\", an \"e\", a digit, "
					+ "a \".\", or it must be the end of the input.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx);
		}
		else {
			measuredValueIndexofError = -1;
			measuredValueErrorMessage = "";
			return measuredValueErrorMessage;
		}

	case 2:
	case 4:
		// States 2 and 4 are the same.  They are both final states with only one possible
		// transition forward, if the next character is an E or an e.
		if (currentCharNdx<input.length()) {
			measuredValueErrorMessage = "This character may only be an \"E\", an \"e\", or it must"
					+ " be the end of the input.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx);
		}
		// If there is no more input, the input was recognized.
		else {
			measuredValueIndexofError = -1;
			measuredValueErrorMessage = "";
			return measuredValueErrorMessage;
		}
	case 3:
	case 6:
		// States 3, and 6 are the same. None of them are final states and in order to
		// move forward, the next character must be a digit.
		measuredValueErrorMessage = "This character may only be a digit.\n";
		return measuredValueErrorMessage + displayInput(input, currentCharNdx);

	case 7:
		// States 7 is similar to states 3 and 6, but it is a final state, so it must be
		// processed differently. If the next character is not a digit, the FSM stops with an
		// error.  We must see here if there are no more characters. If there are no more
		// characters, we accept the input, otherwise we return an error
		if (currentCharNdx<input.length()) {
			measuredValueErrorMessage = "This character may only be a digit.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx);
		}
		else {
			measuredValueIndexofError = -1;
			measuredValueErrorMessage = "";
			return measuredValueErrorMessage;
		}

	case 5:
		// State 5 is not a final state.  In order to move forward, the next character must be
		// a digit or a plus or a minus character.
		measuredValueErrorMessage = "This character may only be a digit, a plus, or minus "
				+ "character.\n";
		return measuredValueErrorMessage + displayInput(input, currentCharNdx);
	default:
		return "";
	}	}






public static String errortermErrorMessage1 = "";
public static String errortermInput1 = "";		// The input being processed
public static int errortermIndexofError1 = -1;// The index where the error was located
private static int states = 0;						// The current state value
private static int nextStates = 0;					// The next state value
private static boolean finalStates = false;			// Is this state a final state
private static String inputLines = "";				// The input line
private static char currentChar1;						// The current character in the line
private static int currentCharNdx1;					// The index of the current character
private static boolean running1;	
/**********
 * This private method display the input line and then on a line under it displays an up arrow
 * at the point where an error was detected.  This method is designed to be used to display the
 * error message on the console terminal.
 * 
 * @param input				The input string
 * @param currentCharNdx		The location where an error was found
 * @return					Two lines, the entire input line followed by a line with an up arrow
 */
private static String displayInputs(String inputs, int currentCharNdx1) {
	// Display the entire input line
	String result = inputs + "\n";

	// Display a line with enough spaces so the up arrow point to the point of an error
	for (int ndx=0; ndx < currentCharNdx1; ndx++) result += " ";

	// Add the up arrow to the end of the second line
	return result + "\u21EB";				// A Unicode up arrow with a base
}
private static void displayDebuggingInfos() {
	if (currentCharNdx1 >= inputLines.length()) 
		
		System.out.println(((states < 10) ? "  " : " ") + state + 
				((finalStates) ? "       F   " : "           ") + "None");
	else
		System.out.println(((state < 10) ? "  " : " ") + state + 
				((finalStates) ? "       F   " : "           ") + "  " + currentChar + " " + 
				((nextStates < 10) && (nextStates != -1) ? "    " : "   ") + nextStates );
}

private static void moveToNextCharacter1() {
	currentCharNdx1++;
	if (currentCharNdx1 < inputLines.length())
		currentChar1 = inputLines.charAt(currentCharNdx1);
	else {
		currentChar1 = ' ';
		running1 = false;
	}
}

/**********
 * This method is a mechanical transformation of a Finite State Machine diagram into a Java
 * method.
 * 
 * @param input		The input string for the Finite State Machine
 * @return			An output string that is empty if every things is okay or it will be
 * 						a string with a help description of the error follow by two lines
 * 						that shows the input line follow by a line with an up arrow at the
 *						point where the error was found.
 */
public static String checkerrorterm(String input) {
	if(input.length() <= 0) return "";
	// The following are the local variable used to perform the Finite State Machine simulation
	states = 0;							// This is the FSM state number
	inputLines = input;					// Save the reference to the input line as a global
	currentCharNdx1 = 0;					// The index of the current character
	currentChar1 = input.charAt(0);		// The current character from the above indexed position

	// The Finite State Machines continues until the end of the input is reached or at some 
	// state the current character does not match any valid transition to a next state

	errortermInput1 = input;			// Set up the alternate result copy of the input
	running1 = true;						// Start the loop
	System.out.println("\nCurrent Final Input  Next\nState   State Char  State");

	// The Finite State Machines continues until the end of the input is reached or at some 
	// state the current character does not match any valid transition to a next state
	while (running1) {
		// The switch statement takes the execution to the code for the current state, where
		// that code sees whether or not the current character is valid to transition to a
		// next state
		switch (states) {
		case 0: 
			// State 0 has three valid transitions.  Each is addressed by an if statement.
			
			// If the current character is in the range from 1 to 9, it transitions to state 1
			if (currentChar1 >= '1' && currentChar1 <= '9') {
				nextStates = 1;
				break;
			}
			// If the current character is a decimal point, it transitions to state 3
			else if (currentChar1 == '.') {
				nextStates= 3;
				break;					
			}
			else if(currentChar1 =='0') {
				nextStates=8;
			}
			
			// If it is none of those characters, the FSM halts
			else 
				running1 = false;
			
			// The execution of this state is finished
			break;
		
		case 1: 
			// State 1 has three valid transitions.  Each is addressed by an if statement.
			
			// In state 1, if the character is 0 through 9, it is accepted and we stay in this
			// state
			if (currentChar1 >= '0' && currentChar1 <= '9') {
				nextStates = 1;
				break;
			}
			
			// If the current character is a decimal point, it transitions to state 2
			else if (currentChar1 == '.') {
				nextStates = 2;
				break;
			}
			// If the current character is an E or an e, it transitions to state 5
			else if (currentChar1 == 'E' || currentChar1 == 'e') {
				nextStates = 5;
				break;
			}
			// If it is none of those characters, the FSM halts
			else
				running1 = false;
			
			// The execution of this state is finished
			break;			
			
		case 2: 
			// State 2 has two valid transitions.  Each is addressed by an if statement.
			
			// If the current character is in the range from 1 to 9, it transitions to state 1
			if (currentChar1 >= '0' && currentChar1 <= '9') {
				nextStates = 2;
				break;
			}
			// If the current character is an 'E' or 'e", it transitions to state 5
			else if (currentChar1 == 'E' || currentChar1 == 'e') {
				nextStates = 5;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running1 = false;

			// The execution of this state is finished
			break;

		case 3:
			// State 3 has only one valid transition.  It is addressed by an if statement.
			
			// If the current character is in the range from 1 to 9, it transitions to state 1
			if (currentChar1 >= '0' && currentChar1 <= '9') {
				nextStates = 4;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running1 = false;

			// The execution of this state is finished
			break;

		case 4: 
			// State 4 has two valid transitions.  Each is addressed by an if statement.
			
			// If the current character is in the range from 1 to 9, it transitions to state 4
			if (currentChar1 >= '0' && currentChar1 <= '9') {
				nextStates = 4;
				break;
			}
			// If the current character is an 'E' or 'e", it transitions to state 5
			else if (currentChar1 == 'E' || currentChar1 == 'e') {
				nextStates = 5;
				break;
			}

			// If it is none of those characters, the FSM halts
			else 
				running1 = false;

			// The execution of this state is finished
			break;

		case 5: 
			if(currentChar1>='0' && currentChar1<='9') {
				nextStates=7;
				break;
			}
			else if(currentChar1=='+'|| currentChar1=='-') {
				nextStates=6;
				break;
			}
			break;

		case 6: 
			if(currentChar1>='0' && currentChar1<='9') {
				nextStates=7;
				break;
			}
			else
				running1=false;
			break;


		case 7: 
			if(currentChar1>='0' && currentChar1<='9') {
				nextStates=7;
				break;
			}
			else
				running1=false;
			break;
		
		case 8:
			if (currentChar1=='.') {
			nextStates = 9;
			break;
		}
		else
			running1= false;
		break;
		
		case 9:
			if(currentChar1=='0') {
				nextStates = 9;
				break;
			}
			else if (currentChar1>='1' && currentChar1<='9') {
				nextStates = 4;
				break;
			}
			else
				running1 = false;
				break;
		}
		
		if (running1) {
			//displayDebuggingInfo();
			// When the processing of a state has finished, the FSM proceeds to the next character
			// in the input and if there is one, it fetches that character and updates the 
			// currentChar.  If there is no next character the currentChar is set to a blank.
			moveToNextCharacter1();
			
			// Move to the next state
			states = nextStates;

		}
		// Should the FSM get here, the loop starts again

	}

	//System.out.println("The loop has ended.");

	errortermIndexofError1 = currentCharNdx1;		// Copy the index of the current character;
	
	// When the FSM halts, we must determine if the situation is an error or not.  That depends
	// of the current state of the FSM and whether or not the whole string has been consumed.
	// This switch directs the execution to separate code for each of the FSM states.
	switch (states) {
	case 0:
		// State 0 is not a final state, so we can return a very specific error message
		errortermIndexofError1 = currentCharNdx1;		// Copy the index of the current character;
		measuredValueErrorMessage = "The first character must be a digit or a decimal point.";
		return "The first character must be a digit or a decimal point.";

	case 1:
		// State 1 is a final state, so we must see if the whole string has been consumed.
		if (currentCharNdx1<input.length()) {
			// If not all of the string has been consumed, we point to the current character
			// in the input line and specify what that character must be in order to move
			// forward.
			errortermErrorMessage1 = "This character may only be an \"E\", an \"e\", a digit, "
					+ "a \".\", or it must be the end of the input.\n";
			return measuredValueErrorMessage + displayInput(input, currentCharNdx1);
		}
		else {
			errortermIndexofError1 = -1;
			errortermErrorMessage1 = "";
			return errortermErrorMessage1;
		}

	case 2:
	case 4:
		// States 2 and 4 are the same.  They are both final states with only one possible
		// transition forward, if the next character is an E or an e.
		if (currentCharNdx1<input.length()) {
			errortermErrorMessage1 = "This character may only be an \"E\", an \"e\", or it must"
					+ " be the end of the input.\n";
			return errortermErrorMessage1 + displayInput(input, currentCharNdx1);
		}
		// If there is no more input, the input was recognized.
		else {
			errortermIndexofError1 = -1;
			errortermErrorMessage1 = "";
			return errortermErrorMessage1;
		}
	case 3:
	case 6:
		// States 3, and 6 are the same. None of them are final states and in order to
		// move forward, the next character must be a digit.
		errortermErrorMessage1 = "This character may only be a digit.\n";
		return errortermErrorMessage1 + displayInput(input, currentCharNdx1);

	case 7:
	case 8:	
	case 9:	
		// States 7 is similar to states 3 and 6, but it is a final state, so it must be
		// processed differently. If the next character is not a digit, the FSM stops with an
		// error.  We must see here if there are no more characters. If there are no more
		// characters, we accept the input, otherwise we return an error
		if (currentCharNdx1<input.length()) {
			errortermErrorMessage1 = "This character may only be a digit.\n";
			return errortermErrorMessage1 + displayInput(input, currentCharNdx1);
		}
		else {
			errortermIndexofError1 = -1;
			errortermErrorMessage1 = "";
			return errortermErrorMessage1;
		}

	case 5:
		// State 5 is not a final state.  In order to move forward, the next character must be
		// a digit or a plus or a minus character.
		errortermErrorMessage1 = "This character may only be a digit, a plus, or minus "
				+ "character.\n";
		return errortermErrorMessage1 + displayInput(input, currentCharNdx1);
	default:
		return "";
	}
}

	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	double measuredValue = 0;
	double errorterm= 0.05;
	String errorMessage = "";
	
	
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/

	/*****
	 * This is the default constructor
	 */
	public CalculatorValue() {
	}

	/*****
	 * This constructor creates a calculator value based on a long integer. For future calculators, it
	 * is best to avoid using this constructor.
	 * 
	 * @param v parameter consisting long integer
	 */
	public CalculatorValue(double v) {
		measuredValue = v;
		errorterm = v;
	}

	/*****
	 * This copy constructor creates a duplicate of an already existing calculator value
	 *@param v  parameter from which the local variables will get values
	 */
	public CalculatorValue(CalculatorValue v) {
		measuredValue = v.measuredValue;
		errorterm= v.errorterm;
		errorMessage = v.errorMessage;
	}

	/*****
	 * This constructor creates a calculator value from a string... Due to the nature
	 * of the input, there is a high probability that the input has errors, so the 
	 * routine returns the value with the error message value set to empty or the string 
	 * of an error message.
	 * 
	 * @param s    string that contains the operands and will be converted to CalculatorValue
	 *@param e    string that contains the error terms and will be converted to CalculatorValue
	 */
	public CalculatorValue(String s ,String e) {
		measuredValue = 0;
		if (s.length() == 0) {								// If there is nothing there,
			errorMessage = "***Error*** Input is empty";		// signal an error	
			return;												
		}
		// If the first character is a plus sign, ignore it.
		int start = 0;										// Start at character position zero
		boolean negative = false;							// Assume the value is not negative
		if (s.charAt(start) == '+')							// See if the first character is '+'
			 start++;										// If so, skip it and ignore it
		
		// If the first character is a minus sign, skip over it, but remember it
		else if (s.charAt(start) == '-'){					// See if the first character is '-'
			start++;											// if so, skip it
			negative = true;									// but do not ignore it
		}
		
		// See if the user-entered string can be converted into an integer value
		Scanner tempScanner = new Scanner(s.substring(start));          // Create scanner for the digits
		if (!tempScanner.hasNextDouble()) {					             // See if the next token is a valid
			errorMessage = "This character may only be an \"E\", an \"e\", a digit , " + "a \".\", or "
					+ "it must be the end of the input. \n "; 		    // integer value.  If not, signal there
			tempScanner.close();								           // return a zero
			return;												
		}
		
		
		// Convert the user-entered string to a integer value and see if something else is following it
		measuredValue = tempScanner.nextDouble();				// Convert the value and check to see
		if (tempScanner.hasNext()) {							// that there is nothing else is 
			errorMessage = "***Error*** Excess data"; 		// following the value.  If so, it
			tempScanner.close();								// is an error.  Therefore we must
			measuredValue = 0;								// return a zero value.
			return;													
		}
		tempScanner.close();
		errorMessage = "";
		if (negative)										// Return the proper value based
			measuredValue = -measuredValue;		// on the state of the flag that
		
		
		
		
		
		errorterm = 0.05;
		if (e.length() == 0) {								// If there is nothing there,
					// signal an error	
			return;												
		}
		// If the first character is a plus sign, ignore it.
		int start1 = 0;										// Start at character position zero
		boolean negative1 = false;							// Assume the value is not negative
		if (e.charAt(start1) == '+')							// See if the first character is '+'
			 start1++;										// If so, skip it and ignore it
		
		// If the first character is a minus sign, skip over it, but remember it
		else if (e.charAt(start) == '-'){					// See if the first character is '-'
			start1++;											// if so, skip it
			negative1 = true;									// but do not ignore it
		}
		
		// See if the user-entered string can be converted into an integer value
		Scanner tempScanner1 = new Scanner(e.substring(start));          // Create scanner for the digits
		if (!tempScanner1.hasNextDouble()) {					             // See if the next token is a valid
			errorMessage = "This character may only be an \"E\", an \"e\", a digit , " + "a \".\", or "
					+ "it must be the end of the input. \n "; 		    // integer value.  If not, signal there
			tempScanner1.close();								           // return a zero
			return;												
		}
		
		
		// Convert the user-entered string to a integer value and see if something else is following it
		errorterm = tempScanner1.nextDouble();				// Convert the value and check to see
		if (tempScanner1.hasNext()) {							// that there is nothing else is 
			errorMessage = "***Error*** Excess data"; 		// following the value.  If so, it
			tempScanner1.close();								// is an error.  Therefore we must
			errorterm = 0.05;								// return a zero value.
			return;													
		}
		tempScanner1.close();
		errorMessage = "";
		if (negative1)										// Return the proper value based
			errorterm = -errorterm;	
	}

	/**********************************************************************************************

	Getters and Setters
	
	**********************************************************************************************/
	
	/*****
	 * This is the start of the getters and setters
	 * 
	 * @return Get the error message
	 */
	public String getErrorMessage(){
		return errorMessage;
	}
	
	/*****
	 * Set the current value of a calculator value to a specific integer
	 *@param v   setting default values
	 */
	public void setValue(double v){
		measuredValue = v;
		errorterm=v;
	}
	
	/*****
	 * Set the current value of a calculator error message to a specific string
	 *@param m  initializing the error message 
	 */
	public void setErrorMessage(String m){
		errorMessage = m;
	}
	
	/*****
	 * Set the current value of a calculator value to the value of another (copy)
	 *
	 *@param v initializing the local variables with the assign values
	 */
	public void setValue(CalculatorValue v){
		measuredValue = v.measuredValue;
		errorterm= v.errorterm;
		errorMessage = v.errorMessage;
	}
	
	/**********************************************************************************************

	The toString() Method
	
	**********************************************************************************************/
	
	/*****
	 * This is the default toString method
	 * When more complex calculator values are creating this routine will need to be updated
	 */
	public String toString() {
		String St= measuredValue + " : "+ errorterm;
		return St;
	}
	
	/*****
	 * This is the debug toString method
	 * 
	 * When more complex calculator values are creating this routine will need to be updated
	 * 
	 * @return for handelinh complec calculator values
	 */
	public String debugToString() {
		return "measuredValue = " + measuredValue + "\nerrorMessage = " + errorMessage + "\n";
	}
	
	
	

	
	/**********************************************************************************************

	The computation methods
	
	**********************************************************************************************/
	

	/*******************************************************************************************************
	 * The following methods implement computation on the calculator values.  These routines assume that the
	 * caller has verified that things are okay for the operation to take place.  These methods understand
	 * the technical details of the values and their reputations, hiding those details from the business 
	 * logic and user interface modules.
	 * 
	 * Since this is addition and we do not yet support units, we don't recognize any errors.
	 */
	
	/******
	 * This method makes an instance of the UNumber class by using its local variable. It converts the two operands 
	 * into UNumber and performs standard methods for adding operands with error term.
	 * 
	 * @param v   the parameter which contains the 2nd operand and its error term which will be added.
	 *            
	 */
	public void add(CalculatorValue v) {
		
		UNumber two = new UNumber(2);
		UNumber lb1 = new UNumber(measuredValue);
		UNumber ub1 = new UNumber(measuredValue);
		UNumber et = new UNumber(errorterm);
		lb1.sub(et);
		ub1.add(et);
		
		UNumber lb2 = new UNumber(v.measuredValue);
		UNumber ub2 = new UNumber(v.measuredValue);
		UNumber et2 = new UNumber(v.errorterm);
		lb2.sub(et2);
		ub2.add(et2);
		
		ub1.add(ub2);
		lb1.add(lb2);
		
		UNumber measuredvalue = new UNumber(ub1);
		UNumber er = new UNumber(ub1);
		
		measuredvalue.add(lb1);
		er.sub(lb1);
		
		measuredvalue.div(two);
		er.div(two);
		
		measuredValue = measuredvalue.getDouble();
		errorterm= er.getDouble();
		errorterm = Math.round(errorterm*100.0)/100.0;
		errorMessage = "";
	}

	/******
	 * This method makes an instance of the UNumber class by using its local variable. It converts the two operands 
	 * into UNumber and performs standard methods for subtracting operands with error term.
	 * 
	 * @param v   the parameter which contains the 2nd operand and its error term which will be subtracted.
	 *            
	 */
	public void sub(CalculatorValue v) {
		
		UNumber two = new UNumber(2);
		UNumber lb1 = new UNumber(measuredValue);
		UNumber ub1 = new UNumber(measuredValue);
		UNumber et = new UNumber(errorterm);
		lb1.sub(et);
		ub1.add(et);
		
		UNumber lb2 = new UNumber(v.measuredValue);
		UNumber ub2 = new UNumber(v.measuredValue);
		UNumber et2 = new UNumber(v.errorterm);
		lb2.sub(et2);
		ub2.add(et2);
		
		ub1.sub(ub2);
		lb1.sub(lb2);
		
		UNumber measuredvalue = new UNumber(ub1);
		UNumber er = new UNumber(ub1);
		
		measuredvalue.add(lb1);
		er.sub(lb1);
		
		measuredvalue.div(two);
		er.div(two);
		
		measuredValue =measuredvalue.getDouble();
		errorterm= er.getDouble();
		errorterm = Math.round(errorterm*100.0)/100.0;
		errorMessage = "";
	}

	/******
	 * This method makes an instance of the UNumber class by using its local variable. It converts the two operands 
	 * into UNumber and performs standard methods for multiplying operands with error term.
	 * 
	 * @param v   the parameter which contains the 2nd operand and its error term which will be multiplied.
	 *            
	 */
	public void mpy(CalculatorValue v) {
		
		UNumber ef1 = new UNumber(errorterm);
		UNumber ef2 = new UNumber(v.errorterm);
		UNumber measuredvalue = new UNumber(measuredValue);
		UNumber vmv = new UNumber(v.measuredValue);
		
		ef1.div(measuredvalue);
		ef2.div(vmv);
		measuredvalue.mpy(vmv);
		ef1.add(ef2);
		ef1.mpy(measuredvalue);
		
		measuredValue =measuredvalue.getDouble();
		errorterm= ef1.getDouble();
		errorterm = Math.round(errorterm*100.0)/100.0;
		errorMessage = "";
	}

	/******
	 * This method makes an instance of the UNumber class by using its local variable. It converts the two operands 
	 * into UNumber and performs standard methods for dividing operands with error term.
	 * 
	 * @param v   the parameter which contains the 2nd operand and its error term which will be divider.
	 *            
	 */
	public void div(CalculatorValue v) {
		
		UNumber ef1 = new UNumber(errorterm);
		UNumber ef2 = new UNumber(v.errorterm);
		UNumber measuredvalue = new UNumber(measuredValue);
		UNumber vmv = new UNumber(v.measuredValue);
		
		ef1.div(measuredvalue);
		ef2.div(vmv);
		measuredvalue.div(vmv);
		ef1.add(ef2);
		ef1.mpy(measuredvalue);
		
		measuredValue =measuredvalue.getDouble();
		errorterm= ef1.getDouble();
		errorterm = Math.round(errorterm*100.0)/100.0;
		errorMessage = "";
		}
	

	/******
	 * This method makes an instance of the UNumber class by using its local variable. It converts the operand 
	 * into UNumber and performs Newton Rapton method from UNumber class methods for finding the square root of
	 * the operand with error term.
	 *            
	 */
	public void square_root() {              
		
		UNumber unumber = new UNumber();
		UNumber a = new UNumber(measuredValue);
		UNumber sqrt = new UNumber();      
		sqrt = unumber.squareRoot(a);
		measuredValue = sqrt.getDouble();
		errorMessage="";
	}
}

