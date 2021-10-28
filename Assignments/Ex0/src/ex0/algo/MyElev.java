package ex0.algo;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import javax.print.attribute.standard.DialogOwner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * A bit better than ShabatElev2Algo: randomly spread the elevators in the init stage, allocate the "closest" elevator..
 */

public class MyElev implements ElevatorAlgo {
        public static final int UP=1, DOWN=-1, LEVEL=0;
        private int _direction;
        private Building _building;
        private int [] ElevatorList;
        private ArrayList<Integer> [] UpCalls;
        private ArrayList <Integer> [] DownCalls;

        public MyElev (Building b) {
            _building = b;
            _direction = UP;

            /* all the cells are default on zero which means they are level
             */
            ElevatorList = new int[this._building.numberOfElevetors()];

            UpCalls = new ArrayList [this._building.numberOfElevetors()];
            /* this is an array of arraylist type integer
            that each cell represents a call going up , after we allocate a call we
            will send it to the right elevator and story the src and dest.
             */
            for(int i = 0; i< this._building.numberOfElevetors(); i++){
                UpCalls[i] = new ArrayList<Integer>();
            }
            DownCalls = new ArrayList[this._building.numberOfElevetors()];
            for(int i = 0; i< this._building.numberOfElevetors(); i++){
                DownCalls[i] = new ArrayList<Integer>();
            }


        }


    @Override
    public Building getBuilding() {
        return _building;
    }

    @Override
    public String algoName() {
        return "OOP_2021__MyElevator(0)";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
            int ans = 0, elevNum = _building.numberOfElevetors();
            if(elevNum>1) {
                if(c.getType() == UP){


                    UpCalls[ans].add(c.getSrc());
                    UpCalls[ans].add(c.getDest());
//                    sortCallsUp(UpCalls[ans],ans);
                    ElevatorList[ans] = UP;
                    ans = FindBest(this.ElevatorList, UP, c.getSrc());

                }


                DownCalls[ans].add(c.getSrc());
                DownCalls[ans].add(c.getDest());
//                sortCallsDown(DownCalls[ans], ans);
                ElevatorList[ans] = DOWN;
                ans = FindBest(this.ElevatorList, DOWN, c.getSrc());

            }
            else {
                if (c.getType() == UP) {
                    UpCalls[0].add(c.getSrc());
                    UpCalls[0].add(c.getDest());
                    ElevatorList[0] = UP;

                }
                DownCalls[0].add(c.getSrc());
                DownCalls[0].add(c.getDest());
                ElevatorList[0] = DOWN;

            }

        return ans;
    }

    /*
    this function will sort the order th elevator has to go in.
     */
    public void sortCallsUp(ArrayList<Integer> lift, int elev){
            Elevator curr = getBuilding().getElevetor(elev);
            for(int i = 1; i<lift.size()-1; i++){
                for(int j = i; j<lift.size()-1-i; j++) {
                    if (lift.get(j) > lift.get(j+1) && curr.getPos() < lift.get(j+1))
                        swap(j, j+1, lift);
                    if (lift.get(j) > lift.get(j+1) && curr.getPos() > lift.get(j+1))
                        MoveToBack(j+1, lift);
                }
            }
    }

    public void sortCallsDown(ArrayList<Integer> lift, int elev){
        Elevator curr = getBuilding().getElevetor(elev);
        for(int i = 1; i<lift.size()-1; i++){
            for(int j = i; j<lift.size()-1-i; j++) {
                if (lift.get(j) < lift.get(j+1) && curr.getPos() > lift.get(j+1))
                    swap(j, j+1, lift);
                if (lift.get(j) < lift.get(j+1) && curr.getPos() < lift.get(j+1))
                    MoveToBack(j+1, lift);
            }
        }
    }


    private void MoveToBack(int i, ArrayList<Integer> lift) {
            int temp = lift.get(i);
            lift.remove(i);
            lift.add(temp);
    }

    public void swap(int a, int b, ArrayList<Integer> lift){
            int temp = lift.get(b);
            lift.remove(b);
            lift.add(b, lift.get(a));
            lift.remove(a);
            lift.add(a, temp);
    }

    public int FindBest(int[] ElevatorList, int Direction, int src){
            // Array list to to check min distance--------------
        if(Direction == UP){
            for(int i = 0; i<ElevatorList.length; i++)
                if(ElevatorList[i] == LEVEL && (UpCalls[i].isEmpty()) || getBuilding().getElevetor(i).getPos() == src){
                    return i;
                }
            for(int i = 0; i<ElevatorList.length; i++){
                if(ElevatorList[i] == UP && UpCalls[i].contains(src) && getBuilding().getElevetor(i).getPos() < src)
                    return i;
            }
            for(int i = 0; i<ElevatorList.length; i++) {
                if (ElevatorList[i] == UP && getBuilding().getElevetor(i).getPos() < src)
                    return i;
            }
            ArrayList<Integer> goingup = new ArrayList();
            for(int i = 0; i<ElevatorList.length; i ++){
                if(ElevatorList[i] == UP)
                    goingup.add(i);
            }
            int rand = (int)(Math.random()* goingup.size());
            return goingup.get(rand);
        }
        else {
            for(int i = 0; i<ElevatorList.length; i++)
                if(ElevatorList[i] == LEVEL && (DownCalls[i].isEmpty()) || getBuilding().getElevetor(i).getPos() == src){
                    return i;
                }
            for(int i = 0; i<ElevatorList.length; i++){
                if(ElevatorList[i] == DOWN && DownCalls[i].contains(src) && getBuilding().getElevetor(i).getPos() > src )
                    return i;
            }
            for(int i = 0; i<ElevatorList.length; i++) {
                if (ElevatorList[i] == DOWN && getBuilding().getElevetor(i).getPos() > src)
                    return i;
            }
            ArrayList<Integer> goingdown = new ArrayList();
            for(int i = 0; i<ElevatorList.length; i ++){
                if(ElevatorList[i] == DOWN)
                    goingdown.add(i);
            }
            return  goingdown.get(rand(0,goingdown.size())); // Random
        }

    }

    @Override
    public void cmdElevator(int elev) {
            Elevator curr = this._building.getElevetor(elev);
        //If the call is UP or LEVEL:
        if (curr.getState() == UP || curr.getState() == LEVEL) {
            if(!UpCalls[elev].isEmpty()) {
                curr.goTo(UpCalls[elev].get(0));
                if (curr.getPos() == UpCalls[elev].get(0))
                    UpCalls[elev].remove(0);
                curr.goTo(UpCalls[elev].get(0));
            }
            else{
                curr.goTo(curr.getMinFloor());
            }
            //Check if the elevator has previous calls and the elevator is on the same floor.
            if (UpCalls[elev].size() > 0 && curr.getPos() == UpCalls[elev].get(0)) {
                //Remove the call
                UpCalls[elev].remove(0);
                curr.goTo(nextStop(UpCalls[elev], UP, curr.getPos()));
            }
            //If the there has previous calls:
            if (UpCalls[elev].size() > 0)
                //Goto the next call:
                curr.goTo(nextStop(UpCalls[elev], UP, curr.getPos()));
        }
        //If the call is Down:
        else {
            if(!DownCalls[elev].isEmpty()) {
                curr.goTo(DownCalls[elev].get(0));
                if (curr.getPos() == DownCalls[elev].get(0))
                    DownCalls[elev].remove(0);
                curr.goTo(DownCalls[elev].get(0));
            }
            else{
                curr.goTo(curr.getMinFloor());
            }
            //Check if the elevator has previous calls and the elevator is on the same floor.
            if (DownCalls[elev].size() > 0 && curr.getPos() == DownCalls[elev].get(0)) {
                //Remove the call
                DownCalls[elev].remove(0);
                curr.goTo(nextStop(DownCalls[elev], DOWN, curr.getPos()));
            }
            //If the there has previous calls:
            if (DownCalls[elev].size() > 0)
                //Goto the next call:
                curr.goTo(nextStop(DownCalls[elev], DOWN, curr.getPos()));
        }
            }



    /**
     * this function will find for us which is the next best stop to go to.

     */
    private int nextStop(ArrayList<Integer> upCall, int up, int pos) {
            if(up== UP) {
                int temp = pos;
                int next = upCall.get(0);
                for (int i = 0; i < upCall.size(); i++) {
                    if (upCall.get(i) > temp && upCall.get(i) < next)
                        next = upCall.get(i);
                }
                return next;
            }
            else{
                int temp = pos;
                int next = upCall.get(0);
                for (int i = 0; i < upCall.size(); i++) {
                    if (upCall.get(i) < temp && upCall.get(i) > next)
                        next = upCall.get(i);
                }
                return next;
            }
    }



    public boolean removeCall(ArrayList<Integer> lift, Elevator curr){
        return true;
    }

    public int getDirection() {
            return this._direction;
    }


    private static int rand(int min, int max) {
        if (max < min) {
            throw new RuntimeException("ERR: wrong values for range max should be >= min");
        }
        int ans = min;
        double dx = max - min;
        double r = Math.random() * dx;
        ans = ans + (int) (r);
        return ans;
    }


}
