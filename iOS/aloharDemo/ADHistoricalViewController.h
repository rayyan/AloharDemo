//
//  ADHistoricalViewController.h
//  aloharDemo
//
//  Created by Sam Warmuth on 3/12/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Alohar/Alohar.h>

@interface ADHistoricalViewController : UIViewController <UITableViewDelegate, UITableViewDataSource, ALRequestDelegate>
@property (nonatomic, weak) IBOutlet UITableView *recentStaysTableView, *allPlacesTableView;
@property (nonatomic, strong) NSMutableArray *places, *userstays;

@property (weak, nonatomic) IBOutlet UILabel *placeCountLabel;
@property (weak, nonatomic) IBOutlet UILabel *stayCountLabel;

- (IBAction)refresh:(id)sender;

@end
