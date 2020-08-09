var app = angular.module('cvApp', ['ngAnimate']);

app.controller('General', function($scope, Connector){
    $scope.loader = true;
    $scope.seoData = {};
    $scope.block_ids = ["about", "expertise", "skills", "languages", "experience", "education", "contact"];
    $scope.blocks = {};
    $scope.about = $scope.blocks.about;
    $scope.expertise = $scope.blocks.expertise;
    $scope.skills = $scope.blocks.skills;
    $scope.languages = $scope.blocks.languages;
    $scope.experience = $scope.blocks.experience;
    $scope.education = $scope.blocks.education;
    $scope.contact = $scope.blocks.contact;
    $scope.messageWrapper = {};
    $scope.messageWrapper.disableSendButton = false;
    $scope.enableRecaptcha = config.enableRecaptcha;
    $scope.recaptchaSiteKey = config.recaptchaSiteKey;
    $scope.recaptchaResult = false;

    $scope.getSeoData = function () {
        return Connector.getSeoData().then(
            function (data) {
                $scope.seoData = data;
                return data;
            },
            function (errResponse) {
                M.toast({html: 'Failed to fetch data', classes: 'error-toast'});
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getSeoData();

    $scope.getBlock = function (block_id) {
        return Connector.getBlock(block_id).then(
            function (block) {
                return block;
            },
            function (errResponse) {
                M.toast({html: 'Failed to fetch data', classes: 'error-toast'});
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };

    $scope.getBlocks = function (block_ids) {
        return Connector.getBlocks(block_ids).then(
            function (blocks) {
                $scope.blocks = blocks;
                $scope.chunkedExpertiseItems = chunkGenericElements($scope.blocks.expertise.blocks, 3);
                $scope.chunkedSkillsItems = chunkGenericElements($scope.blocks.skills.skills, Math.round($scope.blocks.skills.skills.length/2));
                $scope.chunkedLanguagesItems = chunkGenericElements($scope.blocks.languages.skills, 3);
                $scope.chunkedExperienceItems = $scope.blocks.experience.elements.sort(function(a,b){return new Date(b.startDate) - new Date(a.startDate)});
                $scope.loader = false;
                return blocks;
            },
            function (errResponse) {
                M.toast({html: 'Failed to fetch data', classes: 'error-toast'});
                console.error(JSON.stringify(errResponse));
                return null;
            })
    };
    $scope.getBlocks($scope.block_ids);

    $scope.sendMessage = function (message) {
        $scope.messageWrapper.disableSendButton = true;
        $scope.recaptchaResponse = $scope.enableRecaptcha ? grecaptcha.getResponse() : null;
        return Connector.sendMessage(message, $scope.recaptchaResponse).then(
            function (message) {
                M.toast({html: 'Message Successfully Sent', classes: 'success-toast'});
                $scope.messageWrapper.message = {};
                $scope.messageWrapper.disableSendButton = false;
                $('#name, #email, #phone, #message').val('').removeClass('valid');
                M.updateTextFields();
                $scope.contactForm.$setUntouched();
                $scope.contactForm.$setPristine();
                grecaptcha.reset();
                $scope.recaptchaResult = false;
                return message;
            },
            function (errResponse) {
                $scope.messageWrapper.disableSendButton = false;
                M.toast({html: 'Message Can Not Be Sent', classes: 'error-toast'});
                console.error(JSON.stringify(errResponse));
                grecaptcha.reset();
                $scope.recaptchaResult = false;
                return null;
            })
    };

    $scope.setRecaptchaResult = function (result) {
        $scope.recaptchaResult = result;
    };

    function chunkGenericElements(elements, chunkSize) {
        elements.sort(function(a, b){return a.position - b.position});
        var newArr = [];
        for (var i=0; i<elements.length; i+=chunkSize) {
            newArr.push(elements.slice(i, i+chunkSize));
        }
        return newArr;
    }

    $scope.getDateString = function (date, isYearOnly) {
        var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        var res;
        var d = date != null ? new Date(date.substring(0, date.indexOf("T"))) : null;
        if(date == null){
            res = "Present";
        }else if(isYearOnly){
            res = d.getFullYear();
        }else {
            res = monthNames[d.getMonth()] + " " + d.getFullYear();
        }
        return res;
    };

});

app.config( ['$compileProvider', function( $compileProvider){
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|mailto|skype):/);
}]);

var recaptcha_success = function() {
    var scope = angular.element(
        document.getElementById("contact")).scope();
    scope.$apply(function () {
        scope.setRecaptchaResult(true);
    });
};

var recaptcha_fail = function() {
    var scope = angular.element(
        document.getElementById("contact")).scope();
    scope.$apply(function () {
        scope.setRecaptchaResult(false);
    });
};