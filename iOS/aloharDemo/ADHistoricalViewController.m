//
//  ADHistoricalViewController.m
//  aloharDemo
//
//  Created by Sam Warmuth on 3/12/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import "ADHistoricalViewController.h"
#import <Alohar/ALPlace.h>
#import <Alohar/ALUserStay.h>
#import "ADPlacesViewController.h"
#import "ADVisitsViewController.h"

@interface ADHistoricalViewController ()

@end

@implementation ADHistoricalViewController
@synthesize placeCountLabel;
@synthesize stayCountLabel;
@synthesize recentStaysTableView, allPlacesTableView, places, userstays;
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
        places = [[NSMutableArray alloc] init];
        userstays = [[NSMutableArray alloc] init];

    }
    return self;
}

- (void)doSearch 
{
    NSDateComponents *comps = [[NSDateComponents alloc] init];
    [comps setDay:10];
    [comps setMonth:10];
    [comps setYear:2010];
//    NSDate *startDate = [[NSCalendar currentCalendar] dateFromComponents:comps];

    //Here show case three types of place search:
    //case 1 search all places
    [Alohar getPlaces:@".*" withDelegate:self];
    
    //case 2 search all places in restaurant category
    //[Alohar getPlaces:@".*" withCategory:@"^rest" withDelegate:self];
    
    //case 3 search places
//    [Alohar getPlaces:@".*" fromDate:startDate toDate:[NSDate date] minimalVisits:2 withCategory:@".*" limit:10 withDelegate:self];
    
    //Here show case three types of search:
    //case 1: search for one specific date
    [Alohar getUserStaysForDate:[NSDate date] withDelegate:self];
    
    //case 2: search for one period
//    [Alohar getUserStaysFromDate:startDate toDate:[NSDate date] withDelegate:self];
    
    //case 3: search for one period around a location
//    NSInteger lat = 37432110;
//    NSInteger lon = -122103274;
//    [Alohar getUserStaysFromDate:startDate toDate:[NSDate date] atLatitudeE6:lat andLongitudeE6:lon radius:500 limit:0 withCandidiates:TRUE withDelegate:self];
    
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    [self doSearch];
}

- (IBAction)refresh:(id)sender
{
    [self doSearch];
}


- (void)aloharRequestFinished:(ALResponse *)response
{
    if (response.requestType == kALRequestTypePlaces){
        places = [(NSArray *)response.objects mutableCopy];
        NSLog(@"Updated places: %@", places);
        dispatch_async(dispatch_get_main_queue(), ^{
            self.placeCountLabel.text = [NSString stringWithFormat:@"%d", places.count];
            [self.allPlacesTableView reloadData];
        });
    } else if (response.requestType == kALRequestTypeUserStays){
        userstays = [(NSArray *)response.objects mutableCopy];
        NSLog(@"Updated user stays: %@", userstays);
        dispatch_async(dispatch_get_main_queue(), ^{
            self.stayCountLabel.text = [NSString stringWithFormat:@"%d", userstays.count];
            [self.recentStaysTableView reloadData];
        }); 
    }
}

- (void)aloharDidFailWithError:(NSError *)error 
{
    NSLog(@"Error %@", [error description]);
}

- (void)viewDidUnload
{
    [super viewDidUnload];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)aTableView
{
	return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if (tableView == self.recentStaysTableView){
        NSLog(@"userstay count: %d", userstays.count);
        return userstays.count;
    }else{
        NSLog(@"places count");
        return places.count;
    }
    
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell;

    if (tableView == self.recentStaysTableView){
        static NSString *CellIdentifier = @"ALUserstaysTableViewCell";
        cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CellIdentifier];
        }  
        ALUserStay *userstay = [userstays objectAtIndex:indexPath.row];
        ALPlace *selectedPOI =  userstay.selectedPlace;
        NSDate *startTime = [NSDate dateWithTimeIntervalSince1970:userstay.startTime];
        NSDate *endTime = [NSDate dateWithTimeIntervalSince1970:userstay.endTime];
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"MMM d h:mma"];
        
        cell.textLabel.text = [selectedPOI valueForKey:@"name"];
        cell.detailTextLabel.text = [NSString stringWithFormat:@"%@ to %@", [dateFormatter stringFromDate:startTime], [dateFormatter stringFromDate:endTime]];
        
    } else {
        static NSString *CellIdentifier = @"ALPlacesTableViewCell";
        cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
        }  
        ALPlace *place = [places objectAtIndex:indexPath.row];
        cell.textLabel.text = place.name;
    }
        
    return cell;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"showCandidates"]){
        NSIndexPath *indexPath = [self.recentStaysTableView indexPathForSelectedRow];
        ALUserStay *stay = [userstays objectAtIndex:indexPath.row];
        ADPlacesViewController *destinationView = segue.destinationViewController;
        destinationView.stay = stay;
    } else if ([[segue identifier] isEqualToString:@"showVisits"]){
        NSIndexPath *indexPath = [self.allPlacesTableView indexPathForSelectedRow];
        ALPlace *place = [places objectAtIndex:[indexPath row]];
        ADVisitsViewController *destinationView = segue.destinationViewController;
        destinationView.place = place;
    } 
    
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
    if (tableView == self.allPlacesTableView) {
        [self performSegueWithIdentifier:@"showVisits" sender:self];
    } else if (tableView == self.recentStaysTableView) {
        [self performSegueWithIdentifier:@"showCandidates" sender:self];
    }
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
