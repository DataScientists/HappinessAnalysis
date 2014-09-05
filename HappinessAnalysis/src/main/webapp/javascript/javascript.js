/**
 * 
 */
var currentAnalysis = {totalscore:0};
var currentItems = [{id:0,score:0}];

$(document).ready(function() {
	$("#login").on("click", "button",function(e) {
		var login = {};
		login.name = $("input#loginName").val();
		login.password = $("input#password").val();
		login.Page = "1";
		$.post("PageServlet", JSON.stringify(login), function(data){
			$("#login").hide();
			var screentesttypes = "";
			for (var i=0;i<data.length;i++) {
				screentesttypes += "<div onclick='showScreeningTest();' id="+data[i].id+">" +
						"<button id=save class='buttonSave btn btn-primary btn-large' value='" + data[i].name + "'>" + data[i].name + "</button>" +
								"</div>";
			}
			$("#mainScreen").append(screentesttypes);
			
		});
	});
});

function showScreeningTest(){
	var screeningTest = {};
	screeningTest.screeningTest = $(this).id;
	screeningTest.Page = "2";
	
	$.post("PageServlet", JSON.stringify(screeningTest), function(data){
		$("#mainScreen").empty();
		var screentestquestions = "";
		for (var i=0;i<data.length;i++) {
			screentestquestions += "<div class='screeningtestitems'>" 
			+ "<div class='categoryHeading'>"+ data[i].categoryname + "</div>"
			+ "<div class='questionHeading'>"+ data[i].name + "</div>"
			+ "<div class='question'>" + data[i].one + "</div>" 
			+ "<div class='question'>" + data[i].two + "</div>" 
			+ "<div class='question'>" + data[i].three + "</div>" 
			+ "<div class='question'>" + data[i].four + "</div>  " 
			+ "</div>";
			screentestquestions += "<div class='screeningtestitemsScore' id='"+data[i].theid + "' >" 
			+ "<div class='questionScoreOne' onClick=analyse(this,1); >1</div>" 
			+ "<div class='questionScoreTwo' onClick=analyse(this,2);>2</div>" 
			+ "<div class='questionScoreThree' onClick=analyse(this,3);>3</div>" 
			+ "<div class='questionScoreFour' onClick=analyse(this,4);>4</div>  " 
			+ "</div>";
		}
		$("#mainScreen").append(screentestquestions);
		var analysisHtml = "<div>" +
				"<div class='projectname'><input type=text id=analysisname value='default' /></div>" +
				"<div id=currentScore class='questionScoreBlank'> Total Score: "+currentAnalysis.totalscore+"</div>" +
				"<div class='footerSave'><button id=save class='buttonSave btn btn-primary btn-large' onclick='saveAnalysis();'value='save'>Save</button></div>" +
				"</div>";
		$("#startanalysis").append(analysisHtml);
	});
	
}
function analyse(thiz, points){
	var theScore = 0;
	var itemIdSelected = $(thiz).parent().attr('id');
	$(thiz).css('box-shadow','10px 10px 5px #888888');
	var itemSelected = {};
	itemSelected.id = itemIdSelected;
	itemSelected.score = points;
	for (var i=0;i<currentItems.length;i++) {
		var item = currentItems[i];
		if(item.id==itemIdSelected){
			currentItems.splice(i, 1);
		}
	}
	currentItems.push(itemSelected);
	
	for (i=0;i<currentItems.length;i++) {
		theScore += currentItems[i].score;
	}
	
	currentAnalysis.totalscore = theScore;
	var neutralScore = (i-1)*3;
	if(theScore>neutralScore){
		$("#currentScore").attr('class', 'questionScoreFour');
	}else if(theScore==neutralScore){
		$("#currentScore").attr('class', 'questionScoreThree');
	}else if(theScore<neutralScore){
		$("#currentScore").attr('class', 'questionScoreOne');
	}
	$("#currentScore").html('Total Score: '+currentAnalysis.totalscore+' ('+(theScore-neutralScore)+')');	
}
function saveAnalysis(){
	
	var saveAnalysis = {};
	saveAnalysis.Page = "3";
	saveAnalysis.analysisName = $("#analysisname").val();
	saveAnalysis.items = currentItems;
	$.post("PageServlet", JSON.stringify(saveAnalysis), function(data){
		$("#mainScreen").empty();
		var screentestquestions = "";
		for (var i=0;i<data.length;i++) {
			screentestquestions += "<div class='screeningtestitems' title='"+ data[i].name + "'>" 
			+ "<div class='categoryHeading'>"+ data[i].categoryname + "</div>";
			screentestquestions += "<div class='screeningtestitemsScore'  id='"+data[i].theid + "' >";
			if(data[i].score==1){
				screentestquestions += "<div class='questionScoreOne'  ></div>" 
				+ "<div class='questionScoreBlank' ></div>" 
				+ "<div class='questionScoreBlank' ></div>" 
				+ "<div class='questionScoreBlank' ></div>";
			}else if(data[i].score==2){
				screentestquestions += "<div class='questionScoreBlank'  ></div>" 
				+ "<div class='questionScoreTwo' ></div>" 
				+ "<div class='questionScoreBlank' ></div>" 
				+ "<div class='questionScoreBlank' ></div> ";
			}else if(data[i].score==3){
				screentestquestions += "<div class='questionScoreBlank'  ></div>" 
				+ "<div class='questionScoreBlank' ></div>" 
				+ "<div class='questionScoreThree' ></div>" 
				+ "<div class='questionScoreBlank' ></div>  ";
			}else if(data[i].score==4){
				screentestquestions += "<div class='questionScoreBlank'  ></div>" 
				+ "<div class='questionScoreBlank' ></div>" 
				+ "<div class='questionScoreBlank' ></div>" 
				+ "<div class='questionScoreFour' ></div>";
			}
			screentestquestions += "</div>";
		}
		$("#mainScreen").append(screentestquestions);
		$("#save").hide();
	});
	$( document ).tooltip();
}