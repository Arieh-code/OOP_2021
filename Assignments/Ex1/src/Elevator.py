import json


class Elevator:
    def __init__(self, minFloor, maxFloor, id, speed, closetime, opentime, starttime, stoptime):
        self.minFloor = minFloor
        self.maxFloor = maxFloor
        self.id = id
        self.speed = speed
        self.closetime = closetime
        self.opentime = opentime
        self.starttime = starttime
        self.stoptime = stoptime


    def __getPos__(self):
        pass

    def __goTo__(floor):
        pass

    def __stop__(floor):
        pass

    def __getMaxFloor__(self):
        return self.maxFloor

    def __getMinFloor__(self):
        return self.minFloor

    def __getId__(self):
        return self.id

    def __getSpeed__(self):
        return self.speed

    def __getClosetime__(self):
        return self.closetime

    def __getOpentime__(self):
        return self.opentime

    def getstarttime(self):
        return self.starttime

    def getStoptime(self):
        return self.stoptime




