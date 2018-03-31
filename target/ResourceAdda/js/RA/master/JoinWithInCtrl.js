resourceApp.controller("JoinWithInCtrl",["$scope","$state","$stateParams","masterService",function($scope, $state,$stateParams,masterService){
	$scope.$on('$viewContentLoaded', function () {
		$scope.JoinWithIn();

    })
	
	$scope.JoinWithIn = function(){
		masterService.joinwithinlist().then(function(data){
			$scope.joinwithinlist = data;
			console.log($scope.joinwithinlist);
		})
	}
	
	$scope.savenewJoin=function(){
		masterService.createnewJoin($scope.newJoin).then(function(data){
			$scope.allnewJoin = data;
			console.log($scope.allnewJoin);
			$state.go('RA.JoinWithIn');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
}]);

resourceApp.controller("JoinWithInUpdateCtrl",["$scope","$state","$stateParams","masterService",function($scope, $state,$stateParams,masterService){
		$scope.$on('$viewContentLoaded', function () {
			$scope.joinwithinById();
	    })
	    
	$scope.joinwithinById = function(){
		//$scope.resource.registrationId = $scope.comId;
		//debugger;
		masterService.joinwithingetById($stateParams.joinWithIn).then(function(data){
			$scope.newJoin = data;
			console.log($scope.newJoin);
		})
	}
	
	$scope.updatejoinWithIn=function(){
		//debugger;
		masterService.updateJoin($scope.newJoin).then(function(data){
			$scope.newJoin = data;
			console.log($scope.newJoin);
			$state.go('RA.JoinWithIn');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	

}]);