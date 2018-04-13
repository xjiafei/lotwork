; (function () {
    'use strict';

    if (!Date.now) {
      Date.now = function () { return new Date().getTime(); };
    }

    var vendors = ['webkit', 'moz'];
    for (var i = 0; i < vendors.length && !window.requestAnimationFrame; ++i) {
        var vp = vendors[i];
        window.requestAnimationFrame = window[vp + 'RequestAnimationFrame'];
        window.cancelAnimationFrame = (window[vp + 'CancelAnimationFrame']
                                   || window[vp + 'CancelRequestAnimationFrame']);
    }

    function frames (callback, rate) {
        var now = Date.now(),
            gap = 1000/(rate ? rate : 60),
            nextTime = Math.max(lastTime + gap, now);
        return setTimeout(function () {
          callback(lastTime = nextTime);
        }, nextTime - now);
    };

    if (/iP(ad|hone|od).*OS 6/.test(window.navigator.userAgent) // iOS6 is buggy
        || !window.requestAnimationFrame || !window.cancelAnimationFrame) {
        var lastTime = 0;
        window.requestAnimationFrame = frames;
        window.cancelAnimationFrame = clearTimeout;
    }
    var frames = function(callback, rate){
      return (rate == 60|| !rate) ? window.requestAnimationFrame.apply(null, arguments) : frames.apply(null, arguments);
    }

    window.frames = frames;

}());
