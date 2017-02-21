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
package org.openmrs.module.kenyaemr.fragment.controller.account;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.util.Log;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.Privilege;
import org.openmrs.Provider;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.PasswordException;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.EmrConstants;
import org.openmrs.module.kenyaemr.metadata.SecurityMetadata;
import org.openmrs.module.kenyaemr.validator.EmailAddressValidator;
import org.openmrs.module.kenyaemr.validator.TelephoneNumberValidator;
import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaemr.wrapper.PersonWrapper;
import org.openmrs.module.kenyaui.annotation.AppAction;
import org.openmrs.module.kenyaui.form.AbstractWebForm;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.MethodParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.validator.ValidateUtil;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Create new account fragment controller
 */
public class ExportHomeFragmentController {
	

	public void controller(@FragmentParam(value = "person", required = false) Person person,
						   @FragmentParam(value = "patient", required = false) Person patient,
						   FragmentModel model) {

		// Roles which can't be assigned directly to new users
		
	}
	/**
	 * Handles form submission
	 */
	@AppAction(EmrConstants.APP_ADMIN)
	public SimpleObject submit(UiUtils ui) 		
	{
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Export Data");
	        
            
            List<Patient> patients = Context.getPatientService().getAllPatients();
         
            int rowCount = 0;

    		for (Patient patient : patients )
    		{
    			Row row = sheet.createRow(++rowCount);
    			int columnCount = 0;
    			
    			String patientName=" ";
    			Date birthDate;
    			String gender= " ";
    			String MDRTBRegistration=" ";
    			String drTBSuspectNumber=" ";
       			PersonAddress personAddress;
  		    	Person person = new Person(patient) ;
  		        String telephoneContact;
  		    	Concept township;
  		    	Concept entryPoint;
  		    	Concept onSecondlineDrug;
                
    			patientName= patient.getGivenName();
                Cell cell1 = row.createCell(++columnCount);
                cell1.setCellValue(patientName);
                Log.info("Aye Aye Than : " + patientName);
                
                birthDate= person.getBirthdate();
                Cell cell2 = row.createCell(++columnCount);
                cell2.setCellValue(birthDate);
                
                gender= person.getGender();
                Cell cell3 =row.createCell(++columnCount);
                cell3.setCellValue(gender);
               
                PatientWrapper wrapper = new PatientWrapper(patient);
    			drTBSuspectNumber = wrapper.getDrTBSuspectNumber();
    			Cell cell4 = row.createCell(++columnCount);
    			  cell4.setCellValue(drTBSuspectNumber);
    			  
    			MDRTBRegistration=wrapper.getMDRTBRegistration();
    		    Cell cell5=row.createCell(++columnCount);
    		    cell5.setCellValue(MDRTBRegistration);
    		    
    			personAddress = new PersonAddress();
			    personAddress = person.getPersonAddress();
				Cell cell6 = row.createCell(++columnCount);
				cell6.setCellValue(personAddress.getAddress1());
				
				
				PersonWrapper wrapper1 = new PersonWrapper(person);
				telephoneContact = wrapper1.getTelephoneContact();
				Cell cell7 = row.createCell(++columnCount);
				cell7.setCellValue(telephoneContact);
				
			      
				//wrapper.getPerson().setCurrentTownshipTBNumber(currentTownshipTBNumber);
    		
				
				
    			
    	            
    	    }

    		FileOutputStream outputStream = new FileOutputStream("Export Data.xlsx");
            workbook.write(outputStream);

            return SimpleObject.create("success", "1");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SimpleObject.create("success", "0");
		}
		finally 
		{
			// Ensure that all proxy privileges are removed
			
		}

		//return SimpleObject.create("success", "1");
			
			
	}
}

