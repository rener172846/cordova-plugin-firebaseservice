#import "CDVFBConfig.h"

@implementation CDVFBConfig


- (void)init:(CDVInvokedUrlCommand*)command
{
    self.remoteConfig = [FIRRemoteConfig remoteConfig];
    FIRRemoteConfigSettings *remoteConfigSettings = [[FIRRemoteConfigSettings alloc] initWithDeveloperModeEnabled:YES];
    self.remoteConfig.configSettings = remoteConfigSettings;
}

- (void)fetch:(CDVInvokedUrlCommand*)command
{
    long expirationDuration = 3600;
    // If in developer mode cacheExpiration is set to 0 so each fetch will retrieve values from
    // the server.
    if (self.remoteConfig.configSettings.isDeveloperModeEnabled) {
        expirationDuration = 0;
    }
    
    // [START fetch_config_with_callback]
    // cacheExpirationSeconds is set to cacheExpiration here, indicating that any previously
    // fetched and cached config would be considered expired because it would have been fetched
    // more than cacheExpiration seconds ago. Thus the next fetch would go to the server unless
    // throttling is in progress. The default expiration duration is 43200 (12 hours).
    [self.remoteConfig fetchWithExpirationDuration:expirationDuration completionHandler:^(FIRRemoteConfigFetchStatus status, NSError *error) {
        if (status == FIRRemoteConfigFetchStatusSuccess) {
            NSLog(@"Config fetched!");
            [self.remoteConfig activateFetched];
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } else {
            NSLog(@"Config not fetched");
            CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:error.localizedDescription];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
    // [END fetch_config_with_callback]
}

- (void)setDefaults:(CDVInvokedUrlCommand*)command
{
    if (command.arguments.count > 1) {
        NSDictionary *jsonObj = command.arguments[0];
        NSString* key = [command.arguments objectAtIndex:1];
        [self.remoteConfig setDefaults:jsonObj namespace:key];
    } else {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Invalid number of parameters"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void)getLong:(CDVInvokedUrlCommand*)command
{
    if (command.arguments.count > 1) {
        NSString* key = [command.arguments objectAtIndex:0];
        int value = (int)self.remoteConfig[key].numberValue.longValue;
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:value];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Invalid number of parameters"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void)getByteArray:(CDVInvokedUrlCommand*)command
{
    if (command.arguments.count > 1) {
        NSString* key = [command.arguments objectAtIndex:0];
        int value = (int)self.remoteConfig[key].stringValue;
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:value];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Invalid number of parameters"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void)getString:(CDVInvokedUrlCommand*)command
{
    if (command.arguments.count > 1) {
        NSString* key = [command.arguments objectAtIndex:0];
        NSString *value = self.remoteConfig[key].stringValue;
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:value];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Invalid number of parameters"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void)getBoolean:(CDVInvokedUrlCommand*)command
{
    if (command.arguments.count > 1) {
        NSString* key = [command.arguments objectAtIndex:0];
        BOOL value = self.remoteConfig[key].boolValue;
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsBool:value];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Invalid number of parameters"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

- (void)getDouble:(CDVInvokedUrlCommand*)command
{
    if (command.arguments.count > 1) {
        NSString* key = [command.arguments objectAtIndex:0];
        double value = self.remoteConfig[key].numberValue.doubleValue;
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDouble:value];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    } else {
        CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"Invalid number of parameters"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }
}

@end
