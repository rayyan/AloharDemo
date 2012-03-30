//
//  ADEventTableViewCell.m
//  aloharDemo
//
//  Created by Jianming Zhou on 3/26/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import "ADEventTableViewCell.h"

@implementation ADEventTableViewCell

@synthesize eventLabel, detailLabel, timeLabel, durationLabel;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
