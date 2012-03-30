//
//  ADMasterViewController.m
//  aloharDemo
//
//  Created by Sam Warmuth on 2/23/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import "ADMasterViewController.h"
#import "ADRealTimeViewController.h"
#import "ADAppDelegate.h"
#import <Alohar/Alohar.h>

@interface ADMasterViewController () {
    NSMutableArray *_objects;
}

@end

@implementation ADMasterViewController

- (void)awakeFromNib
{
    [super awakeFromNib];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

- (IBAction)toggleMonitoringUser:(UIBarButtonItem *)sender
{
    if ([Alohar monitoringUser]){
        [sender setImage:[UIImage imageNamed:@"UIButtonBarPlay"]];
        [Alohar stopMonitoringUser];
    } else {
        [sender setImage:[UIImage imageNamed:@"UIButtonBarPause"]];
        [Alohar startMonitoringUser];
    }
}


#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}



- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return NO;
}


@end
