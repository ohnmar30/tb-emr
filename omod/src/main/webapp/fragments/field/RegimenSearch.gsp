<%
	ui.includeJavascript("kenyaemr", "controllers/drugRegimenController.js")
	
	def strengths = ["","150/75/400/275 mg","150/75/275 mg","60/30/150 mg","500/125 mg","150/75 mg","60/60 mg","600 mg","500 mg","400 mg","300 mg","250 mg",
	                 "100 mg","50 mg","100 g jar","4 g sachets","500/500 mg vial","1 g vial"]
	                 
	def types = ["","tab","ml","vial","sachet","scoop"]
	
	def frequencys = ["","Once daily","Twice daily","Three times daily","Four times daily","Early morning","Night time","3 times per week","6 times per week","prn","stat"]
	
	def strengthOptions = strengths.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	
	def typeOptions = types.collect( { """<option value="${ it }">${ it }</option>""" } ).join()
	
	def frequencyOptions = frequencys.collect( { """<option value="${ it }">${ it }</option>""" } ).join()

%>

<div id="regimenSearch" ng-controller="DrugCtrl" data-ng-init="init()">

<table>
<tbody>
<tr>
<td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="guide" name="guide" value="Guide" onClick="guidee();" /></td>
<!-- <td><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" id="artRegimen" name="artRegimen" value="TB Regimen" onClick="artRegimenn();" /></td> -->
</tr>

<tr>
<td colspan="3"><input type="radio" id="regimen1" name="regimen" value="regimen1" onClick="regimenSelection(this.value);" />6(Amk Z Lfx Eto Cs)/14(Lfx Eto Cs Z)</td>
</tr>
<tr>
<td colspan="3"><input type="radio" id="regimen2" name="regimen" value="regimen2" onClick="regimenSelection(this.value);" />6(Amk Z Lfx Eto Cs PAS)/14(Lfx Eto Cs Z PAS)</td>
</tr>

<tr style="max-width:100%">
<td class="colA" style="text-align:center  height=50px">Drug</td>
<td class="colB" style="text-align:center  height=50px">Strength per unit</td>
<td class="colM" style="text-align:center  height=50px">Dosage(mg)/day</td>
<td class="colC" style="text-align:center  height=50px">Quantity of Unit</td>
<td class="colD" style="text-align:center  height=50px">Accounting unit</td>
<td class="colE" style="text-align:center  height=50px">Frequency</td>
<td class="colF" style="text-align:center  height=50px">Route</td>
<td class="colG" style="text-align:center  height=50px">Duration(in days)</td>
<td class="colH" style="text-align:center  height=50px"></td>
<td class="colI" style="text-align:center  height=50px"></td>
<td class="colJ" style="text-align:center  height=50px"></td>
<td class="colK" style="text-align:center  height=50px"></td>
</tr>

<% drugOrderProcessedd.each { drugOrderProcessed -> %>
<tr id="row${count}">
<td class="colA" style="text-align:center height=50px"><input type="text" id="drugKey${count}" name="drugKey${count}" style='width: 100%;height: 30px;' ></td>
<td class="colB" style="text-align:center height=50px"><select style='width: 100%;height: 30px;' id="strength${count}"  name="strength${count}">${ strengthOptions }</select></td>
<td class="colM" style="text-align:center height=50px"><input type="text" style='width: 100%;height: 30px;' id="dosage${count}" name="dosage${count}" ></td>
<td class="colC" style="text-align:center height=50px"><input type="text"  style='width: 100%;height: 30px;' id="noOfTablet${count}" name="noOfTablet${count}" ></td>
<td class="colD" style="text-align:center height=50px"><select style='width: 100%;height: 30px;' id="type${count}"  name="type${count}">${typeOptions}</select></td>
<td class="colE" style="text-align:center height=50px"><select style='width: 100%;height: 30px;' id="frequncy${count}"  name="frequncy${count}">${frequencyOptions}"</select></td>
<td class="colF" style="text-align:center height=50px">
<select id="route${count}" name="route${count}" style='width: 100%;height: 30px;'>
<option value="" > </option>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center height=50px""><input type="text" id="durationn${count}" name="durationn${count}" style='width: 100%;height: 30px;'></td>
<td class="colH" style="text-align:center height=50px""><input type="button" style='width: 100%;height: 30px;' class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" onClick="drugInfo('${count}');"/></td>
<td class="colI" style="text-align:center height=50px""></td>
<td class="colJ" style="text-align:center height=50px""><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" style="color:red;width: 100%;height: 30px;" id="remove" name="remove" value="[X]" onClick="removee(${count});" /></td>
<td class="colK" style="text-align:center height=50px""><input type="hidden" id="srNumber${count}" name="srNo" value="${count}"></td>
<td class="colL" style="text-align:center height=50px""><input type="hidden" id="drugConcept${count}" name="drugConcept${count++}"></td>
</tr>
<% } %>
</tbody>
</table>
<fieldset  data-ng-repeat="choice in choices">
<table>
<tbody>
<tr>
<td class="colA" ><input type="text" style='width: 100%;height: 30px;' ng-model="drugKey" id={{choice.drugKey}} name={{choice.drugKey}} placeholder="search box" uib-typeahead="drug as drug.drugName for drug in myDrug | filter : drugKey" typeahead-on-select="drugSearch(drugKey,choice);"></td>
<td class="colB" ><select style='width: 100%;height: 30px;' id={{choice.strength}}  name={{choice.strength}}><option value="" />${ strengthOptions }</select></td>
<td class="colM" style="text-align:center height=50px"><input type="text" style='width: 100%;height: 30px;' id="dosage${count}" name="dosage${count}"></td>
<td class="colC" ><input type="text" style='width: 100%;height: 30px;' ng-model="noOfTablet" id={{choice.noOfTablet}} name={{choice.noOfTablet}}></td>
<td class="colD" ><select style='width: 100%;height: 30px;' type="text" ng-model="type" id={{choice.type}} name={{choice.type}}>${typeOptions}</select></td>
<td class="colE" ><select style='width: 100%;height: 30px;' type="text" ng-model="frequncy" id={{choice.frequncy}} name={{choice.frequncy}} >${ frequencyOptions }</select></td>
<td class="colF" style="text-align:center  height=50px" >
<select ng-model="route" id={{choice.route}} name={{choice.route}} style='width: 100%;height: 30px;'>
<% routeConAnss.each { routeConAns -> %>
<option value="${routeConAns.answerConcept.conceptId}">${routeConAns.answerConcept.name}</option>
<% } %>
</select>
</td>
<td class="colG" style="text-align:center height=50px"><input type="text" ng-model="duration" id={{choice.durationn}} name={{choice.durationn}} style='width: 100%;height: 30px;'></td>
<td class="colH" style="text-align:center height=50px"><input type="button" style='width: 100%;height: 30px;' class="ui-button ui-widget ui-state-default ui-corner-all" id="info" name="info" value="Info" ng-click="artDrugInfoForRegimenSearchh(drugKey);" /></td>
<td class="colI" style="text-align:center height=50px"><input type="button" class="ui-button ui-widget ui-state-default ui-corner-all" style="color:green;font-size:17px;width: 100%;height: 30px;" id="add" name="add" value="Add" ng-click="addNewChoice()"/></td>
<td class="colJ" style="text-align:center height=50px"><input type="button"  class="ui-button ui-widget ui-state-default ui-corner-all" style="color:red;width: 100%;height: 30px;" id="remove" name="remove" value="[X]" ng-click="removeChoice(choice)" /></td>
<td class="colK" style="text-align:center height=50px"><input type="hidden" id={{choice.srNumber}} name="srNo" value={{choice.srNo}}></td>
<td class="colL" style="text-align:center height=50px"><input type="hidden" id={{choice.drugConcept}} name={{choice.drugConcept}} value={{choice.drugConcept}}></td>
</tr>
</fieldset>
</tbody>
</table>
<table>
<tbody>
<tr>
<td class="colA" style="text-align:center"><input type="hidden"  id="regimenNo" name="regimenNo"></td>
</tr>
</tbody>
</table>

</div>

<script type="text/javascript">
var patientId=${patient.patientId};

function guidee(){
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

function artRegimenn(){
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

function regimenSelection(value){

if(value=='regimen1'){
jQuery('#drugKey1').val('${regimenDetails1.drugName}');
jQuery('#strength1').val('${regimenDetails1.strength}');
jQuery('#dosage1').val('750');
jQuery('#type1').val('${regimenDetails1.formulation}');
jQuery('#frequncy1').val('${regimenDetails1.frequency}');
jQuery('#route1').val('${regimenDetails1.route}');
jQuery('#drugConcept1').val('${regimenDetails1.drugConcept}');

jQuery('#drugKey2').val('${regimenDetails2.drugName}');
jQuery('#strength2').val('${regimenDetails2.strength}');
jQuery('#dosage2').val('1000');
jQuery('#type2').val('${regimenDetails2.formulation}');
jQuery('#frequncy2').val('${regimenDetails2.frequency}');
jQuery('#route2').val('${regimenDetails2.route}');
jQuery('#drugConcept2').val('${regimenDetails2.drugConcept}');

jQuery('#drugKey3').val('${regimenDetails3.drugName}');
jQuery('#strength3').val('${regimenDetails3.strength}');
jQuery('#dosage3').val('750');
jQuery('#type3').val('${regimenDetails3.formulation}');
jQuery('#frequncy3').val('${regimenDetails3.frequency}');
jQuery('#route3').val('${regimenDetails3.route}');
jQuery('#drugConcept3').val('${regimenDetails3.drugConcept}');

jQuery('#drugKey4').val('${regimenDetails4.drugName}');
jQuery('#strength4').val('${regimenDetails4.strength}');
jQuery('#dosage4').val('500');
jQuery('#type4').val('${regimenDetails4.formulation}');
jQuery('#frequncy4').val('${regimenDetails4.frequency}');
jQuery('#route4').val('${regimenDetails4.route}');
jQuery('#drugConcept4').val('${regimenDetails4.drugConcept}');

jQuery('#drugKey5').val('${regimenDetails5.drugName}');
jQuery('#strength5').val('${regimenDetails5.strength}');
jQuery('#dosage5').val('500');
jQuery('#type5').val('${regimenDetails5.formulation}');
jQuery('#frequncy5').val('${regimenDetails5.frequency}');
jQuery('#route5').val('${regimenDetails5.route}');
jQuery('#drugConcept5').val('${regimenDetails5.drugConcept}');

jQuery('#drugKey6').val("");
jQuery('#strength6').val("");
jQuery('#dosage6').val("");
jQuery('#type6').val("");
jQuery('#frequncy6').val("");
jQuery('#route6').val("");
jQuery('#drugConcept6').val("");

jQuery('#regimenNo').val("Standard Regimen 1");

var indx = selectedSerialNoArr.indexOf(1);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }

indx = selectedSerialNoArr.indexOf(2);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
indx = selectedSerialNoArr.indexOf(3);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
indx = selectedSerialNoArr.indexOf(4);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
indx = selectedSerialNoArr.indexOf(5);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
indx = selectedSerialNoArr.indexOf(6);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
selectedSerialNoArr.push(1);
selectedSerialNoArr.push(2);
selectedSerialNoArr.push(3);
selectedSerialNoArr.push(4);
selectedSerialNoArr.push(5);
}
else if(value=='regimen2'){
jQuery('#drugKey1').val('${regimenDetails1.drugName}');
jQuery('#strength1').val('${regimenDetails1.strength}');
jQuery('#dosage1').val('750');
jQuery('#type1').val('${regimenDetails1.formulation}');
jQuery('#frequncy1').val('${regimenDetails1.frequency}');
jQuery('#route1').val('${regimenDetails1.route}');
jQuery('#drugConcept1').val('${regimenDetails1.drugConcept}');

jQuery('#drugKey2').val('${regimenDetails2.drugName}');
jQuery('#strength2').val('${regimenDetails2.strength}');
jQuery('#dosage2').val('1000');
jQuery('#type2').val('${regimenDetails2.formulation}');
jQuery('#frequncy2').val('${regimenDetails2.frequency}');
jQuery('#route2').val('${regimenDetails2.route}');
jQuery('#drugConcept2').val('${regimenDetails2.drugConcept}');

jQuery('#drugKey3').val('${regimenDetails3.drugName}');
jQuery('#strength3').val('${regimenDetails3.strength}');
jQuery('#dosage3').val('750');
jQuery('#type3').val('${regimenDetails3.formulation}');
jQuery('#frequncy3').val('${regimenDetails3.frequency}');
jQuery('#route3').val('${regimenDetails3.route}');
jQuery('#drugConcept3').val('${regimenDetails3.drugConcept}');

jQuery('#drugKey4').val('${regimenDetails4.drugName}');
jQuery('#strength4').val('${regimenDetails4.strength}');
jQuery('#dosage4').val('500');
jQuery('#type4').val('${regimenDetails4.formulation}');
jQuery('#frequncy4').val('${regimenDetails4.frequency}');
jQuery('#route4').val('${regimenDetails4.route}');
jQuery('#drugConcept4').val('${regimenDetails4.drugConcept}');

jQuery('#drugKey5').val('${regimenDetails5.drugName}');
jQuery('#strength5').val('${regimenDetails5.strength}');
jQuery('#dosage5').val('500');
jQuery('#type5').val('${regimenDetails5.formulation}');
jQuery('#frequncy5').val('${regimenDetails5.frequency}');
jQuery('#route5').val('${regimenDetails5.route}');
jQuery('#drugConcept5').val('${regimenDetails5.drugConcept}');

jQuery('#drugKey6').val('${regimenDetails6.drugName}');
jQuery('#strength6').val('${regimenDetails6.strength}');
jQuery('#dosage6').val('8000');
jQuery('#type6').val('${regimenDetails6.formulation}');
jQuery('#frequncy6').val('${regimenDetails6.frequency}');
jQuery('#route6').val('${regimenDetails6.route}');
jQuery('#drugConcept6').val('${regimenDetails6.drugConcept}');

jQuery('#regimenNo').val("Standard Regimen 2");


var indx = selectedSerialNoArr.indexOf(1);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }

indx = selectedSerialNoArr.indexOf(2);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
indx = selectedSerialNoArr.indexOf(3);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
indx = selectedSerialNoArr.indexOf(4);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
	    
indx = selectedSerialNoArr.indexOf(5);
	    if (indx > -1) {
	    	selectedSerialNoArr.splice(indx, 1);
	    }
selectedSerialNoArr.push(1);
selectedSerialNoArr.push(2);
selectedSerialNoArr.push(3);
selectedSerialNoArr.push(4);
selectedSerialNoArr.push(5);
selectedSerialNoArr.push(6);
}
}
</script>

<style type="text/css">
  table { width: 100%;max-width:100%; }
  td.colA { width: 10%; }
  td.colB { width: 10%; }
  td.colM { width: 10%; }
  td.colC { width: 10%; }
  td.colD { width: 10%; }
  td.colE { width: 10%; }
  td.colF { width: 10%; }
  td.colG { width: 10%; }
  td.colH { width: 5%; }
  td.colI { width: 5%; }
  td.colJ { width: 5%; }
  td.colK { width: 5%; }
</style>

