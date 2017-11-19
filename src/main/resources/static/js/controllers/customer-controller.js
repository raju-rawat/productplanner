
app.controller('customerController', ['$scope','stateService','$http','deliveryNoteService','$window',function($scope,stateService,$http,deliveryNoteService,$window) {
	$scope.heading='Add Customer';
	$scope.customer={
			startDate: new Date()
	}
	$scope.customers=[];
	$scope.deletedCustomers=[];
	$scope.updatedCustomers=[];
	$scope.modes={
			add : true,
			view : false,
			edit : false,
	};
	
	$scope.changeMode=function(activeMode)
	{
		angular.forEach($scope.modes,function(value,mode){
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		if($scope.modes.view || $scope.modes.edit)
		{
			$scope.viewCustomers();
		}
	}
	
	$scope.generateCustomerID=function()
	{
		deliveryNoteService.generateID('customer')
		.then(function successCallback(response) 
		{
			$scope.customer.customerID=response.data.customerID;
			
		},function failureCallback(response){
			$window.alert('Server Error!');
		});
	}
	
	$scope.generateCustomerID();
	
	//Service returns a promise object, 
	//So it is better to use 'then' in controller and not in service.
	//State should get loaded along with the controller
	stateService.getStates().then(function successCallback(response) 
	{
		$scope.states=response.data;
	});
	
	$scope.addCustomer=function(customer)
	{
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/customer',
    	  data: customer
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
    		$window.alert('Customer Added Successfully!');
			$scope.customer={};
			$scope.generateCustomerID();
    		
    	  }, function errorCallback(response) {
    		  $window.alert('Error Adding Customer!');
    	  });
	}
	
	$scope.viewCustomers=function()
	{
		//This service gets all the customers
		deliveryNoteService.getObject('customer/all').then(function successCallback(response) 
		{
			var count=0;
			$scope.customers=response.data;
			angular.forEach($scope.customers,function(value,key){
				value.sno=++count;
			});
		});
	}
	
	$scope.deleteCustomer=function(index)
	{
		var customer=$scope.customers[index];
		$scope.deletedCustomers.push(customer.customerID);
		$scope.customers.splice(index,1);
		resetSno();
	}
	
	$scope.updateCustomer=function(index)
	{
		var present=false;
		var customer=$scope.customers[index];
		angular.forEach($scope.updatedCustomers,function(value,key){
			if(customer == value)
			{
				present=true;
			}
		});
		if(!present)
		{
			$scope.updatedCustomers.push(customer);
		}
	}
	
	$scope.updateCustomers=function()
	{
		if($scope.deletedCustomers.length==0 && $scope.updatedCustomers.length==0)
		{
			$window.alert('No customer has been modified!');
		}
		else
		{
			var payload={
					deletedCustomers: $scope.deletedCustomers,
					updatedCustomers: $scope.updatedCustomers
			};
			// Simple POST request
	    	$http({
	    	  method: 'POST',
	    	  url: '/customer/update',
	    	  data: payload
	    	}).then(function successCallback(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
	    		$window.alert('Customer Updated Successfully!');
	    		$scope.deletedCustomers=[];
	    		$scope.updatedCustomers=[];
	    	  }, function errorCallback(response) {
	    		  $window.alert('Server Error!');
	    	  });
		}
	}
	
	resetSno=function()
	{
		var sno=0;
		angular.forEach($scope.customers,function(customer,key){
			customer.sno=++sno;
		});
	}
	$scope.reset = function(customerform) {
        if (customerform) {
        	customerform.$setPristine();
        	customerform.$setUntouched();
        	$scope.customer={
        			startDate: new Date()
        	}
        	$scope.successMsg=undefined;
        	$scope.errorMsg=undefined;
        	$scope.generateCustomerID();
        }
    };
}]);
 