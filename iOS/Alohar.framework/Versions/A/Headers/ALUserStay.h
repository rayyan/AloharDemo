//
//  ALUserStay.h
//  Alohar
//
//  Created by Jianming Zhou on 3/16/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ALPlace.h"

/*!
 * The ALUserStay class represents a visit of a place. The user stay 
 * is automatically detected by Alohar SDK based on user's trace. 
 * Though the detection is quite accurate in most cases, the user stay
 * includes a list of Place candidates ranked. The best match will be 
 * chosen as the selectedPlace.
 *
 * The startTime/endTime records the start/end of the stay. The centroid
 * location is the location Alohar detected for that stay, which is not
 * necessary equivalent to the location of the selected place.
 * 
 */
@interface ALUserStay : NSObject

/*!
 */
@property (nonatomic, strong) CLLocation *centroidLocation;
/*!
 */
@property (nonatomic, assign) NSInteger startTime;
/*!
 */
@property (nonatomic, assign) NSInteger endTime;
/*!
 */
@property (nonatomic, assign) NSInteger stayID;
/*!
 */
@property (nonatomic, strong) ALPlace *selectedPlace;
/*!
 */
@property (nonatomic, strong) NSArray *candidates;

@end
