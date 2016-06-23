/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/

var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');

/**
 * @exports FBRemoteConfig
 */
var fbremoteconfig = {

  defaultroot: "configns:firebase",

  init: function(successCallback, errorCallback, options) {
    exec(successCallback, errorCallback, "FBRemoteConfig", "init", []);
  },

  fetch: function(successCallback, errorCallback, options) {
    exec(successCallback, errorCallback, "FBRemoteConfig", "fetch", []);
  },

  setDefaults: function(successCallback, errorCallback, options) {
    argscheck.checkArgs('fFO', 'FBRemoteConfig.setDefaults', arguments);
    options = options || {};
    var getValue = argscheck.getValue;

    var value = getValue(options.value, {});
    var root = getValue(options.root, this.defaultroot);

    var args = [value, root];

    exec(successCallback, errorCallback, "FBRemoteConfig", "setDefaults", args);
  },

  getLong: function(successCallback, errorCallback, options) {
    argscheck.checkArgs('fFO', 'FBRemoteConfig.getLong', arguments);
    options = options || {};
    var getValue = argscheck.getValue;

    var key = getValue(options.key);
    var root = getValue(options.root, this.defaultroot);

    var args = [key, root];

    exec(successCallback, errorCallback, "FBRemoteConfig", "getLong", args);
  },

  getByteArray: function(successCallback, errorCallback, options) {
    argscheck.checkArgs('fFO', 'FBRemoteConfig.getByteArray', arguments);
    options = options || {};
    var getValue = argscheck.getValue;

    var key = getValue(options.key);
    var root = getValue(options.root, this.defaultroot);

    var args = [key, root];

    exec(successCallback, errorCallback, "FBRemoteConfig", "getByteArray", args);
  },

  getString: function(successCallback, errorCallback, options) {
    argscheck.checkArgs('fFO', 'FBRemoteConfig.getString', arguments);
    options = options || {};
    var getValue = argscheck.getValue;

    var key = getValue(options.key);
    var root = getValue(options.root, this.defaultroot);

    var args = [key, root];

    exec(successCallback, errorCallback, "FBRemoteConfig", "getString", args);
  },

  getBoolean: function(successCallback, errorCallback, options) {
    argscheck.checkArgs('fFO', 'FBRemoteConfig.getBoolean', arguments);
    options = options || {};
    var getValue = argscheck.getValue;

    var key = getValue(options.key);
    var root = getValue(options.root, this.defaultroot);

    var args = [key, root];

    exec(successCallback, errorCallback, "FBRemoteConfig", "getBoolean", args);
  },

  getDouble: function(successCallback, errorCallback, options) {
    argscheck.checkArgs('fFO', 'FBRemoteConfig.getDouble', arguments);
    options = options || {};
    var getValue = argscheck.getValue;

    var key = getValue(options.key);
    var root = getValue(options.root, this.defaultroot);

    var args = [key, root];

    exec(successCallback, errorCallback, "FBRemoteConfig", "getDouble", args);
  }

};

module.exports = fbremoteconfig;
