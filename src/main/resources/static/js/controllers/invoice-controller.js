app.controller('invoiceController', ['$scope','$http','deliveryNoteService','$window',function($scope,$http,deliveryNoteService,$window) {
	$scope.heading='Invoice';
	$scope.invoice={
			invoiceID: '',
			invoiceDate: new Date(),
			customerID: '',
			customerName: '',
			notes: []
			};
	$scope.invoices=[];
	$scope.invoiceNotes=[]
	$scope.isSaved=false;
	$scope.simple=false;
	$scope.isInvoiceGenerated=false;
	$scope.createMode=true;
	$scope.viewMode=false;
	$scope.editMode=false;
	$scope.isSearch=false;
	
	$scope.changeMode=function(mode)
	{
		if(mode=='create')
		{
			$scope.createMode=true;
			$scope.viewMode=false;
			$scope.editMode=false;
		}
		else if(mode=='view')
		{
			$scope.createMode=false;
			$scope.viewMode=true;
			$scope.editMode=false;
		}
		else if(mode=='edit')
		{
			$scope.createMode=false;
			$scope.viewMode=true;
			$scope.editMode=false;
		}
	}
	//This service gets the Customer ID and Customer Name
	deliveryNoteService.getObject('customer').then(function successCallback(response) 
	{
		$scope.customers=response.data;
	});
	
	$scope.fetchInvoice=function()
	{
		deliveryNoteService.getObject('invoice',$scope.invoice.customerID,$scope.simple).then(function successCallback(response) 
		{ 
			var count=0;
			$scope.invoices=response.data;
			if($scope.invoices.length==0)
			{
				$scope.errorMsg="No Record Found!";
			}
			else
			{
				$scope.isSearch=true;
			}
			
			angular.forEach($scope.invoices,function(value,key){
				value.sno=++count;
			});
			
		});
	}
	
	$scope.loadCustomerName=function()
	{
		var _customerID=$scope.invoice.customerID;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerID==_customerID)
				{
					console.log('In Customer ID');
					$scope.invoice.customerName=value.customerName;
				}
		});
		
	};
	
	$scope.loadCustomerID=function()
	{
		var _customerName=$scope.invoice.customerName;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerName==_customerName)
				{
					console.log('In Customer Name');
					$scope.invoice.customerID=value.customerID;
				}
		});
		
	};
	
	$scope.fetchInvoiceNotes=function(index)
	{
		var inv=$scope.invoices[index];
		$scope.errorMsg=undefined;
		deliveryNoteService.getObject('invoice/generate',inv.invoiceID,$scope.simple)
		.then(function successCallback(response) 
		{
			var count=0;
			$scope.invoiceNotes=response.data;
			if($scope.invoiceNotes.length==0)
			{
				$scope.errorMsg="No Record Found!";
			}
			else
			{
				$scope.isView=true;
			}
			
			angular.forEach($scope.invoiceNotes,function(value,key){
				value.sno=++count;
			});
		},function failureCallback(response){
			console.log(response.status);
			
		});
		
	}
	
	$scope.downloadInvoiceNotes=function(index)
	{
		var invDonwload=$scope.invoices[index];
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/invoice/download/'+$scope.simple,
    	  data: invDonwload,
    	  responseType : 'arraybuffer',//THIS IS IMPORTANT
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
    		$scope.successMsg='Downloading Delivery Note ... ';
			$scope.errorMsg=undefined;
			var pdfFile = new Blob([ response.data ], {
                type : 'application/pdf'
            });
            var pdfUrl = URL.createObjectURL(pdfFile);
            $window.open(pdfUrl);
            //var printwWindow = $window.open(pdfUrl);
            //printwWindow.print();
    		
    	  }, function errorCallback(response) {
    	    // called asynchronously if an error occurs
    	    // or server returns response with an error status.
    		  $scope.errorMsg='Server Error!';
    		  $scope.successMsg=undefined;
    	  });
	}
	
	$scope.generateInvoice=function()
	{
		$scope.errorMsg=undefined;
		deliveryNoteService.generateInvoice($scope.invoice,$scope.simple).then(function successCallback(response) 
		{ 
			var count=0;
			var grossAmount=0;
			var cgstAmount=0;
			var sgstAmount=0;
			$scope.invoice.notes=response.data;
			if($scope.invoice.notes.length==0)
			{
				$scope.errorMsg="No Record Found!";
			}
			else
			{
				$scope.isInvoiceGenerated=true;
			}
			
			angular.forEach($scope.invoice.notes,function(value,key){
				value.sno=++count;
				grossAmount = grossAmount + value.netTotal;
				cgstAmount = cgstAmount + value.cgstAmount;
				sgstAmount = sgstAmount + value.sgstAmount;
			});
			if($scope.invoice.notes.length!=0)
				{
				$scope.isSaved=false;
				}
			$scope.invoice.grossAmount = grossAmount;
			$scope.invoice.cgstAmount = cgstAmount;
			$scope.invoice.sgstAmount = sgstAmount;
			$scope.invoice.gstAmount = $scope.invoice.cgstAmount + $scope.invoice.sgstAmount;
			$scope.invoice.netAmount=$scope.invoice.gstAmount + $scope.invoice.grossAmount;
			
		});
	}
	
	$scope.generateInvoiceID=function()
	{
		deliveryNoteService.generateID('invoice',$scope.invoice.invoiceDate,$scope.simple)
		.then(function successCallback(response) 
		{
			console.log('success'+response.data);
			$scope.invoice.invoiceID=response.data.invoiceID;
			
		},function failureCallback(response){
			console.log(response.status);
			$scope.invoice.invoiceID='Error';
		});
	}
	
	$scope.generateInvoiceID();
	
	$scope.saveInvoice=function()
	{
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/invoice/'+$scope.simple,
    	  data: $scope.invoice
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
    		if(response.data==true)
    		{
    			$scope.successMsg='Invoice Save Successfully!';
    			$scope.errorMsg=undefined;
    			$scope.isSaved=true;
    		}
    		else
			{
    			$scope.errorMsg='Unable to Save Invoice!';
    			$scope.successMsg=undefined;
    			$scope.isSaved=false;
			}
    	  }, function errorCallback(response) {
    	    // called asynchronously if an error occurs
    	    // or server returns response with an error status.
    		  $scope.errorMsg='Server Error!';
    		  $scope.successMsg=undefined;
    	  });
	}
	
	$scope.downloadInvoice=function()
	{
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/invoice/download/'+$scope.simple,
    	  data: $scope.invoice,
    	  responseType : 'arraybuffer',//THIS IS IMPORTANT
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
    		$scope.successMsg='Downloading Delivery Note ... ';
			$scope.errorMsg=undefined;
			var pdfFile = new Blob([ response.data ], {
                type : 'application/pdf'
            });
            var pdfUrl = URL.createObjectURL(pdfFile);
            $window.open(pdfUrl);
            //var printwWindow = $window.open(pdfUrl);
            //printwWindow.print();
    		
    	  }, function errorCallback(response) {
    	    // called asynchronously if an error occurs
    	    // or server returns response with an error status.
    		  $scope.errorMsg='Server Error!';
    		  $scope.successMsg=undefined;
    	  });
	}
	
}]);
