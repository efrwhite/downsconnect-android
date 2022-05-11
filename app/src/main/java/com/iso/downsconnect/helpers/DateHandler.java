package com.iso.downsconnect.helpers;

//Helper class to handle any problems with dates
public class DateHandler {
    //constructor to create the object
    public DateHandler(){

    }

    //method for converting a month's number into it's abbreviation
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

    //creates a string with the format month day, year
    public String writtenDate(int month, int day, int year){
        return getMonth(month) + " " + day + ", " + year;
    }

    //gets written date with format month day, year from a date string
    public String writtenDateWithString(String date){
        String[] splitDate = date.split("-");
        String month = splitDate[0];
        int numMonth = Integer.parseInt(month);

        String writtenDate = getMonth(numMonth - 1) + " " + splitDate[1] + ", " + splitDate[2];

        return writtenDate;
    }
}
