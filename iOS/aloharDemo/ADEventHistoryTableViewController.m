//
//  ADEventHistoryTableViewController.m
//  aloharDemo
//
//  Created by Sam Warmuth on 3/21/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import "ADEventHistoryTableViewController.h"
#import <Alohar/Alohar.h>
#import <Alohar/ALUserStay.h>
#import "ADEventTableViewCell.h"

@interface ADEventHistoryTableViewController ()

@end

@implementation ADEventHistoryTableViewController

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    

    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [Alohar userStayLocationHistory].count;
}
- (void)viewWillAppear:(BOOL)animated
{
    NSLog(@"Event history: %@", [Alohar userStayLocationHistory]);

    [self.tableView reloadData];
    [super viewWillAppear:animated];
    
    
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"eventCell";
    ADEventTableViewCell *cell = (ADEventTableViewCell *)[tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[ADEventTableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }  
    NSDictionary *event = [[Alohar userStayLocationHistory] objectAtIndex:indexPath.row];
    static NSDateFormatter *dateFormatter;
    if (dateFormatter == nil) {
        dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"MMM d h:mma"];
    }

    NSLog(@"Configuring cell: %@", event);
    NSDate *d = [[NSDate alloc] initWithTimeIntervalSince1970:[[event valueForKey:@"timestamp"] intValue]];
    cell.eventLabel.text = [event valueForKey:@"type"];
    cell.timeLabel.text = [NSString stringWithFormat:@"%@", [dateFormatter stringFromDate:d]];
    if ([[event valueForKey:@"type"] isEqualToString:@"Userstay"]){
        ALUserStay *stay = [event valueForKey:@"stay"];
        cell.detailLabel.text = stay.selectedPlace.name;
        NSDate *startTime = [NSDate dateWithTimeIntervalSince1970:stay.startTime];
        NSDate *endTime = [NSDate dateWithTimeIntervalSince1970:stay.endTime];
        cell.durationLabel.text = [NSString stringWithFormat:@"%@ to %@", [dateFormatter stringFromDate:startTime], [dateFormatter stringFromDate:endTime]];
    }else{
        CLLocation *loc = (CLLocation *)[event valueForKey:@"location"];
        cell.detailLabel.text = [NSString stringWithFormat:@"(%f,%f)", loc.coordinate.latitude, loc.coordinate.longitude];
        cell.durationLabel.text = @"";
    }
    
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}

- (IBAction)emailLog:(id)sender
{
    NSMutableString *emailBody = [[NSMutableString alloc] initWithCapacity:0]; 
    NSArray *history = [Alohar userStayLocationHistory];
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"d h:mma"];
    
    for (NSDictionary *event in history) {
        NSDate *d = [[NSDate alloc] initWithTimeIntervalSince1970:[[event valueForKey:@"timestamp"] intValue]];
        NSString *time = [NSString stringWithFormat:@"%@", [dateFormatter stringFromDate:d]];

        if ([[event valueForKey:@"type"] isEqualToString:@"Userstay"]){
            ALUserStay *stay = [event valueForKey:@"stay"];
            [emailBody appendFormat:@"%@\r\n%@\r\n%@", [event valueForKey:@"type"], time, stay.description];
        }else{
            [emailBody appendFormat:@"%@\r\n%@\r\n", [event valueForKey:@"type"], time];
        }
    }
    
    NSString *emailSubject = @"user stay event history";

    MFMailComposeViewController* controller = [[MFMailComposeViewController alloc] init];
    controller.mailComposeDelegate = self;
    [controller setSubject:emailSubject];
    [controller setMessageBody:emailBody isHTML:YES]; 
    if (controller) [self presentModalViewController:controller animated:YES];
}

- (void)mailComposeController:(MFMailComposeViewController *)controller didFinishWithResult:(MFMailComposeResult)result error:(NSError *)error
{
    [controller dismissModalViewControllerAnimated:TRUE];
}


@end
