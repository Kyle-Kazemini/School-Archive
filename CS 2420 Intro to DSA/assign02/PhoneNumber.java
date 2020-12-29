package assign02;

/**
 * This class represents a phone number.
 * 
 * @author Erin Parker
 * @version January 16, 2020
 */
public class PhoneNumber {

	private String areaCode;

	private String trunk;

	private String rest;

	/**
	 * Parses the given string to create a phone number.
	 * 
	 * @param phoneNum - the given string
	 */
	public PhoneNumber(String phoneNum) {
		phoneNum = phoneNum.replaceAll("-|\\s|\\.|\\(|\\)", "");

		boolean isValid = true;
		if(phoneNum.length() != 10)
			isValid = false;
		for(int i = 0; isValid && i < 10; i++)
			if(!Character.isDigit(phoneNum.charAt(i)))
				isValid = false;

		if(isValid) {
			areaCode = phoneNum.substring(0, 3);
			trunk = phoneNum.substring(3, 6);
			rest = phoneNum.substring(6, 10);
		}
		else {
			areaCode = "000";
			trunk = "000";
			rest = "000";
			System.err.println("Phone number \"" + phoneNum + "\" is not formatted correctly, initializing as " + toString()
					+ ".");
		}
	}

	/**
	 * Two phone numbers are considered equal if they have the same area code, trunk, and remaining numbers.
	 * 
	 * @param other - the object begin compared with this phone number
	 * @return true if other object is a PhoneNumer type and is equal to this phone number, false otherwise
	 */
	public boolean equals(Object other) {
		if(!(other instanceof PhoneNumber))
			return false;

		PhoneNumber rhs = (PhoneNumber) other;
		PhoneNumber lhs = this;

		return lhs.areaCode.equals(rhs.areaCode) && lhs.trunk.equals(rhs.trunk) && lhs.rest.equals(rhs.rest);
	}

	/**
	 * Returns a textual representation of this phone number.
	 */
	public String toString() {
		return "(" + areaCode + ") " + trunk + "-" + rest;
	}
}