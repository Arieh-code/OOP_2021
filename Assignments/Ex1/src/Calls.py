from Elevator import *
import pandas as pd
import numpy as np


class Calls:
    def __init__(self, csv_file):
        self.csv_file = csv_file

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

    # creating a csv with only going up calls and going down calls
    def create_up_down(self, dataframe):
        file_up = dataframe[dataframe['status'] == 1]
        file_down = dataframe[dataframe['status'] == -1]
        # need to decide if we want to sort according to src or timeStamp

    # creating function to return csv file correctly
    def make_output(self, dataframe):
        new_header = dataframe.iloc[0]  # grab the first row for the header
        dataframe = dataframe[1:]  # take the data less the header row
        dataframe.columns = new_header  # set the header row as the df header
        # returning without index
        dataframe.to_csv('output.csv', index=False)
