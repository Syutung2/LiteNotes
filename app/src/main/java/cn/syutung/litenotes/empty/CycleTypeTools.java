package cn.syutung.litenotes.empty;

public class CycleTypeTools {
    public static int cycleTypeToIntNumber(CycleType cycleType){
        if (cycleType == CycleType.DAY){
            return 0;
        }
        else if (cycleType == CycleType.MONTH){
            return 1;
        }
        else if (cycleType == CycleType.YEAR){
            return 2;
        }
        else{
            return 0;
        }

    }
    public static CycleType cycleTypefromIntNumber(int num){
        if (num == 0) {
            return  CycleType.DAY;

        }else if (num == 1) {
            return  CycleType.MONTH;

        }else if (num == 2) {
            return  CycleType.YEAR;

        }else{
            return  CycleType.DAY;

        }


    }
}
