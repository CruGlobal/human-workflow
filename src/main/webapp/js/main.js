;
(function () {
  'use strict';

  angular.module('human-workflow', ['ngResource'])
    .controller('SuaController',
      ['$scope', '$resource', '$window', '$location',
        function(scope, resource, window, location) {

            var absUrl = location.absUrl();
            var searchString = 'returnUrl=';
            var start = absUrl.indexOf(searchString) + searchString.length;
            var length = absUrl.indexOf('&', start) - start;
            if(length > 0)
                scope.returnUrl = absUrl.substr(start, length);
            else
                scope.returnUrl = absUrl.substr(start);

          var userType = resource('api/sua/userType');

          userType.get(function(response) {
            if(response.type === 'supported') {
              scope.license = 'supported.html';
            } else {
              scope.license = 'paid.html';
            }
          })

          var signature = resource('api/sua/signature');

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
            if(url.indexOf("%2F") > 0)
                window.location = decodeURIComponent(url);
            else
                window.location = url;
          }

        }
      ]
    );
})();