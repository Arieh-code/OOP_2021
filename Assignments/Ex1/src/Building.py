import json
from Elevator import *


class Building:
    def __init__(self, j_file):
        with open(j_file, 'r') as json_file:
            json_load = json.load(json_file)
            self.minFloor = json_load['_minFloor']
            self.maxFloor = json_load['_maxFloor']
            elevatorarray = []
            for e in json_load['_elevators']:
                newE = Elevator(e["_id"], e["_speed"], e["_minFloor"], e["_maxFloor"], e["_closeTime"], e["_openTime"],
                                e["_startTime"], e["_stopTime"])
                elevatorarray.append(newE)
            self.ElevatorArray = elevatorarray
        json_file.close()
        self.elevator_count = len(elevatorarray)
        self.timeStmap = 0


file = r"C:\Users\arieh\OneDrive\Desktop\B5.json"
# with open(file, 'r') as json_file:
#     json_load = json.load(json_file)
# for elevator in json_load["_elevators"]:
#     newE = Elevator()
#     print(elevator)
b = Building(file)
for i in b.ElevatorArray:
    print(i.__str__())
