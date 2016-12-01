var _validImageExtensions = ["jpg", "jpeg", "bmp", "gif", "png"];

function updateProfilePictureURl(input, toastr){
		
    	if (input.files && input.files[0]) {
            var reader = new FileReader();
            var pic_real_width, pic_real_height;
            var file = input.files[0];
            reader.onload = function (e) {
            	if(validateImage(file.name)){
	            	$('#pictureToCrop').attr('src', e.target.result).load(function() {
	                    pic_real_width = this.width;   
	                    pic_real_height = this.height; 
	                    if(parseInt(pic_real_width) < 1200){
	                    	if(parseInt(pic_real_width) < 600) pic_real_width = 600; 
		                    $('#profilePictureModel .modal-dialog').css("width",parseInt(parseInt(pic_real_width) + parseInt(40)) + "px");
		                	$('#profilePictureModel .modal-dialog').css("margin-left","-" + parseInt(parseInt(parseInt(pic_real_width)/2) + parseInt(20)) + "px");
		                	$('#profilePictureModel .modal-dialog').css("left", "50%");
	                    }else{
	                    	$('#profilePictureModel .modal-dialog').css("width","auto");
	                    }
	                	selection.update();
	                });
            	}else{
            		// Show a error messages.
                    toastr.options.closeButton = true;
                    toastr.error("Sorry, The Image type is invalid, allowed extensions are: " + _validImageExtensions.join(", "));
            	}
            }
            
            reader.readAsDataURL(input.files[0]);
        }
	}

function getFileExt(input) {
	 var files = input[0].files;
	 var ext = null;
	 if(files[0] != null){
	  var filename = 
	      files[0].name.replace(/\\/g, '/').replace(/.*\//, '');
	  ext = filename.replace(/^.*\./, '').toLowerCase();
	 } 
	 return ext;
}

function getFileNameExt(filename) {
	 var ext = null;
	 if(filename != null){
	  var filename = 
		  filename.replace(/\\/g, '/').replace(/.*\//, '');
	  ext = filename.replace(/^.*\./, '').toLowerCase();
	 } 
	 return ext;
}

function validateImage(filename){
	var ext = getFileNameExt(filename);
	if(jQuery.inArray(ext, _validImageExtensions) !== -1){
		return true;
	}else{
		return false;
	}
}

function formateDate(cDate){
	if(cDate){
		var d = new Date(cDate),
	    date = [
	        d.getDate(),
	        d.getMonth() + 1,
	        d.getFullYear()
	    ];
	    
	    time =[
	    	d.getHours(),
	    	d.getMinutes(),
	    	d.getSeconds()
	    ];
	    
	    return date.join('/')+' at '+time.join(':');
	}else{
		return '';
	}
}

function checkFileSize(max_file_size) {
	var file_list = $('input[type=file]').get(0).files;
	for (var i = 0, file; file = file_list[i]; i++) {
		var fileExtension = file.name.split('.')[file.name.split('.').length - 1].toLowerCase();
		var iConvert = (file.size / 1024).toFixed(2);
		var post_file_size = (file.size / (1024*1024)).toFixed(1);
		if(post_file_size > max_file_size){
			return false;
		}else{
			return true;
		}
	}
}

function getCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

function showErrorToastr(error, toastr) {
	
	if(error.hasOwnProperty('responseJSON')){
    	var responseJSON = error.responseJSON;
    	if(responseJSON.hasOwnProperty('message')){
    		toastr.options.closeButton = true;
            toastr.error(error.responseJSON.message);
    	}else{
    		toastr.options.closeButton = true;
            toastr.error(polyglot.t('error'));
    	}
    }else{
    	toastr.options.closeButton = true;
        toastr.error(polyglot.t('error'));
    }
}

/**
 * Overwrites obj1's values with obj2's and adds obj2's if non existent in obj1
 * @param obj1
 * @param obj2
 * @returns obj3 a new object based on obj1 and obj2
 */
function merge_options(obj1,obj2){
    var obj3 = {};
    for (var attrname in obj1) { obj3[attrname] = obj1[attrname]; }
    for (var attrname in obj2) { obj3[attrname] = obj2[attrname]; }
    return obj3;
}

function searchItinerary(itineraryId, objArr){
    for (var i=0; i < objArr.length; i++) {
        if (objArr[i].itinerary.id === itineraryId) {
            return objArr[i];
        }
    }
}

function cloneRecord(record) {
    var clone ={};
    for( var key in record ){
        if(record.hasOwnProperty(key)) //ensure not adding inherited props
            clone[key]=record[key];
    }
    return clone;
}

function removeMatchedSeatObject(currentSelection, seatId) {
	for(var i = 0; i < currentSelection.length; i++) {
		// splice the seat selection record if its present, comparing only value not type, 
		// May be string or integer so used "==" comparison not "==="
	    if(currentSelection[i].seat == seatId) {
	    	currentSelection.splice(i, 1);
	        break;
	    }
	}
	return currentSelection;
}