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
      ['$scope', '$resource', '$routeParams', '$window',
        function(scope, resource, routeParams, window) {

          scope.returnUrl = routeParams.returnUrl;

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
            if (_.isArray(response)) {
              scope.pastSignature = _.first(response);
              scope.signed = response.length > 0;
            }
          });

          scope.signAgreement = function() {
            signature.save(function() {
              scope.signed = true;
              if(!_.isUndefined(scope.returnUrl))
                redirectTo(scope.returnUrl)
            })
          }

          var redirectTo = function(url) {
            window.location = url;
          }

        }
      ]
    );
})();