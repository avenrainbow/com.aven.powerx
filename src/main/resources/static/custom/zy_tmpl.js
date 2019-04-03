var zy_tmpl_count = function(dd) {
    if (Object.prototype.toString.apply(dd) === "[object Array]") return dd.length;
    else {
        var c = 0;
        for (var i in dd) c++;
        return c;
    }
}
var _function = function(data, content, k1, k2, count) {
    var q = content.match(/(first:|last:)(\"|\'*)([^\"\']*)(\"|\'*)/);
    if (q) {
        if (q[1] == k1) {
            if (q[2] == '\"' || q[2] == '\'') return q[3];
            else return data[q[3]];
        } else if (q[1] == k2 && count > 1) return "";
    }

}
var template_function = function(template, data, index, count, callback) {
	if(template)
    return template.replace(/\#\{([^\}]*)\}/g,
    function(match, content) {
        if (content.match(/index:/)) return index;

        if (content.match(/cb:/) && callback) return callback(data, content.match(/cb:(.*)/));

        if (index == 0) {
            var s = _function(data, content, "first:", "last:", count);
            if (s) return s;
        }
        if (index == (count - 1)) {
            var s = _function(data, content, "last:", "first:", count);
            if (s) return s;
        }
        var array = content.split('.');
        
        var result = data;
        for (var key in array) if(!isNaN(key))result = result[array[key]];
        if(result=="0"){
        	return 0;
        }
        return result || "";
    });
}

var zy_tmpl = function(template, dd, callback) {
    var result = "";
    var index = 0;
    for (var i in dd) {
        result += template_function(template, dd[i], index, dd.length, callback);
        index++;
    }
    return result;
}

var zy_tmpl_r = function(template, dd, count, callback) {
    var result = "";
    var index = 0;
    for (var i in dd) {
        result = template_function(template, dd[i], index, count, callback) + result;
        index++;
    }
    return result;
}

var zy_tmpl_s = function(template, dd, callback) {
    return template_function(template, dd, -1, -1, callback);
}

//列表模式
var zy_tmpl_e = function(template, dd, col, callback) {
	if(col == null) col = 12;
	var hl = "<tr><td colspan='"+col+"' style='text-align:center;' class='text-warning'>暂无数据</td></tr>";
	var result = (dd && dd.length > 0) ? "" :  hl;
	var index = 0;
    for (var i in dd) {
        result += template_function(template, dd[i], index, dd.length, callback);
        index++;
    }
    return result;
}

//卡片模式
var zy_tmpl_c = function(template, dd, id, callback) {
    var result = "";
    if(dd && dd.length > 0){
    	var index = 0;
    	for (var i in dd) {
    		result += template_function(template, dd[i], index, dd.length, callback);
    		index++;
    	}
    }else{
    	result = $("#"+id).html();
    }
    return result;
}
