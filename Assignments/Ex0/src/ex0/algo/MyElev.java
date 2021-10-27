package ex0.algo;
import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A bit better than ShabatElev2Algo: randomly spread the elevators in the init stage, allocate the "closest" elevator..
 */

public class MyElev implements ElevatorAlgo {
        public static final int UP=1, DOWN=-1, LEVEL=0;
        private int _direction;
        private Building _building;
//        private ArrayList<Integer> CallUp;
//        private ArrayList<Integer> CallDown;
        private ArrayList<Boolean> [] Entered;
        private ArrayList <CallForElevator> [] src2dest;

        public MyElev (Building b) {
            _building = b;
            _direction = UP;
            src2dest = new ArrayList [this._building.numberOfElevetors()];
            /* this is an array of arraylist type Call for elevator
            that each cell represents and elevator, after we allocate a call we
            will send it to the right elevator and story the src and dest.
             */
            for(int i = 0; i< this._building.numberOfElevetors(); i++){
                src2dest[i] = new ArrayList<CallForElevator>();
            }
            Entered = new ArrayList[this._building.numberOfElevetors()];
            for(int i = 0; i< this._building.numberOfElevetors(); i++){
                Entered[i] = new ArrayList<Boolean>();
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


            }
            else{
                src2dest[0].add(c);
                Entered[0].add(false);
            }

        return ans;
    }

    @Override
    public void cmdElevator(int elev) {
            if(this._building.numberOfElevetors()==1)
                f0();

    }

    private void f1() {

    }

    /*
    function for a building with one elevator
     */
    private void f0() {
            Elevator curr = this.getBuilding().getElevetor(0);
            if(src2dest[0].size()==1) {
                /* if there is only one call in my arraylist
                ill send the elevator straight to the src
                 */
                curr.goTo(src2dest[0].get(0).getSrc());
                /* after sending the elevator to the src
                i am updating the direction of the elevator
                 */
                if(curr.getPos()>src2dest[0].get(0).getSrc()) {
                    this._direction = DOWN;
                }
                else if(curr.getPos() == src2dest[0].get(0).getSrc()) {

                    this._direction = LEVEL;
                }
                else {
                    this._direction = UP;
                }

                // sending the elevator from SRC to DEST.
                curr.goTo(src2dest[0].get(0).getDest());
                /* updating the direction of the elevator after pick up from SRC

                 */
                this._direction = src2dest[0].get(0).getType();

            }
            else{ // there is more than one call in my ArrayList

                for(int i = 1; i<src2dest[0].size(); i++){
                    if(Pickup(src2dest[0].get(i), curr.getPos())){
                        curr.stop(src2dest[0].get(i).getSrc());
                        curr.goTo(Closest(src2dest[0]));

                    }
                }


            }
            /*this last code will check if someone has truly entered the
            elevator, and if he has will check if he has also exited so we can remove his ca
            from the call list
             */
            for(int i = 0; i < src2dest[0].size(); i++ ){
                if(HasEntered(src2dest[0].get(i), curr))
                    RemoveCall(src2dest[0].get(i), curr, i, 0);
        }


    }

    public int getDirection() {
            return this._direction;
    }

    /**
     this function returns true if on my way to the destination i
    should stop and pick up
     */
    public boolean Pickup(CallForElevator c, int pos){
        if(pos <= c.getSrc() && c.getSrc() < c.getDest() && this._direction == UP ){
            return true;
        }
        return pos >= c.getSrc() && c.getSrc() > c.getDest() && this._direction == DOWN;
    }

    public boolean HasEntered(CallForElevator c, Elevator curr){
        // this will check if the states are the same and if they have crossed paths.
        if(c.getType() == UP && curr.getState()== UP && curr.getPos() >= c.getSrc())
            return true;
        return c.getType() == DOWN && curr.getState() == DOWN && curr.getPos() <= c.getSrc();
    }


    /**
     *  this function will check if someone has lef the elevator on his floor
     */
     public void RemoveCall(CallForElevator c, Elevator curr, int Call_index, int src2destindex){
        if(c.getType() == UP && curr.getState()== UP && curr.getPos() >= c.getDest()) {
            src2dest[src2destindex].remove(Call_index);
            Entered[src2destindex].remove(Call_index);
        }
        if (c.getType() == DOWN && curr.getState() == DOWN && curr.getPos() <= c.getDest()) {
            src2dest[src2destindex].remove(Call_index);
            Entered[src2destindex].remove(Call_index);
        }
    }

    /* this function will let me know what is the closest
    destination between two calls.
     */
    public int Closest(ArrayList<CallForElevator> lift){
        int min = lift.get(0).getDest();
        if(this._direction == UP){
            for(int i = 1; i < lift.size(); i++){
                if(lift.get(i).getDest()< min)
                    min = lift.get(i).getDest();
            }
        }
        else{
            for(int i = 1; i < lift.size(); i++){
                if(lift.get(i).getDest()>min)
                    min = lift.get(i).getDest();
            }


        }
        return min;
    }




}
