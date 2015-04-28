/*******************************************************************************
 * 2015, All rights reserved.
 *******************************************************************************/
package com.mm.caju.caju_seqMdl;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of Movement.
 * 
 * @author muelli
 */
public abstract class Movement implements Cloneable {
	/**
	 * Description of the property movName.
	 */
	private String movName = "";

	/**
	 * Description of the property movNote.
	 */
	private String movNote = "";

	/**
	 * Description of the property movIconID.
	 */
	private int movIconID = 0;

	// Start of user code (user defined attributes for Movement)

	// End of user code

	/**
	 * The constructor.
	 */
	public Movement() {
		// Start of user code constructor for Movement)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Movement)
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	// End of user code
	/**
	 * Returns movName.
	 * @return movName 
	 */
	public String getMovName() {
		return this.movName;
	}

	/**
	 * Sets a value to attribute movName. 
	 * @param newMovName 
	 */
	public void setMovName(String newMovName) {
		this.movName = newMovName;
	}

	/**
	 * Returns movNote.
	 * @return movNote 
	 */
	public String getMovNote() {
		return this.movNote;
	}

	/**
	 * Sets a value to attribute movNote. 
	 * @param newMovNote 
	 */
	public void setMovNote(String newMovNote) {
		this.movNote = newMovNote;
	}

	/**
	 * Returns movIconID.
	 * @return movIconID 
	 */
	public int getMovIconID() {
		return this.movIconID;
	}

	/**
	 * Sets a value to attribute movIconID. 
	 * @param newMovIconID 
	 */
	public void setMovIconID(int newMovIconID) {
		this.movIconID = newMovIconID;
	}

}
