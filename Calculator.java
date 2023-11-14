public class  Calculator{

    public static String extractWholeNumber(String number){
        //This method takes String number and returns the whole
        //number portion of the nummber as a String
        String wholeNumber;
        int decimalPoint = number.indexOf('.');
        wholeNumber = number.substring(0,decimalPoint);
        return wholeNumber;
    }

    public static String extractDecimal(String number){
        //This method takes String number and returns the decimal
        //part of the number as a String
        String decimal;
        int decimalPoint = number.indexOf('.');
        decimal = number.substring(decimalPoint + 1);
        return(decimal);
    }

    public static String prependZeros(String number,int numZeros){
        //This method takes String number and an integer numZeros and
        //prepends numZeros to the string number
        String newString;
        newString = number;
        for (int i=0; i<numZeros;i++){
            newString = "0" + newString;
        }
        return newString;
    }

    public static String appendZeros(String number, int numZeros){
        //This method takes String number and an integer numZeros and
        //prepends appendZeros
        String newString;
        newString = number;
        for (int i=0;i<numZeros;i++){
            newString = newString + "0";
        }
        return newString;
    }

    public static String formatResult(String number){
        //This method takes a String number and returns a formatted
        //result wth no leading or trailing zeros, and with numbers on both
        //sides of the decimal point.
        String newNumber;
        newNumber = number;
        if (number.indexOf('.')==-1){
            newNumber = appendZeros(newNumber+".",1);
        }
        if (number.indexOf('.')==0){
            newNumber = prependZeros(newNumber, 1);
        }
        if (number.indexOf('.')==(number.length()-1)){
            newNumber = appendZeros(newNumber,1);
        }
        //this for loop for leading zeros
        String wholeNumber;
        wholeNumber = extractWholeNumber(newNumber);
        int count = 0;
        for (int i=0;i<wholeNumber.length()-1;i++){
            if (wholeNumber.charAt(i) > 48 && wholeNumber.charAt(i) < 58){
                break;
            }
            else{
                count++;
            }
        }
        newNumber = newNumber.substring(count);
        //this for loop for trailing zeros
        String decimal;
        decimal = extractDecimal(newNumber);
        count = 0;
        for (int i = decimal.length()-1;i>0;i--){
            if (decimal.charAt(i)>48 && decimal.charAt(i)<58){
                break;
            }
            else{
                count++;
            }
        }
        newNumber = newNumber.substring(0,newNumber.length()-count);
        return(newNumber);
    }
    public static char addDigits(char firstDigit,char secondDigit, boolean carryIn){
        //This method adds two single digits and accounts for whether,
        //there was a carry going into the addition.
        int carry = carryIn ? 1 : 0;
        int sum = (int) firstDigit + (int) secondDigit + carry;
        sum = sum - (2 * 48);
        String sumFinal = Integer.toString(sum);
        return(sumFinal.charAt(sumFinal.length()-1));
    }

    public static boolean carryOut(char firstDigit,char secondDigit,boolean carryIn){
        //This method considers the digits you are adding and decides whether they
        //will produce a carry.
        int carry = carryIn ? 1 : 0;
        int sum = (int) firstDigit + (int) secondDigit + carry;
        sum = sum - (2*48);
        String sumFinal = Integer.toString(sum);
        if (sumFinal.length()>1){
            return true;
        }
        else{
            return false;
        }
    }

    public static String add(String firstNumber, String secondNumber){
        //This method takes the two numbers as Strings and returns the
        //answer as a properly formatted String. Should just be calling other methods
        //in this method. Will follow guide given in GitHub
        String top = formatResult(firstNumber);
        String bottom = formatResult(secondNumber);
        //First I will equalize the whole number portionss
        if (extractWholeNumber(top).length()<extractWholeNumber(bottom).length()){
            top = prependZeros(top,extractWholeNumber(bottom).length()-extractWholeNumber(top).length());
        }
        if (extractWholeNumber(top).length()>extractWholeNumber(bottom).length()){
            bottom = prependZeros(bottom,extractWholeNumber(top).length()-extractWholeNumber(bottom).length());
        }
        //Then I will equalize the decimal portions
        if (extractDecimal(top).length()<extractDecimal(bottom).length()){
            top = appendZeros(top,extractDecimal(bottom).length()-extractDecimal(top).length());
        }
        if (extractDecimal(top).length()>extractDecimal(bottom).length()){
            bottom = appendZeros(bottom,extractDecimal(top).length()-extractDecimal(bottom).length());
        }
        //Now the addition is set up perfectly. I will write a for loop that goes right to
        //left through the elements and adds them one by one using the method addDigits
        String ans="";
        boolean carry = false;
        for (int i = top.length()-1;i>-1;i--){
            if (top.charAt(i)=='.'){
                ans = '.' +ans;
            }
            else{
                ans = String.valueOf(addDigits(top.charAt(i),bottom.charAt(i),carry)) + ans;
                carry = carryOut(top.charAt(i),bottom.charAt(i),carry);
            }
            if (i==0 && carry){
                ans = "1" + ans;
            }

        }
        formatResult(ans);
        return ans;
    }
    
    public static String multiply(String number,int numTimes){
        //This method takes a number in String form and the int numTimes, and
        //returns the product of number and numTimes in properly formatted
        //String form. It does so by adding number to itself numTimes times.
        if (numTimes<0){
            return(formatResult(number));
        }
        String ans = "0";
        for (int i = 0; i<numTimes;i++){
            ans = add(number,ans);
        }
        return(formatResult(ans));
    }
}