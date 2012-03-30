//
//  ALResponse.h
//  Alohar
//
//  Created by Sam Warmuth on 2/23/12.
//  Copyright (c) 2012 Alohar Mobile Inc.. All rights reserved.
//

#import <Foundation/Foundation.h>
@class ALResponse, ALMotionState;

enum {
    kALRequestTypeUserStays,
    kALRequestTypePlaces,
    kALRequestTypeMotionState,
    kALRequestTypeStayDetail,
    kALRequestTypePlaceDetail,
    
};

typedef enum {
    kErr_NETWORK,
    kErr_SERVER,
    kErr_REQUEST
} errorCode;

typedef NSUInteger ALRequestType;

/*!
 * ALResponse class declare a common protocol to process
 * Alohar response. 
 *
 * The response has three main parts:
 * + timeStamp The time the response is returned. 
 * + requestType The type of the request type. 
 * + objects The payload of the response
 *
 * ALResponse has following request types:
 * ```
 * enum {
 * kALRequestTypeUserStays,
 * kALRequestTypePlaces,
 * kALRequestTypeMotionState,
 * kALRequestTypeStayDetail,
 * kALRequestTypePlaceDetail,
 * }
 * ```
 */
@interface ALResponse : NSObject

/*!
 */
@property (strong) NSDate *timeStamp;
/*!
 */
@property (nonatomic, strong) NSMutableArray *objects;
/*!
 */
@property ALRequestType requestType;


@end
