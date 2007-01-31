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
if (typeof(Kumu) == 'undefined') {
  Kumu = {};
}
if (typeof(Kumu.Ajax) == 'undefined') {
  Kumu.Ajax = {};
};

Kumu.Ajax = {

  AJAX_COMPONENT_NAME : "AjaxComponent",
  HTTP_STATUS_OK : 200,
  HTTP_STATUS_NOT_FOUND : 404,

  XML_HTTP_REQUEST_STATUS_UNINITIALIZED : 0,
  XML_HTTP_REQUEST_STATUS_LOADING : 1,
  XML_HTTP_REQUEST_STATUS_LOADED : 2,
  XML_HTTP_REQUEST_STATUS_INTERACTIVE : 3,
  XML_HTTP_REQUEST_STATUS_COMPLETE : 4,

  RESPONSE_TYPE_XML : 1,
  RESPONSE_TYPE_JSON : 2,
  RESPONSE_TYPE_TEXT : 3,
  RESPONSE_TYPE_HTML : 4,
  
  READY_STATE : ['Uninitialized', 'Loading', 'Loaded', 'Interactive'],
  
  axo : new Array(
    "Microsoft.XMLHTTP",
    "Msxml2.XMLHTTP.4.0",
    "Msxml2.XMLHTTP.3.0",
    "Msxml2.XMLHTTP"
  ),
  
  URL : 'teeda.ajax',
  
  ASYNC : true,

  DEBUG : false,

  getS2AjaxComponent : function() {
    return new this.AjaxComponent();
  },

  AjaxComponent : function () {
    var self = Kumu.Ajax;
    this.name = self.AJAX_COMPONENT_NAME;
    this.responseType = null;
    this.url = self.URL;
    this.async = self.ASYNC;
    this.params = null;
    this.doAction = function(ajaxResponse){}
  },

  AjaxProcess : function (req, ajaxComponent) {
    var self = Kumu.Ajax;
    this.xhr = req;
    this.ajaxComponent = ajaxComponent;
    this.cancel = self._createCanceler(req, ajaxComponent);
  },

  _createXmlHttp : function(){
    var xmlHttp = false;
    /*@cc_on
    @if (@_jscript_version >= 5)
    var self = Kumu.Ajax;
    for (var i = 0; !xmlHttp && i < self.axo.length; i++) {
      try {
        xmlHttp = new ActiveXObject(self.axo[i]);
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
        //xmlHttp.overrideMimeType("text/xml");
      } catch(e) {
        xmlHttp = false;
      }
    }
    return xmlHttp
  },

  debugPrint : function(message, errorFlg) {
    if (errorFlg) {
      try {
        var div = document.createElement("div");
        document.body.appendChild(div);
        div.setAttribute("id", "ajax_msg");
        message = "<font color='red'>" + message + "</font>";
        document.getElementById("ajax_msg").innerHTML = "<br/>" + message;
      } catch (e) {
      }
    } else {
      try {
        var br = document.createElement("br");
        var span = document.createElement("span");
        document.body.appendChild(br);
        document.body.appendChild(span.appendChild(document.createTextNode(message)));
      } catch (e) {
      }
    }
  },

  _checkComponent : function(component) {
    var self = Kumu.Ajax;
    var name;
    try {
      name = component.name;
    } catch(e) {
      return false;
    }
    if (self.AJAX_COMPONENT_NAME != name || !component.doAction || !component.url) {
      return false;
    }
    return true;
  },

  executeAjax : function(ajaxComponent) {
    var self = Kumu.Ajax;
    if (!self._checkComponent(ajaxComponent)) {
      self.debugPrint("IllegalArgument. argument object is not AjaxComponent. implements url or doAction!", true);
      return;
    }
    var xmlHttp = self._createXmlHttp();
    if (!xmlHttp || !document.getElementById) {
      self.debugPrint("This browser does not support Ajax.", true);
      return;
    }
    var sysdate = new String(new Date());
    var url = ajaxComponent.url;
    var async = ajaxComponent.async;
    var parameters = "";
    var params = ajaxComponent.params;
    if(!params){
      params = {};
    }
    var method = 'GET';
    if(params.method){
      method = params.method.toUpperCase();
      if(method != 'GET' && method != 'POST'){
        method = 'GET';
      }
      delete params.method;
    }
    
    if(ajaxComponent.timeout){
      var timerId = self._setTimeout(xmlHttp, ajaxComponent, ajaxComponent.timeout, ajaxComponent.onTimeout);
      ajaxComponent._clearTimeout = function(){
        clearTimeout(timerId);
      }      
    }
    
    var process = new Kumu.Ajax.AjaxProcess(xmlHttp, ajaxComponent);
    
    if(method == 'GET'){
      url += "?time=" + self.encodeURL(sysdate);
      if(null != params){
        for(var key in params){
          parameters += "&" + key + "=" + self.encodeURL(params[key]);
        }
      }
      url += parameters;
      if(xmlHttp){
        self._registAjaxListener(xmlHttp, ajaxComponent);
        xmlHttp.open("GET", url, async);
        xmlHttp.setRequestHeader("If-Modified-Since", sysdate);
        xmlHttp.send(null);
      }
    }else{
      params['time'] = self.encodeURL(sysdate);
      if(params){
        var array = new Array();
        for(var v in params) {
          array.push(v + "=" + encodeURIComponent(params[v]));
        }
        parameters = array.join("&");
      }
      if(xmlHttp){
        self._registAjaxListener(xmlHttp, ajaxComponent);
        xmlHttp.open("POST", url, async);
        xmlHttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
        xmlHttp.setRequestHeader("If-Modified-Since", sysdate);
        xmlHttp.send(parameters);
      }
    }
    return process;
  },
  
  _setTimeout : function(req, ajaxComponent, time, callback){
    var timerId;
    var self = Kumu.Ajax;
    var canceler = self._createCanceler(req, ajaxComponent);
    var onTimeout = function(){
      canceler();
      if(callback){
        callback(req, ajaxComponent);
      }
      clearTimeout(timerId);
    }
    timerId = setTimeout(onTimeout, time * 1000);
    return timerId;
  },
  
  _createCanceler : function(req, ajaxComponent){
    return function(){
      var self = Kumu.Ajax;
      if (req.readyState == 0 || req.readyState == self.XML_HTTP_REQUEST_STATUS_COMPLETE){
        return;
      }
      req.abort();
      if (ajaxComponent._clearTimeout){
        ajaxComponent._clearTimeout();
      }
    };
  },
  
  _onReadyStateChange : function(req, ajaxComponent){
    var self = Kumu.Ajax;
    var event = 'on'+self.READY_STATE[req.readyState];
    if (ajaxComponent[event] && !(ajaxComponent._called)){
      ajaxComponent[event](req, ajaxComponent);
      ajaxComponent[event]._called = true;
    }else if (ajaxComponent.doAction && ajaxComponent.doAction[event] && !(ajaxComponent.doAction[event]._called)){
      ajaxComponent.doAction[event](req, ajaxComponent);
      ajaxComponent.doAction[event]._called = true;
    }
  },
  
  _onException : function(exception, ajaxComponent){
    if(ajaxComponent.doAction && ajaxComponent.doAction.onException){
      ajaxComponent.doAction.onException(exception, ajaxComponent);
    }
    if (ajaxComponent._clearTimeout){
      ajaxComponent._clearTimeout();
    }
  },
  
  _evalResult : function(req, ajaxComponent) {
    var self = Kumu.Ajax;
    var status;
    try{
      status = req.status;
    }catch(e){
    }
    if (status >= self.HTTP_STATUS_OK && status < 300) {
      if (self.DEBUG) self.debugPrint(req.responseText);
      if (self.RESPONSE_TYPE_JSON == ajaxComponent.responseType) {
        var resText = req.responseText;
        ajaxComponent.doAction(eval('(' + resText + ')'));
      } else if (self.RESPONSE_TYPE_XML == ajaxComponent.responseType) {
        var responseText = req.responseXML;
        ajaxComponent.doAction(responseText);
      } else {
        ajaxComponent.doAction(req.responseText);
      }
      
      if (ajaxComponent._clearTimeout){
        ajaxComponent._clearTimeout();
      }
    } else {
      if(status > 0){ 
        if (ajaxComponent.doAction && ajaxComponent.doAction.onFailure){
          ajaxComponent.doAction.onFailure(req, ajaxComponent);
        }else{
          self.debugPrint("AjaxError! status["+status+"] message["+req.responseText+"]", true);
        }
      }
      if (ajaxComponent._clearTimeout){
        ajaxComponent._clearTimeout();
      }
    }
  },
  
  _registAjaxListener : function(req, ajaxComponent) {
    var self = Kumu.Ajax;
    req.onreadystatechange = function() {
      try{
        if (self.XML_HTTP_REQUEST_STATUS_COMPLETE == req.readyState) {
          self._evalResult(req, ajaxComponent);
        }else{
          self._onReadyStateChange(req, ajaxComponent);
        }
      }catch(e){
        self._onException(e, ajaxComponent);
      }
    };

  },

  encodeURL : function encodeURL(val) {
    if (encodeURI) {
      return encodeURI(val);
    }
    if (encodeURIComponent) {
      return encodeURIComponent(val);
    }
    if (escape) {
      return escape(val);
    }
  },

  _getComponentName : function(func){
    var str = func.toString();
    var ret = str.match(/[0-9A-Za-z_]+\(/).toString();
    ret = ret.substring(0,ret.length-1);
    var idx = ret.indexOf("_");
    if (idx == -1) {
      return [];
    }
    var componentName = ret.substring(0, idx);
    var actionName = ret.substring(idx + 1);
    var arr = new Array(componentName, actionName);

    return arr;
  },

  executeTeedaAjax : function(callback, param, responseType){
    var self = Kumu.Ajax;
    var ajax = self.getS2AjaxComponent();
    var components = self._getComponentName(callback);
    if(!param){
      param = {};
    }
    
    if('onUninitialized' in param){
      ajax.onUninitialized = param['onUninitialized'];
      delete param['onUninitialized'];
    }
    if('onLoading' in param){
      ajax.onLoading = param['onLoading'];
      delete param['onLoading'];
    }
    if('onLoaded' in param){
      ajax.onLoaded = param['onLoaded'];
      delete param['onLoaded'];
    }
    if('onFailure' in param){
      ajax.onFailure = param['onFailure'];
      delete param['onFailure'];
    }
    if('onException' in param){
      ajax.onException = param['onException'];
      delete param['onException'];
    }
    if('timeout' in param){
      ajax.timeout = param['timeout'];
      delete param['timeout'];
    }
    if('onTimeout' in param){
      ajax.onTimeout = param['onTimeout'];
      delete param['onTimeout'];
    }
    
    ajax.params = param;
    if(param instanceof Array){
      for(var i = 0; i < param.length; i++){
        ajax.params["AjaxParam" + new String(i)] = param[i];
      }
    }
    if(!("component" in param) && !("action" in param) && (components.length == 2) ){
      //callback name bind
      ajax.params["component"] = components[0];
      ajax.params["action"] = components[1];
    }

    ajax.doAction = callback;
    if (!responseType) {
      responseType = self.RESPONSE_TYPE_JSON;
    }
    ajax.responseType = responseType;
    return self.executeAjax(ajax);
  },

  _setJSONData : function(node, data){
    if(node.style.display == 'none'){
      node.style.display = '';
    }
    node.innerHTML = data;
  },

  render : function(json){
    var self = Kumu.Ajax;
    for(var v in json){
      var nodes = [];
      var elem = document.getElementById(v);
      while(elem){
        nodes.push(elem);
        elem.id = "";
        elem = document.getElementById(v);
      }
      for(var i = 0; i < nodes.length; i++){
        var node = nodes[i];
        node.id = v;
        var o = json[v];
        if(o && o instanceof Array){
          var parent = node.parentNode;
          if(parent.nodeType == 3){
            parent = parent.parentNode;
          }
          var orig = node.cloneNode(true);
          parent.removeChild(node);
          for(var j = 0; j < o.length; j++){
            var obj = o[j];
            var clone = orig.cloneNode(true);
            if(clone.style.display &&  clone.style.display == 'none'){
              clone.style.display = '';
            }
            parent.appendChild(clone);
            for(var k in obj){
              var temp = document.getElementById(k);
              if(temp){
                self._setJSONData(temp, obj[k]);
                temp.id = temp.id+":rendered";
              }
            }
            clone.id = clone.id+":rendered";
          }
          parent.appendChild(orig);
        }else{
          self._setJSONData(node, o);
        }
      }
    }
  },
  
  removeRender : function(id){
    var removeId = id+":rendered";
    var elem = document.getElementById(removeId);
    while(elem){
	  elem.parentNode.removeChild(elem);
      elem = document.getElementById(removeId);
    }
  }
};