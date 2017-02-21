<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Step 2: Register Account" ])

	%>

<form id="export-raw-form" method="post" action="${ ui.actionLink("kenyaemr", "account/exportHome", "submit") }">
	

	<div class="ke-form-footer">
		<button type="submit"><img src="${ ui.resourceLink("kenyaui", "images/glyphs/ok.png") }" /> Export Raw Data</button>
	</div>
</form>

<script type="text/javascript">
	jq(function() {
		kenyaui.setupAjaxPost('export-raw-form', {
			onSuccess: function(data) {
				
			}
		});
	});
</script>