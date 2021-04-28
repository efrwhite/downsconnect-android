package com.example.downsconnect.objects;

public class Month {
    public Month(){

    }
    public String getMonth(int month){
        String stringMonth;
        switch (month){
            case 0:
                stringMonth = "Jan";
                break;
            case 1:
                stringMonth = "Feb";
                break;
            case 2:
                stringMonth = "Mar";
                break;
            case 3:
                stringMonth = "Apr";
                break;
            case 4:
                stringMonth = "May";
                break;
            case 5:
                stringMonth = "Jun";
                break;
            case 6:
                stringMonth = "Jul";
                break;
            case 7:
                stringMonth = "Aug";
                break;
            case 8:
                stringMonth = "Sept";
                break;
            case 9:
                stringMonth = "Oct";
                break;
            case 10:
                stringMonth = "Nov";
                break;
            default:
                stringMonth = "Dec";
                break;
        }
       return stringMonth;
    }
}
