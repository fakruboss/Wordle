package fakru.wordle;

class WordleUtils {

  private WordleUtils() {
  }

  public static boolean filterExcludedLetters(String word, String excludedWord) {
    if (excludedWord.equalsIgnoreCase("-")) {
      return true;
    }
    for (char c : excludedWord.toCharArray()) {
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
    for (char c : incorrectPositions.toCharArray()) {
      if (word.indexOf(c) == -1) {
        return false;
      }
    }
    return true;
  }

  public static boolean filterCorrectPositions(String word, String correctPositions) {
    if (correctPositions.equalsIgnoreCase("-")) {
      return true;
    }
    for (int i = 0; i < word.length(); ++i) {
      if (correctPositions.charAt(i) != '*' && correctPositions.charAt(i) != word.charAt(i)) {
        return false;
      }
    }
    return true;
  }
}