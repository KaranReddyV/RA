resourceApp.controller('RAlistCtrl',['$scope','$document','RAService',function($scope,$document,RAService){
	$scope.$on('$viewContentLoaded', function () {
		$scope.registration = {};
		$scope.registrationlist();
	})
	$scope.title = "Registration";
	$scope.status = "active";
	$scope.registrationlist = function(){
		RAService.RAReg().then(function(data){
			$scope.details = data;
			console.log($scope.details);
		},function(err){
			if(err){
				$scope.errorMessage = err;
			}
		})
	}
	
	$scope.statusId = function(registration){
		debugger;
		if(registration.status == "Active"){
			registration.status = "InActive";
		RAService.registrationStatus(registration).then(function(data){
			$scope.ssss = data;
			console.log($scope.ssss);
			},function(err){
				if(err){
					$scope.errorMessage = err;
				}
			})
		}else{
				registration.status = "Active";
		RAService.registrationStatus(registration).then(function(data){
			$scope.ssss = data;
			console.log($scope.ssss);
			},function(err){
				if(err){
					$scope.errorMessage = err;
				}
			})
		}
		
		
	}
//	$scope.RegisterDeleteById = function(registration){
//		RAService.deleteregistration(registration).then(function(data){
//			$scope.deleteMesssage = data;
//			console.log("Delete Successfully..!!");
//			$scope.registrationlist();
//		})
//	}

}]);

resourceApp.controller('addRACtrl',['$scope','$state','RAService',function($scope,$state,RAService){
	$scope.$on('$viewContentLoaded', function () {
		$scope.registration = {};
	})
	$scope.companytype = ["Public Limited Company","Private Limited Company","Partnership","Proprietary"];
	$scope.quality = ["ISO 9001","ISO 9002","ISO I400","NONE"];
	$scope.registrationtype = ["Vendor","Customer","RA"];
	$scope.Licences = ['1','2','3','4','5'];
	$scope.Period = ['1','2','3','4','5']
	$scope.addData = function(){
		debugger;
		$scope.registration.registrationType = $scope.registration.registrationType.toString();
		RAService.saveRegistration($scope.registration).then(function(data){
			$scope.dddd = data;
			console.log($scope.dddd);
			$state.go('RA.RAlist');
		},function(err){    
			
			   
			if(err){
				$scope.errorMessage = err;
			}else{
				$scope.errorMessage = err;
			}
		});
	}
}]);
