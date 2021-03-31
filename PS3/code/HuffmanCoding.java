import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCoding {
    private Map<Character, Integer> fTable;
    private Map<Character, String> codeMap;
    private PriorityQueue<BinaryTree<FrequencyNode>> treeQueue;

//    private String pathName = "inputs/USConstitution.txt";
    private String pathName =  "inputs/WarAndPeace.txt";

//    test cases
//    private String pathName = "inputs/simpleTest.txt";
//    private String pathName = "inputs/emptyFileTest.txt";
//    private String pathName = "inputs/singleCharacterTest.txt";
//    private String pathName = "inputs/repeatedSingleCharacter.txt";


    private String compressedPathName = pathName.substring(0, pathName.indexOf(".txt")) + "_compressed.txt";
    private String decompressedPathName = pathName.substring(0, pathName.indexOf(".txt")) + "_decompressed.txt";



    public HuffmanCoding(){
    fTable = new HashMap<Character, Integer>();
    Comparator<BinaryTree<FrequencyNode>> treeComparator = new TreeComparator();
    treeQueue = new PriorityQueue<BinaryTree<FrequencyNode>>(treeComparator);
    codeMap = new HashMap<Character, String>();

    }
    public static void main(String[] args){
        HuffmanCoding hC = new HuffmanCoding();

        // creates the frequency table
        hC.createFrequencyTable();
//        System.out.println(hC.getfTable());

        // turns the frequencies into initial node of tree and add them to a priority queue
        hC.addToPQ();
//        System.out.println(hC.getTreeQueue());

        // builds the tree
        hC.treeBuilding();

        // generate the code map
        hC.generateCodeMap();
//        System.out.println(hC.getCodeMap());

        // compress the file stated in pathName into compressed file using code map
        hC.compression();

        // decompress the compressed file
        hC.decompression();

    }

    // Main functions

    public void createFrequencyTable(){
        FrequencyTable fT1 = new FrequencyTable();
        try{
            fT1.charFrequency(pathName);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        fTable = fT1.getfTable();

    }

    public void addToPQ(){
        for(Character c: fTable.keySet()){
            BinaryTree<FrequencyNode> node = new BinaryTree<FrequencyNode>(new FrequencyNode(c, fTable.get(c)));
            treeQueue.add(node);
        }
    }

    public void treeBuilding(){
        while(treeQueue.size() > 1){
            // take the first two element of queue
            BinaryTree<FrequencyNode> b1 = treeQueue.remove();
            BinaryTree<FrequencyNode> b2 = treeQueue.remove();

            // create a new tree with the parent as total freq and the two node as children
            int totalFreq = b1.getData().getFrequency() + b2.getData().getFrequency();
            BinaryTree<FrequencyNode> parent = new BinaryTree<>(new FrequencyNode(totalFreq), b1, b2);

            treeQueue.add(parent);
        }
    }

    public void generateCodeMap(){

       BinaryTree<FrequencyNode> huffmanTree = treeQueue.peek();
       String code = new String();
       traverse(huffmanTree, code);

        if(codeMap.size() == 1) {
            codeMap.replace(treeQueue.peek().getData().getCharacter(), "1");
        }

    }

    public void traverse(BinaryTree<FrequencyNode> huffmanTree, String code){
        if(huffmanTree != null) {

            if (huffmanTree.isLeaf()) {
                codeMap.put(huffmanTree.data.getCharacter(), code);
            }

            if (huffmanTree.hasLeft()) {
                traverse(huffmanTree.getLeft(), code + "0");
            }

            if (huffmanTree.hasRight()) {
                traverse(huffmanTree.getRight(), code + "1");
            }


        }
    }


    public void compression(){

        try {
            BufferedBitWriter bitOutput = new BufferedBitWriter(compressedPathName);
            BufferedReader input = new BufferedReader(new FileReader(pathName));

            int character;

            //reads the file
            while ((character = input.read()) != -1) {

                char c = (char) (character);

                // use the codeMap
                String code = codeMap.get(c);

                for(int i = 0; i<code.length();i++){
                    String letter = code.substring(i,i+1);
                    if(letter.equals("0")) bitOutput.writeBit(false);
                    else bitOutput.writeBit(true);
                }
            }

            bitOutput.close();
            input.close();
        }

        catch (IOException e){
            System.out.println(e);
        }

    }


    public void decompression() {

        try{
            BufferedBitReader bitInput = new BufferedBitReader(compressedPathName);
            BufferedWriter output = new BufferedWriter(new FileWriter(decompressedPathName));
            BinaryTree<FrequencyNode> tree = treeQueue.peek();
            BinaryTree<FrequencyNode> t1 = tree;
            boolean bit;

            while(bitInput.hasNext()){

                if(!t1.isLeaf()) {
                    bit = bitInput.readBit();
                    if (!bit) t1 = t1.getLeft();
                    else t1 = t1.getRight();
                }


                if (t1.isLeaf()){
                    if(codeMap.size() == 1){
                        bitInput.readBit();
                    }
                    char letter = t1.getData().getCharacter();
                    output.write(letter);
                    t1 = tree;


                }


            }

            bitInput.close();
            output.close();

        }
        catch( IOException e){
            System.out.println(e);
        }

    }


    // Getter functions
    public Map<Character, Integer> getfTable() {
        return fTable;
    }

    public PriorityQueue<BinaryTree<FrequencyNode>> getTreeQueue() {
        return treeQueue;
    }

    public Map<Character, String> getCodeMap() {
        return codeMap;
    }
}
