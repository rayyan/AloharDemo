//
//  ADAppDelegate.m
//  aloharDemo
//
//  Created by Sam Warmuth on 2/23/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import "ADAppDelegate.h"

@implementation ADAppDelegate
@synthesize window = _window;


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    
    //To set a manual uid, use the method below to set the AloharDemoUserID to a custom ID
    //NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    //[defaults setObject:@"27f4547b2c586c809a3887658b5270a488184565" forKey:@"AloharDemoUserID"];
    
    NSString *userToken = [[NSUserDefaults standardUserDefaults] stringForKey:@"AloharDemoUserID"];
    if (userToken == nil || userToken.length == 0){
        [Alohar registerWithAppID:@"10" andAPIKey:@"2a2b3446ebd2af25633a9f600c1d8e8aa1d7b463" withDelegate:self];
    }else{
        [Alohar authenticateWithAppID:@"10" andAPIKey:@"2a2b3446ebd2af25633a9f600c1d8e8aa1d7b463" andUserID:userToken withDelegate:self];
    }
        
    return YES;
}

- (void)aloharDidLogin:(NSString *)userToken
{
    NSLog(@"Logged in! Token: %@", userToken);
    [[NSUserDefaults standardUserDefaults] setObject:userToken forKey:@"AloharDemoUserID"];
    [Alohar startMonitoringUser];
    
}

- (void)aloharDidFailWithError:(NSError *)error
{
    NSLog(@"Sorry, there was an error with alohar: %@", error);
}

							
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

@end
