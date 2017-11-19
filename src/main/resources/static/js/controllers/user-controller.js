app.controller('userController', ['$scope','$http','deliveryNoteService','$window',function($scope,$http,deliveryNoteService,$window) {
	$scope.heading='Create User';
	
	$scope.users=[];
	$scope.user={
			createDate: new Date()
	};
	$scope.deletedUsers=[];
	$scope.updatedUsers=[];
	$scope.modes={
			add : true,
			view : false,
			edit : false,
	};
	
	$scope.userStatus=['Active','Inactive'];
	$scope.userTypes=['Normal','Admin','Super Admin'];
	
	$scope.changeMode=function(activeMode)
	{
		angular.forEach($scope.modes,function(value,mode){
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		if($scope.modes.view || $scope.modes.edit)
		{
			$scope.viewUsers();
		}
	}
	$scope.viewUsers=function()
	{
		//This service gets all the customers
		deliveryNoteService.getObject('user').then(function successCallback(response) 
		{
			var count=0;
			$scope.users=response.data;
			angular.forEach($scope.users,function(user,key){
				user.sno=++count;
				if(user.status=='A')
				{
					user.status='Active';
				}else if(user.status=='I')
				{
					user.status='Inactive';
				}
			});
		});
	}
	
	$scope.deleteUser=function(index)
	{
		var user=$scope.users[index];
		$scope.deletedUsers.push(user.userID);
		$scope.users.splice(index,1);
		resetSno();
	}
	
	$scope.updateUser=function(index)
	{
		var present=false;
		var user=$scope.users[index];
		angular.forEach($scope.updatedUsers,function(value,key){
			if(user == value)
			{
				present=true;
			}
		});
		if(!present)
		{
			$scope.updatedUsers.push(user);
		}
	}
	
	$scope.updateUsers=function()
	{
		if($scope.deletedUsers.length==0 && $scope.updatedUsers.length==0)
		{
			$window.alert('No user has been modified!');
		}
		else
		{
			var payload={
					deletedUsers: $scope.deletedUsers,
					updatedUsers: $scope.updatedUsers
			};
			// Simple POST request
	    	$http({
	    	  method: 'POST',
	    	  url: '/user/update',
	    	  data: payload
	    	}).then(function successCallback(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
	    		$window.alert('User Updated Successfully!');
	    		$scope.deletedUsers=[];
	    		$scope.updatedUsers=[];
	    	  }, function errorCallback(response) {
	    		  $window.alert('Server Error!');
	    	  });
		}
		
	}
	
	resetSno=function()
	{
		var sno=0;
		angular.forEach($scope.users,function(user,key){
			user.sno=++sno;
		});
	}
	$scope.addUser=function(user,rePassword)
	{
		if(rePassword!=user.password)
		{
			$scope.errorMsg='Password Mismatch!';
		}
		else
		{
			$scope.errorMsg=undefined;
	    	$http({
	    	  method: 'POST',
	    	  url: '/user',
	    	  data: user
	    	}).then(function successCallback(response) {
	    		$window.alert('User Created Successfully!');
	    	  }, function errorCallback(response) {
	    		  $window.alert('Server Error!');
	    	  });
		}
		
		
	}
	$scope.reset = function(userform) {
        if (userform) {
        	userform.$setPristine();
        	userform.$setUntouched();
        	$scope.user={
        			createDate: new Date()
        	};
        	$scope.errorMsg=undefined;
        }
    };
    
    
    $scope.today = function () {
        $scope.user.createDate = new Date();
    };
    $scope.mindate = new Date();
    $scope.dateformat="dd/MM/yyyy";
    $scope.today();
    $scope.showcalendar = function ($event) {
        $scope.showdp = true;
    };
    $scope.showdp = false;

}]);
