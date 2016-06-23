#import <Cordova/CDVPlugin.h>
@import UIKit;
@import FirebaseRemoteConfig;

@interface CDVFBConfig : CDVPlugin
@property (nonatomic, strong) FIRRemoteConfig *remoteConfig;

- (void)init:(CDVInvokedUrlCommand*)command;
- (void)fetch:(CDVInvokedUrlCommand*)command;
- (void)setDefaults:(CDVInvokedUrlCommand*)command;
- (void)getLong:(CDVInvokedUrlCommand*)command;
- (void)getByteArray:(CDVInvokedUrlCommand*)command;
- (void)getString:(CDVInvokedUrlCommand*)command;
- (void)getBoolean:(CDVInvokedUrlCommand*)command;
- (void)getDouble:(CDVInvokedUrlCommand*)command;
@end
