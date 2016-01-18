//
//  PreferencesWindowController.m
//  
//
//  Created by Matthew Stallone on 6/20/15.
//
//

#import "PreferencesWindowController.h"
#import "NotificationKeys.h"

@interface PreferencesWindowController ()

@end

@implementation PreferencesWindowController

- (void)windowDidLoad {
    [super windowDidLoad];
    [self.window setOpaque:NO];
    [self.window setBackgroundColor:[NSColor colorWithWhite:0.95 alpha:0.97]];
    [[self.window standardWindowButton:NSWindowMiniaturizeButton] setHidden:YES];
    [[self.window standardWindowButton:NSWindowZoomButton] setHidden:YES];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(segmentValueChanged:) name:kSegmentValueChanged object:nil];
}

- (void)segmentValueChanged:(NSNotification *)aNotification {
    if ([[aNotification object] isEqualTo:[NSNumber numberWithLong:0]]) {
        [self setContentViewController:[[NSStoryboard storyboardWithName:@"Main" bundle:nil] instantiateControllerWithIdentifier:@"preferences"]];
        [[self window] setTitle:@"Preferences"];
    }else {
        [self setContentViewController:[[NSStoryboard storyboardWithName:@"Main" bundle:nil] instantiateControllerWithIdentifier:@"about"]];
        [[self window] setTitle:@"About"];
    }
}

@end
