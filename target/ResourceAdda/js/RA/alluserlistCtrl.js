resourceApp.controller("alluserlistCtrl",["$scope","$state","$stateParams","RAService",function($scope, $state, $stateParams, RAService){
	$scope.$on('$viewContentLoaded', function () {
	 	$scope.getalluserlist();
    })
	
	$scope.getalluserlist = function(){
		RAService.alluserlist().then(function(data){
			$scope.alluserlist = data;
			console.log($scope.alluserlist);
		})
	}
}]);


resourceApp.controller('alluseraddCtrl',["$scope","$state","$stateParams","RAService",function($scope,$state,$stateParams,RAService){
	$scope.$on('$viewContentLoaded',function(){
		$scope.alluser = {};
		$scope.getroles();
	})
	
	$scope.getroles = function(){
		RAService.allusergetroles().the(function(data){
			$scope.alluser = data;
			console.log($scope.alluser);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
	}
	$scope.userstatus = ["Enable","Disable"];

	$scope.userall = function(){
		debugger;
		$scope.alluser.registrationId = localStorage.getItem('registrationId');
		$scope.alluser.registrationType = localStorage.getItem('registrationType');
		RAService.alluseradd($scope.alluser).then(function(data){
			$scope.alluseradd1 = data;
			console.log($scope.alluseradd1);
			$state.go('RA.alluserlist');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
}])


resourceApp.controller('alluserupdateCtrl',["$scope","$state","$stateParams","RAService",function($scope, $state, $stateParams, RAService){
	$scope.$on('$viewContentLoaded',function(){
		$scope.getalluser();
		$scope.getroles;
	})
	
	$scope.getroles = function(){
		RAService.allusergetroles().then(function(data){
			$scope.roles = data;
			console.log($scope.roles);
		})
	}
	
	$scope.userstatus = ["Enable","Disable"];
	$scope.getalluser = function(){
		RAService.allusergetbyid($stateParams.alluserId).then(function(data){
			$scope.alluser = data;
			console.log($scope.alluser)
			localStorage.setItem('alluserid', $scope.alluser._id);
		})
	}
	
	$scope.alluserupdate = function(){
		debugger;
		$scope.alluser._id = localStorage.getItem('alluserid');
		RAService.alluserupdate($scope.alluser).then(function(data){
			$scope.qqq = data;
			console.log($scope.qqq);
			$state.go('RA.alluserlist');
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	}
	
}])


