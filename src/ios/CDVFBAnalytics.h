#import <Cordova/CDVPlugin.h>
@import UIKit;

@interface CDVFBAnalytics : CDVPlugin
- (void)init:(CDVInvokedUrlCommand*)command;
- (void)setUserProperty:(CDVInvokedUrlCommand*)command;
- (void)logEvent:(CDVInvokedUrlCommand*)command;
@end
