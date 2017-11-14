 
app.controller('installmentReceiptController', ['$scope','deliveryNoteService','$http','$window',function($scope,deliveryNoteService,$http,$window) {
	$scope.receipt={
			receiptID: '',
			receiptDate: new Date(),
			customerID: '',
			openingBalance: 0,
			comment: '',
			closingBalance: 0,
			totalAmtPaid: 0,
			};

	$scope.simple=false;
	
	
	//This service gets the Customer ID and Customer Name
	deliveryNoteService.getObject('customer').then(function successCallback(response) 
	{
		$scope.customers=response.data;
	});
	
	$scope.loadCustomerName=function()
	{
		var _customerID=$scope.receipt.customerID;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerID==_customerID)
				{
					$scope.receipt.customerName=value.customerName;
				}
		});
		$scope.generateReceipt();
	};
	
	$scope.loadCustomerID=function()
	{
		var _customerName=$scope.receipt.customerName;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerName==_customerName)
				{
					$scope.receipt.customerID=value.customerID;
				}
		});
		$scope.generateReceipt();
	};
	$scope.calTotalAmtPaid=function(index)
	{
		$scope.receipt.closingBalance=0;
		var invoice=$scope.receipt.invoices[index];
		invoice.currentBalance=invoice.previousBalance - invoice.invoiceAmtPaid;
		$scope.receipt.totalAmtPaid =0;
		angular.forEach($scope.receipt.invoices,function(value,key){
			if(value.invoiceAmtPaid)
				{
					$scope.receipt.totalAmtPaid +=value.invoiceAmtPaid;
					$scope.receipt.closingBalance+=value.currentBalance;
				}
			
		});
	};
	$scope.generateReceipt=function()
	{
		$scope.receipt.closingBalance=0;
		$scope.receipt.totalAmtPaid=0;
		$scope.errorMsg=undefined;
		deliveryNoteService.getObject('invoice',$scope.receipt.customerID,$scope.simple).then(function successCallback(response) 
		{ 
			var count=0;
			$scope.receipt.openingBalance=0;
			$scope.receipt.invoices=response.data;
			if($scope.receipt.invoices.length==0)
			{
				$window.alert('No Record Found!');
			}
			
			angular.forEach($scope.receipt.invoices,function(value,key){
				value.sno=++count;
				if(!value.previousBalance)
				{
					value.previousBalance=value.netAmount;
				}
				$scope.receipt.openingBalance+=value.netAmount;
			});
			
		});
	
	};
	
	$scope.saveReceipt=function()
	{
    	$http({
    	  method: 'POST',
    	  url: '/receipt/save/'+$scope.simple,
    	  data: $scope.receipt
    	}).then(function successCallback(response) {
    		if(response.data==true)
    		{
    			$scope.isSaved=true;
    			$window.alert('Receipt Saved Successfully!');
    		}
    		else
			{
    			$scope.isSaved=false;
    			$window.alert('Unable to Save Receipt!');
			}
    	  }, function errorCallback(response) {
    		  $window.alert('Server Error!');
    	  });
	}
	
	$scope.generateReceiptID=function()
	{
		deliveryNoteService.generateID('receipt',$scope.receipt.receiptDate,$scope.simple)
		.then(function successCallback(response) 
		{
			$scope.receipt.receiptID=response.data.receiptID;
			
		},function failureCallback(response){
			$window.alert('Server Error!');
		});
		if($scope.receipt.customerID.length)
			{
				$scope.generateReceipt();
			}
	};
	$scope.generateReceiptID();	
	
	$scope.downloadReceipt=function()
	{
    	$http({
    	  method: 'POST',
    	  url: '/receipt/download',
    	  data: $scope.receipt,
    	  responseType : 'arraybuffer',//THIS IS IMPORTANT
    	}).then(function successCallback(response) {
			var pdfFile = new Blob([ response.data ], {
                type : 'application/pdf'
            });
            var pdfUrl = URL.createObjectURL(pdfFile);
            $window.open(pdfUrl);
    	  }, function errorCallback(response) {
    		  $window.alert('Server Error!');
    	  });
	}
}]);
