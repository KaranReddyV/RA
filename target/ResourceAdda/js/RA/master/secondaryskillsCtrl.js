resourceApp.controller("secondaryskillCtrl",["$scope","masterService",function($scope,masterService){
	masterService.secondaryget().then(function(data){
		 $scope.secondaryget = data;
        console.log("secondaryget.....................");
        console.log($scope.secondaryget);
	})
}]);
resourceApp.controller("secondaryaddCtrl",["$scope",'$state',"masterService",function($scope,$state,masterService){	
	$scope.secondaryadd = function(){
		masterService.secondaryadd($scope.secondadd).then(function(data){
         $scope.secondaryadd = data;
         $state.go('RA.secondaryskill');
         console.log("secondaryadd...............");
         console.log($scope.secondaryadd);
      },function(err){    
		if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
			}
		});
}
}]);
resourceApp.controller('secondaryeditCtrl',['$scope','$state','$stateParams','masterService',function($scope,$state,$stateParams,masterService){
	$scope.$on('$viewContentLoaded', function () {	
			   $scope.secondarygetId();
			  })

	$scope.secondarygetId = function(){
		masterService.secondaryById($stateParams.putskill).then(function(data){
				$scope.dataput = data;
			console.log($scope.dataput);
			},function(err){
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			})
		}

		$scope.skillput = function(){
			masterService.secondaryedit($scope.dataput).then(function(data){
				$scope.dddd = data;
				console.log($scope.dddd);
				$state.go('RA.secondaryskill');
			},function(err){    
					   
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			});
		}


	}]);