/*******************************************************************************
 * 2015, All rights reserved.
 *******************************************************************************/
package com.mm.caju.caju_seqMdl;

import java.util.LinkedHashSet;
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
	public LinkedHashSet<TimeSlot> timeslots = new LinkedHashSet<TimeSlot>();

	/**
	 * Description of the property seqTitle.
	 */
	private String seqTitle = "";

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
	public LinkedHashSet<TimeSlot> getTimeslots() {
		return this.timeslots;
	}

	/**
	 * Sets a value to attribute timeslots. 
	 * @param newTimeslots 
	 */
	public void setTimeslots(LinkedHashSet<TimeSlot> newTimeslots) {
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
	public void addAllTimeSlotToTimeslots(LinkedHashSet<TimeSlot> timeSlotsToAdd) {
		this.timeslots.addAll(timeSlotsToAdd);
	}

	/**
	 * Removes all the attribute (if timeslots had a multiple cardinality)
	 * @param timeSlotsToRemove in timeslots
	 */
	public void removeAllTimeSlotToTimeslots(
			LinkedHashSet<TimeSlot> timeSlotsToRemove) {
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

}
