<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")

%>

<div id="continueRegimenSearch" ng-controller="DrugCtrl" data-ng-init="init()">

<table>
<tbody>
<tr>
<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="guide" name="guide" value="Guide" onClick="guideForContinRegimen();" /></td>
<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="artRegimen" name="artRegimen" value="TB Regimen" onClick="artForContinRegimen();" /></td>
</tr>
<tr>
<td class="colA" style="text-align:center">Drug</td>
<td class="colB" style="text-align:center">Strength per unit</td>
<td class="colM" style="text-align:center height=50px">Dosage(mg)/day</td>
<td class="colC" style="text-align:center">Quantity of Unit</td>
<td class="colD" style="text-align:center">Accounting unit</td>
<td class="colE" style="text-align:center">Frequency</td>
<td class="colF" style="text-align:center">Route</td>
<td class="colG" style="text-align:center">Duration(in days)</td>
<td class="colH" style="text-align:center"></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"></td>
</tr>
<% drugOrderProcesseds.each { drugOrderProcessed -> %>
<tr>
<td class="colA" style="text-align:center"><input type="text" id="drugKey${count}" name="drugKey${count}" value="${drugOrderProcessed.drugOrder.concept.name}" disabled></td>
<td class="colB" style="text-align:center"><input type="text" id="strength${count}" name="strength${count}" value="${drugOrderProcessed.dose}" disabled></td>
<td class="colM" style="text-align:center height=50px"><input type="text" id="dosage${count}" name="dosage${count}" style='width: 100%;height: 30px;' disabled></td>
<td class="colC" style="text-align:center"><input type="text" id="noOfTablet${count}" name="noOfTablet${count}" value="${drugOrderProcessed.noOfTablet}" disabled></td>
<td class="colD" style="text-align:center"><input type="text" id="type${count}" name="type${count}" value="${drugOrderProcessed.drugOrder.units}" disabled></td>
<td class="colE" style="text-align:center"><input type="text" id="frequncy${count}" name="frequncy${count}" value="${drugOrderProcessed.drugOrder.frequency}" disabled></td>
<td class="colF" style="text-align:center"><input type="text" id="route${count}" name="route${count}" value="${drugOrderProcessed.route.name}" disabled></td>
<td class="colG" style="text-align:center"><input type="text" id="durationnn${drugOrderProcessed.drugOrder.concept.name}" name="durationnn${drugOrderProcessed.drugOrder.concept.name}"></td>
<td class="colH" style="text-align:center"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForContinueRegimenSearch('${drugOrderProcessed.drugOrder.concept.name}');" /></td>
<td class="colI" style="text-align:center"></td>
<td class="colJ" style="text-align:center"></td>
<td class="colK" style="text-align:center"><input type="hidden" id="srNumber${count}" name="srNo" value="${count}"></td>
<td class="colL" style="text-align:center"><input type="hidden" id="drugConcept${count}" name="drugConcept${count++}" value="${drugOrderProcessed.regimenConcept.conceptId}"></td>
</tr>
<% } %>
</tbody>
</table>

</div>

<script type="text/javascript">
var patientId=${patient.patientId};

function guideForContinRegimen(){
jQuery('#guideDiv').empty();
var age=${patient.age};
if(age>14){
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/flow_chart_adult.jpg') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "guideDivAdult"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('guideDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=750&width=820&inlineId=guideDivAdult";
tb_show("Guide",url,false);
}
else{
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/flow_chart_child.jpg') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "guideDivChild"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('guideDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=500&width=840&inlineId=guideDivChild";
tb_show("Guide",url,false);
}
}

function artForContinRegimen(){
jQuery('#artRegimenDiv').empty();
var age=${patient.age};
var htmlText =  "<img src='${ ui.resourceLink('kenyaui', 'images/glyphs/tbRegimen.jpg') }' />"
var newElement = document.createElement('div');
newElement.setAttribute("id", "artRegimenDivAdult"); 
newElement.innerHTML = htmlText;
var fieldsArea = document.getElementById('artRegimenDiv');
fieldsArea.appendChild(newElement);
var url = "#TB_inline?height=500&width=1110&inlineId=artRegimenDivAdult";
tb_show("Art Regimen",url,false);
}

jQuery(document).ready(function(){
//jQuery('#changeRegimenSearch').remove();
var lenth="${drugOrderProcessedArr}".length;
var dopa="${drugOrderProcessedArr}".substring(1,lenth-1).split(", ");
contArr=dopa;
});
</script>

<style type="text/css">
  table { width: 100%; }
  td.colA { width: 10%; }
  td.colB { width: 10%; }
  td.colM { width: 5%; }
  td.colC { width: 10%; }
  td.colD { width: 10%; }
  td.colE { width: 10%; }
  td.colF { width: 10%; }
  td.colG { width: 5%; }
  td.colH { width: 5%; }
  td.colI { width: 5%; }
  td.colJ { width: 5%; }
  td.colK { width: 5%; }

</style>