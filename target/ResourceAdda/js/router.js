resourceApp.config(
        ['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {
        	 var accessLevels = {
        		        admin: 'admin',
        		        RA: 'RA',
        		        vendor: 'vendor',
        		        customer: 'customer'
        		    };	
            $urlRouterProvider.otherwise('/');

            $stateProvider
                .state('/', {
                    url: '/',
                    templateUrl: 'partials/login.html',
                    controller: 'LoginController'
                })
                
                //RA state starts here
				
				.state('RA', {
                   url: '/RA',
                   abstract: true,
                   templateUrl: 'partials/RA/RA.html',
                   cache: false,
                   controller: 'RACtrl'  
                })
                .state('RA.dashboard',{
                	url: '/RADashboard',
                	templateUrl: 'partials/RA/RAdashboard.html',
                	cache: false
                })
                .state('RA.RAlist',{
                	url: '/RAlist',
                	templateUrl: 'partials/RA/RAlist.html',
                    cache: false,
                	controller: 'RAlistCtrl'
                	
                })
                .state('RA.addRA',{
                	url:'/addRA',
                	templateUrl: 'partials/RA/addRA.html',
                    cache: false,
                	controller: 'addRACtrl'
                })
                .state('RA.updateRA',{
                	url:'/updateRA/:userId',
                	templateUrl: 'partials/RA/updateRA.html',
                	cache: false,
                	controller: 'updateRACtrl'
                })
                .state('RA.buynow',{
                	url:'/RAbuynow',
                	templateUrl: 'partials/RA/buynow.html',
                	cache: false,
                	controller: 'buynowCtrl'
                })
                .state('RA.postarequirementlist',{
                	url:'/postarequirementlist',
                	templateUrl:'partials/RA/postareqlist.html',
                	cache: false,
                	controller:'postareqCtrl'
                })
                .state('RA.addpostrequirement',{
                	url:'/addpostarequirement',
                	templateUrl: 'partials/RA/addrequirement.html',
                	cache: false,
                	controller:'addpostreqCtrl'
                })
                .state('RA.updatepostarequirement',{
                	url:'/updatepostarequirement/:postId',
                	templateUrl: 'partials/RA/updatepostreq.html',
                	cache: false,
                	controller:'updatepostareqCtrl'
                })
                .state('RA.registerContact',{
                	url:'/registrationContactlist',
                	templateUrl: 'partials/RA/registercontactlist.html',
                	cache: false,
                	controller: 'registercontactCtrl'
                })
				.state('RA.registeraddContact',{
					url:'/registeraddcontact',
					templateUrl:'partials/RA/registeraddcontact.html',
					cache: false,
					controller: 'registercontactCtrl'
				})
				.state('RA.updateregistercontact',{
					url:'/updateregistercontact/:contactId',
					templateUrl:'partials/RA/updateregistercontact.html',
					cache: false,
					controller: 'updateregistercontactCtrl'
				})
				.state('RA.alluserlist',{
					url: '/AlluserList',
					templateUrl:'partials/RA/alluserlist.html',
					cache: false,
					controller: 'alluserlistCtrl'
				})
				.state('RA.Jobtype',{
					url: '/Jobtype',
					templateUrl:'partials/RA/Master/Jobtype.html',
					cache: false,
					controller: 'JobtypeController'
				})
				.state('RA.JobtypeAdd',{
					url: '/Jobtype',
					templateUrl:'partials/RA/Master/JobtypeAdd.html',
					cache: false,
					controller: 'jobtypeaddCtrl'
				})
				.state('RA.JobtypeEdit',{
					url: '/Jobtype/:alluser',
					templateUrl:'partials/RA/Master/JobtypeEdit.html',
					cache: false,
					controller: 'jobtypeupdateCtrl'
				})
				.state('RA.PrimarySkills',{
					url: '/Primary',
					templateUrl:'partials/RA/Master/PrimarySkillsAll.html',
					cache: false,
					controller: 'primaryskillsController'
				})
				.state('RA.PrimaryskillsAdd',{
					url: '/Primary',
					templateUrl:'partials/RA/Master/PrimaryskillsAdd.html',
					cache: false,
					controller: 'primaryskillsAddCtrl'
				})
				
				.state('RA.PrimaryskillsUpdate',{
					url: '/Primary/:alluser',
					templateUrl:'partials/RA/Master/PrimaryskillsUpdate.html',
					cache: false,
					controller: 'primaryskillsupdateCtrl'
				})
						
				.state('RA.alluserAdd',{
					url:'/alluserAdd',
					templateUrl: 'partials/RA/alluseradd.html',
					cache: false,
					controller: 'alluseraddCtrl'
				})
				.state('RA.alluserupdate',{
					url:'/alluserupdate/:alluserId',
					templateUrl: 'partials/RA/alluserupdate.html',
					cache: false,
					controller:'alluserupdateCtrl'
				})
				.state('RA.resourcelist',{
					url:'/resourcelist',
					templateUrl: 'partials/RA/resourcelist.html',
					cache: false,
					controller:'resourcelistCtrl'
				})
				.state('RA.addresource',{
					url:'/addresource',
					templateUrl: 'partials/RA/addresource.html',
					cache: false,
					controller: 'addresourceCtrl'
				})
				.state('RA.updateresource',{
					url: '/updateresource/:resourceId',
					templateUrl: 'partials/RA/updateresource.html',
					cache: false,
					controller: 'updateresourceCtrl'
				})
		
				.state('RA.JoinWithIn',{
					url: '/JoinWithIn',
					templateUrl: 'partials/RA/Master/JoinWithIn.html',
					cache: false,
					controller: 'JoinWithInCtrl'
				})
				.state('RA.addnewJoin',{
					url: '/addnewJoin',
					templateUrl: 'partials/RA/Master/addnewJoin.html',
					cache: false,
					controller: 'JoinWithInCtrl'
				})
				.state('RA.updatejoinWithIn',{
					url: '/JoinWithIn/:joinWithIn',
					templateUrl: 'partials/RA/Master/updatejoinWithIn.html',
					cache: false,
					controller: 'JoinWithInUpdateCtrl'
				})


				.state('RA.jobcategory',{
					url: '/jobcategory',
					templateUrl: 'partials/RA/Master/jobcategory.html',
					cache: false,
					controller: 'jobgetCtrl'
				})
				.state('RA.jobcategoryadd',{
					url:'/jobcategoryadd',
					templateUrl: 'partials/RA/Master/jobcategoryadd.html',
					cache: false,
					controller: 'postCtrl'
				})
				.state('RA.jobcategoryupdate',{
                	url:'/jobcategoryupdate/:putcategory',
                	templateUrl: 'partials/RA/Master/updatejobcategory.html',
                	cache: false,
                	controller:'putCtrl'
                })
                .state('RA.secondaryskill',{
					url: '/secondaryskill',
					templateUrl: 'partials/RA/Master/secondaryskill.html',
					cache: false,
					controller: 'secondaryskillCtrl'
				})
				.state('RA.secondaryskilladd',{
					url:'/secondaryskilladd',
					templateUrl: 'partials/RA/Master/secondaryskilladd.html',
					cache: false,
					controller: 'secondaryaddCtrl'
				})
				.state('RA.secondaryskilledit',{
                	url:'/secondaryskilledit/:putskill',
                	templateUrl: 'partials/RA/Master/secondaryskilledit.html',
                	cache: false,
                	controller:'secondaryeditCtrl'
                })

				.state('RA.postresource',{
					url: '/postresource',
					templateUrl: 'partials/RA/postresource.html',
					cache: false,
					controller: 'postresourceCtrl'
				})
				.state('RA.postresourceById',{
					url:'/postresourceById/:localId/:resourceById/:objectid',
					templateUrl: 'partials/RA/postresourceById.html',
					cache: false,
					controller: 'postresourceByIdCtrl'
				})
				
				
                //RA state end here
                
                //customer state starts here
                .state('customer',{
                	url: '/customer',
                	abstract: true,
                	templateUrl: 'partials/customer/customer.html',
                	cache: false,
                	controller: 'customerCtrl'
                })
                .state('customer.dashboard',{
                	url: '/customerdashboard',
                	templateUrl: 'partials/customer/customerdashboard.html',
                	cache: false
                })
                
            
                
                //customer state end here
                
                
                //vendor state starts here
                   .state('vendor',{
                	url: '/vendor',
                	abstract: true,
                	templateUrl : 'partials/vendor/vendor.html',
                	cache: false,
                	controller:'vendorCtrl'
                		
                })
                .state('vendor.dashboard',{
                	url: '/vendordashboard',
                	templateUrl: 'partials/vendor/vendorDashboard.html',
                	cache: false
                	
                })
				//

            /* Register error provider that shows message on failed requests or redirects to login page on
             * unauthenticated requests */
            $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
                    return {
                        'responseError': function (rejection) {
                            var status = rejection.status;
                            var config = rejection.config;
                            var method = config.method;
                            var url = config.url;

                            if (status == 401) {
                                $location.path("/login");
                            } else {
                                $rootScope.error = method + " on " + url + " failed with status " + status;
                            }

                            return $q.reject(rejection);
                        }
                    };
                }
            );

            /* Registers auth token interceptor, auth token is either passed by header or by query parameter
             * as soon as there is an authenticated user */
            $httpProvider.interceptors.push(function ($q, $rootScope, $location) {
                    return {
                        'request': function (config) {
                            var isRestCall = config.url.indexOf('rest') == 0;
                            if (isRestCall && angular.isDefined($rootScope.accessToken)) {
                                var accessToken = $rootScope.accessToken;
                                if (exampleAppConfig.useAccessTokenHeader) {
                                    config.headers['X-Access-Token'] = accessToken;
                                } else {
                                    config.url = config.url + "?token=" + accessToken;
                                }
                            }
                            return config || $q.when(config);
                        }
                    };
                }
            );

        }]
    )