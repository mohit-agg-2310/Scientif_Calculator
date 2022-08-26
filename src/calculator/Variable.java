package calculator;

public class Variable {
	/**********************************************************************************************
	Class Attributes
	**********************************************************************************************/	
	String variable="";                
	String value="";			 
	String errorterm="";			  
	String unit="";			 
	
	/**********************************************************************************************
	Constructors
	**********************************************************************************************/
	/**********
	 * This is the default constructor.  
	 */
	public Variable() {
		variable = " ";
		value= " ";
		errorterm= " ";
		unit=" ";
	}
	/**********
	 * This is defining constructor.  This is the one we expect people to use.
	 */
	public Variable(String vr, String va, String er, String u) {
		variable = vr;
		value= va;
		errorterm= er;
		unit=u;
	}
	/*************************** These are the getters for the class*******************************/
	
	public String getVariable() {
		return variable;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getErrorterm() {
		return errorterm;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setVariable(String vr) {
		variable=vr;
	}
	
	public void setValue(String vl) {
		value=vl;
	}
	
	public void setErrorterm(String er) {
		errorterm=er;
	}
	
	public void setUnit(String un) {
		 unit=un;
	}
	
	public void addEntry(String vr, String va, String er, String u) {
		variable = vr;
		value= va;
		errorterm= er;
		unit=u;
	}

}
