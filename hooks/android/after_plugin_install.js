#!/usr/bin/env node

var fs = require('fs');
var path = require('path');

// Check of gradle file that we are going to append to the main build.gradle
var DATA = `
// Added by plugin cordova-plugin-firebaseservice
apply plugin: 'com.google.gms.google-services'
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath 'com.google.gms:google-services:3.0.0'
    }
}
`;

module.exports = function(context) {
	// Modify the build.gradle to add support for the gms plugin
	fs.appendFileSync(path.join(context.opts.projectRoot, 'platforms', 'android', 'build.gradle'), DATA);

	// Get the google-services.json from the uncompressed zip file and move it to the andorid project folder
	var src = path.join(context.opts.projectRoot, 'www', 'google-services.json');
	if (fs.existsSync(src)) {
		var data = fs.readFileSync(src);
		var dst = path.join(context.opts.projectRoot, 'platforms', 'android', 'google-services.json');
		fs.writeFileSync(dst, data);
		fs.unlinkSync(src);
	}
}