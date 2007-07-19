/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
var _global = (function (){return this;})();

if (typeof(Kumu) == 'undefined') {
  Kumu = {};
}

Kumu.VERSION = "0.1";
Kumu.NAME = "Kumu";
Kumu.DEBUG = false;
Kumu.SCRIPT_RE = '<script[^>]*>([\\S\\s]*?)<\/script>',

/** extends **/
Kumu.extend = function (self, obj) {
  if (self == null) {
    self = {};
  }
  for (var i = 1; i < arguments.length; i++) {
    var o = arguments[i];
    if (typeof(o) != 'undefined' && o != null) {
      for (var k in o) {
        self[k] = o[k];
      }
    }
  }
  return self;
};

Kumu = Kumu.extend(Kumu, {
  
  baseUrl : '',
  
  loadLibrary : {},

  options : {},
  
  _idCache : [],
  
  _templateId:0,
  
  getGlobal : function(){
    return _global;
  },
  
  undef : function(name, object){
    return (typeof (object || _global)[name] == "undefined");
  },
  
  provide : function(name, obj){
    if(name.indexOf('.') == -1){
      return Kumu._createObject(name, obj);
    }    
    if(!obj){
      obj = _global;
    }
    return Kumu._provide(name, obj);
  },

  _provide : function(names, ctx){
    if(typeof(names) == 'string'){
      names = Kumu.toArray(names.split('.'));    
      return Kumu._provide(names, ctx);
    }else{
      var name = Kumu.shift(names);
      if(name){
        var obj = Kumu._createObject(name, ctx);
        if(obj && names.length > 0){
          return Kumu._provide(names, obj);
        }else{
          return ctx;
        }
      }
      return ctx;
    }
  },
  
  _createObject : function(name, obj){
    if(!obj){
      obj = _global;
    }
    if(obj && Kumu.undef(name, obj)){
      obj[name] = {};
    }
    return obj[name];
  },
  
  define : function(name){
    Kumu.provide(name);
    eval('var n = '+name);
    return function(obj){
      n = Kumu.extend(n, obj);
    }
  },

  defineClass : function(name){
    Kumu.provide(name);
    eval(name+'=Kumu.create()');
    eval('var n = '+name);
    return function(obj){
      n.prototype = Kumu.extend(n.prototype, obj);
    }
  },
  
  
  bind : function(func){
    return function(){
      var bind = arguments;
      return function(){
        var args  = new Array();
        args = args.concat.apply(args, bind);
        args = args.concat.apply(args, arguments);
        return func.apply(null, args);
      };
    };
  },

  /** DOM shortcut method **/
  $i : function() {
    var elements = new Array();
    for (var i = 0; i < arguments.length; i++) {
      var element = arguments[i];
      if (typeof element == 'string'){
        element = document.getElementById(element);
      }
      if (arguments.length == 1) {
        return element;
      }
      elements.push(element);
    }
    return elements;
  },

  $t : function() {
    var elements = new Array();
    for (var i = 0; i < arguments.length; i++) {
      var element = arguments[i];
      if (typeof element == 'string'){
        var node = document.getElementsByTagName(element);
        for(var j = 0; j <  node.length; j++){
          elements.push(node[j]);
        }
      }
    }
    return elements;
  },

  $n : function() {
    var elements = new Array();
    for (var i = 0; i < arguments.length; i++) {
      var element = arguments[i];
      if (typeof element == 'string'){
        var node = document.getElementsByName(element);
        for(var j = 0; j <  node.length; j++){
          elements.push(node[j]);
        }
      }
    }
    return elements;
  },

  /** filter  map **/
  map : function(func, lst){
    if(!lst){
      lst = this;
    }
    var ret = []; 
    for(var i = 0; i < lst.length; i++){
      ret.push( func(lst[i]));
    }
    return ret;
  },

  filter : function(func, lst){
    if(!lst){
      lst = this;
    }
    var ret = [];
    for(var i = 0; i < lst.length; i++){
      var o = lst[i];
      if(func(o)){
        ret.push(o);
      }
    }
    return ret;
  },

  toArray : function() {
    var arr = [];
    for(var i = 0; i < arguments.length; i++){
      var arg = arguments[i];
      if(typeof(arg) == 'Array'){
        arr.concat(arg);
      } else {
        if(arg.length){
          for (var j = 0; j < arg.length; j++){
            arr.push(arg[j]);
          }
        }else{
          arr.push(arg);
        }
      }
    }
    return arr;
  },
  
  keys : function(obj){
    var result = [];
    for(var v in obj){
      result.push(v);
    }
    return result;
  },

  items : function(obj){
    var result = [];
    var e;
    for(var k in obj){
      var v;
      try{
        v = obj[k];
      } catch(e){
        continue;
      }
      result.push([k, v]);
    }
    return result;
  },

  shift : function(list) {
    if(!list){
      list = this;
    }
    if(list.length == 0){
      return undefined;
    }
    var obj = list[0];
    for (var i = 0; i < list.length-1; i++){
      list[i] = list[i + 1];
    }
    list.length--;
    return obj;
  },

  unshift : function() {
    var start = 1;
    var list;
    if(arguments[0] instanceof Array){
      list = arguments[0];
    }else{
      list = this;
      start = 0;
    }
    var arr = [];
    for (var i = start; i < arguments.length; i++){
      if(arguments[i] instanceof Array){
        arr = arr.concat(arguments[i]);
      }else{
        arr.push(arguments[i]);
      }
    }
    var o = arr.concat(list);
    for (var i = 0; i < o.length; i++){
      list[i] = o[i];
    }
    return list.length;
  },

  include: function(value, list) {
    if(!list){
      list = this;
    }
    for(var i = 0; i < list.length; i++){
      if(list[i] == value){
        return true;
      }
    }
    return false;
  },

  separate : function(str){
    var i = str.indexOf("_");
    if(i == 0){
      i = str.indexOf("_", 1);
    }
    var ar = [];
    ar.push(str.substring(0, i));
    ar.push(str.substring(i+1, str.length));
    return ar;
  },

  ltrim : function(str){
    if(!str){
      str = this;
    }
    return str.replace(/^[ 　]+/, '');
  },
  
  rtrim : function(str){
    if(!str){
      str = this;
    }
    return str.replace(/[ 　]+$/, '');
  },
  
  trim : function(str){
    if(!str){
      str = this;
    }
    var tmp = Kumu.ltrim(str);
    return Kumu.rtrim(tmp);
  },
 
  camelize : function(str){
    if(!str){
      str = this;
    }
    return str.replace(/-([a-z])/g,
      function($0,$1){
        return $1.toUpperCase()
      }
    );
  },
  
  startsWith : function(prefix, str) {
    if(!str){
      str = this;
    }
    if (prefix.length > str.length){
      return false;
    }
    return str.indexOf(prefix) == 0;
  },

  endsWith : function(suffix, str) {
    if(!str){
      str = this;
    }
    if (suffix.length > str.length){
      return false;
    }
    return str.lastIndexOf(suffix) == (str.length - suffix.length);
  },
  
  ignoreScripts: function(str) {
    if(!str){
      str = this;
    }
    return str.replace(new RegExp(Kumu.SCRIPT_RE, 'img'), '');
  },
  
  create: function() {
    return function() {
      if(this.initialize){ 
        this.initialize.apply(this, arguments);
      }
    }
  },
  
  grouping : function(){
    var functions = [];
    for (var i = 0; i < arguments.length; i++){
      if(typeof(arguments[i]) == 'function'){
        functions.push(arguments[i]);
      }
  	}
  	return function(){
      var result = [];
      for (var i = 0; i < functions.length; i++){
        result.push(functions[i].apply(functions[i], arguments));
      }  		
  	}
  },
  
  init : function(){
    var sc = Kumu.toArray(Kumu.$t('script'));
    var root = Kumu.filter(function(s) {
      return (s.src && s.src.match(/kumu\.js$/) || s.src.match(/kumu\.js?/));
    }, sc);
    Kumu.map(function(node){
      if(Kumu.baseUrl == ''){
        var arr = node.src.split('kumu.js');
        Kumu.baseUrl = arr[0];
        if(arr[1] && arr[1] != ''){
          var options = arr[1].replace('?', '').split('&');
          Kumu.map(function(option){
            var param = option.split('=');
            if(param && param.length == 2){
              Kumu.options[param[0]] = param[1];
            }
          }, options);
        }
      }
    }, root);
  },
  
  staticLoad : function(){
    Kumu.map(function(lib){
      var libraryName = Kumu.baseUrl+lib.replace('.','/')+'.js';
      if(!Kumu.loadLibrary[libraryName]){
        Kumu.loadLibrary[libraryName] = libraryName;
        document.write('<script type="text/javascript" src="'+libraryName+'"></script>'); 
      }
    }, Kumu.toArray(arguments));
  },

  _createXHR : function (){
    var axo =  new Array(
      "Microsoft.XMLHTTP",
      "Msxml2.XMLHTTP.4.0",
      "Msxml2.XMLHTTP.3.0",
      "Msxml2.XMLHTTP"
    );
    var xmlHttp = false;
    /*@cc_on
    @if (@_jscript_version >= 5)
    for (var i = 0; !xmlHttp && i < axo.length; i++) {
      try {
        xmlHttp = new ActiveXObject(axo[i]);
      } catch(e) {
      }
    }
   	@else
      xmlHttp = false;
    @end @*/
    if (!xmlHttp && typeof XMLHttpRequest != "undefined") {
      try{
        // for Firefox, safari
        xmlHttp = new XMLHttpRequest();
      } catch(e) {
        xmlHttp = false;
      }
    }
    return xmlHttp;
  },

  _loadModule : function (filename){
    xmlHttp = Kumu._createXHR();
    xmlHttp.open("GET", filename, false);
    xmlHttp.send(null);
    eval(xmlHttp.responseText);
  },

  _loadTemplate : function (){    
    if(Kumu.options && Kumu.options['mockInclude'] && Kumu.options['mockInclude'] == 'true'){
      if(Kumu.options && Kumu.options['layout']){
        var _body = document.body.innerHTML;
        document.body.innerHTML = '';
        var src = Kumu.options['layout'];
        var xmlHttp = Kumu._createXHR();
        xmlHttp.open("GET", src, false);
        xmlHttp.send(null);
        var text = xmlHttp.responseText;
        document.body.innerHTML = text;
        var includes = Kumu.$t('div');
        if(includes){
          for(var i = 0;i < includes.length; i++){
            var node = includes[i];
            var id = node.getAttribute('id');
            if(id && Kumu.startsWith('mockIncludeChildBody', id)){
              if (node.outerHTML) {
                node.outerHTML = _body;
              } else {
                var range = node.ownerDocument.createRange();
                range.selectNodeContents(node);
                node.parentNode.replaceChild(range.createContextualFragment(_body), node);
              }            
            }
          }
        }                
      }
      Kumu._includeTemplate();
    }
  },
  
  _replaceHTML : function(node, text){
    if (node.outerHTML) {
      node.outerHTML = text;
    } else {
       var range = node.ownerDocument.createRange();
       range.selectNodeContents(node);
       node.parentNode.replaceChild(range.createContextualFragment(text), node);
    }            
  },
    
  _includeTemplate : function(){
    var includes = Kumu.$t('div');
    if(includes){
      for(var i = 0;i < includes.length; i++){
        var node = includes[i];
        var id = node.getAttribute('id');
        if(id && Kumu.startsWith('mockInclude', id)){
          var src = node.getAttribute('src');
          if(src){
            var xmlHttp = Kumu._createXHR();
            xmlHttp.open("GET", src, false);
            xmlHttp.send(null);
            var text = xmlHttp.responseText;
            Kumu._replaceHTML(node, text);
          }
        }
      }
    }
  },
  
  _createXMLFromStr : function(str){
    if (!Kumu.undef("DOMParser")) {
      var parser = new DOMParser();
      return parser.parseFromString(str, 'text/xml');
    }else{
      if(!Kumu.undef("ActiveXObject")){
        var doc = null;        
        var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
        for(var i = 0; i < prefixes.length; i++) {
          try {
            doc = new ActiveXObject(prefixes[i] + ".XMLDOM");
          }
          catch (e) {
          }
          if (doc) {
            break;
          }
        }
        doc.async = false;
        doc.loadXML(str);
        return doc;
      }else{
        //TODO implements other
        return "";
      }
    }
  },
  
  dynamicLoad : function(){
    Kumu.map(function(lib){
      var libraryName = Kumu.baseUrl+lib.replace('.','/')+'.js';
      if(!Kumu.loadLibrary[libraryName]){
        Kumu.loadLibrary[libraryName] = libraryName;
        Kumu._loadModule(libraryName);
      }
    }, Kumu.toArray(arguments));
  },
  
  beginTrace : function(args, func){
    var funcstr = func.toString();
    var ret = funcstr.match(/[0-9A-Za-z_]+\(/).toString();
    ret = ret.substring(0,ret.length-1); 
    var str = "START function : ["+ret+"] : prameter : [";
    for(var i = 0; i < args.length; i++){
      str += args[i].toString();
      str += ' : ';
    }
    str += ']';
    Kumu.log(str);
    return args;
  },

  endTrace : function(result, args, func){
    var funcstr = func.toString();
    var ret = funcstr.match(/[0-9A-Za-z_]+\(/).toString();
    ret = ret.substring(0,ret.length-1); 
    var str = "END function : ["+ret+"] : result : ["+result+"] : prameter : [";
    for(var i = 0; i < args.length; i++){
      str += args[i].toString();
      str += ' : ';
    }
    str += ']';
    Kumu.log(str);
    return result;
  },
  
  log :function(obj){
    try {
      if(obj.innerHTML){
        obj = obj.innerHTML;
      }
      var br = document.createElement("br");
      var span = document.createElement("span");
      document.body.appendChild(br);
      document.body.appendChild(span.appendChild(document.createTextNode(obj)));
    } catch (e) {
    }
  },
    
  addBefore : function(func, before) {
    var addfunc = function() {
      return func.apply(this, before(arguments, func));
    };
    addfunc.__proto__ = func;
    addfunc.toString = function() {
      return addfunc.__proto__.toString();
    };
    return addfunc;  
  },

  addAfter : function(func, after) {
    var addfunc = function() {
      return after(func.apply(this, arguments), arguments, func);
    };
    addfunc.__proto__ = func;
    addfunc.toString = function() {
      return addfunc.__proto__.toString();
    };
    return addfunc;  
  },

  addAround : function(func, around) {
    var addfunc = function() {
      return around(arguments, func);
    };
    addfunc.__proto__ = func;
    addfunc.toString = function() {
      return addfunc.__proto__.toString();
    };
    return addfunc;  
  },
  
  trace : function(target){
    if(typeof(target) == "function"){
      obj = this.addBefore(target, this.beginTrace);
      obj = this.addAfter(obj, this.endTrace);
      return obj;        
    }else{
      for(var i in target){
        var obj = target[i];
        if(typeof(obj) == "function"){
          obj = this.addBefore(obj, this.beginTrace);
          obj = this.addAfter(obj, this.endTrace);
          target[i] = obj;
        }
      }
    }
  },
  
  getElementsById : function(id){
    if(!(id instanceof String) && typeof id != "string"){
      return [id];
    }

    var nodes = [];
    var elem = Kumu.$i(id);
    if(!Kumu._idCache[id]){
      Kumu._idCache[id] = []; 
    }
    while(elem){
      if(!elem["cached"]){
        nodes.push(elem);
      }
      elem.id = "";
      elem = $i(id);
    }
    Kumu._idCache[id] = Kumu._idCache[id].concat(nodes);
    Kumu.map(function(n){
      n.id = id;
      n.cached = true;
    },_idCache[id] );
    return Kumu._idCache[id];
  }
  
});

/** global option **/
var $i = Kumu.$i;
var $t = Kumu.$t;
var $n = Kumu.$n;

Array.prototype = Kumu.extend(Array.prototype, {
  map:Kumu.map,
  filter:Kumu.filter,
  shift:Kumu.shift,
  unshift:Kumu.unshift,
  include:Kumu.include
  
});

String.prototype = Kumu.extend(String.prototype,{
  ltrim : Kumu.ltrim,
  rtrim : Kumu.rtrim,
  trim : Kumu.trim,
  camelize : Kumu.camelize,
  startsWith : Kumu.startsWith,
  endsWith : Kumu.endsWith,
  ignoreScripts : Kumu.ignoreScripts
});

Function.prototype.getName = function() {
  var ret = this.toString().match(/[0-9A-Za-z_]+\(/).toString();
  return ret.substring(0,ret.length-1);
}

Function.prototype.bind = function() {
  return Kumu.bind(this);
}

Function.prototype.bindScope = function() {
  if(arguments.length == 0){
    return this;
  }
  var x = this;
  var args = Kumu.toArray(arguments);
  var object = args.shift();
  return function() {
    var args2 = Kumu.toArray(arguments);
    args2 = args2.concat(args);    
    return x.apply(object, args2);
  }
}

Function.prototype.bindScopeAsEventListener = function() {
  if(arguments.length == 0){
    return this;
  }
  var x = this;
  var args = Kumu.toArray(arguments);
  var object = args.shift();
  return function(event) {
    var arr = Array.prototype.concat.apply([event || window.event], args);
    return x.apply(object, arr);
  }
}

Function.prototype.kwargs = function(){
  var x = this;
  var funcstr = x.toString();
  var ret = funcstr.match(/\([0-9A-Za-z_, ]+\)/).toString();
  ret = ret.substring(1,ret.length-1); 
  var arr = ret.split(',');
  
  var func = function(dict){
    if(!dict){
      return;
    }
    var args = [];
    for(var i = 0; i < arr.length; i++){
      var key = arr[i];
      key = key.trim();
      if(key in dict){
        args[i] = dict[key];
      }
    }
    return x.apply(x, args);
  }
  return func;
}

Function.prototype.state = function(obj){
  var x = this;
  var func = function() {
  	var args = [];
    for(var i = 0; i < arguments.length;i++){
      args.push(arguments[i]);
    }
    if(obj && obj['init']){
      if(typeof(obj['init']) == 'function'  && func.__state == 1){
        obj['init'].apply(x,args);
      }
    }
    if(!func.__cancel){
	  func.__state = 2;
      if(obj && obj['execute']){
        if(typeof(obj['execute']) == 'function'){
          obj['execute'].apply(x,args);
        }
      }
      var o = x.apply(x, args);
      func.__state = 3;
      if(obj && obj['complete']){
        if(typeof(obj['complete']) == 'function'){
          obj['complete'].apply(x,args);
        }
      }
      return o;
    }
  }
  func.__cancel = false;
  func.__state = 1;
  func.cancel = function(c){
    func.__cancel = c;
  }
  func.origin = x;
  return func;
}

Function.prototype.delay = function(time){
  var x = this;
  var timeout;
  var args = [];
  
  var exec = function() {
    var o = x.apply(x, args);
    clearTimeout(timeout);
    return o;
  }
  
  var func =  function(){
    for(var i = 0; i < arguments.length;i++){
      args.push(arguments[i]);
    }
    timeout = setTimeout(exec, time);
  }
  
  func.origin = x;
  func.clear = function(){
    if(timeout){
      clearTimeout(timeout);
    }
  }
  return func;
}

Function.prototype.addBefore = function(func){
  var x = this;
  return Kumu.addBefore(this, func);
}

Function.prototype.addAfter = function(func){
  return Kumu.addAfter(this, func);
}

Function.prototype.addAround = function(func){
  return Kumu.addArround(this, func);
}
Kumu.init();

if(Kumu.options && Kumu.options['mockInclude'] && Kumu.options['mockInclude'] == 'true'){
  if (window.addEventListener) {
    window.addEventListener('load', Kumu._loadTemplate, false);
  } else if (window.attachEvent) {
    window.attachEvent('onload', Kumu._loadTemplate);
  }
  if (window.removeEventListener) {
    window.removeEventListener('unload', Kumu._loadTemplate, false);
  } else if (window.detachEvent) {
     window.detachEvent('onunload', Kumu._loadTemplate);
  }
}

Kumu.defineClass('Kumu.Template')({
    
  initialize : function(url, options){
    this._id = Kumu._templateId++;
    this.redneredSuffix = ':rendered'+this._id;
    this._nodeCache = {};
    this._templateCache = {};
    if(typeof url == 'string'){
      this.url = url;
      this.options = options || {};
    }else{
      this.options = url || {};
    }
    this.append = this.options['append'] || false;
    this._state = 'Initialize';
  },
  
  _callStateMethod : function(){
    var cb = this.options['on'+this._state];    
    if(cb && typeof cb == 'function'){
      cb();
    }
  },
  
  setStyle : function(element, style) {
    for(var v in style){
      if(v == 'opacity'){
        this.setOpacity(element, style[v]);
      }else{
        element.style[v.camelize()] = style[v];
      }
    }
  },

  setOpacity : function(element, value){
    if(value == 1){
      var value = (/Gecko/.test(navigator.userAgent) && !/Konqueror|Safari|KHTML/.test(navigator.userAgent)) ? 0.999999 : null;
      this.setStyle(element, {opacity:value}); 
      if(/MSIE/.test(navigator.userAgent)){
        this.setStyle(element, {filter:self._getStyle(element,'filter').replace(/alpha\([^\)]*\)/gi,'')});  
      }
    } else {
      this.setStyle(element, {opacity: value});
      if(/MSIE/.test(navigator.userAgent)){
        this.setStyle(element, {filter:self._getStyle(element,'filter').replace(/alpha\([^\)]*\)/gi,'') + 'alpha(opacity='+value*100+')'});
      }
    }
  },
  
  _setJSONData : function(node, data){
    if(node.style.display == 'none'){
      node.style.display = '';
    }
    if(data['data']){
      var tag = node.tagName.toLowerCase();
      if(tag == 'input'){
        node.value = data['data'];
      }else{
        node.innerHTML = data['data'];
      }
    }
    if(data['attr']){
      var attrs = data['attr'];
      for(var attr in attrs){
        if(attr == 'style'){
          this.setStyle(node, attrs[attr]);
        }else{
          node.setAttribute(attr, attrs[attr]);
        }
      }
    }
  },
  
  _prepare : function(data){
    var predata = {};
    for(var v in data){
      var key = v;
      var attr_key;
      if(v.indexOf('@') > 0){
        key = v.substring(0, v.indexOf('@'));
        attr_key = v.substring(v.indexOf('@')+1);
      }else{
        attr_key = null;
      }
      if(!predata[key]){
        predata[key] = {};
      }
      
      if(attr_key){
        if(!predata[key]['attr']){
          predata[key]['attr'] = {};
        }
        predata[key]['attr'][attr_key] = data[v];        
      }else{
        predata[key]['data'] = data[v];
      }
    };
    return predata;
  }, 
  
  render : function(json, target, replace){    
    this._callStateMethod();
    if(this.url){
      this._renderTemplate.bindScope(this)(this.url, json, this.url, target, replace);
    }else{
      this._render.bindScope(this)('inner', document, json);
    }
    this._state = 'Complete';
    this._callStateMethod();
    this._state = 'Initialize';
  },
    
  _render : function(prefix, doc, json, change_id){
    var prepare_data = this._prepare(json);
    for(var v in prepare_data){
      var nodes = [];
      var o = prepare_data[v];
      var data = o['data'];
      var node = doc.getElementById(v);
      var cacheKey = prefix+v;
      if(data && data instanceof Array){
        var parent, orig;
        if(this._nodeCache[cacheKey]){
          orig = this._nodeCache[cacheKey]['node'];
          parent = this._nodeCache[cacheKey]['parent'];
          if(!this.appnd){
            this.removeRender(orig.id, doc);
          }
        }else{
          var parent = node.parentNode;
          if(parent.nodeType == 3){
            parent = parent.parentNode;
          }
          orig = node.cloneNode(true);
          this._nodeCache[cacheKey] = {'parent':parent,'node':orig};
          parent.removeChild(node);
        }
        for(var j = 0; j < data.length; j++){
          var obj = data[j];
          var clone = orig.cloneNode(true);
          if(clone.style.display &&  clone.style.display == 'none'){
            clone.style.display = '';
          }
          parent.appendChild(clone);
          this._render(prefix, doc, obj, "true");
          clone.id = clone.id+this.redneredSuffix;
          clone.__id = clone.id;
        }
      }else{
        if(node){
          this._setJSONData(node, o);
          if(change_id){
            node.id = v+this.redneredSuffix;
            node.__id = v;
          }
        }
      }
    }
  },
  
  _setTemplate : function(target, data, replace){
    if(replace){
      if (target.outerHTML) {
        target.outerHTML = data;
      } else {
        var range = target.ownerDocument.createRange();
        range.selectNodeContents(target);
        target.parentNode.replaceChild(range.createContextualFragment(data), target);
      }            
    }else{
      target.innerHTML = data;
    }
  },
  
  _update : function(from, to, doc){
    if(!doc){
      doc = document;
    }
    var html = from.innerHTML;
    var tagName = to.tagName.toUpperCase();
    if (['THEAD','TBODY','TR','TD'].include(tagName)) {
      var div = doc.createElement('div');
      switch (tagName) {
        case 'THEAD':
        case 'TBODY':
          div.innerHTML = '<table><tbody>' +  html.ignoreScripts() + '</tbody></table>';
          depth = 2;
          break;
        case 'TR':
          div.innerHTML = '<table><tbody><tr>' +  html.ignoreScripts() + '</tr></tbody></table>';
          depth = 3;
          break;
        case 'TD':
          div.innerHTML = '<table><tbody><tr><td>' +  html.ignoreScripts() + '</td></tr></tbody></table>';
          depth = 4;
      }
      Kumu.toArray(to.childNodes).map(function(node) { to.removeChild(node) });
      for(var i = 0; i < depth; i++){
        div = div.firstChild;
      }
      Kumu.toArray(div.childNodes).map(function(node) { to.appendChild(node) });
    } else {
      to.innerHTML = html.ignoreScripts();
    }
  
  },
  
  _renderIframeDoc : function(prefix, url, iframe, data, id, replace){
    var index = this._templateCache.index || 0;
    var doc;
    if(iframe.contentDocument){
      doc = iframe.contentDocument;
    }else{
      doc = iframe.Document;
    }
    if(this._templateCache[url]){
      doc.body.innerHTML = this._templateCache[url];
      for(var k in this._nodeCache){
        if(k.startsWith(url)){
          var orig = this._nodeCache[k]['node'];
          var eachid = this._nodeCache[k]['node'].id;
          var eachnode = doc.getElementById(eachid+this.redneredSuffix);
          var parent = eachnode.parentNode;
          if(parent.nodeType == 3){
            parent = parent.parentNode;
          }
          this._nodeCache[k]['parent'] = parent;
          var clone = eachnode.cloneNode(true);
          this._update(orig, clone, doc);
          clone.id = clone.id.replace(this.redneredSuffix, '');          
          this._nodeCache[k]['node'] = clone;
        }
      }
    }
    
    this._render(prefix, doc, data);
    var node = document.getElementById(id);
    if(node){
      this._setTemplate(node, doc.body.innerHTML, replace);
      this._templateCache[url] = doc.body.innerHTML;
      this._templateCache.index = index+1;

      (function(){
        var parent = iframe.parentNode;
        if(parent.nodeType == 3){
          parent = parent.parentNode;
        }
        parent.removeChild(iframe);
      }).delay(50);
    }
  }, 
  
  _renderTemplate : function(prefix, data, url, id, replace){
    var scope = this;
    var index = this._templateCache.index || 0;
    var iframe = document.createElement('iframe');
    iframe.setAttribute('id', '_tmpFrame'+index);
    iframe.setAttribute('border', '0');
    iframe.setAttribute('height', '0px');
    iframe.setAttribute('width', '0px');
    if(!this._templateCache[url]){
      iframe.setAttribute('src', url);
    }
    iframe.style.display = 'none';
    this._state = 'Load';
    this._callStateMethod();
    if (document.all) {
      iframe.onreadystatechange = function () {
        if (this.readyState == "complete") {
          scope._state = 'Loaded';
          scope._callStateMethod();
          scope._renderIframeDoc.bindScope(scope)(prefix, url, iframe, data, id, replace);
          this.onreadystatechange = null;
        }
      }
    } else {
      this._state = 'Initialize';
      iframe.onload = (function(){
        this._state = 'Loaded';
        this._callStateMethod();
        this._renderIframeDoc(prefix, url, iframe, data, id, replace);
      }).bindScope(this);
    }
    document.body.appendChild(iframe);
  },

  removeRender : function(id, doc){
    doc = doc || document;
    var removeId = id+this.redneredSuffix;
    var elem = doc.getElementById(removeId);    
    while(elem){
      elem.parentNode.removeChild(elem);
      elem = doc.getElementById(removeId);
    }
  }

});

Kumu.define('Kumu.JSONSerializer')({
  
  serialize:function (o) {
    var type = typeof (o);
    if (type == "undefined") {
      return "undefined";
    } else {
      if ((type == "number") || (type == "boolean")) {
        return o + "";
      } else {
        if (o === null) {
          return "null";
        }
      }
    }
    if (type == "string") {
      return o;
    }
    if(o.innerHTML){
      return o.innerHTML;
    }
    var me = arguments.callee;
    if (type != "function" && typeof (o.length) == "number") {
      var res = [];
      for (var i = 0; i < o.length; i++) {
        var val = me(o[i]);
        if (typeof (val) != "string") {
          val = "undefined";
        }
        res.push(val);
      }
      return "[" + res.join(",") + "]";
    }
    if (type == "function") {
      return o.toString();
    }
    res = [];
    for (var k in o) {
      var useKey;
      if (typeof k == "number") {
        useKey = "\"" + k + "\"";
      } else {
        if (typeof k == "string") {
          useKey = k;
        } else {
          continue;
        }
      }
      val = me(o[k]);
      if (val && typeof (val) != "string") {
        continue;
      }
      res.push(useKey + ":" + val);
    }
    return "{" + res.join(",") + "}";
  }
});

$dump = function(obj){
  if(obj.innerHTML){
    obj = obj.innerHTML;
  }
  Kumu.log(Kumu.JSONSerializer.serialize(obj));
}