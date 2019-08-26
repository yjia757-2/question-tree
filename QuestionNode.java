// Yiran Jia
// 5/24/18
// CSE143
// TA: JASON WAATAJA 
// Assignment 7: Guessing Game  - Question Node
//
// Class for storing a single node of a binary tree of strings

import java.util.*;

public class QuestionNode {
   public String content;
   public QuestionNode yes;
   public QuestionNode no;

   // constructs a answer node with given content
   public QuestionNode(String content) {
      this(content, null, null);
   }
   
   // constructs a question node with given content, yes subtree and no subtree
   public QuestionNode(String content, QuestionNode yes, QuestionNode no) { 
      this.content = content;
      this.yes = yes;
      this.no = no; 
   }
}