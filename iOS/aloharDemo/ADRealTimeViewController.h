//
//  aloharDemo
//
//  Created by Sam Warmuth on 2/23/12.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>

#define METERS_PER_MILE 1609.344


@interface ADRealTimeViewController : UIViewController
@property (nonatomic, strong) NSTimer *callbackTimer;
@property (nonatomic, strong) IBOutlet UIView *motionContainer;
@property (nonatomic, strong) IBOutlet MKMapView *mapView;
@property (nonatomic, strong) CLLocation *currentLocation;
@property (nonatomic, strong) IBOutlet UILabel *motionStateLabel;

- (void)updateMotionStateAndLocation;
@end
