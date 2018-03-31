var resourceApp = angular.module('exampleApp', ['ui.router', 'ngCookies', 'ngResource','checklist-model']);

    
    resourceApp.run(function ($rootScope, $location, $cookieStore, UserService) {

    /* Reset error when a new view is loaded */
    $rootScope.$on('$viewContentLoaded', function () {
        delete $rootScope.error;
    });

    $rootScope.hasRole = function (role) {

        if ($rootScope.user === undefined) {
            return false;
        }

        if ($rootScope.user.roles[role] === undefined) {
            return false;
        }

        return $rootScope.user.roles[role];
    };

//    $rootScope.logout = function () {
//        delete $rootScope.user;
//        delete $rootScope.accessToken;
//        $cookieStore.remove('accessToken');
//        $location.path("/login");
//    };

    /* Try getting valid user from cookie or go to login page */
    var originalPath = $location.path();
   //$location.path("/login");
    var accessToken = $cookieStore.get('accessToken');
    if (accessToken !== undefined) {
        $rootScope.accessToken = accessToken;
        UserService.get(function (user) {
            $rootScope.user = user;
            $location.path(originalPath);
        });
    }

    $rootScope.initialized = true;
});

/*function IndexController($scope, BlogPostService) {

    $scope.blogPosts = BlogPostService.query();

    $scope.deletePost = function (blogPost) {
        blogPost.$remove(function () {
            $scope.blogPosts = BlogPostService.query();
        });
    };
}
*/
/*function EditController($scope, $routeParams, $location, BlogPostService) {

    $scope.blogPost = BlogPostService.get({id: $routeParams.id});

    $scope.save = function () {
        $scope.blogPost.$save(function () {
            $location.path('/');
        });
    };
}

function CreateController($scope, $location, BlogPostService) {

    $scope.blogPost = new BlogPostService();

    $scope.save = function () {
        $scope.blogPost.$save(function () {
            $location.path('/');
        });
    };
}*/
resourceApp.controller('LoginController',['$scope', '$rootScope', '$state', '$cookieStore', 'UserService', 'BlogPostService',function($scope, $rootScope, $state, $cookieStore, UserService, BlogPostService){
    $scope.rememberMe = false;

    $scope.login = function () {
        if(!$scope.username){
            alert('Please Enter Email Id');
        }else if(!$scope.password){
            alert('please Enter password');
        }
        else{
              UserService.authenticate($.param({
                    username: $scope.username,
                    password: $scope.password
                }), function (authenticationResult) {
                    var accessToken = authenticationResult.token;
                    $scope.userName = authenticationResult.userName;
                    console.log($scope.username);
                    $rootScope.accessToken = accessToken;
                    if ($scope.rememberMe) {
                        $cookieStore.put('accessToken', accessToken);
                    }
                    
                    BlogPostService.user($scope.userName).then(function(response){
                        $scope.user = response.data;
                        console.log($scope.user);
                        $scope.user.registrationType  = $scope.user.registrationType.split(',');
                        console.log($scope.user.registrationType);
                        localStorage.setItem('registrationType', $scope.user.registrationType);
                        localStorage.setItem('registrationId', $scope.user.registrationId);
                    // localStorage.getItem('registrationType');
                   
                          if($scope.user.registrationType.length > 1 && $scope.user.registrationType[0] == "RA"){
                            console.log("RA Module");
                         $state.go('RA.dashboard');
                          }
                          if($scope.user.registrationType.length > 1 && $scope.user.registrationType[0] == "customer"){
                             console.log("customer Module")
                             $state.go('customer.dashboard');
                          }
                         if($scope.user.registrationType.length > 1 && $scope.user.registrationType[0] == "vendor"){
                              console.log("customer Module")
                              $state.go('customer.dashboard');
                          }
                        if($scope.user.registrationType.length == 1){
                            if($scope.user.registrationType[0] == "RA"){
                            console.log("RA Module");
                            $state.go('RA.dashboard');
                        }
                        }
                        if($scope.user.registrationType.length == 1){
                            if($scope.user.registrationType[0] == "vendor"){
                            $state.go('vendor.dashboard');
                        }
                        }
                        
                        if($scope.user.registrationType.length == 1){
                            if($scope.user.registrationType[0] == "customer"){
                            console.log("customer Module")
                            $state.go('customer.dashboard');
                        }
                        }    
                    })
                });
        }
      
    };
}])

resourceApp.controller('LoginCtrl',["$scope","$rootScope","$location",function($scope,$rootScope,$location){
        $scope.logout = function(){
            localStorage.clear();
            $location.path('/');
        }
}])

resourceApp.factory('UserService', function ($resource) {

    return $resource('rest/user/:action', {},
        {
            authenticate: {
                method: 'POST',
                params: {'action': 'authenticate'},
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }
        }
    
    );
});

resourceApp.factory('BlogPostService', function ($resource,$http) {

//    return $resource('rest/blogposts/:id', {id: '@id'});
    var obj = {};
    obj.user = function(userName){
        return $http.get('rest/user/userDetails/' +userName);
    }
    return obj;
});