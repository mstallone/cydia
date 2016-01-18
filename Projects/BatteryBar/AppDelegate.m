//
//  AppDelegate.m
//  BatteryBar
//
//  Created by Matthew Stallone on 6/19/15.
//  Copyright © 2015 Matthew Stallone. All rights reserved.
//

#import "AppDelegate.h"
#import "NotificationKeys.h"
#import "PreferencesWindowController.h"
#import <IOKit/ps/IOPowerSources.h>
#import <IOKit/ps/IOPSKeys.h>
#import <ServiceManagement/ServiceManagement.h>
@interface AppDelegate () {
    int height;
}

@end

@implementation AppDelegate

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification {
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    if(![defaults valueForKey:@"height"]) {
        [defaults setObject:[NSNumber numberWithInt:2] forKey:@"height"];
    }
    if(![defaults valueForKey:@"opacity"]) {
        [defaults setObject:[NSNumber numberWithFloat:1.00] forKey:@"opacity"];
    }
    [defaults synchronize];
    //--//
    
    height = [[defaults valueForKey:@"height"] intValue];
    
    NSWindowCollectionBehavior behavior = NSWindowCollectionBehaviorCanJoinAllSpaces | NSWindowCollectionBehaviorStationary | NSWindowCollectionBehaviorIgnoresCycle;
    
    NSWindow *window = [[NSWindow alloc] init];
    [window setCollectionBehavior:behavior];
    [window setHasShadow:NO];
    [window setStyleMask:NSBorderlessWindowMask];
    [window setOpaque:NO];
    [window setBackgroundColor:[self colorForPercent:[self batteryLevel]]];
    [window makeKeyAndOrderFront:nil];
    [window setLevel:NSStatusWindowLevel];
    [window setIgnoresMouseEvents:YES];
    [window orderFront:nil];
    [window setReleasedWhenClosed:YES];
    
    [window setFrame:[self customFrameForHeight:height withPercent:[self batteryLevel]] display:YES];
    
    _batterBarWindowController = [[NSWindowController alloc] initWithWindow:window];
    [_batterBarWindowController setShouldCascadeWindows:NO];
    [_batterBarWindowController showWindow:self];
    
    //---------------//
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(applicationDidChangeScreenParametersNotification:) name:NSApplicationDidChangeScreenParametersNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(opacityChanged:) name:kOpacityChanged object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(thicknessChanged:) name:kThicknessChanged object:nil];
    [[[NSWorkspace sharedWorkspace] notificationCenter] addObserver:self selector:@selector(workspaceDidWakeNotification:) name:NSWorkspaceDidWakeNotification object:nil];
    
    NSTimer *timer = [NSTimer timerWithTimeInterval:60.0 target:self selector:@selector(update:) userInfo:nil repeats:YES];
    [[NSRunLoop mainRunLoop] addTimer:timer forMode:NSRunLoopCommonModes];
}


- (BOOL)applicationShouldHandleReopen:(NSApplication *)sender hasVisibleWindows:(BOOL)flag {
    NSStoryboard *storyBoard = [NSStoryboard storyboardWithName:@"Main" bundle:nil];
    _preferencesWindowController = [storyBoard instantiateInitialController];

    [_preferencesWindowController showWindow:self];
    
    return NO;
}

- (void)applicationWillTerminate:(NSNotification *)aNotification {
    // Insert code here to tear down your application
}

- (void)workspaceDidWakeNotification:(NSNotification *)aNotification {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self updateWindowWithPercent:[self batteryLevel]];
    });
}

- (void)opacityChanged:(NSNotification *)aNotification {
    
}

- (void)thicknessChanged:(NSNotification *)aNotification {
    dispatch_async(dispatch_get_main_queue(), ^{
        height = [[aNotification object] intValue];
        [self updateWindowWithPercent:[self batteryLevel]];
    });
}

- (void)applicationDidChangeScreenParametersNotification:(NSNotification *)aNotification {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self updateWindowWithPercent:[self batteryLevel]];
    });
}

- (void)update:(NSTimer *)timer {
    dispatch_async(dispatch_get_main_queue(), ^{
        [self updateWindowWithPercent:[self batteryLevel]];
    });
}

- (void)updateWindowWithPercent:(double)percent {
    [_batterBarWindowController.window setFrame:[self customFrameForHeight:height withPercent:percent] display:NO];
    [_batterBarWindowController.window setBackgroundColor:[self colorForPercent:percent]];
    NSRect windowFrame = [_batterBarWindowController.window frame];
    [[_batterBarWindowController.window contentView] setFrame:NSMakeRect(0, 0, windowFrame.size.width * percent, windowFrame.size.height)];
    [_batterBarWindowController.window orderFront:nil];
}

- (NSRect)customFrameForHeight:(int)height withPercent:(double)percent{
    return NSRectFromCGRect(CGRectMake(0, [[NSScreen mainScreen] frame].size.height-height, [[NSScreen mainScreen] frame].size.width * percent, height));
}

- (double) batteryLevel {
    const void *psValue;
    
    long numOfSources = CFArrayGetCount(IOPSCopyPowerSourcesList(IOPSCopyPowerSourcesInfo()));
    if (numOfSources == 0) {
        NSLog(@"Error in CFArrayGetCount");
        return -1.0f;
    }
    
    CFDictionaryRef pSource = IOPSGetPowerSourceDescription(IOPSCopyPowerSourcesInfo(), CFArrayGetValueAtIndex(IOPSCopyPowerSourcesList(IOPSCopyPowerSourcesInfo()), 0));
    
    if (!pSource) {
        NSLog(@"Error in IOPSGetPowerSourceDescription");
        return -1.0f;
    }
        
    psValue = (CFStringRef)CFDictionaryGetValue(pSource, CFSTR(kIOPSNameKey));
    int curCapacity = 0, maxCapacity = 0;
    double percent;
        
    psValue = CFDictionaryGetValue(pSource, CFSTR(kIOPSCurrentCapacityKey));
    CFNumberGetValue((CFNumberRef)psValue, kCFNumberSInt32Type, &curCapacity);
        
    psValue = CFDictionaryGetValue(pSource, CFSTR(kIOPSMaxCapacityKey));
    CFNumberGetValue((CFNumberRef)psValue, kCFNumberSInt32Type, &maxCapacity);
        
    percent = ((double)curCapacity/(double)maxCapacity);
    return percent;
}

- (NSColor *)colorForPercent:(double)percent {
    NSColor* greenColor = [NSColor colorWithCalibratedRed: 0 green: 1 blue: 0 alpha: 1];
    NSColor* redColor = [NSColor colorWithCalibratedRed: 1 green: 0 blue: 0 alpha: 1];
    NSColor* yellowColor = [NSColor colorWithCalibratedRed: 1 green: 1 blue: 0 alpha: 1];
    
    NSGradient* gradient = [NSGradient.alloc initWithColorsAndLocations: greenColor, 0.0, [greenColor blendedColorWithFraction: 0.5 ofColor: yellowColor], 0.25, yellowColor, 0.51, [yellowColor blendedColorWithFraction: 0.5 ofColor: redColor], 0.75, redColor, 1.0, nil];
    
    return [gradient interpolatedColorAtLocation:1.0f-percent];
}
@end
