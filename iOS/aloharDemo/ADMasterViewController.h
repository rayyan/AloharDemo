//
//  ADMasterViewController.h
//  aloharDemo
//
//  Created by Sam Warmuth on 2/23/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Alohar/ALMotionState.h>
#import <Alohar/Alohar.h>
#import "ADRealTimeViewController.h"

@interface ADMasterViewController : UITableViewController <ALRequestDelegate>

- (IBAction)toggleMonitoringUser:(UIBarButtonItem *)sender;

@end
