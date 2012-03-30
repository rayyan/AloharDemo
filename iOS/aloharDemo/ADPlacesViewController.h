//
//  ADPlacesViewController.h
//  aloharDemo
//
//  Created by Jianming Zhou on 3/20/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Alohar/ALUserStay.h>
#import <Alohar/Alohar.h>

@interface ADPlacesViewController : UITableViewController<ALRequestDelegate>

@property (nonatomic, strong) NSMutableArray *candidates;
@property (nonatomic, strong) ALUserStay *stay;

@end
