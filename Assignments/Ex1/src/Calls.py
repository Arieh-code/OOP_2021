from Elevator import *
import pandas as pd
import numpy as np


class Calls:
    def __init__(self, csv_file_list):
        self.stam_str = str(csv_file_list[0])
        self.timeStamp = float(csv_file_list[1])
        self.originFloor = int(csv_file_list[2])
        self.destFloor = int(csv_file_list[3])
        self.status = int(csv_file_list[4])
        self.elevatorIndex = int(csv_file_list[5])

    def make_df(self, file):
        # creating the csv file
        dataframe = pd.read_csv(file)
        # now we are going to sort out the columns
        # making header columns so we can work with the csv
        col = dataframe.columns  # saving all the columns names
        dataframe.loc[-1] = [col[0], float(col[1]), int(col[2]), int(col[3]), 0, -1]  # adding a row
        dataframe.index = dataframe.index + 1  # shifting index
        dataframe = dataframe.sort_index()  # sorting by index
        # renaming the columns to what we decide its only for our use when we allocate
        dataframe.rename(
            columns={col[0]: 'stam_str', col[1]: 'timeStamp', col[2]: 'src', col[3]: 'dest', col[4]: 'status',
                     col[5]: 'elevatorIndex'}, inplace=True)
        # now we will generate the status for each call
        for i in range(len(dataframe)):
            if int(dataframe.src[i]) < int(dataframe.dest[i]):
                dataframe.status[i] = 1
            else:
                dataframe.status[i] = -1
        return dataframe

    # create df from only elevators going up
    def create_up(self, dataframe):
        df_up = dataframe[dataframe['status'] == 1]
        df_up = df_up.reset_index()
        return df_up

    # create df from only elevators going down
    def create_down(self, dataframe):
        df_down = dataframe[dataframe['status'] == -1]
        df_down = df_down.reset_index()
        return df_down

        # need to decide if we want to sort according to src or timeStamp

    # creating function to return csv file correctly
    def make_output(self, dataframe, df_name: str = None):
        new_header = dataframe.iloc[0]  # grab the first row for the header
        dataframe = dataframe[1:]  # take the data less the header row
        dataframe.columns = new_header  # set the header row as the df header
        # returning without index
        dataframe.to_csv(df_name, index=False)

    # def allocate(self):


    def _str_(self):
        return self.stam_str, self.timeStamp, self.originFloor, self.destFloor, self.status, self.elevatorIndex
