/*******************************************************************************
 * 2015, All rights reserved.
 *******************************************************************************/
package com.mm.caju.caju_seqMdl;

import java.util.LinkedHashSet;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of SequenceLib.
 * 
 * @author muelli
 */
public class SequenceLib {
	/**
	 * Description of the property sequenceList.
	 */
	public LinkedHashSet<Sequence> sequenceList = new LinkedHashSet<Sequence>();

	// Start of user code (user defined attributes for SequenceLib)

	// End of user code

	/**
	 * The constructor.
	 */
	public SequenceLib() {
		// Start of user code constructor for SequenceLib)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for SequenceLib)

	// End of user code
	/**
	 * Returns sequenceList.
	 * @return sequenceList 
	 */
	public LinkedHashSet<Sequence> getSequenceList() {
		return this.sequenceList;
	}

	/**
	 * Sets a value to attribute sequenceList. 
	 * @param newSequenceList 
	 */
	public void setSequenceList(LinkedHashSet<Sequence> newSequenceList) {
		this.sequenceList = newSequenceList;
	}

	/**
	 * Adds one attribute (if sequenceList had a multiple cardinality)
	 * @param sequenceToAdd in sequenceList
	 */
	public void addSequenceToSequenceList(Sequence sequenceToAdd) {
		this.sequenceList.add(sequenceToAdd);
	}

	/**
	 * Removes an attribute (if sequenceList had a multiple cardinality)
	 * @param sequenceToRemove in sequenceList
	 */
	public void removeSequenceToSequenceList(Sequence sequenceToRemove) {
		this.sequenceList.remove(sequenceToRemove);
	}

	/**
	 * Adds all the attribute (if sequenceList had a multiple cardinality)
	 * @param sequencesToAdd in sequenceList
	 */
	public void addAllSequenceToSequenceList(
			LinkedHashSet<Sequence> sequencesToAdd) {
		this.sequenceList.addAll(sequencesToAdd);
	}

	/**
	 * Removes all the attribute (if sequenceList had a multiple cardinality)
	 * @param sequencesToRemove in sequenceList
	 */
	public void removeAllSequenceToSequenceList(
			LinkedHashSet<Sequence> sequencesToRemove) {
		this.sequenceList.removeAll(sequencesToRemove);
	}

}
