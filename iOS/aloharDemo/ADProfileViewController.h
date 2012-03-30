//
//  ADProfileViewController.h
//  aloharDemo
//
//  Created by Jianming Zhou on 3/21/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ADProfileViewController : UITableViewController

@property (weak, nonatomic) IBOutlet UILabel *uidLabel;
@property (weak, nonatomic) IBOutlet UILabel *userIDLabel;
@property (weak, nonatomic) IBOutlet UILabel *tokenLabel;
@property (weak, nonatomic) IBOutlet UILabel *versionLabel;

@end
