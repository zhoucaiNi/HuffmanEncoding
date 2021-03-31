public class FrequencyNode {
    private char character;
    private int freq;

    public FrequencyNode(char character, int frequency){
        this.character= character;
        this.freq= frequency;
    }

    public FrequencyNode(int frequency){
        this.freq = frequency;
    }

    public char getCharacter(){
        return character;
    }
    public int getFrequency(){
        return freq;
    }

    public String toString(){
        return getCharacter() + "|" + getFrequency();
    }
}

