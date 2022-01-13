package fakru.wordle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Wordle {

  private final Map<Character, List<Integer>> visitedPositions = new HashMap<>();

  public Map<Character, List<Integer>> getVisitedPositions() {
    return visitedPositions;
  }

  public static void main(String[] args) throws FileNotFoundException {
    List<String> availableWords = new ArrayList<>();
    File myFile = new File(
        "/Users/fakrudeenail/Documents/IntelliJ/src/fakru/wordle/five-letter-words.txt");
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
          .filter(word -> WordleUtils.filterIncorrectPositionsV2(word, incorrectPositions))
          .filter(word -> WordleUtils.filterCorrectPositions(word, correctPosition))
          .collect(Collectors.toList());
      System.out.println(availableWords);
      ++tries;
    } while (tries <= 5);
    input.close();
  }
}