 $(document).ready(function () {  
	 
	 $("#vendorName").on("change",function(){
		  $('#vendorName').parent().first().next(".form-message").text("");
	 })
	 
	 var datePattern =/^\d{4}-\d{2}-\d{2}$/;
	 $("#formInput").on("submit", function(e) {
		 e.preventDefault();
		 var flag = true;
		 if(!$('#vendorName').val()){
			 $('#vendorName').parent().first().next(".form-message").text("Vendor name must be entered");
			 flag = false;
		 }
		 
		 if(!$('#inputCountry').val()){
			 $('#inputCountry').parent().first().next(".form-message").text("Country must be entered");
			 flag = false;
		 }
		 
		 if(!$('#staffTotal').val()){
			 $('#staffTotal').parent().first().next(".form-message").text("Staff Strenght must be entered");
			 flag = false;
		 }
		 
		  if(!$('#workDay').val()){
			 $('#workDay').parent().first().next(".form-message").text("Working Days must be entered");
			 flag = false;
		 }
		 var agreedCount = $("#agreedBody").children();
		  for (var i = 0; i < agreedCount.length; i++) {
        var startD = $("#schedules"+i+"\\.startDate").val();
     	var endD = $("#schedules"+i+"\\.endDate").val();
			   if(!$("#schedules"+i+"\\.startDate").val()){
				 $("#schedules"+i+"\\.startDate").next(".form-message").text("Start date must be entered");
				 flag = false;
		 	   }
	
	           if(!datePattern.test(startD)){
	            $("#schedules"+i+"\\.startDate").next(".form-message").text("Start date Invalid");
	           }
	           if(!datePattern.test(endD)){
	            $("#schedules"+i+"\\.endDate").next(".form-message").text("End date Invalid");
	           }
	           
		 	   if(!$("#schedules"+i+"\\.endDate").val()){
				 $("#schedules"+i+"\\.endDate").next(".form-message").text("End date must be entered");
				 flag = false;
		 	   }
		 	   
		 	   if(!$("#schedules"+i+"\\.numberStaff").val()){
				 $("#schedules"+i+"\\.numberStaff").next(".form-message").text("Number Staff must be entered");
				 flag = false;
		 	   }
		 	   
		 	   if(!$("#schedules"+i+"\\.recordPerDay").val()){
				 $("#schedules"+i+"\\.recordPerDay").next(".form-message").text("Record Per Day must be entered");
				 flag = false;
		 	   }
		 	   
		 	    if($("#schedules"+i+"\\.numberStaff").val()<1){
				 $("#schedules"+i+"\\.numberStaff").next(".form-message").text("Number of Staff must be a number and > 0");
				 flag = false;
		 	   }
		 	   
		 	   if($("#schedules"+i+"\\.recordPerDay").val()<1){
				 $("#schedules"+i+"\\.recordPerDay").next(".form-message").text("Record Per Day must be a number and > 0");
				 flag = false;
		 	   }
		  } 
		  if(flag){
			  this.submit();
		  }
		 
	});
  });
$(document).ready(function () {
  $(".js-select2").select2({
    closeOnSelect: true,
  });
});

  function showConfirmation(element) {
      var id = $(element).next().val();
      window.deleteId = id;
      window.element = element;
      $('#confirmModal').modal('show');
    }
    
    function confirmDel() {
      var id = window.deleteId;
      var element = window.element;
      if (id == 0) {
        delete1(element);
      } else {
        var vdID = $("#vendorID").val();
        var absolutePath = window.location.origin + "/schedule/delete?id=" + id + "&vdID=" + vdID;
        window.location.href = absolutePath;
      }
      $('#confirmModal').modal('hide');
      $("document").ready(function () {
        $(".modal-backdrop").remove();
      })
    }

    function delete1(e) {
      e.closest("tr").remove();
    }
3
/*var list = [];
var listID = [];
var list1 = [];
var listID1 = [];
$(document).ready(function () {
  add dropdown list value to array  
  $(".countryValue").each(function () {
    list.push($(this).html());
    listID.push($(this).val());
  });

  $(".vdNameValue").each(function () {
    list1.push($(this).html());
    listID1.push($(this).val());
  });
   add value to input field when select country  
  $("#selectCountry").change(function () {
    for (var i = 0; i < listID.length; i++) {
      if ($(this).val() === listID[i]) {
        $("#inputCountry").val(list[i]);
        break;
      } else {
        $("#inputCountry").val("All");
      }
    }
  });

  $("#selectName").change(function () {
    for (var i = 0; i < listID1.length; i++) {
      if ($(this).val() === listID1[i]) {
        $("#inputName").val(list1[i]);
        break;
      } else {
        $("#inputName").val("All");
      }
    }
  });

  $("#inputCountry").change(function () {
    console.log("checkUp");
    if ($(this).val().length > 50) {
      this.value = $(this).val().slice(0, 50);
    }
    for (var i = 0; i < list.length; i++) {
      if ($(this).val().toUpperCase() === list[i].toUpperCase()) {
        $("#selectCountry").val(listID[i]);
      }
    }
  });

  $("#inputName").change(function () {
    console.log("checkUp");
    if ($(this).val().length > 50) {
      this.value = $(this).val().slice(0, 50);
    }
    for (var i = 0; i < list1.length; i++) {
      if ($(this).val().toUpperCase() === list1[i].toUpperCase()) {
        $("#selectName").val(listID1[i]);
      }
    }
  });
});
$(function () {
  $("#inputCountry").autocomplete({
    source: list,
  });
  $("#inputName").autocomplete({
    source: list1,
  });
});*/

