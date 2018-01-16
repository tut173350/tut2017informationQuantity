//package s4.b173350; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
//import s4.specification.*;
public class Frequencer /*implements FrequencerInterface*/{
    // Code to start with: This code is not working, but good start point to work.
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    int [] suffixArray;
    // The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
    // Each suffix is expressed by a interger, which is the starting position in mySpace. // The following is the code to print the variable
    private void printSuffixArray() {
        if(spaceReady) {
            for(int i=0; i< mySpace.length; i++) {
                int s = suffixArray[i];
                for(int j=s;j<mySpace.length;j++) {
                    System.out.write(mySpace[j]); }
                System.out.write('\n'); }
        }
    }
    private int suffixCompare(int i, int j) {
        // comparing two suffixes by dictionary order. // i and j denoetes suffix_i, and suffix_j
        // if suffix_i > suffix_j, it returns 1
        // if suffix_i < suffix_j, it returns -1
        // if suffix_i = suffix_j, it returns 0;
        // It is not implemented yet,
        // It should be used to create suffix array.
        // Example of dictionary order
        // "i"
        // "Hi"
        // "Ho"
        if(i == j) return 0;
        int icnt = i;
        int jcnt = j;
        while(mySpace.length > icnt && mySpace.length > jcnt){
            if(mySpace[icnt] < mySpace[jcnt]) return -1;
            if(mySpace[icnt] > mySpace[jcnt]) return 1;
            icnt++;
            jcnt++;
        }
        if(icnt < jcnt) return 1;
        else return -1;
    }
    public void setSpace(byte []space) {
        mySpace = space;
        if(mySpace.length>0) spaceReady = true;
        suffixArray = new int[space.length];
        // put all suffixes in suffixArray. Each suffix is expressed by one interger.
        for(int i = 0; i< space.length; i++) {
                suffixArray[i] = i;
        }
            // Sorting is not implmented yet. /* Example from "Hi Ho Hi Ho"
            //0: Hi Ho
            //1: Ho
            //2: Ho Hi Ho 3:Hi Ho
            //4:Hi Ho Hi Ho 5:Ho
            //6:Ho Hi Ho 7:i Ho
            //8:i Ho Hi Ho 9:o
            //A:o Hi Ho
            //*/
        
        for(int i = 0;i < space.length; i++){
            for(int j = 0;j < space.length-1; j++){
                int c = suffixCompare(suffixArray[j],suffixArray[j+1]);
                if(c == 1) {
                    int tmp = suffixArray[j];
                    suffixArray[j] = suffixArray[j+1];
                    suffixArray[j+1] = tmp;
                }
            }
        }
        
        printSuffixArray();
    }
    private int targetCompare(int i, int start, int end) {
// comparing suffix_i and target_j_end by dictonary order with limitation of length;
// if the beginning of suffix_i matches target_i_end, and suffix is longer than target it returns 0;
// if suffix_i > target_i_end it return 1;
// if suffix_i < target_i_end it return -1
// It is not implemented yet.
// It should be used to search the apropriate index of some suffix. // Example of search
// suffix
// "o"
// "o"
// "o"
// "o"
// "Ho" // "Ho" // "Ho" //"Ho" // "Ho" return 0;
//target > "i"
//< "z"
//= "o"
///< "oo"
//> "Hi"
//< "Hz"
//= "Ho"
//< "Ho" :"Ho"isnotintheheadofsuffix"Ho" = "H" : "H" is in the head of suffix "Ho"
        for(int j = 0; end > j  ;j++){
            if(mySpace[suffixArray[i] + j] > myTarget[start + j]) return 1;
            else if(mySpace[suffixArray[i] + j] < myTarget[start + j] ||
                    (mySpace.length -suffixArray[i]) < end) return -1;
        }
        return 0;
    }
    private int subByteStartIndex(int start, int end) {
        // It returns the index of the first suffix which is equal or greater than subBytes;
        // not implemented yet;
        // For "Ho", it will return 5 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 6 for "Hi Ho Hi Ho".
                for(int i = 0 ;mySpace.length > i ; i++){
                    if(targetCompare(i,start,end) == 0) return i;
                }
        return suffixArray.length;
    }
    private int subByteEndIndex(int start, int end) {
        // It returns the next index of the first suffix which is greater than subBytes; // not implemented yet
        // For "Ho", it will return 7 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
                for(int i = mySpace.length - 1 ;0 <= i ; i--){
                    if(targetCompare(i,start,end) == 0) return i+1;
                }
        return suffixArray.length;
    }
    public int subByteFrequency(int start, int end) {
        /* This method could be defined as follows though it is slow.
         int spaceLength = mySpace.length;
         int count = 0;
         for(int offset = 0; offset< spaceLength - (end - start); offset++) {
         boolean abort = false;
         for(int i = 0; i< (end - start); i++) {
         if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; } }
         if(abort == false) { count++; } }
         */
        int first = subByteStartIndex(start,end);
        int last1 = subByteEndIndex(start, end);
        /* inspection code
         for(int k=start;k<end;k++) { System.out.write(myTarget[k]); } System.out.printf(": first=%d last1=%d\n", first, last1);
         */
        return last1 - first;
    }
    public void setTarget(byte [] target) {
        myTarget = target; if(myTarget.length>0) targetReady = true;
    }
    
    public int frequency() {
        if(targetReady == false) return -1; if(spaceReady == false) return 0;
        return subByteFrequency(0, myTarget.length);
    }
    
    public static void main(String[] args) {
        Frequencer frequencerObject;
        try {
            frequencerObject = new Frequencer();
            frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
            frequencerObject.setTarget("  ".getBytes());
            int result = frequencerObject.frequency();
            System.out.print("Freq = "+ result+" ");
            if(4 == result) { System.out.println("OK"); }
            else {System.out.println("WRONG"); }
        }
        catch(Exception e) {
            System.out.println("STOP");
        }
    }
}