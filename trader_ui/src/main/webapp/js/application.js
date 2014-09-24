'use strict';

/* App Module */

var traderUiApp = angular.module('traderUiApp', [
  'ngRoute',
  'traderUiControllers',
  'traderUiServices'
]);

traderUiApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/summary', {
        templateUrl: 'partials/summary.html',
        controller: 'StockSummaryController'
      }).
      otherwise({
        redirectTo: '/summary'
      });
  }]);
