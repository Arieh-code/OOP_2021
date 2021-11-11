import json


class Elevator:
    def __init__(self, id, speed, minFloor, maxFloor, closetime, opentime, starttime, stoptime):
        self.minFloor = minFloor
        self.maxFloor = maxFloor
        self.id = id
        self.speed = speed
        self.closetime = closetime
        self.opentime = opentime
        self.starttime = starttime
        self.stoptime = stoptime
        self.pos = minFloor  # the elevator is starting at min floor
        self.state = 0  # the elevator is starting at level state ( 1 is up and -1 is down)

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



