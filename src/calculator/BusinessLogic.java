
package calculator;

import calculator.CalculatorValue;

/**
 * <p> Title: BusinessLogic Class. </p>
 * 
 * Date: 10 September 2020
 * 
 * <p> Description: The code responsible for performing the calculator business logic functions. 
 * This method deals with CalculatorValues and performs actions on them.  The class expects data
 * from the User Interface to arrive as Strings and returns Strings to it.  This class calls the
 * CalculatorValue class to do computations and this class knows nothing about the actual 
 * representation of CalculatorValues, that is the responsibility of the CalculatorValue class and
 * the classes it calls.</p>
 * 
 * <p> Copyright: Lynn Robert Carter, MD. Ashfak © 2020</p>
 * 
 * @author Lynn Robert Carter, MD.Ashfak
 
 * @version 4.0.1	2020-01-26 The JavaFX-based GUI for the implementation of a  integer calculator
 * @version 4.0.2	2020-02-16 The JavaFX-based GUI for the implementation of a double calculator with Square root  
 * @version 4.0.3	2020-03-30 The JavaFX-based GUI for the implementation of a double calculator with FSM
 * @version 4.0.4	2020-04-24 The JavaFX-based GUI for the implementation of a double calculator with ErrorTerm 
 * @version 4.0.5	2020-09-10 The JavaFX-based GUI for the implementation of a UNumber calculator with ErrorTerm and Square root using Newton Raphson Method
 * @version 4.0.6	2020-11-28 The JavaFX-based GUI for the implementation of a UNumber calculator with Units
 */

public class BusinessLogic {				
	
	/**********************************************************************************************

	Attributes
	
	**********************************************************************************************/
	
	// These are the major calculator values 
	private CalculatorValue operand1 = new CalculatorValue(0);
	private CalculatorValue operand2 = new CalculatorValue(0);
	private CalculatorValue result = new CalculatorValue(0);
	private String operand1ErrorMessage = "";
	private boolean operand1Defined = false;
	private String operand2ErrorMessage = "";
	private boolean operand2Defined = false;
	private String resultErrorMessage = "";
	private CalculatorValue ope2;
	/**********************************************************************************************

	Constructors
	
	**********************************************************************************************/
	
	/**********
	 * This method initializes all of the elements of the business logic aspect of the calculator.
	 * There is no special computational initialization required, so the initialization of the
	 * attributes above are all that are needed.
	 */
	public BusinessLogic() {
	}

	/**********************************************************************************************

	Getters and Setters
	
	**********************************************************************************************/
	
	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand1, any associated error message
	 * into operand1ErrorMessage, and sets flags accordingly.
	 * 
	 * @param value    This variable takes input of the operands and gets converted to CalculatorValue
	 * @param value1    This variable takes input of the error term and gets converted to CalculatorValue
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand1(String value, String value1 ) {
		operand1Defined = false;							// Assume the operand will not be defined
		if (value.length() <= 0) {						// See if the input is empty. If so no error
			operand1ErrorMessage = "";					// message, but the operand is not defined.
			return true;									// Return saying there was no error.
		}
		operand1 = new CalculatorValue(value , value1);		   	// If there was input text, try to convert it
		operand1ErrorMessage = operand1.getErrorMessage();	// into a CalculatorValue and see if it
		if (operand1ErrorMessage.length() > 0) 			// worked. If there is a non-empty error 
			return false;								// message, signal there was a problem.
		operand1Defined = true;							// Otherwise, set the defined flag and
		return true;										// signal that the set worked
	}

	

	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into operand2, any associated error message
	 * into operand1ErrorMessage, and sets flags accordingly.
	 * 
	 * The logic of this method is the same as that for operand1 above.
	 * 
	 * @param value    This variable takes input of the operands and gets converted to CalculatorValue
	 * @param value1    This variable takes input of the error term and gets converted to CalculatorValue
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setOperand2(String value, String value1) {			// The logic of this method is exactly the
		operand2Defined = false;							// same as that for operand1, above.
		if (value.length() <= 0) {
			operand2ErrorMessage = "";
			return true;
		}
		operand2 = new CalculatorValue(value, value1);
		operand2ErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		operand2Defined = true;
		return true;
	}

	
	/**********
	 * This public method takes an input String, checks to see if there is a non-empty input string.
	 * If so, it places the converted CalculatorValue into result, any associated error message
	 * into resuyltErrorMessage, and sets flags accordingly.
	 * 
	 * The logic of this method is similar to that for operand1 above. (There is no defined flag.)
	 * 
	 * @param value    This variable takes input of the operands and gets converted to CalculatorValue
	 * @param value1    This variable takes input of the error term and gets converted to CalculatorValue
	 * @return	True if the set did not generate an error; False if there was invalid input
	 */
	public boolean setResult(String value,String value1) {				// The logic of this method is similar to
		if (value.length() <= 0) {						// that for operand1, above.
			operand2ErrorMessage = "";
			return true;
		}
		result = new CalculatorValue(value, value1);
		resultErrorMessage = operand2.getErrorMessage();
		if (operand2ErrorMessage.length() > 0)
			return false;
		return true;
	}
	
	/**********
	 * This public setter sets the String explaining the current error in operand1.
	 * 
	 * @param  m   it is the error message for operand 1
	 */
	public void setOperand1ErrorMessage(String m) {
		operand1ErrorMessage = m;
		return;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in operand1, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand1ErrorMessage() {
		return operand1ErrorMessage;
	}
	
	/**********
	 * This public setter sets the String explaining the current error into operand1.
	 * 
	 * @param m it is the error message for operand 2
	 */
	public void setOperand2ErrorMessage(String m) {
		operand2ErrorMessage = m;
		return;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in operand2, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getOperand2ErrorMessage() {
		return operand2ErrorMessage;
	}
	
	/**********
	 * This public setter sets the String explaining the current error in the result.
	 * 
	 * @param m sets the String explaining the current error in the result 
	 */
	public void setResultErrorMessage(String m) {
		resultErrorMessage = m;
		return;
	}
	
	/**********
	 * This public getter fetches the String explaining the current error in the result, it there is one,
	 * otherwise, the method returns an empty String.
	 * 
	 * @return and error message or an empty String
	 */
	public String getResultErrorMessage() {
		return resultErrorMessage;
	}
	
	/**********
	 * This public getter fetches the defined attribute for operand1. You can't use the lack of an error 
	 * message to know that the operand is ready to be used. An empty operand has no error associated with 
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 * 
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand1Defined() {
		return operand1Defined;
	}
	
	/**********
	 * This public getter fetches the defined attribute for operand2. You can't use the lack of an error 
	 * message to know that the operand is ready to be used. An empty operand has no error associated with 
	 * it, so the class checks to see if it is defined and has no error before setting this flag true.
	 * 
	 * @return true if the operand is defined and has no error, else false
	 */
	public boolean getOperand2Defined() {
		return operand2Defined;
	}

	/**********************************************************************************************

	The toString() Method
	
	**********************************************************************************************/
	
	/**********
	 * This toString method invokes the toString method of the result type (CalculatorValue is this 
	 * case) to convert the value from its hidden internal representation into a String, which can be
	 * manipulated directly by the BusinessLogic and the UserInterface classes.
	 */
	public String toString() {
		return result.toString() ;
	}
	
	/**********
	 * This public toString method is used to display all the values of the BusinessLogic class in a
	 * textual representation for debugging purposes.
	 * 
	 * @return a String representation of the class
	 */
	public String debugToString() {
		String r = "\n******************\n*\n* Business Logic\n*\n******************\n";
		r += "operand1 = " + operand1.toString() + "\n";
		r += "     operand1ErrorMessage = " + operand1ErrorMessage+ "\n";
		r += "     operand1Defined = " + operand1Defined+ "\n";
		r += "operand2 = " + operand2.toString() + "\n";
		r += "     operand2ErrorMessage = " + operand2ErrorMessage+ "\n";
		r += "     operand2Defined = " + operand2Defined+ "\n";
		r += "result = " + result.toString() + "\n";
		r += "     resultErrorMessage = " + resultErrorMessage+ "\n";
		r += "*******************\n\n";
		return r;
	}

	/**********************************************************************************************

	Business Logic Operations (e.g. addition)
	
	**********************************************************************************************/
	/******************
	 * If the given units of both operands are of the same dimension but in different 
	 * measurement systems, this method normalizes the values of the second operand
	 * 
	 * @param a This is the corresponding index number of the first operand's unit
	 * @param b  This is the corresponding index number of the second operand's unit
	 */
	public void unit(int a, int b)
	{
		ope2 = new CalculatorValue(operand2);
		double op2 = (operand2.measuredValue);
		
		if(a==1 && b==0)
			return;
		else if(a==1 && b==1)
			return;
		else if(a==1 && b==2)
			op2= (op2/1000);
		else if(a==1 && b==3)
			op2= (op2*1.609);
		else if(a==1 && b==4)
			op2= (op2/1000);
		else if(a==1 && b==5)
			return;
		else if(a==1 && b==6)
			return;
		else if(a==1 && b==7)
			op2= (op2*86400);
		else if(a==1 && b==8)
			return;
		
		else if(a==2 && b==0)
			return;
		else if(a==2 && b==1)
			op2= (op2*1000);
		else if(a==2 && b==2)
			return;
		else if(a==2 && b==3)
			op2= (op2*1609.344);
		else if(a==2 && b==4)
			return;
		else if(a==2 && b==5)
			op2= (op2*1000);
		else if(a==2 && b==6)
			return;
		else if(a==2 && b==7)
			op2= (op2*86400);
		else if(a==2 && b==8)
			op2= (op2*1000000000);
		
		else if(a==3 && b==0)
			return;
		else if(a==3 && b==1)
			op2= (op2/1.609);
		else if(a==3 && b==2)
			op2= (op2/1609);
		else if(a==3 && b==3)
			return;
		else if(a==3 && b==4)
			op2= (op2/1609);
		else if(a==3 && b==5)
			op2= (op2/1.609);
		else if(a==3 && b==6)
			return;
		else if(a==3 && b==7)
			op2= (op2*86400);
		else if(a==3 && b==8)
			op2= (op2*0.239912759);
		
		else if(a==4 && b==0)
			return;
		else if(a==4 && b==1)
			op2= (op2*1000);
		else if(a==4 && b==2)
			return;
		else if(a==4 && b==3)
			op2= (op2*1609);
		else if(a==4 && b==4)
			return;
		else if(a==4 && b==5)
			op2= (op2*1000);
		else if(a==4 && b==6)
			return;
		else if(a==4 && b==7)
			op2= (op2*86400);
		else if(a==4 && b==8)
			op2= (op2*1000000000);
		
		
		else if(a==5 && b==0)
			return;
		else if(a==5 && b==1)
			return;
		else if(a==5 && b==2)
			op2= (op2/1000);
		else if(a==5 && b==3)
			op2= (op2*1.609);
		else if(a==5 && b==4)
			op2= (op2/1000);
		else if(a==5 && b==5)
			return;
		else if(a==5 && b==6)
			return;
		else if(a==5 && b==7)
			op2= (op2*86400);
		else if(a==5 && b==8)
			return;
		
		
		else if(a==6 && b==0)
			return;
		else if(a==6 && b==1)
			return;
		else if(a==6 && b==2)
			return;
		else if(a==6 && b==3)
			return;
		else if(a==6 && b==4)
			return;
		else if(a==6 && b==5)
			return;
		else if(a==6 && b==6)
			return;
		else if(a==6 && b==7)
			op2= (op2*86400);
		else if(a==6 && b==8)
			return;
		
		else if(a==7 && b==0)
			return;
		else if(a==7 && b==1)
			return;
		else if(a==7 && b==2)
			return;
		else if(a==7 && b==3)
			return;
		else if(a==7 && b==4)
			op2= (op2*86400);
		else if(a==7 && b==5)
			op2= (op2*86400);
		else if(a==7 && b==6)
			op2= (op2/84600);
		else if(a==7 && b==7)
			return;
		else if(a==7 && b==5)
			op2= (op2*746496*10e4);
		
		else if(a==8 && b==0)
			return;
		else if(a==8 && b==1)
			return;
		else if(a==8 && b==2)
			op2= (op2/1000);
		else if(a==8 && b==3)
			op2= (op2*1.609);
		else if(a==8 && b==4)
			op2= (op2/1000);
		else if(a==8 && b==5)
			return;
		else if(a==8 && b==6)
			return;
		else if(a==8 && b==7)
			op2= (op2*86400);
		else if(a==8 && b==8)
			return;
		else if(a==0)
			return;
		
		ope2.measuredValue=op2;
	}
	/**********
	 * This public method computes the sum of the two operands using the CalculatorValue class method 
	 * for addition. The goal of this class is to support a wide array of different data representations 
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left operand with the 
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 * //System.out.println(result.measuredValue);
		//System.out.println(result.errorterm);
	 * @return a String representation of the result
	 */
	public String addition() {
				
		result = new CalculatorValue(operand1);
		result.add(ope2);
	    resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}
	
	/**********
	 * This public method computes the difference of the two operands using the CalculatorValue class method 
	 * for subtraction. The goal of this class is to support a wide array of different data representations 
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left operand with the 
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 * 
	 * @return  a String representation of the result
	 */
	public String subtraction() {
		result = new CalculatorValue(operand1);
		result.sub(ope2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}
	/**********
	 * This public method computes the product of the two operands using the CalculatorValue class method 
	 * for multiplication . The goal of this class is to support a wide array of different data representations 
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left operand with the 
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 * 
	 * @return  a String representation of the result
	 */
	
	public String multiplication() {
		result = new CalculatorValue(operand1);
		result.mpy(ope2);
		resultErrorMessage = result.getErrorMessage();
		return result.toString();
	}
	
	public static boolean zero = false;
	/**********
	 * This public method computes the quotient of the two operands using the CalculatorValue class method 
	 * for division. The goal of this class is to support a wide array of different data representations 
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left operand with the 
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 * 
	 * @return   a String representation of the result
	 */
	
	public String division() {
		result = new CalculatorValue(operand1);
		if( ope2.measuredValue==0)
		{
			zero=true;
		}
		else {
			result.div(ope2);
    	resultErrorMessage = result.getErrorMessage();}
		return result.toString();
	}
	/**********
	 * This public method computes the logarithm of the operand1 using the CalculatorValue class method 
	 * for logarithm. The goal of this class is to support a wide array of different data representations 
	 * without requiring a change to this class, user interface class, or the Calculator class.
	 * 
	 * This method assumes the operands are defined and valid. It replaces the left operand with the 
	 * result of the computation and it leaves an error message, if there is one, in a String variable
	 * set aside for that purpose.
	 * 
	 * This method does not take advantage or know any detail of the representation!  All of that is
	 * hidden from this class by the ClaculatorValue class and any other classes that it may use.
	 * 
	 * @return a String representation of the result
	 */


	public String squareroot() {
		result=new CalculatorValue(operand1);
		result.square_root();
		//resultErrorMessage=result.getErrorMessage();
		return result.toString();
		
	}

	
	}
