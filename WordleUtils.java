package fakru.wordle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class WordleUtils {

  private WordleUtils() {
  }

  public static boolean filterExcludedLetters(String word, String excludedWord) {
    if (excludedWord.equalsIgnoreCase("-")) {
      return true;
    }
    for (char c : excludedWord.toUpperCase().toCharArray()) {
      if (word.indexOf(c) != -1) {
        return false;
      }
    }
    return true;
  }

  public static boolean filterIncorrectPositions(String word, String incorrectPositions) {
    if (incorrectPositions.equalsIgnoreCase("-")) {
      return true;
    }
    for (char c : incorrectPositions.toUpperCase().toCharArray()) {
      if (word.indexOf(c) == -1) {
        return false;
      }
    }
    return true;
  }

  public static boolean filterIncorrectPositionsV2(String word, String incorrectPositions) {
    if (incorrectPositions.equalsIgnoreCase("-")) {
      return true;
    }
    incorrectPositions = incorrectPositions.toUpperCase();
    Map<Character, List<Integer>> positions = new Wordle().getVisitedPositions();
    for (int i = 0; i < incorrectPositions.length(); ++i) {
      char c = incorrectPositions.charAt(i);
      if (c == '*') {
        continue;
      }
      if (positions.containsKey(c)) {
        positions.get(c).add(i);
      } else {
        List<Integer> list = new ArrayList<>();
        list.add(i);
        positions.put(c, list);
      }
    }
    for (int i = 0; i < incorrectPositions.length(); ++i) {
      char c = incorrectPositions.charAt(i);
      if (c == '*') {
        continue;
      }
      if (word.indexOf(c) == -1 || positions.get(c).contains(word.indexOf(c))) {
        return false;
      }
    }
    return true;
  }

  public static boolean filterCorrectPositions(String word, String correctPositions) {
    if (correctPositions.equalsIgnoreCase("-")) {
      return true;
    }
    correctPositions = correctPositions.toUpperCase();
    for (int i = 0; i < word.length(); ++i) {
      if (correctPositions.charAt(i) != '*' && correctPositions.charAt(i) != word.charAt(i)) {
        return false;
      }
    }
    return true;
  }

  public static List<String> betterGuess(List<String> availableWords) {
    List<String> result = new ArrayList<>();
    long maxLength = getDistinctCount(availableWords.get(0));
    for (String word : availableWords) {
      if (getDistinctCount(word) == maxLength) {
        result.add(word);
      } else if (getDistinctCount(word) > maxLength) {
        result = new ArrayList<>();
        result.add(word);
      }
    }
    return result;
  }

  private static long getDistinctCount(String word) {
    return word.chars().distinct().count();
  }
}