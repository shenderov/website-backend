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

app.controller('MainController', function($scope, $interval, ConnectorAdmin){
    $scope.viewData = {};
    $scope.newMessagesCount = 0;
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

app.controller('DashboardController', function($scope){
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
        "cpuModel": "Dual-Core Intel Core i5",
        "cpuNumberOfCores": 4,
        "cpuSpeed": 2700,
        "cpuUsage": 34.5
    };
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

app.controller('SettingsController', function($scope){
    $scope.viewData.header = "Settings";
    $scope.viewData.icon = "fa-cog";
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