//
//  GameScene.m
//  Don't Fall
//
//  Created by Matthew Stallone on 12/22/14.
//  Copyright (c) 2014 Matthew Stallone. All rights reserved.
//

#import "GameScene.h"

@implementation GameScene

-(void)didMoveToView:(SKView *)view {
    self.scaleMode = SKSceneScaleModeAspectFit;
    [self.physicsWorld setContactDelegate:self];
    
    platforms = [[NSMutableArray alloc] init];
    scoreLabel = (SKLabelNode *)[self childNodeWithName:@"Score"];
    contactTimeCounter = CFAbsoluteTimeGetCurrent();
    scoreCount = 0;
    currentState = Start;
    
    [self enumerateChildNodesWithName:@"Platform" usingBlock:^(SKNode *node, BOOL *stop) {
        node.physicsBody.contactTestBitMask = 4294967294;
        [platforms addObject:node];
    }];
    
    player = (SKSpriteNode *)[self childNodeWithName:@"Player"];
    
    effect = [SKSpriteNode spriteNodeWithColor:[UIColor blackColor] size:CGSizeMake(1100, 1600)];
    effect.position = CGPointMake(450, 800);
    effect.alpha = 0.80f;
    effect.zPosition = 1;
    [self addChild:effect];
    
    motionManager = [[CMMotionManager alloc] init];
    if(motionManager.deviceMotionAvailable) [motionManager startDeviceMotionUpdates];
    else [[[UIAlertView alloc] initWithTitle:@"Device motion is not available" message:nil delegate:nil cancelButtonTitle:nil otherButtonTitles: nil] show];
    
    #warning Remove upon distribution
    [self changeEffect:(currentState = 1)];
}

-(void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    SKNode *node = [platforms objectAtIndex:3];
    [node runAction:[SKAction moveToY:-400 duration:1] completion:^{ //Edit for scale
        [node runAction:[SKAction moveToY:0 duration:1]];
    }];
}

-(void)didBeginContact:(SKPhysicsContact *)contact {
    uint32_t collision = (contact.bodyA.categoryBitMask | contact.bodyB.categoryBitMask);
    
    if (collision == (4294967294 | 4294967295) && CFAbsoluteTimeGetCurrent() - contactTimeCounter > 0.5) {
        [self updateScore];
        contactTimeCounter = CFAbsoluteTimeGetCurrent();
    }
}

-(void)update:(CFTimeInterval)currentTime {
    NSTimeInterval delta;
    if (lastTime != 0)  delta = currentTime - lastTime;
    secondCounter += delta;
    lastTime = currentTime;
    
    if (scoreCount > 4) {
        [self enumerateChildNodesWithName:@"Platform" usingBlock:^(SKNode *node, BOOL *stop) {
            [node runAction:[SKAction moveByX:-1 y:0 duration:0.0]];
            if (node.position.x <= -75) {
                node.position = CGPointMake(1125, node.position.y);
            }
        }];
    }
    
    if (secondCounter > 1) {
        secondCounter = 0;
        
        //[self updateScore];
    }
    
    double tilt = -asin(2*(motionManager.deviceMotion.attitude.quaternion.x * motionManager.deviceMotion.attitude.quaternion.z - motionManager.deviceMotion.attitude.quaternion.w * motionManager.deviceMotion.attitude.quaternion.y));
    [player.physicsBody applyImpulse:CGVectorMake(15 * tilt, 0)];
    
    if(player.position.x <= 50) {
        player.position = CGPointMake(50, player.position.y);
        if (tilt < 0) [player.physicsBody setVelocity:CGVectorMake(0, player.physicsBody.velocity.dy)];
    }
    if(player.position.x >= self.frame.size.width - 50) {
        player.position = CGPointMake(self.frame.size.width - 50, player.position.y);
        if (tilt > 0) [player.physicsBody setVelocity:CGVectorMake(0, player.physicsBody.velocity.dy)];
    }
}



-(void)changeEffect:(State)iState{
    switch (iState) {
        case Start:
            [effect runAction:[SKAction fadeAlphaTo:0.8f duration:0]];
            break;
        case Playing:
            [effect runAction:[SKAction fadeAlphaTo:0.0f duration:1]];
            break;
        case Pause:
            [effect runAction:[SKAction fadeAlphaTo:0.8f duration:1]];
            break;
        default:
            break;
    }
}

-(void)updateScore{
    scoreCount++;
    [scoreLabel setText:[NSString stringWithFormat:@"%li", scoreCount]];
}

@end
