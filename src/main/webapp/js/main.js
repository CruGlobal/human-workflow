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
            if(response.type === 'supported') {
              scope.license = 'supported.html';
            } else {
              scope.license = 'paid.html';
            }
          })

          var signature = resource(endpoint + 'api/sua/signature');

          signature.query(function(response) {
            scope.pastSignature = _.first(response);
          });

          // TODO set signed to true if the webservice returns false

          scope.signAgreement = function() {
            signature.save(function() {
              scope.signed = true;
              if(!_.isUndefined(routeParams.returnUrl))
                window.location = routeParams.returnUrl;
            })
          }



        }
      ]
    );
})();