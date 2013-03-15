;
(function () {
  'use strict';

  angular.module('human-workflow', [])
    .config(['$routeProvider', function ($routeProvider) {
      $routeProvider
        .when('/systems-use-agreement',
        {
          templateUrl:'sua.html',
          controller:'SuaController'
        })
        .otherwise({redirectTo:'/systems-use-agreement'});
    }])
    .controller('SuaController',
      ['$scope', '$http',
        function(scope) {

          scope.license = 'paid.html';

        }
      ]
    );
})();