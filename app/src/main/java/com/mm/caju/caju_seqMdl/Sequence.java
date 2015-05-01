/*******************************************************************************
 * 2015, All rights reserved.
 *******************************************************************************/
package com.mm.caju.caju_seqMdl;

import java.util.ArrayList;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of Sequence.
 * 
 * @author muelli
 */
public class Sequence {
	/**
	 * Description of the property timeslots.
	 */
	public ArrayList<TimeSlot> timeslots = new ArrayList<TimeSlot>();

	/**
	 * Description of the property seqTitle.
	 */
	private String seqTitle = "";

	/**
	 * Description of the property date.
	 */
	private String date = "";

	// Start of user code (user defined attributes for Sequence)

	// End of user code

	/**
	 * The constructor.
	 */
	public Sequence() {
		// Start of user code constructor for Sequence)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for Sequence)

	// End of user code
	/**
	 * Returns timeslots.
	 * @return timeslots 
	 */
	public ArrayList<TimeSlot> getTimeslots() {
		return this.timeslots;
	}

	/**
	 * Sets a value to attribute timeslots. 
	 * @param newTimeslots 
	 */
	public void setTimeslots(ArrayList<TimeSlot> newTimeslots) {
		this.timeslots = newTimeslots;
	}

	/**
	 * Adds one attribute (if timeslots had a multiple cardinality)
	 * @param timeSlotToAdd in timeslots
	 */
	public void addTimeSlotToTimeslots(TimeSlot timeSlotToAdd) {
		this.timeslots.add(timeSlotToAdd);
	}

	/**
	 * Removes an attribute (if timeslots had a multiple cardinality)
	 * @param timeSlotToRemove in timeslots
	 */
	public void removeTimeSlotToTimeslots(TimeSlot timeSlotToRemove) {
		this.timeslots.remove(timeSlotToRemove);
	}

	/**
	 * Adds all the attribute (if timeslots had a multiple cardinality)
	 * @param timeSlotsToAdd in timeslots
	 */
	public void addAllTimeSlotToTimeslots(ArrayList<TimeSlot> timeSlotsToAdd) {
		this.timeslots.addAll(timeSlotsToAdd);
	}

	/**
	 * Removes all the attribute (if timeslots had a multiple cardinality)
	 * @param timeSlotsToRemove in timeslots
	 */
	public void removeAllTimeSlotToTimeslots(
			ArrayList<TimeSlot> timeSlotsToRemove) {
		this.timeslots.removeAll(timeSlotsToRemove);
	}

	/**
	 * Returns seqTitle.
	 * @return seqTitle 
	 */
	public String getSeqTitle() {
		return this.seqTitle;
	}

	/**
	 * Sets a value to attribute seqTitle. 
	 * @param newSeqTitle 
	 */
	public void setSeqTitle(String newSeqTitle) {
		this.seqTitle = newSeqTitle;
	}

	/**
	 * Returns date.
	 * @return date 
	 */
	public String getDate() {
		return this.date;
	}

	/**
	 * Sets a value to attribute date. 
	 * @param newDate 
	 */
	public void setDate(String newDate) {
		this.date = newDate;
	}

}
