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

package org.openmrs.module.kenyaemr.reporting.library.shared.common;

import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

/**
 * Library of common dimension definitions
 */
@Component
public class CommonDimensionLibrary {

	@Autowired
	private CommonCohortLibrary commonCohortLibrary;

	/**
	 * Gender dimension
	 * @return the dimension
	 */
	public CohortDefinitionDimension gender() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("gender");
		dim.addCohortDefinition("M", map(commonCohortLibrary.males()));
		dim.addCohortDefinition("F", map(commonCohortLibrary.females()));
		return dim;
	}

	/**
	 * Dimension of age using the 3 standard age groups
	 * @return the dimension
	 */
	public CohortDefinitionDimension standardAgeGroups() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("age groups (<1, <15, 15+)");
		dim.addParameter(new Parameter("onDate", "Date", Date.class));
		dim.addCohortDefinition("<1", map(commonCohortLibrary.agedAtMost(0), "effectiveDate=${onDate}"));
		dim.addCohortDefinition("<15", map(commonCohortLibrary.agedAtMost(14), "effectiveDate=${onDate}"));
		dim.addCohortDefinition("15+", map(commonCohortLibrary.agedAtLeast(15), "effectiveDate=${onDate}"));
		return dim;
	}

	public CohortDefinitionDimension treatmentOutcome() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("TreatmentOutcme");
		dim.addParameter(new Parameter("onDate", "Date", Date.class));
		dim.addCohortDefinition("Cure", map(commonCohortLibrary.treatmentOutcome_Cure()));
		dim.addCohortDefinition("Completed", map(commonCohortLibrary.treatmentOutcome_TreatmentCompleted()));
		dim.addCohortDefinition("Failure", map(commonCohortLibrary.treatmentOutcome_Failure()));
		dim.addCohortDefinition("Defaulted", map(commonCohortLibrary.treatmentOutcome_Defaulted()));
		dim.addCohortDefinition("Died", map(commonCohortLibrary.treatmentOutcome_Died()));
		dim.addCohortDefinition("MoveToXDR", map(commonCohortLibrary.treatmentOutcome_Transferedout()));
		dim.addCohortDefinition("StillEnroll", map(commonCohortLibrary.treatmentOutcome_Enroll()));
		
		return dim;
	}
	public CohortDefinitionDimension typeOfPatients() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("PatientType");
		dim.addParameter(new Parameter("onDate", "Date", Date.class));
		dim.addCohortDefinition("conventionalDST", map(commonCohortLibrary.conventionaldDst()));
		dim.addCohortDefinition("enrollmentTbRegnumber", map(commonCohortLibrary.enrollTbnum()));
		return dim;
	}
}