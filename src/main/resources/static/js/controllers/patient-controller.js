 
app.controller('patientController', ['$scope','ngDialog','NgTableParams','patientService','popUpService',function($scope,ngDialog,NgTableParams,patientService,popUpService) {
			
	$scope.modes={
			add : false,
			view : true
	};
	
	$scope.patientStatus=['Active','Inactive'];
	
	$scope.generatePatientID=function()
	{
		var _patientId=patientService.get({_Id:'generateId'},
		function successCallback()
		{
			$scope.patient.id=_patientId.patientID;
		},
		function failureCallback()
		{
			popUpService.openInfoBox('Error generating Patient Id!',$scope);
		});
	}
	
	var patientInit=function()
	{
		$scope.patient=
		{
				status: 'Active',
				effectiveDate: new Date()
		}
		$scope.generatePatientID();
	}
	
	patientInit();
	
	$scope.changeMode=function(activeMode)
	{
		angular.forEach($scope.modes,function(value,mode)
		{
			$scope.modes[mode] = mode==activeMode?true:false;
		})
		
		if($scope.modes.add)
		{
			$scope.generatePatientID();
		}
		else if($scope.modes.view)
		{
			getPatientList();
		}
	}
	
	$scope.addPatient=function(patient)
	{
		patientService.save(patient,function()
		{
			patientInit();
			popUpService.openInfoBox('Patient Added Successfully!',$scope);
		},
		function()
		{
			popUpService.openInfoBox('Server Error!',$scope);
		});
	}
	
	var getPatientList=function()
	{
		var patients=patientService.query(function(){
			$scope.tablePatient = new NgTableParams({}, { dataset: patients});
		});
	}
	getPatientList();
	
	$scope.deletePatient=function(patientID)
	{
		popUpService.openConfirmBox('patient',$scope).then(function (success) 
		{
			patientService.remove({_Id: patientID},function()
			{
				getPatientList();
				popUpService.openInfoBox('Patient Deleted Successfully!',$scope);
			},
			function()
			{
				popUpService.openInfoBox('Server Error!',$scope);
			});
		}, function (failure){
			//do nothing
		});
	}
	
	$scope.updatePatient=function(patient)
	{
		patientService.update(patient,function()
		{
			popUpService.openInfoBox('Patient Updated Successfully!',$scope);
		},
		function()
		{
			popUpService.openInfoBox('Server Error!',$scope);
		});
	}
	
	$scope.reset = function(patientform) {
        if (patientform) {
        	patientform.$setPristine();
        	patientform.$setUntouched();
        	$scope.patient={
        			effectiveDate: new Date()
        	}
        	$scope.successMsg=undefined;
        	$scope.errorMsg=undefined;
        	$scope.generatepatientID();
        }
    };
    
    //Calendar logic with disabled past dates
    $scope.today = function () {
        $scope.patient.effectiveDate = new Date();
    };
    $scope.mindate = new Date();
    $scope.dateformat="dd/MM/yyyy";
    $scope.today();
    $scope.showcalendar = function ($event) {
        $scope.showdp = true;
    };
    $scope.showdp = false;
    //End
    $scope.openEditPatientForm=function(patient)
    {
    	patient.mobile=parseInt(patient.mobile);
    	$scope.editPatient=patient;
    	popUpService.openEditForm($scope,'views/patient/EditPatient.html',600);
    }
    
}]);
