<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient, layout: "sidebar" ])

	def allowNew = !history.changes
	def allowChange = history.changes && history.changes.last().started
	def allowRestart = history.changes && !history.changes.last().started
	def allowUndo = history.changes && history.changes.size() > 0

	def changeDateField = { label ->
		[ label: label, formFieldName: "changeDate", class: java.util.Date, showTime: true, initialValue: initialDate ]
	}

	def regimenField = {
		[ label: "Regimen", formFieldName: "regimen", class: "org.openmrs.module.kenyaemr.regimen.Regimen", fieldFragment: "field/Regimen", category: category,patient: currentPatient ]
	}
	
	def regimenSearchField = {
		[ label: "Info Buttons", formFieldName: "regimen", class: "org.openmrs.module.kenyaemr.regimen.Regimen", fieldFragment: "field/RegimenSearch", category: category,patient: currentPatient ]
	}
	
	def changeRegimenSearchField = {
		[ label: "Regimen", formFieldName: "regimen", class: "org.openmrs.module.kenyaemr.regimen.Regimen", fieldFragment: "field/changeRegimenSearch", category: category,patient: currentPatient ]
	}
	
	def continueRegimenField = {
		[ label: "Regimen", formFieldName: "regimen", class: "org.openmrs.module.kenyaemr.regimen.Regimen", fieldFragment: "field/continueRegimen", category: category,patient: currentPatient ]
	}
	
	def continueRegimenSearchField = {
		[ label: "Regimen", formFieldName: "regimen", class: "org.openmrs.module.kenyaemr.regimen.Regimen", fieldFragment: "field/continueRegimenSearch", category: category,patient: currentPatient ]
	}
	
	def stopRegimenField = {
		[ label: "Regimen", formFieldName: "regimen", class: "org.openmrs.module.kenyaemr.regimen.Regimen", fieldFragment: "field/stopDrugRegimen", category: category,patient: currentPatient ]
	}

	def reasonFields = { reasonType ->
		ui.includeFragment("kenyaui", "widget/rowOfFields", [
			fields: [
				[ label: "Reason", formFieldName: "changeReason", class: "org.openmrs.Concept", fieldFragment: "field/RegimenChangeReason", category: category, reasonType: reasonType ],
				[ label: "Reason (Other)", formFieldName: "changeReasonNonCoded", class: java.lang.String ]
			]
		]);
	}
%>

<script type="text/javascript">

	function choseAction(formId) {
		// Hide the regimen action buttons
		jq('#regimen-action-buttons').hide();

		ui.confirmBeforeNavigating('#' + formId);

		// Show the relevant regimen action form
		jq('#' + formId).show();
	}

	function cancelAction() {
	    
		ui.cancelConfirmBeforeNavigating('.regimen-action-form');
	      jq('#regimen-action-buttons').show();
		  jq('.regimen-action-form').hide();
		  jq('.regimen-action-form').get(0).reset();

		
	     
		 
	}

	function undoLastChange() {
		if (confirm('Undo the last regimen change?')) {
			ui.getFragmentActionAsJson('kenyaemr', 'regimenUtil', 'undoLastChange', { patient: ${ currentPatient.patientId }, category: '${ category }' }, function (data) {
				ui.reloadPage();
			});
		}
	}
</script>

<!-- <div class="ke-page-sidebar">
	<div class="ke-panel-frame">
		${ ui.includeFragment("kenyaui", "widget/panelMenuItem", [ iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back", href: returnUrl ]) }
	</div>
</div> -->

<div class="ke-page-content">
	<div class="ke-panel-frame">
		<div class="ke-panel-heading">${ category } Regimen</div>
	    <div class="ke-panel-content" style="max-width:100%;overflow:auto">

			${ ui.includeFragment("kenyaemr", "regimenHistory", [ history: history ]) }

			<br/>

			<div id="regimen-action-buttons" style="text-align: center">
			<% if (allowNew) { %>
			${ ui.includeFragment("kenyaui", "widget/button", [ iconProvider: "kenyaui", icon: "buttons/regimen_start.png", label: "Start", extra: "a new regimen", onClick: "updateAction('start-new-regimen');choseAction('start-new-regimen')" ]) }
			<% } %>

			<% if (allowChange) { %>
			${ ui.includeFragment("kenyaui", "widget/button", [ iconProvider: "kenyaui", icon: "buttons/regimen_stop.png", label: "Continue", extra: "the current regimen", onClick: "updateAction('continue-regimen');choseAction('continue-regimen')" ]) }
			
			${ ui.includeFragment("kenyaui", "widget/button", [ iconProvider: "kenyaui", icon: "buttons/regimen_change.png", label: "Change", extra: "the current regimen", onClick: "updateAction('change-regimen');choseAction('change-regimen')" ]) }
			
			${ ui.includeFragment("kenyaui", "widget/button", [ iconProvider: "kenyaui", icon: "buttons/regimen_stop.png", label: "Stop", extra: "the current regimen", onClick: "choseAction('stop-regimen')" ]) }
			<% } %>

			<% if (allowRestart) { %>
			${ ui.includeFragment("kenyaui", "widget/button", [ iconProvider: "kenyaui", icon: "buttons/regimen_restart.png", label: "Restart", extra: "a new regimen", onClick: "updateAction('restart-regimen');choseAction('restart-regimen')" ]) }
			<% } %>

			<% if (allowUndo) { %>
			${ ui.includeFragment("kenyaui", "widget/button", [ iconProvider: "kenyaui", icon: "buttons/undo.png", label: "Undo", extra: "the last change", onClick: "undoLastChange()" ]) }
			<% } %>
			</div>
            
			<% if (allowNew) { %>
			<fieldset id="start-new-regim" class="regimen-action-form" style="display: none">
				<legend>Start New Regimen</legend>

				${ ui.includeFragment("kenyaui", "widget/form", [
					fragmentProvider: "kenyaemr",
					fragment: "regimenUtil",
					action: "changeRegimen",
					fields: [
							[ hiddenInputName: "patient", value: currentPatient.id ],
							[ hiddenInputName: "changeType", value: "Start" ],
							[ hiddenInputName: "category", value: category ],
							changeDateField("Start date"),
							regimenField()
					],
					submitLabel: "Save",
					successCallbacks: [ "ui.reloadPage();" ],
					cancelLabel: "Cancel",
					cancelFunction: "cancelAction"
				]) }
			</fieldset>
			
			<fieldset id="start-new-regimen" class="regimen-action-form" style="display: none">
				<legend>Start New Regimen</legend>

				${ ui.includeFragment("kenyaui", "widget/form", [
					fragmentProvider: "kenyaemr",
					fragment: "regimenUtil",
					action: "changeRegimen",
					fields: [
							[ hiddenInputName: "patient", value: currentPatient.id ],
							[ hiddenInputName: "changeType", value: "Start" ],
							[ hiddenInputName: "category", value: category ],
							changeDateField("Start date"),
							regimenSearchField()
					],
					submitLabel: "Save",
					successCallbacks: [ back ],
					cancelLabel: "Cancel",
					cancelFunction: "cancelAction"
				]) }
			</fieldset>
			<% } %>

			<% if (allowChange) { %>
			<fieldset id="continue-regimen" class="regimen-action-form" style="display: none">
				<legend>Continue Regimen</legend>

				${ ui.includeFragment("kenyaui", "widget/form", [
					fragmentProvider: "kenyaemr",
					fragment: "regimenUtil",
					action: "changeRegimen",
					fields: [
							[ hiddenInputName: "patient", value: currentPatient.id ],
							[ hiddenInputName: "changeType", value: "Continue" ],
							[ hiddenInputName: "category", value: category ],
							changeDateField("Continue date"),
							continueRegimenSearchField()
					],
					submitLabel: "Save",
					successCallbacks: [ back ],
					cancelLabel: "Cancel",
					cancelFunction: "cancelAction"
				]) }
			</fieldset>
			
			<fieldset id="change-regim" class="regimen-action-form" style="display: none">
				<legend>Change Regimen</legend>

				${ ui.includeFragment("kenyaui", "widget/form", [
					fragmentProvider: "kenyaemr",
					fragment: "regimenUtil",
					action: "changeRegimen",
					fields: [
							[ hiddenInputName: "patient", value: currentPatient.id ],
							[ hiddenInputName: "changeType", value: "Change" ],
							[ hiddenInputName: "category", value: category ],
							changeDateField("Change date"),
							regimenField(),
							[ value: reasonFields("change") ]
					],
					submitLabel: "Save",
					successCallbacks: [ back ],
					cancelLabel: "Cancel",
					cancelFunction: "cancelAction"
				]) }
			</fieldset>
			
			<fieldset id="change-regimen" class="regimen-action-form" style="display: none">
				<legend>Change Regimen</legend>

				${ ui.includeFragment("kenyaui", "widget/form", [
					fragmentProvider: "kenyaemr",
					fragment: "regimenUtil",
					action: "changeRegimen",
					fields: [
							[ hiddenInputName: "patient", value: currentPatient.id ],
							[ hiddenInputName: "changeType", value: "Change" ],
							[ hiddenInputName: "category", value: category ],
							changeDateField("Change date"),
							changeRegimenSearchField(),
							[ value: reasonFields("change") ]
					],
					submitLabel: "Save",
					successCallbacks: [ back ],
					cancelLabel: "Cancel",
					cancelFunction: "cancelAction"
				]) }
			</fieldset>
			
			<fieldset id="stop-regimen" class="regimen-action-form" style="display: none">
				<legend>Stop Regimen</legend>

				${ ui.includeFragment("kenyaui", "widget/form", [
					fragmentProvider: "kenyaemr",
					fragment: "regimenUtil",
					action: "changeRegimen",
					fields: [
							[ hiddenInputName: "patient", value: currentPatient.id ],
							[ hiddenInputName: "changeType", value: "Stop" ],
							[ hiddenInputName: "category", value: category ],
							changeDateField("Stop date"),
							[ value: reasonFields("stop") ]
					],
					submitLabel: "Save",
					successCallbacks: [ "ui.reloadPage();" ],
					cancelLabel: "Cancel",
					cancelFunction: "cancelAction"
				]) }
			</fieldset>
			<% } %>

			<% if (allowRestart) { %>
			<fieldset id="restart-regim" class="regimen-action-form" style="display: none">
				<legend>Restart Regimen</legend>

				${ ui.includeFragment("kenyaui", "widget/form", [
					fragmentProvider: "kenyaemr",
					fragment: "regimenUtil",
					action: "changeRegimen",
					fields: [
							[ hiddenInputName: "patient", value: currentPatient.id ],
							[ hiddenInputName: "changeType", value: "Restart" ],
							[ hiddenInputName: "category", value: category ],
							changeDateField("Restart date"),
							regimenField()
					],
					submitLabel: "Save",
					successCallbacks: [ "ui.reloadPage();" ],
					cancelLabel: "Cancel",
					cancelFunction: "cancelAction"
				]) }
			</fieldset>
			
			<fieldset id="restart-regimen" class="regimen-action-form" style="display: none">
				<legend>Restart Regimen</legend>

				${ ui.includeFragment("kenyaui", "widget/form", [
					fragmentProvider: "kenyaemr",
					fragment: "regimenUtil",
					action: "changeRegimen",
					fields: [
							[ hiddenInputName: "patient", value: currentPatient.id ],
							[ hiddenInputName: "changeType", value: "Restart" ],
							[ hiddenInputName: "category", value: category ],
							changeDateField("Restart date"),
							regimenSearchField()
					],
					submitLabel: "Save",
					successCallbacks: [ back ],
					cancelLabel: "Cancel",
					cancelFunction: "cancelAction"
				]) }
			</fieldset>
			<% } %>
		</div>
	</div>
</div>

<script type="text/javascript">
var actionName="";
function updateAction(action){
actionName=action;
}
</script>