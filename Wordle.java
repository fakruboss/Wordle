package fakru.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Wordle {

  public static void main(String[] args) throws FileNotFoundException {
    List<String> availableWords = new ArrayList<>();
    File myFile = new File("/Users/fakrudeenail/Documents/IntelliJ/src/fakru/wordle/five-letter-words.txt");
    Scanner fileScanner = new Scanner(myFile);
    Scanner input = new Scanner(System.in);
    while (fileScanner.hasNextLine()) {
      String line = fileScanner.nextLine();
      Collections.addAll(availableWords, line.split(" "));
    }
    fileScanner.close();
    int tries = 0;
    do {
      System.out.println("ENTER - to ignore that filter");
      System.out.println("letters with incorrect positions (YELLOW COLOR)");
      String incorrectPositions = input.next();
      System.out.println("letters that has to be excluded (GREY COLOR)");
      String excludedLetters = input.next();
      System.out.println("letters with correct positions in the format a*b** (GREEN COLOR)");
      String correctPosition = input.next();
      availableWords = availableWords.stream()
          .filter(word -> WordleUtils.filterExcludedLetters(word, excludedLetters))
          .filter(word -> WordleUtils.filterIncorrectPositions(word, incorrectPositions))
          .filter(word -> WordleUtils.filterCorrectPositions(word, correctPosition))
          .collect(Collectors.toList());
      System.out.println(availableWords);
      ++tries;
    } while (tries <= 5);
    input.close();
  }
}