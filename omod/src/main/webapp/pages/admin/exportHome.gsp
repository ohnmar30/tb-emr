<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

	def menuItems = [
			[ label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to accounts", href: ui.pageLink("kenyaemr", "admin/manageAccounts") ]
	]
%>

	<div class="ke-page-content">
		${ui.includeFragment("kenyaemr", "account/exportHome")}		

	</div>