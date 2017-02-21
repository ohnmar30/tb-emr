/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.api.db.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.kenyaemr.api.db.KenyaEmrDAO;
import org.openmrs.module.kenyaemr.model.DrugInfo;
import org.openmrs.module.kenyaemr.model.DrugObsProcessed;
import org.openmrs.module.kenyaemr.model.DrugOrderProcessed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Hibernate specific data access functions. This class should not be used directly.
 */
@SuppressWarnings("deprecation")
public class HibernateKenyaEmrDAO implements KenyaEmrDAO {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat formatterExt = new SimpleDateFormat("yyyy-MM-dd");
	
	private SessionFactory sessionFactory;

	/**
	 * Sets the session factory
	 * @param sessionFactory the session factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Convenience method to get current session
	 * @return the session
	 */
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<Object> executeSqlQuery(String query, Map<String, Object> substitutions) {
		SQLQuery q = sessionFactory.getCurrentSession().createSQLQuery(query);

		for (Map.Entry<String, Object> e : substitutions.entrySet()) {
			if (e.getValue() instanceof Collection) {
				q.setParameterList(e.getKey(), (Collection) e.getValue());
			} else if (e.getValue() instanceof Object[]) {
				q.setParameterList(e.getKey(), (Object[]) e.getValue());
			} else if (e.getValue() instanceof Cohort) {
				q.setParameterList(e.getKey(), ((Cohort) e.getValue()).getMemberIds());
			} else if (e.getValue() instanceof Date) {
				q.setDate(e.getKey(), (Date) e.getValue());
			} else {
				q.setParameter(e.getKey(), e.getValue());
			}


		}

		q.setReadOnly(true);

		List<Object> r = q.list();
		return r;
	}

	@Override
	public List<Object> executeHqlQuery(String query, Map<String, Object> substitutions) {
		Query q = sessionFactory.getCurrentSession().createQuery(query);

		applySubstitutions(q, substitutions);

		// optimizations go here
		q.setReadOnly(true);

		return q.list();
	}

	private void applySubstitutions(Query q, Map<String, Object> substitutions) {
		for (Map.Entry<String, Object> e : substitutions.entrySet()) {
			if (e.getValue() instanceof Collection) {
				q.setParameterList(e.getKey(), (Collection) e.getValue());
			} else if (e.getValue() instanceof Object[]) {
				q.setParameterList(e.getKey(), (Object[]) e.getValue());
			} else if (e.getValue() instanceof Cohort) {
				q.setParameterList(e.getKey(), ((Cohort) e.getValue()).getMemberIds());
			} else if (e.getValue() instanceof Date) {
				q.setDate(e.getKey(), (Date) e.getValue());
			} else {
				q.setParameter(e.getKey(), e.getValue());
			}
		}
	}
	
	/*
	 * ENCOUNTER
	 */
	public Encounter getFirstEncounterByDateTime(Patient patient,Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("visit", visit));
		criteria.addOrder(Order.asc("encounterDatetime"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getFirstEncounterByCreatedDateTime(Patient patient,Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("visit", visit));
		criteria.addOrder(Order.asc("dateCreated"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getLastEncounterByDateTime(Patient patient,Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("visit", visit));
		criteria.addOrder(Order.desc("encounterDatetime"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getLastEncounterByCreatedDateTime(Patient patient,Visit visit) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("visit", visit));
		criteria.addOrder(Order.desc("dateCreated"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getLastEncounterByDateTime(Patient patient,Set<EncounterType> encounterTypes) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.in("encounterType", encounterTypes));
		criteria.addOrder(Order.desc("encounterDatetime"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public Encounter getLastEncounterByCreatedDateTime(Patient patient,Set<EncounterType> encounterTypes) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Encounter.class);
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.in("encounterType", encounterTypes));
		criteria.addOrder(Order.desc("dateCreated"));
		criteria.setMaxResults(1);
		return (Encounter) criteria.uniqueResult();
	}
	
	public List<org.openmrs.Order> getOrderByDateAndOrderType(Date date,OrderType orderType) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(org.openmrs.Order.class,"order");
		criteria.add(Restrictions.eq("order.orderType", orderType));
		if(date!=null){
		String datee = formatterExt.format(date);
		String startFromDate = datee + " 00:00:00";
		String endFromDate = datee + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("order.startDate", formatter.parse(startFromDate)),
				    Restrictions.le("order.startDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		return criteria.list();
	}
	
	public List<Obs> getObsGroupByDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		if(date!=null){
		String dat = formatterExt.format(date);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		Concept concept1=Context.getConceptService().getConceptByUuid("163021AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept2=Context.getConceptService().getConceptByUuid("163022AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept3=Context.getConceptService().getConceptByUuid("163023AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		List<Concept> obsGroupCollection=new LinkedList<Concept>();
		obsGroupCollection.add(concept1);
		obsGroupCollection.add(concept2);
		obsGroupCollection.add(concept3);
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obs.obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obs.obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		criteria.add(Restrictions.in("obs.concept", obsGroupCollection));
		}
		criteria.add(Restrictions.isNull("comment"));
		return criteria.list();
	}
	
	public List<Obs> getObsGroupByDateAndPerson(Date date,Person person) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		criteria.add(Restrictions.eq("obs.person", person));
		Concept concept1=Context.getConceptService().getConceptByUuid("163021AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept2=Context.getConceptService().getConceptByUuid("163022AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Concept concept3=Context.getConceptService().getConceptByUuid("163023AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		List<Concept> obsGroupCollection=new LinkedList<Concept>();
		obsGroupCollection.add(concept1);
		obsGroupCollection.add(concept2);
		obsGroupCollection.add(concept3);
		if(date!=null){
		String dat = formatterExt.format(date);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("obs.obsDatetime", formatter.parse(startFromDate)),
				    Restrictions.le("obs.obsDatetime", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		criteria.add(Restrictions.in("obs.concept", obsGroupCollection));
		criteria.add(Restrictions.isNull("comment"));
		return criteria.list();
	}
	
	public List<Obs> getObsByObsGroup(Obs obsGroup) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		criteria.add(Restrictions.eq("obs.obsGroup", obsGroup));
		return criteria.list();
	}
	
	public Obs saveOrUpdateObs(Obs obs) throws DAOException {
		return (Obs) sessionFactory.getCurrentSession().merge(obs);
	}
	
	public DrugOrderProcessed saveDrugOrderProcessed(DrugOrderProcessed drugOrderProcessed) throws DAOException {
		return (DrugOrderProcessed) sessionFactory.getCurrentSession().merge(drugOrderProcessed);
	}
	
	public DrugObsProcessed saveDrugObsProcessed(DrugObsProcessed drugObsProcessed) throws DAOException {
		return (DrugObsProcessed) sessionFactory.getCurrentSession().merge(drugObsProcessed);
	}
	
	public DrugOrderProcessed getDrugOrderProcessed(DrugOrder drugOrder) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("drugOrder", drugOrder));
		criteria.add(Restrictions.eq("processedStatus", false));
		criteria.add(Restrictions.isNull("discontinuedDate"));
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public DrugOrderProcessed getLastDrugOrderProcessed(DrugOrder drugOrder) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("drugOrder", drugOrder));
		criteria.addOrder(Order.desc("createdDate"));
		criteria.setMaxResults(1);
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public DrugOrderProcessed getLastDrugOrderProcessedNotDiscontinued(DrugOrder drugOrder) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("drugOrder", drugOrder));
		criteria.add(Restrictions.isNull("discontinuedDate"));
		criteria.addOrder(Order.desc("createdDate"));
		criteria.setMaxResults(1);
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public List<DrugOrderProcessed> getDrugOrderProcessedCompleted(DrugOrder drugOrder) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("drugOrder", drugOrder));
		criteria.add(Restrictions.eq("processedStatus", true));
		return criteria.list();
	}
	
	public DrugOrderProcessed getDrugOrderProcesedById(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("id", id));
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public List<DrugOrderProcessed> getDrugOrdersByProcessedDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		String dat = formatterExt.format(date);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("processedDate", formatter.parse(startFromDate)),
					Restrictions.le("processedDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return criteria.list();
	}
	
	public List<DrugObsProcessed> getObsDrugOrdersByProcessedDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugObsProcessed.class,"DrugObsProcessed");
		String dat = formatterExt.format(date);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("processedDate", formatter.parse(startFromDate)),
				    Restrictions.le("processedDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return criteria.list();
	}
	
	public List<DrugOrderProcessed> getDrugOrdersByPatientAndProcessedDate(Patient patient,Date processedDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("patient", patient));
		criteria.add(Restrictions.eq("processedStatus", true));
		if(processedDate!=null){
		String dat = formatterExt.format(processedDate);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("processedDate", formatter.parse(startFromDate)),
					Restrictions.le("processedDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		return criteria.list();
	}
	
	public List<DrugObsProcessed> getObsDrugOrdersByPatientAndProcessedDate(Patient patient,Date processedDate) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugObsProcessed.class,"DrugObsProcessed");
		criteria.add(Restrictions.eq("patient", patient));
		if(processedDate!=null){
		String dat = formatterExt.format(processedDate);
		String startFromDate = dat + " 00:00:00";
		String endFromDate = dat + " 23:59:59";
		try {
			criteria.add(Restrictions.and(Restrictions.ge("processedDate", formatter.parse(startFromDate)),
				    Restrictions.le("processedDate", formatter.parse(endFromDate))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		}
		return criteria.list();
	}
	
	public List<DrugInfo> getDrugInfo() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugInfo.class,"drugInfo");
		return criteria.list();
	}
	
	public DrugInfo getDrugInfo(String drugCode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugInfo.class,"drugInfo");
		criteria.add(Restrictions.eq("drugCode", drugCode));
		return (DrugInfo) criteria.uniqueResult();
	}
	
	public DrugOrderProcessed getLastRegimenChangeType(Patient patient) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("patient", patient));
		//criteria.add(Restrictions.isNotNull("regimenChangeType"));
		//criteria.add(Restrictions.isNotNull("discontinuedDate"));
		//criteria.addOrder(Order.desc("discontinuedDate"));
		criteria.addOrder(Order.desc("createdDate"));
		criteria.setMaxResults(1);
		return (DrugOrderProcessed) criteria.uniqueResult();
	}
	
	public List<ConceptAnswer> getConceptAnswerByAnsweConcept(Concept answerConcept) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ConceptAnswer.class,"conceptAnswer");
		criteria.add(Restrictions.eq("answerConcept", answerConcept));
		return criteria.list();
	}
	
	public List<PersonAddress> getPatientsByTownship(String township) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PersonAddress.class,"personAddress");
		criteria.add(Restrictions.ilike("countyDistrict", township+"%"));
		return criteria.list();
	}
	
	public List<Obs> getObsByScheduledDate(Date date) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		Collection<Concept> conList=new	ArrayList<Concept>();
		conList.add(Context.getConceptService().getConceptByUuid("5096AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		conList.add(Context.getConceptService().getConceptByUuid("161261AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		criteria.add(Restrictions.in("concept", conList));
		criteria.add(Restrictions.eq("valueDatetime", date));
		return criteria.list();
	}
	
	public Obs getObsForNextAppointmentByPerson(Person person,Set encounters) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Obs.class,"obs");
		criteria.add(Restrictions.eq("person", person));
		Collection<Concept> conList=new	ArrayList<Concept>();
		conList.add(Context.getConceptService().getConceptByUuid("5096AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		conList.add(Context.getConceptService().getConceptByUuid("161261AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
		criteria.add(Restrictions.in("concept", conList));
		//criteria.add(Restrictions.isNull("dateVoided"));
		criteria.add(Restrictions.in("encounter", encounters));
		criteria.addOrder(Order.desc("dateCreated"));
		criteria.setMaxResults(1);
		return (Obs) criteria.uniqueResult();
	}

	@Override
	public List<DrugOrderProcessed> getPatientWithPASregime() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("regimenNo",new String("Standard Regimen 2")));
		return criteria.list();
	}

	@Override
	public List<DrugOrderProcessed> getPatientWithoutPASregime() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.eq("regimenNo",new String("Standard Regimen 1")));
		return criteria.list();
	}

	@Override
	public List<DrugOrderProcessed> getPatientWitheitherPASregime() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugOrderProcessed.class,"drugOrderProcessed");
		criteria.add(Restrictions.in("regimenNo",new String[]{"Standard Regimen 1","Standard Regimen 2"}));
		return criteria.list();
	}
}