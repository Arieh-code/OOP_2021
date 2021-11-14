from Elevator import *
from Calls import *

''' This library is a static library that we will use in our allocate function to 
    find the best suited elevator '''


def is_in_time(curr_elevator):
    pass


def call_time(curr_elevator, curr_call):
    start_time = curr_call.timeStamp
    labor_time = curr_elevator.closetime + curr_elevator.opentime + curr_elevator.starttime + curr_elevator.stoptime
    total_floors = abs(curr_call.destFloor - curr_call.originFloor)
    total_time = (total_floors * curr_elevator.speed) + labor_time
    return total_time


def is_relevant_call(curr_elevator, next_call):
    next_timestamp = next_call.timeStamp
    next_src = next_call.src


def time_to_floor(curr_elevator, floor):
    pass


def first_call(curr_elevator, call):
    pass


def is_start_time(curr_elevator, call):
    pass


def is_stop_time(curr_elevator, call):
    pass

def add_stop_time(curr_elevator, call):
    pass

def add_start_time(curr_elevator, call):
    pass