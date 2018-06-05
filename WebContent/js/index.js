var reJson;
$(function () {
    $('#anniu').click(function(e){
    	alert("1");
    });
    
});



function getJson(DATA,URL){
	 $.ajax({
	  type:'POST',
	  url:URL,
	  async:false,
	  data : DATA,
	  beforeSend : function () {
			
		},
	  success:function(responseData){
		  reJson = responseData;
	  },
	 });
	 return reJson;
}

