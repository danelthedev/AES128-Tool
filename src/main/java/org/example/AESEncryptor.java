package org.example;

import java.util.List;

public class AESEncryptor {

    private String key;
    private int rounds;

    private byte[][] cipher(String in, byte[][] keys){
        byte[][] state = new byte[4][4];
        // Copy input to state
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                state[j][i] = (byte) in.charAt(i * 4 + j);
            }
        }
        // add round key
        state = addRoundKey(state, keys);
        // iterate over number of rounds
        for (int round = 1; round < rounds; round++) {
            state = subBytes(state);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, keys);
        }
        // final round
        state = subBytes(state);
        state = shiftRows(state);
        state = addRoundKey(state, keys);

        return state;
    }

    private byte[][] subBytes(byte[][] state) {
        return null;
    }

    private byte[][] shiftRows(byte[][] state) {
        return null;
    }

    private byte[][] mixColumns(byte[][] state) {
        return null;
    }

    private byte[][] addRoundKey(byte[][] state, byte[][] roundKey) {
        return null;
    }

    public List<String> keyExpansion(String key) {
        return null;
    }

    public String invCipher(String in, List<String> keys) {
        return null;
    }

    public String invShiftRows(String in) {
        return null;
    }

    public String invSubBytes(String in) {
        return null;
    }

    public String invMixColumns(String in) {
        return null;
    }

}
