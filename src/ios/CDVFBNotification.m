#import "CDVFBNotification.h"
@import FirebaseInstanceID;
@import FirebaseMessaging;

@implementation CDVFBNotification

- (void)init:(CDVInvokedUrlCommand*)command
{
    // [START get_iid_token]
    NSString *token = [[FIRInstanceID instanceID] token];
    NSLog(@"InstanceID token: %@", token);
    // [END get_iid_token]
}

@end