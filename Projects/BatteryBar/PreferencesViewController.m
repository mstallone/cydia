//
//  ViewController.m
//  BatteryBar
//
//  Created by Matthew Stallone on 6/19/15.
//  Copyright © 2015 Matthew Stallone. All rights reserved.
//

#import "PreferencesViewController.h"
#import <ServiceManagement/ServiceManagement.h>
#import "NotificationKeys.h"
#import "PreferencesTextField.h"

@interface PreferencesViewController ()

@end

@implementation PreferencesViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [_startAtLoginCheckBox setState:[self appIsPresentInLoginItems]];
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [_thicknessTextField setStringValue:[NSString stringWithFormat:@"%i", [[defaults valueForKey:@"height"] intValue]]];
    [_opacityTextField setStringValue:[NSString stringWithFormat:@"%.02f", [[defaults valueForKey:@"opacity"] floatValue]]];
    [_thicknessStepper setMaxValue:[[NSScreen mainScreen] frame].size.height];
}

- (IBAction)generalValueChanged:(id)sender {
    if ([[sender title] isEqualToString:@"Start at login"]){
        if (!SMLoginItemSetEnabled ((CFStringRef)@"com.matthewstallone.BatteryBarLoginItem", [sender state])) {
            //--//ERROR//-//
        }
    }else {
        NSLog(@"Menu bar item");
    }
}

- (void)controlTextDidChange:(NSNotification *)aNotification {
    [self checkValues:aNotification];
}

- (void)controlTextDidEndEditing:(NSNotification *)aNotification {
    [self checkValues:aNotification];
}

-(void) checkValues:(NSNotification *)aNotification {
    if ([[[aNotification object] identifier] isEqualToString:@"opacity"]) {
        if (![self isDecimal:[[aNotification object] stringValue]] || [[[aNotification object] stringValue] floatValue] > 1.0 || [[[aNotification object] stringValue] floatValue] < 0.0) {
            NSAlert *alert = [[NSAlert alloc] init];
            [alert setMessageText:@"Invalid Opacity Value"];
            [alert addButtonWithTitle:@"Okay"];
            [alert setInformativeText:@"The opacity value has to an number be between 0.0 and 1.0"];
            [alert runModal];
        } else {
            [_opacityStepper setDoubleValue:[[[aNotification object] stringValue] doubleValue]];
            //--//Send new opacity//--//
            [[NSNotificationCenter defaultCenter] postNotificationName:kOpacityChanged object:nil];
        }
    }
    if ([[[aNotification object] identifier] isEqualToString:@"thickness"]) {
        if (![self isDecimal:[[aNotification object] stringValue]] || [[[aNotification object] stringValue] floatValue] < 0.0 || [[[aNotification object] stringValue] floatValue] > [[NSScreen mainScreen] frame].size.height) {
            NSAlert *alert = [[NSAlert alloc] init];
            [alert setMessageText:@"Invalid Thickness Value"];
            [alert addButtonWithTitle:@"Okay"];
            [alert setInformativeText:[NSString stringWithFormat:@"The opacity value has to an number be between 0.0 and %0.1f", [[NSScreen mainScreen] frame].size.width]];
            [alert runModal];
        } else {
            [_thicknessStepper setDoubleValue:[[[aNotification object] stringValue] intValue]];
            //--//Send new Thickness//--//
            [[NSNotificationCenter defaultCenter] postNotificationName:kThicknessChanged object:[NSNumber numberWithInt:[[[aNotification object] stringValue] intValue]]];
        }
    }
}

- (IBAction)segmentValueChanged:(id)sender {
    [[NSNotificationCenter defaultCenter] postNotificationName:kSegmentValueChanged object:[NSNumber numberWithLong:[sender selectedSegment]]];
}

-(BOOL)appIsPresentInLoginItems
{
    NSString *bundleID = @"com.matthewstallone.BatteryBarLoginItem";
    NSArray * jobDicts = nil;
    jobDicts = (__bridge NSArray *)SMCopyAllJobDictionaries( kSMDomainUserLaunchd );
    // Note: Sandbox issue when using SMJobCopyDictionary()
    
    if ( (jobDicts != nil) && [jobDicts count] > 0 ) {
        
        BOOL bOnDemand = NO;
        
        for ( NSDictionary * job in jobDicts ) {
            
            if ( [bundleID isEqualToString:[job objectForKey:@"Label"]] ) {
                bOnDemand = [[job objectForKey:@"OnDemand"] boolValue];
                break;
            }
        }
        
        CFRelease((CFDictionaryRef)jobDicts); jobDicts = nil;
        return bOnDemand;
        
    }
    return NO;
}

-(BOOL)isDecimal:(NSString *)input {
    NSMutableCharacterSet *digitsAndDots = [NSMutableCharacterSet decimalDigitCharacterSet];
    [digitsAndDots addCharactersInString:@"."];
    [digitsAndDots addCharactersInString:@","];
    return ([input rangeOfCharacterFromSet:[digitsAndDots invertedSet]].location == NSNotFound);
}

@end
