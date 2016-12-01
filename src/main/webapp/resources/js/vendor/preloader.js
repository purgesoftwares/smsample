/*!
 * preloader
 *
 * Copyright 2014-2015 SDP
 * Released under the MIT license
 */
(function () {
	
	var cSpeed=15;
	var cWidth=66;
	var cHeight=66;
	var cTotalFrames=8;
	var cFrameWidth=66;
	var cImageSrc='../resources/images/sprites.png';
	
	var cImageTimeout=false;
	var cIndex=0;
	var cXpos=0;
	var cPreloaderTimeout=false;
	var SECONDS_BETWEEN_FRAMES=0;
	
    preloader = {};
    
    function identity(v) {
        return v;
    }
    function toBool(v) {
        return !!v;
    }
    function notId(v) {
        return !v;
    }
    
    // Establish the root object, `window` (`self`) in the browser, `global`
    // on the server, or `this` in some virtual machines. We use `self`
    // instead of `window` for `WebWorker` support.
    var root = typeof self === 'object' && self.self === self && self ||
            typeof global === 'object' && global.global === global && global ||
            this;
    
    preloader.startAnimation = function startAnimation(func){
    	
    	$('<div id="loader" ><div id="loaderImage"></div></div>').prependTo('body');
    	document.getElementById('loader').style.position = "fixed";
    	document.getElementById('loader').style.left = "0px";
    	document.getElementById('loader').style.top = "0px";
    	document.getElementById('loader').style.width='100%';
		document.getElementById('loader').style.height='100%';
    	document.getElementById('loader').style.zIndex = "9999";
    	document.getElementById('loader').style.background = "rgba(0, 0, 0, 0.6)";
    	
    	document.getElementById('loaderImage').style.left = "50%";
    	document.getElementById('loaderImage').style.top = "50%";
    	document.getElementById('loaderImage').style.margin = "-33px 0 0 -33px";

    	document.getElementById('loaderImage').style.position = "fixed";
		document.getElementById('loaderImage').style.background = 'url('+cImageSrc+') center no-repeat';
		document.getElementById('loaderImage').style.width= cWidth+'px';
		document.getElementById('loaderImage').style.height= cHeight+'px';
		
		//FPS = Math.round(100/(maxSpeed+2-speed));
		FPS = Math.round(100/cSpeed);
		SECONDS_BETWEEN_FRAMES = 1 / FPS;
		
		cPreloaderTimeout=setTimeout('preloader.continueAnimation()', SECONDS_BETWEEN_FRAMES/1000);
		
	}

    preloader.continueAnimation = function continueAnimation(){
		
		cXpos += cFrameWidth;
		//increase the index so we know which frame of our animation we are currently on
		cIndex += 1;
		 
		//if our cIndex is higher than our total number of frames, we're at the end and should restart
		if (cIndex >= cTotalFrames) {
			cXpos =0;
			cIndex=0;
		}
		
		if(document.getElementById('loaderImage'))
			document.getElementById('loaderImage').style.backgroundPosition=(-cXpos)+'px 0';
		
		cPreloaderTimeout=setTimeout('preloader.continueAnimation()', SECONDS_BETWEEN_FRAMES*1000);
	}
    
	function stopAnimation(){//stops animation
		clearTimeout(cPreloaderTimeout);
		$('#loader').remove();
		cPreloaderTimeout=false;
	}
    
    function imageLoader(s, fun)//Pre-loads the sprites image
	{
		clearTimeout(cImageTimeout);
		cImageTimeout=0;
		genImage = new Image();
		genImage.onload=function (){cImageTimeout=setTimeout(fun, 0)};
		genImage.onerror=new Function('alert(\'Could not load the loader image\')');
		genImage.src=s;
		
	}
    
    preloader.start = function start(func) {
        //The following code starts the animation
    	return new imageLoader(cImageSrc, 'preloader.startAnimation()');
    };
    
    preloader.stop = function stop() {
        //The following code starts the animation
    	return new stopAnimation();
    };

    // Node.js
    if (typeof module === 'object' && module.exports) {
        module.exports = preloader;
    }
    // AMD / RequireJS
    else if (typeof define === 'function' && define.amd) {
        define([], function () {
            return preloader;
        });
    }
    // included directly via <script> tag
    else {
        root.preloader = preloader;
    }

}());
