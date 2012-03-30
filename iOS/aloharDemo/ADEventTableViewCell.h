//
//  ADEventTableViewCell.h
//  aloharDemo
//
//  Created by Jianming Zhou on 3/26/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ADEventTableViewCell : UITableViewCell

@property (nonatomic, weak) IBOutlet UILabel *eventLabel;
@property (nonatomic, weak) IBOutlet UILabel *timeLabel;
@property (nonatomic, weak) IBOutlet UILabel *detailLabel;
@property (nonatomic, weak) IBOutlet UILabel *durationLabel;

@end
