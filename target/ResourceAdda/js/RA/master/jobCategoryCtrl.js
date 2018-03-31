resourceApp.controller('jobgetCtrl',['$scope','masterService',function($scope,masterService) {	  
	masterService.jobCategoryget().then(function(data){
        $scope.category = data;
        console.log("jobCategoryget.....................");
        console.log($scope.category);
  })
}]);

resourceApp.controller('postCtrl',['$scope','$state','masterService',function($scope,$state,masterService){
	$scope.postjob = function(){
		masterService.jobCategorypost($scope.postcategory).then(function(data){
         $scope.postresult = data;
         $state.go('RA.jobcategory');
         console.log("postresult...............");
         console.log($scope.postresult);
      },function(err){    
		if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
			}
		});
}
}]);

resourceApp.controller('putCtrl',['$scope','$state','$stateParams','masterService',function($scope,$state,$stateParams,masterService){
	$scope.$on('$viewContentLoaded', function () {	
			   $scope.getId();
			  })

	$scope.getId = function(){
		masterService.jobCategoryById($stateParams.putcategory).then(function(data){
				$scope.putdata = data;
			console.log($scope.putdata);
			},function(err){
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			})
		}

		$scope.putjob = function(){
			masterService.jobCategoryPut($scope.putdata).then(function(data){
				$scope.dddd = data;
				console.log($scope.dddd);
				$state.go('RA.jobcategory');
			},function(err){    
					   
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			});
		}


	}])
