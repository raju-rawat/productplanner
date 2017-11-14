app.controller('reportController', ['$scope','deliveryNoteService','$window','$http',function($scope,deliveryNoteService,$window,$http) {
	$scope.simple=false;
	$scope.receipts={};
	$scope.activeMode='';
	$scope.modes={
			sales : false,
			partyWise : false,
			stockWise : false,
			receipt : false
	};
	
	$scope.changeMode=function(activeMode)
	{
		$scope.activeMode=activeMode;
		angular.forEach($scope.modes,function(value,mode){
			$scope.modes[mode] = mode==activeMode?true:false;
		})
	}
	
	$scope.generateReport=function()
	{
		if($scope.modes.receipt)
		{
			deliveryNoteService.submit('/receipt',$scope.report)
			.then(function successCallback(response){
				$scope.receipts=response.data;
				
				if($scope.receipts.length==0)
					{
					  $window.alert('No Record Found');
					}
				else
					{
					var count=0;
					angular.forEach($scope.receipts,function(value,key){
						value.sno = ++count;
					})
					}
			},function failureCallback(){
				$window.alert('Server Error');
			})
		}
		else if($scope.modes.sales)
		{
			deliveryNoteService.submit('/invoice/report/'+$scope.simple,$scope.report)
			.then(function successCallback(response){
				$scope.report.invoices=response.data.invoices;
				$scope.report.variants=response.data.variants;
				$scope.report.listofVariantInstancesSum=response.data.listofVariantInstancesSum;
				if($scope.report.invoices.length==0)
					{
					  $window.alert('No Record Found');
					}
				else
					{
					var count=0;
					angular.forEach($scope.report.invoices,function(value,key){
						value.sno = ++count;
					})
					}
			},function failureCallback(){
				$window.alert('Server Error');
			})
		}
		
	}
	$scope.exportReport=function()
	{
		var strData;
		if($scope.modes.receipt)
		{
			strData=$scope.report;
		}
		if($scope.modes.sales)
		{
			strData=$scope.report;
		}
		$http({
	    	  method: 'POST',
	    	  url: '/reports/'+$scope.activeMode,
	    	  data: strData,
	    	  responseType : 'arraybuffer',//THIS IS IMPORTANT
	    	}).then(function successCallback(response) {
	    	   
				var data = new Blob([ response.data ], {
	                type : 'application/ms-excel'
	            });
	            var disposition = response.headers('Content-Disposition');
                var filename = getFileNameFromHttpResponse(response);
                download(data,filename);
                //window.saveAs(data, filename);
	            //$window.open(excelUrl);
	            //$window.location=excelUrl;
	    		
	    	  }, function errorCallback(response) {
	    		  $window.alert('Server Error');
	    	  });
	}
	
	function getFileNameFromHttpResponse(httpResponse) {
	      var contentDispositionHeader = httpResponse.headers('Content-Disposition');
	      var result = contentDispositionHeader.split(';')[1].trim().split('=')[1];
	      return result.replace(/"/g, '');
	  }
	function download(data, filename)
	{
	        var a = document.createElement('a');
	        a.href = window.URL.createObjectURL(data);
	        a.download = filename;
	        a.click();
	}
}]);