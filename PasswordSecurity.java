import java.util.Scanner;
//Importing Scanner

public class PasswordSecurity {
    //Class in which I am doing my work
    public static void main(String[] args){
        //The main method which is the only method for this PA
        //Creating Scanner String object to get an input from user, as well as
        //creating variables for uppercase, lowercase, number, and symbol counts.
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Please enter a password: ");
        String password = keyboard.nextLine();
        int U = 0;
        int L = 0;
        int num = 0;
        int sym = 0;
        //Minimum password length requirement
        if (password.length()<8){
            System.out.println("Password is too short");
            return;
        }
        //Loop which counts the number of each category of character.
        for (int i = 0; i < password.length(); i++){
            if (password.charAt(i)>= 65 && password.charAt(i)<=90){
                ++U;
            }
            else if (password.charAt(i)>=97 && password.charAt(i)<=122){
                ++L;
            }
            else if (password.charAt(i)>=48 && password.charAt(i)<=57){
                ++num;
            }
            else if (password.charAt(i)>=33 && password.charAt(i)<=126){
                ++sym;
            }
            else {
                System.out.println("Invalid Character Type");
                return;
            }
        }
        //Creating a count for the number of categories the password contains.
        int category = 0;
        if (U>0){
            ++category;
        }
        if (L>0){
            ++category;
        }
        if (num>0){
            ++category;
        }
        if (sym>0){
            ++category;
        }
        //Loop for printing out the password strength.
        if (category == 4){
            System.out.println("Password strength: strong");
            return;
        }
        else if (category == 3){
            System.out.println("Password strength: medium");
        }
        else if (category == 2){
            System.out.println("Password strength: weak");
        }
        else {
            System.out.println("Password strength: very weak");
        }
        //Applying rules for stronger password suggestion.
        //Rule 1 - If less than 2 letters, prepend Cse
        String CurrentPassword = password;
        int ascii[] = new int[L];
        int n = 0;
        if (U+L<2){
            CurrentPassword = "Cse" + password;
        }
        //Rule 2 - If no lowercase, change first uppercase to lowercase
        else if (L == 0) {
            for (int i = 0; i < password.length(); i++){
                if (password.charAt(i)>= 65 && password.charAt(i)<=90){
                    int swap = password.charAt(i) + 32;
                    char letterswap = (char) swap;
                    String LetterSwap = Character.toString(letterswap);
                    if (i == 0) {
                        CurrentPassword = LetterSwap + password.substring(i+1);
                    }
                    else{
                    CurrentPassword = password.substring(0,i-1) + LetterSwap + password.substring(i+1);
                    }
                    break; 
                }
            }
        }
        //Rule 3 - If no uppercase, change the last of the highest ASCII
        //valued lowercase to uppercase
        else if (U==0){
            for (int i = 0;i<password.length();i++){
                if (password.charAt(i)>=97 && password.charAt(i)<=122){
                    ascii[n] = password.charAt(i);
                    n++;
                }
            }
            int max = ascii[0];
            for (int x=1;x<ascii.length;x++){
                if (ascii[x]>max){
                    max = ascii[x];
                }
            }
            int LastIndex = password.lastIndexOf((char) max);
            if (LastIndex==0){
                CurrentPassword = Character.toString((char) (password.charAt(LastIndex)-32)) + password.substring(1);
            }
            else {
                CurrentPassword = password.substring(0,LastIndex) + Character.toString((char) (password.charAt(LastIndex)-32)) + password.substring(LastIndex+1);
            }

        }
        //Rule 4 - If no numbers, insert k into current password every 4 chars,
        //k is the original length of the password before any rules applied mod 10
        //If password length after rules 1-3 is divisible by 4, put k at end as well
        String r4 = "";
        if (num == 0){
            int k = password.length()%10;
            for (int i=0;i<CurrentPassword.length();i+=4){
                if (i+4>CurrentPassword.length()){
                    r4 = r4 + CurrentPassword.substring(i);
                }
                else{
                r4 = r4 + CurrentPassword.substring(i,i+4) + String.valueOf(k);
                }
            }
         CurrentPassword = r4;  
        }
        //Rule 5 - If no symbols, append @! to the password.
        if (sym == 0){
            CurrentPassword = CurrentPassword + "@!";
        }
        System.out.println("Here is a suggested stronger password: " + CurrentPassword);

    }
}