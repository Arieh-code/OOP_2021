from Elevator import *
import pandas as pd
import numpy as np


def df(file):
    # creating the csv file
    dataframe = pd.read_csv(file)
    return dataframe


def sortcols(dataframe):
    # making header columns so we can work with the csv
    col = dataframe.columns  # saving all the columns names
    dataframe.loc[-1] = [col[0], float(col[1]), int(col[2]), int(col[3]), 0, -1]  # adding a row
    dataframe.index = dataframe.index + 1  # shifting index
    dataframe = dataframe.sort_index()  # sorting by index
    # renaming the columns to what we decide its only for our use when we allocate
    dataframe.rename(columns={col[0]: 'stam_str', col[1]: 'timeStamp', col[2]: 'src', col[3]: 'dest', col[4]: 'status',
                              col[5]: 'elevatorIndex'}, inplace=True)


# function to generate a status of a call, 1 if want to go up and -1 if want to go down
def changestatus(dataframe):
    for i in range(len(dataframe)):
        if int(dataframe.src[i]) < int(dataframe.dest[i]):
            dataframe.status[i] = 1
        else:
            dataframe.status[i] = -1
    return dataframe


# creating a csv with only going up calls and going down calls
def createup_down(dataframe):
    file_up = dataframe[dataframe['status'] == 1]
    file_down = dataframe[dataframe['status'] == -1]
    # need to decide if we want to sort according to src or timeStamp


# removing the header so our csv file at the end wont give us issues with the tester
def remove_header(dataframe):
    new_header = dataframe.iloc[0]  # grab the first row for the header
    dataframe = dataframe[1:]  # take the data less the header row
    dataframe.columns = new_header  # set the header row as the df header


def make_output(dataframe):
    # using the remove header so we can make a correct csv file again
    remove_header(dataframe)
    # returning without index
    dataframe.to_csv('output.csv', index=False)


class Call:
    def __init__(self, csv_file_row):
        self.stam_str = str(csv_file_row[0])
        self.timeStamp = float(csv_file_row[1])
        self.originFloor = int(csv_file_row[2])
        self.destFloor = int(csv_file_row[3])
        self.status = int(csv_file_row[4])
        self.elevatorIndex = int(csv_file_row[5])
