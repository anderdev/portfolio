(function() {
        "use strict";
        angular.module("app", 
        		[
        		 "ngRoute", 
        		 "ngAnimate", 
        		 "ui.bootstrap", 
//        		 "easypiechart", 
        		 "textAngular", 
        		 "ui.tree", 
        		 "ngMap", 
        		 "ngTagsInput", 
        		 "app.controllers", 
        		 "app.directives", 
        		 "app.localization", 
        		 "app.nav", 
        		 "app.ui.ctrls", 
        		 "app.ui.directives", 
        		 "app.ui.services", 
//        		 "app.ui.map", 
        		 "app.form.validation", 
        		 "app.ui.form.ctrls", 
        		 "app.ui.form.directives", 
//        		 "app.tables", 
        		 "app.task", 
//        		 "app.chart.ctrls", 
//        		 "app.chart.directives", 
        		 "app.page.ctrls"
        		 ]).config(["$routeProvider",
            function($routeProvider) {
                var routes, setRoutes;
                return routes = ["dashboard", "ui/typography", "ui/buttons", "ui/icons", "ui/grids", "ui/widgets", "ui/components", "ui/timeline", "ui/nested-lists", "ui/pricing-tables", "ui/maps", "tables/static", "tables/dynamic", "tables/responsive", "forms/elements", "forms/layouts", "forms/validation", "forms/wizard", "charts/charts", "charts/flot", "charts/morris", "pages/404", "pages/500", "pages/blank", "pages/forgot-password", "pages/invoice", "pages/lock-screen", "pages/profile", "pages/signin", "pages/signup", "mail/compose", "mail/inbox", "mail/single", "tasks/tasks"], setRoutes = function(route) {
                    var config, url;
                    return url = "/" + route, config = {
                        templateUrl: "views/" + route + ".html"
                    }, $routeProvider.when(url, config), $routeProvider
                }, routes.forEach(function(route) {
                    return setRoutes(route)
                }), $routeProvider.when("/", {
                    redirectTo: "/pages/signin"
                }).when("/404", {
                    templateUrl: "views/pages/404.html"
                }).otherwise({
                    redirectTo: "/404"
                })
            }
        ])
    }.call(this),
    function() {
        "use strict";
        angular.module("app.directives", []).directive("imgHolder", [
            function() {
                return {
                    restrict: "A",
                    link: function(scope, ele) {
                        return Holder.run({
                            images: ele[0]
                        })
                    },
                }
            }
        ]).directive("customPage", function() {
            return {
                restrict: "A",
                controller: ["$scope", "$element", "$location",
                    function($scope, $element, $location) {
                        var addBg, path;
                        return path = function() {
                            return $location.path()
                        }, addBg = function(path) {
                            switch ($element.removeClass("body-wide body-lock"), path) {
                                case "/404":
                                case "/pages/404":
                                case "/pages/500":
                                case "/pages/signin":
                                case "/pages/signup":
                                case "/pages/forgot-password":
                                    return $element.addClass("body-wide");
                                case "/pages/lock-screen":
                                    return $element.addClass("body-wide body-lock")
                            }
                        }, addBg($location.path()), $scope.$watch(path, function(newVal, oldVal) {
                            return newVal !== oldVal ? addBg($location.path()) : void 0
                        })
                    }
                ]
            }
        }).directive("uiColorSwitch", [
            function() {
                return {
                    restrict: "A",
                    link: function(scope, ele) {
                        return ele.find(".color-option").on("click", function(event) {
                            var $this, hrefUrl, style;
                            if ($this = $(this), hrefUrl = void 0, style = $this.data("style"), "loulou" === style) hrefUrl = "styles/main.css", $('link[href^="styles/main"]').attr("href", hrefUrl);
                            else {
                                if (!style) return !1;
                                style = "-" + style, hrefUrl = "styles/main" + style + ".css", $('link[href^="styles/main"]').attr("href", hrefUrl)
                            }
                            return event.preventDefault()
                        })
                    },
                }
            }
        ]).directive("goBack", [
            function() {
                return {
                    restrict: "A",
                    controller: ["$scope", "$element", "$window",
                        function($scope, $element, $window) {
                            return $element.on("click", function() {
                                return $window.history.back()
                            })
                        }
                    ]
                }
            }
        ])
    }).call(this);
    
        
    