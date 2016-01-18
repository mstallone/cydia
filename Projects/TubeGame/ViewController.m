//
//  ViewController.m
//  Tube
//
//  Created by Matthew Stallone on 9/27/15.
//  Copyright © 2015 Matthew Stallone. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    SCNView *view = (SCNView *)self.view;
    view.scene = [SCNScene scene];
    view.backgroundColor = [UIColor blackColor];
    view.delegate = self;
    view.scene.physicsWorld.contactDelegate = self;
    SCNCamera *camera = [SCNCamera camera];
    camera.automaticallyAdjustsZRange = YES;
    SCNNode *cameraNode = [SCNNode node];
    cameraNode.position = SCNVector3Make(0, 98, -10);
    cameraNode.camera = camera;
    cameraNode.transform = SCNMatrix4Mult(SCNMatrix4MakeRotation([self degToRad:180], 0, 0, 1), cameraNode.transform);
    
    SCNNode *playerNode = [SCNNode nodeWithGeometry:[SCNBox boxWithWidth:1 height:1 length:1 chamferRadius:0]];
    playerNode.position = SCNVector3Make(0, 98, -20);
    [view.scene.rootNode addChildNode:playerNode];
    playerNode.physicsBody = [SCNPhysicsBody bodyWithType:SCNPhysicsBodyTypeKinematic shape:[SCNPhysicsShape shapeWithGeometry:playerNode.geometry options:nil]];
    playerNode.physicsBody.categoryBitMask = 8;
    playerNode.physicsBody.collisionBitMask = 4;
    playerNode.physicsField = [SCNPhysicsField customFieldWithEvaluationBlock:^SCNVector3(SCNVector3 position, SCNVector3 velocity, float mass, float charge, NSTimeInterval time) {
        NSLog(@"ouch");
        return position;
    }];
    playerNode.physicsField.categoryBitMask = 4;
    playerNode.physicsField.usesEllipsoidalExtent = YES;
    playerNode.physicsField.halfExtent = SCNVector3Make(2, 2, 2);
    
    SCNNode *cameraOrbit = [SCNNode node];
    [cameraOrbit addChildNode:cameraNode];
    [view.scene.rootNode addChildNode:cameraOrbit];
    
    float radius = 100, angle = 0, angleIncrement = (float)(M_PI*2.0)/(float)(16.0);
    
    SCNNode *node = [SCNNode node];
    
    for (int i = 0; i < 16; i++) {
        SCNTube *tube = [SCNTube tubeWithInnerRadius:5.5 outerRadius:6 height:1];
        tube.firstMaterial.diffuse.contents = [UIColor yellowColor];
        SCNNode *tubeNode = [SCNNode nodeWithGeometry:tube];
        [tubeNode setPosition:SCNVector3Make(0, radius*sin(angle), radius*cos(angle))];
        [node addChildNode:tubeNode];
        
        SCNBox *box = [SCNBox boxWithWidth:11 height:1 length:[self randomCGFloat]*5 chamferRadius:0];
        box.firstMaterial.diffuse.contents = [UIColor yellowColor];
        SCNNode *boxNode = [SCNNode nodeWithGeometry:box];
        [boxNode setEulerAngles:SCNVector3Make(0, [self randomCGFloat]*5, 0)];
        boxNode.physicsBody = [SCNPhysicsBody bodyWithType:SCNPhysicsBodyTypeKinematic shape:[SCNPhysicsShape shapeWithGeometry:boxNode.geometry options:nil]];
        boxNode.physicsBody.categoryBitMask = 4;
        boxNode.physicsBody.collisionBitMask = 8;
        [tubeNode addChildNode:boxNode];
        
        [CATransaction begin]; {
            [CATransaction setCompletionBlock:^{
                [boxNode setEulerAngles:SCNVector3Make(0, boxNode.eulerAngles.y + M_PI, 0)];
            }];
            CABasicAnimation *animate = [CABasicAnimation animationWithKeyPath:@"eulerAngles"];
            animate.fromValue = [NSValue valueWithSCNVector3:boxNode.eulerAngles];
            animate.toValue = [NSValue valueWithSCNVector3:SCNVector3Make(0, boxNode.eulerAngles.y + M_PI, 0)];
            animate.duration = 9;
            animate.repeatCount = INFINITY;
            [boxNode addAnimation:animate forKey:@"Test"];
        } [CATransaction commit];
        
        
        SCNLookAtConstraint *target = [SCNLookAtConstraint lookAtConstraintWithTarget:[SCNNode node]];
        tubeNode.constraints = @[target];
        
        angle += angleIncrement;
    }
    
    [view.scene.rootNode addChildNode:node];
    [CATransaction begin]; {
        [CATransaction setCompletionBlock:^{
            [node setEulerAngles:SCNVector3Make(60, 0, 0)];
        }];
        CABasicAnimation *animate = [CABasicAnimation animationWithKeyPath:@"eulerAngles"];
        animate.fromValue = [NSValue valueWithSCNVector3:SCNVector3Zero];
        animate.toValue = [NSValue valueWithSCNVector3:SCNVector3Make(60, 0, 0)];
        animate.duration = 180;
        animate.repeatCount = INFINITY;
        [node addAnimation:animate forKey:@"Test"];
    } [CATransaction commit];

    view.autoenablesDefaultLighting = true;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)physicsWorld:(SCNPhysicsWorld *)world didBeginContact:(SCNPhysicsContact *)contact{
    NSLog(@"ouch");
}

-(CGFloat)randomCGFloat {
    return (CGFloat)arc4random() / (CGFloat)UINT32_MAX;
}

-(float) degToRad:(float) deg {
    return deg / 180 * (float)M_PI;
}

@end
