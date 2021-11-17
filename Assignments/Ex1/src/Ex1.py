import pandas as pd
from Call import *
from Building import *


def Ex1(Building_json, call_csv, output_csv):
    building_b = Building(Building_json)
    elev_list = building_b.ElevatorList
    call_file = make_df(call_csv)
    output = pd.DataFrame(output_csv)
    while call_file.size != 0:
        first_call = Call(call_file.iloc[0])
        temp_df = {}
        for i in elev_list:
            i.addCall(first_call)
        if first_call.status == 1:
            temp_df = make_up(call_file.loc[1:])
            for src in range(first_call.originFloor, building_b.maxFloor):
                src_df = temp_df[temp_df['src'] == src]
                src_df.drop(['index'], axis=1, inplace=True)
                for e in elev_list:
                    for row in src_df:
                        curr_call = Call(row)
                        e.addCall(curr_call)
            allocate_elev(elev_list, output)
        else:
            temp_df = make_down(call_file.loc[1:])
            for src in range(first_call.originFloor, building_b.minFloor, -1):
                src_df = temp_df[temp_df['src'] == src]
                src_df.drop(['index'], axis=1, inplace=True)
                for e in elev_list:
                    for row in src_df:
                        curr_call = Call(row)
                        e.addCall(curr_call)
            allocate_elev(elev_list, output)
    make_output(output, "output.txt")


def allocate_elev(elevator_list, output):
    best = 0
    min_time = calc_time(elevator_list[0])
    for i in range(1, len(elevator_list) + 1):
        if calc_time(elevator_list[i]) < min_time:
            min_time = calc_time(elevator_list[i])
            best = i
    for i in range(1, len(elevator_list) + 1):
        if i == best:
            continue
        else:
            elevator_list[i].wipe_dict()  ## this will wipe the dict from its time since we aren't using those elevators
    addIndex(elevator_list[best])  ## this will add the index to the calls
    sent_to_output(elevator_list[best], output)


def calc_time(elev):
    if len(elev.callList) != 0:
        call_list = elev.callList
        total_time = 0
        people = len(elev.callList)
        for call in call_list:
            total_time += (elev.floor_timestamp_dict[call.destFloor] - call.timeStamp)
        return total_time / people


def addIndex(elev):
    call_list = elev.callList
    call_list = call_list.assign(elevatorIndex=elev.id)


def sent_to_output(elev, output):
    elev_df = pd.DataFrame(elev.callList)
    output = pd.concat([elev_df, output], ignore_index=True)


def make_df(file):
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


# function to make a data frame with only up calls
def make_up(dataframe):
    df_up = dataframe[dataframe['status'] == 1]
    df_up = df_up.reset_index()
    df_up = df_up.sort_values(['src', 'timeStamp'], ascending=True)
    return df_up


# function to make dataframe with only down calls
def make_down(dataframe):
    df_down = dataframe[dataframe['status'] == -1]
    df_down = df_down.reset_index()
    df_down = df_down.sort_values(['src', 'timeStamp'], ascending=True)
    return df_down


# creating function to return csv file correctly
def make_output(dataframe, df_name: str = None):
    new_header = dataframe.iloc[0]  # grab the first row for the header
    dataframe = dataframe[1:]  # take the data less the header row
    dataframe.columns = new_header  # set the header row as the df header
    # returning without index
    dataframe.to_csv(df_name, index=False)


def main():
    b2 = r"C:\Users\arieh\PycharmProjects\OOP_2021\Assignments\Ex1\data\Ex1_input\Ex1_Buildings\B1.json"
    b = Building(b2)
    c_a = r"C:\Users\arieh\PycharmProjects\OOP_2021\Assignments\Ex1\data\Ex1_input\Ex1_Calls\Calls_a.csv"
    calls = pd.read_csv(c_a)
    print(calls)
    # c = Call(calls.iloc[4])
    # print(c.__str__())
    call_list = make_df(c_a)
    print(call_list)
    call_list = call_list.assign(elevatorIndex=0)
    make_output(call_list, "call_out.txt")

    # print(b.__str__())
    # e = b.ElevatorList[0]
    # print(e.__str__())

    # print(call_list)
    # print(call_list.loc[[4]])
    # print(call_list.loc[[4]].stam_str)
    # print(call_list.iloc[4].values)
    # print(call_list.iloc[4].values[1])
    # row6 = Call(call_list.iloc[4].values)
    # print(row6.__str__())
    Ex1()


if __name__ == "__main__":
    main()
