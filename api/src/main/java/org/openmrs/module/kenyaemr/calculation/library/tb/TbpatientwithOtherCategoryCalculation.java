package org.openmrs.module.kenyaemr.calculation.library.tb;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyaemr.Dictionary;

public class TbpatientwithOtherCategoryCalculation extends AbstractPatientCalculation{
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) 
	{ 
		Concept registration_group=Dictionary.getConcept(Dictionary.REGISTRATION_GROUP);
		Concept previuoslytreated=Dictionary.getConcept(Dictionary.PREVIOUSLY_TREATED_EP);
		Concept unknownmdr=Dictionary.getConcept(Dictionary.UNKNOWN_OUTCOME_PREVIOUSLY_TREATED_EP);
		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			Patient patient = Context.getPatientService().getPatient(ptId);
			boolean onotherCategory = false;
			List<Obs> obs = Context.getObsService()
					.getObservationsByPersonAndConcept(patient, registration_group);
			
			for (Obs o : obs) {
				if (o.getValueCoded() == previuoslytreated|| o.getValueCoded() == unknownmdr) 
				{
					onotherCategory = true;
				}
			}
		 	 
			
			ret.put(ptId, new BooleanResult(onotherCategory , this, context));
		}
		return ret;
	}

}

