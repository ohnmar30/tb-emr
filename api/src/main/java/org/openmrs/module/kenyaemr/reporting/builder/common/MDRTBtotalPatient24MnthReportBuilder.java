

package org.openmrs.module.kenyaemr.reporting.builder.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.tb.TbIndicatorLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Builds({"kenyaemr.tb.report.patient.mdrtb.total.24"})
public class MDRTBtotalPatient24MnthReportBuilder extends AbstractReportBuilder{
	@Autowired
	TbIndicatorLibrary tbIndicatorLibrary;
	@Autowired
	private CommonDimensionLibrary commonDimensions;
	/**
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#getParameters(org.openmrs.module.kenyacore.report.ReportDescriptor)
	 */

	@Override
	protected List<Parameter> getParameters(ReportDescriptor descriptor) {
		return Arrays.asList(
				new Parameter("startDate", "Start Date", Date.class),
				new Parameter("endDate", "End Date", Date.class)
		);
	}

	/**
	 * @see org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder#buildDataSets(org.openmrs.module.kenyacore.report.ReportDescriptor, org.openmrs.module.reporting.report.definition.ReportDefinition)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		return Arrays.asList(
				ReportUtils.map(createTbNewCategorywithEitherpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIIFwithEitherpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbRelapseCategoryIIwithEitherpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIITADwithEitherpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbCategoryIFwithEitherpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbMDRTBCASESwithEitherpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}"),
				ReportUtils.map(createTbOtherCategorywitheitherpaspatientwithTBEnrollNumber(), "startDate=${startDate},endDate=${endDate}")
			);
	}

	/**
	 * Create the data set
	 * @return data set
	 */
	
	private DataSetDefinition createTbCategoryIIFwithEitherpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("M");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen either with or without PAS  and with Treatment after failure of treatment (IR , RR)");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "MA1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwitheitherpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "MB1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwitheitherpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "MC1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwitheitherpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "MD1", "No. of detected cases with Treatment after failure of treatment (IR , RR) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwitheitherpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "ME1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwitheitherpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "MF1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (MovToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwitheitherpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "MG1", "No. of detected cases with Treatment after failure of treatment (IR , RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatIITBwitheitherpas(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbNewCategorywithEitherpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("L");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen either with or without PAS  and  with New (New, MDR-TB contact, PLHIV) category ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "LA1", "No. of detected cases with new Category with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwitheitherpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "LB1", "No. of detected cases with new Category with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwitheitherpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "LC1", "No. of detected cases with new Category  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwitheitherpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "LD1", "No. of detected cases with new Category  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwitheitherpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "LE1", "No. of detected cases with new Category  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwitheitherpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "LF1", "No. of detected cases with new Category  with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwitheitherpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "LG1", "No. of detected cases with new Category  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmednewCategoryMDRTBwitheitherpas(), indParams), columnsG);
		return  dsd;
	}
   
	private DataSetDefinition createTbCategoryIITADwithEitherpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("N");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen either with or without PAS  and with Treatment after lost to follow up (IR , RR)");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "NA1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultTBwitheitherpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "NB1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultTBwitheitherpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "NC1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultTBwitheitherpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "ND1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultTBwitheitherpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "NE1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultTBwitheitherpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "NF1", "No. of detected cases with Treatment after lost to follow up (IR , RR)  with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultTBwitheitherpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "NG1", "No. of detected cases with Treatment after lost to follow up (IR , RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedDefaultTBwitheitherpas(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbRelapseCategoryIIwithEitherpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("O");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen either with or without PAS  and with Relapse ( IR, RR) ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "OA1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwitheitherpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "OB1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedTBwitheitherpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "OC1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwitheitherpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "OD1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwitheitherpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "OE1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwitheitherpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "OF1", "No. of detected cases with Relapse ( IR, RR)  with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwitheitherpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "OG1", "No. of detected cases with Relapse ( IR, RR)  with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedTBwitheitherpas(), indParams), columnsG);
		return  dsd;
	}
	
	private DataSetDefinition createTbCategoryIFwithEitherpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("P");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen either with or without PAS  and with Non-Converter (IR , RR)");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "PA1", "No. of detected cases  with Non-Converter (IR , RR)  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwitheitherpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "PB1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwitheitherpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "PC1", "No. of detected cases with Non-Converter (IR , RR)  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwitheitherpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "PD1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwitheitherpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "PE1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwitheitherpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "PF1", "No. of detected cases with Non-Converter (IR , RR) with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwitheitherpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "PG1", "No. of detected cases with Non-Converter (IR , RR) with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedCatITBwitheitherpas(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbMDRTBCASESwithEitherpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("Q");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen either with or without PAS  and with MDR tb cases ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));
	
		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
		
        List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
        
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "QA1", "No. of detected cases with MDR tb cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwitheitherpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "QB1", "No. of detected cases with MDR tb cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwitheitherpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "QC1", "No. of detected cases with MDR tb cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwitheitherpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "QD1", "No. of detected cases with MDR tb cases with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwitheitherpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "QE1", "No. of detected cases with MDR tb cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwitheitherpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "QF1", "No. of detected cases with MDR tb cases with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwitheitherpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "QG1", "No. of detected cases with MDR tb cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedstandardMDRTBwitheitherpas(), indParams), columnsG);
		return  dsd;
	}
	private DataSetDefinition createTbOtherCategorywitheitherpaspatientwithTBEnrollNumber() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("R");
		dsd.setDescription("Outcome of patient treated with MDR TB standard regimen either with or without PAS  and with Other (Previously treated EP, Unknown outcome of previously treated Pul:TB) category cases ");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("parameter",map(commonDimensions.treatmentOutcome()));
		dsd.addDimension("patienttype",map(commonDimensions.typeOfPatients()));

		List<ColumnParameters> columnsA = new ArrayList<ColumnParameters>();
		columnsA.add(new ColumnParameters("T", "Cure", "parameter=Cure|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsB = new ArrayList<ColumnParameters>();
		columnsB.add(new ColumnParameters("T1", "Died", "parameter=Died|patienttype=enrollmentTbRegnumber"));
		
	    List<ColumnParameters> columnsC = new ArrayList<ColumnParameters>();
		columnsC.add(new ColumnParameters("T2", "Completed", "parameter=Completed|patienttype=enrollmentTbRegnumber"));
		
	    List<ColumnParameters> columnsD = new ArrayList<ColumnParameters>();
		columnsD.add(new ColumnParameters("T3", "Failure", "parameter=Failure|patienttype=enrollmentTbRegnumber")); 
	    
		List<ColumnParameters> columnsE = new ArrayList<ColumnParameters>();
		columnsE.add(new ColumnParameters("T4", "Defaulted", "parameter=Defaulted|patienttype=enrollmentTbRegnumber"));
		
		List<ColumnParameters> columnsF = new ArrayList<ColumnParameters>();
		columnsF.add(new ColumnParameters("T5", "MoveToXDR", "parameter=MoveToXDR|patienttype=enrollmentTbRegnumber"));
	    
		List<ColumnParameters> columnsG = new ArrayList<ColumnParameters>();
		columnsG.add(new ColumnParameters("T6", "Still Enroll", "parameter=StillEnroll|patienttype=enrollmentTbRegnumber"));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EmrReportingUtils.addRow(dsd, "RA1", "No. of detected cases with Other Category cases  with outcome (Cure) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwitheitherpas(), indParams), columnsA);
		EmrReportingUtils.addRow(dsd, "RB1", "No. of detected cases with Other Category cases with outcome (Died) ", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwitheitherpas(), indParams), columnsB);
		EmrReportingUtils.addRow(dsd, "RC1", "No. of detected cases with Other Category cases  with outcome (Completed)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwitheitherpas(), indParams), columnsC);
		EmrReportingUtils.addRow(dsd, "RD1", "No. of detected cases with Other Category with outcome (Failure)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwitheitherpas(), indParams), columnsD);
		EmrReportingUtils.addRow(dsd, "RE1", "No. of detected cases with Other Category cases with outcome (Defaulted)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwitheitherpas(), indParams), columnsE);
		EmrReportingUtils.addRow(dsd, "RF1", "No. of detected cases with Other Category cases with outcome (MoveToXDR)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwitheitherpas(), indParams), columnsF);
		EmrReportingUtils.addRow(dsd, "RG1", "No. of detected cases with Other Category cases with outcome (Still Enroll)", ReportUtils.map(tbIndicatorLibrary.confirmedotherCategoryMDRTBwitheitherpas(), indParams), columnsG);
		return  dsd;
	}
}

