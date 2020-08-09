app.factory('ConnectorAdmin', ['$http', '$q', function ($http, $q) {
    var baseUrl = config.hostname + "/admin/";
    var publicBaseUrl = config.hostname + "/public/";
    var headers = {"Authorization": "Basic YWRtaW46cGFzc3dvcmQ="};
    return {
        getAllMessages: function () {
            return $http.get(baseUrl + 'getAllMessages', {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        getNewMessagesCount: function () {
            return $http.get(baseUrl + 'getNewMessagesCount', {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        readMessage: function (uuid) {
            var data = {
                uuid: uuid
            };
            var config = {
                params: data,
                headers : headers
            };
            return $http.get(baseUrl + 'readMessage', config)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        readMessages: function (uuids) {
            return $http.post(baseUrl + 'readMessages', uuids, {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        readAllMessages: function () {
            return $http.get(baseUrl + 'readAllMessages', {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        deleteMessage: function (uuid) {
            var data = {
                uuid: uuid
            };
            var config = {
                params: data,
                headers : headers
            };
            return $http.delete(baseUrl + 'deleteMessage', config)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        deleteMessages: function (uuids) {
            return $http.post(baseUrl + 'deleteMessages', uuids, {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        deleteAllMessages: function () {
            return $http.delete(baseUrl + 'deleteAllMessages', {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        getBlock: function (block_id) {
            return $http.get(publicBaseUrl + 'block', {params: {id: block_id}})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        getAllBlocks: function () {
            return $http.get(baseUrl + 'getAllBlocks', {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        updateBlock: function (block) {
            return $http.post(baseUrl + 'updateBlock', block, {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        addBlock: function (block) {
            return $http.post(baseUrl + 'addBlock', block, {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        deleteBlock: function (id) {
            var data = {
                id: id
            };
            var config = {
                params: data,
                headers : headers
            };
            return $http.delete(baseUrl + 'deleteBlock', config)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        getSeoData: function () {
            return $http.get(publicBaseUrl + 'seo')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        updateSeoInfo: function (seo) {
            return $http.post(baseUrl + 'updateSeoInfo', seo, {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        getAllUsers: function () {
            return $http.get(baseUrl + 'getAllUsers', {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        getAuthorities: function () {
            return $http.get(baseUrl + 'getAuthorities', {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        createUser: function (user) {
            return $http.post(baseUrl + 'addUser', user, {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        updateUser: function (user) {
            return $http.post(baseUrl + 'updateUser', user, {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        getUserDetails: function (username) {
            var data = {
                username: username
            };
            var config = {
                params: data,
                headers : headers
            };
            return $http.get(baseUrl + 'getUserDetails', config)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        deleteUser: function (username) {
            var data = {
                username: username
            };
            var config = {
                params: data,
                headers : headers
            };
            return $http.delete(baseUrl + 'deleteUser', config)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        changePassword: function (wrapper) {
            return $http.post(baseUrl + 'changeUserPassword', wrapper, {headers: headers})
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },




        getBlocks: function (blocks_id) {
            return $http.post(baseUrl + 'blocks', blocks_id)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },
        sendMessage: function (message, recaptcha) {
            var params = recaptcha === null ? {} : {params: {'g-recaptcha-response': recaptcha}};
            return $http.post(baseUrl + 'sendMessage', message, params)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse.data);
                    }
                );
        },


    };
}]);