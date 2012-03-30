//
//  ADEventHistoryTableViewController.h
//  aloharDemo
//
//  Created by Sam Warmuth on 3/21/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MessageUI/MFMailComposeViewController.h>

@interface ADEventHistoryTableViewController : UITableViewController <MFMailComposeViewControllerDelegate>

- (IBAction)emailLog:(id)sender;
@end
