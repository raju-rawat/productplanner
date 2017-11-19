app.directive('viewOrder', function() {
	   //define the directive object
	   var directive = {};
	   
	   //restrict = E, signifies that directive is Element directive
	   directive.restrict = 'E';
	   
	   //template replaces the complete element with its text. 
	   directive.templateUrl = "/views/ViewOrder.html";
	   
	   //scope is used to distinguish each student element based on criteria.
	   //directive.scope = {
	   //   student : "=name"
	   //}
	   
	   //compile is called during application initialization. AngularJS calls it once when html page is loaded.
		
	   //directive.compile = function(element, attributes) {
	    //  element.css("border", "1px solid #cccccc");
	      
	      //linkFunction is linked with each element with scope to get the element specific data.
	     // var linkFunction = function($scope, element, attributes) {
	     //    element.html("Student: <b>"+$scope.student.name +"</b> , Roll No: <b>"+$scope.student.rollno+"</b><br/>");
	     //    element.css("background-color", "#ff00ff");
	     // }
	     // return linkFunction;
	   // }
	   return directive;
	});

app.directive('createOrder', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/CreateOrder.html";
	   
	   return directive;
	});

app.directive('salesReport', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/SalesReport.html";
	   
	   return directive;
	});

app.directive('receiptReport', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/ReceiptReport.html";
	   
	   return directive;
	});

app.directive('stockWiseReport', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/StockWiseReport.html";
	   
	   return directive;
	});

app.directive('partyWiseReport', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/PartyWiseReport.html";
	   
	   return directive;
	});

app.directive('addProducts', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/AddProducts.html";
	   
	   return directive;
	});

app.directive('viewProducts', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/ViewProducts.html";
	   
	   return directive;
	});

app.directive('editProducts', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/EditProducts.html";
	   
	   return directive;
	});
app.directive('addCustomer', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/AddCustomer.html";
	   
	   return directive;
	});

app.directive('viewCustomers', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/ViewCustomers.html";
	   
	   return directive;
	});

app.directive('editCustomers', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/EditCustomers.html";
	   
	   return directive;
	});

app.directive('addUser', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/AddUser.html";
	   
	   return directive;
	});

app.directive('viewUsers', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/ViewUsers.html";
	   
	   return directive;
	});

app.directive('editUsers', function() {
	   var directive = {};
	   
	   directive.restrict = 'E';
	   
	   directive.templateUrl = "/views/EditUsers.html";
	   
	   return directive;
	});