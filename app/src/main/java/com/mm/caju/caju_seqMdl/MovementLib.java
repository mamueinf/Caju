/*******************************************************************************
 * 2015, All rights reserved.
 *******************************************************************************/
package com.mm.caju.caju_seqMdl;

import java.util.LinkedHashSet;
// Start of user code (user defined imports)

// End of user code

/**
 * Description of MovementLib.
 * 
 * @author muelli
 */
public class MovementLib {
	/**
	 * Description of the property offMovList.
	 */
	private LinkedHashSet<OffMovement> offMovList = new LinkedHashSet<OffMovement>();

	/**
	 * Description of the property defMovList.
	 */
	private LinkedHashSet<DefMovement> defMovList = new LinkedHashSet<DefMovement>();

	/**
	 * Description of the property miscMovList.
	 */
	private LinkedHashSet<MiscMovement> miscMovList = new LinkedHashSet<MiscMovement>();

	// Start of user code (user defined attributes for MovementLib)

	// End of user code

	/**
	 * The constructor.
	 */
	public MovementLib() {
		// Start of user code constructor for MovementLib)
		super();
		// End of user code
	}

	// Start of user code (user defined methods for MovementLib)

	// End of user code
	/**
	 * Returns offMovList.
	 * @return offMovList 
	 */
	public LinkedHashSet<OffMovement> getOffMovList() {
		return this.offMovList;
	}

	/**
	 * Sets a value to attribute offMovList. 
	 * @param newOffMovList 
	 */
	public void setOffMovList(LinkedHashSet<OffMovement> newOffMovList) {
		this.offMovList = newOffMovList;
	}

	/**
	 * Adds one attribute (if offMovList had a multiple cardinality)
	 * @param offMovementToAdd in offMovList
	 */
	public void addOffMovementToOffMovList(OffMovement offMovementToAdd) {
		this.offMovList.add(offMovementToAdd);
	}

	/**
	 * Removes an attribute (if offMovList had a multiple cardinality)
	 * @param offMovementToRemove in offMovList
	 */
	public void removeOffMovementToOffMovList(OffMovement offMovementToRemove) {
		this.offMovList.remove(offMovementToRemove);
	}

	/**
	 * Adds all the attribute (if offMovList had a multiple cardinality)
	 * @param offMovementsToAdd in offMovList
	 */
	public void addAllOffMovementToOffMovList(
			LinkedHashSet<OffMovement> offMovementsToAdd) {
		this.offMovList.addAll(offMovementsToAdd);
	}

	/**
	 * Removes all the attribute (if offMovList had a multiple cardinality)
	 * @param offMovementsToRemove in offMovList
	 */
	public void removeAllOffMovementToOffMovList(
			LinkedHashSet<OffMovement> offMovementsToRemove) {
		this.offMovList.removeAll(offMovementsToRemove);
	}

	/**
	 * Returns defMovList.
	 * @return defMovList 
	 */
	public LinkedHashSet<DefMovement> getDefMovList() {
		return this.defMovList;
	}

	/**
	 * Sets a value to attribute defMovList. 
	 * @param newDefMovList 
	 */
	public void setDefMovList(LinkedHashSet<DefMovement> newDefMovList) {
		this.defMovList = newDefMovList;
	}

	/**
	 * Adds one attribute (if defMovList had a multiple cardinality)
	 * @param defMovementToAdd in defMovList
	 */
	public void addDefMovementToDefMovList(DefMovement defMovementToAdd) {
		this.defMovList.add(defMovementToAdd);
	}

	/**
	 * Removes an attribute (if defMovList had a multiple cardinality)
	 * @param defMovementToRemove in defMovList
	 */
	public void removeDefMovementToDefMovList(DefMovement defMovementToRemove) {
		this.defMovList.remove(defMovementToRemove);
	}

	/**
	 * Adds all the attribute (if defMovList had a multiple cardinality)
	 * @param defMovementsToAdd in defMovList
	 */
	public void addAllDefMovementToDefMovList(
			LinkedHashSet<DefMovement> defMovementsToAdd) {
		this.defMovList.addAll(defMovementsToAdd);
	}

	/**
	 * Removes all the attribute (if defMovList had a multiple cardinality)
	 * @param defMovementsToRemove in defMovList
	 */
	public void removeAllDefMovementToDefMovList(
			LinkedHashSet<DefMovement> defMovementsToRemove) {
		this.defMovList.removeAll(defMovementsToRemove);
	}

	/**
	 * Returns miscMovList.
	 * @return miscMovList 
	 */
	public LinkedHashSet<MiscMovement> getMiscMovList() {
		return this.miscMovList;
	}

	/**
	 * Sets a value to attribute miscMovList. 
	 * @param newMiscMovList 
	 */
	public void setMiscMovList(LinkedHashSet<MiscMovement> newMiscMovList) {
		this.miscMovList = newMiscMovList;
	}

	/**
	 * Adds one attribute (if miscMovList had a multiple cardinality)
	 * @param miscMovementToAdd in miscMovList
	 */
	public void addMiscMovementToMiscMovList(MiscMovement miscMovementToAdd) {
		this.miscMovList.add(miscMovementToAdd);
	}

	/**
	 * Removes an attribute (if miscMovList had a multiple cardinality)
	 * @param miscMovementToRemove in miscMovList
	 */
	public void removeMiscMovementToMiscMovList(
			MiscMovement miscMovementToRemove) {
		this.miscMovList.remove(miscMovementToRemove);
	}

	/**
	 * Adds all the attribute (if miscMovList had a multiple cardinality)
	 * @param miscMovementsToAdd in miscMovList
	 */
	public void addAllMiscMovementToMiscMovList(
			LinkedHashSet<MiscMovement> miscMovementsToAdd) {
		this.miscMovList.addAll(miscMovementsToAdd);
	}

	/**
	 * Removes all the attribute (if miscMovList had a multiple cardinality)
	 * @param miscMovementsToRemove in miscMovList
	 */
	public void removeAllMiscMovementToMiscMovList(
			LinkedHashSet<MiscMovement> miscMovementsToRemove) {
		this.miscMovList.removeAll(miscMovementsToRemove);
	}

}
