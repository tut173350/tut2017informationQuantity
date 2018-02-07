package s4.b173350; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;
public class Frequencer implements FrequencerInterface{
    // Code to start with: This code is not working, but good start point to work.
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    
    int left;
    int right;
    int mid;
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
        if(mySpace.length<=0) return;
        suffixArray = new int[space.length];
        // put all suffixes in suffixArray. Each suffix is expressed by one interger.
        for(int i = 0; i< space.length; i++) {
                suffixArray[i] = i;
        }
            // Sorting is not implmented yet. /* Example from "Hi Ho Hi Ho"
            //0: Hi Ho
            //1: Ho
            //2: Ho Hi Ho
            //3:Hi Ho
            //4:Hi Ho Hi Ho
            //5:Ho
            //6:Ho Hi Ho
            //7:i Ho
            //8:i Ho Hi Ho
            //9:o
            //A:o Hi Ho
            //*/
        
        /* suffixをバブルソートで作成
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
         */
        
        /*suffixをクイックソートで作成*/
        quicksort(suffixArray,0,suffixArray.length-1);
        
        
        printSuffixArray();
    }
    private void quicksort(int suffixArray[],int left,int right){
        int pivot;
        int l_hold;
        int r_hold;
        
        l_hold = left;
        r_hold = right;
        pivot = suffixArray[left];
        
        while (left < right)
        {
            while ((suffixCompare(suffixArray[right], pivot) >= 0) && (left < right))
                right--;
            if (left != right)
            {
                suffixArray[left] = suffixArray[right];
                left++;
            }
            while ((suffixCompare(suffixArray[left], pivot) <= 0) && (left < right))
                left++;
            if (left != right)
            {
                suffixArray[right] = suffixArray[left];
                right--;
            }
        }
        suffixArray[left] = pivot;
        pivot = left;
        left = l_hold;
        right = r_hold;
        if (left < pivot)
            quicksort(suffixArray, left, pivot-1);
        if (right > pivot)
            quicksort(suffixArray, pivot+1, right);
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
        for(int j = 0; end > j+start  ;j++){
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
        if(left <= 0) left = 0;
                for(int i = left/*0*/ ;mySpace.length > i ; i++){
                    if(targetCompare(i,start,end) == 0) return i;
                }
        return suffixArray.length;
    }
    private int subByteEndIndex(int start, int end) {
        // It returns the next index of the first suffix which is greater than subBytes; // not implemented yet
        // For "Ho", it will return 7 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
        if(right >= mySpace.length) right=mySpace.length-1;
                for(int i =right/*mySpace.length - 1*/ ;0 <= i ; i--){
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
        
        left = start;
        right = mySpace.length;
        int result;
        int bmid = -1;
        int b2mid = -1;
        int endflag = 0;
        
        while(endflag == 0){
            mid = left + (right - left) / 2;
            //b2mid = bmid;
            //bmid = mid;
            //if(bmid == b2mid) mid++;
            //System.out.println("mid is a "+mid);
            result = targetCompare(mid,0,myTarget.length);
            if(result == 0) break;
            else if(result == 1) right = mid - 1;
            else left = mid + 1;
            
            //if(left >= right){
            //    left--;
            //    right++;
            //}
            //System.out.println("left is a "+left);
            //System.out.println("rght is a "+right);
            System.out.println("left:"+left+" mid:"+mid+" right:"+right);
            //System.out.println(mid);
            if(left >= right){
                left = left - 10;
                right = right + 10;
                endflag = 1;
            }
        }
        System.out.println("end");
        System.out.println("left:"+left+" mid:"+mid+" right:"+right);
        
        int first = subByteStartIndex(start,end);
        int last1 = subByteEndIndex(start, end);
        
        //int first = subByteStartIndex(left,mid);
        //int last1 = subByteEndIndex(mid, right);
        /* inspection code
         for(int k=start;k<end;k++) { System.out.write(myTarget[k]); } System.out.printf(": first=%d last1=%d\n", first, last1);
         */
        
        System.out.println("first is "+first);
        System.out.println("last1 is "+last1);
        return last1 - first;
    }
    public void setTarget(byte [] target) {
        myTarget = target; if(myTarget.length>0) targetReady = true;
    }
    
    public int frequency() {
        if(targetReady == false) return -1; if(spaceReady == false) return 0;
        if(myTarget.length == 0) return -1;
        return subByteFrequency(0, myTarget.length);
    }
    
    public static void main(String[] args) {
        Frequencer frequencerObject;
        try {
            frequencerObject = new Frequencer();
            //frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
            //frequencerObject.setTarget("H".getBytes());
            frequencerObject.setSpace("3210321001230123".getBytes());
            frequencerObject.setTarget("32103".getBytes());
            int result = frequencerObject.frequency();
            System.out.println("Freq = "+ result+" ");
            //if(4 == result) { System.out.println("OK"); }
            //else {System.out.println("WRONG"); }
        }
        catch(Exception e) {
            System.out.println("STOP");
        }
    }
}