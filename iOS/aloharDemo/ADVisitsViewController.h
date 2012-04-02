//
//  ADVisitsViewController.h
//  aloharDemo
//
//  Created by Jianming Zhou on 3/20/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <UIKit/UIKit.h>

#import <Alohar/Alohar.h>
#import <Alohar/ALPlace.h>

@interface ADVisitsViewController : UITableViewController <ALRequestDelegate>

@property (nonatomic, strong) NSMutableArray *visits;
@property (nonatomic, strong) ALPlace *place;
@end
