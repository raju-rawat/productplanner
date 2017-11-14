app.factory('stateService',['$http',function($http){
	return {
		getStates: function()
		{
			// Simple GET request
			return $http({
			  method: 'GET',
			  url: 'api/states',
			})
		}
	}
	
}]);

app.factory('deliveryNoteService',['$http',function($http){
	return {
		getObject: function(objectType,id,simple)
		{
			var strURL='/'+objectType;
			if(id)
			{
				strURL +='/'+id;
			}
			if(simple!=undefined)
			{
				strURL +='/'+simple;
			}
			// Simple GET request
			return $http({
			  method: 'GET',
			  url: strURL
			})
		},
		getStateGSTType: function(customerID)
		{
			return $http({
				method: 'GET',
				url: '/api/get/stateGSTType/'+customerID
			})
		},
		generateInvoice: function(invoice,simple){
			return $http({
				method: 'POST',
				url: '/invoice/generate/'+simple,
				data: invoice
			})
		},
		generateID: function(objectType,date,simple)
		{
			var strURl=strURl='/'+objectType+'/generate/Id';
			if(date!=null && date!='')
			{
				strURl=strURl+'/'+date+'/'+simple;
			}
			
			return $http({
				method: 'GET',
				url: strURl
			})
		},
		submit: function(strURl,strData)
		{
			return $http({
				method: 'POST',
				url: strURl,
				data: strData
			})
		}
	}
	
}]);



app.factory('downloadService',function getStream(params){
	return {
		download: function()
		{
			console.log("RUNNING");
		    var deferred = $q.defer();

		    $http({
		        url:'/api/deliveryNote/generate',
		        method:"POST",
		        data:params,
		        headers:{'Content-type': 'application/json'},
		        responseType : 'arraybuffer',//THIS IS IMPORTANT
		       })
		       .success(function (data) {
		           console.debug("SUCCESS");
		           deferred.resolve(data);
		       }).error(function (data) {
		            console.error("ERROR");
		            deferred.reject(data);
		       });

		    return deferred.promise;
		}
	}
    
   });
