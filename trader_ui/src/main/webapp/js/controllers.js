'use strict';

/* Controllers */

var traderUiControllers = angular.module('traderUiControllers', []);

traderUiControllers.controller('StockSummaryController', ['$scope', '$timeout',
    'StockPricesService',
    'StockAlertsService',
    'StockAlertService',
    function ($scope, $timeout, StockPricesService, StockAlertsService, StockAlertService) {

        // order by on page
        $scope.orderProp = 'symbol';

        // Function to get the data
        $scope.getData = function() {
            $scope.stockPrices = StockPricesService.getStockPrices();
            $scope.stockAlerts = StockAlertsService.getStockAlerts();
        };

        // Get the data immediately
        $scope.getData();

        var timeout = $timeout( function refresh(){
            $scope.getData();
            timeout = $timeout(refresh, 2000);
        }, 2000);
		
		$scope.refreshStockPrices = function () {
            $scope.stockPrices = StockPricesService.getStockPrices();
        };

        $scope.refreshAlerts = function () {
            $scope.stockAlerts = StockAlertsService.getStockAlerts();
        };

        $scope.addWatchOnSymbol = function () {
            //alert($scope.companySymbol)
            StockAlertService.post({
                symbol: $scope.companySymbol,
                alertBelow: $scope.alertBelow,
                alertAbove: $scope.alertAbove
            });
        }
    }]);