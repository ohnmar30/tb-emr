<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">
		${ ui.includeFragment("kenyaemr", "intake/listLabOrders", [patientId : currentPatient.patientId, returnUrl : ui.thisUrl() ]) }
</div>