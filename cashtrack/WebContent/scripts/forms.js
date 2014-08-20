/**
 * New node file
 */

(function() {
        "use strict";
        angular.module("app.ui.form.ctrls", []).controller("TagsDemoCtrl", ["$scope",
            function($scope) {
                return $scope.tags = ["foo", "bar"]
            }
        ]).controller("DatepickerDemoCtrl", ["$scope",
            function($scope) {
                return $scope.today = function() {
                    return $scope.dt = new Date;
                }, $scope.today(), $scope.showWeeks = !0, $scope.toggleWeeks = function() {
                    return $scope.showWeeks = !$scope.showWeeks
                }, $scope.clear = function() {
                    return $scope.dt = null
                }, $scope.disabled = function(date, mode) {
                    return "day" === mode && (0 === date.getDay() || 6 === date.getDay())
                }, $scope.toggleMin = function() {
                    var _ref;
                    return $scope.minDate = null != (_ref = $scope.minDate) ? _ref : {
                        "null": new Date
                    }
                }, $scope.toggleMin(), $scope.open = function($event) {
                    return $event.preventDefault(), $event.stopPropagation(), $scope.opened = !0
                }, $scope.dateOptions = {
                    "year-format": "'yyyy'",
                    "starting-day": 1
                }, $scope.formats = ["dd/MM/yyyy", "shortDate"], $scope.format = $scope.formats[0]
            }
        ]).controller("TimepickerDemoCtrl", ["$scope",
            function($scope) {
                return $scope.mytime = new Date, $scope.hstep = 1, $scope.mstep = 15, $scope.options = {
                    hstep: [1, 2, 3],
                    mstep: [1, 5, 10, 15, 25, 30]
                }, $scope.ismeridian = !0, $scope.toggleMode = function() {
                    return $scope.ismeridian = !$scope.ismeridian
                }, $scope.update = function() {
                    var d;
                    return d = new Date, d.setHours(14), d.setMinutes(0), $scope.mytime = d
                }, $scope.changed = function() {
                    return void 0
                }, $scope.clear = function() {
                    return $scope.mytime = null;
                }
            }
        ]).controller("TypeaheadCtrl", ["$scope",
            function($scope) {
                return $scope.selected = void 0, $scope.states = ["Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Dakota", "North Carolina", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"]
            }
        ]).controller("RatingDemoCtrl", ["$scope",
            function($scope) {
                return $scope.rate = 7, $scope.max = 10, $scope.isReadonly = !1, $scope.hoveringOver = function(value) {
                    return $scope.overStar = value, $scope.percent = 100 * (value / $scope.max)
                }, $scope.ratingStates = [{
                    stateOn: "glyphicon-ok-sign",
                    stateOff: "glyphicon-ok-circle"
                }, {
                    stateOn: "glyphicon-star",
                    stateOff: "glyphicon-star-empty"
                }, {
                    stateOn: "glyphicon-heart",
                    stateOff: "glyphicon-ban-circle"
                }, {
                    stateOn: "glyphicon-heart"
                }, {
                    stateOff: "glyphicon-off"
                }]
            }
        ])
    }.call(this),
    function() {
        angular.module("app.ui.form.directives", []).directive("uiRangeSlider", [
            function() {
                return {
                    restrict: "A",
                    link: function(scope, ele) {
                        return ele.slider();
                    }
                }
            }
        ]).directive("uiFileUpload", [
            function() {
                return {
                    restrict: "A",
                    link: function(scope, ele) {
                        return ele.bootstrapFileInput();
                    }
                }
            }
        ]).directive("uiSpinner", [
            function() {
                return {
                    restrict: "A",
                    compile: function(ele) {
                        return ele.addClass("ui-spinner"), {
                            post: function() {
                                return ele.spinner();
                            }
                        }
                    }
                }
            }
        ]).directive("uiWizardForm", [
            function() {
                return {
                    link: function(scope, ele) {
                        return ele.steps();
                    }
                }
            }
        ])
    }.call(this),
    function() {
        "use strict";
        angular.module("app.form.validation", []).controller("wizardFormCtrl", ["$scope",
            function($scope) {
                return $scope.wizard = {
                    firstName: "some name",
                    lastName: "",
                    email: "",
                    password: "",
                    age: "",
                    address: ""
                }, $scope.isValidateStep1 = function() {
                    return void 0;
                }, $scope.finishedWizard = function() {
                    return void 0;
                }
            }
        ]).controller("formConstraintsCtrl", ["$scope",
            function($scope) {
                var original;
                return $scope.form = {
                    required: "",
                    minlength: "",
                    maxlength: "",
                    length_rage: "",
                    type_something: "",
                    confirm_type: "",
                    foo: "",
                    email: "",
                    url: "",
                    num: "",
                    minVal: "",
                    maxVal: "",
                    valRange: "",
                    pattern: ""
                }, original = angular.copy($scope.form), $scope.revert = function() {
                    return $scope.form = angular.copy(original), $scope.form_constraints.$setPristine()
                }, $scope.canRevert = function() {
                    return !angular.equals($scope.form, original) || !$scope.form_constraints.$pristine
                }, $scope.canSubmit = function() {
                    return $scope.form_constraints.$valid && !angular.equals($scope.form, original);
                }
            }
        ]).controller("signinCtrl", ["$scope",
            function($scope) {
                var original;
                return $scope.user = {
                    email: "",
                    password: ""
                }, $scope.showInfoOnSubmit = !1, original = angular.copy($scope.user), $scope.revert = function() {
                    return $scope.user = angular.copy(original), $scope.form_signin.$setPristine()
                }, $scope.canRevert = function() {
                    return !angular.equals($scope.user, original) || !$scope.form_signin.$pristine
                }, $scope.canSubmit = function() {
                    return $scope.form_signin.$valid && !angular.equals($scope.user, original)
                }, $scope.submitForm = function() {
                    return $scope.showInfoOnSubmit = !0, $scope.revert();
                }
            }
        ]).controller("signupCtrl", ["$scope",
            function($scope) {
                var original;
                return $scope.user = {
                    name: "",
                    email: "",
                    password: "",
                    confirmPassword: "",
                    age: ""
                }, $scope.showInfoOnSubmit = !1, original = angular.copy($scope.user), $scope.revert = function() {
                    return $scope.user = angular.copy(original), $scope.form_signup.$setPristine(), $scope.form_signup.confirmPassword.$setPristine()
                }, $scope.canRevert = function() {
                    return !angular.equals($scope.user, original) || !$scope.form_signup.$pristine
                }, $scope.canSubmit = function() {
                    return $scope.form_signup.$valid && !angular.equals($scope.user, original)
                }, $scope.submitForm = function() {
                    return $scope.showInfoOnSubmit = !0, $scope.revert();
                }
            }
        ]).directive("validateEquals", [
            function() {
                return {
                    require: "ngModel",
                    link: function(scope, ele, attrs, ngModelCtrl) {
                        var validateEqual;
                        return validateEqual = function(value) {
                            var valid;
                            return valid = value === scope.$eval(attrs.validateEquals), ngModelCtrl.$setValidity("equal", valid), "function" == typeof valid ? valid({
                                value: void 0
                            }) : void 0;
                        }, ngModelCtrl.$parsers.push(validateEqual), ngModelCtrl.$formatters.push(validateEqual), scope.$watch(attrs.validateEquals, function(newValue, oldValue) {
                            return newValue !== oldValue ? ngModelCtrl.$setViewValue(ngModelCtrl.$ViewValue) : void 0
                        })
                    }
                }
            }
        ])
    }.call(this),
    function() {
        "use strict";
        angular.module("app.page.ctrls", []).controller("invoiceCtrl", ["$scope", "$window",
            function($scope) {
                return $scope.printInvoice = function() {
                    var originalContents, popupWin, printContents;
                    return printContents = document.getElementById("invoice").innerHTML, originalContents = document.body.innerHTML, popupWin = window.open(), popupWin.document.open(), popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="styles/main.css" /></head><body onload="window.print()">' + printContents + "</html>"), popupWin.document.close();
                }
            }
        ])
    }).call(this);