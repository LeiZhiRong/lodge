function time(){return Math.floor(new Date().getTime()/1000)};//10位时间戳
function date(format,timestamp){var that=this;var jsdate,f;var txt_words=["Sun","Mon","Tues","Wednes","Thurs","Fri","Satur","January","February","March","April","May","June","July","August","September","October","November","December"];var formatChr=/\\?(.?)/gi;var formatChrCb=function(t,s){return f[t]?f[t]():s};var _pad=function(n,c){n=String(n);while(n.length<c){n="0"+n}return n};f={d:function(){return _pad(f.j(),2)},D:function(){return f.l().slice(0,3)},j:function(){return jsdate.getDate()},l:function(){return txt_words[f.w()]+"day"},N:function(){return f.w()||7},S:function(){var j=f.j();var i=j%10;if(i<=3&&parseInt((j%100)/10,10)==1){i=0}return["st","nd","rd"][i-1]||"th"},w:function(){return jsdate.getDay()},z:function(){var a=new Date(f.Y(),f.n()-1,f.j());var b=new Date(f.Y(),0,1);return Math.round((a-b)/86400000)},W:function(){var a=new Date(f.Y(),f.n()-1,f.j()-f.N()+3);var b=new Date(a.getFullYear(),0,4);return _pad(1+Math.round((a-b)/86400000/7),2)},F:function(){return txt_words[6+f.n()]},m:function(){return _pad(f.n(),2)},M:function(){return f.F().slice(0,3)},n:function(){return jsdate.getMonth()+1},t:function(){return(new Date(f.Y(),f.n(),0)).getDate()},L:function(){var j=f.Y();return j%4===0&j%100!==0|j%400===0},o:function(){var n=f.n();var W=f.W();var Y=f.Y();return Y+(n===12&&W<9?1:n===1&&W>9?-1:0)},Y:function(){return jsdate.getFullYear()},y:function(){return f.Y().toString().slice(-2)},a:function(){return jsdate.getHours()>11?"pm":"am"},A:function(){return f.a().toUpperCase()},B:function(){var H=jsdate.getUTCHours()*3600;var i=jsdate.getUTCMinutes()*60;var s=jsdate.getUTCSeconds();return _pad(Math.floor((H+i+s+3600)/86.4)%1000,3)},g:function(){return f.G()%12||12},G:function(){return jsdate.getHours()},h:function(){return _pad(f.g(),2)},H:function(){return _pad(f.G(),2)},i:function(){return _pad(jsdate.getMinutes(),2)},s:function(){return _pad(jsdate.getSeconds(),2)},u:function(){return _pad(jsdate.getMilliseconds()*1000,6)},e:function(){throw"Not supported (see source code of date() for timezone on how to add support)"},I:function(){var a=new Date(f.Y(),0);var c=Date.UTC(f.Y(),0);var b=new Date(f.Y(),6);var d=Date.UTC(f.Y(),6);return((a-c)!==(b-d))?1:0},O:function(){var tzo=jsdate.getTimezoneOffset();var a=Math.abs(tzo);return(tzo>0?"-":"+")+_pad(Math.floor(a/60)*100+a%60,4)},P:function(){var O=f.O();return(O.substr(0,3)+":"+O.substr(3,2))},T:function(){return"UTC"},Z:function(){return -jsdate.getTimezoneOffset()*60},c:function(){return"Y-m-d\\TH:i:sP".replace(formatChr,formatChrCb)},r:function(){return"D, d M Y H:i:s O".replace(formatChr,formatChrCb)},U:function(){return jsdate/1000|0}};this.date=function(format,timestamp){that=this;jsdate=(timestamp===undefined?new Date():(timestamp instanceof Date)?new Date(timestamp):new Date(timestamp*1000));return format.replace(formatChr,formatChrCb)};return this.date(format,timestamp)};
function microtime(get_as_float){if(typeof performance!=="undefined"&&performance.now){var now=(performance.now()+performance.timing.navigationStart)/1000;if(get_as_float){return now}var s=now|0;return(Math.round((now-s)*1000000)/1000000)+" "+s}else{var now=(Date.now?Date.now():new Date().getTime())/1000;if(get_as_float){return now}var s=now|0;return(Math.round((now-s)*1000)/1000)+" "+s}};
function strtotime(text,now){var parsed,match,today,year,date,days,ranges,len,times,regex,i,fail=false;if(!text){return fail}text=text.replace(/^\s+|\s+$/g,"").replace(/\s{2,}/g," ").replace(/[\t\r\n]/g,"").toLowerCase();match=text.match(/^(\d{1,4})([\-\.\/\:])(\d{1,2})([\-\.\/\:])(\d{1,4})(?:\s(\d{1,2}):(\d{2})?:?(\d{2})?)?(?:\s([A-Z]+)?)?$/);if(match&&match[2]===match[4]){if(match[1]>1901){switch(match[2]){case"-":if(match[3]>12||match[5]>31){return fail}return new Date(match[1],parseInt(match[3],10)-1,match[5],match[6]||0,match[7]||0,match[8]||0,match[9]||0)/1000;case".":return fail;case"/":if(match[3]>12||match[5]>31){return fail}return new Date(match[1],parseInt(match[3],10)-1,match[5],match[6]||0,match[7]||0,match[8]||0,match[9]||0)/1000}}else{if(match[5]>1901){switch(match[2]){case"-":if(match[3]>12||match[1]>31){return fail}return new Date(match[5],parseInt(match[3],10)-1,match[1],match[6]||0,match[7]||0,match[8]||0,match[9]||0)/1000;case".":if(match[3]>12||match[1]>31){return fail}return new Date(match[5],parseInt(match[3],10)-1,match[1],match[6]||0,match[7]||0,match[8]||0,match[9]||0)/1000;case"/":if(match[1]>12||match[3]>31){return fail}return new Date(match[5],parseInt(match[1],10)-1,match[3],match[6]||0,match[7]||0,match[8]||0,match[9]||0)/1000}}else{switch(match[2]){case"-":if(match[3]>12||match[5]>31||(match[1]<70&&match[1]>38)){return fail}year=match[1]>=0&&match[1]<=38?+match[1]+2000:match[1];return new Date(year,parseInt(match[3],10)-1,match[5],match[6]||0,match[7]||0,match[8]||0,match[9]||0)/1000;case".":if(match[5]>=70){if(match[3]>12||match[1]>31){return fail}return new Date(match[5],parseInt(match[3],10)-1,match[1],match[6]||0,match[7]||0,match[8]||0,match[9]||0)/1000}if(match[5]<60&&!match[6]){if(match[1]>23||match[3]>59){return fail}today=new Date();return new Date(today.getFullYear(),today.getMonth(),today.getDate(),match[1]||0,match[3]||0,match[5]||0,match[9]||0)/1000}return fail;case"/":if(match[1]>12||match[3]>31||(match[5]<70&&match[5]>38)){return fail}year=match[5]>=0&&match[5]<=38?+match[5]+2000:match[5];return new Date(year,parseInt(match[1],10)-1,match[3],match[6]||0,match[7]||0,match[8]||0,match[9]||0)/1000;case":":if(match[1]>23||match[3]>59||match[5]>59){return fail}today=new Date();return new Date(today.getFullYear(),today.getMonth(),today.getDate(),match[1]||0,match[3]||0,match[5]||0)/1000}}}}if(text==="now"){return now===null||isNaN(now)?new Date().getTime()/1000|0:now|0}if(!isNaN(parsed=Date.parse(text))){return parsed/1000|0}date=now?new Date(now*1000):new Date();days={"sun":0,"mon":1,"tue":2,"wed":3,"thu":4,"fri":5,"sat":6};ranges={"yea":"FullYear","mon":"Month","day":"Date","hou":"Hours","min":"Minutes","sec":"Seconds"};function lastNext(type,range,modifier){var diff,day=days[range];if(typeof day!=="undefined"){diff=day-date.getDay();if(diff===0){diff=7*modifier}else{if(diff>0&&type==="last"){diff-=7}else{if(diff<0&&type==="next"){diff+=7}}}date.setDate(date.getDate()+diff)}}function process(val){var splt=val.split(" "),type=splt[0],range=splt[1].substring(0,3),typeIsNumber=/\d+/.test(type),ago=splt[2]==="ago",num=(type==="last"?-1:1)*(ago?-1:1);if(typeIsNumber){num*=parseInt(type,10)}if(ranges.hasOwnProperty(range)&&!splt[1].match(/^mon(day|\.)?$/i)){return date["set"+ranges[range]](date["get"+ranges[range]]()+num)}if(range==="wee"){return date.setDate(date.getDate()+(num*7))}if(type==="next"||type==="last"){lastNext(type,range,num)}else{if(!typeIsNumber){return false}}return true}times="(years?|months?|weeks?|days?|hours?|minutes?|min|seconds?|sec"+"|sunday|sun\\.?|monday|mon\\.?|tuesday|tue\\.?|wednesday|wed\\.?"+"|thursday|thu\\.?|friday|fri\\.?|saturday|sat\\.?)";regex="([+-]?\\d+\\s"+times+"|"+"(last|next)\\s"+times+")(\\sago)?";match=text.match(new RegExp(regex,"gi"));if(!match){return fail}for(i=0,len=match.length;i<len;i++){if(!process(match[i])){return fail}}return(date.getTime()/1000)};
/*html编码函数和解码函数,转换成实体和还原实体,同名php函数*/
function htmlspecialchars_decode(e,E){var T=0,_=0,r=!1;"undefined"==typeof E&&(E=2),e=e.toString().replace(/&lt;/g,"<").replace(/&gt;/g,">");var t={ENT_NOQUOTES:0,ENT_HTML_QUOTE_SINGLE:1,ENT_HTML_QUOTE_DOUBLE:2,ENT_COMPAT:2,ENT_QUOTES:3,ENT_IGNORE:4};if(0===E&&(r=!0),"number"!=typeof E){for(E=[].concat(E),_=0;_<E.length;_++)0===t[E[_]]?r=!0:t[E[_]]&&(T|=t[E[_]]);E=T}return E&t.ENT_HTML_QUOTE_SINGLE&&(e=e.replace(/&#0*39;/g,"'")),r||(e=e.replace(/&quot;/g,'"')),e=e.replace(/&amp;/g,"&")}
function htmlspecialchars(e,E,T,_){var r=0,t=0,a=!1;("undefined"==typeof E||null===E)&&(E=2),e=e.toString(),_!==!1&&(e=e.replace(/&/g,"&amp;")),e=e.replace(/</g,"&lt;").replace(/>/g,"&gt;");var N={ENT_NOQUOTES:0,ENT_HTML_QUOTE_SINGLE:1,ENT_HTML_QUOTE_DOUBLE:2,ENT_COMPAT:2,ENT_QUOTES:3,ENT_IGNORE:4};if(0===E&&(a=!0),"number"!=typeof E){for(E=[].concat(E),t=0;t<E.length;t++)0===N[E[t]]?a=!0:N[E[t]]&&(r|=N[E[t]]);E=r}return E&N.ENT_HTML_QUOTE_SINGLE&&(e=e.replace(/'/g,"&#039;")),a||(e=e.replace(/"/g,"&quot;")),e};
var htmlencode=function(sStr){return htmlspecialchars(sStr);};
var htmldecode=function(sStr){return htmlspecialchars_decode(sStr)};

/*浏览器判断和网络判断*/
var browser={version:function(){var u=navigator.userAgent.toLowerCase(),app=navigator.appVersion;return{ie:u.indexOf("trident")>-1,opera:u.indexOf("tresto")>-1,webKit:u.indexOf("applewebkit")>-1,firefox:u.indexOf("gecko")>-1&&u.indexOf("khtml")==-1,mobile:!!u.match(/applewebkit.*mobile.*/),ios:!!u.match(/\(i[^;]+;( u;)? cpu.+mac os x/),android:u.indexOf("android")>-1||u.indexOf("linux")>-1,iphone:u.indexOf("iphone")>-1,ipad:u.indexOf("ipad")>-1,weixin:u.match(/micromessenger/i)=="micromessenger"}}(),language:(navigator.browserLanguage||navigator.language).toLowerCase(),wifi:!function(t){var e=!0,n=t.navigator.userAgent,i=t.navigator.connection;if(/MicroMessenger/.test(n))if(/NetType/.test(n)){var o=n.match(/NetType\/(\S)+/)[0].replace("NetType/","");o&&"WIFI"!=o&&(e=!1)}else document.addEventListener("WeixinJSBridgeReady",function(){WeixinJSBridge.invoke("getNetworkType",{},function(t){"network_type:wifi"!=t.err_msg&&(e=!1)})});else if(i){var a=i.type;"wifi"!=a&&"2"!=a&&"unknown"!=a&&(e=!1)}t.wifi=e}(window)};

/*变量处理*/
function isset(){var a=arguments,l=a.length,i=0,undef;if(l===0){throw new Error("Empty isset")}while(i!==l){if(a[i]===undef||a[i]===null){return false}i++}return true};
function empty(mixed_var){var undef,key,i,len;var emptyValues=[undef,null,false,0,"","0"];for(i=0,len=emptyValues.length;i<len;i++){if(mixed_var===emptyValues[i]){return true}}if(typeof mixed_var==="object"){for(key in mixed_var){return false}return true}return false};
function intval(mixed_var,base){var tmp;var type=typeof mixed_var;if(type==="boolean"){return +mixed_var}else{if(type==="string"){tmp=parseInt(mixed_var,base||10);return(isNaN(tmp)||!isFinite(tmp))?0:tmp}else{if(type==="number"&&isFinite(mixed_var)){return mixed_var|0}else{return 0}}}};
function function_exists(func_name){if(typeof func_name==="string"){func_name=this.window[func_name]}return typeof func_name==="function"};
function in_array(needle,haystack,argStrict){var key="",strict=!!argStrict;if(strict){for(key in haystack){if(haystack[key]===needle){return true}}}else{for(key in haystack){if(haystack[key]==needle){return true}}}return false};
function range(low,high,step){var matrix=[];var inival,endval,plus;var walker=step||1;var chars=false;if(!isNaN(low)&&!isNaN(high)){inival=low;endval=high}else{if(isNaN(low)&&isNaN(high)){chars=true;inival=low.charCodeAt(0);endval=high.charCodeAt(0)}else{inival=(isNaN(low)?0:low);endval=(isNaN(high)?0:high)}}plus=((inival>endval)?false:true);if(plus){while(inival<=endval){matrix.push(((chars)?String.fromCharCode(inival):inival));inival+=walker}}else{while(inival>=endval){matrix.push(((chars)?String.fromCharCode(inival):inival));inival-=walker}}return matrix};
function strip_tags(input,allowed){allowed=(((allowed||"")+"").toLowerCase().match(/<[a-z][a-z0-9]*>/g)||[]).join("");var tags=/<\/?([a-z][a-z0-9]*)\b[^>]*>/gi,commentsAndPhpTags=/<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;return input.replace(commentsAndPhpTags,"").replace(tags,function($0,$1){return allowed.indexOf("<"+$1.toLowerCase()+">")>-1?$0:""})};
function rand(min,max){var argc=arguments.length;if(argc===0){min=0;max=2147483647}else{if(argc===1){throw new Error("Warning: rand() expects exactly 2 parameters, 1 given")}}return Math.floor(Math.random()*(max-min+1))+min};
function round(value,precision,mode){var m,f,isHalf,sgn;precision|=0;m=Math.pow(10,precision);value*=m;sgn=(value>0)|-(value<0);isHalf=value%1===0.5*sgn;f=Math.floor(value);if(isHalf){switch(mode){case"PHP_ROUND_HALF_DOWN":value=f+(sgn<0);break;case"PHP_ROUND_HALF_EVEN":value=f+(f%2*sgn);break;case"PHP_ROUND_HALF_ODD":value=f+!(f%2);break;default:value=f+(sgn>0)}}return(isHalf?value:Math.round(value))/m};
function strtolower(str){return(str+"").toLowerCase()};
function strtoupper(str){return(str+"").toUpperCase()};
function floatval(mixed_var){return(parseFloat(mixed_var)||0)};
function ucfirst(str){str+="";var f=str.charAt(0).toUpperCase();return f+str.substr(1)};
function base_convert(number,frombase,tobase){return parseInt(number+"",frombase|0).toString(tobase|0)};
function floor(value){return Math.floor(value)};
function ceil(value){return Math.ceil(value)}; 

function utf8_encode(argString){if(argString===null||typeof argString==="undefined"){return""}var string=(argString+"");var utftext="",start,end,stringl=0;start=end=0;stringl=string.length;for(var n=0;n<stringl;n++){var c1=string.charCodeAt(n);var enc=null;if(c1<128){end++}else{if(c1>127&&c1<2048){enc=String.fromCharCode((c1>>6)|192,(c1&63)|128)}else{if((c1&63488)!=55296){enc=String.fromCharCode((c1>>12)|224,((c1>>6)&63)|128,(c1&63)|128)}else{if((c1&64512)!=55296){throw new RangeError("Unmatched trail surrogate at "+n)}var c2=string.charCodeAt(++n);if((c2&64512)!=56320){throw new RangeError("Unmatched lead surrogate at "+(n-1))}c1=((c1&1023)<<10)+(c2&1023)+65536;enc=String.fromCharCode((c1>>18)|240,((c1>>12)&63)|128,((c1>>6)&63)|128,(c1&63)|128)}}}if(enc!==null){if(end>start){utftext+=string.slice(start,end)}utftext+=enc;start=end=n+1}}if(end>start){utftext+=string.slice(start,stringl)}return utftext};
function utf8_decode(str_data){var tmp_arr=[],i=0,c1=0,seqlen=0;str_data+="";while(i<str_data.length){c1=str_data.charCodeAt(i)&255;seqlen=0;if(c1<=191){c1=(c1&127);seqlen=1}else{if(c1<=223){c1=(c1&31);seqlen=2}else{if(c1<=239){c1=(c1&15);seqlen=3}else{c1=(c1&7);seqlen=4}}}for(var ai=1;ai<seqlen;++ai){c1=((c1<<6)|(str_data.charCodeAt(ai+i)&63))}if(seqlen==4){c1-=65536;tmp_arr.push(String.fromCharCode(55296|((c1>>10)&1023)),String.fromCharCode(56320|(c1&1023)))}else{tmp_arr.push(String.fromCharCode(c1))}i+=seqlen}return tmp_arr.join("")};
/*URL编码解码*/
function urlencode(str){str=(str+"").toString();return encodeURIComponent(str).replace(/!/g,"%21").replace(/'/g,"%27").replace(/\(/g,"%28").replace(/\)/g,"%29").replace(/\*/g,"%2A").replace(/%20/g,"+")};
function urldecode(str){return decodeURIComponent((str+"").replace(/%(?![\da-f]{2})/gi,function(){return"%25"}).replace(/\+/g,"%20"))};

/*base64*/
function base64_encode(data){var b64="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";var o1,o2,o3,h1,h2,h3,h4,bits,i=0,ac=0,enc="",tmp_arr=[];if(!data){return data}data=unescape(encodeURIComponent(data));do{o1=data.charCodeAt(i++);o2=data.charCodeAt(i++);o3=data.charCodeAt(i++);bits=o1<<16|o2<<8|o3;h1=bits>>18&63;h2=bits>>12&63;h3=bits>>6&63;h4=bits&63;tmp_arr[ac++]=b64.charAt(h1)+b64.charAt(h2)+b64.charAt(h3)+b64.charAt(h4)}while(i<data.length);enc=tmp_arr.join("");var r=data.length%3;return(r?enc.slice(0,r-3):enc)+"===".slice(r||3)};
function base64_decode(data){var b64="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";var o1,o2,o3,h1,h2,h3,h4,bits,i=0,ac=0,dec="",tmp_arr=[];if(!data){return data}data+="";do{h1=b64.indexOf(data.charAt(i++));h2=b64.indexOf(data.charAt(i++));h3=b64.indexOf(data.charAt(i++));h4=b64.indexOf(data.charAt(i++));bits=h1<<18|h2<<12|h3<<6|h4;o1=bits>>16&255;o2=bits>>8&255;o3=bits&255;if(h3==64){tmp_arr[ac++]=String.fromCharCode(o1)}else{if(h4==64){tmp_arr[ac++]=String.fromCharCode(o1,o2)}else{tmp_arr[ac++]=String.fromCharCode(o1,o2,o3)}}}while(i<data.length);dec=tmp_arr.join("");return decodeURIComponent(escape(dec.replace(/\0+$/,"")))};
function str_replace (search, replace, subject, countObj) {
    let i = 0
    let j = 0
    let temp = ''
    let repl = ''
    let sl = 0
    let fl = 0
    const f = [].concat(search)
    let r = [].concat(replace)
    let s = subject
    let ra = Object.prototype.toString.call(r) === '[object Array]'
    const sa = Object.prototype.toString.call(s) === '[object Array]'
    s = [].concat(s)
    const $global = (typeof window !== 'undefined' ? window : global)
    $global.$locutus = $global.$locutus || {}
    const $locutus = $global.$locutus
    $locutus.php = $locutus.php || {}
    if (typeof (search) === 'object' && typeof (replace) === 'string') {
        temp = replace
        replace = []
        for (i = 0; i < search.length; i += 1) {
            replace[i] = temp
        }
        temp = ''
        r = [].concat(replace)
        ra = Object.prototype.toString.call(r) === '[object Array]'
    }
    if (typeof countObj !== 'undefined') {
        countObj.value = 0
    }
    for (i = 0, sl = s.length; i < sl; i++) {
        if (s[i] === '') {
            continue
        }
        for (j = 0, fl = f.length; j < fl; j++) {
            if (f[j] === '') {
                continue
            }
            temp = s[i] + ''
            repl = ra ? (r[j] !== undefined ? r[j] : '') : r[0]
            s[i] = (temp).split(f[j]).join(repl)
            if (typeof countObj !== 'undefined') {
                countObj.value += ((temp.split(f[j])).length - 1)
            }
        }
    }
    return sa ? s : s[0]
}
function preg_replace(pattern,replacement,subject,limit){if(typeof limit==='undefined')limit=-1;if(subject.match(eval(pattern))){if(limit==-1){return subject.replace(eval(pattern+'g'),replacement);}else{for(x=0;x<limit;x++){subject=subject.replace(eval(pattern),replacement);}return subject;}}else{return subject;}}
var strcut=function(str,iMaxBytes,sSuffix){if(isNaN(iMaxBytes)){return str}if(strlen(str)<=iMaxBytes){return str}var i=0,bytes=0;for(;i<str.length&&bytes<iMaxBytes;++i,++bytes){if(str.charCodeAt(i)>255){++bytes}}sSuffix=sSuffix||"";return(bytes-iMaxBytes==1?str.substr(0,i-1):str.substr(0,i))+sSuffix};
var strlen=function(str){var bytes=0,i=0;for(;i<str.length;++i,++bytes){if(str.charCodeAt(i)>255){++bytes}}return bytes};
var implode=function(c,a){return a.join(c);};
var explode=function(separator,str,limit){if(typeof limit=='undefined'){return str.split(separator);}return str.split(separator,limit);}
var setcookie=function(c,e,g){if(e===undefined){var f="; "+document.cookie;var d=f.split("; "+c+"=");if(d.length===2){return d.pop().split(";").shift()}return null}else{if(e===false){g=-1}var a="";if(g){var b=new Date();b.setTime(b.getTime()+(g*24*60*60*1000));a="; expires="+b.toGMTString()}document.cookie=c+"="+e+a+"; path=/"}};
/*json与字符串转换*/
var json2str=function(oJson){if(typeof(oJson)==typeof(false)){return oJson}if(oJson==null){return"null"}if(typeof(oJson)==typeof(0)){return oJson.toString()}if(typeof(oJson)==typeof("")||oJson instanceof String){oJson=oJson.toString();oJson=oJson.replace(/\\r\\n/,"\\\\r\\\\n");oJson=oJson.replace(/\\n/,"\\\\n");oJson=oJson.replace(/\\"/,'\\\\"');return'"'+oJson+'"'}if(oJson instanceof Array){var strRet="[";for(var i=0;i<oJson.length;i++){if(strRet.length>1){strRet+=","}strRet+=json2str(oJson[i])}strRet+="]";return strRet}if(typeof(oJson)==typeof({})){var strRet="{";for(var p in oJson){if(strRet.length>1){strRet+=","}strRet+='"'+p.toString()+'":'+json2str(oJson[p])}strRet+="}";return strRet}};
var str2json=function(str){return JSON.stringify(str)};

/*验证判断*/
var is_eq=function(str1,str2){if(str1==str2){return(true)}else{return(false)}};
var is_num=function(num){var reg=new RegExp("^[0-9]*$");return reg.test(num)};
var is_phone=function(num){var reg=/^1\d{10}$/;return reg.test(num)};
var is_qq=function(num){var reg=/^[1-9]{1}\d{4,11}$/;return reg.test(num)};
var is_email=function(num){var reg=/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;return reg.test(num)};
var is_id=function(num){var reg=/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;return reg.test(num)};
var is_chinese=function(num){var reg=/[\u4e00-\u9fa5]/g;return reg.test(num)};
var is_reg=function(num){var reg=/^([a-zA-z_]{1})([\w]*)$/g;return reg.test(num)};
var is_tel=function(str){var reg=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;return reg.test(str)};
var is_ip=function(strIP){if(isNull(strIP)){return false}var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g;if(re.test(strIP)){if(RegExp.$1<256&&RegExp.$2<256&&RegExp.$3<256&&RegExp.$4<256){return true}}return false};
var is_zipcode=function(str){var reg=/^(\d){6}$/;return reg.test(str)};
var is_english=function(str){var reg=/^[A-Za-z]+$/;return reg.test(str)};
var is_url=function(str){var reg=/^http:\/\/[A-Za-z0-9-_]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;return reg.test(str)};
var is_int=function(n,iMin,iMax){if(!isFinite(n)){return false}if(!/^[+-]?\d+$/.test(n)){return false}if(iMin!=undefined&&parseInt(n)<parseInt(iMin)){return false}if(iMax!=undefined&&parseInt(n)>parseInt(iMax)){return false}return true};
var is_float=function(n,fMin,fMax){if(!isFinite(n)){return false}if(fMin!=undefined&&parseFloat(n)<parseFloat(fMin)){return false}if(fMax!=undefined&&parseFloat(n)>parseFloat(fMax)){return false}return true};
var is_http=function(url){if(url.indexOf("http://")===-1&&url.indexOf("https://")===-1){return false}return true};

function strfind(string, find) {return !(string.indexOf(find)=== -1);};
function date_eq(strDate1,strDate2){var date1=new Date(strDate1.replace(/\-/g,"\/"));var date2=new Date(strDate2.replace(/\-/g,"\/"));if((date1-date2)>=0){return true;}else{return false;}}
var timeline=function(tt){var today=new Date();var d=new Date(tt);var m=today.getTime()-d.getTime();if(m<=0){m=1000}if(m<60*1000){return Math.floor(m/1000)+"秒前"}else{if(m<60*60*1000){return Math.floor(m/(1000*60))+"分钟前"}else{if(m<60*60*1000*24){return Math.floor(m/(1000*60*60))+"小时前"}else{if(m<60*60*1000*24*7){return Math.floor(m/(1000*60*60*24))+"天前"}else{if(m<60*60*1000*24*7*56){return Math.floor(m/(1000*60*60*24*7))+"周前"}else{return Math.floor(m/(1000*60*60*24*7*52))+"年前"}}}}}};
function strtrim(a){return a.replace(/\s+/g," ");};
