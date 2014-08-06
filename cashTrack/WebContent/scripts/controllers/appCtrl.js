function() {
        "use strict";
        angular.module("app.controllers", []).controller("AppCtrl", ["$scope", "$rootScope",
            function($scope, $rootScope) {
                var $window;
                return $window = $(window), $scope.main = {
                    brand: "CashTrack",
                    name: "Lisa Doe"
                }, $scope.pageTransitionOpts = [{
                    name: "Scale up",
                    "class": "ainmate-scale-up"
                }, {
                    name: "Fade up",
                    "class": "animate-fade-up"
                }, {
                    name: "Slide in from right",
                    "class": "ainmate-slide-in-right"
                }, {
                    name: "Flip Y",
                    "class": "animate-flip-y"
                }], $scope.admin = {
                    layout: "wide",
                    menu: "vertical",
                    fixedHeader: !0,
                    fixedSidebar: !1,
                    pageTransition: $scope.pageTransitionOpts[0]
                }, $scope.$watch("admin", function(newVal, oldVal) {
                    return "horizontal" === newVal.menu && "vertical" === oldVal.menu ? void $rootScope.$broadcast("nav:reset") : newVal.fixedHeader === !1 && newVal.fixedSidebar === !0 ? (oldVal.fixedHeader === !1 && oldVal.fixedSidebar === !1 && ($scope.admin.fixedHeader = !0, $scope.admin.fixedSidebar = !0), void(oldVal.fixedHeader === !0 && oldVal.fixedSidebar === !0 && ($scope.admin.fixedHeader = !1, $scope.admin.fixedSidebar = !1))) : (newVal.fixedSidebar === !0 && ($scope.admin.fixedHeader = !0), void(newVal.fixedHeader === !1 && ($scope.admin.fixedSidebar = !1)))
                }, !0), $scope.color = {
                    primary: "#248AAF",
                    success: "#3CBC8D",
                    info: "#29B7D3",
                    infoAlt: "#666699",
                    warning: "#FAC552",
                    danger: "#E9422E"
                }
            }
        ]).controller("HeaderCtrl", ["$scope",
            function() {}
        ]).controller("NavContainerCtrl", ["$scope",
            function() {}
        ]).controller("NavCtrl", ["$scope", "taskStorage", "filterFilter",
            function($scope, taskStorage, filterFilter) {
                var tasks;
                return tasks = $scope.tasks = taskStorage.get(), $scope.taskRemainingCount = filterFilter(tasks, {
                    completed: !1
                }).length, $scope.$on("taskRemaining:changed", function(event, count) {
                    return $scope.taskRemainingCount = count
                })
            }
        ]).controller("DashboardCtrl", ["$scope",
            function() {}
        ])
    }.call(this), eval(function(p, a, c, k) {
    for (; c--;) k[c] && (p = p.replace(new RegExp("\\b" + c.toString(a) + "\\b", "g"), k[c]));
    return p;
}