resourceApp.controller("primaryskillsController",["$scope","$state","masterService",function($scope, $state, masterService){
	
	$scope.$on('$viewContentLoaded',function(){
		$scope.alluser = {};
		$scope.getprimaryskillslist();
	})
	
	
	$scope.getprimaryskillslist = function(){
		masterService.allprimaryskills().then(function(data){
			$scope.primary = data;
			console.log($scope.primary);
		})
	}
	
	
}]);

resourceApp.controller('primaryskillsAddCtrl',["$scope","$state","$stateParams","masterService",function($scope,$state,$stateParams,masterService){
/*	$scope.$on('$viewContentLoaded',function(){
		$scope.newprimaryskills();
		
		
	})*/
	$scope.newprimaryskills = function(){
		debugger;
		masterService.saveprimaryskills($scope.alluser).then(function(data){
			$scope.primaryskills = data;
			console.log($scope.primaryskills);
			$state.go('RA.PrimarySkills');
			
		},function(err){
			
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
}])
		
resourceApp.controller('primaryskillsupdateCtrl',["$scope","$state","$stateParams","masterService",function($scope, $state, $stateParams, masterService){
				$scope.$on('$viewContentLoaded',function(){
					$scope.getPrimaryskillsbyId();
					//$scope.jobtypeupdate();
					$scope.getroles;
				})
				

				// get data By Id 
				
				$scope.getPrimaryskillsbyId = function(){
					debugger;
					masterService.getPrimaryskillsById($stateParams.alluser).then(function(data){
						$scope.alluser = data;
						console.log($scope.alluser)
						
					})
				}
				
				$scope.primaryskillsupdate = function(){
					debugger;
					
					masterService.updatePrimaryskills($scope.alluser).then(function(data){
						$scope.qqq = data;
						console.log($scope.qqq);
						$state.go('RA.PrimarySkills');
					},function(err){
						if(err){
							$scope.errorMessage = err;
						}else{
							$scope.errorMessage = err;
			           }   
			        })
				}
				
			}])
		







