import json
from elevator import *

'''
This is the Building class:
    1. The __init__ receives a JSON file and extracts the key values:
        - Minimum Floor.
        - Maximum Floor.
        - elevator list.
        - Number of elevators.
    2) The class contains an elevator list:
        - Each index contains an elevator(imported from class - elevator).
'''


class Building:
    def __init__(self, j_file):
        with open(j_file, 'r') as json_file:
            json_load = json.load(json_file)
            self.minFloor = json_load['_minFloor']
            self.maxFloor = json_load['_maxFloor']
            self.ElevatorList = []
            for e in json_load["_elevators"]:
                newE = elevator(e["_id"], e["_speed"], e["_minFloor"], e["_maxFloor"], e["_closeTime"], e["_openTime"],
                                e["_startTime"], e["_stopTime"])
                self.ElevatorList.append(newE)
        json_file.close()
        self.elevatorAmount = len(self.ElevatorList)

    def __str__(self):
        return "Minimum Floor: {} \nMaximum Floor: {} \nElevator List: {} \nNumber of Elevators: {}".format(
            self.minFloor, self.maxFloor,
            [e.__str__() for e in self.ElevatorList], self.elevatorAmount)

    def get_elevator(self, index):
        return self.ElevatorList[index]



