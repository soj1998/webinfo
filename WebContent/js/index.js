var reJson;
$(function () {
    $('#anniu').click(function(e){
    	getJson(null,"test/ceshi");
    });
    $('#anniu2').click(function(e){
    	getJson(null,"test/ceshi2");
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

