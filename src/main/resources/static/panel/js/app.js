var app = angular.module('adminApp', ['ngRoute']);

app.config(function ($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl: 'pages/dashboard.html',
            controller: 'DashboardController'
        })
        .when("/users", {
            templateUrl: 'pages/users.html',
            controller: 'UsersController'
        })
        .when("/editUser/:username", {
            templateUrl: 'pages/users/new-edit-user-view.html',
            controller: 'NewEditUserController'
        })
        .when("/addUser", {
            templateUrl: 'pages/users/new-edit-user-view.html',
            controller: 'NewEditUserController'
        })
        .when("/messages", {
            templateUrl: 'pages/messages.html',
            controller: 'MessagesController'
        })
        .when("/blocks", {
            templateUrl: 'pages/blocks.html',
            controller: 'BlocksController'
        })
        .when("/editBlock/:id", {
            templateUrl: 'pages/blocks/new-edit-block-view.html',
            controller: 'NewEditBlockController'
        })
        .when("/addBlock/:classType", {
            templateUrl: 'pages/blocks/new-edit-block-view.html',
            controller: 'NewEditBlockController'
        })
        .when("/seo", {
            templateUrl: 'pages/seo.html',
            controller: 'SeoController'
        })
        .when("/settings", {
            templateUrl: 'pages/settings.html',
            controller: 'SettingsController'
        });
});

app.controller('MainController', function($scope, $interval, $http, ConnectorAdmin){
    $scope.version = "";
    $scope.userInfo = {};
    $scope.viewData = {};
    $scope.newMessagesCount = 0;

    $scope.getBuildInfo = function (){
        $http.get("build.json")
            .then(
                function (response) {
                    $scope.version = response.data["build.version"];
                    response.data["build.number"] ? $scope.version = $scope.version + "." + response.data["build.number"] : "";
                    response.data["build.timestamp"] ? $scope.version = $scope.version + "(" + response.data["build.timestamp"] + ")" : "";
                }
            );
    };
    $scope.getBuildInfo();

    $scope.getUserInfo = function (){
        return ConnectorAdmin.getCurrentUser().then(
            function (data) {
                $scope.userInfo = data;
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getUserInfo();

    $scope.getNewMessagesCount = function () {
        return ConnectorAdmin.getNewMessagesCount().then(
            function (data) {
                if(data > $scope.newMessagesCount){
                    $scope.$broadcast('newMessageReceived', {});
                }
                $scope.newMessagesCount = data;
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.logout = function () {
        window.location.replace(config.context + '/logout');
    };

    $scope.getNewMessagesCount();
    $interval(function() {
        $scope.getNewMessagesCount();
    }, 10000);
});

app.controller('DashboardController', function($scope, ConnectorAdmin){
    $scope.viewData.header = "Dashboard";
    $scope.viewData.icon = "fa-dashboard";
    $scope.diskInfo = {
        "diskSizeBytes": 121123069952,
        "diskUsedBytes": 113806368768,
        "diskAvailableBytes": 7316701184,
        "diskCapacityPercent": 6.0407166
    };
    $scope.memoryInfo = {
        "memoryTotalBytes": 268435456,
        "memoryFreeBytes": 234049120,
        "swapTotalBytes": 286435456,
        "swapFreeBytes": 243049120
    };
    $scope.cpuInfo = {
        "cpuModel": "Intel Core i5",
        "cpuNumberOfCores": 4,
        "cpuSpeed": 2700,
        "cpuUsage": 34.5
    };

    $scope.ym = {};
    $scope.ym.enabled = false;
    $scope.ym.geography = {};
    $scope.ym.url = {};
    $scope.ym.users = {};
    $scope.ym.newUsers = {};
    $scope.ym.trafficSource = {};

    // $scope.ym.geography = {"result":{"query":{"ids":[51672647],"dimensions":["ym:s:regionCountry","ym:s:regionArea","ym:s:regionCity"],"metrics":["ym:s:visits","ym:s:users","ym:s:bounceRate","ym:s:pageDepth","ym:s:avgVisitDurationSeconds"],"sort":["-ym:s:visits"],"date1":"2020-07-20","date2":"2020-08-19","filters":"ym:s:regionCountry!n","limit":50,"offset":1,"attribution":"LastSign","group":"day","auto_group_size":"1","attr_name":"","quantile":"50","offline_window":"21","currency":"USD","adfox_event_id":"0"},"data":[{"dimension":{"icon_id":"95","icon_type":"country","iso_name":"CA","id":"95","name":"Canada"},"metrics":[40,6,27.5,2.925,423.45],"expand":true,"dimensionValue":"95","dimensionPath":["95"],"id":"[\"95\"]"},{"dimension":{"icon_id":"10063","icon_type":"country","iso_name":"IE","id":"10063","name":"Ireland"},"metrics":[4,4,100,1,0],"expand":true,"dimensionValue":"10063","dimensionPath":["10063"],"id":"[\"10063\"]"},{"dimension":{"icon_id":"180","icon_type":"country","iso_name":"RS","id":"180","name":"Serbia"},"metrics":[4,2,25,1,11.25],"expand":true,"dimensionValue":"180","dimensionPath":["180"],"id":"[\"180\"]"},{"dimension":{"icon_id":"84","icon_type":"country","iso_name":"US","id":"84","name":"United States"},"metrics":[4,4,75,1.25,6.25],"expand":true,"dimensionValue":"84","dimensionPath":["84"],"id":"[\"84\"]"},{"dimension":{"icon_id":"181","icon_type":"country","iso_name":"IL","id":"181","name":"Israel"},"metrics":[3,2,0,1,32.33333333],"expand":true,"dimensionValue":"181","dimensionPath":["181"],"id":"[\"181\"]"},{"dimension":{"icon_id":"96","icon_type":"country","iso_name":"DE","id":"96","name":"Germany"},"metrics":[2,2,100,1,0],"expand":true,"dimensionValue":"96","dimensionPath":["96"],"id":"[\"96\"]"},{"dimension":{"icon_id":"124","icon_type":"country","iso_name":"FR","id":"124","name":"France"},"metrics":[1,1,100,1,0],"expand":false,"dimensionValue":"124","dimensionPath":["124"],"id":"[\"124\"]"},{"dimension":{"icon_id":"204","icon_type":"country","iso_name":"ES","id":"204","name":"Spain"},"metrics":[1,1,100,1,0],"expand":true,"dimensionValue":"204","dimensionPath":["204"],"id":"[\"204\"]"},{"dimension":{"icon_id":"20957","icon_type":"country","iso_name":"SD","id":"20957","name":"Sudan"},"metrics":[1,1,100,1,0],"expand":false,"dimensionValue":"20957","dimensionPath":["20957"],"id":"[\"20957\"]"}],"meta":{"dimensions":[{"id":"RegionCountry","dim":"ym:s:regionCountry","name":"Country","short_name":"Country","description":"Countries where site users are located.","type":"country","internal_type":"UInt32","allow_filters":false,"fields":{"name":{"id":"RegionCountryName","dim":"ym:s:regionCountryName","name":"Country","short_name":"Country","description":"Countries where site users are located.","type":"country","internal_type":"String","allow_filters":true,"allow_sort":true,"default_sort":"asc","allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},"id":{"id":"RegionCountry","dim":"ym:s:regionCountry","name":"Country","short_name":"Country","description":"Countries where site users are located.","type":"country","internal_type":"UInt32","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},"icon_id":{"id":"RegionCountryIcon","dim":"ym:s:regionCountryIcon","name":"Country","short_name":"Country","description":"Countries where site users are located.","type":"country","internal_type":"UInt32","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},"iso_name":{"id":"RegionCountryIsoName","dim":"ym:s:regionCountryIsoName","name":"Country","short_name":"Country","description":"Countries where site users are located.","type":"country","internal_type":"UInt32","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},"icon_type":{"id":"RegionCountryIconType","dim":"ym:s:regionCountryIconType","name":"Country","short_name":"Country","description":"Countries where site users are located.","type":"country","internal_type":"String","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true}},"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},{"id":"RegionArea","dim":"ym:s:regionArea","name":"Area","short_name":"Area","description":"Oblasts where site users are located.","type":"area","internal_type":"UInt32","allow_filters":false,"fields":{"name":{"id":"RegionAreaName","dim":"ym:s:regionAreaName","name":"Area","short_name":"Area","description":"Oblasts where site users are located.","type":"area","internal_type":"String","allow_filters":true,"allow_sort":true,"default_sort":"asc","allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},"id":{"id":"RegionArea","dim":"ym:s:regionArea","name":"Area","short_name":"Area","description":"Oblasts where site users are located.","type":"area","internal_type":"UInt32","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},"iso_name":{"id":"RegionAreaIsoName","dim":"ym:s:regionAreaIsoName","name":"Area","short_name":"Area","description":"Oblasts where site users are located.","type":"area","internal_type":"UInt32","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true}},"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},{"id":"RegionCity","dim":"ym:s:regionCity","name":"City","short_name":"City","description":"Cities where site users are located.","type":"city","internal_type":"UInt32","allow_filters":false,"fields":{"name":{"id":"RegionCityName","dim":"ym:s:regionCityName","name":"City","short_name":"City","description":"Cities where site users are located.","type":"city","internal_type":"String","allow_filters":true,"allow_sort":true,"default_sort":"asc","allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},"id":{"id":"RegionCity","dim":"ym:s:regionCity","name":"City","short_name":"City","description":"Cities where site users are located.","type":"city","internal_type":"UInt32","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true},"iso_name":{"id":"RegionCityIsoName","dim":"ym:s:regionCityIsoName","name":"City","short_name":"City","description":"Cities where site users are located.","type":"city","internal_type":"UInt32","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true}},"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"disjoint":true}],"metrics":[{"dim":"ym:s:visits","metric_string":"ym:s:visits","name":"Sessions","description":"Total number of visits.","allow_normalisation":true,"allow_filters":true,"type":"int","allow_sort":true,"default_sort":"desc","support_confidence":true,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"additive":true},{"dim":"ym:s:users","metric_string":"ym:s:users","name":"Users","description":"Number of unique users.","allow_normalisation":true,"allow_filters":true,"type":"int","allow_sort":true,"default_sort":"desc","permission_scope":"common","since":"2009-01-01","required_keys":["counter"],"additive":true},{"dim":"ym:s:bounceRate","metric_string":"ym:s:bounceRate","name":"Bounce rate","description":"Percentage of sessions, during which at least one page view lasting less than 15 seconds took place.","allow_filters":true,"type":"percents","allow_sort":true,"default_sort":"desc","support_confidence":true,"negative":true,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"additive":true},{"dim":"ym:s:pageDepth","metric_string":"ym:s:pageDepth","name":"Page depth","description":"Number of pages viewed by users during visit.","allow_filters":true,"type":"double","allow_sort":true,"default_sort":"desc","support_confidence":true,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"additive":true},{"dim":"ym:s:avgVisitDurationSeconds","metric_string":"ym:s:avgVisitDurationSeconds","name":"Time on site","description":"Average session duration in minutes and seconds.","allow_filters":true,"type":"second","allow_sort":true,"default_sort":"desc","support_confidence":true,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"additive":true}]},"total_rows":9,"total_rows_rounded":false,"sampled":false,"sampleable":false,"contains_sensitive_data":false,"sample_share":1,"max_sample_share":1,"sample_size":60,"sample_space":60,"data_lag":98,"totals":[60,23,40,2.3,285.08333333],"min":[1,1,0,1,0],"max":[40,6,100,2.925,423.45]},"profile":false};
    // $scope.ym.url = {"result":{"query":{"ids":[51672647],"dimensions":["ym:pv:URLHash"],"metrics":["ym:pv:pageviews"],"sort":["-ym:pv:pageviews"],"date1":"2020-07-19","date2":"2020-08-18","filters":"ym:pv:URLHash!n","limit":10,"offset":1,"attribution":"LastSign","group":"Week","auto_group_size":"1","attr_name":"","quantile":"50","offline_window":"21","currency":"USD","adfox_event_id":"0"},"data":[{"dimensions":[{"id":"3729012656806482819","favicon":"localhost","name":"http://localhost:8080/"}],"metrics":[42]},{"dimensions":[{"id":"14811726624536252083","favicon":"192.168.100.200","name":"http://192.168.100.200:8080/website-dev/"}],"metrics":[33]},{"dimensions":[{"id":"13152052789045781452","favicon":"shenderov.me","name":"https://shenderov.me/"}],"metrics":[23]},{"dimensions":[{"id":"9924477222638099971","favicon":"votie.ca","name":"http://votie.ca/"}],"metrics":[7]},{"dimensions":[{"id":"4946314150587265219","favicon":"octant.ca","name":"http://octant.ca/"}],"metrics":[6]},{"dimensions":[{"id":"12374299066277922811","favicon":"localhost","name":"http://localhost:8080/front/index.html"}],"metrics":[5]},{"dimensions":[{"id":"11757978131274186386","favicon":"localhost","name":"http://localhost:63342/personal-site/dist/index.html?_ijt=d99q6kqouc2p8lq59u5or28tf2"}],"metrics":[4]},{"dimensions":[{"id":"13004267862353689079","favicon":"localhost","name":"http://localhost:8080/index.html"}],"metrics":[3]},{"dimensions":[{"id":"15340030889135721681","favicon":"104.248.107.34","name":"http://104.248.107.34/"}],"metrics":[2]},{"dimensions":[{"id":"2618286942062124701","favicon":"165.227.40.53","name":"http://165.227.40.53/"}],"metrics":[2]}],"meta":{"dimensions":[{"id":"URLHash","dim":"ym:pv:URLHash","name":"URL","short_name":"URL","type":"url","internal_type":"UInt64","allow_filters":false,"fields":{"name":{"id":"URLHashName","dim":"ym:pv:URLHashName","name":"URL","short_name":"URL","type":"url","internal_type":"String","allow_filters":true,"allow_sort":true,"default_sort":"asc","allow_null":false,"permission_scope":"common","since":"2013-06-19","required_keys":["counter"],"disjoint":true},"id":{"id":"URLHash","dim":"ym:pv:URLHash","name":"URL","short_name":"URL","type":"url","internal_type":"UInt64","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2013-06-19","required_keys":["counter"],"disjoint":true},"favicon":{"id":"URLHashFavicon","dim":"ym:pv:URLHashFavicon","name":"URL","short_name":"URL","type":"url","internal_type":"String","allow_filters":true,"allow_sort":true,"default_sort":"asc","allow_null":false,"permission_scope":"common","since":"2013-06-19","required_keys":["counter"],"disjoint":true}},"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2013-06-19","required_keys":["counter"],"disjoint":true}],"metrics":[{"dim":"ym:pv:pageviews","metric_string":"ym:pv:pageviews","name":"Pageviews","description":"Number of page views on the site in the reporting period.","allow_normalisation":true,"allow_filters":true,"type":"int","allow_sort":true,"default_sort":"desc","support_confidence":true,"permission_scope":"common","since":"2013-06-19","required_keys":["counter"],"additive":true}]},"total_rows":18,"total_rows_rounded":false,"sampled":false,"sampleable":false,"contains_sensitive_data":false,"sample_share":1,"max_sample_share":1,"sample_size":138,"sample_space":138,"data_lag":89,"totals":[138],"min":[2],"max":[42]},"profile":false};
    // $scope.ym.users = {"result":{"query":{"ids":[51672647],"dimensions":[],"metrics":["ym:s:users"],"sort":["-ym:s:users"],"date1":"2020-07-19","date2":"2020-08-18","filters":"","attribution":"LastSign","group":"day","auto_group_size":"1","attr_name":"","quantile":"50","offline_window":"21","currency":"USD","adfox_event_id":"0","auto_group_type":"day"},"data":[{"dimensions":[],"metrics":[[0,2,2,1,1,1,1,0,1,0,1,0,0,2,2,2,5,4,4,1,2,3,1,2,1,1,0,1,0,0,0]]}],"meta":{"dimensions":[],"metrics":[{"dim":"ym:s:users","metric_string":"ym:s:users","name":"Users","description":"Number of unique users.","allow_normalisation":true,"allow_filters":true,"type":"int","allow_sort":true,"default_sort":"desc","permission_scope":"common","since":"2009-01-01","required_keys":["counter"],"additive":true}]},"total_rows":22,"total_rows_rounded":false,"sampled":false,"sampleable":false,"contains_sensitive_data":false,"sample_share":1,"max_sample_share":1,"sample_size":60,"sample_space":60,"data_lag":99,"totals":[[23]],"time_intervals":[["2020-07-19","2020-07-19"],["2020-07-20","2020-07-20"],["2020-07-21","2020-07-21"],["2020-07-22","2020-07-22"],["2020-07-23","2020-07-23"],["2020-07-24","2020-07-24"],["2020-07-25","2020-07-25"],["2020-07-26","2020-07-26"],["2020-07-27","2020-07-27"],["2020-07-28","2020-07-28"],["2020-07-29","2020-07-29"],["2020-07-30","2020-07-30"],["2020-07-31","2020-07-31"],["2020-08-01","2020-08-01"],["2020-08-02","2020-08-02"],["2020-08-03","2020-08-03"],["2020-08-04","2020-08-04"],["2020-08-05","2020-08-05"],["2020-08-06","2020-08-06"],["2020-08-07","2020-08-07"],["2020-08-08","2020-08-08"],["2020-08-09","2020-08-09"],["2020-08-10","2020-08-10"],["2020-08-11","2020-08-11"],["2020-08-12","2020-08-12"],["2020-08-13","2020-08-13"],["2020-08-14","2020-08-14"],["2020-08-15","2020-08-15"],["2020-08-16","2020-08-16"],["2020-08-17","2020-08-17"],["2020-08-18","2020-08-18"]],"annotations":[[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]]},"profile":false};
    // $scope.ym.newUsers = {"result":{"query":{"ids":[51672647],"dimensions":[],"metrics":["ym:s:newUsers"],"sort":["-ym:s:newUsers"],"date1":"2020-07-19","date2":"2020-08-18","filters":"","attribution":"LastSign","group":"day","auto_group_size":"1","attr_name":"","quantile":"50","offline_window":"21","currency":"USD","adfox_event_id":"0","auto_group_type":"day"},"data":[{"dimensions":[],"metrics":[[0,1,1,0,1,1,0,0,0,0,1,0,0,1,2,0,4,1,3,0,1,2,0,1,0,0,0,0,0,0,0]]}],"meta":{"dimensions":[],"metrics":[{"dim":"ym:s:newUsers","metric_string":"ym:s:newUsers","name":"New users","description":"Number of new users.","allow_filters":true,"type":"int","allow_sort":true,"default_sort":"desc","permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"additive":true}]},"total_rows":22,"total_rows_rounded":false,"sampled":false,"sampleable":false,"contains_sensitive_data":false,"sample_share":1,"max_sample_share":1,"sample_size":60,"sample_space":60,"data_lag":97,"totals":[[20]],"time_intervals":[["2020-07-19","2020-07-19"],["2020-07-20","2020-07-20"],["2020-07-21","2020-07-21"],["2020-07-22","2020-07-22"],["2020-07-23","2020-07-23"],["2020-07-24","2020-07-24"],["2020-07-25","2020-07-25"],["2020-07-26","2020-07-26"],["2020-07-27","2020-07-27"],["2020-07-28","2020-07-28"],["2020-07-29","2020-07-29"],["2020-07-30","2020-07-30"],["2020-07-31","2020-07-31"],["2020-08-01","2020-08-01"],["2020-08-02","2020-08-02"],["2020-08-03","2020-08-03"],["2020-08-04","2020-08-04"],["2020-08-05","2020-08-05"],["2020-08-06","2020-08-06"],["2020-08-07","2020-08-07"],["2020-08-08","2020-08-08"],["2020-08-09","2020-08-09"],["2020-08-10","2020-08-10"],["2020-08-11","2020-08-11"],["2020-08-12","2020-08-12"],["2020-08-13","2020-08-13"],["2020-08-14","2020-08-14"],["2020-08-15","2020-08-15"],["2020-08-16","2020-08-16"],["2020-08-17","2020-08-17"],["2020-08-18","2020-08-18"]],"annotations":[[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[],[]]},"profile":false};
    // $scope.ym.trafficSource = {"result":{"query":{"ids":[51672647],"dimensions":["ym:s:lastSignTrafficSource"],"metrics":["ym:s:visits"],"sort":["-ym:s:visits"],"date1":"2020-07-19","date2":"2020-08-18","filters":"","attribution":"LastSign","group":"all","auto_group_size":"1","attr_name":"","quantile":"50","offline_window":"21","currency":"USD","adfox_event_id":"0","auto_group_type":"all"},"data":[{"dimensions":[{"icon_id":"1","icon_type":"traffic-source","name":"Link traffic","id":"referral"}],"metrics":[[30]]},{"dimensions":[{"icon_id":"0","icon_type":"traffic-source","name":"Direct traffic","id":"direct"}],"metrics":[[20]]},{"dimensions":[{"icon_id":"4","icon_type":"traffic-source","name":"Cached page traffic","id":"saved"}],"metrics":[[5]]},{"dimensions":[{"icon_id":"2","icon_type":"traffic-source","name":"Search engine traffic","id":"organic"}],"metrics":[[4]]},{"dimensions":[{"icon_id":"8","icon_type":"traffic-source","name":"Social network traffic","id":"social"}],"metrics":[[1]]}],"meta":{"dimensions":[{"id":"LastSignTrafficSource","dim":"ym:s:lastSignTrafficSource","name":"Traffic source","short_name":"Traffic source","type":"traffic-source","internal_type":"Int8","allow_filters":false,"fields":{"name":{"id":"LastSignTrafficSourceName","dim":"ym:s:lastSignTrafficSourceName","name":"Traffic source","short_name":"Traffic source","type":"traffic-source","internal_type":"Int8","allow_filters":false,"allow_sort":false,"allow_null":true,"permission_scope":"common","since":"2014-10-26","required_keys":["counter"],"disjoint":true},"id":{"id":"LastSignTrafficSource","dim":"ym:s:lastSignTrafficSource","name":"Traffic source","short_name":"Traffic source","type":"traffic-source","internal_type":"Int8","allow_filters":false,"allow_sort":false,"allow_null":true,"permission_scope":"common","since":"2014-10-26","required_keys":["counter"],"disjoint":true},"icon_id":{"id":"LastSignTrafficSourceIcon","dim":"ym:s:lastSignTrafficSourceIcon","name":"Traffic source","short_name":"Traffic source","type":"traffic-source","internal_type":"Int8","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2014-10-26","required_keys":["counter"],"disjoint":true},"icon_type":{"id":"LastSignTrafficSourceIconType","dim":"ym:s:lastSignTrafficSourceIconType","name":"Traffic source","short_name":"Traffic source","type":"traffic-source","internal_type":"String","allow_filters":false,"allow_sort":false,"allow_null":false,"permission_scope":"common","since":"2014-10-26","required_keys":["counter"],"disjoint":true}},"allow_sort":false,"allow_null":true,"permission_scope":"common","since":"2014-10-26","required_keys":["counter"],"disjoint":true}],"metrics":[{"dim":"ym:s:visits","metric_string":"ym:s:visits","name":"Sessions","description":"Total number of visits.","allow_normalisation":true,"allow_filters":true,"type":"int","allow_sort":true,"default_sort":"desc","support_confidence":true,"permission_scope":"common","since":"2009-01-18","required_keys":["counter"],"additive":true}]},"total_rows":1,"total_rows_rounded":false,"sampled":false,"sampleable":false,"contains_sensitive_data":false,"sample_share":1,"max_sample_share":1,"sample_size":60,"sample_space":60,"data_lag":97,"totals":[[60]],"last_period_index":0,"time_intervals":[["2020-07-19","2020-08-18"]]},"profile":false};

    $scope.getYMData = function (type) {
        return ConnectorAdmin.getYMData(type).then(
            function (data) {
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.getYMGeographyData = function () {
        return ConnectorAdmin.getYMData("geography").then(
            function (data) {
                $scope.ym.geography = data;
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getYMGeographyData();

    $scope.getYMUrlsData = function () {
        return ConnectorAdmin.getYMData("urls").then(
            function (data) {
                $scope.ym.url = data;
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getYMUrlsData();

    $scope.ym.users = $scope.getYMData("users");
    $scope.ym.newUsers = $scope.getYMData("new_users");
    $scope.ym.trafficSource = $scope.getYMData("traffic_source");


    $scope.drawUsersChart = function () {
        new Chartist.Line('.users-chart', {
            labels: Array.from(new Set([].concat.apply([], $scope.ym.users.time_intervals))),
            series: [
                $scope.ym.users.data[0].metrics[0]
            ]
        }, {
            fullWidth: true,
            chartPadding: {
                right: 40
            }
        });
    };
    //$scope.drawUsersChart();

    $scope.drawNewUsersChart = function () {
        new Chartist.Line('.new-users-chart', {
            labels: Array.from(new Set([].concat.apply([], $scope.ym.newUsers.time_intervals))),
            series: [
                $scope.ym.newUsers.data[0].metrics[0]
            ]
        }, {
            fullWidth: true,
            chartPadding: {
                right: 40
            }
        });
    };
    //$scope.drawNewUsersChart();

    $scope.drawTrafficSource = function () {
        $scope.labels = [];
        $scope.values = [];

        var data = {
            labels: $scope.labels,
            series: $scope.values
        };

        var options = {
            labelInterpolationFnc: function(value) {
                return value[0]
            }
        };

        var responsiveOptions = [
            ['screen and (min-width: 640px)', {
                chartPadding: 30,
                labelOffset: 100,
                labelDirection: 'explode',
                labelInterpolationFnc: function(value) {
                    return value;
                }
            }],
            ['screen and (min-width: 1024px)', {
                labelOffset: 80,
                chartPadding: 20
            }]
        ];


        $scope.getLabels = function () {
            for(let i = 0; i < $scope.ym.trafficSource.data.length; i++){
                $scope.labels.push($scope.ym.trafficSource.data[i].dimensions[0].name)
            }
        };
        $scope.getLabels();

        $scope.getValues = function () {
            for(let i = 0; i < $scope.ym.trafficSource.data.length; i++){
                $scope.values.push($scope.ym.trafficSource.data[i].metrics[0][0])
            }
        };
        $scope.getValues();

        new Chartist.Pie('.ct-chart3', data, options, responsiveOptions);
    };
    //$scope.drawTrafficSource();

});

app.controller('UsersController', function($scope, $location, ConnectorAdmin){
    $scope.viewData.header = "Users";
    $scope.viewData.icon = "fa-address-book";
    $scope.users = [];

    $scope.getAllUsers = function () {
        return ConnectorAdmin.getAllUsers().then(
            function (data) {
                $scope.users = data;
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getAllUsers();

    $scope.convertRoles = function (authorities) {
        let result = "";
        authorities.forEach(function(authority) {
            result += authority.name.split('_')[1] + ', ';
        });
        return result.substring(0, result.length-2);
    };

    $scope.addUser = function () {
        $location.path('/addUser');
    };

    $scope.editUser = function (id) {
        $location.path('/editUser/' + id);
    };

    $scope.deleteUser = function (username, index) {
        return ConnectorAdmin.deleteUser(username).then(
            function (data) {
                $scope.users.splice(index, 1);
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
});

app.controller('NewEditUserController', function($scope, $location, $routeParams, ConnectorAdmin){
    $scope.username = $routeParams.username;
    $scope.viewData.header = "Users";
    $scope.viewData.icon = "fa-address-book";
    $scope.user = {};
    $scope.user.authorities = [];
    $scope.authorities = [];
    $scope.newPasswordModalOpen = false;

    $scope.getAuthorities = function () {
        return ConnectorAdmin.getAuthorities().then(
            function (data) {
                $scope.authorities = data;
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getAuthorities();

    $scope.getUserDetails = function (username) {
        return ConnectorAdmin.getUserDetails(username).then(
            function (data) {
                $scope.setUser(data);
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.setUser = function (user) {
        $scope.user = user;
        $scope.user.lastPasswordResetDate = new Date(user.lastPasswordResetDate);
    };

    if($scope.username){
        $scope.getUserDetails($scope.username);
    }

    $scope.addAuthority = function (authority) {
        let found = false;
        for(let i = 0; i < $scope.user.authorities.length; i++) {
            if ($scope.user.authorities[i].id === authority.id) {
                found = true;
                break;
            }
        }
        if(!found){
            $scope.user.authorities.push(authority);
        }
    };

    $scope.removeAuthority = function (index) {
        $scope.user.authorities.splice(index, 1);
    };

    $scope.cancel = function () {
        $location.path('/users');
        $scope.user = {};
    };

    $scope.save = function (user) {
        if(user.id){
            $scope.updateUser(user);
        }else{
            $scope.createUser(user);
        }
    };

    $scope.updateUser = function (user) {
        return ConnectorAdmin.updateUser(user).then(
            function (data) {
                $location.path('/users');
                $scope.user = {};
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.createUser = function (user) {
        return ConnectorAdmin.createUser(user).then(
            function (data) {
                $location.path('/users');
                $scope.user = {};
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.openResetPasswordModal = function () {
        $scope.newPasswordModalOpen = true;
    };

    $scope.closeResetPasswordModal = function () {
        $scope.newPasswordModalOpen = false;
    };

    $scope.resetPassword = function (username, password) {
        $scope.wrapper = {};
        $scope.wrapper.username = username;
        $scope.wrapper.value = password;
        return ConnectorAdmin.changePassword($scope.wrapper).then(
            function (data) {
                $scope.setUser(data);
                $scope.closeResetPasswordModal();
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

});

app.controller('MessagesController', function($scope, ConnectorAdmin){
    $scope.viewData.header = "Messages";
    $scope.viewData.icon = "fa-envelope";
    $scope.modalOpen = false;
    $scope.messages = [];
    $scope.currentMessage = {};
    $scope.selectAll = false;
    $scope.selectedMessages = [];

    $scope.$on('newMessageReceived', function () {
        $scope.getAllMessages();
    });

    $scope.getAllMessages = function () {
        return ConnectorAdmin.getAllMessages().then(
            function (data) {
                $scope.messages = data;
                $scope.getNewMessagesCount();
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getAllMessages();

    $scope.readMessage = function (uuid, index) {
        return ConnectorAdmin.readMessage(uuid).then(
            function (data) {
                $scope.messages[index] = data;
                $scope.getNewMessagesCount();
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.readMessages = function () {
        if($scope.selectAll){
            $scope.readAllMessages();
        }else {
            return ConnectorAdmin.readMessages($scope.selectedMessages).then(
                function (data) {
                    $scope.selectedMessages = [];
                    $scope.selectAll = false;
                    $scope.getAllMessages();
                    $scope.getNewMessagesCount();
                    return data;
                },
                function (errResponse) {
                    console.error(JSON.stringify(errResponse));
                    return null;
                })
        }
    };

    $scope.readAllMessages = function () {
        return ConnectorAdmin.readAllMessages().then(
            function (data) {
                $scope.selectedMessages = [];
                $scope.selectAll = false;
                $scope.getAllMessages();
                $scope.getNewMessagesCount();
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.deleteCurrentMessage = function () {
        return ConnectorAdmin.deleteMessage($scope.currentMessage.message.uuid).then(
            function (data) {
                if(data){
                    $scope.messages.splice($scope.currentMessage.index, 1);
                    $scope.closeModal();
                }
                $scope.getNewMessagesCount();
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.deleteMessages = function () {
        if($scope.selectAll){
            $scope.deleteAllMessages();
        }else {
            return ConnectorAdmin.deleteMessages($scope.selectedMessages).then(
                function (data) {
                    if(data){
                        for(const id in $scope.selectedMessages){
                            $scope.messages.splice($scope.messages.map(m => m.uuid).indexOf($scope.selectedMessages[id]), 1);
                        }
                        $scope.selectedMessages = [];
                        $scope.selectAll = false;
                    }
                    $scope.getNewMessagesCount();
                    return data;
                },
                function (errResponse) {
                    console.error(JSON.stringify(errResponse));
                    return null;
                })
        }
    };

    $scope.deleteAllMessages = function () {
        return ConnectorAdmin.deleteAllMessages().then(
            function (data) {
                if(data){
                    $scope.selectedMessages = [];
                    $scope.selectAll = false;
                    $scope.messages = [];
                }
                $scope.getNewMessagesCount();
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.openModal = function (index) {
        if($scope.messages[index].status === "NEW"){
            $scope.readMessage($scope.messages[index].uuid, index);
        }
        $scope.currentMessage.message = $scope.messages[index];
        $scope.currentMessage.index = index;
        $scope.modalOpen = true;
    };
    $scope.closeModal = function () {
        $scope.modalOpen = false;
        $scope.currentMessage = {};
    };
    $scope.sync = function (message, value) {
        if(value){
            $scope.selectedMessages.push(message.uuid)
        }else {
            $scope.selectedMessages = $scope.selectedMessages.filter(e => e !== message.uuid)
        }
    };
    $scope.setSelectAll = function (value) {
        if(value){
            for(const m in $scope.messages){
                if($scope.messages.hasOwnProperty(m) && !$scope.selectedMessages.includes($scope.messages[m].uuid)) {
                    $scope.selectedMessages.push($scope.messages[m].uuid)
                }
            }
        }else {
            $scope.selectedMessages = []
        }
    }
});

app.controller('BlocksController', function($scope, $location, ConnectorAdmin){
    $scope.viewData.header = "Blocks";
    $scope.viewData.icon = "fa-columns";
    $scope.blockTypes = ["AboutBlock", "ContactBlock", "ExpertiseBlock", "SkillsBlock", "TimelineBlock"];
    $scope.blocks = {};
    $scope.modalOpen = false;

    $scope.getAllBlocks = function () {
        return ConnectorAdmin.getAllBlocks().then(
            function (data) {
                $scope.blocks = data;
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getAllBlocks();

    $scope.editBlock = function (id) {
        $location.path('/editBlock/' + id)
    };
    $scope.addBlock = function (classType) {
        $location.path('/addBlock/' + classType);
        $scope.closeModal();
    };

    $scope.deleteBlock = function (id) {
        return ConnectorAdmin.deleteBlock(id).then(
            function (data) {
                if(data){
                    delete $scope.blocks[id];
                }
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.closeModal = function () {
        $scope.modalOpen = false;
    };
});

app.controller('NewEditBlockController', function($scope, $location, $routeParams, ConnectorAdmin){
    $scope.blockId = $routeParams.id;
    $scope.classType = $routeParams.classType;
    $scope.viewData.icon = "fa-columns";
    $scope.block = {};
    $scope.getBlock = function () {
        return ConnectorAdmin.getBlock($scope.blockId).then(
            function (block) {
                $scope.block = block;
                $scope.setTemplate(block.classType, false);
                if(block.classType === "me.shenderov.website.dao.TimelineBlock"){
                    $scope.block.elements.forEach(function(element, i) {
                        element.startDate = new Date(element.startDate);
                        if(element.endDate){
                            element.endDate = new Date(element.endDate);
                        }
                    });
                }
                return block;
            },
            function (errResponse) {
                M.toast({html: 'Failed to fetch data', classes: 'error-toast'});
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.newBlock = function (classType) {
        $scope.setTemplate("me.shenderov.website.dao." + classType, true);
        $scope.block.classType = "me.shenderov.website.dao." + classType;
    };

    $scope.setTemplate = function(classType, isNew) {
        switch (classType) {
            case "me.shenderov.website.dao.AboutBlock":
                $scope.template = "pages/blocks/about.html";
                if(isNew){
                    $scope.block.contacts = [];
                    $scope.block.mediaLinks = [];
                }
                break;
            case "me.shenderov.website.dao.ContactBlock":
                $scope.template = "pages/blocks/contact.html";
                if(isNew){
                    $scope.block.formLabels = [];
                }
                break;
            case "me.shenderov.website.dao.ExpertiseBlock":
                $scope.template = "pages/blocks/expertise.html";
                if(isNew){
                    $scope.block.blocks = [];
                }
                break;
            case "me.shenderov.website.dao.SkillsBlock":
                $scope.template = "pages/blocks/skills.html";
                if(isNew){
                    $scope.block.skills = [];
                }
                break;
            case "me.shenderov.website.dao.TimelineBlock":
                $scope.template = "pages/blocks/timeline.html";
                if(isNew){
                    $scope.block.elements = [];
                }
                break;
            default:
                console.log("Not existing classType");
                break;
        }
    };

    if($scope.blockId){
        $scope.viewData.header = "Edit Block [id: " + $scope.blockId + "]";
        $scope.getBlock();
    } else if($scope.classType){
        $scope.viewData.header = "Add Block";
        $scope.newBlock($scope.classType);
    }

    $scope.formatDate = function (date) {
        return date.substring(0, 10)
    };

    $scope.deletePositionedArrayRow = function(array, index){
        array.splice(index, 1);
        $scope.sortPositionedArray(array);
    };

    $scope.sortPositionedArray = function(array){
        if(array){
            array.sort();
            array.forEach(function(element, i) {
                element.position = i+1;
            });
        }
    };

    $scope.setNoEndDate = function (e) {
        if(e.noEndDate){
            e.endDate = "";
        }
    };

    $scope.moveUp = function(array, index){
        if(index >= 1 && index <= array.length){
            let tmp;
            tmp = array[index];
            array[index] = array[index-1];
            array[index-1] = tmp;
            $scope.sortPositionedArray(array);
        }
    };

    $scope.moveDown = function(array, index){
        if(index < array.length-1){
            let tmp;
            tmp = array[index];
            array[index] = array[index+1];
            array[index+1] = tmp;
            $scope.sortPositionedArray(array);
        }
    };

    $scope.updateBlock = function (block) {
        return ConnectorAdmin.updateBlock(block).then(
            function (data) {
                $location.path('/blocks');
                $scope.block = {};
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.addNewBlock = function (block) {
        return ConnectorAdmin.addBlock(block).then(
            function (data) {
                $location.path('/blocks');
                $scope.block = {};
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.cancel = function () {
        $location.path('/blocks');
        $scope.block = {};
    };

    $scope.save = function (block) {
        if($scope.blockId){
            $scope.updateBlock(block)
        } else if($scope.classType){
            $scope.addNewBlock(block);
        }
    };
});

app.controller('SeoController', function($scope, ConnectorAdmin){
    $scope.viewData.header = "Seo";
    $scope.viewData.icon = "fa-search";
    $scope.seo = {};

    $scope.getSeoData = function () {
        return ConnectorAdmin.getSeoData().then(
            function (data) {
                $scope.seo = data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getSeoData();

    // $scope.addMetadata = function () {
    // };
    //
    // $scope.setMetadataContent = function (name, content) {
    //     $scope.metaData[name] = content;
    // };
    //
    // $scope.setMetadataName = function (name, content) {
    //     //delete $scope.metaData[name];
    //     $scope.metaData[name] = content;
    // };
    //
    // $scope.deleteMetadata = function (name) {
    //     delete $scope.metaData[name];
    // };

    $scope.save = function (seo) {
        return ConnectorAdmin.updateSeoInfo(seo).then(
            function (data) {
                $scope.seo = data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
});

app.controller('SettingsController', function($scope, ConnectorAdmin){
    $scope.viewData.header = "Settings";
    $scope.viewData.icon = "fa-cog";
    $scope.settings = {};

    $scope.getSettings = function (id) {
        return ConnectorAdmin.getSettings(id).then(
            function (data) {
                $scope.settings = data;

                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getSettings("application");

    $scope.save = function (settings) {
        return ConnectorAdmin.updateSettings(settings).then(
            function (data) {
                $scope.settings = data;
                return data;
            },
            function (errResponse) {
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

});

app.filter('secondsToDateTime', function() {
    return function(seconds) {
        var d = new Date(0,0,0,0,0,0,0);
        d.setSeconds(seconds);
        return d;
    };
});
// app.filter('notInArray', function($filter) {
//     return function(list, arrayFilter) {
//         if(arrayFilter) {
//             return $filter("filter")(list, function(listItem) {
//                 return arrayFilter.indexOf(listItem) === -1;
//             });
//         }
//     };
// });