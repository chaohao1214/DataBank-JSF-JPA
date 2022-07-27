package databank.ejb;

import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import databank.model.PersonPojo;


@Singleton
/**
 * 
 * @author Chaohao
 * Stateless Session Bean - PersonService
 */
public class PersonService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//get the log4j2 logger for this class
	private static final Logger LOG = LogManager.getLogger();
	@PersistenceContext( name = "PU_DataBank")
	protected EntityManager em;
	
	/**
	 * Default constructor
	 */
	public PersonService() {
		
	}

	public List< PersonPojo > findAllPersons(){
		
		TypedQuery<PersonPojo> allPersonQuery = em.createNamedQuery(PersonPojo.PERSON_FIND_ALL ,PersonPojo.class);
		return allPersonQuery.getResultList();
		
	}
	
	// InventoryService has region, person for id
	public List< PersonPojo > findAllPersonForId (PersonPojo person){
		TypedQuery<PersonPojo> tq = em.createQuery("select i from Person i where i.id = :id", PersonPojo.class);
		tq.setParameter("id", person);
		return tq.getResultList();
		
	}
	
	
	@Transactional
	public PersonPojo persistPerson(PersonPojo person) {
		LOG.debug("inserting a person = {}", person);
		em.persist(person);
		return person;
	}
	
	public PersonPojo findPersonByPrimaryKey(int empPK) {
		LOG.debug("find a specific person = {}", empPK);
		return em.find(PersonPojo.class, empPK);
	}
	
	@Transactional
	// used for updating
	public PersonPojo mergePerson (PersonPojo personWithUpdate) {
		PersonPojo personToBeUpdated = findPersonByPrimaryKey(personWithUpdate.getId());
		
		if (personToBeUpdated != null) {
			try {
				em.merge(personWithUpdate);
			} catch (OptimisticLockException e) {
				// TODO: handle exception
				e.printStackTrace();
				return null;
			}
			
		}
		return personToBeUpdated;
				
	}
	
	@Transactional
	public void removePerson (int personId) {
		PersonPojo person = findPersonByPrimaryKey(personId);
		if (person != null) {
			em.refresh(person);
			em.remove(person);
		}
	}
}
