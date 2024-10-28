import java.util.Scanner;

/**
 * This program stores the information for a family of up to 6 people in the Florida Dental Association
 *
 * @author Josue Munguia
 */
//=================================================================================================

public class FloridaDentalAssociation {
    /**
     * Global scanner used for keyboard inputs
     */
    private static final Scanner keyboard = new Scanner(System.in);
    /**
     * Maximum allowed family members
     */
    private static final int MAX_FAMILY_MEMBERS = 6;
    /**
     * Max allowed row numbers
     */
    private static final int MAX_ROWS = 2;
    /**
     * Max allowed teeth in each row
     */
    private static final int MAX_TEETH = 8;
    /**
     * Incisor teeth
     */
    private static final char I = 'I';
    /**
     * Bicuspid teeth
     */
    private static final char B = 'B';
    /**
     * Missing tooth
     */
    private static final char M = 'M';


    //-------------------------------------------------------------------------------------------------

    /**
     * Main method gets the family size and assigns the names of each person to an array of strings. A Three-Dimensional
     *                     array is then created to store the teeth information for each member. Also prompts the menu options
     *                     for the user to choose from, sending each option to its respective method.
     * @param args Main method gets the family size and assigns the names of each person to an array of strings. A Three-Dimensional
    array is then created to store the teeth information for each member. Also prompts the menu options
    for the user to choose from, sending each option to its respective method.
     *
     */
    public static void main(String[] args) {

        String[] names = new String[MAX_FAMILY_MEMBERS]; // Array to hold names of family members
        char[][][] teeth = new char[MAX_FAMILY_MEMBERS][MAX_ROWS][MAX_TEETH]; // 3D array for teeth (6 members, 2 rows, 8 columns)
        int familySize;
        int memberIndex;


        // Welcome message
        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        // Get family size
        System.out.print("Enter the number of people in the family    : ");
        familySize = keyboard.nextInt();
        while (familySize < 1 || familySize > MAX_FAMILY_MEMBERS) {
            System.out.print("Invalid number of people, try again         : ");
            familySize = keyboard.nextInt();
        }
        keyboard.nextLine();

        // Get each family member's information
        for (memberIndex = 0; memberIndex < familySize; memberIndex++) {
            System.out.print("Please enter the name for family member " + (memberIndex + 1) + "   : ");
            names[memberIndex] = keyboard.nextLine();

            teeth[memberIndex][0] = getTeeth(names[memberIndex], "uppers");
            teeth[memberIndex][1] = getTeeth(names[memberIndex], "lowers");
        }


        // Menu loop
        while (true) {
            System.out.println();
            System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
            String choice = keyboard.nextLine().toUpperCase();

            while (true) {
                switch (choice) {
                    case "P":
                        printTeeth(names, teeth, familySize);
                        break;
                    case "E":
                        extractTooth(names, teeth, familySize);
                        break;
                    case "R":
                        reportRootCanals(teeth, familySize);
                        break;
                    case "X":
                        System.out.println();
                        System.out.println("Exiting the Floridian Tooth Records :-)");
                        keyboard.close();
                        return;
                    default:
                        System.out.print("Invalid menu option, try again              : ");
                        choice = keyboard.nextLine().toUpperCase();
                        continue; // Restart the inner loop to check the new choice
                }
                break; // Exit the inner loop if a valid option was selected
            }
        }

    }// end of main method

    //=================================================================================================


    /**
     *
     * This method gets the teeth in each row of a family member's mouth
     * @param name Imports the string of the family names
     * @param layer Imports which layer is being requested
     * @return returns the teeth array if it is valid
     */
    private static char[] getTeeth(String name, String layer) {
        char[] teethArray;

        System.out.printf("Please enter the %s for %s%s: ", layer, name, " ".repeat(42 - (20 + layer.length() +
                name.length())));

        while (true) {
            String input = keyboard.nextLine().toUpperCase();

            // Check for valid teeth string
            if (isValidTeethString(input)) {
                teethArray = input.toCharArray();
                if (teethArray.length <= MAX_TEETH) {
                    return teethArray; // Valid input, return the teeth array
                } else {
                    System.out.print("Too many teeth, try again                   : ");
                }
            } else {
                System.out.print("Invalid teeth types, try again              : ");
            }
        }
    }// end of getTeeth method

    //=================================================================================================

    /**
     * this method checks whether the letters in the string of teeth provided is valid only having I, B, or M letters
     * @param input imports the string of teeth provided by the user
     * @return returns boolean true or false if the sting only
     */
    private static boolean isValidTeethString(String input) {
        char letter; // Declare the variable outside the loop
        int letterIndex;
        for (letterIndex = 0; letterIndex < input.length(); letterIndex++) {
            letter = input.charAt(letterIndex); // Assign value inside the loop
            if (letter != I && letter != B && letter != M) {
                return false;
            }
        }
        return true;
    } // end of isValidTeethString method


    //=================================================================================================

    /**
     * This method prints the tooth records for each family member
     * @param names Imports the string of names from the main method
     * @param teeth Imports the 3-dimensional array for the teeth
     * @param familySize imports the size of the family to only go through the array up to that value
     */
    private static void printTeeth(String[] names, char[][][] teeth, int familySize) {
        int memberIndex;
        for (memberIndex = 0; memberIndex < familySize; memberIndex++) {
            System.out.println(names[memberIndex]);
            System.out.print("Uppers: ");
            printToothLayer(teeth[memberIndex][0]);
            System.out.print("Lowers: ");
            printToothLayer(teeth[memberIndex][1]);
        }
    }// end of printTeeth method

    //=================================================================================================

    /**
     * This method helps the printTeeth method by writing the teeth found in a readable line with each tooth number
     * @param layer Imports the array of characters to print them as part of the print method
     */

    private static void printToothLayer(char[] layer) {
        int toothIndex;
        for (toothIndex = 0; toothIndex < layer.length; toothIndex++) {
            System.out.print((toothIndex + 1) + ":" + layer[toothIndex] + " ");
        }
        System.out.println();
    }// end of printToothLayer method

    //=================================================================================================

    /**
     * This method extracts a tooth from a family member, labeling it as missing upon completion
     * @param names Imports the names for the family members to be identified
     * @param teeth Imports the 3-dimensional array with the names and the teeth
     * @param familySize imports the family size to be used in a call to a method within this method
     */

    private static void extractTooth(String[] names, char[][][] teeth, int familySize) {
        String familyMember;
        int memberIndex = -1;
        int toothLayer;
        int toothNumber;
        String layer;

        System.out.print("Which family member                         : ");
        // Loop until a valid family member is entered
        while (memberIndex == -1) {
            familyMember = keyboard.nextLine().trim();
            memberIndex = findMemberIndex(names, familyMember, familySize);

            if (memberIndex == -1) {
                System.out.print("Invalid family member, try again            : ");
            }
        }

        // Prompt for tooth layer initially
        System.out.print("Which tooth layer (U)pper or (L)ower        : ");

        // Loop to handle tooth layer and tooth number
        while (true) {
            layer = keyboard.nextLine().toUpperCase();

            if (layer.equals("U") || layer.equals("L")) {
                toothLayer = layer.equals("U") ? 0 : 1; // assigns true or false depending on if it matches U or L

                System.out.print("Which tooth number                          : ");
                while (true) {
                    toothNumber = keyboard.nextInt() - 1; // Convert to 0-based index
                    keyboard.nextLine();

                    if (toothNumber >= 0 && toothNumber < teeth[memberIndex][toothLayer].length) {
                        if (teeth[memberIndex][toothLayer][toothNumber] != M) {
                            // Extract the tooth (mark as missing)
                            teeth[memberIndex][toothLayer][toothNumber] = M;
                            return; // Exit the method after successful extraction
                        } else {
                            System.out.print("Missing tooth, try again                    : ");
                        }
                    } else {
                        System.out.print("Invalid tooth number, try again             : ");
                    }
                }
            } else {
                // Invalid layer, prompt again in the same line
                System.out.print("Invalid layer, try again                    : ");
            }
        }
    }// end of extractTooth method
    //=================================================================================================

    /**
     * This method finds the index for the family member name provided
     * @param names Imports the names of the family members
     * @param familyMember Imports which member of the family the method is searching the index for
     * @param familySize Imports the family size so that the for loop does not read any null values
     * @return returns value for the index of the family member
     */
    private static int findMemberIndex(String[] names, String familyMember, int familySize) {
        int index;
        for (index = 0; index < familySize; index++) {
            if (names[index].equalsIgnoreCase(familyMember)) {
                return index;
            }
        }
        return -1; // Not found
    }// end of findMemberIndex method

    //=================================================================================================

    /**
     * This method goes through the array of teeth and counts the number for each teeth for the whole family and
     calculates the roots using the quadratic formula
     * @param teeth Imports the array storing the teeth for each family member to go through it and count the teeth
     * @param familySize Imports the family size so that the for loop does not read any null values
     */

    private static void reportRootCanals(char[][][] teeth, int familySize) {
        int totalCountI = 0;
        int totalCountB = 0;
        int totalCountM = 0;
        int memberIndex;
        double root1;
        double root2;

        // Iterate through each family member and count the teeth
        for (memberIndex = 0; memberIndex < familySize; memberIndex++) {
            for (char tooth : teeth[memberIndex][0]) { // Uppers
                if (tooth == I) totalCountI++;
                else if (tooth == B) totalCountB++;
                else if (tooth == M) totalCountM++;
            }
            for (char tooth : teeth[memberIndex][1]) { // Lowers
                if (tooth == I) totalCountI++;
                else if (tooth == B) totalCountB++;
                else if (tooth == M) totalCountM++;
            }
        }
        double a = totalCountI;
        double b = totalCountB;
        double c = -totalCountM;

        // Calculate the roots of the quadratic equation ax^2 + bx + c = 0
        double discriminant = b * b - 4 * a * c;
        if (discriminant >= 0) {
            root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            System.out.printf("One root canal at %.2f\n", root1);
            System.out.printf("Another root canal at %.2f\n", root2);
        } else {
            System.out.println("No real roots for total counts: I=" + totalCountI + ", B=" + totalCountB + ", M=" +
                    totalCountM);
        }
    }// end of reportRootCanals method

}// end of HelpMe public class
