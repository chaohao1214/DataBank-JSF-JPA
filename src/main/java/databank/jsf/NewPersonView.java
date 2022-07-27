/*****************************************************************
 
  * File: PersonPojo.java Course materials (22W) CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.io.Serializable;


import javax.faces.annotation.ManagedProperty;

import javax.inject.Inject;
import javax.inject.Named;

import databank.model.PersonPojo;

/**
 * This class represents the scope of adding a new person to the DB.
 * 
 * TODO 09 - This class is a managed been. Use the name "newPerson".<br> done
 * TODO 10 - Unlike previous assignment where PersonPojo was view scoped, now this class is.<br>
 * TODO 11 - Add the missing variables and their getters and setters. Have in mind dates and version are internal
 * only.<br>
 * 
 */
@Named("newPerson")
@javax.faces.view.ViewScoped
public class NewPersonView implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected String lastName;
	protected String firstName;
	protected String email;
	protected String phoneNumber;
	protected String city;
	// date version no n6eed

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Inject
	@ManagedProperty("#{personController}")
	protected PersonController personController;

	public NewPersonView() {
	}

	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param LastName LastName to set
	 */
	public void setLastName( String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName firstName to set
	 */
	public void setFirstName( String firstName) {
		this.firstName = firstName;
	}

	public void addPerson() {
		if ( allNotNullOrEmpty( firstName, lastName, email, phoneNumber /* TODO 11 - don't forget the other variables */)) {
			PersonPojo theNewPerson = new PersonPojo();
			theNewPerson.setFirstName( getFirstName());
			theNewPerson.setLastName( getLastName());
			//TODO 12 - call other setters
			theNewPerson.setEmail(getEmail());
			theNewPerson.setPhoneNumber(getPhoneNumber());
			theNewPerson.setCity(getCity());
			personController.addNewPerson( theNewPerson);
			//clean up
			personController.toggleAdding();
			setFirstName( null);
			setLastName( null);
			//TODO 13 - set everything to null
			setEmail(null);
			setCity(null);
			setPhoneNumber(null);
		}
	}

	static boolean allNotNullOrEmpty( final Object... values) {
		if ( values == null) {
			return false;
		}
		for ( final Object val : values) {
			if ( val == null) {
				return false;
			}
			if ( val instanceof String) {
				String str = (String) val;
				if ( str.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
}