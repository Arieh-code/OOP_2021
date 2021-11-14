import json


class Elevator:
    def __init__(self, id, speed, minFloor, maxFloor, closetime, opentime, starttime, stoptime):
        self.minFloor = int(minFloor)
        self.maxFloor = int(maxFloor)
        self.id = int(id)
        self.speed = float(speed)
        self.closetime = float(closetime)
        self.opentime = float(opentime)
        self.starttime = float(starttime)
        self.stoptime = float(stoptime)
        self.pos = int(minFloor)  # the elevator is starting at min floor
        self.state = 0  # the elevator is starting at level state ( 1 is up and -1 is down)
        self.time_stamp = 0.0
        #########################################################################
        self.callList = []  # {2}Here we can add the calls appointed to the elevator
        self.callAmount = 0
        self.finish_timestamp = 0.0  # {3} The end-time will be initialized to 0.
        total_floors = (abs(self.minFloor) + abs(self.maxFloor) + 1)
        self.floor_timestamp_dict = {index: x for index, x in enumerate([0.0] * total_floors, start=self.minFloor)}

    def goto(self, floor):
        if self.pos < floor:
            self.setstate(1)
        elif self.pos > floor:
            self.setstate(-1)
        else:
            self.setstate(0)
        self.pos = floor

    def setstate(self, num):
        if self.pos == self.maxFloor:
            self.state = -1
        elif self.pos == self.minFloor:
            self.state = 1
        else:
            self.state = num

    def stop(self, floor):
        self.goto(floor)

    def __str__(self):
        return self.id, self.speed, self.minFloor, self.maxFloor, self.closetime, self.opentime, self.starttime, \
               self.stoptime

    def __repr__(self):
        return self.id, self.speed, self.minFloor, self.maxFloor, self.closetime, self.opentime, self.starttime, \
               self.stoptime
