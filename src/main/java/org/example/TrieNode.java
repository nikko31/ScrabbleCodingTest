package org.example;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
  private final Map<Character, TrieNode> children = new HashMap<>();

  public boolean isEndOfWord() {
    return isEndOfWord;
  }

  private boolean isEndOfWord;

  public void insert(String word) {
    TrieNode current = this;
    for (char character : word.toCharArray()) {
      current = current.children.computeIfAbsent(character, c -> new TrieNode());
    }
    current.isEndOfWord = true;
  }

  public TrieNode getNode(Character character){
    return this.children.get(character);
  }
}