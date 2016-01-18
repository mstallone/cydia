//
//  PreferencesTextField.m
//  
//
//  Created by Matthew Stallone on 6/21/15.
//
//

#import "PreferencesTextField.h"

@implementation PreferencesTextField

- (void)drawRect:(NSRect)dirtyRect {
    NSPoint origin = { 0.0,0.0 };
    NSRect rect;
    rect.origin = origin;
    rect.size.width  = [self bounds].size.width;
    rect.size.height = [self bounds].size.height;
    
    NSBezierPath * path;
    path = [NSBezierPath bezierPathWithRect:rect];
    [path setLineWidth:2];
    [[NSColor colorWithCalibratedWhite:1.0 alpha:0.394] set];
    [path fill];
    [[NSColor colorWithCalibratedRed:0.276 green:0.276 blue:0.276 alpha:0.90] set];
    [path stroke];
    
    rect.origin = CGPointMake(4.75, 2.93);
    if([[self window] firstResponder] != [self currentEditor])  [[self attributedStringValue] drawInRect:rect];
}

@end
