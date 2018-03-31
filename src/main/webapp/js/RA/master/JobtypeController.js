resourceApp.controller("JobtypeController",["$scope","$state","masterService",function($scope, $state, masterService){
	
	$scope.$on('$viewContentLoaded',function(){
		$scope.alluser = {};
		$scope.getjobtypelist();
	})
	
	
	$scope.getjobtypelist = function(){
		masterService.newjobtype().then(function(data){
			$scope.newjobtype = data;
			console.log($scope.newjobtype);
		})
	}
	
	
}]);


resourceApp.controller('jobtypeaddCtrl',["$scope","$state","$stateParams","masterService",function($scope,$state,$stateParams,masterService){
	
	$scope.newjobtype = function(){
		debugger;
		masterService.saveNewJobtype($scope.alluser).then(function(data){
			$scope.newjobtype1 = data;
			console.log($scope.newjobtype1);
			$state.go('RA.Jobtype');
		},function(err){
			
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
}])

resourceApp.controller('jobtypeupdateCtrl',["$scope","$state","$stateParams","masterService",function($scope, $state, $stateParams, masterService){
	$scope.$on('$viewContentLoaded',function(){
		$scope.getjobtypebyId();
		$scope.jobtypeupdate();
		
	})
	

	// get data By Id 
	
	$scope.getjobtypebyId = function(){
		debugger;
		masterService.getJobtypeById($stateParams.alluser).then(function(data){
			$scope.alluser = data;
			console.log($scope.alluser)
			
		})
	}
	
	$scope.jobtypeupdate = function(){
		debugger;
		
		masterService.updateJobtype($scope.alluser).then(function(data){
			$scope.qqq = data;
			console.log($scope.qqq);
			$state.go('RA.Jobtype');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	
}])