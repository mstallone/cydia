//
//  ViewController.h
//  BatteryBar
//
//  Created by Matthew Stallone on 6/19/15.
//  Copyright © 2015 Matthew Stallone. All rights reserved.
//

#import <Cocoa/Cocoa.h>

@interface PreferencesViewController : NSViewController <NSTextViewDelegate>

@property (weak) IBOutlet NSButton *startAtLoginCheckBox;

//--//Appearance//--//
@property (weak) IBOutlet NSTextFieldCell *thicknessTextField;
@property (weak) IBOutlet NSTextField *opacityTextField;
@property (weak) IBOutlet NSStepper *thicknessStepper;
@property (weak) IBOutlet NSStepper *opacityStepper;

@end
