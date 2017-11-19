 
app.controller('productController', ['$scope','$http','deliveryNoteService','$window',function($scope,$http,deliveryNoteService,$window) {
	$scope.product={
			effectiveDate: new Date()
	}
	$scope.modes={
			add : true,
			view : false,
			edit : false,
	};
	
	$scope.products=[];
	$scope.deletedProducts=[];
	$scope.updatedProducts=[];
	
	$scope.productStatus=['Active','InActive'];
	
	$scope.changeMode=function(activeMode)
	{
		angular.forEach($scope.modes,function(value,mode){
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		if($scope.modes.view || $scope.modes.edit)
		{
			$scope.viewProducts();
		}
	}
	
	
	$scope.generateProductID=function()
	{
		deliveryNoteService.generateID('product')
		.then(function successCallback(response) 
		{
			$scope.product.productID=response.data.productID;
			
		},function failureCallback(response){
			$window.alert('Error generating Product Id!');
		});
	}
	
	$scope.generateProductID();
	
	$scope.addProduct=function(product)
	{
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/product',
    	  data: product
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
    		if(response.data==true)
    		{
    			$window.alert('Product Added Successfully!');
    			$scope.product={};
    			$scope.generateProductID();
    		}
    		else
			{
    			$window.alert('Unable to add product!');
			}
    	  }, function errorCallback(response) {
    		  $window.alert('Server Error!');
    	  });
	}
	
	$scope.viewProducts=function()
	{
		//This service gets all the products
		deliveryNoteService.getObject('product').then(function successCallback(response) 
		{
			var count=0;
			$scope.products=response.data;
			angular.forEach($scope.products,function(value,key){
				value.sno=++count;
			});
		});
	}
	
	$scope.deleteProduct=function(index)
	{
		var product=$scope.products[index];
		$scope.deletedProducts.push(product.productID);
		$scope.products.splice(index,1);
		resetSno();
	}
	
	$scope.updateProduct=function(index)
	{
		var present=false;
		var product=$scope.products[index];
		angular.forEach($scope.updatedProducts,function(value,key){
			if(product == value)
			{
				present=true;
			}
		});
		if(!present)
		{
			$scope.updatedProducts.push(product);
		}
	}
	
	$scope.updateProducts=function()
	{
		if($scope.deletedProducts.length==0 && $scope.updatedProducts.length==0)
		{
			$window.alert('No product has been modified!');
		}
		else
		{
			var payload={
					deletedProducts: $scope.deletedProducts,
					updatedProducts: $scope.updatedProducts
			};
			// Simple POST request
	    	$http({
	    	  method: 'POST',
	    	  url: '/product/update',
	    	  data: payload
	    	}).then(function successCallback(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
	    		$window.alert('Product Updated Successfully!');
	    		$scope.deletedProducts=[];
	    		$scope.updatedProducts=[];
	    	  }, function errorCallback(response) {
	    		  $window.alert('Server Error!');
	    	  });
		}
	}
	
	resetSno=function()
	{
		var sno=0;
		angular.forEach($scope.products,function(product,key){
			product.sno=++sno;
		});
	}
	$scope.reset = function(productform) {
        if (productform) {
        	productform.$setPristine();
        	productform.$setUntouched();
        	$scope.product={
        			effectiveDate: new Date()
        	}
        	$scope.successMsg=undefined;
        	$scope.errorMsg=undefined;
        	$scope.generateProductID();
        }
    };
}]);
