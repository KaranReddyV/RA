resourceApp.controller('postresourceCtrl',['$scope',"$state","RAService",function($scope,$state,RAService){
	$scope.$on('$viewContentLoaded', function () {
		$scope.postresourcelist();
	})
	
	$scope.postresourcelist = function(){
		RAService.postareqList().then(function(data){
			$scope.postresource = data;
			$scope.localid =localStorage.getItem('registrationId');
			console.log($scope.localid);
			console.log($scope.postresource)
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
	}
}]);

resourceApp.controller('postresourceByIdCtrl',['$scope','$state','$stateParams','RAService',function($scope,$state,$stateParams,RAService){
	$scope.$on('$viewContentLoaded', function(){
		$scope.resourcepostById();
		$scope.postresource = {};
	})
	$scope.ssss = function(id,emailid){
		$scope.cccc = id;
		$scope.mail = emailid;
	}
	$scope.resourcepostById = function(){
		RAService.postresourceById($stateParams.localId,$stateParams.resourceById,$stateParams.objectid).then(function(data){
			$scope.resourceById = data;
			console.log($scope.resourceById);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
	}
	
	$scope.mappingpostresource = function(id){
	
			debugger;
		$scope.postresource.companyId = $stateParams.resourceById;
		$scope.postresource.vendorId = $stateParams.localId;
		$scope.postresource.resourceId = $scope.cccc;
		$scope.postresource.requirementId = $stateParams.objectid;
		$scope.postresource.throughRA = "RA";
	RAService.postresourceMapping($scope.postresource).then(function(data){
		$scope.resourcemapping = data;
		console.log($scope.resourcemapping)
	},function(err){
		if(err){
			$scope.errorMessage = err;
		}
	})
	}
}])
