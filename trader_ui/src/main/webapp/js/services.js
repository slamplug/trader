'use strict';

/* Services */

angular.module('traderUiServices', ['ngResource'])
    .factory('StockPricesService', ['$resource',
        function ($resource) {
            return $resource('/traderui/rest/stock/prices', {}, {
                getStockPrices: {method: 'GET', isArray: true}
            });
        }])
    .factory('StockAlertsService', ['$resource',
        function ($resource) {
            return $resource('/traderui/rest/stock/alerts', {}, {
                getStockAlerts: {method: 'GET', isArray: true}
            });
        }])
    .factory('StockAlertService', ['$resource',
        function ($resource) {
            var apiCall = $resource('/traderui/rest/stock/alerts',
                {symbol: '@symbol', alertBelow: '@alertBelow', alertAbove: '@alertAbove'}, {
                    post: {method: 'POST', headers: {'Content-Type': 'application/json'}}
                }
            );
            return apiCall;
        }])
  
  
