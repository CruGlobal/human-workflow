;
(function () {
  'use strict';

  angular.module('human-workflow', ['ngResource'])
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
      ['$scope', '$resource', '$routeParams',
        function(scope, resource, routeParams) {

          var endpoint = 'http://localhost\\:8080/interrupt-webapp/';

          var userType = resource(endpoint + 'api/sua/userType');

          userType.get(function(response) {
            console.log(response);
            if(response.type === 'supported') {
              scope.license = 'supported.html';
            } else {
              scope.license = 'paid.html';
            }
          })

          var sign = resource(endpoint + 'api/sua/signature');

          // TODO set signed to true if the webservice returns false

          scope.signAgreement = function() {
            sign.save(function() {
              scope.signed = true;
              if(!_.isUndefined(routeParams.returnUrl))
                window.location = routeParams.returnUrl;
            })
          }



        }
      ]
    );
})();