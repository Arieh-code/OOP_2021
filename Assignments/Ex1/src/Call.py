class Call:
    def __init__(self, csv_file_list):
        self.stam_str = str(csv_file_list.values[0])
        self.timeStamp = float(csv_file_list.values[1])
        self.originFloor = int(csv_file_list.values[2])
        self.destFloor = int(csv_file_list.values[3])
        self.status = int(csv_file_list.values[4])
        self.elevatorIndex = int(csv_file_list.values[5])

    def __str__(self):
        return self.stam_str, self.timeStamp, self.originFloor, self.destFloor, self.status, self.elevatorIndex




