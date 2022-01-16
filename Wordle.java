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

  private static Wordle wordle;
  private final Map<Character, List<Integer>> visitedPositions = new HashMap<>();
  private String incorrectPositions;
  private String correctPositions;
  private String excludedLetters;

  public Map<Character, List<Integer>> getVisitedPositions() {
    return visitedPositions;
  }

  public String getIncorrectPositions() {
    return incorrectPositions;
  }

  public void setIncorrectPositions(String incorrectPositions) {
    this.incorrectPositions = incorrectPositions;
  }

  public String getCorrectPositions() {
    return correctPositions;
  }

  public void setCorrectPositions(String correctPositions) {
    this.correctPositions = correctPositions;
  }

  public String getExcludedLetters() {
    return excludedLetters;
  }

  public void setExcludedLetters(String excludedLetters) {
    this.excludedLetters = excludedLetters;
  }

  public static Wordle getInstance() {
    if (wordle == null) {
      wordle = new Wordle();
    }
    return wordle;
  }

  public static void main(String[] args) throws FileNotFoundException {
    getInstance().executeWordle();
  }

  private void executeWordle() throws FileNotFoundException {
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
      print("ENTER - to ignore that filter");
      print("letters with incorrect positions (YELLOW COLOR)");
      setIncorrectPositions(input.next());
      print("letters that has to be excluded (GREY COLOR)");
      setExcludedLetters(input.next());
      print("letters with correct positions in the format a*b** (GREEN COLOR)");
      setCorrectPositions(input.next());
      availableWords = availableWords.stream()
          .filter(WordleUtils::filterExcludedLetters)
          .filter(WordleUtils::filterIncorrectPositions)
          .filter(WordleUtils::filterCorrectPositions)
          .collect(Collectors.toList());
      print("Available words in list : " + availableWords.size());
      print(availableWords);
      List<String> uniqueWords = WordleUtils.uniqueWordsList(availableWords);
      print("Words with max unique letters & max vowels from available words : "
          + uniqueWords.size());
      print(uniqueWords);
      ++tries;
    } while (tries <= 5);
    input.close();
  }

  private void print(String s) {
    System.out.println(s);
  }

  private void print(List<String> list) {
    System.out.println(list);
  }
}