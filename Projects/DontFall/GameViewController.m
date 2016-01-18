//
//  GameViewController.m
//  Don't Fall
//
//  Created by Matthew Stallone on 12/22/14.
//  Copyright (c) 2014 Matthew Stallone. All rights reserved.
//

#import "GameViewController.h"
#import "GameScene.h"

@implementation SKScene (Unarchive)

+ (instancetype)unarchiveFromFile:(NSString *)file {
    /* Retrieve scene file path from the application bundle */
    NSString *nodePath = [[NSBundle mainBundle] pathForResource:file ofType:@"sks"];
    /* Unarchive the file to an SKScene object */
    NSData *data = [NSData dataWithContentsOfFile:nodePath options:NSDataReadingMappedIfSafe error:nil];
    NSKeyedUnarchiver *arch = [[NSKeyedUnarchiver alloc] initForReadingWithData:data];
    [arch setClass:self forClassName:@"SKScene"];
    SKScene *scene = [arch decodeObjectForKey:NSKeyedArchiveRootObjectKey];
    [arch finishDecoding];
    
    return scene;
}

@end

@implementation GameViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    SKView * skView = (SKView *)self.view;
    skView.showsFPS = YES;
    skView.showsNodeCount = YES;
    skView.ignoresSiblingOrder = YES;
    
    // Create and configure the scene.
    GameScene *scene;
    if ((double)([[UIScreen mainScreen] preferredMode].size.width/[[UIScreen mainScreen] preferredMode].size.height) < 0.6) scene = [GameScene unarchiveFromFile:@"GameScenePillarbox"];
    else if ((double)([[UIScreen mainScreen] preferredMode].size.width/[[UIScreen mainScreen] preferredMode].size.height) > 0.7) scene = [GameScene unarchiveFromFile:@"GameScenePad"];
    else scene = [GameScene unarchiveFromFile:@"GameSceneLetterbox"];
    
    //scene.scaleMode = SKSceneScaleModeAspectFill;

    adView = [[ADBannerView alloc] initWithFrame:CGRectZero];
    adView.delegate = self;
    [adView setFrame:CGRectMake(0, self.view.frame.size.height - adView.frame.size.height, adView.frame.size.width, adView.frame.size.height)];
    NSLog(@"%@", NSStringFromCGRect(adView.frame));
    
    #warning Uncomment upon distribution
    //[self.view addSubview:adView];
    
    [skView presentScene:scene];
}

- (BOOL)shouldAutorotate {
    return NO;
}

- (NSUInteger)supportedInterfaceOrientations {
    return UIInterfaceOrientationPortrait;
    /*
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return UIInterfaceOrientationMaskAllButUpsideDown;
    } else {
        return UIInterfaceOrientationMaskAll;
    }*/
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

- (BOOL)prefersStatusBarHidden {
    return YES;
}

@end
