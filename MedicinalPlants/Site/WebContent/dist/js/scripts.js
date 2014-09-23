

// Function to get the Min value in Array
Array.min = function(array) {
    return Math.min.apply(Math, array);
};
    
/**************************************************************/
/* Prepares the cv to be dynamically expandable/collapsible   */
/**************************************************************/
$(function prepareList() {
	$('#expList').find('li:has(ul)').unbind('click').click(function(event) {
		if(this == event.target) {
			$(this).toggleClass('expanded');
			$(this).children('ul').toggle('medium');
		}
		return false;
	}).addClass('collapsed').removeClass('expanded').children('ul').hide();
 
	//Hack to add links inside the cv
	$('#expList a').unbind('click').click(function() {
		//window.open($(this).attr('href'));
	    //$(".browsinglink").bind('click', false);
		//alert($(this).attr('href'));
		
		 $("#divplant").load($(this).attr('href'));
		 //setupBlocks();
		 
		return false;
	});
	//Create the button functionality
	$('#expandList').unbind('click').click(function() {
		$('.collapsed').addClass('expanded');
		$('.collapsed').children().show('medium');
	})
	$('#collapseList').unbind('click').click(function() {
		$('.collapsed').removeClass('expanded');
		$('.collapsed').children().hide('medium');
	})
});

/**************************************************************/
/* Functions to execute on loading the document               */
/**************************************************************/
$(document).ready( function() {
    prepareList();

    
	 //$("#divplant").load("test.html");
})

function testFunction(plant) {
	$('body').scrollTop(0);
	
	$("body").css("overflow", "hidden");
	
    $( "#light" ).load("plant_info.jsp?p="+plant);
    
    $( "#fade" ).click(function() {
    	  $("#light").hide()
    	  $("#fade").hide()
    	  $("body").css("overflow", "auto");
    });
}

function innerLoadPlant(plant) {
	
    $( "#light" ).load("plant_info.jsp?p="+plant);
    
    /*$( "#fade" ).click(function() {
    	  $("#light").hide()
    	  $("#fade").hide()
    });*/
}


