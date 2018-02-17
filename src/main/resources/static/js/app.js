
var app = angular.module('productPlannerApp', ['ngRoute','ngResource','ngLoadingSpinner','ngAnimate', 'ui.bootstrap','ngDialog','ngTable']);

app.config(function($routeProvider){
    $routeProvider
        .when('/login',{
        	
            templateUrl: '/views/LoginPage.html',
            controller: 'loginController',
            activePage: 'login'
        })
        .when('/addProduct',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/product/AddProducts.html',
            controller: 'productController',
            activePage: 'product'
        })
        .when('/viewProduct',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/product/ViewProducts.html',
            controller: 'productController',
            activePage: 'product'
        })
        .when('/addPatient',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/patient/AddPatient.html',
            controller: 'patientController',
            activePage: 'patient'
        })
        .when('/viewPatient',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/patient/ViewPatients.html',
            controller: 'patientController',
            activePage: 'patient'
        })
        .when('/addUser',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/user/AddUser.html',
            controller: 'userController',
            activePage: 'user'
        })
        .when('/viewUser',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/user/ViewUsers.html',
            controller: 'userController',
            activePage: 'user'
        })
        .when('/createOrder',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/order/CreateOrder.html',
            controller: 'OrderController',
            activePage: 'order'
        })
        .when('/viewOrder',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/order/ViewOrder.html',
            controller: 'OrderController',
            activePage: 'order'
        })
        .when('/addCustomer',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/customer/AddCustomer.html',
            controller: 'customerController',
            activePage: 'customer'
        })
        .when('/viewCustomer',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/customer/ViewCustomers.html',
            controller: 'customerController',
            activePage: 'customer'
        })
        .when('/owner',{
            templateUrl: '/views/OwnerInfo.html',
            controller: 'ownerController',
            activePage: 'owner'
        })
        .when('/generateInvoice',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/InvoiceGeneration.html',
            controller: 'invoiceController',
            activePage: 'invoice'
        })
        .when('/invoice',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/InvoiceGeneration.html',
            controller: 'invoiceController',
            activePage: 'invoice'
        })
        .when('/installment',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/InstallmentReceipt.html',
            controller: 'installmentReceiptController',
            activePage: 'installment'
        })
        .when('/salesReport',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/SalesReport.html',
            controller: 'reportController',
            activePage: 'report'
        })
        .when('/receiptReport',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/ReceiptReport.html',
            controller: 'reportController',
            activePage: 'report'
        })
        .when('/stockWiseReport',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/StockWiseReport.html',
            controller: 'reportController',
            activePage: 'report'
        })
        .when('/partyWiseReport',{
        	resolve:{
        		"check":function($rootScope,$location)
        		{
        			if(!$rootScope.isLoggedIn)
        				{
        					$location.path("/login");
        				}
        		}
        	},
            templateUrl: '/views/PartyWiseReport.html',
            controller: 'reportController',
            activePage: 'report'
        })
        .when('/dashboard',{
        	
            templateUrl: '/views/DashBoard.html',
            controller: 'dashboardController',
        })
        .otherwise(
            { redirectTo: '/'}
        );
    
    //$locationProvider.html5Mode(true);
});

