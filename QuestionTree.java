// Yiran Jia
// 5/24/18
// CSE143
// TA: JASON WAATAJA 
// Assignment 7: Guessing Game  - Question Tree
//
// This program helps intialize a guessing game which has yes/no questions and 
// answers. Client can choose to either read a question tree from a file, or read 
// in previous tree, which has been stored in an output file if clinet wants. 
// It keeps asking user a series of yes/no questions until it either guesses 
// correctly or until it fails, in which case it will ask user about the real 
// answer in the user's mind and a good question to distinguish the wrong answer with 
// the right answer. It will properly modify the game and become smarter. 

import java.io.*;
import java.util.*;

public class QuestionTree {
   private Scanner console;
   private QuestionNode overallRoot;
   
   // post: constructs a question tree with only one object called "computer". 
   // construct a scanner for interacting with user 
   public QuestionTree() {
      console = new Scanner(System.in); 
      overallRoot = new QuestionNode("computer");
   }

   // pre: assumes the file is legal and in standard format
   // post: replaces the current question tree by passing a scanner that is linked 
   // to a file which contains information of a new question tree
   public void read(Scanner input) { 
      overallRoot = readHelper(input);
   }
   
   // post: returns a QuestonNode based on the file that the scanner passes. This 
   // new question tree covers information in the file that the scanner reads
   private QuestionNode readHelper(Scanner input) {
      String line1 = input.nextLine();
      String line2 = input.nextLine();
      QuestionNode root = new QuestionNode(line2);
      if (line1.contains("Q:")) {
         root.yes = readHelper(input); 
         root.no = readHelper(input);
      }
      return root;       
   } 
   
   // post: stores the current tree with pre-order to an output file
   public void write(PrintStream output){
      writeHelper(output, overallRoot);
   }
   
   // post: by passing the current QuestionNode, it stores the current tree to an 
   // output file. It writes "A:" one line above the answer, and write "Q:" one line 
   // above the question. It writes all nodes in pre-order in the output file
   private void writeHelper(PrintStream output, QuestionNode root) {
      if (root != null) {
         if (root.yes == null) { 
            output.println("A:");
            output.println(root.content);
         } else {
            output.println("Q:");
            output.println(root.content);
            writeHelper(output, root.yes);
            writeHelper(output, root.no);
         }
      }
   }     
   
   // post: uses the current tree to ask the user a series of yes/no questions 
   // until it either guesses user's object correctly or until it fails, 
   // in which case it expands the tree to include the user's object 
   // and a new question to distinguish the user's object from the others.
   public void askQuestions() {
      overallRoot = askHelper(overallRoot);
   }
   
   // post: updates the current tree and returns a QuestionNode, or a modified
   // version of question tree. By passing a QuestionNode, which is the current 
   // tree, it asks the user a series of yes/no questions until it either 
   // guesses user's object correctly or until it fails, in which case it expands 
   // the tree to include the user's object and a new question to distinguish 
   // the user's object from the others. 
   private QuestionNode askHelper(QuestionNode root) {
      if (root.yes == null) { 
         if (yesTo("Would your object happen to be " + root.content + "?")) {
            System.out.println("Great, I got it right!");
         } else {
            QuestionNode oldAnswer = root;
            System.out.print("What is the name of your object? ");
            QuestionNode newAnswer = new QuestionNode(console.nextLine());
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            root = new QuestionNode(console.nextLine());
            if (yesTo("And what is the answer for your object?")) {
               root.yes = newAnswer;
               root.no = oldAnswer;
            } else {
               root.yes = oldAnswer;
               root.no = newAnswer;
            }
            return root;
         }
      } else { 
         if (yesTo(root.content)) {
            root.yes = askHelper(root.yes);
         } else {
            root.no = askHelper(root.no);
         }
      }
      return root;
   }

   // post: asks the user a question, forcing an answer of "y " or "n"; 
   // returns true if the answer was yes, returns false otherwise 
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
}

   
   