//
//  AppDelegate.m
//  BatteryBarLoginItem
//
//  Created by Matthew Stallone on 6/20/15.
//  Copyright (c) 2015 Matthew Stallone. All rights reserved.
//

#import "AppDelegate.h"

@interface AppDelegate ()

@end

@implementation AppDelegate

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification {
    // Insert code here to initialize your application
    BOOL alreadyRunning = NO;
    BOOL isActive = NO;
    NSArray *running = [[NSWorkspace sharedWorkspace] runningApplications];
    for (NSRunningApplication *app in running) {
        if ([[app bundleIdentifier] isEqualToString:@"com.matthewstallone.BatteryBar"]) {
            alreadyRunning = YES;
            isActive = [app isActive];
        }
    }
    
    if (!alreadyRunning || !isActive) {
        NSString *path = [[NSBundle mainBundle] bundlePath];
        
        NSArray *p = [path pathComponents];
        NSMutableArray *pathComponents = [NSMutableArray arrayWithArray:p];
        NSLog(@"%@", [pathComponents lastObject]);
        [pathComponents removeLastObject];
        [pathComponents removeLastObject];
        [pathComponents removeLastObject];
        [pathComponents addObject:@"MacOS"];
        [pathComponents addObject:@"BatteryBar"];
        NSString *newPath = [NSString pathWithComponents:pathComponents];
        [[NSWorkspace sharedWorkspace] launchApplication:newPath];
    }
    [NSApp terminate:nil];
}

- (void)applicationWillTerminate:(NSNotification *)aNotification {
    // Insert code here to tear down your application
}

@end
