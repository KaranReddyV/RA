resourceApp.controller('registercontactCtrl',['$scope','$state','$stateParams','RAService',function($scope,$state,$stateParams,RAService){
	 $scope.$on('$viewContentLoaded', function () {
		 	$scope.getContactlist();
			$scope.contact = {};
        })
        $scope.contacttype = ['Bill-Address','Primary-Address','Secondary-Address','Others'];
        $scope.getContactlist = function(){
		 RAService.registergetcontact().then(function(data){
			 $scope.contactlist = data;
			 console.log($scope.contactlist);
		 })
	 }
	 
	 
	 $scope.addcontact = function(){
		 RAService.registeraddcontact($scope.contact).then(function(data){
			 $scope.addcontact = data;
			 console.log($scope.addcontact);
			 $state.go('RA.registerContact');
		 },function(err){
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
           }   
        })
	 }
        
     
	
}])

resourceApp.controller('updateregistercontactCtrl',['$scope','$state','$stateParams','RAService',function($scope,$state,$stateParams,RAService){
	$scope.$on('$viewContentLoaded',function(){
		$scope.getuser();
	})
	 $scope.getuser = function(){
        	RAService.registergetbyid($stateParams.contactId).then(function(data){
				$scope.contact = data;
				console.log($scope.contact);
			}, function(err){
				if(err){
					$scope.errorMessage = err;
				}else{
					$scope.errorMessage = err;
				}
			})
		}
}])