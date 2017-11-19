 
app.controller('OrderController', ['$scope','deliveryNoteService','$http','$window',function($scope,deliveryNoteService,$http,$window) {
	$scope.heading='Order';
	$scope.isRowAdded=false;
	$scope.isSaved=false;
	$scope.simple=false;
	$scope.createMode=true;
	$scope.viewMode=false;
	$scope.editMode=false;
	$scope.isSearch=false;
	$scope.isUpdate=false;
	$scope.isNewNote=false;
	$scope.stateGSTType='I/SGST';
	$scope.orderNotes=[];
	$scope.newNotes=[];
	$scope.deletedOrders=[];
	$scope.updatedOrders=[];
	$scope.deletedOrderNotes=[];
	$scope.updatedOrderNotes=[];
	
	$scope.deliveryNotes={
			deliveryNoteID:'',
			deliveryDate : new Date(),
			customerID :'',
			customerName : '',
			grandTotal:0,
			notes :[]
	};
	$scope.orders=[];
	$scope.notes=[];
	$scope.counter=0;
	
	
	//This service gets all the products
	deliveryNoteService.getObject('product').then(function successCallback(response) 
	{
		$scope.products=response.data;
	});
	
	//This service gets the Customer ID and Customer Name
	deliveryNoteService.getObject('customer').then(function successCallback(response) 
	{
		$scope.customers=response.data;
	});
	
	
	
	$scope.generateDeliveryNoteID=function()
	{
		deliveryNoteService.generateID('order',$scope.deliveryNotes.deliveryDate,$scope.simple)
		.then(function successCallback(response) 
		{
			console.log('success'+response.data);
			$scope.deliveryNotes.deliveryNoteID=response.data.deliveryNoteID;
			
		},function failureCallback(response){
			console.log(response.status);
			$scope.deliveryNotes.deliveryNoteID='Error';
		});
	}
	
	$scope.generateDeliveryNoteID();
	
	$scope.loadProductByID=function(index)
	{
		var _note;
		if($scope.editMode)
		{
			_note=$scope.orderNotes[index];
		}
		else
		{
			_note=$scope.notes[index];
		}
		_note.isProductSelected=true;
		angular.forEach($scope.products,function(value,key){
			if(value.productID==_note.productID)
				{
					console.log('In Product ID');
					_note.rate=value.rate;
					_note.productDescription=value.productDescription;
					_note.cgst=value.gst/2;
					_note.sgst=value.gst/2;
					console.log(_note.productDescription);
				}
		});
	};
	
	$scope.loadCustomerName=function()
	{
		$scope.isSearch=false;
		$scope.isUpdate=false;
		var _customerID=$scope.deliveryNotes.customerID;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerID==_customerID)
				{
					$scope.deliveryNotes.customerName=value.customerName;
				}
		});
		deliveryNoteService.getStateGSTType($scope.deliveryNotes.customerID).then(function successCallback(response) 
		{
			$scope.stateGSTType=response.data.stateGSTType;
		});
	};
	
	$scope.loadCustomerID=function()
	{
		$scope.isSearch=false;
		$scope.isUpdate=false;
		var _customerName=$scope.deliveryNotes.customerName;
		angular.forEach($scope.customers,function(value,key){
			if(value.customerName==_customerName)
				{
					$scope.deliveryNotes.customerID=value.customerID;
				}
		});
		deliveryNoteService.getStateGSTType($scope.deliveryNotes.customerID).then(function successCallback(response) 
		{
			$scope.stateGSTType=response.data.stateGSTType;
		});
	};
	
	$scope.loadProductByDesc=function(index)
	{
		var _note;
		if($scope.editMode)
		{
			_note=$scope.orderNotes[index];
			
		}
		else
		{
			_note=$scope.notes[index];
		}
		_note.isProductSelected=true;
		angular.forEach($scope.products,function(value,key){
			if(value.productDescription==_note.productDescription)
				{
					_note.rate=value.rate;
					_note.productID=value.productID;
					_note.cgst=value.gst/2;
					_note.sgst=value.gst/2;
				}
		});
	};
	
	$scope.setUpdatableNotes=function(_note)
	{
		var present=false;
		angular.forEach($scope.updatedOrderNotes,function(value,key){
			if(_note == value)
				{
					present=true;
				}
		});
		if(!present && !_note.isNewNote)
		{
			$scope.updatedOrderNotes.push(_note);
		}
		console.log('updatedOrderNotes ==> '+$scope.updatedOrderNotes);
	}
	
	$scope.calculateTotal=function(index)
	{
		if($scope.editMode)
		{
			var _note=$scope.orderNotes[index];
			$scope.setUpdatableNotes(_note);
			$scope.isUpdate=true;
		}
		else
		{
			var _note=$scope.notes[index];
		}
		_note.total=_note.quantity*_note.rate;
		_note.netTotal=_note.total-(_note.total*_note.discount)/100;
		_note.cgstAmount=(_note.netTotal*_note.cgst)/100;
		_note.sgstAmount=(_note.netTotal*_note.sgst)/100;
		_note.netPrice=_note.netTotal+_note.cgstAmount+_note.sgstAmount;
		if($scope.simple)
		{
			$scope.calGrandTotal(_note.deliveryNoteID,_note.netTotal);
		}
		else
		{
			$scope.calGrandTotal(_note.deliveryNoteID,_note.netPrice);
		}
	}
	
	$scope.calculateNetTotal=function(index)
	{
		if($scope.editMode)
		{
			var _note=$scope.orderNotes[index];
			$scope.setUpdatableNotes(_note);
			$scope.isUpdate=true;
		}
		else
		{
			var _note=$scope.notes[index];
		}
		_note.netTotal=_note.total-(_note.total*_note.discount)/100;
		_note.cgstAmount=(_note.netTotal*_note.cgst)/100;
		_note.sgstAmount=(_note.netTotal*_note.sgst)/100;
		_note.netPrice=_note.netTotal+_note.cgstAmount+_note.sgstAmount;
		if($scope.simple)
		{
			$scope.calGrandTotal(_note.deliveryNoteID,_note.netTotal);
		}
		else
		{
			$scope.calGrandTotal(_note.deliveryNoteID,_note.netPrice);
		}
		
		
	};
	
	$scope.calGrandTotal=function(orderId,netPrice,isDelete)
	{
		if($scope.editMode)
			{
				angular.forEach($scope.orders,function(order,key){
					if(order.deliveryNoteID == orderId)
						{
						if(isDelete)
						{
							order.grandTotal = order.grandTotal - netPrice;
						}
						else
						{
							order.grandTotal=0;
							angular.forEach($scope.orderNotes,function(value,key){
								if($scope.simple)
								{
									order.grandTotal += value.netTotal;
								}
								else
								{
									order.grandTotal += value.netPrice;
								}
							});
						}
							
							var present=false;
							angular.forEach($scope.updatedOrders,function(updatedOrder,key){
								if(order == updatedOrder)
									{
										present=true;
									}
							});
							if(!present)
							{
								$scope.updatedOrders.push(order);
							}
						}
				});
				
			}
		else
			{
				$scope.deliveryNotes.grandTotal=0;
				if($scope.simple)
					{
					angular.forEach($scope.notes,function(value,key){
						$scope.deliveryNotes.grandTotal = $scope.deliveryNotes.grandTotal + value.netTotal;
					});
					}
				else
					{
					angular.forEach($scope.notes,function(value,key){
						$scope.deliveryNotes.grandTotal = $scope.deliveryNotes.grandTotal + value.netPrice;
					});
					}
			}
		console.log('updatedOrders ==> '+$scope.updatedOrders);
	}
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
			$scope.editMode=true;
		}
	}
	$scope.addNewNote=function()
	{
		
		$scope.isRowAdded=true;
		$scope.counter++;
		$scope.note={
				sno:$scope.counter,
				productID:'',
				productDescription:'',
				notation:'',
				quantity:0,
				rate:0,
				total:0,
				netTotal:0,
				discount:0,
				cgst:0,
				cgstAmount:0,
				sgst:0,
				sgstAmount:0,
				netPrice:0,
				isProductSelected: false
		};
		if($scope.editMode)
		{
			var order=$scope.orderNotes[0];
			$scope.note.isNewNote=true;
			$scope.note.sno=$scope.orderNotes.length+1;
			$scope.note.deliveryNoteID=order.deliveryNoteID;
			$scope.orderNotes.push($scope.note);
			$scope.newNotes.push($scope.note);
			$scope.isUpdate=true;
		}
		else
		{
			$scope.notes.push($scope.note);
			$scope.deliveryNotes.notes=$scope.notes;
		}
		resetSno();
	}
	
	var resetSno=function()
	{
		var counter=0;
		angular.forEach($scope.notes,function(note,key){
			note.sno=++counter;
		});
	}
	$scope.deleteNote=function(index)
	{
		$scope.notes.splice(index, 1);
		if($scope.notes.length==0)
			{
				$scope.isRowAdded=false;
				$scope.isSaved=false;
				$scope.deliveryNotes.grandTotal=0;
			}
		$scope.calGrandTotal();
		resetSno();
	}
	$scope.fetchOrders=function()
	{
		$scope.newNotes=[];
		$scope.deletedOrders=[];
		$scope.updatedOrders=[];
		$scope.deletedOrderNotes=[];
		$scope.updatedOrderNotes=[];
		
		$scope.isSearch=true;
		$scope.isView=false;
		if(($scope.deliveryNotes.fromDate || $scope.deliveryNotes.toDate) && (!$scope.deliveryNotes.fromDate || !$scope.deliveryNotes.toDate))
		{
			$window.alert('Please select proper range(from-to)!');
		}
		else
		{
			deliveryNoteService.submit('order/fetch/'+$scope.simple,$scope.deliveryNotes)
			.then(function successCallback(response) 
			{
				$scope.orders=response.data;
				if($scope.orders.length==0)
				{
					$window.alert('No Record Found!');
				}
				else
				{
					var count=0;
					angular.forEach($scope.orders,function(value,key){
						value.sno=++count;
					});
				}
				
			},function failureCallback(response){
				$window.alert('Server Error!');
			});
			
			
			/*var fetchCriteria={
					customerID: $scope.deliveryNotes.customerID,
					fromDate: $scope.deliveryNotes.fromDate,
					toDate: $scope.deliveryNotes.toDate,
					simple: $scope.simple
			};
			deliveryNoteService.getObject('order/fetch',fetchCriteria)
			.then(function successCallback(response) 
			{
				$scope.orders=response.data;
				if($scope.orders.length==0)
				{
					$window.alert('No Record Found!');
				}
				else
				{
					var count=0;
					angular.forEach($scope.orders,function(value,key){
						value.sno=++count;
					});
				}
				
			},function failureCallback(response){
				$window.alert('Server Error!');
			});*/
		}
		
		
	}
	$scope.DeleteOrder=function(index)
	{
		var order=$scope.orders[index];
		$scope.deletedOrders.push(order.deliveryNoteID);
		$scope.orders.splice(index, 1);
		$scope.orderNotes=[];
		$scope.isUpdate=true;
	}
	
	$scope.DeleteOrderNote=function(index)
	{
		var count=0;
		var note=$scope.orderNotes[index];		
		if($scope.orderNotes.length==1)
		{
			$window.alert('If you wish to delete the order, Please use delete button!');
		}
		else
		{
			if(note.isNewNote)
			{
				angular.forEach($scope.newNotes,function(value,key){
					if(note == value)
					{
						$scope.newNotes.splice(key,1);
					}
				});
				$scope.orderNotes.splice(index, 1);
			}
			else
			{
				$scope.deletedOrderNotes.push(note.objid);
				$scope.orderNotes.splice(index, 1);
				angular.forEach($scope.orderNotes,function(value,key){
					value.sno = ++count;
				});
				
				if($scope.simple)
				{
					$scope.calGrandTotal(note.deliveryNoteID,note.netTotal,true);
				}
				else
				{
					$scope.calGrandTotal(note.deliveryNoteID,note.netPrice,true);
				}
			}
		}
		$scope.isUpdate=true;
		
	}
	
	$scope.UpdateDeliveryNote=function()
	{
		var request={};
		request.strSimple=$scope.simple;
		request.newNotes=$scope.newNotes;
		request.deletedOrders=$scope.deletedOrders;
		request.updatedOrders=$scope.updatedOrders;
		request.deletedOrderNotes=$scope.deletedOrderNotes;
		request.updatedOrderNotes=$scope.updatedOrderNotes;
		
		deliveryNoteService.submit('order',request).then(function successCallback(response) 
		{
			$scope.editMode=false;
			$scope.viewMode=true;
			$window.alert('Order successfully updated!');
		},function failureCallback(response){
			$window.alert('Order failed to update!');
		});
	}
	$scope.fetchOrderNote=function(index)
	{
		$scope.isView=true;
		var order=$scope.orders[index];
		var orderId=order.deliveryNoteID;
		var invoiceGenerated=order.invoiceGenerated;
		deliveryNoteService.getObject('order/notes',orderId,$scope.simple)
		.then(function successCallback(response) 
		{
			console.log('success'+response.data);
			$scope.orderNotes=response.data;
			var count=0;
			angular.forEach($scope.orderNotes,function(value,key){
				value.sno=++count;
				value.invoiceGenerated=invoiceGenerated;
				value.isProductSelected=true;
			});
		},function failureCallback(response){
			console.log(response.status);
			//$scope.deliveryNotes.deliveryNoteID='Error';
		});
		
	}
	
	$scope.downloadOrderNote=function(index)
	{
		var order=$scope.orders[index];
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/order/generate/'+$scope.simple,
    	  data: order,
    	  headers:{'Content-type': 'application/json'},
          responseType : 'arraybuffer',//THIS IS IMPORTANT
    	}).then(function successCallback(response) {
    	    // this callback will be called asynchronously
    	    // when the response is available
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
    		  $window.alert('Server Error.Unable to download Order!');
    	  });
	}
	$scope.saveDeliveryNotes=function()
	{
		if($scope.deliveryNotes.customerID)
		{
			// Simple POST request
	    	$http({
	    	  method: 'POST',
	    	  url: '/order/'+$scope.simple,
	    	  data: $scope.deliveryNotes
	    	}).then(function successCallback(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
	    		if(response.data==true)
	    		{
	    			$window.alert('Delivery Note Saved Successfully!');
	    			//$scope.isSaved=true;
	    			reset();
	    		}
	    		else
				{
	    			$window.alert('Unable to save Delivery Note!');
				}
	    	  }, function errorCallback(response) {
	    		  $window.alert('Server Error!');
	    	  });
		}
		else
		{
			$window.alert('Please select customer before saving Order!');
		}
		
	}

	var reset=function()
	{
		$scope.isSaved=false;
        $scope.deliveryNotes={
    			deliveryNoteID:'',
    			deliveryDate : new Date(),
    			customerID :'',
    			customerName : '',
    			grandTotal:0,
    			notes :[]
    	};
        $scope.counter=0;
        $scope.notes=[];
        $scope.notes.push();
		$scope.deliveryNotes.notes=$scope.notes;
		$scope.generateDeliveryNoteID();
	}
	
	$scope.downloadDeliveryNote=function()
	{
		$scope.isSaved=false;
        
		// Simple POST request
    	$http({
    	  method: 'POST',
    	  url: '/order/generate/'+$scope.simple,
    	  data: $scope.deliveryNotes,
    	  headers:{'Content-type': 'application/json'},
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
