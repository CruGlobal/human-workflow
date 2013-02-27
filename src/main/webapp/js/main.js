;
(function () {
  'use strict';

  angular.module('cru-quickstart', [])
    .controller('HelloWorldController',
      ['$scope', '$http',
        function($scope, $http) {
          $scope.message = $http.get('api/');
        }
      ]
    );
})();