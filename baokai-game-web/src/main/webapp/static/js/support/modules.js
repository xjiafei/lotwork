var Modules = function () {
    var modules = {};
    var scope = {};
    var init = function (key, module) {
        if (checkIsFunc(module)) {
            logger.log('init:' + key);
            try {
                module(scope);
            } catch (e) {
                logger.log('error:' + key);
                logger.error(e);
            }
        }
    };
    var checkIsFunc = function (func) {
        return typeof func === 'function';
    };
    var logger = {
        log: function (msg) {
            try {
                console.log(msg);
            } catch (e) {
            }
        },
        error: function (e) {
            try {
                console.error(e);
            } catch (e) {
            }
        }
    };
    return {
        putModule: function (name, module) {
            if (checkIsFunc(module)) {
                modules[name] = module;
            }
        },
        initModule: function (name) {
            var module = modules[name];
            init(name, module);
        },
        initModules: function () {
            for (var key in modules) {
                var module = modules[key];
                init(key, module);
            }
        }
    };
};