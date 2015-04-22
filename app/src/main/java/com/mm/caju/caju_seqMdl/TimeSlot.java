/*******************************************************************************
 * 2015, All rights reserved.
 *******************************************************************************/
package com.mm.caju.caju_seqMdl;

// Start of user code (user defined imports)

// End of user code

/**
 * Description of TimeSlot.
 * 
 * @author muelli
 */
public class TimeSlot {
	/**
	 * Description of the property time.
	 */
	private int time = 0;

	/**
	 * Description of the property topPlayerMov.
	 */
	private Movement topPlayerMov = null;

	/**
	 * Description of the property botPlayerMov.
	 */
	private Movement botPlayerMov = null;

	// Start of user code (user defined attributes for TimeSlot)

	// End of user code

	/**
	 * The constructor.
	 */
	public TimeSlot() {
		// Start of user code constructor for TimeSlot)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for TimeSlot)

	// End of user code
	/**
	 * Returns time.
	 * @return time 
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * Sets a value to attribute time. 
	 * @param newTime 
	 */
	public void setTime(int newTime) {
		this.time = newTime;
	}

	/**
	 * Returns topPlayerMov.
	 * @return topPlayerMov 
	 */
	public Movement getTopPlayerMov() {
		return this.topPlayerMov;
	}

	/**
	 * Sets a value to attribute topPlayerMov. 
	 * @param newTopPlayerMov 
	 */
	public void setTopPlayerMov(Movement newTopPlayerMov) {
		this.topPlayerMov = newTopPlayerMov;
	}

	/**
	 * Returns botPlayerMov.
	 * @return botPlayerMov 
	 */
	public Movement getBotPlayerMov() {
		return this.botPlayerMov;
	}

	/**
	 * Sets a value to attribute botPlayerMov. 
	 * @param newBotPlayerMov 
	 */
	public void setBotPlayerMov(Movement newBotPlayerMov) {
		this.botPlayerMov = newBotPlayerMov;
	}

}
